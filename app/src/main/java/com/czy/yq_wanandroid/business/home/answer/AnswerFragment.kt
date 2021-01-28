package com.czy.yq_wanandroid.business.home.answer

import androidx.recyclerview.widget.LinearLayoutManager
import com.czy.business_base.entity.ArticleEntity
import com.czy.business_base.event.LoginEvent
import com.czy.lib_base.mvpbase.MvpFragment
import com.czy.lib_base.net.ApiException
import com.czy.yq_wanandroid.R
import com.czy.yq_wanandroid.adapter.HomeArticleListAdapter
import com.yangqing.record.ext.toast
import kotlinx.android.synthetic.main.fragment_answer.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class AnswerFragment : MvpFragment<AnswerPresenter>(), IAnswerView {
    var curPage = 0
    override fun getLayoutId(): Int {
        return R.layout.fragment_answer
    }

    val datas = ArrayList<ArticleEntity>()
    lateinit var mAdapter: HomeArticleListAdapter
    override fun initView() {
        changNormalTopView(context!!, mTitleBar)
        mSmartRefresh.setOnLoadMoreListener {
            getData(true)
        }
        mSmartRefresh.setOnLoadMoreListener {
            getData(false)
        }
        mAnswerRv.layoutManager = LinearLayoutManager(context)
        mAdapter = HomeArticleListAdapter(datas)
        mAnswerRv.adapter = mAdapter

        multiply.setErrorViewClickListener {
            getData(true)
        }
        multiply.setEmptyViewClickListener {
            getData(true)
        }
    }

    override fun initData() {

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
        if (fresh) {
            this.datas.clear()
        }
        datas.addAll(result)
        mAdapter.notifyDataSetChanged()

    }

    override fun getDataFaile(e: ApiException, fresh: Boolean) {
        toast(e.message!!)
        if (fresh) multiply.showErrorView(e.message!!)
        mSmartRefresh.finishRefresh(false)
        mSmartRefresh.finishLoadMore(false)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginEvent(loginEvent: LoginEvent) {
        if (isDetached) return
        getData(true)
    }


    override fun onVisible(ifFirstVisiable: Boolean) {
        super.onVisible(ifFirstVisiable)
        if (ifFirstVisiable) {
            multiply.showLoadingView()
            getData(true)
        }
    }

    fun getData(fresh: Boolean) {
        if (fresh) {
            curPage = 0
        } else curPage++
        mPresenter?.getAnswerList(curPage, fresh)
    }
}