package piggy.co.in.piggytask.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import piggy.co.in.piggytask.R;
import piggy.co.in.piggytask.rest.responses.FundDetailsItem;

public class FundDetailsAdapter extends RecyclerView.Adapter<FundDetailsAdapter.ViewHolder> {

    private Context context;
    private List<FundDetailsItem> list;

    private int colors[] = {android.R.color.holo_blue_dark,R.color.green,R.color.purple,R.color.red,R.color.yellow,
            R.color.olive,R.color.orange,R.color.navy,R.color.pink,R.color.grey};

    public FundDetailsAdapter(Context context, List<FundDetailsItem> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.fund_details_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        FundDetailsItem item = list.get(i);

        Double return1 = item.getYoy_return();
        Double return3 = item.getReturn_3yr();
        Double return5 = item.getReturn_5yr();

        viewHolder.tvFundName.setText(item.getFund_name());
        viewHolder.tv1.setText(item.getRating());

        if(return1 == -1000){
            viewHolder.tv2.setTextColor(context.getResources().getColor(R.color.yellow));
            viewHolder.tv2.setText(" NA");
        } else {
            viewHolder.tv2.setTextColor(context.getResources().getColor(R.color.black));

            viewHolder.tv2.setText(return1 + "%");
        }
        if(return3 == -1000){
            viewHolder.tv3.setTextColor(context.getResources().getColor(R.color.yellow));
            viewHolder.tv3.setText(" NA");
        } else {
            viewHolder.tv3.setTextColor(context.getResources().getColor(R.color.black));
            viewHolder.tv3.setText(return3 + "%");
        }
        if(return5 == -1000){
            viewHolder.tv4.setTextColor(context.getResources().getColor(R.color.yellow));
            viewHolder.tv4.setText(" NA");
        } else {
            viewHolder.tv4.setTextColor(context.getResources().getColor(R.color.black));
            viewHolder.tv4.setText(return5 + "%");
        }
        viewHolder.ll.setBackgroundTintList(context.getResources().getColorStateList(colors[i]));
        viewHolder.tv5.setText(item.getCategory());
        viewHolder.tv6.setText(item.getRiskometer());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_details) LinearLayout ll;
        @BindView(R.id.tv_fund_name) TextView tvFundName;
        @BindView(R.id.tv_value1) TextView tv1;
        @BindView(R.id.tv_value2) TextView tv2;
        @BindView(R.id.tv_value3) TextView tv3;
        @BindView(R.id.tv_value4) TextView tv4;
        @BindView(R.id.tv_value5) TextView tv5;
        @BindView(R.id.tv_value6) TextView tv6;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }
    }
}
