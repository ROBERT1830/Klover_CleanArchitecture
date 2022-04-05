package com.robertconstantindinescu.domain.model.model_component

import com.robertconstantindinescu.domain.util.Constant.Companion.BENIGN
import com.robertconstantindinescu.domain.util.Constant.Companion.MALIGNANT


sealed class MoleType(val type: String){
    object Malignant: MoleType(MALIGNANT)
    object Benign: MoleType(BENIGN)

    companion object {

        fun fromString(type: String): MoleType{
           return when(type){
                MALIGNANT -> Malignant
               BENIGN -> Benign
               else -> Benign
            }
        }
    }

}
