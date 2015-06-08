package com.neocite.eaiblza.infrastructure.Background;

import com.neocite.eaiblza.infrastructure.MQTT.ConnectionManager;

/**
 * Created by paulo-silva on 6/7/15.
 */
public class CommunicationRunnable implements Runnable {
    @Override
    public void run() {
        ConnectionManager.getInstance().connect();
    }
}
