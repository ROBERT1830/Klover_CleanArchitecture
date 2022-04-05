package com.robertconstantindinescu.presentation.util

import com.robertconstantindinescu.domain.util.Constant.Companion.ARM_LEFT
import com.robertconstantindinescu.domain.util.Constant.Companion.ARM_RIGHT
import com.robertconstantindinescu.domain.util.Constant.Companion.BACK
import com.robertconstantindinescu.domain.util.Constant.Companion.CHEST
import com.robertconstantindinescu.domain.util.Constant.Companion.FACE
import com.robertconstantindinescu.domain.util.Constant.Companion.LEG_LEFT
import com.robertconstantindinescu.domain.util.Constant.Companion.LEG_RIGHT

class BodyParts {

    companion object{
        val bodyParts =
            arrayOf(ARM_LEFT, ARM_RIGHT, LEG_LEFT, LEG_RIGHT, CHEST ,FACE, BACK)
    }

}