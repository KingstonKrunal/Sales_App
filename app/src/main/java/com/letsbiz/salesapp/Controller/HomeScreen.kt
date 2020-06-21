package com.letsbiz.salesapp.Controller

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.letsbiz.salesapp.Model.Callback
import com.letsbiz.salesapp.Model.UserRepository
import com.letsbiz.salesapp.R
import kotlin.collections.ArrayList

class HomeScreen : AppCompatActivity() {
    var mLogoutBtn: Button? = null
    var mViewProfileBtn: Button? = null
    var mAddFeedbackBtn: Button? = null
    var mViewFeedbackListBtn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        mAddFeedbackBtn = findViewById(R.id.add_feedback_btn)
        mViewFeedbackListBtn = findViewById(R.id.view_feedback_list_btn)
        mLogoutBtn = findViewById(R.id.log_out_btn)
        mViewProfileBtn = findViewById(R.id.view_profile_btn)

        mAddFeedbackBtn?.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@HomeScreen, AddFeedback::class.java))
        })

        mViewFeedbackListBtn?.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@HomeScreen, FeedbackList::class.java))
        })

        mViewProfileBtn?.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@HomeScreen, UserProfile::class.java))
        })

        mLogoutBtn?.setOnClickListener(View.OnClickListener {
            val sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@HomeScreen, LoginActivity::class.java))
            finish()
        })

    }
}