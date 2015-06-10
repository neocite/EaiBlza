package com.neocite.eaiblza.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import com.neocite.eaiblza.R;
import com.neocite.eaiblza.controllers.MessageController;
import com.neocite.eaiblza.infrastructure.Background.CommunicationService;
import com.neocite.eaiblza.infrastructure.MQTT.ConnectionManager;
import com.neocite.eaiblza.infrastructure.MQTT.ConnectionPublisher;
import com.neocite.eaiblza.infrastructure.MQTT.ConnectionSubscriber;
import com.neocite.eaiblza.models.Message;
import com.neocite.eaiblza.utils.Subscriber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Chat extends Activity implements Subscriber<Message>,ConnectionSubscriber, LocationListener {

    ArrayList<Message> messages;
    ListView receivedMessages;
    Button sendButton;
    ChatSendMessageAdapter messageArrayAdapter;
    MessageController messageController;
    LocationManager locationManager;
    String provider;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assignSubscriberToStatusConnection();
        startCommunicationService();
        setContentView(R.layout.chat);
        messages = new ArrayList<Message>();
        messageArrayAdapter = new ChatSendMessageAdapter(this, messages);
        receivedMessages =  (ListView) findViewById(R.id.list);
        sendButton = (Button) findViewById(R.id.send_button);
        receivedMessages.setAdapter(messageArrayAdapter);
        getCurrentLocation();
        showWaitMessage();

    }

    private void getCurrentLocation(){
        Criteria criteria = new Criteria();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        }
    }

    private void showWaitMessage(){

        alertDialog = new AlertDialog.Builder(this)
                .setTitle("Eai, flza?")
                .setMessage("Aguarde....conecfando ao DinoFerferf")
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    private void closeWaitMessage(){
        alertDialog.cancel();
    }

    private void startCommunicationService(){
        Intent communicationService = new Intent(this, CommunicationService.class);
        startService(communicationService);
    }

    private void assignSubscriberToStatusConnection(){
        ConnectionPublisher connectionPublisher = ConnectionManager.getInstance();
        connectionPublisher.addConnectionSubscriber(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }



    public void sendMessage(View view) {
        getCurrentLocation();
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String actualMessage = editText.getText().toString();
        messageController.sendNewMessage(actualMessage);
        editText.setText(null);
    }

    @Override
    public void notify(final Message message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageArrayAdapter.insert(message,0);
                messageArrayAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void connected() {

        messageController = MessageController.getInstance();
        messageController.addSubscriber(this);

        closeWaitMessage();

    }

    @Override
    public void notConnected() {

    }

    @Override
    public void published() {

    }

    @Override
    public void notPublished() {

    }

    @Override
    public void signed() {

    }

    @Override
    public void notSigned() {

    }

    @Override
    public void onLocationChanged(Location location) {

        double lat = location.getLatitude();
        double lng = location.getLongitude();

        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        StringBuilder builder = new StringBuilder();
        try {
            List<Address> address = geoCoder.getFromLocation(lat, lng, 1);
            String finalAddress =  address.get(0).getSubLocality() + " - " + address.get(0).getSubAdminArea();
            messageController.setOwnerLocation(finalAddress);

        } catch (IOException e) {}
        catch (NullPointerException e) {}
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
