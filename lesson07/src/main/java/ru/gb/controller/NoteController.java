package ru.gb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.domain.Note;
import ru.gb.service.NoteService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @GetMapping("/notes")
    public ResponseEntity<List<Note>> getAllNotes() {
        return new ResponseEntity<>(noteService.getAllNotes(), HttpStatus.OK);
    }

    @GetMapping("notes/{id}")
    public ResponseEntity<Note> findById(@PathVariable Long id) {
        var book = noteService.findNoteById(id);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("notes/add")
    public ResponseEntity<Note> addNewNote(@RequestBody Note note) {
        return new ResponseEntity<>(noteService.addNote(note), HttpStatus.CREATED);
    }

    @PutMapping("notes/add")
    public ResponseEntity<Note> updateNote(@RequestBody Note note) {
        return new ResponseEntity<>(noteService.addNote(note), HttpStatus.OK);
    }

    @DeleteMapping("notes/delete/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }
}
