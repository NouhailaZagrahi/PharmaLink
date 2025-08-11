package com.example.pharmacyapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pharmacyapp.databinding.FragmentMedicamentsBinding
import com.example.pharmacyapp.databinding.FragmentStatusBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class StatusFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var dbHelper: DatabaseHelper
    val args : StatusFragmentArgs by navArgs()

    private var _binding: FragmentStatusBinding? = null
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
        _binding = FragmentStatusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var idOrder = args.idOrder
        var status = dbHelper.getOrderStatusById(idOrder)

        binding.etatDemande.text = status
        if(status == "validated"){
            binding.successMsg.visibility = View.VISIBLE
            binding.trackerBtn.visibility = View.VISIBLE

            binding.trackerBtn.setOnClickListener {
                findNavController().navigate(R.id.action_statusFragment_to_blankFragmentConfirmation)
            }
        }
        else if(status == "refused"){
            binding.refuseMsg.visibility = View.VISIBLE
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StatusFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}