package com.example.braintrainhcmiu.activities

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextUtils.split
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
import com.example.braintrainhcmiu.BrainTrainApplication
import com.example.braintrainhcmiu.R
import com.example.braintrainhcmiu.models.UserViewModel
import com.example.braintrainhcmiu.models.UserViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import java.util.Locale

public class ConjunctionGameActivity : AppCompatActivity() {
  private val START_TIMER = 60000
  private var userInput: String? = null
  private var topicWord: kotlin.String? = null
  var timer: CountDownTimer? = null
  var timeLeft = START_TIMER.toLong()
  var totalScore = 0
  var score: Int = 0
  var countWord: Int = 0
  var index: Int = 0
  var currentScore: Int = 0
  private var txtConjunctionWordCount: TextView? =
    null
  private var txtConjunctionWordTime: TextView? = null
  private var txtConjunctionWordScore: TextView? = null
  private var txtConjunctionWordQuestion: TextView? = null
  private var txtConjunctionWordNoti: TextView? = null
  private var txtConjunctionWordError: TextView? = null
  private var tryAgainButton: AppCompatButton? = null
  private var submitConjunctionWordButton: AppCompatButton? = null
  private var editConjunctionWordAnswer: EditText? = null

  private val userViewModel: UserViewModel by viewModels {
    UserViewModelFactory((application as BrainTrainApplication).userRepository)
  }

  companion object {
    private const val TAG = "ConjunctionGameActivity"
  }

