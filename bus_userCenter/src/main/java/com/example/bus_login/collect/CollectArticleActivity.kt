package com.example.bus_login.collect

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.czy.business_base.ArouterConfig
import com.czy.business_base.entity.ArticleEntity
import com.czy.business_base.mvpbase.MvpActivity
import com.czy.business_base.adapter.CommonArticleListAdapter
import com.example.bus_login.R
import com.infoholdcity.basearchitecture.self_extends.log
import kotlinx.android.synthetic.main.activity_collect.*

@Route(path = ArouterConfig.collectPage, extras = ArouterConfig.intercept_login)
class CollectArticleActivity : MvpActivity<CollectArticlePresenter>(), ICollectArticleView {
    override fun createPresenter(): CollectArticlePresenter {
        return CollectArticlePresenter()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_collect
    }

    override fun initView() {
        changNormalTopView(this, mTitleBar)
        mCollectRv.layoutManager = LinearLayoutManager(this)

    }

    override fun initData() {
        multiply.showLoadingView()
        mPresenter?.getCollectArticleData(0)
    }

    override fun showCollectArticle(datas: List<ArticleEntity>) {
        if (datas.isEmpty()) {
            multiply.showEmptyView()
            return
        }
        val adapter =
            CommonArticleListAdapter(datas as ArrayList<ArticleEntity>)
        mCollectRv.adapter = adapter
        multiply.showContentView()
    }

    override fun showFailureView(msg: String) {
        multiply.showErrorView(msg)
        msg.log()
    }
}