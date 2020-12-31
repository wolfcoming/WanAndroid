package com.czy.yq_wanandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.czy.lib_base.ArouterConfig
import com.czy.lib_ui.SideslipLayout
import com.czy.yq_wanandroid.R
import com.czy.yq_wanandroid.room.AppDatabase
import com.czy.yq_wanandroid.room.entity.ReadHistory
import com.yangqing.record.ext.threadSwitch
import io.reactivex.rxjava3.core.Observable

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
        holder.btnDel.setOnClickListener { it ->
            Observable.create<Boolean> {
                AppDatabase.instance.readHistoryDao().delete(item.link)
                it.onNext(true)
            }
                .threadSwitch()
                .subscribe {
                    (datas as ArrayList).remove(item)
                    notifyDataSetChanged()
                    holder.sideslipLayout.close()
                }
        }
        holder.itemView.setOnClickListener {
            ARouter.getInstance().build(ArouterConfig.webviewPath)
                .withString("title", item.title)
                .withString("url", item.link)
                .navigation()
        }
    }

    override fun getItemCount(): Int {
        return this.datas.size
    }

    class ReadHistoryViewHolder : RecyclerView.ViewHolder {
        var tvTitle: TextView
        var tvTime: TextView
        var btnDel:TextView
        var sideslipLayout: SideslipLayout

        constructor(view: View) : super(view) {
            tvTitle = view.findViewById<TextView>(R.id.tvTitle)
            tvTime = view.findViewById<TextView>(R.id.tvTime)
            btnDel = view.findViewById(R.id.btnDel)
            sideslipLayout = view.findViewById(R.id.sideslip)
        }
    }
}