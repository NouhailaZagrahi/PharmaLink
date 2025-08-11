package com.example.pharmacyapp

import VideoAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class BlankFragmentDefini : Fragment() {
    private lateinit var viewPager: ViewPager2
    private lateinit var indicatorLayout: LinearLayout
    private val totalVideos = 2

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
        // Gonfler le layout pour ce fragment
        val view = inflater.inflate(R.layout.fragment_blank_defini, container, false)

        // Initialisation des vues
        viewPager = view.findViewById(R.id.videoViewPager)
        indicatorLayout = view.findViewById(R.id.indicatorLayout)


        // Définir l'adaptateur pour ViewPager2
        val videoList = listOf(
            R.raw.enligne,
            R.raw.present
        )
        val adapter = VideoAdapter(requireContext(), videoList)
        viewPager.adapter = adapter

        // Configurer les indicateurs
        setupIndicators(totalVideos)
        setCurrentIndicator(0)

        // Gérer le changement de page
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        val btn = view.findViewById<Button>(R.id.button)
        btn.setOnClickListener {
            findNavController().navigate(R.id.action_blankFragmentDefini_to_blankFragmentLogin)
        }

        return view
    }


    private fun setupIndicators(count: Int) {
        val indicators = Array(count) { ImageView(requireContext()) }
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)
        indicators.forEach { imageView ->
            imageView.setImageResource(R.drawable.indicator_inactive)
            imageView.layoutParams = layoutParams
            indicatorLayout.addView(imageView)
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = indicatorLayout.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorLayout.getChildAt(i) as ImageView
            if (i == index) {
                imageView.setImageResource(R.drawable.indicator_active) // Icône active
            } else {
                imageView.setImageResource(R.drawable.indicator_inactive) // Icône inactive
            }
        }
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragmentDefini.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BlankFragmentDefini().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}