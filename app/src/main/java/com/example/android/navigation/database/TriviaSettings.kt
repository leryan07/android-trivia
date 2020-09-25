package com.example.android.navigation.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trivia_settings_table")
data class TriviaSettings(
        @PrimaryKey(autoGenerate = true)
        var settingsId: Long = 0L,

        @ColumnInfo(name = "correct_questions_to_win")
        var numQuestionsToWin: Int = 3
)