package com.czy.yq_wanandroid.adapter

import android.content.Context
import android.content.DialogInterface
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.czy.business_base.ArouterConfig
import com.czy.business_base.api.WanApiService
import com.czy.business_base.entity.ArticleEntity
import com.czy.lib_base.utils.SettingUtils
import com.czy.yq_wanandroid.R
import com.yangqing.record.ext.commonSubscribe
import com.yangqing.record.ext.threadSwitch

class HomeArticleListAdapter : RecyclerView.Adapter<HomeArticleListAdapter.ArticleListViewHolder> {
    private var datas: ArrayList<ArticleEntity>

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

        holder.tvTitle.text = Html.fromHtml(this.datas[position].title)
        holder.ll_new.visibility = if (item.fresh) View.VISIBLE else View.GONE
        holder.ll_top.visibility = if (item.top) View.VISIBLE else View.GONE
        holder.tv_author.text = if (item.author.isEmpty()) {
            "无名~"
        } else item.author
        holder.tv_tag.visibility = View.GONE
        holder.tv_time.text = item.niceDate
        if (item.desc.isNullOrEmpty()) {
            holder.tv_desc.visibility = View.GONE
        } else {
            holder.tv_desc.visibility = View.VISIBLE
            holder.tv_desc.text = Html.fromHtml(item.desc).toString();
        }

        holder.tv_chapter_name.text = item.superChapterName + "·" + item.chapterName

        if (item.collect) (holder.ll_collect.getChildAt(0) as TextView).text = "已收藏" else
            (holder.ll_collect.getChildAt(0) as TextView).text = "收藏"

        holder.ll_collect.setOnClickListener {
            dealCollect(item, position, holder.itemView.context)
        }
        holder.itemView.setOnClickListener {
            ARouter.getInstance().build(ArouterConfig.webviewPath)
                .withString("title", item.title)
                .withString("url", item.link)
                .navigation()
        }

        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setItems(arrayOf("浏览器打开","复制URL"), object :DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        if(which == 0){
                            SettingUtils.openSystemBrower(holder.itemView.context,item.link)
                        }else if (which == 1){
                            SettingUtils.copyContentToClipboard(holder.itemView.context,item.link)
                        }
                    }

                }).show()
            return@setOnLongClickListener true
        }
    }


    /**
     * 收藏文章逻辑
     */
    private fun dealCollect(item: ArticleEntity, position: Int, context: Context) {
        if (!item.collect) {
            WanApiService.getWanApi().collectArticle(item.id)
                .threadSwitch()
                .commonSubscribe({
                    item.collect = true
                    notifyItemChanged(position)
                }, {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                })
        } else {
            WanApiService.getWanApi().unCollectArticle(item.id)
                .threadSwitch()
                .commonSubscribe({
                    item.collect = false
                    notifyItemChanged(position)
                }, {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                })
        }

    }

    override fun getItemCount(): Int {
        return this.datas.size
    }

    class ArticleListViewHolder : RecyclerView.ViewHolder {
        var tvTitle: TextView
        var tv_author: TextView
        var tv_tag: TextView
        var tv_time: TextView
        var tv_desc: TextView
        var tv_chapter_name: TextView
        var ll_new: LinearLayout
        var ll_top: LinearLayout
        var ll_collect: LinearLayout

        constructor(itemView: View) : super(itemView) {
            tvTitle = itemView.findViewById(R.id.tv_title)
            tv_author = itemView.findViewById<TextView>(R.id.tv_author)
            tv_tag = itemView.findViewById<TextView>(R.id.tv_tag)
            tv_time = itemView.findViewById<TextView>(R.id.tv_time)
            tv_desc = itemView.findViewById<TextView>(R.id.tv_desc)
            tv_chapter_name = itemView.findViewById<TextView>(R.id.tv_chapter_name)
            ll_new = itemView.findViewById(R.id.ll_new)
            ll_top = itemView.findViewById(R.id.ll_top)
            ll_collect = itemView.findViewById(R.id.ll_collect)
        }
    }
}