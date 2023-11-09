package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.NoteItemBinding

class NotesAdapter(
    private val onDeleteNoteClick: (index: Int) -> Unit,
) : RecyclerView.Adapter<NotesAdapter.NoteAppViewHolder>() {


    private val noteList = mutableListOf<NotesModel>()

    fun updateList(NoteList: List<NotesModel>) {
        noteList.clear()
        noteList.addAll(NoteList)
        notifyDataSetChanged()
    }

    inner class NoteAppViewHolder(
        private val binding: NoteItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(notesModel: NotesModel) {
            binding.tvNoteTitle.text = notesModel.noteTitle
            binding.tvNoteOverView.text = notesModel.noteDescription
            binding.deleteNoteBtn.setOnClickListener {
                onDeleteNoteClick.invoke(
                    noteList.indexOf(notesModel)
                )
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteAppViewHolder {
        val binding = NoteItemBinding.bind(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.note_item, parent, false)
        )
        return NoteAppViewHolder(binding)
    }

    override fun getItemCount(): Int = noteList.size

    override fun onBindViewHolder(
        holder: NoteAppViewHolder,
        position: Int)
    {
        holder.bind(noteList[position])
    }
}
