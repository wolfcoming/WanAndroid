package com.czy.yq_wanandroid.business.readHistory

import androidx.recyclerview.widget.LinearLayoutManager
import com.czy.yq_wanandroid.R
import com.czy.yq_wanandroid.adapter.ReadHistoryAdapter
import com.czy.yq_wanandroid.mvpbase.MvpActivity
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
    }

    override fun initData() {
        mPresenter?.getHistory(0, 100)
    }

    override fun showHistory(result: List<ReadHistory>) {
        val adapter = ReadHistoryAdapter(result)
        mHistoryRv.adapter = adapter
    }
}