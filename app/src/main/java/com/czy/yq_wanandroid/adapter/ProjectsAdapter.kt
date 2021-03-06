package com.czy.yq_wanandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.czy.business_base.entity.ProjectEntity
import com.czy.lib_ui.FlowLayout
import com.czy.yq_wanandroid.R
import com.czy.yq_wanandroid.business.projects.ProjectsListActivity

class ProjectsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    var datas = ArrayList<ProjectEntity>()

    constructor(data: ArrayList<ProjectEntity>) {
        this.datas = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.project_item, parent, false)
        return ProjectsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val projectsViewHolder = holder as ProjectsViewHolder
        projectsViewHolder.tvTitle.text = this.datas[position].name
        val childrenList = this.datas[position].children.map { it.name }
        projectsViewHolder.flowLayout.addData(childrenList as ArrayList<String>) {
            val entity = this.datas[position].children[it]
            ProjectsListActivity.start(holder.itemView.context, entity.name, entity.id)
        }
    }

    override fun getItemCount(): Int {
        return this.datas.size
    }


    class ProjectsViewHolder : RecyclerView.ViewHolder {
        lateinit var tvTitle: TextView
        var flowLayout: FlowLayout

        constructor(itemView: View) : super(itemView) {
            tvTitle = itemView.findViewById(R.id.tvTitle)
            flowLayout = itemView.findViewById(R.id.mFlowLayout)
        }
    }
}