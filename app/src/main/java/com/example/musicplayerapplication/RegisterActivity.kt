package com.example.musicplayerapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()

        val btnLogRegister = findViewById<Button>(R.id.btnLogRegister)
        btnLogRegister.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
        }

        val registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {
            signUpUser()
        }
    }

    private fun signUpUser(){
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

        auth.createUserWithEmailAndPassword(e.text.toString(), p.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser? = auth.currentUser
                    user!!.sendEmailVerification()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(applicationContext, "Sign Up Success.",
                                    Toast.LENGTH_LONG).show()
                                startActivity(Intent(this,MainActivity::class.java))
                                finish()
                            }
                        }

                } else {
                    Toast.makeText(applicationContext, "Sign Up failed. Try again after sometime.",
                        Toast.LENGTH_LONG).show()
                }
            }
    }
}