package com.android.firebasetest2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {


    /*override fun onClick(v: View) {
        val i = v.id
        when (i) {
            R.id.button_register -> createAccount(edittext_email.text.toString(), edittext_password.text.toString())
            R.id.buttontest -> {
                val toast= Toast.makeText(this, "Test", Toast.LENGTH_LONG).show()
            }
        }
    }*/

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val button_login = findViewById<Button>(R.id.button_login)
        val button_register = findViewById<Button>(R.id.button_register)
        val edittext_email = findViewById<EditText>(R.id.edittext_email)
        val edittext_password = findViewById<EditText>(R.id.edittext_password)


        auth = FirebaseAuth.getInstance()

        button_register.setOnClickListener{
            createAccount(edittext_email.text.toString(), edittext_password.text.toString())
        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
    }

    private fun createAccount(email: String, password: String) {
        Log.d(TAG, "createAccount:$email")
        if (!validateForm()) {
            return
        }

        showProgressDialog()

        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        val toast = Toast.makeText(this, "User : ${user!!.email} is created", Toast.LENGTH_LONG).show()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }

                    // [START_EXCLUDE]
                    hideProgressDialog()
                    // [END_EXCLUDE]
                }
        // [END create_user_with_email]
    }

    private fun signIn(email: String, password: String) {
        Log.d(TAG, "signIn:$email")
        if (!validateForm()) {
            return
        }

        showProgressDialog()

        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                    hideProgressDialog()
                    // [END_EXCLUDE]
                }
        // [END sign_in_with_email]
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = edittext_email.text.toString()
        if (TextUtils.isEmpty(email)) {
            val toast = Toast.makeText(this, "Empty Email", Toast.LENGTH_LONG).show()
            valid = false
        }

        val password = edittext_password.text.toString()
        if (TextUtils.isEmpty(password)) {
            val toast = Toast.makeText(this, "Empy Password", Toast.LENGTH_LONG).show()
            valid = false
        }

        return valid
    }

    companion object {
        private const val TAG = "EmailPassword"
    }

}
