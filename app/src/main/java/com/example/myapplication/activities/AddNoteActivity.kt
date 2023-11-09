package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.NotesDataBase
import com.example.myapplication.NotesModel
import com.example.myapplication.databinding.ActivityAddNoteBinding
import com.google.android.material.snackbar.Snackbar

class AddNoteActivity : AppCompatActivity() {
    private val binding: ActivityAddNoteBinding by lazy {
        ActivityAddNoteBinding.inflate(layoutInflater)
    }
    private val sharedPreferences: NotesDataBase by lazy {
        NotesDataBase(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.saveCard.setOnClickListener {
        saveNotes()
    }
        binding.backCard.setOnClickListener{
            finish()
        }
    }
    private fun saveNotes() = binding.apply{
        if (titleEt.text?.isNotEmpty()== true && overviewEt.text?.isNotEmpty() ==true){
            sharedPreferences.saveNotes(
                NotesModel(
                    noteTitle = titleEt.text.toString(),
                    noteDescription = overviewEt.text.toString()
                )
            )
            startActivity(Intent(this@AddNoteActivity,MainActivity::class.java))
        }
        else showToastMessage("Enter the fields")
    }

    private fun  showToastMessage(message: String){
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}