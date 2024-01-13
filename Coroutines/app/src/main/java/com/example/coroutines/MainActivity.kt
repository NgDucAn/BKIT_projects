package com.example.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.coroutines.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isRunningPlus = true
    private var isRunningMinus = true
    private var isRunningMultiply = true
    private var isRunningRandom = true
    private val coroutineScope = lifecycleScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // phep cong
        binding.btStopAddition.setOnClickListener() {
            isRunningPlus = !isRunningPlus
            binding.btStopAddition.text = if (isRunningPlus) "Dừng cộng" else "Chạy lại cộng"
        }
        startPlusGeneration()

        // phep tru
        binding.btStopMinus.setOnClickListener() {
            isRunningMinus = !isRunningMinus
            binding.btStopMinus.text = if (isRunningMinus) "Dừng trừ" else "Chạy lại trừ"
        }
        startMinusGeneration()

        // phep nhan
        binding.btStopKernel.setOnClickListener() {
            isRunningMultiply = !isRunningMultiply
            binding.btStopKernel.text = if (isRunningMultiply) "Dừng nhân" else "Chạy lại nhân"
        }
        startMultiplyGeneration()

        // random
        binding.btStopRandom.setOnClickListener() {
            isRunningRandom = !isRunningRandom
            binding.btStopRandom.text = if (isRunningRandom) "Dừng Random" else "Chạy lại Random"
        }
        startRandomGeneration()
    }


    private fun startPlusGeneration() {
        coroutineScope.launch {
            var plus = 0
            while (isActive) {
                if (isRunningPlus) {
                    plus++
                    binding.tvValuePlus.text = "Giá trị hiện tại là: $plus"
                }
                delay(1000)
            }
        }
    }

    private fun startMinusGeneration() {
        coroutineScope.launch {
            var minus = 0
            while (isActive) {
                if (isRunningMinus) {
                    --minus
                    binding.tvValueMinus.text = "Giá trị hiện tại là: $minus"
                }
                delay(1000)
            }
        }
    }

    private fun startMultiplyGeneration() {
        coroutineScope.launch {
            var multiply = 1
            while (isActive) {
                if (isRunningMultiply) {
                    multiply *= 2
                    binding.tvValueMultiply.text = "Giá trị hiện tại là: $multiply"
                }
                delay(5000)
            }
        }
    }

    private fun startRandomGeneration() {
        coroutineScope.launch {
            val random = Random
            while (isActive) { //kiem tra trang thai cua coroutine
                if (isRunningRandom) {
                    val randomValue = random.nextInt()
                    binding.tvValueRandom.text = "Giá trị hiện tại là: $randomValue"
                }
                delay(3000)
            }
        }
    }

}
