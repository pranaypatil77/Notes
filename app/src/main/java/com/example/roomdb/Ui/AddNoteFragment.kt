package com.example.roomdb.Ui

import android.content.DialogInterface
import android.os.AsyncTask
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.roomdb.Db.Notes
import com.example.roomdb.Db.NotesDao
import com.example.roomdb.Db.NotesDb

import com.example.roomdb.R
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class AddNoteFragment : BaseFragment() {

    private var note : Notes? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            note = AddNoteFragmentArgs.fromBundle(it).note
            etTitle.setText(note?.title)
            etDetails.setText(note?.notes)
        }

        btnSave.setOnClickListener {view ->
            val title = etTitle.text.toString()
            val desr = etDetails.text.toString()
            if (title.isEmpty()){
                etTitle.error = "Title is Requied"
                etTitle.requestFocus()
                return@setOnClickListener
            }
            if (desr.isEmpty()){
                etDetails.error = "Description is Requied"
                etDetails.requestFocus()
                return@setOnClickListener
            }

            launch {

                context?.let {
                    val Mnote = Notes(title,desr)
                    if (note == null){
                        NotesDb(it).getNotesDao().addNote(Mnote)
                        it.toast("Saved Note..")
                    }else{
                        Mnote.id = note!!.id
                        NotesDb(it).getNotesDao().updateNotes(Mnote)
                        it.toast("Updated Note..")
                    }


                    val action = AddNoteFragmentDirections.saveNote()
                    Navigation.findNavController(view).navigate(action)
                }
            }
            val note = Notes(title,desr)
//            saveNote(note)
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.del -> if (note != null){
                deleteNote()
            }else{
                context?.toast("No note selected..")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun deleteNote() {
        val alertDialog = AlertDialog.Builder(context!!)
        alertDialog.setTitle("Delete Note")
        alertDialog.setMessage("Are You Sure ?")
        alertDialog.setPositiveButton("Yes"){ dialog: DialogInterface?, which: Int ->
            launch {
                context?.let {
                    NotesDb(it).getNotesDao().deleteNotes(note!!)
                    val action = AddNoteFragmentDirections.saveNote()
                    Navigation.findNavController(view!!).navigate(action)
                }
            }
        }
        alertDialog.setNegativeButton("Cancel"){dialog: DialogInterface?, which: Int ->
            context?.toast("Cancel")
        }
        alertDialog.create().show()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu,menu)
    }
//    private fun saveNote(notes: Notes){
//        class SaveNote : AsyncTask<Void,Void,Void>(){
//            override fun doInBackground(vararg params: Void?): Void? {
//                NotesDb(context!!.applicationContext).getNotesDao().addNote(notes)
//                return null
//
//            }
//
//            override fun onPostExecute(result: Void?) {
//                super.onPostExecute(result)
//                Toast.makeText(context?.applicationContext,"Note Saved",Toast.LENGTH_SHORT).show()
//            }
//        }
//        SaveNote().execute()
//    }

}
