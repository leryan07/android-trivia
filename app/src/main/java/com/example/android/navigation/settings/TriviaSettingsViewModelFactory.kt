package com.example.android.navigation.settings

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.navigation.database.TriviaSettingsDatabaseDao
import java.lang.IllegalArgumentException

class TriviaSettingsViewModelFactory(
        private val dataSource: TriviaSettingsDatabaseDao,
        private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TriviaSettingsViewModel::class.java)) {
            return TriviaSettingsViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unkown ViewModel class")
    }
}