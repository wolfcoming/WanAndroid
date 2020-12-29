package com.czy.yq_wanandroid.business.search

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import com.czy.lib_base.mvpbase.MvpActivity
import com.czy.lib_base.utils.KeyboardUtils
import com.czy.yq_wanandroid.R
import com.czy.yq_wanandroid.adapter.HomeArticleListAdapter
import com.czy.yq_wanandroid.entity.ArticleEntity
import com.czy.yq_wanandroid.entity.HotKey
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.mTitleBar
import kotlinx.android.synthetic.main.activity_search.multiply

class SearchActivity : MvpActivity<SearchPresenter>(), ISearchView {
    val datas: ArrayList<ArticleEntity> = ArrayList()

    override fun createPresenter(): SearchPresenter {
        return SearchPresenter()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    var isFresh = true
    var pageIndex = 0
    var currentWords = ""
    override fun initView() {
        changNormalTopView(this, mTitleBar)
        mSearchRv.layoutManager = LinearLayoutManager(this)
        mSearchRv.adapter = HomeArticleListAdapter(datas)

        mSmartRefresh.setOnLoadMoreListener {
            getContentData(false)
        }
        mSmartRefresh.setOnRefreshListener {
            getContentData(true)
        }

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (s.isBlank()) {
                    showContentView(false)
                }
                currentWords = s.toString()
            }
        })
        etSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                getContentData(true)
                KeyboardUtils.hideSoftInput(this)
                return@setOnEditorActionListener true
            }
            false
        }
        ivSearch.setOnClickListener {
            getContentData(true)
        }

        ivBack.setOnClickListener { finish() }
    }

    override fun initData() {
        mPresenter?.getHotKey()
    }

    override fun showHotKeys(list: List<HotKey>) {
        val names = list.map { it.name }
        flHot.addData(names as ArrayList<String>) {
            currentWords = names[it]
            etSearch.setText(currentWords)
            getContentData(true)
        }
    }

    override fun getHotKeysFailed(msg: String) {
        showToast(msg)
    }

    override fun showHistoryInput(list: List<HotKey>) {

    }


    override fun showArticleList(result: ArrayList<ArticleEntity>) {
        if (isFresh) {
            datas.clear()
        }
        multiply.showContentView()
        if (isFresh) {
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

        datas.addAll(result)
        mSearchRv.adapter?.notifyDataSetChanged()
    }

    override fun getArticleListFailed(msg: String) {
        multiply.showErrorView(msg)
    }


    fun showContentView(show: Boolean) {
        multiply.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun getContentData(fresh: Boolean) {
        showContentView(true)
        isFresh = fresh
        pageIndex = if (fresh) 0 else pageIndex++
        if (isFresh) {
            multiply.showLoadingView()
        }
        mPresenter?.searchArticle(pageIndex, currentWords)
    }


}