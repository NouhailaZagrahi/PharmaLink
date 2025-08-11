package com.example.pharmacyapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SignupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignupFragment : Fragment() {
    private lateinit var dbHelper: DatabaseHelper
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Référencer les vues après l'inflation
        val fname: EditText = view.findViewById(R.id.prenom)
        val lname: EditText = view.findViewById(R.id.nom)
        val email: EditText = view.findViewById(R.id.email)
        val password: EditText = view.findViewById(R.id.password)
        val phone: EditText = view.findViewById(R.id.tel)
        val location: EditText = view.findViewById(R.id.location)
        val signUpButton: Button = view.findViewById(R.id.signUpButton)

        // Action de connexion
        signUpButton.setOnClickListener {
            val fname1 = fname.text.toString()
            val lname1 = lname.text.toString()
            val email1 = email.text.toString()
            val password1 = password.text.toString()
            val phone1 = phone.text.toString().trim()
            val location1 = location.text.toString()

            if (email1.isEmpty() || password1.isEmpty() || fname1.isEmpty() || lname1.isEmpty() || location1.isEmpty() || phone1.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            if (!dbHelper.isValidEmail(email1)) {
                Toast.makeText(requireContext(), "Invalid email format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validation pour le numéro de téléphone
            if (!phone1.all { it.isDigit() }) {
                Toast.makeText(requireContext(), "Invalid phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Option 1 : Si vous utilisez Long pour le numéro
            val phoneNumber: Int
            try {
                phoneNumber = phone1.toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(requireContext(), "Phone number is too large", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            // Enregistrement de l'utilisateur
            val new_id = dbHelper.registerUser(email1, password1, fname1, lname1, phoneNumber, location1)

            if (new_id != -1L) {
                Toast.makeText(requireContext(), "Sign up Successful", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_signupFragment_to_blankFragmentLogin)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Email already exists or error occurred",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }



    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignupFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}