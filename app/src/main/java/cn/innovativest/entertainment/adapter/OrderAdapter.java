package cn.innovativest.entertainment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.innovativest.entertainment.R;
import cn.innovativest.entertainment.bean.OrderBean;
import cn.innovativest.entertainment.bean.RecordBean;

public class OrderAdapter extends BaseAdapter {
    private Context context;
    private List<OrderBean> lstOrderBeans;

    public OrderAdapter(Context context, List<OrderBean> lstOrderBeans) {
        this.context = context;
        this.lstOrderBeans = lstOrderBeans;
    }

    public void onRefresh(List<OrderBean> lstOrderBeans) {
        this.lstOrderBeans = lstOrderBeans;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return lstOrderBeans.size();
    }

    public void setCount(int count) {
        this.lstOrderBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return lstOrderBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_order, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        // 封装数据
        OrderBean orderBean = (OrderBean) getItem(position);

        holder.tvScore.setText(orderBean.getFee());
        holder.tvDay.setText(orderBean.getDay());
        holder.tvPayTime.setText(orderBean.getTime());
        holder.tvPayStatus.setText(orderBean.getState());

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tvScore)
        TextView tvScore;
        @BindView(R.id.tvDay)
        TextView tvDay;
        @BindView(R.id.tvPayTime)
        TextView tvPayTime;
        @BindView(R.id.tvPayStatus)
        TextView tvPayStatus;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
