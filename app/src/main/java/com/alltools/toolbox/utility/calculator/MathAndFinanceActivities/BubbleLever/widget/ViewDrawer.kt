package com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.BubbleLever.widget

import android.graphics.Canvas

interface ViewDrawer<T> {
    fun layout(width: Int, height: Int)
    fun draw(canvas: Canvas?)
    fun update(value: T)
}