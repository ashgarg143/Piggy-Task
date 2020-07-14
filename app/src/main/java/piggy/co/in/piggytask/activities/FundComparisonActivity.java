package piggy.co.in.piggytask.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import piggy.co.in.piggytask.R;
import piggy.co.in.piggytask.adapters.FundBestReturnAdapter;
import piggy.co.in.piggytask.adapters.FundDetailsAdapter;
import piggy.co.in.piggytask.adapters.FundOthersAdapter;
import piggy.co.in.piggytask.rest.RetrofitClient;
import piggy.co.in.piggytask.rest.responses.FundBestReturnItem;
import piggy.co.in.piggytask.rest.responses.FundDetailsItem;
import piggy.co.in.piggytask.rest.responses.FundHoldingsItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//activity for comparing 2 or more funds
public class FundComparisonActivity extends AppCompatActivity {

    private static final String TAG = FundComparisonActivity.class.getSimpleName();
    private Context context = FundComparisonActivity.this;

    @BindView(R.id.rv_fund_details) RecyclerView rvFundDetails;
    @BindView(R.id.rv_best_return) RecyclerView rvFundBestReturn;
    @BindView(R.id.rv_others) RecyclerView rvTopHoldings;
    @BindView(R.id.rv_top_sectors) RecyclerView rvTopSectors;
    @BindView(R.id.pb_activity_fund_comparison) ProgressBar pb;
    @BindView(R.id.ll_fund_comparison) LinearLayout llFundComparison;

    private ArrayList<String> keysList;
    private List<FundDetailsItem> fundDetailsItemList;
    private List<FundBestReturnItem> fundBestReturnItemsList;
    private List<FundHoldingsItem> fundHoldingsItemList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_comparison);
        setTitle("Compare Funds");

        if(getSupportActionBar() !=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //binding all views
        ButterKnife.bind(this);

        //creating all lists
        createLists();

        //receiving intent for key list from search activity
        keysList = getIntent().getStringArrayListExtra("keys");

        //requesting details of all funds which has to compared
        getAllFunds();

        //initialising recycler views
        initRecyclerViews();


    }

    private void getAllFunds() {
        for (int i = 0; i < keysList.size(); i++) {
            //requesting all details of a fund
            getFund(keysList.get(i),i);
        }
    }

    private void initRecyclerViews() {
        rvFundDetails.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        rvFundBestReturn.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        rvTopHoldings.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        rvTopSectors.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
    }

    private void createLists() {
        keysList = new ArrayList<>();
        fundDetailsItemList = new ArrayList<>();
        fundBestReturnItemsList = new ArrayList<>();
        fundHoldingsItemList = new ArrayList<>();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getFund(String key, final int i) {

        Call<ResponseBody> responseBodyCall = RetrofitClient.getRetrofitClient()
                .connectUser()
                .getMutualFund(key);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()){

                    String s="";

                    try {
                        s = response.body().string();
                        JSONObject object = new JSONObject(s);

                        JSONObject data = object.getJSONObject("data");
                        JSONObject mutualFund = data.getJSONObject("mutual_fund");
                        JSONObject details = mutualFund.getJSONObject("details");
                        String fund_name = details.getString("name");

                        //fund details
                        FundDetailsItem detailsItem = extractFundDetails(details);
                        fundDetailsItemList.add(detailsItem);

                        //fund best return
                        FundBestReturnItem bestReturnItem = extractFundBestReturn(mutualFund);
                        fundBestReturnItemsList.add(bestReturnItem);

                        JSONObject holdings = data.getJSONObject("holdings");

                        //fund top holding
                        String topHoldingNames = extractTopHoldings(holdings);

                        //fund top sectors
                        String topSectorNames = extractTopSectors(holdings);

                        FundHoldingsItem holdingsItem = new FundHoldingsItem(fund_name,topSectorNames,topHoldingNames);
                        fundHoldingsItemList.add(holdingsItem);


                        if (i == keysList.size()-1){
                            pb.setVisibility(View.GONE);
                            llFundComparison.setVisibility(View.VISIBLE);

                            rvFundDetails.setAdapter(new FundDetailsAdapter(context,fundDetailsItemList));
                            rvFundBestReturn.setAdapter(new FundBestReturnAdapter(context,fundBestReturnItemsList));
                            rvTopHoldings.setAdapter(new FundOthersAdapter(context,"holdings", fundHoldingsItemList));
                            rvTopSectors.setAdapter(new FundOthersAdapter(context,"sectors", fundHoldingsItemList));

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "onFailure: "+ t.getMessage());
            }
        });

    }

    //all top sectors of a fund
    private String extractTopSectors(JSONObject holdings) throws JSONException {
        JSONArray topSectors = holdings.getJSONObject("top_5_sectors").getJSONArray("values");

        String sectorNames="";
        for (int j = 0; j < topSectors.length()-1; j++) {
            String name = topSectors.getJSONObject(j).getString("sector");
            sectorNames+=name+"\n";
        }
        sectorNames+=topSectors.getJSONObject(topSectors.length()-1).getString("sector");
        return sectorNames;

    }

    //all top holdings of a fund
    private String extractTopHoldings(JSONObject holdings) throws JSONException {
        JSONArray topHoldings = holdings.getJSONObject("top_10_holdings").getJSONArray("values");

        String holdingName="";
        for (int j = 0; j < topHoldings.length()-1; j++) {
            String name = topHoldings.getJSONObject(j).getString("script");
            holdingName=holdingName+name+"\n";
        }
        holdingName+=topHoldings.getJSONObject(topHoldings.length()-1).getString("script");
        return holdingName;
    }

    //best return of a fund within a date
    private FundBestReturnItem extractFundBestReturn(JSONObject mutualFund) throws JSONException {
        JSONObject details = mutualFund.getJSONObject("details");
        String fund_name = details.getString("name");

        JSONObject bestReurn = mutualFund.getJSONObject("best_return");
        String toDate = bestReurn.getString("todate");
        Double perChange = bestReurn.getDouble("percent_change");
        String fromdate = bestReurn.getString("fromdate");

        FundBestReturnItem bestReturnItem = new FundBestReturnItem(fund_name,fromdate,toDate,perChange);
        return bestReturnItem;
    }

    //fund details
    private FundDetailsItem extractFundDetails(JSONObject details) throws JSONException {
        String fund_name = details.getString("name");
        String rating = details.getString("rating");
        Double return_1yr = details.getDouble("yoy_return");
        Double return_3yr = details.getDouble("return_3yr");
        Double return_5yr = details.getDouble("return_5yr");
        String category = details.getString("scheme_class");
        String riskometer = details.getString("riskometer");

        FundDetailsItem detailsItem = new FundDetailsItem(fund_name,rating,category,riskometer,return_1yr,return_3yr,return_5yr);
        return detailsItem;

    }
}
