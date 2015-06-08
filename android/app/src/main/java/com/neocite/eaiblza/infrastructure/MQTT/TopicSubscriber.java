package com.neocite.eaiblza.infrastructure.MQTT;

/**
 * Created by paulo-silva on 6/7/15.
 */
public interface TopicSubscriber<T> {
    void notify(T message);
    String getTopicToListen();
    boolean canExecute(String topic);
}
