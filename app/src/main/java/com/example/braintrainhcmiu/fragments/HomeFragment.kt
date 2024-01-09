package com.example.braintrainhcmiu.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.braintrainhcmiu.MainActivity
import com.example.braintrainhcmiu.R
import com.example.braintrainhcmiu.activities.LanguageActivity
import com.example.braintrainhcmiu.activities.MathActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.navigation.NavigationView
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout


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
    super.onCreateView(inflater, container, savedInstanceState)

    val view: View = inflater.inflate(R.layout.fragment_home, container, false)

    mathCardView = view.findViewById(R.id.mathCardView)
    languageCardView = view.findViewById(R.id.languageCardView)

    mathCardView.setOnClickListener(this)
    languageCardView.setOnClickListener(this)

    return view

  }

  override fun onClick(v: View) {

    val intent: Intent = when (v.id) {
      R.id.languageCardView -> Intent(activity, LanguageActivity::class.java)
      R.id.mathCardView -> Intent(activity, MathActivity::class.java)
      else -> Intent(activity, MainActivity::class.java)
    }
    startActivity(intent)
  }



}