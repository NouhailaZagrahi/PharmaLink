package com.example.pharmacyapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmacyapp.databinding.FragmentAdminCategoriesBinding
import com.example.pharmacyapp.databinding.FragmentReceptionBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AdminCategories : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var dbHelper: DatabaseHelper

    private var _binding: FragmentAdminCategoriesBinding? = null
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
        _binding = FragmentAdminCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var categories = dbHelper.getAllCategories()

        // Handle clicks using a lambda function
        val myAdapter = admincategoriesAdapter(categories, object : admincategoriesAdapter.OnItemClickListener {
            override fun onItemClick(categorie: Categorie) {

                val action = AdminCategoriesDirections.actionAdminCategoriesToAdminMedicines(categorie.id)
                findNavController().navigate(action)
            }
        })

        val rv = binding.categoriesRv
        rv.adapter = myAdapter
        rv.layoutManager = GridLayoutManager(requireContext(), 2)
    }




    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdminCategories().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}