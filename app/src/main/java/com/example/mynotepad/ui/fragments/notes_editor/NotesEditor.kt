package com.example.mynotepad.ui.fragments.notes_editor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import com.example.mynotepad.R
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mynotepad.model.Note
import com.example.mynotepad.databinding.FragmentNotesEditorBinding
import com.example.mynotepad.model.NoteArg
import com.example.mynotepad.ui.fragments.notes_list.viewModel.NoteViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotesEditor : Fragment() {
    private lateinit var binding: FragmentNotesEditorBinding
    private val viewModel: NoteViewModel by viewModel<NoteViewModel>()
    private val args: NotesEditorArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesEditorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        loadNoteData()
        setupSaveAction()
    }

    private fun setupToolbar() {
        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_editor, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_save -> {
                        saveNote()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner)
    }

    private fun loadNoteData() {
        args.noteArg?.let { noteArg: NoteArg ->
            binding.editTitle.setText(noteArg.title)
            binding.editContent.setText(noteArg.content)
        }
    }

    private fun setupSaveAction() {
        binding.editContent.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                saveNote()
                true
            } else false
        }
    }

    private fun saveNote() {
        val title = binding.editTitle.text.toString().trim()
        val content = binding.editContent.text.toString().trim()

        if (title.isEmpty() && content.isEmpty()) {
            return
        }
        args.noteArg?.let { noteArg: NoteArg ->
            viewModel.updateNote(Note(noteArg.noteId, title, content))
        } ?: viewModel.addNote(title, content)

        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}