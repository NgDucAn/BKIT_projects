package com.example.employeemanagement2.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.employeemanagement2.R
import com.example.employeemanagement2.databinding.ActivityMainBinding
import com.example.employeemanagement2.databinding.ItemStaffBinding
import com.example.employeemanagement2.model.StaffData
import com.example.employeemanagement2.until.AndroidUtil
import com.example.employeemanagement2.until.OnResultStaffListener
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var staffList: ArrayList<StaffData>
    private lateinit var staffAdapter: StaffAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        staffList = ArrayList()
        staffList.add(StaffData("1", "Nguyen Duc An", "Dev", "Thực tập", false))
        staffList.add(StaffData("2", "Trinh Van Manh", "Dev", "Chính thức", false))
        staffList.add(StaffData("3", "Pham Van Hieu", "Dev", "Thực tập", false))
        staffList.add(StaffData("4", "Nguyen Thi Hang", "Dev", "Thực tập", false))
        staffList.add(StaffData("5", "Doan Van Tien", "Dev", "Thực tập", false))
        staffList.add(StaffData("6", "Nguyen Tuan Anh", "Design", "Chính thức", false))
        staffList.add(StaffData("7", "Nguyen The Duc", "Dev", "Chính thức", false))
        staffList.add(StaffData("8", "Pham Thai Duong", "Dev", "Chính thức", false))
        staffList.add(StaffData("9", "Nguyen Duc An", "Dev", "Thực tập", false))
        staffAdapter = StaffAdapter(this, staffList)
        binding.rvListStaff.layoutManager = LinearLayoutManager(this)
        binding.rvListStaff.adapter = staffAdapter
        binding.fbAddStaff.setOnClickListener { addInfoStaff() }
        binding.svSearchStaff.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })

        binding.fbDeleteItems.setOnClickListener {
            staffAdapter.deleteItem()
        }

        binding.fbDeleteItems.setOnLongClickListener {
            staffAdapter.selectAllItems()
            true
        }
    }

    private fun filterList(query: String?) {
        if (query != null) {
            val filteredList = ArrayList<StaffData>()
            for (i in staffList) {
                if (i.nameStaff.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()) {
                Toast.makeText(this, "Không tìm thấy nhân viên", Toast.LENGTH_SHORT).show()
            }
            staffAdapter.setFilteredList(filteredList)
        }
    }

    @SuppressLint("InflateParams")
    private fun addInfoStaff() {
        AndroidUtil.showMenuAddStaff(this, object : OnResultStaffListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResultStaff(staffData: StaffData) {
                staffList.add(staffData)
                staffAdapter.notifyDataSetChanged()
            }
        })
    }
}