package com.example.braintrainhcmiu.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.braintrainhcmiu.MainActivity
import com.example.braintrainhcmiu.R

class GameResultActivity : AppCompatActivity() {
  var resultScoreTextView: TextView? = null
  var resultBonusScoreTextView:TextView? = null
  var resultTotalScoreTextView:TextView? = null
  var backToHomeButton: Button? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_game_result)
    resultScoreTextView = findViewById<TextView>(R.id.resultScoreTextView)
    backToHomeButton = findViewById<Button>(R.id.backToHomeButton)
    resultTotalScoreTextView = findViewById<TextView>(R.id.resultTotalScoreTextView)
    val intent = intent
    val score = intent.getIntExtra("score", 0)
    resultScoreTextView?.setText("Điểm của bạn: $score")
    resultTotalScoreTextView?.setText("Tổng điểm: " + score)
    backToHomeButton?.setOnClickListener(View.OnClickListener {
      startActivity(Intent(this@GameResultActivity, MainActivity::class.java))
      finish()
    })
  }
}