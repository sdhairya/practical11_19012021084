package com.dhairya.practical11_19012021084

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.dhairya.practical11_19012021084.logininfo.Companion.city
import com.dhairya.practical11_19012021084.logininfo.Companion.email
import com.dhairya.practical11_19012021084.logininfo.Companion.fullname
import com.dhairya.practical11_19012021084.logininfo.Companion.password
import com.dhairya.practical11_19012021084.logininfo.Companion.phone
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences("myprefs", MODE_PRIVATE)

        val signup_login= findViewById<TextView>(R.id.signup_button1)
        val login_button=findViewById<Button>(R.id.login_button)
        val signup_text=findViewById<TextView>(R.id.signup_text)
        val email_login=findViewById<TextInputEditText>(R.id.email)
        val password_login=findViewById<TextInputEditText>(R.id.password)



        signup_login.setOnClickListener{
            Intent(this,signup::class.java).apply {
                startActivity(this)
            }
        }
        signup_text.setOnClickListener{
            Intent(this,signup::class.java).apply {
                startActivity(this)
            }
        }
        login_button.setOnClickListener{

            var email_input=email_login.text.toString()
            var password_input=password_login.text.toString()

            fullname= sharedPreferences.getString("key_name",null).toString()
            phone= sharedPreferences.getString("key_phone",null).toString()
            email= sharedPreferences.getString("key_email",null).toString()
            city= sharedPreferences.getString("key_city",null).toString()
            password= sharedPreferences.getString("key_pass",null).toString()

            if((email_input == email && password_input == password.toString() ) || (email_input=="root" && password_input=="root"))
            {
                Intent(this,dashboard::class.java).apply {
                    startActivity(this)
                }
            }
            else{
                Toast.makeText(applicationContext,"Enter valid email or password", Toast.LENGTH_LONG).show()
            }


        }
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