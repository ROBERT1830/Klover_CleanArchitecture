package com.robertconstantindinescu.presentation.records_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.robertconstantindinescu.core.SingleUiEvent
import com.robertconstantindinescu.domain.use_case.global.MelanomaDetectionUseCases
import com.robertconstantindinescu.presentation.R
import com.robertconstantindinescu.presentation.databinding.FragmentMelanomaRecordsBinding
import com.robertconstantindinescu.presentation.detection_screen.DetectorActivity
import com.robertconstantindinescu.presentation.records_screen.adapter.RecordsScreenAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MelanomaRecordsFragment : Fragment() {
    //private lateinit var mBinding: FragmentMelanomaRecordsBinding
    private var _binding: FragmentMelanomaRecordsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecordsScreenViewModel by viewModels()
    private val mAdapter: RecordsScreenAdapter by lazy {
        RecordsScreenAdapter(
            requireActivity(),
            viewModel
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMelanomaRecordsBinding.inflate(inflater, container, false)


        lifecycleScope.launchWhenStarted {
            viewModel.singleUiEvent.collectLatest {
                when (it) {
                    is SingleUiEvent.ShowSnackBar -> {
                        Snackbar.make(
                            binding.root,
                            it.message.asString(requireContext()),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                    is SingleUiEvent.NavigateSuccess -> {
                        findNavController().navigate(MelanomaRecordsFragmentDirections.actionMelanomaRecordsToDetectorActivity())
                    }
                    else -> Unit
                }
            }
        }

        binding.lifecycleOwner = this
        binding.recordsScreenViewModel = viewModel
        binding.mAdapter = mAdapter

        setHasOptionsMenu(true)
        setupRecyclerView()



        binding.btnAddRecord.setOnClickListener {
            viewModel.onEvent(RecordsScreenEvent.onDetectorActivityNavigateClick)
        }

        return binding.root

    }

    private fun setupRecyclerView() {
        binding.recyclerViewCancerData.apply {
            adapter = mAdapter
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.delete_all_cancer_records) {
            viewModel.onEvent(RecordsScreenEvent.onDeleteAllMelanomaRecords)
        }


        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}