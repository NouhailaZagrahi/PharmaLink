package com.example.pharmacyapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmacyapp.databinding.FragmentAdminArticlesBinding
import com.example.pharmacyapp.databinding.FragmentAdminCategoriesBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AdminArticles : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var dbHelper: DatabaseHelper

    private var _binding: FragmentAdminArticlesBinding? = null
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
        _binding = FragmentAdminArticlesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Récupérer les articles depuis la base de données
        val articles = dbHelper.getAllArticles()

        // Initialiser l'adaptateur des articles
        val myAdapter = adminarticlesAdapter(
            data = articles,
            itemClickListener = object : adminarticlesAdapter.OnItemClickListener {
                override fun onItemClick(article: Article) {
                    //Toast.makeText(requireContext(), "you clicked on ${article.title}", Toast.LENGTH_SHORT).show()
                }
            },
            getCommentsByArticleId = { articleId ->
                dbHelper.getCommentsByArticleId(articleId)
            }
        )

        val rv = binding.articlesRv
        rv.adapter = myAdapter
        rv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }






    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdminArticles().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}