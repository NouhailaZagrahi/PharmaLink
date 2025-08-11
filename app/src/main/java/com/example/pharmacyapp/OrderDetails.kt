package com.example.pharmacyapp

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmacyapp.databinding.FragmentMedicamentsBinding
import com.example.pharmacyapp.databinding.FragmentOrderDetailsBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class OrderDetails : Fragment() {
    private lateinit var dbHelper: DatabaseHelper
    val args : OrderDetailsArgs by navArgs()

    private var _binding: FragmentOrderDetailsBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idOrder = args.idOrder
        val order = dbHelper.getOrderById(idOrder)
        val user = dbHelper.getUserById(order.idUser)

        // Initialization of fields like price, location, etc.
        binding.titreOrder.text = "Order n°$idOrder Details"
        binding.clientname.text = "${user.first_name} ${user.last_name}"
        binding.localisation.text = "${user.location}"
        binding.date.text = convertirTimestampEnDate(order.dateOrder)
        binding.locafinale.text = "${user.location}"
        binding.prixfinale.text = "${order.prix_total}"

        // Initialization of RecyclerView
        val ordersitems = dbHelper.getOrderDetails(idOrder)
        val myAdapter = orderitemsAdapter(ordersitems)

        val rv = binding.orderitemsRv
        rv.adapter = myAdapter
        rv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        // Invoice section
        val invoiceSection = binding.factureSection
        val invoiceDetailsGroup = binding.factureDetailsGroup
        var isExpanded = false

        invoiceSection.setOnClickListener {
            // Initialize an automatic transition
            val transition = AutoTransition()
            transition.duration = 300
            TransitionManager.beginDelayedTransition(invoiceSection, transition)

            // Toggle the state (collapsed or expanded)
            isExpanded = !isExpanded
            invoiceDetailsGroup.visibility = if (isExpanded) View.VISIBLE else View.GONE

            // Adjust height if necessary (not mandatory here)
            val layoutParams = invoiceSection.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.height = if (isExpanded) ConstraintLayout.LayoutParams.WRAP_CONTENT else ConstraintLayout.LayoutParams.WRAP_CONTENT
            invoiceSection.layoutParams = layoutParams
        }

        binding.validateBtn.setOnClickListener {
            val isValidated = dbHelper.validateOrder(idOrder, "validated")
            if (isValidated) {
                Toast.makeText(requireContext(), "Order successfully validated", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Validation failed", Toast.LENGTH_SHORT).show()
            }
        }

        binding.refuseBtn.setOnClickListener {
            val isValidated = dbHelper.validateOrder(idOrder, "refused")
            if (isValidated) {
                Toast.makeText(requireContext(), "Order successfully refused", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Refusal failed", Toast.LENGTH_SHORT).show()
            }
        }
    }




    private fun convertirTimestampEnDate(timestamp: Long): String {
        val date = Date(timestamp)
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return format.format(date)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrderDetails().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}