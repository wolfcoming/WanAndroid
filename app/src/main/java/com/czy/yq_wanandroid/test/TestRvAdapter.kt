package com.czy.yq_wanandroid.test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.czy.yq_wanandroid.R
import com.example.lib_imageloader.image.ImageLoaderUtil
import com.wanglu.photoviewerlibrary.PhotoViewer
import java.util.*

class TestRvAdapter(
    testActivity: TestActivity,
    recyclerView: RecyclerView
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var myRecyclerView:RecyclerView
    lateinit var activity: TestActivity
    var url =
        "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3653738266,3251206500&fm=11&gp=0.jpg"

    var datas = ArrayList<String>()

    init {
        myRecyclerView = recyclerView
        this.activity = testActivity
        for (i in 0 .. 400){
            datas.add(url)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.test_img_item, null)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        ImageLoaderUtil.getInstance().loadImage(url, (holder as MyViewHolder).image)
        val myViewHolder:MyViewHolder = holder as MyViewHolder
        myViewHolder.image.setOnClickListener {
            PhotoViewer.setData(datas)
                .setCurrentPage(position)
                .setImgContainer(myRecyclerView)
                .setShowImageViewInterface(object : PhotoViewer.ShowImageViewInterface {
                    override fun show(iv: ImageView, url: String) {
                        ImageLoaderUtil.getInstance().loadImage(url, iv)
                    }
                }).start(activity)


//            PhotoViewer.setData(datas)
//                .setClickSingleImg(url,myViewHolder.image)
//                .setShowImageViewInterface(object : PhotoViewer.ShowImageViewInterface {
//                    override fun show(iv: ImageView, url: String) {
//                        ImageLoaderUtil.getInstance().loadImage(url, iv)
//                    }
//                }).start(activity)
        }
    }

    override fun getItemCount(): Int {
         return datas.size
    }

    class MyViewHolder :RecyclerView.ViewHolder{
        lateinit var image:ImageView
        constructor(view: View):super(view){
            image = view.findViewById(R.id.iv)
        }

    }
}