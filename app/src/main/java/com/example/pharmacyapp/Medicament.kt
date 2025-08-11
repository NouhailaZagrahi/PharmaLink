package com.example.pharmacyapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Medicament(var id: Int, var pic : Int , var title : String, var desc : String ,var mini_desc : String, var price : Int,  var categoryId: Int) : Parcelable
