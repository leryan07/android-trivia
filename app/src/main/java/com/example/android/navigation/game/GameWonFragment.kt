/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentGameWonBinding
import com.google.android.material.snackbar.Snackbar


class GameWonFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_won, container, false)

        binding.nextMatchButton.setOnClickListener { view: View ->
            view.findNavController().navigate(
                    GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
        }

        val snackbar = Snackbar.make(binding.gameWonCoordinatorLayout,
                "Good job! Try increasing the number of questions", Snackbar.LENGTH_LONG)

        snackbar.setAction(R.string.settings, SettingsListener())
        snackbar.show()

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun getShareIntent(): Intent {
        val args = GameWonFragmentArgs.fromBundle(requireArguments())
        return ShareCompat.IntentBuilder.from(activity!!)
                .setText(getString(R.string.share_success_text, args.numCorrect, args.numQuestions))
                .setType("text/plain")
                .intent
    }

    private fun shareSuccess() {
        startActivity(getShareIntent())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.winner_menu, menu)
        // check if the activity resolves
        if (null == getShareIntent().resolveActivity(requireActivity().packageManager)) {
            // hide the menu item if it doesn't resolve
            menu.findItem(R.id.share)?.isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }
}

class SettingsListener : View.OnClickListener {
    override fun onClick(v: View?) {
        v?.findNavController()?.navigate(R.id.settingsActivity)
    }
}
