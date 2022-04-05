package com.robertconstantindinescu.presentation.records_screen.binding


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat

import androidx.databinding.BindingAdapter

import com.robertconstantindinescu.core_ui.R
import com.robertconstantindinescu.domain.model.model_component.BodyLocation

import java.util.*


class MelanomaRecordRowBinding {

    companion object{
        @BindingAdapter("loadImage")
        @JvmStatic
        fun loadImage(imageView: ImageView, bitmap: Bitmap){

            imageView.setImageBitmap(bitmap)
//            val imageBytes = Base64.decode(uri, 0)
//            val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
//            imageView.setImageBitmap(image)
//            var bitmap: Bitmap? = null
////            while (imageView == null){
////                bitmap = BitmapFactory.decodeFile(uri)
////            }
//            if (imageView==null){
//                bitmap = BitmapFactory.decodeFile(uri)
//            }
//            imageView.setImageBitmap(bitmap)
//
////            var bitmap: Bitmap? = null
////            while(bitmap == null)
////                imageView.setImageBitmap(BitmapFactory.decodeFile(bitmap));
////            //val fname: String = File(uri).absolutePath
//           // imageView.setImageBitmap(BitmapFactory.decodeFile(uri))
//////            imageView.load(bitmap){
//////                scale(Scale.FILL)
//////            }
//            val uri:Uri =  File(uri).toUri()
//
////            val myUri = Uri.fromFile( File(uri))
//            imageView.setImageBitmap(BitmapFactory.decodeFile(uri.toString()))
////            val bitmap =
////                BitmapFactory.decodeStream(imageView.context.contentResolver.openInputStream(myUri))
////            imageView.setImageBitmap(bitmap)
////            imageView.load(bitmap){
////                scale(Scale.FILL)
////            }

        }
        @BindingAdapter("applyMalignBenignColor")
        @JvmStatic
        fun applyMalignBenignColor(view: View, text: String ){
            if (text.contains("malignant", true)){
                when(view){
                    is TextView -> {
                        view.setTextColor(
                            ContextCompat.getColor(
                                view.context,
                                R.color.malignantColor
                            )
                        )
                    }
                }
            }else{
                when(view){
                    is TextView -> {
                        view.setTextColor(
                            ContextCompat.getColor(
                                view.context,
                                R.color.benignColor
                            )
                        )
                    }
                }
            }
        }
        @BindingAdapter("setProbabilitytText")
        @JvmStatic
        fun setProbability(textView: TextView, probability: String){
            textView.text = probability

        }

        @BindingAdapter("setBodyLocation")
        @JvmStatic
        fun setBodyLocation(textView: TextView, bodyLocation: BodyLocation){
            textView.text = bodyLocation.location
        }

        @BindingAdapter("setDateText")
        @JvmStatic
        fun setDate(textView: TextView, date: String){
            textView.text = date.toString()
        }

    }
}