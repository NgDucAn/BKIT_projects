package com.example.handler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.handler.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val plusHandler = Handler(Looper.getMainLooper())
    private val minusHandler = Handler(Looper.getMainLooper())
    private val multiplyHandler = Handler(Looper.getMainLooper())
    private val randomHandler = Handler(Looper.getMainLooper())
    private var isRunningPlus = true
    private var plus = 0
    private var isRunningMinus = true
    private var minus = 0
    private var isRunningMultiply = true
    private var multiply = 1
    private var isRunningRandom = true
    private var randomValue = 0
    val random = Random

    private val myPlusRunnable = object : Runnable {
        override fun run() {
            if (isRunningPlus) {
                plus++
                binding.tvValuePlus.text = "Giá trị hiện tại là: $plus"
                plusHandler.postDelayed(this, 1000)
            }
        }
    }

    private val myMinusRunnable = object : Runnable {
        override fun run() {
            if (isRunningPlus) {
                --minus
                binding.tvValueMinus.text = "Giá trị hiện tại là: $minus"
                minusHandler.postDelayed(this, 1000)
            }
        }
    }

    private val myMultiplyRunnable = object : Runnable {
        override fun run() {
            if (isRunningMultiply) {
                multiply *= 2
                binding.tvValueMultiply.text = "Giá trị hiện tại là: $multiply"
                multiplyHandler.postDelayed(this, 5000)
            }
        }
    }

    private val myRandomRunnable = object : Runnable {
        override fun run() {
            if (isRunningRandom) {
                randomValue = random.nextInt()
                binding.tvValueRandom.text = "Giá trị hiện tại là: $randomValue"
                randomHandler.postDelayed(this, 3000)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        plusHandler.postDelayed(myPlusRunnable, 1000)
        minusHandler.postDelayed(myMinusRunnable, 1000)
        multiplyHandler.postDelayed(myMultiplyRunnable, 5000)
        randomHandler.postDelayed(myRandomRunnable, 3000)

        binding.btStopAddition.setOnClickListener {
            isRunningPlus = !isRunningPlus
            binding.btStopAddition.text = if (isRunningPlus) "Dừng cộng" else "Chạy lại cộng"
            if (isRunningPlus) {
                plusHandler.postDelayed(myPlusRunnable, 1000)
            } else {
                plusHandler.removeCallbacks(myPlusRunnable)
            }
        }

        binding.btStopMinus.setOnClickListener() {
            isRunningMinus = !isRunningMinus
            binding.btStopMinus.text = if (isRunningMinus) "Dừng trừ" else "Chạy lại trừ"
            if (isRunningMinus) {
                minusHandler.postDelayed(myMinusRunnable, 1000)
            } else {
                minusHandler.removeCallbacks(myMinusRunnable)
            }
        }

        binding.btStopKernel.setOnClickListener() {
            isRunningMultiply = !isRunningMultiply
            binding.btStopKernel.text = if (isRunningMultiply) "Dừng nhân" else "Chạy lại nhân"
            if (isRunningMultiply) {
                multiplyHandler.postDelayed(myMultiplyRunnable, 5000)
            } else {
                multiplyHandler.removeCallbacks(myMultiplyRunnable)
            }
        }

        binding.btStopRandom.setOnClickListener() {
            isRunningRandom = !isRunningRandom
            binding.btStopRandom.text = if (isRunningRandom) "Dừng random" else "Chạy lại random"
            if (isRunningRandom) {
                randomHandler.postDelayed(myRandomRunnable, 3000)
            } else {
                randomHandler.removeCallbacks(myRandomRunnable)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        plusHandler.removeCallbacksAndMessages(null)
        minusHandler.removeCallbacksAndMessages(null)
        multiplyHandler.removeCallbacksAndMessages(null)
        randomHandler.removeCallbacksAndMessages(null)
    }
}
