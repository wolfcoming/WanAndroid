package com.czy.yq_wanandroid.entity

import com.czy.lib_base.net.entity.BaseEntity

//"desc":"享学~","id":29,"imagePath":"https://wanandroid.com/blogimgs/affe33fb-a160-4c63-bcc5-2ba83965a7bc.png",
// "isVisible":1,"order":0,"title":"现在的Android程序员，要具备什么能力才能保持竞争","type":0,
// "url":"https://www.bilibili.com/video/BV1gp4y1k7mw"
data class Banner(
    var desc: String,
    var id: Int,
    var imagePath: String,
    var isVisible: Int,
    var order: Int,
    var title: String,
    var type: Int,
    var url: String
) : BaseEntity() {
}