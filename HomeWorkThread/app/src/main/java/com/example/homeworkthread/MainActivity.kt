package com.example.homeworkthread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homeworkthread.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isRunningPlus = true
    private var plus = 0
    private var threadPlus: Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
    }
}
