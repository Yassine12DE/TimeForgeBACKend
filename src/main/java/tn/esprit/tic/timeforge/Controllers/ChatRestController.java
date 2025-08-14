package tn.esprit.tic.timeforge.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.tic.timeforge.Entity.Chat;
import tn.esprit.tic.timeforge.Service.ChatService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin("*")
public class ChatRestController {

    @Autowired
    private ChatService chatService;

    @GetMapping
    public List<Chat> getAllChats() {
        return chatService.getAllChats();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chat> getChatById(@PathVariable Long id) {
        Optional<Chat> chat = chatService.getChatById(id);
        return chat.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/send", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendChat(@Valid @RequestBody Chat chat) {
        try {
            Chat savedChat = chatService.saveChat(chat);
            return ResponseEntity.ok(savedChat);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/send-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> sendChatWithFile(
            @RequestParam("message") String message,
            @RequestParam("username") String username, // ✅ Correction ici
            @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            Chat savedChat = chatService.saveChat(message, username, file, false, file != null);
            return ResponseEntity.ok(savedChat);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'envoi du fichier.");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
        chatService.deleteChat(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        Optional<Chat> chat = chatService.getChatById(id);

        if (chat.isPresent() && chat.get().getFileData() != null) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + chat.get().getFileName())
                    .contentType(MediaType.parseMediaType(chat.get().getFileType()))
                    .body(chat.get().getFileData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + " : " + error.getDefaultMessage())
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(errors);
    }
}