package com.robertconstantindinescu.domain.use_case

import java.text.SimpleDateFormat

class GenerateDate {

    operator fun invoke(): String{
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val currentDate = sdf.format(java.util.Date())

        return currentDate
    }
}