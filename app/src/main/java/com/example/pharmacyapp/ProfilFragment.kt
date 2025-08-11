package com.example.pharmacyapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pharmacyapp.databinding.FragmentProfilBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProfilFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    val args : ProfilFragmentArgs by navArgs()

    private var _binding: FragmentProfilBinding? = null
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
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var user = args.userconnecte

        binding.fullname.text = "${user.first_name} ${user.last_name}"
        binding.textfull.text = "${user.first_name} ${user.last_name}"
        binding.emailtxt.text = user.email
        binding.passwordtxt.text = user.pasword
        binding.phonetxt.text = user.phone.toString()
        binding.locationtxt.text = user.location

        binding.contactCard.setOnClickListener{
            findNavController().navigate(R.id.action_profilFragment_to_pharmacyInfo)
        }

        binding.logoutBtn.setOnClickListener {
            findNavController().navigate(R.id.action_profilFragment_to_blankFragmentLogin)
        }
    }


    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfilFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}