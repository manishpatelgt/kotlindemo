package com.kotlindemo.activity.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kotlindemo.activity.room.entity.Word

/**
 * Created by Manish Patel on 5/7/2019.
 */

@Dao
interface WordDao {

    @Query("SELECT * from word_table ORDER by word ASC")
    fun getAllWords(): LiveData<List<Word>>

    @Insert
    suspend fun insert(word: Word)

    @Query("DELETE FROM word_table")
    fun deleteAll()
}