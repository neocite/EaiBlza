package com.neocite.eaiblza.models;

import com.neocite.eaiblza.utils.Subscriber;

/**
 * Created by paulo-silva on 6/2/15.
 */
public interface MessageRepository {
    void save(Message message);
    void addListenerToNewMessage(Subscriber<Message> subscriber);
}
