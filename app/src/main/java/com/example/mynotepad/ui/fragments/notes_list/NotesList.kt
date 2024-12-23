package com.example.mynotepad.ui.fragments.notes_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynotepad.databinding.FragmentNotesListBinding
import com.example.mynotepad.domain.NoteResult
import com.example.mynotepad.model.NoteArg
import com.example.mynotepad.ui.fragments.notes_list.adapter.NoteAdapter
import com.example.mynotepad.ui.fragments.notes_list.viewModel.NoteViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class NotesList : Fragment() {
    private lateinit var binding: FragmentNotesListBinding
    private val viewModel: NoteViewModel by viewModel<NoteViewModel>()
    private val noteAdapter = NoteAdapter(
        onNoteClick = { note ->
            findNavController().navigate(
                NotesListDirections.actionListToEditor(
                    NoteArg(note.id, note.title, note.content)
                )
            )
        },
        onDeleteClick = { note ->
            viewModel.deleteNote(note)
            Snackbar.make(binding.root, "Note deleted", Snackbar.LENGTH_LONG)
                .setAction("UNDO") { viewModel.addNote(note.title, note.content) }
                .show()
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupFab()
        observeNotes()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            adapter = noteAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun setupFab() {
        binding.fabAddNote.setOnClickListener {
            findNavController().navigate(
                NotesListDirections.actionListToEditor(null)
            )
        }
    }

    private fun observeNotes() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.notesState.collect { result ->
                    when (result) {
                        is NoteResult.Success -> noteAdapter.submitList(result.data)
                        is NoteResult.Error -> showError(result.exception.message)
                    }
                }
            }
        }
    }

    private fun showError(message: String?) {
        Snackbar.make(
            binding.root,
            message ?: "An error occurred",
            Snackbar.LENGTH_LONG
        ).show()
    }
}