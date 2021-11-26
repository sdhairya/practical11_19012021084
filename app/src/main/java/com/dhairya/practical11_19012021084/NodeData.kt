package com.dhairya.practical11_19012021084

class NodeData {

    companion object {
        const val DATABASE_NAME = "NotesDatabase"
        const val DATABASE_VERSION = 1
        const val TABLE_NOTES = "NotesTable"

        const val KEY_ID = "_id"
        const val KEY_NOTES_TITLE = "notesTitle"
        const val KEY_NOTES_SUBTITLE = "notesSubTitle"
        const val KEY_NOTES_DESCRIPTION = "notesDescription"
        const val KEY_NOTES_MODIFIED_TIME = "notesModifiedTime"
        const val KEY_NOTES_IS_REMINDER = "isReminder"
        const val KEY_NOTES_TIME_STAMP = "timeStamp"

        const val CREATE_NOTE_TABLE =
            "CREATE TABLE $TABLE_NOTES (" +
                    "$KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$KEY_NOTES_TITLE TEXT, " +
                    "$KEY_NOTES_SUBTITLE TEXT, " +
                    "$KEY_NOTES_DESCRIPTION TEXT, " +
                    "$KEY_NOTES_MODIFIED_TIME TEXT, " +
                    "$KEY_NOTES_IS_REMINDER INTEGER, " +
                    "$KEY_NOTES_TIME_STAMP TEXT);"

        const val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NOTES"
    }
}