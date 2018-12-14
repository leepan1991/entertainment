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
import cn.innovativest.entertainment.bean.RecordBean;

public class RecordAdapter extends BaseAdapter {
    private Context context;
    private List<RecordBean> lstRecordBeans;

    public RecordAdapter(Context context, List<RecordBean> lstRecordBeans) {
        this.context = context;
        this.lstRecordBeans = lstRecordBeans;
    }

    public void onRefresh(List<RecordBean> lstRecordBeans) {
        this.lstRecordBeans = lstRecordBeans;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return lstRecordBeans.size();
    }

    public void setCount(int count) {
        this.lstRecordBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return lstRecordBeans.get(position);
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
                    R.layout.item_record, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        // 封装数据
        RecordBean recordBean = (RecordBean) getItem(position);

        holder.tvwRecordName.setText(recordBean.getTitle());

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tvwRecordName)
        TextView tvwRecordName;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
