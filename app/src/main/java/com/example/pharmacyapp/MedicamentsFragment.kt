package com.example.pharmacyapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmacyapp.databinding.FragmentMedicamentsBinding
import com.example.pharmacyapp.databinding.FragmentReceptionBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MedicamentsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MedicamentsFragment : Fragment() {
    private lateinit var dbHelper: DatabaseHelper
    val args : MedicamentsFragmentArgs by navArgs()

    private var _binding: FragmentMedicamentsBinding? = null
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
        _binding = FragmentMedicamentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var id_cat = args.idcat
        var id_user = args.idUser


        binding.Titletxt.text = dbHelper.getCategorieTitleById(id_cat)


        //filling the recycleview with medicaments of the category selected
        var pers = dbHelper.getMedicamentsByCategory(id_cat)

        val myAdapter = medicamentAdapter(pers, object : medicamentAdapter.OnItemClickListener {
            override fun onItemClick(medicament: Medicament) {

                val action = MedicamentsFragmentDirections.actionMedicamentsFragmentToBlankFragmentDetails(medicament,id_user)
                findNavController().navigate(action)
            }
        })

        val rv = binding.medicinerecycleview
        rv.adapter=myAdapter
        rv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)



        //searchview
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { myAdapter.filter(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                myAdapter.filter(newText ?: "")
                return true
            }
        })

        val backBtn =binding.imageView2

        backBtn.setOnClickListener {

            requireActivity().onBackPressed()
        }


    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MedicamentsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}