package com.alltools.toolbox.utility.calculator.MathAndFinanceActivities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.AgeCalculator.AgeCalculatorActivity
import com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.BubbleLever.MainActivityBubble
import com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.ClothSize.ClothActivity
import com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.Countdown.CountDownStartActivity
import com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.FuelCost.FuelCostActivity
import com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.PercentageCalculator.PercentageCalculatorActivity
import com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.PeriodicTable.PeriodicTableActivity
import com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.Protector.ProtectorActivity
import com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.RandomNumbersAndPassword.RandomHomeActivity
import com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.Ruler.StraightRulerActivity
import com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.ScientificCalculator.ScientificCalculatorActivity
import com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.ShoeSize.ShoeSizeActivity
import com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.SimpleCalculator.SimpleCalculatorActivity
import com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.UnitConverter.AreaConverter.AreaConverterActivity
import com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.UnitConverter.UnitsActivityMain
import com.alltools.toolbox.utility.calculator.R
import com.alltools.toolbox.utility.calculator.databinding.ActivityMathAndFinanceBinding

class MathAndFinanceActivity : AppCompatActivity() {
    var binding: ActivityMathAndFinanceBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMathAndFinanceBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)


        val animation = AnimationUtils.loadAnimation(this, R.anim.press_animation)

        binding!!.backICC.setOnClickListener { v ->
            v.startAnimation(animation)
            onBackPressed()
        }

        binding!!.simpleCalculatorIc.setOnClickListener { v ->
            v.startAnimation(animation)
            startActivity(Intent(applicationContext, SimpleCalculatorActivity::class.java))
        }
        binding!!.scientificCalculatorIc.setOnClickListener { v ->
            v.startAnimation(animation)
            startActivity(Intent(applicationContext, ScientificCalculatorActivity::class.java))
        }
        binding!!.countDownIc.setOnClickListener { v ->
            v.startAnimation(animation)
            startActivity(Intent(applicationContext, CountDownStartActivity::class.java))
        }
        binding!!.ageCalculatorIc.setOnClickListener { v ->
            v.startAnimation(animation)
            startActivity(Intent(applicationContext, AgeCalculatorActivity::class.java))
        }
        binding!!.discountIc.setOnClickListener { v ->
            v.startAnimation(animation)
            startActivity(Intent(applicationContext, PercentageCalculatorActivity::class.java))
        }
        binding!!.unitConverterIc.setOnClickListener { v ->
            v.startAnimation(animation)
            startActivity(Intent(applicationContext, UnitsActivityMain::class.java))
        }

        binding!!.straighRulerIc.setOnClickListener { v ->
            v.startAnimation(animation)
            startActivity(Intent(applicationContext, StraightRulerActivity::class.java))
        }
        binding!!.fuelCostIc.setOnClickListener { v ->
            v.startAnimation(animation)
            startActivity(Intent(applicationContext, FuelCostActivity::class.java))
        }
        binding!!.bubbleLevelerIc.setOnClickListener { v ->
            v.startAnimation(animation)
            startActivity(Intent(applicationContext, MainActivityBubble::class.java))
        }
        binding!!.shoeSizeIc.setOnClickListener { v ->
            v.startAnimation(animation)
            startActivity(Intent(applicationContext, ShoeSizeActivity::class.java))
        }
        binding!!.protractor.setOnClickListener { v ->
            v.startAnimation(animation)
            startActivity(Intent(applicationContext, ProtectorActivity::class.java))
        }
        binding!!.clothSize.setOnClickListener { v ->
            v.startAnimation(animation)
            startActivity(Intent(applicationContext, ClothActivity::class.java))
        }
        binding!!.periodicTable.setOnClickListener { v ->
            v.startAnimation(animation)
            startActivity(Intent(applicationContext, PeriodicTableActivity::class.java))
        }

        findViewById<View>(R.id.randomNumberGenerator).setOnClickListener { v ->
            v.startAnimation(animation)
            startActivity(Intent(applicationContext, RandomHomeActivity::class.java))
        }
    }
}