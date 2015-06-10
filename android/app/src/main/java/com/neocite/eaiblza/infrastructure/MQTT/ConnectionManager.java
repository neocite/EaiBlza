package com.neocite.eaiblza.infrastructure.MQTT;

import android.util.Log;

import com.neocite.eaiblza.infrastructure.translate.dinofauro;

import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.Listener;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

/**
 * Created by paulo-silva on 6/2/15.
 *
 */


public class ConnectionManager implements TopicPublisher,ConnectionPublisher {
    MQTT mqtt;
    CallbackConnection connection;
    Boolean connected = false;
    private final static ConnectionManager connectionManager = new ConnectionManager();
    List<ConnectionSubscriber> connectionSubscribers = new ArrayList<ConnectionSubscriber>();
    List<TopicSubscriber> topicSubscribers = new ArrayList<TopicSubscriber>();

    protected ConnectionManager() {
        mqtt = new MQTT();
        try {
            //URI uri = new URI("tcp://broker.eaiblza.com.br:1883");
            mqtt.setHost("54.84.155.100",1883);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionManager getInstance(){
        return connectionManager;
    }

    public void connect() {

        if (!connected) {
            connection = mqtt.callbackConnection();

            connection.connect(new Callback<Void>() {

                @Override
                public void onSuccess(Void value) {

                    for(ConnectionSubscriber connectionSubscriber:connectionSubscribers){
                        connectionSubscriber.connected();
                    }
                    assignSubscribers();
                }

                @Override
                public void onFailure(Throwable value) {
                    for(ConnectionSubscriber connectionSubscriber:connectionSubscribers){
                        connectionSubscriber.notConnected();
                    }
                }
            });

            final CallbackConnection listener = connection.listener(new Listener() {
                @Override
                public void onConnected() {

                }

                @Override
                public void onDisconnected() {
                    for(ConnectionSubscriber connectionSubscriber:connectionSubscribers){
                        connectionSubscriber.notConnected();
                    }
                }

                @Override
                public void onPublish(UTF8Buffer topic, Buffer body, Runnable ack) {
                    ack.run();
                    for (TopicSubscriber topicSubscriber : topicSubscribers) {
                        String topicName = topic.toString();
                        if (topicSubscriber.canExecute(topicName)) {
                            String topicValue = body.ascii().toString();
                            topicSubscriber.notify(topicValue);
                        }
                    }


                }


                @Override
                public void onFailure(Throwable value) {
                    final int d = Log.d("s",value.getMessage());
                }
            });
        }
    }

    private void assignSubscribers() {
        Topic[] topics = new Topic[topicSubscribers.size()];

        for (TopicSubscriber topicSubscriber : topicSubscribers) {
            topics[topics.length-1] = new Topic((topicSubscriber.getTopicToListen()),QoS.AT_MOST_ONCE);
        }

        connection.subscribe(topics, new Callback<byte[]>() {
            @Override
            public void onSuccess(byte[] value) {
                for(ConnectionSubscriber connectionSubscriber:connectionSubscribers){
                    connectionSubscriber.signed();
                }
            }

            @Override
            public void onFailure(Throwable value) {
                connection.disconnect(null);
                for(ConnectionSubscriber connectionSubscriber:connectionSubscribers){
                    connectionSubscriber.notSigned();
                }
            }
        });
    }


    public void publish(String topic, String message) {

        final byte[] bytesMessage = message.getBytes();
        connection.publish(topic, bytesMessage, QoS.AT_MOST_ONCE, false, new Callback<Void>() {

            @Override
            public void onSuccess(Void value) {
                for(ConnectionSubscriber connectionSubscriber:connectionSubscribers){
                    connectionSubscriber.published();
                }
            }

            @Override
            public void onFailure(Throwable value) {
                connection.disconnect(null);
                for(ConnectionSubscriber connectionSubscriber:connectionSubscribers){
                    connectionSubscriber.notPublished();
                }
            }

        });
    }

    @Override
    public void addSubscriber(TopicSubscriber topicSubscriber) {
        topicSubscribers.add(topicSubscriber);
    }

    @Override
    public void addConnectionSubscriber(ConnectionSubscriber connectionSubscriber) {
        connectionSubscribers.add(connectionSubscriber);
    }
}
