package com.example.braintrainhcmiu.activities

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.asFlow
import androidx.lifecycle.lifecycleScope
import com.example.braintrainhcmiu.BrainTrainApplication
import com.example.braintrainhcmiu.R
import com.example.braintrainhcmiu.data.CompareMathGame
import com.example.braintrainhcmiu.models.CompareMathGameViewModel
import com.example.braintrainhcmiu.models.CompareMathGameViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CompareGameActivity : AppCompatActivity() {

  private lateinit var textView: TextView
  private lateinit var compareTimeTextView: TextView
  private lateinit var compareInforTextView: TextView
  private lateinit var compareCompleteNotiTextView: TextView
  private lateinit var compareOpportunityTextView: TextView
  private lateinit var compareScoreTextView: TextView
  private lateinit var expression1Btn: Button
  private lateinit var expression2Btn: Button
  private lateinit var resultButton: AppCompatButton
  private var timer: CountDownTimer? = null
  private lateinit var questions: List<CompareMathGame>
  private var expressionResult1: String? = null
  private var expressionResult2: String? = null
  private val startTimer = 60
  private var timeLeft = startTimer.toLong()
  private var count = 0
  private var point = 0
  private var level = 0
  private var score = 0

  private val compareModel: CompareMathGameViewModel by viewModels {
    CompareMathGameViewModelFactory((application as BrainTrainApplication).compareMathGameRepository)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_game_compare)

    initializeViews()
    initializeListeners()

    level = 1
    textView.text = "Level: $level"
    gameStart(level - 1)
  }

  private fun initializeViews() {
    textView = findViewById(R.id.gameCompareLevelTextView)
    compareTimeTextView = findViewById(R.id.gameCompareTimeTextView)
    expression1Btn = findViewById(R.id.expressionOne)
    expression2Btn = findViewById(R.id.expressionTwo)
    compareCompleteNotiTextView = findViewById(R.id.gameCompareNoticeTextView)
    compareScoreTextView = findViewById(R.id.gameCompareScoreTextView)
    resultButton = findViewById(R.id.gameCompareResultButton)
  }

  private fun initializeListeners() {
    resultButton.setOnClickListener {
      val intent = Intent(this@CompareGameActivity, GameResultActivity::class.java)
      intent.putExtra("score", score)
      startActivity(intent)
      finish()
    }

    expression1Btn.setOnClickListener { clickExpression(1) }
    expression2Btn.setOnClickListener { clickExpression(2) }
  }

  private fun clickExpression(selectedExpression: Int) {
    val correctExpression = if (expressionResult1!!.toInt() < expressionResult2!!.toInt()) 1 else 2

    if (selectedExpression == correctExpression) {
      handleCorrectExpression()
    } else {
      handleIncorrectExpression()
    }
  }

  private fun handleCorrectExpression() {
    setCorrectExpressionBackground()
    count++
    if (count == 5) {
      pauseTimer()
      timeLeft += 10
      updateTimer()
      count = 0
    }

    level++
    score += point
    if (level == questions.size) {
      gameEnd()
    } else {
      generate(level - 1)
    }
  }

  private fun handleIncorrectExpression() {
    pauseTimer()
    timeLeft -= 2
    level++
    generate(level - 1)
    updateTimer()
  }

  private fun setCorrectExpressionBackground() {
    val correctExpressionBtn = if (expressionResult1!!.toInt() < expressionResult2!!.toInt()) expression1Btn else expression2Btn
    correctExpressionBtn.background = ContextCompat.getDrawable(this@CompareGameActivity, R.drawable.expression_correct)
  }

  private fun generate(level: Int) {
    textView.text = "Cấp độ: ${level + 1}"
    updateScore(score)

    lifecycleScope.launch {
      compareModel.compareMathQuestions.asFlow().collect {
        questions = it
        val expressionText1: String? = questions[level].Expression1
        val expressionText2: String? = questions[level].Expression2
        expressionResult1 = questions[level].ExpressionResult1
        expressionResult2 = questions[level].ExpressionResult2
        point = questions[level].score!!
        expression1Btn.text = expressionText1
        expression2Btn.text = expressionText2
      }
    }

    setExpressionBackgrounds()
  }

  private fun setExpressionBackgrounds() {
    expression1Btn.background = ContextCompat.getDrawable(this@CompareGameActivity, R.drawable.expression)
    expression2Btn.background = ContextCompat.getDrawable(this@CompareGameActivity, R.drawable.expression)
  }

  private fun gameStart(level: Int) {
    compareCompleteNotiTextView.visibility = View.GONE
    resultButton.visibility = View.GONE
    generate(level)
    startTimer()
  }

  private fun updateTimer() {
    pauseTimer()
    startTimer()
  }

  private fun startTimer() {
    timer = object : CountDownTimer(timeLeft * 1000, 1000) {
      override fun onTick(millisUntilFinished: Long) {
        timeLeft = millisUntilFinished / 1000
        updateText()
      }

      override fun onFinish() {
        gameStop()
      }
    }.start()
  }

  private fun pauseTimer() {
    timer?.cancel()
  }

  private fun updateText() {
    compareTimeTextView.text = "Bạn còn: $timeLeft giây"
  }

  private fun updateScore(score: Int) {
    compareScoreTextView.text = "Điểm: $score"
  }

  private fun gameStop() {
    compareCompleteNotiTextView.visibility = View.VISIBLE
    resultButton.visibility = View.VISIBLE
    expression1Btn.isClickable = false
    expression2Btn.isClickable = false
  }

  override fun onBackPressed() {
    super.onBackPressed()
    pauseTimer()
    finish()
  }

  private fun gameEnd() {
    compareCompleteNotiTextView.visibility = View.VISIBLE
    compareCompleteNotiTextView.text = "Hoàn Thành"
    resultButton.visibility = View.VISIBLE
    expression1Btn.isClickable = false
    expression2Btn.isClickable = false

    // clear timer
    pauseTimer()
  }

  private fun back() {
    finish()
  }
}
