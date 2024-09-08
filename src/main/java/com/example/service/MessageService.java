package com.example.service;
// MessageService.java


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import java.util.List;

@Service
public class MessageService {
    
    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private AccountRepository accountRepository;

    public Message createMessage(Message message) {
        if (message.getMessageText() == null || message.getMessageText().isEmpty() || message.getMessageText().length() > 255) {
            throw new RuntimeException("Invalid message text");
        }
        Account postedBy = accountRepository.findById(message.getPostedBy().getAccountId()).orElse(null);
        if (postedBy == null) {
            throw new RuntimeException("Invalid user");
        }
        message.setPostedBy(postedBy);
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    public void deleteMessage(Integer messageId) {
        messageRepository.deleteById(messageId);
    }

    public Message updateMessage(Integer messageId, String newMessageText) {
        if (newMessageText == null || newMessageText.isEmpty() || newMessageText.length() > 255) {
            throw new RuntimeException("Invalid message text");
        }
        Message message = messageRepository.findById(messageId).orElse(null);
        if (message == null) {
            throw new RuntimeException("Message not found");
        }
        message.setMessageText(newMessageText);
        return messageRepository.save(message);
    }

    public List<Message> getMessagesByUser(Integer accountId) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account == null) {
            throw new RuntimeException("User not found");
        }
        return messageRepository.findByPostedBy(account);
    }
}
