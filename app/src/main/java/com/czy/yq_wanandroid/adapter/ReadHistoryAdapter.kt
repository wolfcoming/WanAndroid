package com.czy.yq_wanandroid.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.czy.yq_wanandroid.R
import com.czy.yq_wanandroid.business.WebViewActivity
import com.czy.yq_wanandroid.common.SideslipLayout
import com.czy.yq_wanandroid.room.AppDatabase
import com.czy.yq_wanandroid.room.entity.ReadHistory
import com.yangqing.record.ext.threadSwitch
import io.reactivex.rxjava3.core.Observable
import kotlin.concurrent.thread

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
            val context = holder.itemView.context
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("title", item.title)
            intent.putExtra("url", item.link)
            context.startActivity(intent)
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