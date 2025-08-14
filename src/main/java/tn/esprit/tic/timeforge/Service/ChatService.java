package tn.esprit.tic.timeforge.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.tic.timeforge.Entity.Chat;
import tn.esprit.tic.timeforge.Entity.User;
import tn.esprit.tic.timeforge.Repository.ChatRepository;
import tn.esprit.tic.timeforge.Repository.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    public Optional<Chat> getChatById(Long id) {
        return chatRepository.findById(id);
    }

    public Chat saveChat(Chat chat) {
        return chatRepository.save(chat);
    }

    public Chat saveChat(String message, String username, MultipartFile file, boolean isVoiceMessage, boolean isFileMessage) throws IOException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec le username : " + username));

        Chat chat = new Chat();
        chat.setMessage(message);
        chat.setUser(user); // ✅ Correction ici
        chat.setVoiceMessage(isVoiceMessage);
        chat.setFileMessage(isFileMessage);

        if (file != null && !file.isEmpty()) {
            chat.setFileData(file.getBytes());
            chat.setFileName(file.getOriginalFilename());
            chat.setFileType(file.getContentType());
        }

        return chatRepository.save(chat);
    }

    public void deleteChat(Long id) {
        chatRepository.deleteById(id);
    }

    public Chat assignChatToUser(String username, Chat chat) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec le username : " + username));

        chat.setUser(user); // ✅ Correction ici
        return chatRepository.save(chat);
    }
}