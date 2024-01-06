package com.example.employeemanagement2.until

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.employeemanagement2.R
import com.example.employeemanagement2.databinding.DialogAddStaffBinding
import com.example.employeemanagement2.model.ListSpiner
import com.example.employeemanagement2.model.StaffData

object AndroidUtil {
    fun showMenuEditStaff(
        context: Context,
        onResultStaffListener: OnResultStaffListener
    ) {
        val dialogView = LayoutInflater.from(context)
            .inflate(R.layout.dialog_add_staff, null)
        val binding = DialogAddStaffBinding.bind(dialogView)
        with(binding) {
            val listSpinner = ListSpiner()
            val departmentAdapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                listSpinner.itemsDepartment
            )
            departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spDepartmentStaff.adapter = departmentAdapter
            val statusAdapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                listSpinner.itemsStatus
            )
            statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spStatusStaff.adapter = statusAdapter
            AlertDialog.Builder(context).setView(dialogView)
                .setPositiveButton("Ok") { dialog, _ ->
                    onResultStaffListener.onResultStaff(
                        StaffData(
                            etIdStaff.text.toString(),
                            etNameStaff.text.toString(),
                            spDepartmentStaff.selectedItem.toString(),
                            spStatusStaff.selectedItem.toString(),
                            false
                        )
                    )
                    Toast.makeText(
                        context, "Thông tin nhân viên đã thay đổi", Toast.LENGTH_SHORT
                    ).show()
                    dialog.dismiss()
                }.setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }.create().show()
        }
    }

    fun showMenuAddStaff(
        context: Context,
        onResultStaffListener: OnResultStaffListener
    ) {
        val dialogView = LayoutInflater.from(context)
            .inflate(R.layout.dialog_add_staff, null)
        val dialogBinding = DialogAddStaffBinding.bind(dialogView)
        with(dialogBinding) {
            val listSpinner = ListSpiner()
            val departmentAdapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                listSpinner.itemsDepartment
            )
            departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spDepartmentStaff.adapter = departmentAdapter

            val statusAdapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                listSpinner.itemsStatus
            )
            statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spStatusStaff.adapter = statusAdapter

            AlertDialog.Builder(context).setView(dialogView)
                .setPositiveButton("Ok") { dialog, _ ->
                    onResultStaffListener.onResultStaff(
                        StaffData(
                            etIdStaff.text.toString(),
                            etNameStaff.text.toString(),
                            spDepartmentStaff.selectedItem.toString(),
                            spStatusStaff.selectedItem.toString(),
                            false
                        )
                    )
                    Toast.makeText(
                        context, "Đã thêm nhân viên", Toast.LENGTH_SHORT
                    ).show()
                    dialog.dismiss()
                }.setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }.create().show()
        }
    }
}

interface OnResultStaffListener {
    fun onResultStaff(staffData: StaffData)
}