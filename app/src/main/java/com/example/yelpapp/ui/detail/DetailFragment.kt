package com.example.yelpapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.yelpapp.R
import com.example.yelpapp.databinding.FragmentDetailBinding
import com.example.yelpapp.ui.common.launchAndCollect
import com.example.yelpapp.ui.common.loadUrl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewModel by viewModels<DetailViewModel>()
    private lateinit var binding: FragmentDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailBinding.bind(view)
        binding.setUi()

        launchAndCollect(viewModel.state) { binding.updateUI(it) }
    }

    private fun FragmentDetailBinding.setUi() {
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        fav.setOnClickListener {
            viewModel.onFavoriteClicked()
        }
    }

    private fun FragmentDetailBinding.updateUI(state: DetailViewModel.UiState) {
        state.business?.let {
            fav.setImageResource(if (it.favorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border)
            backdrop.loadUrl(it.image_url)
            toolbar.title = it.name
            summary.text = buildSpannedString {

                bold { append("City: ") }
                appendLine(it.city)

                bold { append("Address: ") }
                appendLine(it.address)

                bold { append("Phone: ") }
                appendLine(it.phone)

                bold { append("Price: ") }
                appendLine(it.price)
            }
        }
    }
}