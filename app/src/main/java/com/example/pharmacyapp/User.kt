package com.example.pharmacyapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(var id : Int , var email : String, var pasword : String, var first_name : String, var last_name : String, var location : String , var phone : Int) : Parcelable
