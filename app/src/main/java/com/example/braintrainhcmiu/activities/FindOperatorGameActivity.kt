package com.example.braintrainhcmiu.activities

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.asFlow
import androidx.lifecycle.lifecycleScope
import com.example.braintrainhcmiu.BrainTrainApplication
import com.example.braintrainhcmiu.R
import com.example.braintrainhcmiu.data.FindOperatorGame
import com.example.braintrainhcmiu.models.FindOperatorGameViewModel
import com.example.braintrainhcmiu.models.FindOperatorGameViewModelFactory
import kotlinx.coroutines.launch

public class FindOperatorGameActivity : AppCompatActivity() {
  private var LevelTextView: TextView? = null
  private var FindOperatorTextView: TextView? = null
  private var FindOperatorTimeTextView: TextView? = null
  private var FindOperatorCompleteNotiTextView: TextView? = null
  private var FindOperatorScoreTextView: TextView? = null

  private var Option1: Button? = null
  private var Option2: Button? = null
  private var Option3: Button? = null
  private var Option4: Button? = null
  private var Option5: Button? = null
  private var Option6: Button? = null

  var resultButton: AppCompatButton? = null
  var timer: CountDownTimer? = null

  private val START_TIMER = 10000

  private var option1: Int = 0
  private var option2: Int = 0
  private var option3: Int = 0
  private var option4: Int = 0
  private var option5: Int = 0
  private var option6: Int = 0

  var timeLeft: Long = START_TIMER.toLong()
  private var count = 0
  private var point1 = 0
  private var point2: Int = 0
  private var point3: Int = 0

  private var level = 0
  private var score = 0
  private val text: String? = null
  private lateinit var currentQuestion: FindOperatorGame
  lateinit var numbers: ArrayList<Int>

  private var select1: Int = 0
  private var select2: Int = 0
  private var totalSelect = 0
  private var temp1: String? = null
  private var temp2: String? = null

  private val findOperatorModel: FindOperatorGameViewModel by viewModels {
    FindOperatorGameViewModelFactory((application as BrainTrainApplication).findOperatorGameRepository)
  }

  private lateinit var questions: List<FindOperatorGame>

  companion object {
    const val TAG = "FindOperatorGameActivity"

  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_find_operator_game)

    initializeViews()

