package com.example.social.presentation.home

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.social.R
import com.example.social.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private var statusAdapter: StatusAdapter = StatusAdapter()
    private var quickTipsAdapter: QuickTipsAdapter = QuickTipsAdapter()
    private var connectionAdapter: ConnectionAdapter = ConnectionAdapter()
    private var offerAdapter: OfferAdapter = OfferAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenCreated {

            binding.statusRecyclerView.layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.statusRecyclerView.adapter = statusAdapter


            binding.quickTipRecyclerView.layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.quickTipRecyclerView.adapter = quickTipsAdapter


            val connectionText = SpannableString("Whats happening in \n your circle ? ")
            connectionText.setSpan(ForegroundColorSpan(resources.getColor(R.color.blue_100)),
                0,
                5,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.connectionTextView.textView.text = connectionText


            binding.connectionRecyclerView.layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.connectionRecyclerView.adapter = connectionAdapter


            val offerText = SpannableString("New Offers")
            offerText.setSpan(ForegroundColorSpan(resources.getColor(R.color.blue_100)),
                0,
                3,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.offerText.textView.text = offerText

            binding.offerRecyclerView.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            binding.offerRecyclerView.adapter = offerAdapter

            viewModel.getStatus(requireContext())
        }
        observeFromViewModal()
    }


    private fun observeFromViewModal() {
        viewModel.status.observe(requireActivity()) {
            statusAdapter.submitList(it)
        }

        viewModel.quickTips.observe(requireActivity()) {
            quickTipsAdapter.submitList(it)
        }

        viewModel.connection.observe(requireActivity()) {
            connectionAdapter.submitList(it)
        }
        viewModel.offer.observe(requireActivity()) {
            offerAdapter.submitList(it)
        }
    }
}