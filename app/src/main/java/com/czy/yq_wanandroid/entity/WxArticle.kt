package com.czy.yq_wanandroid.entity

//"courseId":13,"id":408,"name":"鸿洋","order":190000,"parentChapterId":407,"userControlSetTop":false,"visible":1
/**
 * 微信公众号数据类
 */
data class WxArticle(
    var courseId: Int,
    var id: Int,
    var name: String,
    var order: Long,
    var parentChapterId: Int,
    var userControlSetTop: Boolean,
    var visible: Int



) {
    override fun toString(): String {
        return "WxArticle(courseId=$courseId, id=$id, name='$name', order=$order, parentChapterId=$parentChapterId, userControlSetTop=$userControlSetTop, visible=$visible)"
    }
}