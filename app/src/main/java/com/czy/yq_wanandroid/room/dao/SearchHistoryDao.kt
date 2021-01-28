package com.czy.yq_wanandroid.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.czy.yq_wanandroid.room.entity.SearchHistory

@Dao
interface SearchHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearch(name: SearchHistory)

    @Query("select * from search_history order by time DESC")
    fun getHistorySearch(): List<SearchHistory>

    @Query("""delete from search_history where name NOT IN 
        (select name from search_history order by time DESC limit 0,:maxCount)""")
    fun autoDelete(maxCount: Int)

    @Query("delete from search_history")
    fun delete()
}