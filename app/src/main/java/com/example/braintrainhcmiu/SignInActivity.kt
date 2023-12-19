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

    // Configure sign-in to request the user's ID, email address, and basic
    // profile. ID and basic profile are included in DEFAULT_SIGN_IN.

    binding.googleSignInButton.setOnClickListener {
      signIn()
    }
  }

  override fun onStart() {
    super.onStart()
    // Check for existing Google Sign In account, if the user is already signed in
    // the GoogleSignInAccount will be non-null.
    val account = GoogleSignIn.getLastSignedInAccount(this)

    if (account != null) {
      Toast.makeText(this, "Signed in as ${account.displayName}", Toast.LENGTH_SHORT).show()
      startActivity(Intent(this, MainActivity::class.java))
      finish()
    }
  }

  private fun signIn() {
    val gso =
      GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()

    // Build a GoogleSignInClient with the options specified by gso.
    mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

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

      // Signed in successfully, show authenticated UI.
      Toast.makeText(this, "Signed in as ${user?.displayName}", Toast.LENGTH_SHORT).show()
      startActivity(Intent(this, MainActivity::class.java))
      finish()

    } catch (e: ApiException) {
      // The ApiException status code indicates the detailed failure reason.
      // Please refer to the GoogleSignInStatusCodes class reference for more information.
      Log.w(TAG, "signInResult:failed code=" + e.statusCode)
      Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()

    }
  }

}