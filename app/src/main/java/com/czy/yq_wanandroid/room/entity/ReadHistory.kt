package com.czy.yq_wanandroid.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 阅读历史表
 */
@Entity(tableName = "read_history")
data class ReadHistory(@PrimaryKey val link: String, val title: String, val time: Long)