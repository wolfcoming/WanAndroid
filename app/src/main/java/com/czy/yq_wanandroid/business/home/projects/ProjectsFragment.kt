package com.czy.yq_wanandroid.business.home.projects

import androidx.recyclerview.widget.LinearLayoutManager
import com.czy.yq_wanandroid.R
import com.czy.yq_wanandroid.adapter.ProjectsAdapter
import com.czy.yq_wanandroid.entity.ProjectEntity
import com.czy.yq_wanandroid.mvpbase.MvpFragment
import kotlinx.android.synthetic.main.fragment_projects.*

class ProjectsFragment : MvpFragment<ProjectsPrensenter>(), IProjectsView {
    override fun getLayoutId(): Int {
        return R.layout.fragment_projects
    }

    lateinit var mAdapter: ProjectsAdapter
    val datas = ArrayList<ProjectEntity>()
    override fun initView() {
        changNormalTopView(context, mTitleBar)
        mProjectRv.layoutManager = LinearLayoutManager(context)
        mAdapter = ProjectsAdapter(datas)
        mProjectRv.adapter = mAdapter
        multiply.setErrorViewClickListener { initData() }
        multiply.setEmptyViewClickListener { initData() }
    }

    override fun initData() {
        multiply.showLoadingView()
        mPresenter?.getProjectsData()
    }

    override fun createPresenter(): ProjectsPrensenter {
        return ProjectsPrensenter()
    }

    override fun showProjectsData(result: List<ProjectEntity>?) {
        if (result.isNullOrEmpty() || result.size == 0) {
            multiply.showEmptyView()
            return
        }
        multiply.showContentView()
        this.datas.clear()
        this.datas.addAll(result)
        mAdapter.notifyDataSetChanged()


    }

    override fun showFailePage(faileMsg: String?) {
        if (faileMsg.isNullOrEmpty()) return
        multiply.showErrorView(faileMsg)
    }

}