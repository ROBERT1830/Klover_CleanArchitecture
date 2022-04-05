package com.robertconstantindinescu.domain.model.model_component


import com.robertconstantindinescu.domain.util.Constant.Companion.ARM_LEFT
import com.robertconstantindinescu.domain.util.Constant.Companion.ARM_RIGHT
import com.robertconstantindinescu.domain.util.Constant.Companion.BACK
import com.robertconstantindinescu.domain.util.Constant.Companion.CHEST
import com.robertconstantindinescu.domain.util.Constant.Companion.FACE

import com.robertconstantindinescu.domain.util.Constant.Companion.LEG_LEFT
import com.robertconstantindinescu.domain.util.Constant.Companion.LEG_RIGHT

sealed class BodyLocation(val location: String){
    object ArmLeft: BodyLocation(ARM_LEFT)
    object ArmRight: BodyLocation(ARM_RIGHT)
    object LegLeft: BodyLocation(LEG_LEFT)
    object LegRight: BodyLocation(LEG_RIGHT)
    object Chest: BodyLocation(CHEST)
    object Face: BodyLocation(FACE)
    object Back: BodyLocation(BACK)

    companion object {

        fun fromString(location: String): BodyLocation{
            return when(location){
                ARM_LEFT -> ArmLeft
                ARM_RIGHT -> ArmRight
                LEG_LEFT -> LegLeft
                LEG_RIGHT -> LegRight
                CHEST -> Chest
                FACE -> Face
                BACK -> Back
                else -> Face
            }
        }
    }
}
