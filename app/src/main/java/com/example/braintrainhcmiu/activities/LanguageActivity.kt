package com.example.braintrainhcmiu.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import com.example.braintrainhcmiu.BrainTrainApplication
import com.example.braintrainhcmiu.R
import com.example.braintrainhcmiu.models.UserViewModel
import com.example.braintrainhcmiu.models.UserViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn

public class LanguageActivity : AppCompatActivity(), View.OnClickListener {
  private lateinit var conjunctionCardView: CardView
  private lateinit var sortingCharCardView: CardView

  private lateinit var conjunctionCompleted: ImageView
  private lateinit var sortingCharCompleted: ImageView

  private lateinit var conjunctionScore: TextView
  private lateinit var sortingCharScore: TextView

  private var conjunctionGuideButton: AppCompatButton? = null
  private var sortingCharGuideButton: AppCompatButton? = null

  private val userViewModel: UserViewModel by viewModels {
    UserViewModelFactory((application as BrainTrainApplication).userRepository)
  }

  @Override
  protected override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_language)


    conjunctionCardView = findViewById<CardView>(R.id.conjunctionCardView)
    sortingCharCardView = findViewById<CardView>(R.id.sortingCharCardView)

    conjunctionCompleted = findViewById(R.id.conjunctionComplete)
    sortingCharCompleted = findViewById(R.id.sortingCharComplete)

    conjunctionScore = findViewById(R.id.conjunctionScore)
    sortingCharScore = findViewById(R.id.sortingCharScore)

    conjunctionGuideButton = findViewById(R.id.conjunctionGuideButton)
    sortingCharGuideButton = findViewById(R.id.sortingCharGuideButton)

    conjunctionCardView.setOnClickListener(this)
    sortingCharCardView.setOnClickListener(this)

    conjunctionCompleted.setVisibility(View.INVISIBLE)
    sortingCharCompleted.setVisibility(View.INVISIBLE)

    val sharedPreferences: SharedPreferences = getSharedPreferences("guideButton", MODE_PRIVATE)
    val editor: SharedPreferences.Editor = sharedPreferences.edit()

    editor.putBoolean("conjunction", false)
    editor.putBoolean("sortingChar", false)
    editor.apply()

    val conjunction: Boolean = sharedPreferences.getBoolean("conjunction", false)
    val sortingChar: Boolean = sharedPreferences.getBoolean("sortingChar", false)

    if (conjunction) {
      conjunctionCompleted.setVisibility(View.VISIBLE)
    }
    if (sortingChar) {
      sortingCharCompleted.setVisibility(View.VISIBLE)
    }

    val account = GoogleSignIn.getLastSignedInAccount(this)

    val userId = account!!.id.hashCode()

    val conjunctionScore: Int = userViewModel.getUserAsync(userId).conjunctionScore
    val sortingCharScore: Int = userViewModel.getUserAsync(userId).sortingCharScore

    this.conjunctionScore.text = "Điểm của bạn: $conjunctionScore"
    this.sortingCharScore.text = "Điểm của bạn: $sortingCharScore"

    conjunctionGuideButton!!.setOnClickListener {
      val alert = AlertDialog.Builder(this@LanguageActivity)
      alert.setTitle("Hướng Dẫn")
      alert.setMessage(
        """
          Người dùng tiếp tục tìm một từ khác để nối với từ cuối cùng trong từ ghép đã tìm được trước đó để tạo từ ghép có nghĩa mới
          
          Tiếp tục làm điều này cho đến khi không thể tìm thấy nhiều từ hơn để phù hợp
          """.trimIndent()
      )
      alert.setCancelable(false)
      alert.setNegativeButton(
        "Không hiện lại"
      ) { dialog, which ->
        dialog.cancel()
        conjunctionGuideButton!!.visibility = View.GONE
        editor.putString("gameThreeGuideLanguage", "notAppear")
        editor.apply()
      }
      alert.setPositiveButton(
        "Đã Hiểu"
      ) { dialog, which -> dialog.cancel() }
      val alertDialog = alert.create()
      alertDialog.show()
    }

    sortingCharGuideButton!!.setOnClickListener {
      val alert = AlertDialog.Builder(this@LanguageActivity)
      alert.setTitle("Hướng Dẫn")
      alert.setMessage(
        """
          Trò chơi này sẽ cung cấp một cụm từ có các chữ cái được xáo trộn
          
          Nhiệm vụ của người chơi là sắp xếp lại thứ tự các chữ cái để tìm ra từ chính xác
          """.trimIndent()
      )
      alert.setCancelable(false)
      alert.setNegativeButton(
        "Không hiện lại"
      ) { dialog, which ->
        dialog.cancel()
        sortingCharGuideButton!!.visibility = View.GONE
        editor.putString("gameFourGuideLanguage", "notAppear")
        editor.apply()
      }
      alert.setPositiveButton(
        "Đã Hiểu"
      ) { dialog, which -> dialog.cancel() }
      val alertDialog = alert.create()
      alertDialog.show()
    }

  }

  override fun onClick(v: View) {
    val intent: Intent
    intent = when (v.id) {
      R.id.conjunctionCardView -> Intent(this@LanguageActivity, ConjunctionGameActivity::class.java)
      else -> Intent(this@LanguageActivity, SortingCharGameActivity::class.java)
    }
    startActivity(intent)
    finish()
  }
}