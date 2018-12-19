package cn.innovativest.entertainment.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.innovativest.entertainment.R;
import cn.innovativest.entertainment.utils.ToastUtils;


public class ReechargeDialog extends Dialog implements View.OnClickListener {
    public static int TYPE_CONFIRM = 1;
    public static int TYPE_TIP = 2;
    @BindView(R.id.dialog_msg_tv)
    TextView mMsgTv;

    @BindView(R.id.dialog_left_bt)
    Button mLeftBt;

    @BindView(R.id.dialog_right_bt)
    Button mRightBt;

    @BindView(R.id.edtSDSD)
    EditText edtSDSD;

    ChooseListener mListener;

    public ReechargeDialog(final Context context) {
        super(context, R.style.mDialog);
        setContentView(R.layout.dialog_recharge);
        ButterKnife.bind(this);

        mLeftBt.setOnClickListener(this);
        mRightBt.setOnClickListener(this);

    }

    public ReechargeDialog isCancelable(boolean flag) {
        setCancelable(flag);
        return this;
    }

    public ReechargeDialog setMsg(String msg) {
        mMsgTv.setText(msg);
        edtSDSD.setText("");
        edtSDSD.requestFocus();
        return this;
    }


    public ReechargeDialog setMRightBt(String mRightBtTxt) {

        mRightBt.setText(mRightBtTxt);
        return this;
    }

    public ReechargeDialog setMLeftBtt(String mLeftBtTxt) {

        mLeftBt.setText(mLeftBtTxt);
        return this;
    }

    public String getEdtSDSDText() {
        return edtSDSD.getText().toString();
    }

    public ReechargeDialog setEdtSDSDHint(String edtSDSDText) {
        edtSDSD.setHint(edtSDSDText);
        return this;
    }


    public ReechargeDialog setIsCancelable(boolean flag) {

        setCancelable(flag);
        return this;
    }

    public ReechargeDialog setType(int type) {
        if (type == TYPE_TIP) {
            findViewById(R.id.dialog_line).setVisibility(View.GONE);
            findViewById(R.id.dialog_left_bt).setVisibility(View.GONE);
        }
        return this;
    }

    public ReechargeDialog setBtStr(String... BtStr) {

        if (BtStr != null) {
            if (BtStr.length >= 1) {
                mLeftBt.setText(BtStr[0]);
            }
            if (BtStr.length >= 2) {
                mRightBt.setText(BtStr[1]);
            }

        }
        return this;
    }

    public ReechargeDialog setChooseListener(ChooseListener l) {
        mListener = l;
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_left_bt:
                dismiss();
                if (mListener != null) {
                    mListener.onChoose(ChooseListener.WHICH_LEFT);
                }
                break;
            case R.id.dialog_right_bt:
                wallet();
                break;
        }
    }

    private void wallet() {
        if (TextUtils.isEmpty(edtSDSD.getText().toString())) {
            ToastUtils.showShort(getContext(), "请输入充值码");
            return;
        }
        dismiss();
        if (mListener != null) {
            mListener.onChoose(ChooseListener.WHICH_RIGHT);
        }
    }


    public interface ChooseListener {
        public final static int WHICH_LEFT = 1;
        public final static int WHICH_RIGHT = 2;

        /**
         * which=1,左button。 which=2,右button。
         */
        void onChoose(int which);
    }

}
