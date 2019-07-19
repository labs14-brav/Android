package com.thadocizn.brav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_landing.*

class LandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if (user != null) {
            textView.text = user.email
        }
    }
}