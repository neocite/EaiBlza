package com.neocite.eaiblza.infrastructure.MQTT;

/**
 * Created by paulo-silva on 6/7/15.
 */
public interface ConnectionSubscriber {
    void connected();
    void notConnected();
    void published();
    void notPublished();
    void signed();
    void notSigned();
}
