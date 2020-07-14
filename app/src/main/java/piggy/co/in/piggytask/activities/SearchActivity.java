package piggy.co.in.piggytask.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import piggy.co.in.piggytask.R;
import piggy.co.in.piggytask.adapters.SearchResultsAdapter;
import piggy.co.in.piggytask.rest.AddToCompareListener;
import piggy.co.in.piggytask.rest.RetrofitClient;
import piggy.co.in.piggytask.rest.responses.FundItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//search activity for searching all funds
public class SearchActivity extends AppCompatActivity implements AddToCompareListener {


    private static final String TAG = SearchActivity.class.getSimpleName();

    @BindView(R.id.pb_activity_search) ProgressBar pbSearchFund;
    @BindView(R.id.rl) RelativeLayout relativeLayout;
    @BindView(R.id.rv_search_funds) RecyclerView rvSearchFunds;
    @BindView(R.id.bt_go_to_compare) Button btGoToCompare;
    @BindView(R.id.tv_no_funds) TextView tvNofunds;

    private ArrayList<String> keysList;
    private List<FundItem> dataResultsList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //binding all views
        ButterKnife.bind(this);

        //creating all lists
        createLists();

        //initialising recycler views
        initRecyclerViews();

        //searching all mutual funds
        searchMutualFund();


    }

    private void initRecyclerViews() {
        rvSearchFunds.setHasFixedSize(true);
        rvSearchFunds.setLayoutManager(new LinearLayoutManager(this));
    }

    private void createLists() {
        dataResultsList = new ArrayList<>();
        keysList = new ArrayList<>();
    }

    private void searchMutualFund() {
        dataResultsList.clear();
        JsonObject json = new JsonObject();
        json.addProperty("search","hdfc");
        json.addProperty("rows","2");
        json.addProperty("offset","1");

        Call<ResponseBody> responseBodyCall = RetrofitClient.getRetrofitClient()
                .connectUser()
                .searchMutualFund(json);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    relativeLayout.setVisibility(View.VISIBLE);
                    pbSearchFund.setVisibility(View.GONE);

                    String s="";
                    try {
                        s = response.body().string();
                        extractDataFromResponse(s);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if(dataResultsList.size()==0){
                        btGoToCompare.setVisibility(View.GONE);
                        tvNofunds.setVisibility(View.VISIBLE);
                    }
                    SearchResultsAdapter adapter = new SearchResultsAdapter(SearchActivity.this,keysList,dataResultsList,SearchActivity.this);
                    rvSearchFunds.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "onFailure: "+ t.getMessage());
            }
        });

    }

    private void extractDataFromResponse(String s) throws JSONException {
        JSONObject jsonObject = new JSONObject(s);
        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray search_results = data.getJSONArray("search_results");

        for (int i = 0; i < search_results.length() ; i++) {
            JSONObject object = search_results.getJSONObject(i);

            Double return1 = object.getDouble("yoy_return");
            Double return3 = object.getDouble("return_3yr");
            Double return5 = object.getDouble("return_5yr");
            String key = object.getString("details_id");
            String category = object.getString("category");
            String name = object.getString("name");

            FundItem item = new FundItem(name,category,key,return1,return3,return5);
            dataResultsList.add(item);
        }
    }


    // receiving callback for adding a fund to compare
    @Override
    public void onClick(String key) {
        if(!keysList.contains(key)) {
            keysList.add(key);
        } else {
            Toast.makeText(this, "Already added for comparison", Toast.LENGTH_SHORT).show();
        }
    }

    // implementing onclick listener for add to compare button
    @OnClick(R.id.bt_go_to_compare)
    public void addTocompare(){
        Log.i(TAG, "onCreate: key list size: " + keysList.size());
        if(keysList.size() <2){
            Toast.makeText(SearchActivity.this, "Please select minimum 2 funds to compare", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(SearchActivity.this,FundComparisonActivity.class);
            intent.putStringArrayListExtra("keys",keysList);
            startActivity(intent);
        }
    }
}

