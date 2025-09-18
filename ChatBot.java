package com.saravanan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ChatBot extends JFrame {

    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;

    // Knowledge base (FAQs)
    private Map<String, String> knowledgeBase;

    public ChatBot() {
        // Initialize GUI
        setTitle("Java ChatBot");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        sendButton = new JButton("Send");

        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        // Initialize knowledge base
        trainBot();

        // Action listener
        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());

        setVisible(true);
        showMessage("Bot", "Hello! Ask me anything.");
    }

    // Train the bot with simple FAQ
    private void trainBot() {
        knowledgeBase = new HashMap<>();

        knowledgeBase.put("hi", "Hello! How can I help you?");
        knowledgeBase.put("hello", "Hi there! What can I do for you?");
        knowledgeBase.put("how are you", "I'm just a bot, but I'm doing great!");
        knowledgeBase.put("what is your name", "I am a simple Java ChatBot.");
        knowledgeBase.put("bye", "Goodbye! Have a nice day!");
        knowledgeBase.put("help", "You can ask me about our services, working hours, or general questions.");
        knowledgeBase.put("what are your working hours", "We are open from 9 AM to 5 PM, Monday to Friday.");
        knowledgeBase.put("thank you", "You're welcome!");
    }

    private void sendMessage() {
        String userInput = inputField.getText().trim();
        if (userInput.isEmpty()) return;

        showMessage("You", userInput);
        inputField.setText("");

        String response = getResponse(userInput.toLowerCase());
        showMessage("Bot", response);
    }

    // NLP + Rule-based matching
    private String getResponse(String input) {
        // Basic normalization
        input = input.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase();

        // Check for exact matches
        for (String key : knowledgeBase.keySet()) {
            if (input.contains(key)) {
                return knowledgeBase.get(key);
            }
        }

        return "I'm sorry, I don't understand. Try asking something else.";
    }

    private void showMessage(String sender, String message) {
        chatArea.append(sender + ": " + message + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatBot::new);
    }
} 