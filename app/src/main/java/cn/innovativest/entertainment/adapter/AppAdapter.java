package cn.innovativest.entertainment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.innovativest.entertainment.R;
import cn.innovativest.entertainment.bean.AppBean;

public class AppAdapter extends BaseAdapter {
    private Context context;
    private List<AppBean> listAppItems;

    public AppAdapter(Context context, List<AppBean> listAppItems) {
        this.context = context;
        this.listAppItems = listAppItems;
    }

    public void onRefresh(List<AppBean> listAppItems) {
        this.listAppItems = listAppItems;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listAppItems.size();
    }

    public void setCount(int count) {
        this.listAppItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listAppItems.get(position);
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
                    R.layout.item_app, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        // 封装数据
        AppBean appItem = (AppBean) getItem(position);

        Glide.with(context).load("http://ath.pub" + appItem.img).into(holder.iv_logo);
        holder.tvwName.setText(appItem.name);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_logo)
        ImageView iv_logo;
        @BindView(R.id.tvwName)
        TextView tvwName;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
