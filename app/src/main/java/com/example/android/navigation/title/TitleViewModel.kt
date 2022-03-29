package com.example.android.navigation.title

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.navigation.database.TriviaSettings
import com.example.android.navigation.database.TriviaSettingsDatabaseDao
import kotlinx.coroutines.*

class TitleViewModel (
        val database: TriviaSettingsDatabaseDao,
        application: Application) : ViewModel() {

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var triviaSettings = MutableLiveData<TriviaSettings?>()

    private val _navigateToGameFragment = MutableLiveData<Boolean>()
    val navigateToGameFragment: LiveData<Boolean>
        get() = _navigateToGameFragment

    init {
        initializeTriviaSettings()
    }

    private fun initializeTriviaSettings() {
        uiScope.launch {
            triviaSettings.value = getTriviaSettingsFromDatabase()
        }
    }

    private suspend fun getTriviaSettingsFromDatabase(): TriviaSettings? {
        return withContext(Dispatchers.IO) {
            var currentSettings = database.getTriviaSettings()

            if (currentSettings == null) {
                val defaultSettings = TriviaSettings()

                database.insert(defaultSettings)

                currentSettings = database.getTriviaSettings()
            }

            currentSettings
        }
    }

    fun onPlayButtonClicked() {
        uiScope.launch {
            _navigateToGameFragment.value = true
        }
    }

    fun doneNavigating() {
        _navigateToGameFragment.value = null
    }
}