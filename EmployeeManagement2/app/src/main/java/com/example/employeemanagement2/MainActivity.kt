package com.example.employeemanagement2

import android.annotation.SuppressLint
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
import com.example.employeemanagement2.databinding.AddStaffBinding
import com.example.employeemanagement2.model.StaffData
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

        binding.imgAddStaff.setOnClickListener { addInforStaff() }

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
        val addDialogBinding = AddStaffBinding.inflate(LayoutInflater.from(this))
//        val v = LayoutInflater.from(this).inflate(R.layout.add_staff, null)
//        val id = v.findViewById<EditText>(R.id.edt_id_staff)
//        val name = v.findViewById<EditText>(R.id.edt_name_staff)
//        val department = v.findViewById<EditText>(R.id.edt_department_staff)
//        val status = v.findViewById<EditText>(R.id.edt_status_staff)
        val id = addDialogBinding.edtIdStaff
        val name = addDialogBinding.edtNameStaff
        val department = addDialogBinding.edtDepartmentStaff
        val status = addDialogBinding.edtStatusStaff
        val addDialog = AlertDialog.Builder(this)
        addDialog.setView(addDialogBinding.root)
        addDialog.setPositiveButton("Ok") { dialog, _ ->
            val id = id.text.toString()
            val name = name.text.toString()
            val department = department.text.toString()
            val status = status.text.toString()
            staffList.add(StaffData(id, name, department, status))
            staffAdapter.notifyDataSetChanged()
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
        }
        addDialog.create()
        addDialog.show()
    }
}