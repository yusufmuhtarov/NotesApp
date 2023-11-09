package com.example.myapplication

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

private const val SHARED_PREFENCE_KEY = "ibe"
private const val NOTES_SHARED_PREF = "ibe"
class NotesDataBase (
    private val context:Context,){
    private val sharedPreferences = context.getSharedPreferences(
        SHARED_PREFENCE_KEY,Context.MODE_PRIVATE
    )


    fun getAllNotes(): List<NotesModel>{
        val gson = Gson()
        val json = sharedPreferences.getString(NOTES_SHARED_PREF,null)
        val type : Type = object : TypeToken<ArrayList<NotesModel?>?>() {}.type
        val noteLIst = gson.fromJson<ArrayList<NotesModel>>(json,type)
        return noteLIst?: emptyList()
    }

    fun saveNotes(notesModel: NotesModel){
        val notes = getAllNotes().toMutableList()
        notes.add(0,notesModel)
        val notesGson = Gson().toJson(notes)
        sharedPreferences
            .edit().putString(NOTES_SHARED_PREF,notesGson)
            .apply()
    }

    fun deleteAllNotes(){
        sharedPreferences.edit().clear().apply()

    }

    fun deleteByIndex(index: Int) {
        Log.d("DDD", "Before: ${getAllNotes()}")
        val getAllNotes = getAllNotes().toMutableList()
        if (index in 0 until getAllNotes().size) {
            getAllNotes.removeAt(index)
            val noteGsonString = Gson().toJson(getAllNotes)
            sharedPreferences.edit().putString(NOTES_SHARED_PREF, noteGsonString).apply()
        }
        Log.d("DDD", "After: ${getAllNotes()}")
    }
}

