package com.alltools.toolbox.utility.calculator.CommonToolsActivity.ImageCompressor

import java.lang.Double
import java.text.DecimalFormat
import kotlin.Long
import kotlin.String
import kotlin.arrayOf
import kotlin.math.log10
import kotlin.math.pow

object Constants {
    @JvmStatic
    fun getFileSize(j: Long): String {
        if (j <= 0) {
            return "0"
        }
        val d = j.toDouble()
        val log10 = (log10(d) / log10(1024.0)).toInt()
        val sb = StringBuilder()
        val decimalFormat = DecimalFormat("#,##0.#")
        val pow = 1024.0.pow(log10.toDouble())
        Double.isNaN(d)
        Double.isNaN(d)
        Double.isNaN(d)
        Double.isNaN(d)
        sb.append(decimalFormat.format(d / pow))
        sb.append(" ")
        sb.append(arrayOf<String>("B", "KB", "MB", "GB", "TB")[log10])
        return sb.toString()
    }
}
