package ru.gb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.domain.Note;
import ru.gb.repository.NoteRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public Note addNote(Note note) {
        noteRepository.save(note);
        return note;
    }

    public Optional<Note> findNoteById(Long id) {
        return noteRepository.findNoteById(id);
    }

    public void updateNote(Note note) {
        noteRepository.save(note);
    }

    public void deleteNoteById(Long id) {
        noteRepository.deleteById(id);
    }
}
