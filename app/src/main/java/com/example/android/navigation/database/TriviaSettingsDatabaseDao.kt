package com.example.android.navigation.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TriviaSettingsDatabaseDao {

    @Insert
    fun insert(triviaSettings: TriviaSettings)

    @Update
    fun update(triviaSettings: TriviaSettings)

    @Query("SELECT * FROM trivia_settings_table ORDER BY settingsId DESC LIMIT 1")
    fun getTriviaSettings(): TriviaSettings?
}