package com.example.braintrainhcmiu.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import com.example.braintrainhcmiu.R

public class LanguageActivity : AppCompatActivity(), View.OnClickListener {
  private lateinit var completeWordCardView: CardView
  private lateinit var findWordCardView: CardView
  private lateinit var conjunctionCardView: CardView
  private lateinit var sortingCharCardView: CardView
  private lateinit var completeWordCompleted: ImageView
  private lateinit var findWordCompleted: ImageView
  private lateinit var conjunctionCompleted: ImageView
  private lateinit var sortingCharCompleted: ImageView

  private var gameOneGuide: String? = null
  private var gameTwoGuide: String? = null
  private var gameThreeGuide: String? = null
  private var gameFourGuide: String? = null

  private var completeWordGuideButton: AppCompatButton? = null
  private var findWordGuideButton: AppCompatButton? = null
  private var conjunctionGuideButton: AppCompatButton? = null
  private var sortingCharGuideButton: AppCompatButton? = null


  @Override
  protected override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_language)

    completeWordCardView = findViewById<CardView>(R.id.completeWordCardView)
    findWordCardView = findViewById<CardView>(R.id.findWordCardView)
    conjunctionCardView = findViewById<CardView>(R.id.conjunctionCardView)
    sortingCharCardView = findViewById<CardView>(R.id.sortingCharCardView)

    completeWordCompleted = findViewById(R.id.completeWordComplete)
    findWordCompleted = findViewById(R.id.findWordCompleted)
    conjunctionCompleted = findViewById(R.id.conjunctionComplete)
    sortingCharCompleted = findViewById(R.id.sortingCharComplete)

    completeWordGuideButton = findViewById(R.id.completeWordGuideButton)
    findWordGuideButton = findViewById(R.id.findWordGuideButton)
    conjunctionGuideButton = findViewById(R.id.conjunctionGuideButton)
    sortingCharGuideButton = findViewById(R.id.sortingCharGuideButton)

    completeWordCardView.setOnClickListener(this)
    findWordCardView.setOnClickListener(this)
    conjunctionCardView.setOnClickListener(this)
    sortingCharCardView.setOnClickListener(this)

    completeWordCompleted.setVisibility(View.INVISIBLE)
    findWordCompleted.setVisibility(View.INVISIBLE)
    conjunctionCompleted.setVisibility(View.INVISIBLE)
    sortingCharCompleted.setVisibility(View.INVISIBLE)

    val sharedPreferences: SharedPreferences = getSharedPreferences("guideButton", MODE_PRIVATE)
    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    editor.putBoolean("completeWord", false)
    editor.putBoolean("findWord", false)
    editor.putBoolean("conjunction", false)
    editor.putBoolean("sortingChar", false)
    editor.apply()

    val completeWord: Boolean = sharedPreferences.getBoolean("completeWord", false)
    val findWord: Boolean = sharedPreferences.getBoolean("findWord", false)
    val conjunction: Boolean = sharedPreferences.getBoolean("conjunction", false)
    val sortingChar: Boolean = sharedPreferences.getBoolean("sortingChar", false)

    if (completeWord) {
      completeWordCompleted.setVisibility(View.VISIBLE)
    }
    if (findWord) {
      findWordCompleted.setVisibility(View.VISIBLE)
    }
    if (conjunction) {
      conjunctionCompleted.setVisibility(View.VISIBLE)
    }
    if (sortingChar) {
      sortingCharCompleted.setVisibility(View.VISIBLE)
    }

    completeWordGuideButton!!.setOnClickListener {
      val alert = AlertDialog.Builder(this@LanguageActivity)
      alert.setTitle("Hướng Dẫn")
      alert.setMessage(
        """
          Trò chơi sẽ cung cấp cho người dùng 1 chữ cái
          
          Trong vòng 2 phút, hãy tìm những từ có nghĩa bắt đầu bằng chữ cái này
          
          Từ bạn tìm thấy càng dài, bạn càng nhận được số điểm cao
          """.trimIndent()
      )
      alert.setCancelable(false)
      alert.setNegativeButton(
        "Không hiện lại"
      ) { dialog, which ->
        dialog.cancel()
        completeWordGuideButton!!.visibility = View.GONE
        editor.putString("gameOneGuideLanguage", "notAppear")
        editor.apply()
      }
      alert.setPositiveButton(
        "Đã Hiểu"
      ) { dialog, which -> dialog.cancel() }
      val alertDialog = alert.create()
      alertDialog.show()
    }

    findWordGuideButton!!.setOnClickListener {
      val alert = AlertDialog.Builder(this@LanguageActivity)
      alert.setTitle("Hướng Dẫn")
      alert.setMessage("Trong vòng 2 phút, nhiệm vụ là tìm những từ có thể ghép với từ cho sẵn ban đầu thành từ ghép có nghĩa")
      alert.setCancelable(false)
      alert.setNegativeButton(
        "Không hiện lại"
      ) { dialog, which ->
        dialog.cancel()
        findWordGuideButton!!.visibility = View.GONE
        editor.putString("gameTwoGuideLanguage", "notAppear")
        editor.apply()
      }
      alert.setPositiveButton(
        "Đã Hiểu"
      ) { dialog, which -> dialog.cancel() }
      val alertDialog = alert.create()
      alertDialog.show()
    }

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
      R.id.completeWordCardView -> Intent(
        this@LanguageActivity,
        CompleteWordGameActivity::class.java
      )

      R.id.findWordCardView -> Intent(this@LanguageActivity, FindWordGameActivity::class.java)
      R.id.conjunctionCardView -> Intent(this@LanguageActivity, ConjunctionGameActivity::class.java)
      else -> Intent(this@LanguageActivity, SortingCharGameActivity::class.java)
    }
    startActivity(intent)
    finish()
  }
}