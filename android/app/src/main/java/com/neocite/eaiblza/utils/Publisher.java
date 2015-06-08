package com.neocite.eaiblza.utils;

import com.neocite.eaiblza.infrastructure.MQTT.TopicSubscriber;

/**
 * Created by paulo-silva on 6/7/15.
 */
public interface Publisher<TSubscriber> {
    void addSubscriber(Subscriber<TSubscriber> subscriber);
}
