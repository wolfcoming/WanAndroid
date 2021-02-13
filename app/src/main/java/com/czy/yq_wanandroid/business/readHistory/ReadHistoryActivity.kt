package com.czy.yq_wanandroid.business.readHistory

import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import com.czy.business_base.ext.toast
import com.czy.business_base.mvpbase.MvpActivity
import com.czy.yq_wanandroid.R
import com.czy.yq_wanandroid.adapter.ReadHistoryAdapter
import com.czy.yq_wanandroid.room.entity.ReadHistory
import kotlinx.android.synthetic.main.activity_readhistory.*

class ReadHistoryActivity : MvpActivity<ReadHistoryPresenter>(), IReadHistoryView {
    override fun createPresenter(): ReadHistoryPresenter {
        return ReadHistoryPresenter()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_readhistory
    }

    override fun initView() {
        changNormalTopView(this, mTitleBar)
        mHistoryRv.layoutManager = LinearLayoutManager(this)
        multiply.setEmptyViewClickListener {
            initData()
        }

        multiply.setErrorViewClickListener {
            initData()
        }
    }

    override fun initData() {
        multiply.showLoadingView()
        Handler().postDelayed({
            mPresenter?.getHistory(0, 100)
        },1000)

    }

    override fun showHistory(result: List<ReadHistory>) {
        if(result.isEmpty()) {
            multiply.showEmptyView()
            return
        }
        multiply.showContentView()
        val adapter = ReadHistoryAdapter(result)
        mHistoryRv.adapter = adapter
    }


    override fun showLoadError(msg: String) {
        multiply.showErrorView(msg)
        toast(msg)
    }
}