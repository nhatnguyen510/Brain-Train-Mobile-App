package com.example.braintrainhcmiu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.braintrainhcmiu.database.AppDatabase
import com.example.braintrainhcmiu.databinding.ActivityMainBinding
import com.example.braintrainhcmiu.fragments.HomeFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private lateinit var mGoogleSignInClient: GoogleSignInClient
  private lateinit var mAuth: FirebaseAuth

  companion object {
    private const val TAG = "MainActivity"
  }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityMainBinding.inflate(layoutInflater)

    setContentView(binding.root)

    mAuth = FirebaseAuth.getInstance()

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
      .requestEmail()
      .build()

    mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

    val account = GoogleSignIn.getLastSignedInAccount(this)

    val user = account?.account

    replaceFragment(HomeFragment())



  }


  private fun signOutAndStartSignInActivity() {
    mAuth.signOut()

    mGoogleSignInClient.signOut().addOnCompleteListener(this) {
      // Optional: Update UI or show a message to the user
      val intent = Intent(this@MainActivity, SignInActivity::class.java)
      startActivity(intent)
      finish()
    }
  }

  private fun replaceFragment(fragment: Fragment) {
    val fragmentManager = supportFragmentManager
    val fragmentTransaction = fragmentManager.beginTransaction()
    fragmentTransaction.replace(R.id.frame_layout, fragment)
    fragmentTransaction.commit()
  }
}

