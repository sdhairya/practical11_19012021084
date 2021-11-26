package com.dhairya.practical11_19012021084

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList



class List_BaseAdapter (var context: Context, var noteList: ArrayList<Notes>) : BaseAdapter() {
    private val databaseHelper: DatabaseHelper = DatabaseHelper(context)

    override fun getCount(): Int {
        return noteList.size
    }

    override fun getItem(position: Int): Any {
        return noteList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)

        val noteTitle = view.findViewById<TextView>(R.id.note_title)
        val noteSubTitle = view.findViewById<TextView>(R.id.note_sub_title)
        val noteDescription = view.findViewById<TextView>(R.id.note_content)
        val noteTimeStamp = view.findViewById<TextView>(R.id.note_time_stamp)
        val timeFormat = SimpleDateFormat("MMM, dd yyyy hh:mm:ss a", Locale.ENGLISH)
        val time = timeFormat.format(noteList[position].timeStamp)

        noteTitle.text = noteList[position].title
        noteSubTitle.text = noteList[position].subTitle
        noteDescription.text = noteList[position].description
        noteTimeStamp.text = time

        val ivEditNote = view.findViewById<Button>(R.id.edit_note)
        val ivDeleteNote = view.findViewById<Button>(R.id.delete_note)

        ivEditNote.setOnClickListener {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.add_note_dialog)
            val tvTitle = dialog.findViewById<TextView>(R.id.tv_dialog_title)
            val timePicker = dialog.findViewById<TimePicker>(R.id.time_picker)
            val reminderSwitch = dialog.findViewById<SwitchMaterial>(R.id.switch_reminder)
            val etNoteTitle = dialog.findViewById<TextInputEditText>(R.id.et_note_title)
            val etNoteSubTitle = dialog.findViewById<TextInputEditText>(R.id.et_note_sub_title)
            val etNoteDescription = dialog.findViewById<TextInputEditText>(R.id.et_note_description)
            val btnOk = dialog.findViewById<TextView>(R.id.btn_ok)

            tvTitle.text = "Edit Note"
            etNoteTitle.setText(noteList[position].title)
            etNoteSubTitle.setText(noteList[position].subTitle)
            etNoteDescription.setText(noteList[position].description)
            reminderSwitch.isChecked = noteList[position].isReminder
            timePicker.hour = noteList[position].modifiedTime.get(Calendar.HOUR_OF_DAY)
            timePicker.minute = noteList[position].modifiedTime.get(Calendar.MINUTE)

            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val date = cal.get(Calendar.DATE)

            btnOk.setOnClickListener {
                if (etNoteTitle.text.toString().isEmpty() or etNoteSubTitle.text.toString()
                        .isEmpty() or etNoteDescription.text.toString().isEmpty()
                ) {
                    Toast.makeText(
                        context,
                        "Please enter all fields\nAll fields are required",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else {
                    cal.set(year, month, date, timePicker.hour, timePicker.minute, 0)
                    noteList[position].title = etNoteTitle.text.toString().trim()
                    noteList[position].subTitle = etNoteSubTitle.text.toString().trim()
                    noteList[position].description = etNoteDescription.text.toString().trim()
                    noteList[position].modifiedTime = cal
                    noteList[position].isReminder = reminderSwitch.isChecked
                    Notes.setReminder(context, noteList[position])
                    databaseHelper.updateNote(note = noteList[position])
                    notifyDataSetChanged()
                    dialog.dismiss()
                }
            }
            dialog.show()
        }
        ivDeleteNote.setOnClickListener {
            val note = noteList[position]
            noteList[position].isReminder = false
            Notes.setReminder(context, noteList[position])
            noteList.removeAt(position)
            databaseHelper.deleteNote(note = note)
            notifyDataSetChanged()
        }

        return view
    }
}

