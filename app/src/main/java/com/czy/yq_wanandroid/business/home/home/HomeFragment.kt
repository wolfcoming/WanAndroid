package com.czy.yq_wanandroid.business.home.home

import android.Manifest
import android.content.Intent
import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.czy.business_base.ArouterConfig
import com.czy.business_base.entity.ArticleEntity
import com.czy.business_base.entity.Banner
import com.czy.business_base.event.LoginEvent
import com.czy.business_base.ext.toast
import com.czy.business_base.flowResult.FlowResult
import com.czy.business_base.mvpbase.MvpFragment
import com.czy.lib_log.HiLog
import com.czy.lib_net.ApiException
import com.czy.lib_qrcode.app.CaptureActivity
import com.czy.yq_wanandroid.R
import com.czy.business_base.adapter.CommonArticleListAdapter
import com.czy.yq_wanandroid.adapter.HomeBannerAdapter
import com.czy.yq_wanandroid.business.search.SearchActivity
import com.tbruyelle.rxpermissions3.RxPermissions
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_home.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.NullPointerException


class HomeFragment : MvpFragment<HomePresenter>(), IHomeView {
    var currentPage: Int = 0
    var articleList = ArrayList<ArticleEntity>()
    lateinit var articleAdapter: CommonArticleListAdapter
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    var temp = 0
    override fun initView() {
        changNormalTopView(activity, mTitleBar)
        mHomeRv.layoutManager = LinearLayoutManager(context)
        articleAdapter =
            CommonArticleListAdapter(articleList)
        mHomeRv.adapter = articleAdapter
        mSmartRefresh.setOnRefreshListener {
            getData(true)
        }

        mSmartRefresh.setOnLoadMoreListener {
            getData(false)
        }

        multiply.setErrorViewClickListener {
            getData(true)
        }
        multiply.setEmptyViewClickListener {
            getData(true)
        }
        mTitleBar.leftClickListener {
            if (true) {
//                ARouter.getInstance().build(ArouterConfig.webviewPath)
//                    .withString("url", "http://192.168.0.101:8080/#/jsAndroidCall")
//                    .withString("title", "http://192.168.0.101:8080/#/yunzhengtong")
//                    .navigation()

//                showLoading()
//                Handler().postDelayed({
//                    hideLoading()
//                }, 3000)

//                ARouter.getInstance().build("/login/sss").navigation()
                throw NullPointerException("自定义崩溃")

                return@leftClickListener
            }
            RxPermissions(this)
                .request(Manifest.permission.CAMERA)
                .subscribe {
                    if (it) {
                        FlowResult.Builder(activity)
                            .setIntent(Intent(context, CaptureActivity::class.java))
                            .addResultListener {
                                val result = it.getStringExtra("SCAN_RESULT")
                                result?.let {
                                    if (result.trim().startsWith("http")) {
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
        mTitleBar.rightClickListener {
            startActivity(Intent(activity, SearchActivity::class.java))
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
        getData(true)
    }


    override fun onVisible(isFirstVisible: Boolean) {
        super.onVisible(isFirstVisible)
        if (isFirstVisible) {
            multiply.showLoadingView()
            getData(true)
        }
    }

    fun getData(fresh: Boolean) {
        if (fresh) {
            currentPage = 0
        } else currentPage++
        mPresenter?.getHomeData(currentPage, fresh)
    }
}


