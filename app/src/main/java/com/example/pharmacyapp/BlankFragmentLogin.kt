package com.example.pharmacyapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.GraphRequest
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var callbackManager: CallbackManager


class BlankFragmentLogin : Fragment() {
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
        if (!FacebookSdk.isInitialized()) {
            FacebookSdk.sdkInitialize(requireContext())
            AppEventsLogger.activateApp(requireActivity().application)
        }

        // Initialisation du CallbackManager
        callbackManager = CallbackManager.Factory.create()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflater le layout du fragment
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
        }

        return inflater.inflate(R.layout.fragment_blank_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //code facebook
        /*val loginButton: LoginButton = view.findViewById(R.id.facebookButton)
        loginButton.setReadPermissions("email", "public_profile")

        // Enregistrement du CallbackManager pour le bouton de connexion
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                val accessToken = loginResult.accessToken
                fetchUserProfile(accessToken)
            }

            override fun onCancel() {
                Toast.makeText(context, "Connexion Facebook annulée", Toast.LENGTH_SHORT).show()
            }

            override fun onError(exception: FacebookException) {
                Toast.makeText(context, "Erreur Facebook : ${exception.message}", Toast.LENGTH_LONG).show()
            }
        })*/




        // Référencer les vues après l'inflation
        val emailEditText: EditText = view.findViewById(R.id.emailEditText)
        val passwordEditText: EditText = view.findViewById(R.id.passwordEditText)
        val signInButton: Button = view.findViewById(R.id.signInButton)
        val signUpTextView: TextView = view.findViewById(R.id.signUpTextView)

        // Action de connexion
        signInButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //it was commented
            if (dbHelper.checkUser(email, password)) {

                    val role = dbHelper.getUserRole(email, password)

                    if (role == "admin") {
                        Toast.makeText(requireContext(), "Login Successful as Admin", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_blankFragmentLogin_to_adminDashboard)
                    } else {
                        Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT).show()
                        var id_user = dbHelper.getUserIdByCredentials(email, password)
                        val action = BlankFragmentLoginDirections.actionBlankFragmentLoginToReceptionFragment2(id_user)
                        findNavController().navigate(action)
                    }


            }
            else {
                Toast.makeText(requireContext(), "Invalid Email or Password", Toast.LENGTH_SHORT).show()
            }
        }

        // Redirection vers l'inscription
        signUpTextView.setOnClickListener {
                findNavController().navigate(R.id.action_blankFragmentLogin_to_signupFragment)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    // Méthode pour récupérer les données utilisateur après connexion
    /*private fun fetchUserProfile(accessToken: AccessToken) {
        val request = GraphRequest.newMeRequest(accessToken) { obj, _ ->
            if (obj != null) {
                val userEmail = obj.optString("email")
                val userName = obj.optString("name")

                if (userEmail.isNotEmpty()) {
                    // Ajout de l'utilisateur à la base si ce n'est pas déjà fait
                    if (!dbHelper.checkUser(userEmail, "")) {
                        dbHelper.registerUser(userEmail, "", userName, "Facebook User", 0, "Unknown")
                        Toast.makeText(context, "Compte Facebook lié avec succès!", Toast.LENGTH_SHORT).show()
                    }

                    // Redirection après succès
                    val userId = dbHelper.getUserId(userEmail) ?: run {
                        Toast.makeText(context, "Impossible de récupérer l'ID utilisateur.", Toast.LENGTH_SHORT).show()
                        return@newMeRequest
                    }
                    val action = BlankFragmentLoginDirections.actionBlankFragmentLoginToReceptionFragment2(userId)
                    findNavController().navigate(action)
                } else {
                    Toast.makeText(context, "Impossible de récupérer l'e-mail Facebook.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Erreur lors de la récupération des données Facebook.", Toast.LENGTH_SHORT).show()
            }
        }

        val parameters = Bundle()
        parameters.putString("fields", "id,name,email")
        request.parameters = parameters
        request.executeAsync()
    }*/
    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BlankFragmentLogin().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}