  @Override
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_conjunctionword_game)

    txtConjunctionWordQuestion = findViewById<View>(R.id.txtConjunctionWordQuestion) as TextView
    txtConjunctionWordTime = findViewById<View>(R.id.txtConjunctionWordTime) as TextView
    txtConjunctionWordScore = findViewById<View>(R.id.txtConjunctionWordScore) as TextView
    txtConjunctionWordNoti = findViewById<View>(R.id.txtConjunctionWordNoti) as TextView
    txtConjunctionWordCount = findViewById<View>(R.id.txtConjunctionWordCount) as TextView
    editConjunctionWordAnswer = findViewById(R.id.editConjunctionWordAnswer)
    tryAgainButton = findViewById(R.id.tryAgainButton)
    submitConjunctionWordButton = findViewById(R.id.submitConjunctionWordButton)
    txtConjunctionWordError = findViewById(R.id.txtConjunctionWordError)

    gameStart();

  }

  @Override
  override fun onBackPressed() {
    super.onBackPressed()
    timer!!.cancel()

    startActivity(Intent(this@ConjunctionGameActivity, LanguageActivity::class.java))
    finish()
  }

  private fun gameStart() {
    timeLeft = START_TIMER.toLong()
    txtConjunctionWordScore!!.text = "Điểm: $score"
    tryAgainButton!!.visibility = View.GONE
    editConjunctionWordAnswer!!.visibility = View.VISIBLE
    txtConjunctionWordNoti!!.visibility = View.GONE
    txtConjunctionWordError!!.visibility = View.GONE
    tryAgainButton!!.visibility = View.VISIBLE
    txtConjunctionWordCount!!.text = "Số câu đúng:$countWord"

    topicWord = pickRandomWord();
    txtConjunctionWordQuestion?.text = topicWord + " "

    submitConjunctionWordButton?.isEnabled = true;

    submitConjunctionWordButton!!.setOnClickListener {
      submit(it)
    }

    editConjunctionWordAnswer!!.addTextChangedListener(object : TextWatcher {
      override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
      }

      override fun onTextChanged(s: kotlin.CharSequence?, start: Int, before: Int, count: Int) {
        var shouldSubmitButtonBeVisible = s.toString().split("\\s".toRegex()).dropLastWhile { it.isEmpty() }
          .toTypedArray().size > 1

        submitConjunctionWordButton!!.isEnabled = shouldSubmitButtonBeVisible
      }

      override fun afterTextChanged(p0: Editable?) {
      }

    })

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

  open fun startTimer() {
    timer = object : CountDownTimer(timeLeft, 1000) {
      override fun onTick(millisUntilFinished: Long) {
        timeLeft = millisUntilFinished / 1000
        updateCountDownText()
      }

      override fun onFinish() {
        timeLeft = 0
        updateCountDownText()
        tryAgainButton!!.visibility = View.VISIBLE
        editConjunctionWordAnswer!!.visibility = View.GONE
        txtConjunctionWordNoti!!.visibility = View.VISIBLE
        txtConjunctionWordError!!.visibility = View.VISIBLE
        txtConjunctionWordError!!.text = "Điểm của bạn là: $score"
        submitConjunctionWordButton?.isEnabled = false;
        tryAgainButton!!.setOnClickListener {
          tryAgain(it)
        }

        updateScoreToDatabase(score)
      }
    }.start()

  }

  fun updateCountDownText() {
    txtConjunctionWordTime?.setText("Bạn còn: $timeLeft giây")
  }

  private fun updateScore() {
    score += 200
    txtConjunctionWordScore!!.text = "Điểm: $score"
  }

  private fun updateScoreToDatabase(score: Int) {
    val account = GoogleSignIn.getLastSignedInAccount(this@ConjunctionGameActivity)

    Log.d(TAG, "account: ${account?.id.hashCode()}")

    val user = userViewModel.getUserAsync(account?.id.hashCode()!!)

    Log.d(TAG, "user: $user")

    if (user != null) {
      // Update score if it is higher than the current score
      if (score > user.conjunctionScore) {
        userViewModel.updateConjunctionScore(account?.id.hashCode(), score)
      }
    } else {
      Log.d(TAG, "user is null")
    }
  }

  @Throws(IOException::class)
  fun submit(view: View?) {
    userInput = editConjunctionWordAnswer!!.text.toString()
    if (spellingCheck(userInput!!)) {
      val words = userInput!!.split("\\s".toRegex()).dropLastWhile { it.isEmpty() }
        .toTypedArray()
      val secondWord = words[1]
      topicWord = findNextWord(secondWord!!)
      Log.d(TAG, "nextWord: " + topicWord)
      txtConjunctionWordQuestion!!.text = topicWord
      editConjunctionWordAnswer!!.text.clear()
      countWord++
      txtConjunctionWordCount!!.text = "Số câu đúng:$countWord"
      updateScore()


    } else {
      Toast.makeText(this@ConjunctionGameActivity, "Câu trả lời Sai!", Toast.LENGTH_LONG).show()
    }
  }

  @Throws(IOException::class)
  fun spellingCheck(sb: String): Boolean {

    var sb = sb.lowercase(Locale.getDefault()).split("\\s".toRegex()).dropLastWhile { it.isEmpty() }
      .toTypedArray()[0]

    Log.d(TAG, "sb: " + sb)
    Log.d(TAG, "topicWord: " + topicWord)

    if (!topicWord!!.contains(sb)) {
      return false
    }

    return true
  }

  private fun findNextWord(topicWord: String): String? {
    var topicWord = topicWord
    topicWord = topicWord.lowercase(Locale.getDefault())

    Log.d(TAG, "topicWord: " + topicWord)

    var line: String? = null
    try {
      val reader = BufferedReader(InputStreamReader(assets.open("words.txt")))
      while (reader.readLine().also { line = it } != null) {
        if (line!!.startsWith(topicWord)) {
          return line
        }
      }
    } catch (e: FileNotFoundException) {
      e.printStackTrace()
    }
    return line
  }

  fun tryAgain(view: View?) {
    countWord = 0
    score = 0
    editConjunctionWordAnswer!!.text.clear()

    // clear timer
    timer?.cancel()

    // Reset visibility of views
    txtConjunctionWordNoti!!.visibility = View.GONE
    txtConjunctionWordError!!.visibility = View.GONE

    gameStart()

  }
}


