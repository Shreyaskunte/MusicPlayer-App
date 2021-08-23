package com.example.musicplayerapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.musicplayerapplication.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    private lateinit var musicAdapter: MusicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
    }

    fun onclick(v: View){
        val btnRegLogin = v as Button
        btnRegLogin.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left)
        }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            currentUser.reload()
            if (currentUser.isEmailVerified) {
                    Toast.makeText(applicationContext,"Sign in success",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, SongsActivity::class.java))
                finish()
            }
            else{
                Toast.makeText(applicationContext,"Please verify your Email address",Toast.LENGTH_SHORT).show()
            }
        }
        else
        {
            Toast.makeText(applicationContext,"Sign in Failed",Toast.LENGTH_SHORT).show()
        }
    }

    fun onLogin(v: View){
        val loginButton = v as Button
        loginButton.setOnClickListener {
            doLogin()
        }
    }

    private fun doLogin() {

        val e = findViewById<View>(R.id.email) as EditText
        val p = findViewById<View>(R.id.password) as EditText

        if (e.text.toString().isEmpty()){
            e.error = "Please Enter Email"
            e.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(e.text.toString()).matches()){
            e.error = "Please Enter Email"
            e.requestFocus()
            return
        }

        if (p.text.toString().isEmpty()){
            p.error = "Please Enter password"
            p.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(e.text.toString(), p.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    startActivity((Intent(this,SongsActivity::class.java)))
                    updateUI(user)
                } else {
                    updateUI(null)
                }
            }
    }

}