package com.example.pharmacyapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmacyapp.databinding.FragmentAdminCategoriesBinding
import com.example.pharmacyapp.databinding.FragmentAdminMedicinesBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AdminMedicines : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var dbHelper: DatabaseHelper
    val args : AdminMedicinesArgs by navArgs()

    private var _binding: FragmentAdminMedicinesBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        dbHelper = DatabaseHelper(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAdminMedicinesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var idCat = args.idCat
        var medicines = dbHelper.getMedicamentsByCategory(idCat)

        binding.Titletxt.text = dbHelper.getCategorieTitleById(idCat)

        val myAdapter = adminmedicinesAdapter(medicines, object : adminmedicinesAdapter.OnChangeClickListener {
            override fun onChangeClick(medicament: Medicament) {

                val success = dbHelper.updateMedicamentPrice(medicament.id, medicament.price)
                if (success) {
                    Toast.makeText(requireContext(), "Prix mis à jour avec succès", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Erreur lors de la mise à jour", Toast.LENGTH_SHORT).show()
                }
            }
        })

        val rv = binding.medicinerecycleview
        rv.adapter=myAdapter
        rv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdminMedicines().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}