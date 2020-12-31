package com.czy.yq_wanandroid.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class SearchHistory(@PrimaryKey val name:String, val time:Long = 0)