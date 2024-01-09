package com.example.braintrainhcmiu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.braintrainhcmiu.databinding.SignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class SignInActivity : AppCompatActivity() {
  private lateinit var binding: SignInBinding
  private lateinit var mGoogleSignInClient: GoogleSignInClient
  private lateinit var auth: FirebaseAuth

  companion object {
    private const val RC_SIGN_IN = 9001
    private const val TAG = "SignInActivity"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = SignInBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.googleSignInButton.setOnClickListener {
      signIn()
    }
  }

  override fun onStart() {
    super.onStart()
    // Check for existing Google Sign In account, if the user is already signed in
    // the GoogleSignInAccount will be non-null.
    val account = GoogleSignIn.getLastSignedInAccount(this)
    checkAndHandleSignInAccount(account)
  }

  private fun initGoogleSignIn() {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
    mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
  }

  private fun checkAndHandleSignInAccount(account: GoogleSignInAccount?) {
    if (account != null) {
      Toast.makeText(this, "Signed in as ${account.displayName}", Toast.LENGTH_SHORT).show()
      // Check and add user to the database if necessary
      // ...

      startActivity(Intent(this, MainActivity::class.java))
      finish()
    }
  }

  private fun signIn() {
    initGoogleSignIn()
    val signInIntent = mGoogleSignInClient.signInIntent
    startActivityForResult(signInIntent, RC_SIGN_IN)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
    if (requestCode == RC_SIGN_IN) {
      // The Task returned from this call is always completed, no need to attach
      // a listener.
      val task = GoogleSignIn.getSignedInAccountFromIntent(data)
      handleSignInResult(task)
    }
  }

  private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
    try {
      val user = completedTask.getResult(ApiException::class.java)
      checkAndHandleSignInAccount(user)
    } catch (e: ApiException) {
      Log.w(TAG, "signInResult:failed code=" + e.statusCode)
      Toast.makeText(this, "Authentication failed. Please try again.", Toast.LENGTH_SHORT).show()
    }
  }
}
