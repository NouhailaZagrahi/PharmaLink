package com.example.pharmacyapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pharmacyapp.databinding.FragmentAdminDashboardBinding
import com.example.pharmacyapp.databinding.FragmentAdminOrdersBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AdminDashboard : Fragment() {
    private var param1: String? = null
    private var param2: String? = null


    private var _binding: FragmentAdminDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAdminDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ordersCard.setOnClickListener{
            findNavController().navigate(R.id.action_adminDashboard_to_adminOrders)
        }

        binding.medicinesCard.setOnClickListener{
            findNavController().navigate(R.id.action_adminDashboard_to_adminCategories)
        }

        binding.articlesCard.setOnClickListener{
            findNavController().navigate(R.id.action_adminDashboard_to_adminArticles)
        }

        binding.usersCard.setOnClickListener{
            findNavController().navigate(R.id.action_adminDashboard_to_adminUsers)
        }
    }



    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdminDashboard().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}