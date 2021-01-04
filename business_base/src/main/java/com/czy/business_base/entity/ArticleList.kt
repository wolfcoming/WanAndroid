package com.czy.business_base.entity

import com.czy.lib_base.net.entity.BaseEntity

data class ArticleList<T>(
    val curPage: Int,
    val datas: List<T>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
):BaseEntity()
