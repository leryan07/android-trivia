package com.example.android.navigation.game

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.android.navigation.database.TriviaSettingsDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class GameViewModel(
        val database: TriviaSettingsDatabaseDao,
        application: Application) : ViewModel() {

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


}