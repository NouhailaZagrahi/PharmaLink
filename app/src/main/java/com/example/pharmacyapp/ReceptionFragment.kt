package com.example.pharmacyapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmacyapp.databinding.FragmentArticleDetaillsBinding
import com.example.pharmacyapp.databinding.FragmentReceptionBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ReceptionFragment : Fragment() {
    private lateinit var dbHelper: DatabaseHelper
    val args : ReceptionFragmentArgs by navArgs()

    private var _binding: FragmentReceptionBinding? = null
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
        _binding = FragmentReceptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var id_user_connecte = args.idUser
        var user = dbHelper.getUserById(id_user_connecte)

        if(dbHelper.hasUserOrder(id_user_connecte)){
            binding.checkBtn.visibility = View.VISIBLE
            binding.checkBtn.setOnClickListener {
                var idOrder = dbHelper.getLastOrderIdByUserId(id_user_connecte)
                val action = ReceptionFragmentDirections.actionReceptionFragmentToStatusFragment(idOrder)
                findNavController().navigate(action)

            }
        }

        binding.nomUser.text = "Hi ${user.first_name}"


        //recyleview of categories
        var pers = dbHelper.getAllCategories()

        // Handle clicks using a lambda function
        val myAdapter = categorieAdapter(pers, object : categorieAdapter.OnItemClickListener {
            override fun onItemClick(categorie: Categorie) {

                val action = ReceptionFragmentDirections.actionReceptionFragmentToMedicamentsFragment(categorie.id,id_user_connecte)
                findNavController().navigate(action)
            }
        })

        val rv = view.findViewById<RecyclerView>(R.id.recyclerView1)
        rv.adapter = myAdapter
        rv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        //recycleview of articles
        var articles = dbHelper.getAllArticles()

        val myAdapter2 = articleAdapter(articles, object : articleAdapter.OnReadMoreClickListener {
            override fun onReadMoreClick(article: Article) {

                val action2 = ReceptionFragmentDirections.actionReceptionFragmentToArticleDetaills2(user,article.id)
                findNavController().navigate(action2)
            }
        })

        val rv2 = view.findViewById<RecyclerView>(R.id.recycleView2)
        rv2.adapter = myAdapter2
        rv2.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        //the bottom navigation
        val bottomNav = view.findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.buttom_nav)
        bottomNav.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    //Toast.makeText(requireContext(), "Home clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.cart -> {
                    val action = ReceptionFragmentDirections.actionReceptionFragmentToCartFragment(id_user_connecte)
                    findNavController().navigate(action)
                    true
                }
                R.id.profile -> {
                    val action = ReceptionFragmentDirections.actionReceptionFragmentToProfilFragment(user)
                    findNavController().navigate(action)
                    true
                }
                else -> false
            }
        }

    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReceptionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}