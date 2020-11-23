package com.czy.yq_wanandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.czy.yq_wanandroid.R
import com.czy.yq_wanandroid.room.entity.ReadHistory

open class ReadHistoryAdapter : RecyclerView.Adapter<ReadHistoryAdapter.ReadHistoryViewHolder> {
    var datas: List<ReadHistory>

    constructor(data: List<ReadHistory>) {
        this.datas = data
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReadHistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_readhistory_item, parent, false)
        return ReadHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReadHistoryViewHolder, position: Int) {
        val item = this.datas[position]
        holder.tvTime.text = item.time.toString()
        holder.tvTitle.text = item.title
    }

    override fun getItemCount(): Int {
        return this.datas.size
    }

    class ReadHistoryViewHolder : RecyclerView.ViewHolder {
        var tvTitle: TextView
        var tvTime: TextView

        constructor(view: View) : super(view) {
            tvTitle = view.findViewById<TextView>(R.id.tvTitle)
            tvTime = view.findViewById<TextView>(R.id.tvTime)
        }
    }
}