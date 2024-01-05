package com.example.braintrainhcmiu.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.braintrainhcmiu.MainActivity
import com.example.braintrainhcmiu.R
import com.example.braintrainhcmiu.activities.LanguageActivity
import com.example.braintrainhcmiu.activities.MathActivity
import com.example.braintrainhcmiu.database.AppDatabase


public class HomeFragment : Fragment() , View.OnClickListener {
  private lateinit var mathCardView: CardView
  private lateinit var languageCardView: CardView

  companion object {
    private const val TAG = "HomeFragment"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)


  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view: View = inflater.inflate(R.layout.fragment_home, container, false)

    mathCardView = view.findViewById<CardView>(R.id.mathCardView)
    languageCardView = view.findViewById<CardView>(R.id.languageCardView)

    mathCardView.setOnClickListener(this)
    languageCardView.setOnClickListener(this)

    return view

  }

  override fun onClick(v: View) {
    val intent: Intent
    intent = when (v.id) {
      R.id.languageCardView -> Intent(activity, LanguageActivity::class.java)
      R.id.mathCardView -> Intent(activity, MathActivity::class.java)
      else -> Intent(activity, MainActivity::class.java)
    }
    startActivity(intent)
  }

}