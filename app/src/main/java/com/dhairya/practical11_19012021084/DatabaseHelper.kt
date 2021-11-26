package com.dhairya.practical11_19012021084

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.dhairya.practical11_19012021084.NodeData.Companion.CREATE_NOTE_TABLE
import com.dhairya.practical11_19012021084.NodeData.Companion.DATABASE_NAME
import com.dhairya.practical11_19012021084.NodeData.Companion.DATABASE_VERSION
import com.dhairya.practical11_19012021084.NodeData.Companion.DROP_TABLE
import com.dhairya.practical11_19012021084.NodeData.Companion.KEY_ID
import com.dhairya.practical11_19012021084.NodeData.Companion.KEY_NOTES_DESCRIPTION
import com.dhairya.practical11_19012021084.NodeData.Companion.KEY_NOTES_IS_REMINDER
import com.dhairya.practical11_19012021084.NodeData.Companion.KEY_NOTES_MODIFIED_TIME
import com.dhairya.practical11_19012021084.NodeData.Companion.KEY_NOTES_SUBTITLE
import com.dhairya.practical11_19012021084.NodeData.Companion.KEY_NOTES_TIME_STAMP
import com.dhairya.practical11_19012021084.NodeData.Companion.KEY_NOTES_TITLE
import com.dhairya.practical11_19012021084.NodeData.Companion.TABLE_NOTES
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DatabaseHelper (
    context: Context
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_NOTE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    private fun getValues(note: Notes): ContentValues {
        val values = ContentValues()

        val timeFormat = SimpleDateFormat("MMM, dd yyyy hh:mm:ss a", Locale.ENGLISH)
        val time = timeFormat.format(note.modifiedTime.timeInMillis)
        val time1 = timeFormat.format(note.timeStamp)

        values.apply {
            try {
                put(KEY_NOTES_TITLE, note.title)
                put(KEY_NOTES_SUBTITLE, note.subTitle)
                put(KEY_NOTES_DESCRIPTION, note.description)
                put(KEY_NOTES_MODIFIED_TIME, time)
                put(KEY_NOTES_IS_REMINDER, note.isReminder)
                put(KEY_NOTES_TIME_STAMP, time1)
            } catch (e: SQLiteException) {
                Log.d("DatabaseFragment", e.message.toString())
            }
        }
        return values
    }

    //Method to insert Data
    fun insetNote(note: Notes): Long {

        val db = this.writableDatabase

        var success: Long? = 0
        try {
            success = db?.insert(TABLE_NOTES, null, getValues(note))
        } catch (e: SQLiteException) {
            Log.d("DatabaseHandler", e.message.toString())
        }

        db.close()

        return success!!
    }

    fun updateNote(note: Notes): Int {
        val db = this.writableDatabase
        val result = db.update(
            TABLE_NOTES, getValues(note), KEY_ID +
                    " = ? ", arrayOf(note.id.toString())
        )
        db.close()

        return result
    }

    fun deleteNote(note: Notes): Int {
        val db = this.writableDatabase
        val result = db.delete(
            TABLE_NOTES, "$KEY_ID = ? ",
            arrayOf(note.id.toString())
        )
        db.close()
        return result
    }

    //Method to Read Data
    @SuppressLint("Range")
    fun getAllNotes(): ArrayList<Notes> {
        val notesList: ArrayList<Notes> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_NOTES"
        val db = this.readableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.d("DatabaseHandler: ", e.message.toString())
            return ArrayList()
        }
        var id: Int
        var noteTitle: String
        var noteSubTitle: String
        var noteDescription: String
        var noteModifiedTime: String
        var noteIsReminder: Int
        var timeStamp: String
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                noteTitle = cursor.getString(cursor.getColumnIndex(KEY_NOTES_TITLE))
                noteSubTitle = cursor.getString(cursor.getColumnIndex(KEY_NOTES_SUBTITLE))
                noteDescription = cursor.getString(cursor.getColumnIndex(KEY_NOTES_DESCRIPTION))
                noteModifiedTime = cursor.getString(cursor.getColumnIndex(KEY_NOTES_MODIFIED_TIME))
                noteIsReminder = cursor.getInt(cursor.getColumnIndex(KEY_NOTES_IS_REMINDER))
                timeStamp = cursor.getString(cursor.getColumnIndex(KEY_NOTES_TIME_STAMP))

                val cal: Calendar = Calendar.getInstance()
                val sdf = SimpleDateFormat("MMM, dd yyyy hh:mm:ss a", Locale.ENGLISH)
                cal.time = sdf.parse(noteModifiedTime)!!

                val cal1: Calendar = Calendar.getInstance()
                cal1.time = sdf.parse(timeStamp)!!

                val note = Notes(
                    id = id,
                    title = noteTitle,
                    subTitle = noteSubTitle,
                    description = noteDescription,
                    modifiedTime = cal,
                    isReminder = noteIsReminder != 0,
                    timeStamp = cal1.timeInMillis
                )
                notesList.add(note)

            } while (cursor.moveToNext())
        }
        cursor.close()
        return notesList
    }

}