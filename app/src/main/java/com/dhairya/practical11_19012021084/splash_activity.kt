package com.dhairya.practical11_19012021084

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class splash_activity : AppCompatActivity(), Animation.AnimationListener {

    lateinit var guni: AnimationDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setStatusBarTransparent()
        supportActionBar?.hide()

        val tap = findViewById<TextView>(R.id.textView3)

        val imgview = findViewById<ImageView>(R.id.image)
        imgview.setBackgroundResource(R.drawable.animation)
        guni = imgview.background as AnimationDrawable
        guni.start()

        Handler().postDelayed({
            val animationScale = AnimationUtils.loadAnimation(this, R.anim.rotate)
            imgview.startAnimation(animationScale)
        }, 2500)

        Handler().postDelayed({
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
            }
        }, 7000)


        tap.setOnClickListener {
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
            }
            finish()
        }


    }

    override fun onAnimationStart(animation: Animation?) {
    }

    override fun onAnimationEnd(animation: Animation?) {

    }

    override fun onAnimationRepeat(animation: Animation?) {
        TODO("Not yet implemented")
    }

    private fun setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT in 19..20) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
            }
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val winParameters = window.attributes
        if (on) {
            winParameters.flags = winParameters.flags or bits
        } else {
            winParameters.flags = winParameters.flags and bits.inv()
        }
        window.attributes = winParameters
    }


}