    gameStart()

  }

  private fun initializeViews() {
    LevelTextView = findViewById(R.id.FindOperatorLevelTextView)
    FindOperatorTextView = findViewById(R.id.FindOperatorTextView)
    FindOperatorTimeTextView = findViewById(R.id.FindOperatorTimeTextView)
    FindOperatorCompleteNotiTextView = findViewById(R.id.FindOperatorCompleteNoticeTextView)
    FindOperatorScoreTextView = findViewById(R.id.FindOperatorScoreTextView)

    Option1 = findViewById(R.id.Option1)
    Option2 = findViewById(R.id.Option2)
    Option3 = findViewById(R.id.Option3)
    Option4 = findViewById(R.id.Option4)
    Option5 = findViewById(R.id.Option5)
    Option6 = findViewById(R.id.Option6)

    resultButton = findViewById(R.id.resultButton)

    FindOperatorCompleteNotiTextView?.visibility = View.GONE

  }

  private fun gameStart() {
    val intentGet = intent
    level = intentGet.getIntExtra("level", 0)

    lifecycleScope.launch {
      findOperatorModel.findOperatorGameQuestions.asFlow().collect { it ->
        // get questions from database and filter based on the level

        questions = when(level) {
          10 -> it.filter {
            it.target_sum in 11..99
          }

          100 -> it.filter {
            it.target_sum in 101..999
          }


          else -> it.filter {
            it.target_sum in 1001..10000
          }
        }

        LevelTextView?.text = "Level $level"

        currentQuestion = questions[count]
        FindOperatorTextView?.text = "Tìm hai số có tổng bằng ${currentQuestion.target_sum}"

        numbers = ArrayList(currentQuestion.options.split(",").map(String::toInt))

        generate(level)

        UpdateTimer()
      }
    }
  }

  private fun generate(level: Int) {
    val temp = level + 1
    option1 = numbers!![0]
    Option1!!.text = Integer.toString(option1)
    option2 = numbers!![1]
    Option2!!.text = Integer.toString(option2)
    option3 = numbers!![2]
    Option3!!.text = Integer.toString(option3)
    option4 = numbers!![3]
    Option4!!.text = Integer.toString(option4)
    option5 = numbers!![4]
    Option5!!.text = Integer.toString(option5)
    option6 = numbers!![5]
    Option6!!.text = Integer.toString(option6)

    Option1!!.setBackgroundColor(-0xc5c872)
    Option2!!.setBackgroundColor(-0xc5c872)
    Option3!!.setBackgroundColor(-0xc5c872)
    Option4!!.setBackgroundColor(-0xc5c872)
    Option5!!.setBackgroundColor(-0xc5c872)
    Option6!!.setBackgroundColor(-0xc5c872)
  }

  fun ClickOption1(view: View?) {
    if (totalSelect == 0) {
      Option1!!.setBackgroundColor(-0x499b)
      select1 = option1
      temp1 = "option1"
    }
    if (totalSelect == 1) {
      Option1!!.setBackgroundColor(-0x499b)
      select2 = option1
      temp2 = "option1"
      checkSelect()
    }
    totalSelect = totalSelect + 1
  }

  fun ClickOption2(view: View?) {
    if (totalSelect == 0) {
      Option2!!.setBackgroundColor(-0x499b)
      select1 = option2
      temp1 = "option2"
    }
    if (totalSelect == 1) {
      Option2!!.setBackgroundColor(-0x499b)
      select2 = option2
      temp2 = "option2"
      checkSelect()
    }
    totalSelect = totalSelect + 1
  }

  fun ClickOption3(view: View?) {
    if (totalSelect == 0) {
      Option3!!.setBackgroundColor(-0x499b)
      select1 = option3
      temp1 = "option3"
    }
    if (totalSelect == 1) {
      Option3!!.setBackgroundColor(-0x499b)
      select2 = option3
      temp2 = "option3"
      checkSelect()
    }
    totalSelect = totalSelect + 1
  }

  fun ClickOption4(view: View?) {
    if (totalSelect == 0) {
      Option4!!.setBackgroundColor(-0x499b)
      select1 = option4
      temp1 = "option4"
    }
    if (totalSelect == 1) {
      Option4!!.setBackgroundColor(-0x499b)
      select2 = option4
      temp2 = "option4"
      checkSelect()
    }
    totalSelect = totalSelect + 1
  }

  fun ClickOption5(view: View?) {
    if (totalSelect == 0) {
      Option5!!.setBackgroundColor(-0x499b)
      select1 = option5
      temp1 = "option5"
    }
    if (totalSelect == 1) {
      Option5!!.setBackgroundColor(-0x499b)
      select2 = option5
      temp2 = "option5"
      checkSelect()
    }
    totalSelect = totalSelect + 1
  }

  fun ClickOption6(view: View?) {
    if (totalSelect == 0) {
      Option6!!.setBackgroundColor(-0x499b)
      select1 = option6
      temp1 = "option6"
    }
    if (totalSelect == 1) {
      Option1!!.setBackgroundColor(-0x499b)
      select2 = option6
      temp2 = "option6"
      checkSelect()
    }
    totalSelect = totalSelect + 1
  }

  fun UpdateTimer() {
    timer = object : CountDownTimer(START_TIMER.toLong(), 1000) {
      override fun onTick(millisUntilFinished: Long) {
        timeLeft = millisUntilFinished / 1000
        updateText()
      }

      override fun onFinish() {
        if (totalSelect == 0) {
          gameStop()
        } else {
          // Timer finished, but user has selected options
          // You might want to handle this case differently
          // For now, just restarting the timer
          UpdateTimer()
        }
      }
    }.start()
  }

  fun pauseTimer() {
    timer!!.cancel()
  }

  fun updateText() {
    FindOperatorTimeTextView!!.text = "Bạn còn: $timeLeft giây"
  }

  fun updateScore(score: Int) {
    FindOperatorScoreTextView!!.text = "Điểm: $score"
  }

  fun gameStop() {
    FindOperatorCompleteNotiTextView!!.visibility = View.VISIBLE
    resultButton!!.visibility = View.VISIBLE
    Option1!!.isClickable = false
    Option2!!.isClickable = false
    Option3!!.isClickable = false
    Option4!!.isClickable = false
    Option5!!.isClickable = false
    Option6!!.isClickable = false
  }

  fun gameEnd() {
    FindOperatorCompleteNotiTextView!!.visibility = View.VISIBLE
    FindOperatorCompleteNotiTextView!!.text = "Hoàn Thành"
    resultButton!!.visibility = View.VISIBLE
    Option1!!.isClickable = false
    Option2!!.isClickable = false
    Option3!!.isClickable = false
    Option4!!.isClickable = false
    Option5!!.isClickable = false
    Option6!!.isClickable = false
  }

  override fun onBackPressed() {
    super.onBackPressed()
    pauseTimer()
    finish()
  }

  fun checkSelect() {
    var esimatedSum = select1 + select2
    Log.d(TAG, select1.toString())
    Log.d(TAG, select2.toString())

    if (esimatedSum == currentQuestion.target_sum) {
      Toast.makeText(this@FindOperatorGameActivity, "Câu trả lời Đúng!", Toast.LENGTH_SHORT).show()
      totalSelect = -1
      pauseTimer()
      level++
      count++

      score += currentQuestion.score

      updateScore(score)

      if (count == questions.size) {
        gameEnd()
      } else {
        gameStart()
      }

    } else {
      if (temp1 == "option1" || temp2 == "option1") {
        Option1!!.setBackgroundColor(-0xc5c872)
      }
      if (temp1 == "option2" || temp2 == "option2") {
        Option2!!.setBackgroundColor(-0xc5c872)
      }
      if (temp1 == "option3" || temp2 == "option3") {
        Option3!!.setBackgroundColor(-0xc5c872)
      }
      if (temp1 == "option4" || temp2 == "option4") {
        Option4!!.setBackgroundColor(-0xc5c872)
      }
      if (temp1 == "option5" || temp2 == "option5") {
        Option5!!.setBackgroundColor(-0xc5c872)
      }
      if (temp1 == "option6" || temp2 == "option6") {
        Option6!!.setBackgroundColor(-0xc5c872)
      }

      Toast.makeText(this@FindOperatorGameActivity, "Câu trả lời Sai!", Toast.LENGTH_SHORT).show()

      totalSelect = -1
      select1 = 0
      select2 = 0
    }
  }

}