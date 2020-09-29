package com.example.android.navigation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.navigation.R
import com.example.android.navigation.database.TriviaSettingsDatabase
import com.example.android.navigation.databinding.FragmentSettingsBinding

class TriviaSettingsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentSettingsBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_settings, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = TriviaSettingsDatabase.getInstance(application).triviaSettingsDatabaseDao

        val viewModelFactory = TriviaSettingsViewModelFactory(dataSource, application)

        val triviaSettingsViewModel = ViewModelProvider(this, viewModelFactory)
                .get(TriviaSettingsViewModel::class.java)

        binding.triviaSettingsViewModel = triviaSettingsViewModel
        binding.lifecycleOwner = this

        binding.root.setOnClickListener {
            val view: EditText = binding.correctQuestionsEditTextNumber

            triviaSettingsViewModel.clearCorrectQuestionsEditTextFocus(view)
        }
        binding.updateSettingButton.setOnClickListener {
            val correctQuestionsToWin: Int? = binding.correctQuestionsEditTextNumber.text.toString().toIntOrNull()
            val view: EditText = binding.correctQuestionsEditTextNumber

            triviaSettingsViewModel.clearCorrectQuestionsEditTextFocus(view)

            if (correctQuestionsToWin != null) {
                triviaSettingsViewModel.onUpdateTriviaSettings(correctQuestionsToWin)

                Toast.makeText(binding.root.context, getString(R.string.settings_updated), Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}