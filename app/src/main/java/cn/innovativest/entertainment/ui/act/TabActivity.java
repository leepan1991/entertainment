package cn.innovativest.entertainment.ui.act;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.innovativest.entertainment.R;
import cn.innovativest.entertainment.adapter.UniFragmentPagerAdapter;
import cn.innovativest.entertainment.base.BaseMvpActivity;
import cn.innovativest.entertainment.bean.TabBean;
import cn.innovativest.entertainment.common.HttpRespond;
import cn.innovativest.entertainment.presenter.TabPresenter;
import cn.innovativest.entertainment.ui.frag.GetFilmFragment;
import cn.innovativest.entertainment.ui.frag.IndexFragment;
import cn.innovativest.entertainment.ui.frag.MyCenterFragment;
import cn.innovativest.entertainment.ui.frag.SearchFilmFragment;
import cn.innovativest.entertainment.ui.frag.TvFragment;
import cn.innovativest.entertainment.utils.LogUtils;
import cn.innovativest.entertainment.view.TabView;
import cn.innovativest.entertainment.widget.NoScrollViewPager;

public class TabActivity extends BaseMvpActivity<TabView, TabPresenter> implements TabView {
    public static final String EXIT_LOGIN = "exit_login";
    public static final int GO_SHOP = 111;
    @BindView(R.id.vp_home)
    NoScrollViewPager mVpHome;
    @BindView(R.id.tb_main)
    JPTabBar tbMain;

    private SearchFilmFragment searchFilmFragment;
    private TvFragment tvFragment;
    private IndexFragment indexFragment;
    private GetFilmFragment getFilmFragment;

    private UniFragmentPagerAdapter uniFragmentPagerAdapter;
    @Titles
    private static final String[] tabNames = {"搜片", "电视", "推荐", "求片", "我的"};
    @NorIcons
    private static final int[] normalTabRes = {R.mipmap.ic_home_normal, R.mipmap.ic_alarm_normal, R.mipmap.ic_alarm_normal,
            R.mipmap.ic_gift_normal, R.mipmap.ic_mine_normal};
    @SeleIcons
    private static final int[] selectedTabRes = {R.mipmap.ic_home_selected, R.mipmap.ic_alarm_selected, R.mipmap.ic_alarm_selected,
            R.mipmap.ic_gift_selected, R.mipmap.ic_mine_selected};
    private List<Fragment> mFragments;

    @Override
    protected int getStatusBarBackground() {
        return Color.TRANSPARENT;
    }

    @Override
    protected boolean isLightMode() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tab;
    }

    @Override
    public void initView() {
        mVpHome.setOffscreenPageLimit(5);
        mFragments = new ArrayList<>();
//        mFragments.add(new FirstTabFrag());
//        mFragments.add(new FlashSaleFragment());  //限时抢购
//        mFragments.add(new IntegralStoreFragment());
//        mFragments.add(new MyCenterFragment());
        searchFilmFragment = new SearchFilmFragment();
        mFragments.add(searchFilmFragment);
        tvFragment = new TvFragment();
        mFragments.add(tvFragment);
        indexFragment = new IndexFragment();
        mFragments.add(indexFragment);
        getFilmFragment = new GetFilmFragment();
        mFragments.add(getFilmFragment);
        mFragments.add(new MyCenterFragment());
        uniFragmentPagerAdapter = new UniFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        mVpHome.setAdapter(uniFragmentPagerAdapter);
        initTab();
    }

    @Override
    public void initData() {
//        ClipboardManager clipboard =
//                (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//        if (!TextUtils.isEmpty(clipboard.getText())) {
        mPresenter.onGetBottomTab();
//        }
    }

    /**
     * 初始化TAB标签
     */
    private void initTab() {

        tbMain.setContainer(mVpHome);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        LogUtils.e(requestCode + " " + resultCode);
        switch (requestCode) {
            case LoginActivity.REQUEST_CODE:
                if (resultCode == LoginActivity.RESULT_CODE &&
                        data.getBooleanExtra(LoginActivity.IS_LOGINED, false)) {
                    if (mFragments != null) {
                        mVpHome.setCurrentItem(data.getIntExtra("item_id", 0));
                    }
                }
                break;
//            case GO_SHOP:
//                if (resultCode == 11 && mFragments != null) {
//                    mVpHome.setCurrentItem(2);
//                }
//                break;
            default:
                break;
        }
    }

    @Override
    public TabPresenter initPresenter() {
        return new TabPresenter();
    }

    @Override
    public void onGetBottomTab(HttpRespond<TabBean> respond) {
//        Log.e("taoPwd", "onGetTaoPwd: " + respond.message + " " + respond.data.value);
        if (respond != null && respond.data != null) {

            if (!TextUtils.isEmpty(respond.data.getSoupian())) {
//                SearchFilmFragment searchFilmFragment = new SearchFilmFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("url", respond.data.getSoupian());
//                searchFilmFragment.setArguments(bundle);
//                mFragments.add(searchFilmFragment);
                searchFilmFragment.setUrlAndId(respond.data.getSoupian(),0);
//                searchFilmFragment.setUrl(respond.data.getSoupian());
//                searchFilmFragment.setItem_id(0);
            }
            if (!TextUtils.isEmpty(respond.data.getDianshi())) {
//                TvFragment tvFragment = new TvFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("url", respond.data.getDianshi());
//                tvFragment.setArguments(bundle);
//                mFragments.add(tvFragment);
                tvFragment.setUrlAndId(respond.data.getDianshi(),1);
//                tvFragment.setUrl(respond.data.getDianshi());
//                tvFragment.setItem_id(1);
            }
            if (!TextUtils.isEmpty(respond.data.getShouye())) {
//                IndexFragment indexFragment = new IndexFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("url", respond.data.getShouye());
//                indexFragment.setArguments(bundle);
//                mFragments.add(indexFragment);
                indexFragment.setUrlAndId(respond.data.getShouye(),2);
//                indexFragment.setUrl(respond.data.getShouye());
//                indexFragment.setItem_id(2);
            }
            if (!TextUtils.isEmpty(respond.data.getQiupian())) {
//                GetFilmFragment getFilmFragment = new GetFilmFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("url", respond.data.getQiupian());
//                getFilmFragment.setArguments(bundle);
//                mFragments.add(getFilmFragment);
                getFilmFragment.setUrlAndId(respond.data.getQiupian(),3);
//                getFilmFragment.setUrl(respond.data.getQiupian());
//                getFilmFragment.setItem_id(3);
            }

//            mFragments.add(new MyCenterFragment());
            uniFragmentPagerAdapter.notifyDataSetChanged();
            // 登录状态检测，确认是否展示我的页面
            mVpHome.setCheckLoginListener(new NoScrollViewPager.CheckLoginListener() {
                @Override
                public void jumpToLogin(int item) {
                    Intent loginIntent = new Intent(TabActivity.this, LoginActivity.class);
                    loginIntent.putExtra("item_id", item);
                    startActivityForResult(loginIntent, LoginActivity.REQUEST_CODE);
                }
            });
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            boolean isExit = intent.getBooleanExtra(EXIT_LOGIN, false);
            if (isExit) {
                mVpHome.setCurrentItem(0);
            }
        }
    }
}