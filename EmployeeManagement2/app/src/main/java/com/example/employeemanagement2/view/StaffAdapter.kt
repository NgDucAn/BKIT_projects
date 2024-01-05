package com.example.employeemanagement2.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
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
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStaffBinding.inflate(inflater, parent, false)
        return StaffViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
        val newList = staffList[position]
        holder.binding.tvNameStaff.text = newList.nameStaff
        holder.binding.tvStatus.text = newList.status

        holder.itemView.setOnClickListener() {
            val intentInforStaff = Intent(context, StaffDetailInfor::class.java)
            intentInforStaff.putExtra("id_staff", newList.id)
            intentInforStaff.putExtra("name_staff", newList.nameStaff)
            intentInforStaff.putExtra("department_staff", newList.department)
            intentInforStaff.putExtra("status_staff", newList.status)

            context.startActivity(intentInforStaff)
        }
    }

    override fun getItemCount(): Int {
        return staffList.size
    }

    inner class StaffViewHolder(val binding: ItemStaffBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.ivMenu.setOnClickListener { popupMenus(it) }
        }

        @SuppressLint("ResourceType", "MissingInflatedId", "CutPasteId", "NotifyDataSetChanged")
        private fun popupMenus(view: View?) {
            val popupMenus = PopupMenu(itemView.context, view)
            val staffData = staffList[position]
            popupMenus.inflate(R.menu.show_menu)
            popupMenus.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.tv_edit_infor_staff-> {
                        showMenuEditStaff(context, object : OnResultStaffListener{
                            override fun onResultStaff(staffData: StaffData) {
                                staffList.removeAt(position)
                                staffList.add(staffData)
                                notifyDataSetChanged()
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
                                notifyDataSetChanged()
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

    fun setFilteredList(staffList: ArrayList<StaffData>) {
        this.staffList = staffList
        notifyDataSetChanged()
    }
}
