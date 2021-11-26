package com.dhairya.practical11_19012021084

import android.app.Dialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import java.util.*


class NotesActivity : AppCompatActivity() {
    lateinit var gnu: AnimationDrawable
    private lateinit var databaseHelper: DatabaseHelper


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes)
        createNotificationChannel()

        val guniimg = findViewById<ImageView>(R.id.image_gnu)
        guniimg.setBackgroundResource(R.drawable.animation_gnu)
        gnu = guniimg.background as AnimationDrawable
        gnu.start()

        var bottomnavview = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomnavview.selectedItemId = R.id.notes

        databaseHelper = DatabaseHelper(this)
        val listItems = databaseHelper.getAllNotes()


        val adapter = List_BaseAdapter(this, listItems)
        val lvNotes = findViewById<ListView>(R.id.listview)
        lvNotes.adapter = adapter

        val fabAddNote = findViewById<FloatingActionButton>(R.id.add_notes)
        fabAddNote.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.add_note_dialog)
            val timePicker = dialog.findViewById<TimePicker>(R.id.time_picker)
            val reminderSwitch = dialog.findViewById<SwitchMaterial>(R.id.switch_reminder)
            val noteTitle = dialog.findViewById<TextInputEditText>(R.id.et_note_title)
            val noteSubTitle = dialog.findViewById<TextInputEditText>(R.id.et_note_sub_title)
            val noteDescription = dialog.findViewById<TextInputEditText>(R.id.et_note_description)
            val btnOk = dialog.findViewById<TextView>(R.id.btn_ok)

            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val date = cal.get(Calendar.DATE)

            btnOk.setOnClickListener {
                if (noteTitle.text.toString().isEmpty() or noteSubTitle.text.toString()
                        .isEmpty() or noteDescription.text.toString().isEmpty()
                ) {
                    Toast.makeText(
                        this,
                        "Please enter all fields\nAll fields are required",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    cal.set(year, month, date, timePicker.hour, timePicker.minute, 0)
                    val note = Notes(
                        title = noteTitle.text.toString().trim(),
                        subTitle = noteSubTitle.text.toString().trim(),
                        description = noteDescription.text.toString().trim(),
                        modifiedTime = cal,
                        isReminder = reminderSwitch.isChecked
                    )
                    listItems.add(note)
                    databaseHelper.insetNote(note = note)
                    Notes.setReminder(this, note)
                    adapter.notifyDataSetChanged()
                    dialog.dismiss()
                }
            }
            dialog.show()
        }


        bottomnavview.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.notes -> {
                    Intent(this, NotesActivity::class.java).apply {
                        startActivity(this)
                    }
                    return@setOnItemSelectedListener true
                }
                else -> {
                    Intent(this, dashboard::class.java).apply {
                        startActivity(this)
                    }
                    return@setOnItemSelectedListener true

                }
            }
        }
    }


    private fun createNotificationChannel() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val description = "Channel for sending notes notification"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, importance)
            channel.description = description
            channel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            channel.enableVibration(true)
            notificationManager.createNotificationChannel(channel)
        }
    }

}

const val channelId = "notesChannel"
const val channelName = "Notes Channel"