package com.example.pharmacyapp


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.pharmacyapp.databinding.FragmentArticleDetaillsBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ArticleDetaills : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    val args : ArticleDetaillsArgs by navArgs()
    private lateinit var dbHelper: DatabaseHelper

    private var _binding: FragmentArticleDetaillsBinding? = null
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
        _binding = FragmentArticleDetaillsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var user = args.userconnecte
        var id_article = args.idArticle
        var article = dbHelper.getArticleById(id_article)

        binding.titletxt.text = article.title
        binding.imagetxt.setImageResource(article.pic)
        binding.title2txt.text = article.title
        binding.companytxt.text = article.company
        binding.desctxt.text = article.desc

        binding.sendbtn.setOnClickListener{
            var comment = binding.commenttxt.text
            var fullname = "${user.first_name} ${user.last_name}"

            var newrow = dbHelper.addComment(id_article,fullname,comment.toString())

            if(newrow != -1L){
                Toast.makeText(requireContext(), "comment sent", Toast.LENGTH_SHORT).show()
            }
            binding.commenttxt.text.clear()
        }



        val backBtn = view.findViewById<ImageView>(R.id.back)

        backBtn.setOnClickListener {

            requireActivity().onBackPressed()
        }


    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ArticleDetaills.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ArticleDetaills().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}