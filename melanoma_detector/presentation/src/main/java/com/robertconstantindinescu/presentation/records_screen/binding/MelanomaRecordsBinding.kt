package com.robertconstantindinescu.presentation.records_screen.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.robertconstantindinescu.domain.model.MelanomaRecord
import com.robertconstantindinescu.presentation.records_screen.adapter.RecordsScreenAdapter

class MelanomaRecordsBinding {
    companion object{
        @BindingAdapter("viewVisibility", "setData", requireAll = false)
        @JvmStatic
        fun setDataAndViewVisibility(
            view: View,
            melanomaRecordsList: List<MelanomaRecord>?,
            mapAdapter: RecordsScreenAdapter?
        ){
            if (melanomaRecordsList.isNullOrEmpty()){
                when(view){
                    is ImageView -> {view.visibility = View.VISIBLE}
                    is TextView -> {view.visibility = View.VISIBLE}
                    is RecyclerView -> {view.visibility = View.INVISIBLE}
                }

            }else{
                when(view){
                    is ImageView -> {view.visibility = View.INVISIBLE}
                    is TextView -> {view.visibility = View.INVISIBLE}
                    is RecyclerView -> {
                        view.visibility = View.VISIBLE
                        mapAdapter?.setData(melanomaRecordsList)
                    }
                }
            }
        }
    }
}