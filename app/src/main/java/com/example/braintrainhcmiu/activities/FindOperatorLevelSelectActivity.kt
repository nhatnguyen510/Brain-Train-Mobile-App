package com.example.braintrainhcmiu.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.braintrainhcmiu.R

class FindOperatorLevelSelectActivity : AppCompatActivity() {
  private lateinit var sumOfTen: Button
  private lateinit var sumOfHundred: Button
  private lateinit var sumOfThousand: Button
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_find_operator_level_select)
    sumOfTen = findViewById<Button>(R.id.sumOfTen)
    sumOfHundred = findViewById<Button>(R.id.sumOfHundred)
    sumOfThousand = findViewById<Button>(R.id.sumOfThousand)
    sumOfTen.setOnClickListener(View.OnClickListener {
      val intent = Intent(
        this@FindOperatorLevelSelectActivity,
        FindOperatorGameActivity::class.java
      )
      intent.putExtra("level", 10)
      startActivity(intent)
      finish()
    })
    sumOfHundred.setOnClickListener(View.OnClickListener {
      val intent = Intent(
        this@FindOperatorLevelSelectActivity,
        FindOperatorGameActivity::class.java
      )
      intent.putExtra("level", 100)
      startActivity(intent)
      finish()
    })
    sumOfThousand.setOnClickListener(View.OnClickListener {
      val intent = Intent(
        this@FindOperatorLevelSelectActivity,
        FindOperatorGameActivity::class.java
      )
      intent.putExtra("level", 1000)
      startActivity(intent)
      finish()
    })
  }
}