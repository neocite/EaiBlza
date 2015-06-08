package com.neocite.eaiblza.infrastructure.MQTT;

/**
 * Created by paulo-silva on 6/7/15.
 */
public interface ConnectionPublisher {
    void addConnectionSubscriber(ConnectionSubscriber connectionSubscriber);
}
