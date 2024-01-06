package com.example.employeemanagement2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.employeemanagement2.databinding.ActivityStaffDetailInforBinding
import com.example.employeemanagement2.model.StaffData

class StaffDetailInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStaffDetailInforBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStaffDetailInforBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val staffInfoData = intent.getSerializableExtra("staffInfo") as StaffData
        binding.tvIdStaff.text = staffInfoData.id
        binding.tvFullName.text = staffInfoData.nameStaff
        binding.tvDepartmentStaff.text = staffInfoData.department
        binding.tvStatusStaff.text = staffInfoData.status
        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}