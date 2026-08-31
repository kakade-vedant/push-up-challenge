package com.vedant.kakade.push.up.challenges.controller;

import com.vedant.kakade.push.up.challenges.exception.IdModificationException;
import com.vedant.kakade.push.up.challenges.exception.ItemNotFoundException;
import com.vedant.kakade.push.up.challenges.model.request.AddEntryLog;
import com.vedant.kakade.push.up.challenges.model.request.DeleteEntryLog;
import com.vedant.kakade.push.up.challenges.model.response.GetAllEntriesLog;
import com.vedant.kakade.push.up.challenges.model.response.GetEntryLog;
import com.vedant.kakade.push.up.challenges.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("entry")
public class EntryController {
    @Autowired
    EntryService entryService;

    @PostMapping("add-new-entry")
    public ResponseEntity addEntry(@RequestBody AddEntryLog addEntryLog){
        try {
            GetEntryLog result = entryService.addEntry(addEntryLog);

            URI uri = entryService.generateURI(result);

            return ResponseEntity.created(uri).body(result);
        } catch (IdModificationException idModificationException) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("delete-entry")
    public ResponseEntity deleteEntry(@RequestBody DeleteEntryLog deleteEntryLog) {
        try {
            entryService.deleteEntry(deleteEntryLog);

            return ResponseEntity.ok().build();
        } catch (ItemNotFoundException itemNotFoundException) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping()
    public ResponseEntity getAllEntries() {
        try {
            GetAllEntriesLog result = entryService.getEntries();

            return ResponseEntity.ok(result);
        } catch (IdModificationException idModificationException) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
