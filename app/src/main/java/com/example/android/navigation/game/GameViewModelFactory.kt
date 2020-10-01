package com.example.android.navigation.game

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.navigation.database.TriviaSettingsDatabaseDao
import com.example.android.navigation.title.TitleViewModel
import java.lang.IllegalArgumentException

class GameViewModelFactory(
        private val dataSource: TriviaSettingsDatabaseDao,
        private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unkown ViewModel class")
    }
}