package com.example.employeemanagement2.model

import java.io.Serializable

data class StaffData(
    var id : String,
    var nameStaff: String,
    var department: String,
    var status: String,
    var selected: Boolean
) : Serializable
