package com.example.employeemanagement2

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.LayerDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.employeemanagement2.databinding.ActivityMainBinding
import com.example.employeemanagement2.databinding.DialogAddStaffBinding
import com.example.employeemanagement2.model.StaffData
import com.example.employeemanagement2.until.AndroidUtil
import com.example.employeemanagement2.until.OnResultStaffListener
import com.example.employeemanagement2.view.StaffAdapter
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

        staffAdapter = StaffAdapter(this, staffList)

        binding.rvListStaff.layoutManager = LinearLayoutManager(this)
        binding.rvListStaff.adapter = staffAdapter

        binding.fbAddStaff.setOnClickListener { addInforStaff() }

        binding.svSearchStaff.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })

        binding.fbDeleteItems.setOnClickListener() {
            staffAdapter.deleteItem()
        }
    }

    private fun filterList(query: String?) {
        if(query != null) {
            val filteredList = ArrayList<StaffData>()
            for(i in staffList) {
                if(i.nameStaff.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }
            if(filteredList.isEmpty()) {
                Toast.makeText(this, "Không tìm thấy nhân viên", Toast.LENGTH_SHORT).show()
            } else {
                staffAdapter.setFilteredList(filteredList)
            }
        }
    }

    @SuppressLint("InflateParams")
    private fun addInforStaff() {
        AndroidUtil.showMenuAddStaff(this, object : OnResultStaffListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResultStaff(staffData: StaffData) {
                staffList.add(staffData)
                staffAdapter.notifyDataSetChanged()
            }
        })
//        val addDialogBinding = DialogAddStaffBinding.inflate(LayoutInflater.from(this))
//        val addDialog = AlertDialog.Builder(this@MainActivity)
//        with(addDialogBinding) {
//            addDialog.setView(addDialogBinding.root)
//            addDialog.setPositiveButton("Ok") { dialog, _ ->
//                val id = addDialogBinding.etIdStaff.text.toString()
//                val name = addDialogBinding.etNameStaff.text.toString()
//                val department = addDialogBinding.spDepartmentStaff.dropDownVerticalOffset.toString()
//                val status = addDialogBinding.spStatusStaff.dropDownVerticalOffset.toString()
//                staffList.add(StaffData(id, name, department, status))
//                staffAdapter.notifyDataSetChanged()
//                Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
//                dialog.dismiss()
//            }
//            addDialog.setNegativeButton("Cancel") { dialog, _ ->
//                dialog.dismiss()
//                Toast.makeText(this@MainActivity, "Cancel", Toast.LENGTH_SHORT).show()
//            }
//            addDialog.create()
//            addDialog.show()
//        }
    }
}