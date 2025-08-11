package com.example.pharmacyapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class CartFragment : Fragment() {
    private lateinit var dbHelper: DatabaseHelper
    val args : CartFragmentArgs by navArgs()


    private lateinit var emptyCartText: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var summary: TextView
    private lateinit var facture: View
    private lateinit var confirm_btn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = DatabaseHelper(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id_user = args.idUser

        emptyCartText = view.findViewById(R.id.emptyCartText)
        recyclerView = view.findViewById(R.id.cartView)

        summary = view.findViewById(R.id.summarytxt)
        facture = view.findViewById(R.id.facturetxt)
        confirm_btn = view.findViewById(R.id.confirm_btn)

        var cartsitems = dbHelper.getCartItemsByUser(id_user)

        val sub_total = view.findViewById<TextView>(R.id.subtotal_txt)
        val total = view.findViewById<TextView>(R.id.total_txt)

        updateCartUI(cartsitems, sub_total, total)

        // Créer l'adaptateur et l'assigner à la RecyclerView
        val myAdapter = cartAdapter(cartsitems) { updatedCarts ->
            updateCartUI(updatedCarts, sub_total, total)
        }

        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)




        confirm_btn.setOnClickListener {
            val totalPriceText = total.text.toString().replace(" DH", "")
            val totalPrice = totalPriceText.toInt()

            val orderId = dbHelper.insertOrder(id_user, totalPrice)

            if (orderId != -1) { // Vérifier si la commande a été insérée avec succès
                dbHelper.fillCommandeDetails(orderId, id_user)
                dbHelper.deleteCartItemsByUser(id_user) // Supprimer les articles du panier


                //val action = CartFragmentDirections.actionCartFragmentToStatusFragment(orderId)
                val action = CartFragmentDirections.actionCartFragmentToBlankFragmentPaiement()
                findNavController().navigate(action)

                Toast.makeText(requireContext(), "Commande enregistrée avec succès. ID : $orderId", Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(requireContext(), "Erreur lors de l'insertion de la commande", Toast.LENGTH_LONG).show()
            }
        }

        val backBtn = view.findViewById<ImageView>(R.id.backBtn)

        backBtn.setOnClickListener {

            requireActivity().onBackPressed()
        }

    }

    // Fonction pour mettre à jour l'interface utilisateur
    fun updateCartUI(cartsitems: List<Cart>, sub_total: TextView, total: TextView) {
        if (cartsitems.isEmpty()) {
            emptyCartText.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            summary.visibility = View.GONE
            facture.visibility = View.GONE
            confirm_btn.visibility = View.GONE

        } else {
            emptyCartText.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            summary.visibility = View.VISIBLE
            facture.visibility = View.VISIBLE
            confirm_btn.visibility = View.VISIBLE


            updateTotals(cartsitems, sub_total, total)
        }
    }


    // Fonction de mise à jour des totaux
    fun updateTotals(cartsitems: List<Cart>, sub_total: TextView, total: TextView) {
        var subtotal = cartsitems.sumOf { it.priceUnite * it.quantity }
        sub_total.text = "$subtotal DH"

        var totalPrice = subtotal + 5 + 2
        total.text = "$totalPrice DH"
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

