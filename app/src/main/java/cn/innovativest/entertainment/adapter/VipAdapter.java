package cn.innovativest.entertainment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.innovativest.entertainment.R;
import cn.innovativest.entertainment.bean.BuyBean;
import cn.innovativest.entertainment.ui.act.VipActivity;
import cn.innovativest.entertainment.utils.SPUtils;

public class VipAdapter extends BaseAdapter {
    private Context context;
    private List<BuyBean> listBuyItems;

    public VipAdapter(Context context, List<BuyBean> listBuyItems) {
        this.context = context;
        this.listBuyItems = listBuyItems;
    }

    public void onRefresh(List<BuyBean> listBuyItems) {
        this.listBuyItems = listBuyItems;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listBuyItems.size();
    }

    public void setCount(int count) {
        this.listBuyItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listBuyItems.get(position);
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
                    R.layout.item_vip, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        // 封装数据
        BuyBean buyBean = (BuyBean) getItem(position);

        holder.tvMonthNotice.setText(buyBean.getCard_title() + "会员-" + buyBean.getCard_day() + "天");
        BuyBean tempBuyBean = new Gson().fromJson((String) SPUtils.get(context, "vipselect", ""), BuyBean.class);
        if (tempBuyBean == null) {
            if (position == 0) {
                holder.ivMonth.setVisibility(View.VISIBLE);
                holder.ivMonth.setImageResource(R.mipmap.ic_share_checked);
                SPUtils.put(context, "vipselect", new Gson().toJson(buyBean));
            } else {
                holder.ivMonth.setVisibility(View.INVISIBLE);
            }
        } else {
            if (tempBuyBean.getCard_day().equals(buyBean.getCard_day())) {
                holder.ivMonth.setVisibility(View.VISIBLE);
                holder.ivMonth.setImageResource(R.mipmap.ic_share_checked);
            } else {
                holder.ivMonth.setVisibility(View.INVISIBLE);
            }
        }


        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.ivMonth)
        ImageView ivMonth;
        @BindView(R.id.tvMonthNotice)
        TextView tvMonthNotice;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
