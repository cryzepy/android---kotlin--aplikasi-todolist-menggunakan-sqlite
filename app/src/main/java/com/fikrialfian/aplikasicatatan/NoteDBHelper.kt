package com.fikrialfian.aplikasicatatan

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NoteDBHelper(context: Context) :
    SQLiteOpenHelper(context, DB_DATABASE_NAME, null, DB_DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $DB_TABLE_NAME ($DB_COL_ID INTEGER PRIMARY KEY, $DB_COL_NOTE TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $DB_TABLE_NAME")
        onCreate(db)
    }

    fun createNote(note: String) {
        val db = writableDatabase
        val value = ContentValues()
        value.put(DB_COL_NOTE, note)
        db.insert(DB_TABLE_NAME, null, value)
        db.close()
    }

    @SuppressLint("Range", "Recycle")
    fun readNotes(): List<NotesModel> {
        val db = readableDatabase

        val cursor = db.rawQuery("SELECT * FROM $DB_TABLE_NAME", null)
        cursor!!.moveToFirst()
        val result = mutableListOf<NotesModel>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(DB_COL_ID))
            val note = cursor.getString(cursor.getColumnIndex(DB_COL_NOTE))
            result.add(NotesModel(id, note))
        }
        db.close()
        return result.toList()
    }

    fun deleteNote(id: Int) {
        val db = writableDatabase
        db.delete(DB_TABLE_NAME, "$DB_COL_ID=$id", null)
        db.close()
    }

    companion object {
        val DB_DATABASE_NAME = "notesv1.db"
        val DB_DATABASE_VERSION = 1
        val DB_TABLE_NAME = "notes"
        val DB_COL_ID = "id"
        val DB_COL_NOTE = "note"
    }
}