package com.czy.yq_wanandroid.ui.fragment.answer

import androidx.recyclerview.widget.LinearLayoutManager
import com.czy.yq_wanandroid.R
import com.czy.yq_wanandroid.adapter.HomeArticleListAdapter
import com.czy.yq_wanandroid.entity.ArticleEntity
import com.czy.yq_wanandroid.mvpbase.MvpFragment
import com.czy.yq_wanandroid.net.ApiException
import com.infoholdcity.basearchitecture.self_extends.log
import com.yangqing.record.ext.toast
import kotlinx.android.synthetic.main.fragment_answer.*

class AnswerFragment:MvpFragment<AnswerPresenter>(),IAnswerView {
    var curPage = 0
    override fun getLayoutId(): Int {
        return R.layout.fragment_answer
    }

    val datas = ArrayList<ArticleEntity>()
    lateinit var mAdapter: HomeArticleListAdapter
    override fun initView() {
        changNormalTopView(context!!,mTitleBar)
        mSmartRefresh.setOnLoadMoreListener {
            curPage = 0
            mPresenter?.getAnswerList(curPage,true)
        }
        mSmartRefresh.setOnLoadMoreListener {
            curPage++
            mPresenter?.getAnswerList(curPage,false)
        }
        mAnswerRv.layoutManager = LinearLayoutManager(context)
        mAdapter = HomeArticleListAdapter(datas)
        mAnswerRv.adapter = mAdapter

        multiply.setErrorViewClickListener {
            initData()
        }
        multiply.setEmptyViewClickListener {
            initData()
        }
    }

    override fun initData() {
        multiply.showLoadingView()
        mPresenter?.getAnswerList(curPage, true)
    }

    override fun createPresenter(): AnswerPresenter {
        return AnswerPresenter()
    }

    override fun showAnswerList(result: List<ArticleEntity>?, fresh: Boolean) {
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
        if(fresh){
            this.datas.clear()
        }
        datas.addAll(result)
        mAdapter.notifyDataSetChanged()

    }

    override fun getDataFaile(e: ApiException, fresh: Boolean) {
        "wendang".log()
        toast(e.message!!)
        if (fresh) multiply.showErrorView(e.message)
        mSmartRefresh.finishRefresh(false)
        mSmartRefresh.finishLoadMore(false)
    }
}