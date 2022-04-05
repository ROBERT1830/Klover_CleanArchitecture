package com.robertconstantindinescu.presentation.records_screen.adapter

import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.robertconstantindinescu.core.util.DiffUtil
import com.robertconstantindinescu.domain.model.MelanomaRecord
import com.robertconstantindinescu.core_ui.R
import com.robertconstantindinescu.presentation.databinding.FragmentMelanomaRecordsRowBinding
import com.robertconstantindinescu.presentation.records_screen.RecordsScreenEvent
import com.robertconstantindinescu.presentation.records_screen.RecordsScreenViewModel
import kotlinx.android.synthetic.main.fragment_melanoma_records_row.view.*

class RecordsScreenAdapter(
    private val requireActivity: FragmentActivity,
    private val viewModel: RecordsScreenViewModel
) : RecyclerView.Adapter<RecordsScreenAdapter.MyViewHolder>(), ActionMode.Callback {

    private var melanomaRecordsList = emptyList<MelanomaRecord>()
    private lateinit var mActionMode: ActionMode
    private lateinit var rootView: View

    /**State*/
    private var state = viewModel.state.value

    /**Variables*/
    private var multiSelection = false
    private var selectedMelanomaRecordList = state.selectedMelanomaRecordsList

    //private var selectedMelanomaRecordList = arrayListOf<MelanomaRecord>()

    //when select
    private var myViewHolders = arrayListOf<MyViewHolder>()


    fun setData(melanomaRecordsList: List<MelanomaRecord>) {
        val melanomaRecordsDiffUtil = DiffUtil(this.melanomaRecordsList, melanomaRecordsList)
        val diffUtilResult =
            androidx.recyclerview.widget.DiffUtil.calculateDiff(melanomaRecordsDiffUtil)
        this.melanomaRecordsList = melanomaRecordsList
        diffUtilResult.dispatchUpdatesTo(this)
    }

    class MyViewHolder(private val binding: FragmentMelanomaRecordsRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(melanomaRecord: MelanomaRecord) {
            binding.melanomaRecord = melanomaRecord
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    FragmentMelanomaRecordsRowBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    /******Adapter part*******/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        myViewHolders.add(holder)
        rootView = holder.itemView.rootView

        val currentRecord = this.melanomaRecordsList[position]
        holder.bind(currentRecord)

        holder.itemView.melanomaRecords_row_layout.setOnLongClickListener {

            if (!multiSelection) {
                viewModel.onEvent(
                    RecordsScreenEvent.onMelanomaRecordLongClickListener(
                        requireActivity = requireActivity,
                        holder = holder,
                        currentRecord = currentRecord,
                        selectedCancerRecordList = selectedMelanomaRecordList
                    )
                )
                multiSelection = state.multiSelection
                if (state.startActionMode) {
                    requireActivity.startActionMode(this)
                }
                applySelection(holder, currentRecord)
                true
            } else {
                multiSelection = state.multiSelection
                false
            }
        }

        holder.itemView.melanomaRecords_row_layout.setOnClickListener {
            if (multiSelection){
                applySelection(holder, currentRecord)
            }
        }


    }

    override fun getItemCount(): Int {
        return this.melanomaRecordsList.size
    }

    private fun applySelection(
        holder: MyViewHolder,
        currentRecord: MelanomaRecord
    ) {

        if (selectedMelanomaRecordList.contains(currentRecord)) {
            changeRecordStyle(
                holder,
                R.color.defaultCardBackgroundColor,
                R.color.strokeColor
            )
            applyActionModeTitle()
        } else {
            changeRecordStyle(
                holder,
                R.color.cardBackgroundLightColor,
                R.color.primaryColor
            )
        }
    }

    private fun changeRecordStyle(
        holder: MyViewHolder,
        defaultCardBackgroundColor: Int,
        strokeColor: Int
    ) {

        holder.itemView.melanomaRecords_row_layout.setBackgroundColor(
            ContextCompat.getColor(requireActivity, defaultCardBackgroundColor)
        )
        holder.itemView.melanomaRecords_row_cardView.strokeColor =
            ContextCompat.getColor(requireActivity, strokeColor)

    }

    private fun applyActionModeTitle() {
        when (selectedMelanomaRecordList.size) {
            0 -> {
                mActionMode.finish()
            }
            1 -> {
                mActionMode.title =
                    "${selectedMelanomaRecordList.size}" + requireActivity.getString(
                        com.robertconstantindinescu.presentation.R.string.itemSelected
                    )
            }
            else -> {
                mActionMode.title =
                    "${selectedMelanomaRecordList.size} " + requireActivity.getString(
                        com.robertconstantindinescu.presentation.R.string.itemsSelected
                    )
            }
        }


    }

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(
            com.robertconstantindinescu.presentation.R.menu.records_screen_contextual_menu,
            menu
        )
        mActionMode =
            actionMode!! //set the global variable mActionMode to the actionMode of here. so from this function we get the actionmode and store taht in a global variable. Becasue we wil need taht to set the title of contextual action mode
        applyStatusBarColor(R.color.primaryDarkColor)
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menu: MenuItem?): Boolean {
        if (menu?.itemId == com.robertconstantindinescu.presentation.R.id.delete_cancer_record_menu) {
            selectedMelanomaRecordList.forEach { melanomaRecord ->
                viewModel.onEvent(RecordsScreenEvent.onDeleteMelanomaRecord(melanomaRecord))
            }
            //the snackbar is shown in the fragment
            selectedMelanomaRecordList.clear()
            actionMode?.finish()

        }
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        myViewHolders.forEach { holder ->
            changeRecordStyle(holder, R.color.defaultCardBackgroundColor, R.color.strokeColor)
        }
        multiSelection = false
        selectedMelanomaRecordList.clear()
        applyStatusBarColor(R.color.primaryDarkColor)
    }


    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor =
            ContextCompat.getColor(requireActivity, color)

    }
}