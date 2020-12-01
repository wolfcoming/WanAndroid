package com.czy.yq_wanandroid.business.collect

import androidx.recyclerview.widget.LinearLayoutManager
import com.czy.yq_wanandroid.R
import com.czy.yq_wanandroid.entity.ArticleEntity
import com.czy.yq_wanandroid.mvpbase.MvpActivity
import com.infoholdcity.basearchitecture.self_extends.log
import kotlinx.android.synthetic.main.activity_collect.*

class CollectArticleActivity:MvpActivity<CollectArticlePresenter>(),ICollectArticleView {
    override fun createPresenter(): CollectArticlePresenter {
        return CollectArticlePresenter()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_collect
    }

    override fun initView() {
        mCollectRv.layoutManager = LinearLayoutManager(this)
    }

    override fun initData() {
        multiply.showLoadingView()
        mPresenter?.getCollectArticleData(0)
    }

    override fun showCollectArticle(datas: List<ArticleEntity>) {
        if(datas.size == 0) {
            multiply.showEmptyView()
            return
        }
        multiply.showContentView()
        datas.log()
    }

    override fun showFailureView(msg: String) {
        multiply.showErrorView(msg)
        msg.log()
    }
}