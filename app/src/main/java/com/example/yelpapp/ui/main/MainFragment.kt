package com.example.yelpapp.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.yelpapp.R
import com.example.yelpapp.databinding.FragmentMainBinding
import com.example.yelpapp.data.repository.BusinessRepository
import com.example.yelpapp.ui.common.app
import com.example.yelpapp.ui.common.launchAndCollect
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels()

    private val adapter = BusinessesAdapter { mainState.onBusinessClicked(it) }

    private lateinit var mainState: MainState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainState = buildMainState()

        val binding = FragmentMainBinding.bind(view).apply {
            recycler.adapter = adapter
        }

        viewLifecycleOwner.launchAndCollect(viewModel.state) {binding.updateUI(it)}

        mainState.requestLocationPermission { viewModel.onUiReady() }

    }

    private fun FragmentMainBinding.updateUI(state: MainViewModel.UiState) {
        progress.isVisible = state.loading
        state.businesses?.let(adapter::submitList)
    }


}