package com.dhairya.practical11_19012021084

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class NoteInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_info)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)



        val tvNoteTitle = findViewById<TextView>(R.id.tv_note_title)
        val tvNoteSubTitle = findViewById<TextView>(R.id.tv_note_sub_title)
        val tvNoteDescription = findViewById<TextView>(R.id.tv_note_description)
        val tvNoteTimeStamp = findViewById<TextView>(R.id.tv_note_time_stamp)
        val tvNoteReminderTime = findViewById<TextView>(R.id.tv_notes_reminder_time)

        val title = intent?.getStringExtra("title")
        val subTitle = intent?.getStringExtra("subTitle")
        val description = intent?.getStringExtra("description")
        val modifiedTime = intent?.getLongExtra("modifiedTime",0)
        val timeStamp = intent?.getLongExtra("timeStamp", 0)

        tvNoteTitle.text = title
        tvNoteSubTitle.text = subTitle
        tvNoteDescription.text = description
        val timeFormat = SimpleDateFormat("MMM, dd yyyy hh:mm:ss a", Locale.ENGLISH)
        tvNoteTimeStamp.text = timeFormat.format(timeStamp)
        val time =
            "Reminder at " + timeFormat.format(modifiedTime)
        tvNoteReminderTime.text = time
    }

    override fun onBackPressed() {
        Intent(this, NotesActivity::class.java).apply {
            startActivity(this)
        }
        super.onBackPressed()
    }
}

