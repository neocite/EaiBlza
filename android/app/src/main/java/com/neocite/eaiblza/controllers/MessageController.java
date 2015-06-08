package com.neocite.eaiblza.controllers;


import android.content.Context;
import android.location.LocationManager;

import com.neocite.eaiblza.models.Message;
import com.neocite.eaiblza.models.MessageRepository;
import com.neocite.eaiblza.utils.Publisher;
import com.neocite.eaiblza.utils.Subscriber;

/**
 * Created by paulo-silva on 6/2/15.
 */
public class MessageController implements Subscriber<Message>,Publisher<Message> {

    MessageRepository messageRepository;
    Subscriber<Message> activitySubscriber;
    String ownerLocation;
    final static MessageController messageController = new MessageController();

    private MessageController() {
        messageRepository = com.neocite.eaiblza.infrastructure.repositories.MQTT.MessageRepository.getInstance();
        messageRepository.addListenerToNewMessage(this);
    }

    public static MessageController getInstance(){
        return messageController;
    }

    public void setOwnerLocation(String location){
        ownerLocation = location;
    }

    public void sendNewMessage(String valueMessage){

        Message message = new Message(valueMessage,"Alguem em, "+ownerLocation+", disse:");
        messageRepository.save(message);
    }

    @Override
    public void notify(Message message) {
        this.activitySubscriber.notify(message);
    }

    @Override
    public void addSubscriber(Subscriber<Message> subscriber) {
        this.activitySubscriber = subscriber;
    }
}
