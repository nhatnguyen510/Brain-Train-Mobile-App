package com.example.braintrainhcmiu.activities

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.braintrainhcmiu.BrainTrainApplication
import com.example.braintrainhcmiu.R
import com.example.braintrainhcmiu.models.UserViewModel
import com.example.braintrainhcmiu.models.UserViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.util.Collections
import java.util.Random
import kotlin.text.Typography.section

public class SortingCharGameActivity : AppCompatActivity() {
  private val START_TIMER = 20000
  var timer: CountDownTimer? = null
  var timeLeft = START_TIMER.toLong()
  var timeRes: kotlin.Long = 0
  var totalTimeRes: kotlin.Long = 0

  private var score = 0
  private var section: Int = 1
  private var level: Int = 0
  private var current: Int = 1
  private var index: Int = 0
  private var totalScore: Int = 0
  private var correctWord: String? = null
  var averageRes = 0.0
  var bonusScore: kotlin.Double = 0.0
  private var textQuestion: TextView? = null
  private var txtQId: TextView? = null
  private var txtLanguageTime: TextView? = null
  private var txtLanguageScore: TextView? = null
  private var txtLanguageCompleteNoti: TextView? = null
  private var txtLanguageCompleteGame: TextView? = null
  var submitButton: AppCompatButton? = null
  private var editAnswer: EditText? = null

  private val userViewModel: UserViewModel by viewModels {
    UserViewModelFactory((application as BrainTrainApplication).userRepository)
  }

  companion object {
    private const val TAG = "SortingCharGameActivity"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_sorting_char_game)
    textQuestion = findViewById<View>(R.id.txtQuestion) as TextView
    txtLanguageTime = findViewById<View>(R.id.txtLanguageTime) as TextView
    txtLanguageScore = findViewById<View>(R.id.txtLanguageScore) as TextView
    txtLanguageCompleteNoti = findViewById<View>(R.id.txtLanguageCompleteNoti) as TextView
    txtLanguageCompleteGame = findViewById<View>(R.id.txtLanguageCompleteGame) as TextView
    submitButton = findViewById<AppCompatButton>(R.id.submitButton)
    txtQId = findViewById<View>(R.id.txtQId) as TextView
    editAnswer = findViewById<EditText>(R.id.editAnswer)
    txtLanguageCompleteGame!!.setVisibility(View.GONE)
    gameStart()
  }

  override fun onBackPressed() {
    super.onBackPressed()
    getTotalScore()

    startActivity(Intent(this@SortingCharGameActivity, LanguageActivity::class.java))
    finish()
  }

  // Game section:
  fun gameStart() {

    level++
    txtLanguageScore?.setText("Điểm: $score")
    submitButton?.setVisibility(View.VISIBLE)
    editAnswer!!.visibility = View.VISIBLE
    txtLanguageCompleteNoti?.setVisibility(View.GONE)
    submitButton?.setEnabled(false)
    editAnswer!!.addTextChangedListener(object : TextWatcher {
      override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        submitButton?.setEnabled(s.toString().trim { it <= ' ' }.length != 0)
      }

      override fun beforeTextChanged(
        s: CharSequence, start: Int, count: Int,
        after: Int
      ) {
      }

      override fun afterTextChanged(s: Editable) {}
    })
    txtQId?.setText("Câu $level")
    correctWord = pickRandomWord()
    Log.d("correctWord", correctWord.toString())
    var word = correctWord
    word = word!!.replace(" ".toRegex(), "").trim { it <= ' ' }
    val r = Random()
    word = scramble(r, word)
    word = word.replace(".(?=.)".toRegex(), "$0 ")
    textQuestion!!.text = word
    startTimer()

  }

  private fun pickRandomWord(): String? {
    var line: String? = null
    try {
      val reader = BufferedReader(InputStreamReader(assets.open("words.txt")))
      val random = (Math.random() * 40000).toInt()
      var i = 0
      while (reader.readLine().also { line = it } != null) {
        if (i == random) {
          return line
        }
        i++
      }
    } catch (e: FileNotFoundException) {
      e.printStackTrace()
    }
    return line
  }

  private fun scramble(random: Random, inputString: String): String {
    // Convert string into a simple char array:
    val a = inputString.toCharArray()

    // Scramble the letters using the standard Fisher-Yates shuffle,
    for (i in a.indices) {
      val j = random.nextInt(a.size)
      // Swap letters
      val temp = a[i]
      a[i] = a[j]
      a[j] = temp
    }
    return String(a)
  }

  // Time section:
  fun startTimer() {
    timer = object : CountDownTimer(START_TIMER.toLong(), 1000) {
      override fun onTick(millisUntilFinished: Long) {
        timeLeft = millisUntilFinished / 1000
        updateTimeCounterText()
      }

      override fun onFinish() {
        timeOver()
      }
    }.start()
  }

  fun updateTimeCounterText() {
    txtLanguageTime?.setText("Bạn còn: $timeLeft giây")
  }

  private fun getTimeRes() {
    timeRes = 20 - timeLeft
    var totalTimeRes: Long = 0
    totalTimeRes += timeRes
  }

  // Notification section
  fun timeOver() {
    textQuestion!!.text = correctWord
    editAnswer!!.visibility = View.GONE
    txtLanguageCompleteNoti?.setText("Hết giờ!")
    txtLanguageCompleteNoti?.setVisibility(View.VISIBLE)
    txtLanguageCompleteGame?.setVisibility(View.VISIBLE)
    txtLanguageCompleteGame?.setText("Điểm của bạn là: $score")
    submitButton?.setEnabled(false)

    val totalScore = getTotalScore()

    updateScoreToDatabase(totalScore)
  }

  // Score section
  private fun updateScore() {
    score += 200
    txtLanguageScore?.setText("Điểm: $score")
  }

  private fun updateScoreToDatabase(score: Int) {
    val account = GoogleSignIn.getLastSignedInAccount(this@SortingCharGameActivity)

    val user = userViewModel.getUserAsync(account?.id.hashCode()!!)

    if (user != null) {
      // Update score if it is higher than the current score
      if (score > user.sortingCharScore) {
        userViewModel.updateSortingCharScore(account?.id.hashCode(), score)
      }
    } else {
      Log.d(FindOperatorGameActivity.TAG, "user is null")
    }
  }

  private fun getTotalScore(): Int {
    totalScore = (score + bonusScore).toInt()
    return totalScore
  }

  // Submit button handle:
  fun Submit(view: View?) {
    val userInput = editAnswer!!.text.toString().lowercase().trim { it <= ' ' }
    Log.d("userInput", userInput)
    if (userInput == correctWord) {
      timer!!.cancel()
      getTimeRes()
      updateScore()
      editAnswer!!.text.clear()
      index = index + 1
      gameStart()
    } else {
      Toast.makeText(this@SortingCharGameActivity, "Câu trả lời Sai!", Toast.LENGTH_LONG).show()
    }
  }


}