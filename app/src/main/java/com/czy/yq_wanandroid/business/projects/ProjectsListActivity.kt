package com.czy.yq_wanandroid.business.projects

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.czy.business_base.entity.ArticleEntity
import com.czy.business_base.mvpbase.MvpActivity
import com.czy.yq_wanandroid.R
import com.czy.yq_wanandroid.adapter.HomeArticleListAdapter
import com.yangqing.record.ext.toast
import kotlinx.android.synthetic.main.activity_project_list.*

class ProjectsListActivity : MvpActivity<IProjectsListPresenter>(), IProjectsList {

    companion object {
        fun start(context: Context, title: String, cid: Int) {
            val intent = Intent(context, ProjectsListActivity::class.java)
            intent.putExtra("title", title)
            intent.putExtra("cid", cid)
            context.startActivity(intent)
        }
    }

    override fun createPresenter(): IProjectsListPresenter {
        return IProjectsListPresenter()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_project_list
    }

    private var cid: Int = 0
    private var page: Int = 0
    private val datas = ArrayList<ArticleEntity>()
    private var fresh: Boolean = true
    override fun initView() {
        changNormalTopView(this, mTitleBar)
        val title = intent.getStringExtra("title")
        mTitleBar.setTitle(title)
        cid = intent.getIntExtra("cid", 0)
        mProjectListRv.layoutManager = LinearLayoutManager(this)
        val mAdapter = HomeArticleListAdapter(datas)
        mProjectListRv.adapter = mAdapter
        mSmartRefresh.setOnLoadMoreListener {
            fresh = false
            page++
            mPresenter?.getProjectsList(page, cid)
        }
        mSmartRefresh.setOnRefreshListener {
            fresh = true
            page = 0
            mPresenter?.getProjectsList(page, cid)
        }
    }

    override fun initData() {
        fresh = true
        mPresenter?.getProjectsList(page, cid)
    }

    override fun showProjectsListView(result: ArrayList<ArticleEntity>?) {
        if (fresh) {
            this.datas.clear()
            mSmartRefresh.finishRefresh()
        } else {
            if (result?.size == 0) {
                mSmartRefresh.finishLoadMoreWithNoMoreData()
            } else mSmartRefresh.finishLoadMore()
        }
        result?.let {
            this.datas.addAll(it)
            mProjectListRv.adapter?.notifyDataSetChanged()
        }
    }

    override fun showFailureView(mes: String?) {
        toast(mes)
    }
}