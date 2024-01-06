package com.example.employeemanagement2.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.employeemanagement2.R
import com.example.employeemanagement2.databinding.ItemStaffBinding
import com.example.employeemanagement2.model.StaffData
import com.example.employeemanagement2.until.AndroidUtil.showMenuEditStaff
import com.example.employeemanagement2.until.OnResultStaffListener

class StaffAdapter(
    val context: Context, var staffList: ArrayList<StaffData>
) : RecyclerView.Adapter<StaffAdapter.StaffViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder {
        val binding = ItemStaffBinding
            .inflate(LayoutInflater
                .from(parent.context), parent, false)
        return StaffViewHolder(binding)
    }

    @SuppressLint("RecyclerView")
    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
        val staff = staffList[position]
        holder.binding.tvNameStaff.text = staff.nameStaff
        holder.binding.tvDepartment.text = staff.department
        holder.itemView.setOnClickListener() {
            val intentInfoStaff = Intent(context, StaffDetailInfoActivity::class.java)
            intentInfoStaff.putExtra("staffInfo", staff)
            context.startActivity(intentInfoStaff)
        }

        holder.binding.cbSelect.setOnCheckedChangeListener(null)
        holder.binding.cbSelect.isChecked = staff.selected

        holder.binding.cbSelect.setOnCheckedChangeListener { _, isChecked ->
            staffList[position].selected = isChecked
        }

    }

    override fun getItemCount(): Int {
        return staffList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteItem() {
        val itemsToRemove = staffList.filter { it.selected }.toList()
        staffList.removeAll(itemsToRemove)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun selectAllItems() {
        staffList.forEach { it.selected = true }
        notifyDataSetChanged()
    }

    inner class StaffViewHolder(val binding: ItemStaffBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.ivMenu.setOnClickListener { popupMenus(it) }
        }

        @SuppressLint("ResourceType", "MissingInflatedId", "CutPasteId", "NotifyDataSetChanged")
        private fun popupMenus(view: View?) {
            val popupMenus = PopupMenu(itemView.context, view)
            popupMenus.inflate(R.menu.show_menu)
            popupMenus.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.tv_edit_infor_staff-> {
                        showMenuEditStaff(context, object : OnResultStaffListener{
                            override fun onResultStaff(staffData: StaffData) {
                                staffList[adapterPosition] = staffData
                                notifyItemChanged(adapterPosition)
                            }
                        })
                        true
                    }

                    R.id.tv_remove_staff -> {
                        AlertDialog.Builder(context).setTitle("Delete")
                            .setIcon(R.drawable.ic_warning)
                            .setMessage("Bạn có chắc chắn xóa thông tin nhân viên")
                            .setPositiveButton("Yes") { dialog, _ ->
                                staffList.removeAt(adapterPosition)
                                notifyItemRemoved(adapterPosition)
                                Toast.makeText(
                                    context, "Xóa thông tin nhân viên", Toast.LENGTH_SHORT
                                ).show()
                                dialog.dismiss()
                            }.setNegativeButton("No") { dialog, _ ->
                                dialog.dismiss()
                            }.create().show()
                        true
                    }
                    else -> true
                }
            }
            popupMenus.show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(staffList: ArrayList<StaffData>) {
        this.staffList = staffList
        notifyDataSetChanged()
    }
}
