package com.neocite.eaiblza.infrastructure.Background;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.neocite.eaiblza.infrastructure.MQTT.ConnectionManager;

import java.io.FileDescriptor;

/**
 * Created by paulo-silva on 6/7/15.
 */
public class CommunicationService extends Service {

    CommunicationRunnable communicationRunnable;

    public CommunicationService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        communicationRunnable = new CommunicationRunnable();

    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        communicationRunnable.run();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
