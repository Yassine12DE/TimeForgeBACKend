package tn.esprit.tic.timeforge.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.timeforge.Entity.Message;
import tn.esprit.tic.timeforge.Entity.ReadReceiptRequest;
import tn.esprit.tic.timeforge.Repository.MessageRepository;
import tn.esprit.tic.timeforge.dto.ChatMessage;

@RestController

public class MessageController {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping(value = "/messages/{channelId}")
    public Page<Message> findMessages(Pageable pageable, @PathVariable("channelId") String channelId) {
        return messageRepository.findAllByChannel(channelId, pageable);
    }

    @PostMapping(value = "/messages")
    public void sendReadReceipt(@RequestBody ReadReceiptRequest request) {
        messageRepository.sendReadReceipt(request.getChannel(), request.getUsername());
        System.out.println("Received Read Receipt Request: " + request);

    }

    @MessageMapping("/chat/{roomId}")
    public void processMessage(@Payload ChatMessage message, @DestinationVariable String roomId) {
        System.out.println("Message received in room " + roomId + ": " + message.getMessage());
        messagingTemplate.convertAndSend("/topic/" + roomId, message);
    }
}