package com.neocite.eaiblza.infrastructure.repositories.MQTT;

import android.util.JsonReader;

import com.neocite.eaiblza.infrastructure.MQTT.ConnectionManager;
import com.neocite.eaiblza.infrastructure.MQTT.TopicSubscriber;
import com.neocite.eaiblza.infrastructure.translate.dinofauro;
import com.neocite.eaiblza.models.Message;
import com.neocite.eaiblza.utils.Subscriber;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by paulo-silva on 6/2/15.
 */
public class MessageRepository implements com.neocite.eaiblza.models.MessageRepository, TopicSubscriber<String>  {

    private final static String TOPIC_MESSAGES_TO_EVERYONE = "neociteeaiblzatopicseveryone";
    private final static MessageRepository messageRepository = new MessageRepository();
    ConnectionManager connectionManager = ConnectionManager.getInstance();
    Subscriber<Message> subscriber;

    private MessageRepository() {
        connectionManager.addSubscriber(this);
    }

    public static MessageRepository getInstance(){
        return messageRepository;
    }

    @Override
    public void save(final Message message) {
        JSONObject jsonMessage = new JSONObject();
        try {
            jsonMessage.put("owner", dinofauro.translate(message.getOwner()));
            jsonMessage.put("value",dinofauro.translate(message.getValue()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        connectionManager.publish(TOPIC_MESSAGES_TO_EVERYONE,jsonMessage.toString());
    }

    @Override
    public void addListenerToNewMessage(Subscriber<Message> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void notify(String message) {
        JSONObject jsonReaderMessage = null;
        try {
            jsonReaderMessage = new JSONObject(message);
            subscriber.notify(new Message(jsonReaderMessage.getString("value"), jsonReaderMessage.getString("owner")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getTopicToListen() {
        return TOPIC_MESSAGES_TO_EVERYONE;
    }

    @Override
    public boolean canExecute(String topic) {
        return topic.equals(TOPIC_MESSAGES_TO_EVERYONE);
    }



}
