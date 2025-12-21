package com.alltools.toolbox.utility.calculator

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.alltools.toolbox.utility.calculator.CommonToolsActivity.CommonToolsActivty
import com.alltools.toolbox.utility.calculator.EssentialTools.Compass.CompassActivity
import com.alltools.toolbox.utility.calculator.EssentialTools.EssentialToolsActivity
import com.alltools.toolbox.utility.calculator.EssentialTools.FlashLight.FlashlightActivity
import com.alltools.toolbox.utility.calculator.EssentialTools.QRCode.QRCodeActivity
import com.alltools.toolbox.utility.calculator.EssentialTools.SpeedMeter.SpeedMeterActivity
import com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.MathAndFinanceActivity
import com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.SimpleCalculator.SimpleCalculatorActivity
import com.alltools.toolbox.utility.calculator.TimeAndDateActivity.TimeAndDateActivity
import com.alltools.toolbox.utility.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)

        val animation = AnimationUtils.loadAnimation(this, R.anim.press_animation)

        val textView = binding!!.seeAllTextView
        textView.text = Html.fromHtml("<u>See All</u>")

        textView.setOnClickListener { v ->
            v.startAnimation(animation)
            startActivity(Intent(applicationContext, EssentialToolsActivity::class.java))
        }
        binding!!.compassIC.setOnClickListener { v ->
            v.startAnimation(animation)
            startActivity(Intent(applicationContext, CompassActivity::class.java))
        }
        binding!!.flashLightIC.setOnClickListener { v ->
            v.startAnimation(animation)
            startActivity(Intent(applicationContext, FlashlightActivity::class.java))
        }
        binding!!.qrScannerIC.setOnClickListener { v ->
            v.startAnimation(animation)
            startActivity(Intent(applicationContext, QRCodeActivity::class.java))
        }
        binding!!.calculatorIC.setOnClickListener { v ->
            v.startAnimation(animation)
            startActivity(Intent(applicationContext, SimpleCalculatorActivity::class.java))
        }
        binding!!.speedMeterIC.setOnClickListener { v ->
            v.startAnimation(animation)
            startActivity(Intent(applicationContext, SpeedMeterActivity::class.java))
        }
        binding!!.easyNoteIC.setOnClickListener { v -> v.startAnimation(animation) }


        binding!!.commonToolsIC.setOnClickListener { v ->
            v.startAnimation(animation)
            startActivity(Intent(applicationContext, CommonToolsActivty::class.java))
        }
        binding!!.timeDataIC.setOnClickListener { v ->
            v.startAnimation(animation)
            startActivity(Intent(applicationContext, TimeAndDateActivity::class.java))
        }
        binding!!.mathFinanceIC.setOnClickListener { v ->
            v.startAnimation(animation)
            startActivity(Intent(applicationContext, MathAndFinanceActivity::class.java))
        }
    }
}