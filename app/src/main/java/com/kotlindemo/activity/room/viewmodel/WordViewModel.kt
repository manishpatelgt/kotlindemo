package com.kotlindemo.activity.room.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.kotlindemo.activity.room.database.WordRoomDatabase
import com.kotlindemo.activity.room.entity.Word
import com.kotlindemo.activity.room.repository.WordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Manish Patel on 5/7/2019.
 */

class WordViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WordRepository
    val allWords: LiveData<List<Word>>

    init {
        val wordDao = WordRoomDatabase.getDatabase(application, viewModelScope).wordDao()
        repository = WordRepository(wordDao)
        allWords = repository.allWords
    }

    fun insert(word: Word) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(word)
    }
}