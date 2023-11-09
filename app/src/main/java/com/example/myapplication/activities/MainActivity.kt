package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.NotesAdapter
import com.example.myapplication.NotesDataBase
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val sharedPreferences: NotesDataBase by lazy {
        NotesDataBase(this)
    }
    private val adapter: NotesAdapter by lazy {
        NotesAdapter(
            onDeleteNoteClick = { index ->
                sharedPreferences.deleteByIndex(index)
                    setUpViewsAndAdapter()
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpViewsAndAdapter()
        setupClickListener()
    }


    private fun setupClickListener() = binding.apply {
        addNoteBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddNoteActivity::class.java))
        }
        deleteCard.setOnClickListener {
            showConfirmDeleteDialog()
        }
    }

    private fun setUpViewsAndAdapter() {
        val listOfNotes = sharedPreferences.getAllNotes()
        if (listOfNotes.isNotEmpty()) {
            binding.firstNoteIv.visibility = View.GONE
            binding.mainRv.visibility = View.VISIBLE
            adapter.updateList(listOfNotes)
            binding.mainRv.adapter = adapter
        } else{
            binding.firstNoteIv.visibility = View.VISIBLE
            binding.mainRv.visibility = View.GONE
        }
    }

    private fun showConfirmDeleteDialog() {
        val alertDialog = MaterialAlertDialogBuilder(this)
        alertDialog.setMessage("Do you really want to delete all notes")
        alertDialog.setPositiveButton("yes") { dialog, _ ->
            deleteAllSavedNotes()
            dialog.dismiss()
        }
        alertDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()

        }

        alertDialog.create().show()
    }

    private fun deleteAllSavedNotes() {
        sharedPreferences.deleteAllNotes()
        adapter.updateList(emptyList())
        binding.mainRv.visibility = View.GONE
        binding.firstNoteIv.visibility = View.VISIBLE
    }


}