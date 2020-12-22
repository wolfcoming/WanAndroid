package com.czy.yq_wanandroid.business.home.home

import android.Manifest
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.czy.business_base.ArouterConfig
import com.czy.yq_wanandroid.R
import com.czy.yq_wanandroid.adapter.HomeArticleListAdapter
import com.czy.yq_wanandroid.adapter.HomeBannerAdapter
import com.czy.yq_wanandroid.entity.ArticleEntity
import com.czy.yq_wanandroid.entity.Banner
import com.czy.yq_wanandroid.event.LoginEvent
import com.czy.lib_base.mvpbase.MvpFragment
import com.czy.lib_base.net.ApiException
import com.czy.lib_qrcode.app.CaptureActivity
import com.czy.yq_wanandroid.flowResult.FlowResult
import com.tbruyelle.rxpermissions3.RxPermissions
import com.yangqing.record.ext.toast
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_home.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class HomeFragment : MvpFragment<HomePresenter>(), IHomeView {
    var currentPage: Int = 0
    var articleList = ArrayList<ArticleEntity>()
    lateinit var articleAdapter: HomeArticleListAdapter
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        changNormalTopView(context!!, mTitleBar)
        mHomeRv.layoutManager = LinearLayoutManager(context)
        articleAdapter = HomeArticleListAdapter(articleList)
        mHomeRv.adapter = articleAdapter
        mSmartRefresh.setOnRefreshListener {
            currentPage = 0
            mPresenter?.getHomeData(currentPage, true)

        }

        mSmartRefresh.setOnLoadMoreListener {
            currentPage++
            mPresenter?.getHomeData(currentPage, false)
        }

        multiply.setErrorViewClickListener {
            initData()
        }
        multiply.setEmptyViewClickListener {
            initData()
        }
        mTitleBar.leftClickListener {
            RxPermissions(this)
                .request(Manifest.permission.CAMERA)
                .subscribe {
                    if (it) {
                        FlowResult.Builder(activity)
                            .setIntent(Intent(context, CaptureActivity::class.java))
                            .addResultListener {
                                val result = it.getStringExtra("SCAN_RESULT")
                                result?.let {
                                    if (result.startsWith("http")) {
                                        ARouter.getInstance().build(ArouterConfig.webviewPath)
                                            .withString("url", result)
                                            .withString("title", result)
                                            .navigation()
                                    }
                                }
                            }
                            .call()
                    } else {
                        toast("暂无相机权限")
                    }
                }

        }
    }

    override fun initData() {

    }

    override fun createPresenter(): HomePresenter {
        return HomePresenter()
    }

    /**
     * 展示banner图数据
     */
    private fun showBannerView(result: List<Banner>?) {
        if (result != null) {
            mBanner.addBannerLifecycleObserver(this)
                .setAdapter(HomeBannerAdapter(result))
                .setIndicator(CircleIndicator(context))
        }
    }

    /**
     * 展示文章列表数据
     */
    private fun showArticleList(result: List<ArticleEntity>?, fresh: Boolean) {
        multiply.showContentView()
        if (fresh) {
            mSmartRefresh.finishRefresh()
        } else {
            if (result?.size == 0) {
                mSmartRefresh.finishLoadMoreWithNoMoreData()
            } else mSmartRefresh.finishLoadMore()
        }


        if (result.isNullOrEmpty()) {
            showToast("获取文章列表数据为空")
            return
        }
        if (fresh) {
            articleList.clear()
        }
        articleList.addAll(result)
        articleAdapter.notifyDataSetChanged()

    }


    override fun showData(
        articleList: List<ArticleEntity>?,
        bannerList: List<Banner>?,
        fresh: Boolean
    ) {
        showArticleList(articleList, fresh)
        showBannerView(bannerList)
    }

    override fun getDataFail(e: ApiException, fresh: Boolean) {
        toast(e.message!!)
        if (fresh) multiply.showErrorView(e.message!!)
        mSmartRefresh.finishRefresh(false)
        mSmartRefresh.finishLoadMore(false)
    }

    override fun isRegisterEventBus(): Boolean {
        return true
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginEvent(loginEvent: LoginEvent) {
        if (isDetached) return
        initData()
    }


    override fun onVisible(isFirstVisible: Boolean) {
        super.onVisible(isFirstVisible)
        if (isFirstVisible) {
            multiply.showLoadingView()
            mPresenter?.getHomeData(currentPage, true)
        }
    }
}


