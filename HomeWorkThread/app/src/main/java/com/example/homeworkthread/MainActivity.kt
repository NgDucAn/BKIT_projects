package com.example.homeworkthread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homeworkthread.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isRunningPlus = true
    private var plus = 0
    private var threadPlus: Thread? = null
    private var isRunningMinus = true
    private var minus = 0
    private var threadMinus: Thread? = null
    private var isRunningMultiply = true
    private var multiply = 1
    private var threadMultiply: Thread? = null
    private var isRunningRandom = true
    private var randomValue = 0
    private var threadRandom: Thread? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Phep cong
        threadPlus = Thread {
            while (isRunningPlus) {
                plus++
                runOnUiThread {
                    binding.tvValuePlus.text = "Giá trị hiện tại là: $plus"
                }
                Thread.sleep(1000)
            }
        }
        threadPlus?.start()
        binding.btStopAddition.setOnClickListener {
            if (isRunningPlus) {
                binding.btStopAddition.text = "Chạy lại cộng"
                isRunningPlus = false
            } else {
                binding.btStopAddition.text = "Dừng cộng"
                isRunningPlus = true
                if (threadPlus?.isAlive == false) { // isAlive kiem tra luong co dang chay hay khong
                    threadPlus = Thread {
                        while (isRunningPlus) {
                            plus++
                            runOnUiThread {
                                binding.tvValuePlus.text = "Giá trị hiện tại là: $plus"
                            }
                            Thread.sleep(1000)
                        }
                    }
                    threadPlus?.start()
                }
            }
        }

        // Phep tru
        threadMinus = Thread {
            while (isRunningMinus) {
                --minus
                runOnUiThread {
                    binding.tvValueMinus.text = "Giá trị hiện tại là: $minus"
                }
                Thread.sleep(1000)
            }
        }
        threadMinus?.start()
        binding.btStopMinus.setOnClickListener {
            if (isRunningMinus) {
                binding.btStopMinus.text = "Chạy lại trừ"
                isRunningMinus = false
            } else {
                binding.btStopMinus.text = "Dừng trừ"
                isRunningMinus = true
                if (threadMinus?.isAlive == false) { // isAlive kiem tra luong co dang chay hay khong
                    threadMinus = Thread {
                        while (isRunningMinus) {
                            --minus
                            runOnUiThread {
                                binding.tvValueMinus.text = "Giá trị hiện tại là: $minus"
                            }
                            Thread.sleep(1000)
                        }
                    }
                    threadMinus?.start()
                }
            }
        }

        // Pheo nhan
        threadMultiply = Thread {
            while (isRunningMultiply) {
                multiply *= 2
                runOnUiThread {
                    binding.tvValueMultiply.text = "Giá trị hiện tại là: $multiply"
                }
                Thread.sleep(5000)
            }
        }
        threadMultiply?.start()
        binding.btStopKernel.setOnClickListener {
            if (isRunningMultiply) {
                binding.btStopKernel.text = "Chạy lại nhân"
                isRunningMultiply = false
            } else {
                binding.btStopKernel.text = "Dừng nhân"
                isRunningMultiply = true
                if (threadMultiply?.isAlive == false) { // isAlive kiem tra luong co dang chay hay khong
                    threadMultiply = Thread {
                        while (isRunningMultiply) {
                            multiply *= 2
                            runOnUiThread {
                                binding.tvValueMultiply.text = "Giá trị hiện tại là: $multiply"
                            }
                            Thread.sleep(5000)
                        }
                    }
                    threadMultiply?.start()
                }
            }
        }

        // Random
        val random = Random
        threadRandom = Thread {
            while (isRunningRandom) {
                randomValue = random.nextInt()
                runOnUiThread {
                    binding.tvValueRandom.text = "Giá trị hiện tại là: $randomValue"
                }
                Thread.sleep(3000)
            }
        }
        threadRandom?.start()
        binding.btStopRandom.setOnClickListener {
            if (isRunningRandom) {
                binding.btStopRandom.text = "Chạy lại random"
                isRunningRandom = false
            } else {
                binding.btStopRandom.text = "Dừng random"
                isRunningRandom = true
                if (threadRandom?.isAlive == false) { // isAlive kiem tra luong co dang chay hay khong
                    threadRandom = Thread {
                        while (isRunningRandom) {
                            randomValue = random.nextInt()
                            runOnUiThread {
                                binding.tvValueRandom.text = "Giá trị hiện tại là: $randomValue"
                            }
                            Thread.sleep(5000)
                        }
                    }
                    threadRandom?.start()
                }
            }
        }
    }
}
