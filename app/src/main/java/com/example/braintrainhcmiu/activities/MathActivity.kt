package com.example.braintrainhcmiu.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.lifecycle.asFlow
import androidx.lifecycle.lifecycleScope
import com.example.braintrainhcmiu.BrainTrainApplication
import com.example.braintrainhcmiu.DAO.CompareMathGameDAO
import com.example.braintrainhcmiu.R
import com.example.braintrainhcmiu.database.AppDatabase
import com.example.braintrainhcmiu.fragments.HomeFragment
import com.example.braintrainhcmiu.models.CompareMathGameViewModel
import com.example.braintrainhcmiu.models.CompareMathGameViewModelFactory
import com.example.braintrainhcmiu.models.UserViewModel
import com.example.braintrainhcmiu.models.UserViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow

public class MathActivity : AppCompatActivity() {
  private lateinit var findOperatorScore: TextView
  private lateinit var compareScore: TextView
  var findOperatorCardView: CardView? = null
  var compareCardView: CardView? = null
  var findOperatorCompleted: ImageView? = null
  var compareCompleted: ImageView? = null

  var findOperatorGuideButton: AppCompatButton? = null
  var compareGuideButton: AppCompatButton? = null
  var sharedPreferences: SharedPreferences? = null
  var gameOneGuide: String? = null
  var gameTwoGuide: String? = null

  private val userViewModel: UserViewModel by viewModels {
    UserViewModelFactory((application as BrainTrainApplication).userRepository)
  }

  companion object {
    private const val TAG = "MathActivity"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_math)
    findOperatorCardView = findViewById(R.id.findOperatorCardView)
    findOperatorScore = findViewById(R.id.findOperatorScore)
    compareScore = findViewById(R.id.compareScore)
    compareCardView = findViewById(R.id.compareCardView)
    findOperatorCompleted = findViewById(R.id.findOperatorCompleted)
    compareCompleted = findViewById(R.id.compareComplete)
    findOperatorGuideButton = findViewById(R.id.findOperatorGuideButton)
    compareGuideButton = findViewById(R.id.compareGuideButton)
    sharedPreferences = getSharedPreferences("guideButton", MODE_PRIVATE)
    val editor = sharedPreferences?.edit()
    gameOneGuide = sharedPreferences?.getString("gameOneGuideMath", "")
    gameTwoGuide = sharedPreferences?.getString("gameTwoGuideMath", "")
    findOperatorGuideButton?.setVisibility(if (gameOneGuide!!.isEmpty()) View.VISIBLE else View.INVISIBLE)
    compareGuideButton?.setVisibility(if (gameTwoGuide!!.isEmpty()) View.VISIBLE else View.INVISIBLE)

    val account = GoogleSignIn.getLastSignedInAccount(this)

    val userId = account!!.id.hashCode()

    val compareScore: Int = userViewModel.getUserAsync(userId).compareScore
    val findOperatorScore: Int = userViewModel.getUserAsync(userId).findOperatorScore

    this.compareScore.text = "Điểm của bạn: $compareScore"
    this.findOperatorScore.text = "Điểm của bạn: $findOperatorScore"

    compareCardView?.setOnClickListener(View.OnClickListener {
      val intent = Intent(this@MathActivity, CompareGameActivity::class.java)
      startActivity(intent)
    })

    findOperatorCardView?.setOnClickListener(View.OnClickListener {
      val intent = Intent(this@MathActivity, FindOperatorLevelSelectActivity::class.java)
      startActivity(intent)
    })

    compareCardView?.setOnLongClickListener(View.OnLongClickListener {
      compareGuideButton?.setVisibility(View.VISIBLE)
      editor?.putString("gameOneGuideMath", "")
      editor?.apply()
      false
    })

    findOperatorCardView?.setOnLongClickListener(View.OnLongClickListener {
      findOperatorGuideButton?.setVisibility(View.VISIBLE)
      editor?.putString("gameTwoGuideMath", "")
      editor?.apply()
      false
    })

    compareGuideButton?.setOnClickListener(View.OnClickListener {
      val alert = AlertDialog.Builder(this@MathActivity)
      alert.setTitle("Hướng Dẫn")
      alert.setMessage(
        """
              Trò chơi mô phỏng cảnh mua sắm. Nhiệm vụ là chọn sản phẩm có chi phí thấp hơn để mua
              
              Giá của sản phẩm được thể hiện bằng các biểu thức toán học bao gồm các phép tính đơn giản như cộng, trừ, nhân và chia
              
              Người chơi phải tính giá của các sản phẩm rồi chạm vào sản phẩm có giá trị thấp nhất
              """.trimIndent()
      )
      alert.setCancelable(false)
      alert.setNegativeButton(
        "Không hiện lại"
      ) { dialog, which ->
        dialog.cancel()
        compareGuideButton?.setVisibility(View.GONE)
        editor?.putString("gameOneGuideMath", "notAppear")
        editor?.apply()
      }
      alert.setPositiveButton(
        "Đã Hiểu"
      ) { dialog, which -> dialog.cancel() }
      val alertDialog = alert.create()
      alertDialog.show()
    })

    findOperatorGuideButton?.setOnClickListener(View.OnClickListener {
      val alert = AlertDialog.Builder(this@MathActivity)
      alert.setTitle("Hướng Dẫn")
      alert.setMessage("Nhiệm vụ của người chơi là tìm hai số có tổng là bội số của chục, bội số của trăm hoặc bội số của nghìn")
      alert.setCancelable(false)
      alert.setNegativeButton(
        "Không hiện lại"
      ) { dialog, which ->
        dialog.cancel()
        findOperatorGuideButton?.setVisibility(View.GONE)
        editor?.putString("gameThreeGuideMath", "notAppear")
        editor?.apply()
      }
      alert.setPositiveButton(
        "Đã Hiểu"
      ) { dialog, which -> dialog.cancel() }
      val alertDialog = alert.create()
      alertDialog.show()
    })
  }
}