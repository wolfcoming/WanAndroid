package com.czy.yq_wanandroid.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.czy.yq_wanandroid.entity.Banner
import com.example.lib_imageloader.image.ImageLoaderManager
import com.youth.banner.adapter.BannerAdapter

class HomeBannerAdapter(datas: List<Banner>) :
    BannerAdapter<Banner, HomeBannerAdapter.BannerViewHolder>(datas) {

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
        val imageView = ImageView(parent?.getContext())
        imageView.setLayoutParams(
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP)
        return BannerViewHolder(imageView)
    }

    override fun onBindView(holder: BannerViewHolder, data: Banner, position: Int, size: Int) {
        ImageLoaderManager.getInstance().displayImageForView(holder.imageView,this.mDatas[position].imagePath)
    }

    class BannerViewHolder(@param:NonNull var imageView: ImageView) :
        RecyclerView.ViewHolder(imageView)

}