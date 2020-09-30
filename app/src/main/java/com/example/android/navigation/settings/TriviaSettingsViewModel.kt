package com.example.android.navigation.settings

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.android.navigation.R
import com.example.android.navigation.database.TriviaSettings
import com.example.android.navigation.database.TriviaSettingsDatabaseDao
import kotlinx.coroutines.*

class TriviaSettingsViewModel(
        val database: TriviaSettingsDatabaseDao,
        application: Application) : ViewModel() {

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _triviaSettings = MutableLiveData<TriviaSettings?>()
    val triviaSettings: LiveData<TriviaSettings?>
        get() = _triviaSettings

    val displayCorrectQuestionsToWin = Transformations.map(triviaSettings) {
        application.applicationContext.getString(
                R.string.correct_questions_val, it?.numQuestionsToWin
        )
    }

    init {
        initializeTriviaSettings()
    }

    private fun initializeTriviaSettings() {
        uiScope.launch {
            _triviaSettings.value = getTriviaSettingsFromDatabase()
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

    fun onUpdateTriviaSettings(correctQuestionsToWin: Int) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                _triviaSettings.value?.numQuestionsToWin = correctQuestionsToWin

                database.update(_triviaSettings.value!!)
            }
        }
    }
}