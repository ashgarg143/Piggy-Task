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
import piggy.co.in.piggytask.rest.responses.FundBestReturnItem;


public class FundBestReturnAdapter extends RecyclerView.Adapter<FundBestReturnAdapter.ViewHolder> {
    private Context context;
    private List<FundBestReturnItem> list;


    private int colors[] = {android.R.color.holo_blue_dark,R.color.green,R.color.purple,R.color.red,R.color.yellow,
            R.color.olive,R.color.orange,R.color.navy,R.color.pink,R.color.grey};

    public FundBestReturnAdapter(Context context, List<FundBestReturnItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public FundBestReturnAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.fund_best_return_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FundBestReturnAdapter.ViewHolder viewHolder, int i) {
        FundBestReturnItem item = list.get(i);

        viewHolder.ll.setBackgroundTintList(context.getResources().getColorStateList(colors[i]));
        viewHolder.tvFundName.setText(item.getFundName());
        viewHolder.perChange.setText(String.format("%.5f", item.getPerChange()) );
        viewHolder.fromDate.setText(item.getFromDate());
        viewHolder.todate.setText(item.getTodate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_fund_name) TextView tvFundName;
        @BindView(R.id.tv_value2) TextView fromDate;
        @BindView(R.id.tv_value3) TextView todate;
        @BindView(R.id.tv_value1) TextView perChange;
        @BindView(R.id.ll_best_return) LinearLayout ll;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }
    }
}
