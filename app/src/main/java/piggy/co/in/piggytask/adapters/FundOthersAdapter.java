package piggy.co.in.piggytask.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import piggy.co.in.piggytask.R;
import piggy.co.in.piggytask.rest.responses.FundHoldingsItem;

public class FundOthersAdapter extends RecyclerView.Adapter<FundOthersAdapter.ViewHolder> {

    private int colors[] = {android.R.color.holo_blue_dark,R.color.green,R.color.purple,R.color.red,R.color.yellow,
            R.color.olive,R.color.orange,R.color.navy,R.color.pink,R.color.grey};

    private Context context;
    private String itemName;
    private List<FundHoldingsItem> list;

    public FundOthersAdapter(Context context,String itemName, List<FundHoldingsItem> list) {
        this.context = context;
        this.list = list;
        this.itemName = itemName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.fund_others_item,viewGroup,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        FundHoldingsItem holdings = list.get(position);

        String[] names;
        if(itemName.equals("holdings")){
            names= holdings.getHoldingNames().split("\n");
        } else {
            names = holdings.getSectorNames().split("\n");
        }

        viewHolder.tvFundName.setText(holdings.getFundName());
        viewHolder.ll.setBackgroundTintList(context.getResources().getColorStateList(colors[position]));

        for (int i = 0; i < names.length; i++) {
            TextView tv = addTextViews();
            tv.setText(names[i]);
            viewHolder.llHoldings.addView(tv);
        }

    }

    private TextView addTextViews() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tv = new TextView(context);
        params.setMargins(8,8,8,8);
        params.gravity = Gravity.CENTER;
        tv.setLayoutParams(params);
        tv.setPadding(8,4,8,4);
        tv.setBackground(context.getResources().getDrawable(R.drawable.search_box));
        tv.setBackgroundTintList(context.getResources().getColorStateList(R.color.white));
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(context.getResources().getColor(R.color.black));

        return tv;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_h_s_item) LinearLayout ll;
        @BindView(R.id.ll_top_h_s_names) LinearLayout llHoldings;
        @BindView(R.id.tv_fund_name) TextView tvFundName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);

        }
    }
}
