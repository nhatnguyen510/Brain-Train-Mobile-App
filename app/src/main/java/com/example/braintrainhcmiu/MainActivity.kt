package com.example.braintrainhcmiu

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.braintrainhcmiu.databinding.ActivityMainBinding
import com.example.braintrainhcmiu.databinding.SignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

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


    val textView = binding.textView

    val auth = Firebase.auth
    val user = auth.currentUser

    if (user != null) {
      // log the user
      Log.d(TAG, "onCreate: $user")

      val userName = user.displayName
      user.toString()
      textView.text = "Welcome, " + user.toString()

    } else {
      // Handle the case where the user is not signed in
    }


// Inside onCreate() method
    val signOutButton = binding.logout
    signOutButton.setOnClickListener {
      signOutAndStartSignInActivity()
    }


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
}

