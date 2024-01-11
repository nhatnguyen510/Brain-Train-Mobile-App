package com.example.braintrainhcmiu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.braintrainhcmiu.database.AppDatabase
import com.example.braintrainhcmiu.databinding.ActivityMainBinding
import com.example.braintrainhcmiu.fragments.HomeFragment
import com.example.braintrainhcmiu.models.FindOperatorGameViewModel
import com.example.braintrainhcmiu.models.FindOperatorGameViewModelFactory
import com.example.braintrainhcmiu.models.UserViewModel
import com.example.braintrainhcmiu.models.UserViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private lateinit var mGoogleSignInClient: GoogleSignInClient
  private lateinit var mAuth: FirebaseAuth

  private lateinit var drawerLayout: DrawerLayout
  private lateinit var navigationView: NavigationView
  private lateinit var headerView: View

  private lateinit var navHeaderImage: ImageView
  private lateinit var navHeaderName: TextView
  private lateinit var navHeaderEmail: TextView

  private lateinit var actionBarToggle: ActionBarDrawerToggle

  private val userViewModel: UserViewModel by viewModels {
    UserViewModelFactory((application as BrainTrainApplication).userRepository)
  }

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

    replaceFragment(HomeFragment())

    drawerLayout = findViewById(R.id.drawer_layout)
    navigationView = findViewById(R.id.navigation_view)
    headerView = navigationView.getHeaderView(0)

    navHeaderImage = headerView.findViewById(R.id.nav_header_image)
    navHeaderName = headerView.findViewById(R.id.nav_header_name)
    navHeaderEmail = headerView.findViewById(R.id.nav_header_email)

    if (account != null) {
      navHeaderImage.setImageURI(account.photoUrl)
      navHeaderName.text = account.displayName
      navHeaderEmail.text = account.email
    }

    // Initialize ActionBarDrawerToggle after onCreate
    actionBarToggle = ActionBarDrawerToggle(
      this, drawerLayout, R.string.nav_open, R.string.nav_close
    )

    actionBarToggle.isDrawerIndicatorEnabled = true // show hamburger icon
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    drawerLayout.addDrawerListener(actionBarToggle)

    actionBarToggle.syncState()

    navigationView.setNavigationItemSelectedListener { menuItem ->
      when (menuItem.itemId) {
        R.id.nav_logout -> {
          signOutAndStartSignInActivity()
          true
        }

        else -> {
          false
        }
      }
    }
  }

  private fun signOutAndStartSignInActivity() {
    mAuth.signOut()

    mGoogleSignInClient.signOut().addOnCompleteListener(this) {
      // Optional: Update UI or show a message to the user
      Toast.makeText(this, "Signed out successfully!", Toast.LENGTH_SHORT).show()

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

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return if (actionBarToggle.onOptionsItemSelected(item)) {
      true
    } else {
      super.onOptionsItemSelected(item)
    }
  }
}

