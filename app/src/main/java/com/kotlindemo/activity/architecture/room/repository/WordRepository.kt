package com.kotlindemo.activity.architecture.room.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.kotlindemo.activity.architecture.room.dao.WordDao
import com.kotlindemo.activity.architecture.room.entity.Word

/**
 * Created by Manish Patel on 5/7/2019.
 */

class WordRepository(private val wordDao: WordDao) {

    val allWords: LiveData<List<Word>> = wordDao.getAllWords()

    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}