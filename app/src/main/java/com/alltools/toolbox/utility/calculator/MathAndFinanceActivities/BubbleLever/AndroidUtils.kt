package com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.BubbleLever

import android.content.res.Resources

object AndroidUtils {
    fun dpToPx(dp: Int): Float {
        return dpToPx(dp.toFloat())
    }
    private fun dpToPx(dp: Float): Float {
        return dp * Resources.getSystem().displayMetrics.density
    }
}