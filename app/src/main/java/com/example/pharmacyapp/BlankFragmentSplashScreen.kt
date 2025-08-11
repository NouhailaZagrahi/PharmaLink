package com.example.pharmacyapp

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class BlankFragmentSplashScreen : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
            val view = inflater.inflate(R.layout.fragment_blank_splash_screen, container, false)

            // Récupérer les vues
            val welcomeMessage = view.findViewById<TextView>(R.id.welcome_message)
            val healthQuote = view.findViewById<TextView>(R.id.health_quote)
            val loadingDots = view.findViewById<TextView>(R.id.loading_dots)

            // Animation 1 : Apparition du message de bienvenue
            val fadeInWelcome = ObjectAnimator.ofFloat(welcomeMessage, "alpha", 0f, 1f).apply {
                duration = 1000
            }

            // Animation 2 : Apparition de la citation santé
            val fadeInQuote = ObjectAnimator.ofFloat(healthQuote, "alpha", 0f, 1f).apply {
                duration = 1000
                startDelay = 1000
            }

            // Lancer les animations
            welcomeMessage.visibility = View.VISIBLE
            fadeInWelcome.start()
            healthQuote.visibility = View.VISIBLE
            fadeInQuote.start()

            // Animation des points de loading
            val loadingTexts = arrayOf(".", "..", "...")
            var index = 0
            val handler = Handler(Looper.getMainLooper())
            val runnable = object : Runnable {
                override fun run() {
                    loadingDots.text = loadingTexts[index]
                    index = (index + 1) % loadingTexts.size // Boucle sur les 3 états
                    handler.postDelayed(this, 1500) // Répéter toutes les 1,5 secondes
                }
            }
            loadingDots.visibility = View.VISIBLE
            handler.post(runnable)

            // Navigation après 5 secondes
            Handler(Looper.getMainLooper()).postDelayed({
                handler.removeCallbacks(runnable) // Arrêter l'animation des points
                findNavController().navigate(R.id.action_blankFragmentSplashScreen_to_blankFragmentDefini)
            }, 5000)

            return view
        }


    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BlankFragmentSplashScreen().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}