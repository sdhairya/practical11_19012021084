package com.dhairya.practical11_19012021084

import android.R.attr
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import com.dhairya.practical11_19012021084.logininfo.Companion.city
import com.dhairya.practical11_19012021084.logininfo.Companion.confirm_pass
import com.dhairya.practical11_19012021084.logininfo.Companion.email
import com.dhairya.practical11_19012021084.logininfo.Companion.fullname
import com.dhairya.practical11_19012021084.logininfo.Companion.password
import com.dhairya.practical11_19012021084.logininfo.Companion.phone
import com.google.android.material.textfield.TextInputEditText

class signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val login=findViewById<TextView>(R.id.login)
        val signup_button=findViewById<Button>(R.id.signup_button1)
        val fullname_signup=findViewById<TextInputEditText>(R.id.fullname)
        val phone_signup=findViewById<TextInputEditText>(R.id.phone)
        val email_signup=findViewById<TextInputEditText>(R.id.email)
        val city_signup=findViewById<TextInputEditText>(R.id.city)
        val password_signup=findViewById<TextInputEditText>(R.id.password)
        val confirm_pass_signup=findViewById<TextInputEditText>(R.id.confirm_pass)
        val sharedPreferences: SharedPreferences = this.getSharedPreferences("myprefs", MODE_PRIVATE)

        login.setOnClickListener({
            Intent(this,MainActivity::class.java).apply {
                startActivity(this)
            }
        })
        signup_button.setOnClickListener({

            fullname=fullname_signup.text.toString()
            phone=phone_signup.text.toString()
            email=email_signup.text.toString()
            city=city_signup.text.toString()
            password=password_signup.text.toString()
            confirm_pass=confirm_pass_signup.text.toString()
            if(password== confirm_pass)
            {
                Intent(this,MainActivity::class.java).apply {
                    startActivity(this)
                }
            }
            else{

                Toast.makeText(applicationContext,"Password and Confirm Password does not match",
                    Toast.LENGTH_LONG).show()
            }

            val editor:SharedPreferences.Editor =  sharedPreferences.edit()
            editor.putString("key_name", fullname)
            editor.putString("key_phone", phone)
            editor.putString("key_email", email)
            editor.putString("key_city", city)
            editor.putString("key_pass", password)
            editor.commit();

        })
        setStatusBarTransparent()
        supportActionBar?.hide()
    }
    private fun setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT in 19..20){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
            }
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    private fun setWindowFlag(bits: Int, on: Boolean){
        val winParameters=window.attributes
        if(on) {
            winParameters.flags = winParameters.flags or bits
        }else{
            winParameters.flags=winParameters.flags and bits.inv()
        }
        window.attributes=winParameters
    }
}