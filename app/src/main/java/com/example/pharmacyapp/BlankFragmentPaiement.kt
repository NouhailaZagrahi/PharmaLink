package com.example.pharmacyapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BlankFragmentPaiement : Fragment(R.layout.fragment_blank_paiement) {

    private lateinit var dbHelper: DatabaseHelper
    val args : CartFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = DatabaseHelper(requireContext())
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // Références aux vues
        val btnFinaliser: Button = view.findViewById(R.id.btnFinaliser)
        val msgStatut: TextView = view.findViewById(R.id.msgStatut)
        val progressBar: ProgressBar = view.findViewById(R.id.loading)
        val radioButtonPaiementCash: RadioButton = view.findViewById(R.id.radioButtonPaiementCash)

        // Désactiver la sélection du bouton radio (par défaut déjà sélectionné)
        radioButtonPaiementCash.isClickable = false

        btnFinaliser.setOnClickListener {
            // Make the ProgressBar and message visible
            progressBar.visibility = View.VISIBLE
            msgStatut.visibility = View.VISIBLE
            msgStatut.text = "Order status: Under validation..."

            lifecycleScope.launch {
                // Simulate a delay for validation (can be replaced with an actual network request)
                delay(2000)

                val commandeId = 1
                val status = getCommandeStatus(commandeId) // Simulate fetching the status

                // Hide the ProgressBar
                progressBar.visibility = View.GONE

                when (status) {
                    "ongoing" -> {
                        msgStatut.text = "Order status: Still under validation..."
                    }
                    "valide" -> {
                        // Navigation to BlankFragmentConfirmation
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, BlankFragmentConfirmation())
                            .addToBackStack(null)
                            .commit()
                    }
                    "refuse" -> {
                        msgStatut.text = "Order status: Rejected, product out of stock."
                    }
                    else -> {
                        msgStatut.text = "Error: Unknown status."
                    }
                }
            }
        }
    }

    /**
     * Simuler la récupération du statut de commande.
     * @param commandeId Identifiant de la commande.
     * @return Le statut simulé.
     */
    private fun getCommandeStatus(commandeId: Int): String {

        return when (commandeId % 3) {
            0 -> "valide"
            1 -> "ongoing"
            else -> "refuse"
        }
    }
}