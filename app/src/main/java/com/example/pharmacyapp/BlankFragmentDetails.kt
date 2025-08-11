package com.example.pharmacyapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.pharmacyapp.databinding.FragmentArticleDetaillsBinding
import com.example.pharmacyapp.databinding.FragmentBlankDetailsBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class BlankFragmentDetails : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val args : BlankFragmentDetailsArgs by navArgs()
    private lateinit var dbHelper: DatabaseHelper

    private var _binding: FragmentBlankDetailsBinding? = null
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
        _binding = FragmentBlankDetailsBinding.inflate(inflater, container, false)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id_user = args.idUser
        var medicament = args.medicament

        var quantity_initial = 1

        binding.medicineName.text = medicament.title
        binding.medicineDescription.text = medicament.desc
        binding.medicineImage.setImageResource(medicament.pic)

        binding.medicinePrice.text = "${medicament.price} DH"
        binding.quantity.text = quantity_initial.toString()


        binding.decreaseQuantity.setOnClickListener{
            if (quantity_initial > 1) {
                quantity_initial-- // Décrémenter la quantité
                binding.quantity.text = quantity_initial.toString() // Mettre à jour l'affichage

            }
        }

        binding.increaseQuantity.setOnClickListener {
            quantity_initial++
            binding.quantity.text = quantity_initial.toString()

        }


        binding.addToCart.setOnClickListener {

            val dbHelper = DatabaseHelper(requireContext())
            dbHelper.addToCart(id_user, medicament.id, quantity_initial, medicament.price)
            Toast.makeText(requireContext(),"${medicament.title} a été ajouté au panier avec succès !",Toast.LENGTH_SHORT).show()
        }

    }
    fun resizeImage(imagePath: String, newWidth: Int, newHeight: Int): Bitmap {
        // Charge l'image depuis le chemin donné
        val originalBitmap = BitmapFactory.decodeFile(imagePath)

        // Redimensionne l'image à la nouvelle taille
        return Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, false)
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BlankFragmentDetails().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}