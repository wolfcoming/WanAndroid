package com.czy.yq_wanandroid.fragment.home

import androidx.recyclerview.widget.LinearLayoutManager
import com.czy.yq_wanandroid.R
import com.czy.yq_wanandroid.adapter.HomeArticleListAdapter
import com.czy.yq_wanandroid.adapter.HomeBannerAdapter
import com.czy.yq_wanandroid.entity.ArticleEntity
import com.czy.yq_wanandroid.entity.Banner
import com.czy.yq_wanandroid.mvpbase.MvpFragment
import com.czy.yq_wanandroid.net.ApiException
import com.yangqing.record.ext.toast
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : MvpFragment<HomePresenter>(), IHomeView {
    var currentPage: Int = 0
    var articleList = ArrayList<ArticleEntity>()
    lateinit var articleAdapter: HomeArticleListAdapter
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        changNormalTopView(context!!, mTitleBar)
        mTitleBar.leftClickListener {
            mPresenter?.getArticleList(1, false)
        }

        mTitleBar.rightClickListener {
            mPresenter?.getArticleList(0, true)
        }

        mHomeRv.layoutManager = LinearLayoutManager(context)
        articleAdapter = HomeArticleListAdapter(articleList)
        mHomeRv.adapter = articleAdapter

        mSmartRefresh.setOnRefreshListener {
            currentPage = 0
            mPresenter?.getArticleList(currentPage, true)

        }

        mSmartRefresh.setOnLoadMoreListener {
            currentPage++
            mPresenter?.getArticleList(currentPage, false)
        }
    }

    override fun initData() {
        mPresenter?.getBannerData()
        mPresenter?.getArticleList(currentPage, true)
    }

    override fun createPresenter(): HomePresenter {
        return HomePresenter()
    }

    /**
     * 展示banner图数据
     */
    override fun showBannerView(result: List<Banner>?) {
        if (result != null) {
            mBanner.addBannerLifecycleObserver(this)
                .setAdapter(HomeBannerAdapter(result))
                .setIndicator(CircleIndicator(context))
        }
    }

    /**
     * 展示文章列表数据
     */
    override fun showArticleList(result: List<ArticleEntity>?, fresh: Boolean) {
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

    override fun getArticleListFail(e: ApiException) {
        toast(e.message!!)
        mSmartRefresh.finishRefresh(false)
        mSmartRefresh.finishLoadMore(false)
    }

}


