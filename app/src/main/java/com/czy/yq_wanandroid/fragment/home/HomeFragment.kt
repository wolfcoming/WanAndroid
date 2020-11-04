package com.czy.yq_wanandroid.fragment.home

import androidx.recyclerview.widget.LinearLayoutManager
import com.czy.yq_wanandroid.R
import com.czy.yq_wanandroid.adapter.HomeArticleListAdapter
import com.czy.yq_wanandroid.adapter.HomeBannerAdapter
import com.czy.yq_wanandroid.entity.ArticleEntity
import com.czy.yq_wanandroid.entity.Banner
import com.czy.yq_wanandroid.mvpbase.MvpFragment
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : MvpFragment<HomePresenter>(), IHomeView {

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

    }

    override fun initData() {
        mPresenter?.getBannerData()
//        mPresenter?.getArticleList(0, true)
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

}


