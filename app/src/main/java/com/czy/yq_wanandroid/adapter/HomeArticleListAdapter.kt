package com.czy.yq_wanandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.czy.yq_wanandroid.R
import com.czy.yq_wanandroid.entity.ArticleEntity

class HomeArticleListAdapter : RecyclerView.Adapter<HomeArticleListAdapter.ArticleListViewHolder> {
    private lateinit var datas: ArrayList<ArticleEntity>

    constructor(data: ArrayList<ArticleEntity>) {
        this.datas = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item_article, parent, false)
        return ArticleListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleListViewHolder, position: Int) {
        val item = datas[position]
        holder.tvTitle.setText(this.datas[position].title)
        holder.ll_new.visibility = if (item.fresh) View.VISIBLE else View.GONE
        holder.ll_top.visibility = if (item.top) View.VISIBLE else View.GONE
        holder.tv_author.text = if (item.author.isEmpty()) {
            "无名~"
        } else item.author
        holder.tv_tag.visibility = View.GONE
        holder.tv_time.text = item.niceDate
        holder.tv_desc.text = item.desc
        holder.tv_chapter_name.text = item.superChapterName+"·"+item.chapterName
    }

    override fun getItemCount(): Int {
        return this.datas.size
    }

    class ArticleListViewHolder : RecyclerView.ViewHolder {
        lateinit var tvTitle: TextView
        lateinit var tv_author: TextView
        lateinit var tv_tag: TextView
        lateinit var tv_time: TextView
        lateinit var tv_desc: TextView
        lateinit var tv_chapter_name: TextView
        lateinit var ll_new: LinearLayout
        lateinit var ll_top: LinearLayout

        constructor(itemView: View) : super(itemView) {
            tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
            tv_author = itemView.findViewById<TextView>(R.id.tv_author)
            tv_tag = itemView.findViewById<TextView>(R.id.tv_tag)
            tv_time = itemView.findViewById<TextView>(R.id.tv_time)
            tv_desc = itemView.findViewById<TextView>(R.id.tv_desc)
            tv_chapter_name = itemView.findViewById<TextView>(R.id.tv_chapter_name)
            ll_new = itemView.findViewById(R.id.ll_new)
            ll_top = itemView.findViewById(R.id.ll_top)
        }

    }
}