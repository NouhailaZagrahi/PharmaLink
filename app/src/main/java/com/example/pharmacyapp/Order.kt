package com.example.pharmacyapp

data class Order(var idOrder : Int, var idUser : Int, var dateOrder : Long, var prix_total : Int, var status : String)
