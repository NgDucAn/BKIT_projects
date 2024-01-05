package com.example.employeemanagement2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.employeemanagement2.R
import com.example.employeemanagement2.databinding.ActivityStaffDetailInforBinding

class StaffDetailInfor : AppCompatActivity() {
    private lateinit var binding: ActivityStaffDetailInforBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStaffDetailInforBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvIdStaff.text = intent.getStringExtra("id_staff")
        binding.tvFullName.text = intent.getStringExtra("name_staff")
        binding.tvDepartmentStaff.text = intent.getStringExtra("department_staff")
        binding.tvStatusStaff.text = intent.getStringExtra("status_staff")

        binding.imgBack.setOnClickListener() {
            finish()
        }
    }
}