package piggy.co.in.piggytask.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import piggy.co.in.piggytask.R;
import piggy.co.in.piggytask.rest.AddToCompareListener;
import piggy.co.in.piggytask.rest.responses.FundItem;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {

    private Context context;
    private List<FundItem> dataResultsList;
    private AddToCompareListener listener;
    private List<String> keyList;

    public SearchResultsAdapter(Context context, List<String> keyList, List<FundItem> dataResultsList, AddToCompareListener listener) {
        this.context = context;
        this.dataResultsList = dataResultsList;
        this.listener = listener;
        this.keyList = keyList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.fund_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        FundItem item = dataResultsList.get(i);

        String name = item.getName();
        Double return1 = item.getYoy_return();
        Double return3 = item.getReturn_3yr();
        Double return5 = item.getReturn_5yr();
        final String key = item.getDetails_id();;

        viewHolder.tvFundName.setText(name);
        viewHolder.tvCategory.setAllCaps(true);
        viewHolder.tvCategory.setText(": "+item.getCategory());

        if(return1 == -1000){
            viewHolder.tvReturn1.setTextColor(context.getResources().getColor(R.color.yellow));
            viewHolder.tvReturn1.setText(": NA");
        } else {
            viewHolder.tvReturn1.setTextColor(context.getResources().getColor(R.color.green));
            viewHolder.tvReturn1.setText(": "+return1 + "%");
        }
        if(return3 == -1000){
            viewHolder.tvReturn3.setTextColor(context.getResources().getColor(R.color.yellow));
            viewHolder.tvReturn3.setText(": NA");
        } else {
            viewHolder.tvReturn3.setTextColor(context.getResources().getColor(R.color.green));
            viewHolder.tvReturn3.setText(": "+return3 + "%");
        }
        if(return5 == -1000){
            viewHolder.tvReturn5.setTextColor(context.getResources().getColor(R.color.yellow));
            viewHolder.tvReturn5.setText(": NA");
        } else {
            viewHolder.tvReturn5.setTextColor(context.getResources().getColor(R.color.green));
            viewHolder.tvReturn5.setText(": "+return5 + "%");
        }

        if(keyList.contains(key)){
            viewHolder.btAddToCompare.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            viewHolder.btAddToCompare.setText("Added to Compare");
        } else {

            viewHolder.btAddToCompare.setTextColor(context.getResources().getColor(R.color.black));
            viewHolder.btAddToCompare.setText("Add to Compare");
        }
        viewHolder.btAddToCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(keyList.size()<10){
                    viewHolder.btAddToCompare.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    viewHolder.btAddToCompare.setText("Added to Compare");

                    listener.onClick(key);
                } else {
                    Toast.makeText(context, "Sorry you can compare maximum 10 funds at a time", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }

    @Override
    public int getItemCount() {
        return dataResultsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_fund_name) TextView tvFundName;
        @BindView(R.id.tv_category) TextView tvCategory;
        @BindView(R.id.tv_return_1) TextView tvReturn1;
        @BindView(R.id.tv_return_3) TextView tvReturn3;
        @BindView(R.id.tv_return_5) TextView tvReturn5;
        @BindView(R.id.bt_add_to_compare) Button btAddToCompare;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }
    }
}
