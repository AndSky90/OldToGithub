package com.hfad.odometer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

public class OdometerService extends Service {
    private final IBinder binder = new OdometerBinder();
    private static double distanceInMeters;
    private static Location lastLocation = null;

    public class OdometerBinder extends Binder{     //связывает активити со службой
        OdometerService getOdometer(){return OdometerService.this;}
    }
    @Override public IBinder onBind(Intent intent){return binder;}//вызывается при связывании ак со службой
    //вверху Return the communication channel to the service.

    @Override public void onCreate()
    {
        LocationListener listener = new LocationListener() //-отслеживает устройство
        {
            @Override public void onStatusChanged(String s, int i, Bundle bundle) {}
            @Override public void onProviderEnabled(String s) {}
            @Override public void onProviderDisabled(String s) {}
            @Override public void onLocationChanged(Location location)
            {
                if(lastLocation==null){lastLocation=location;}
                distanceInMeters+=location.distanceTo(lastLocation);
                lastLocation=location;
            }
        };
        //регистрация листенера в службе позиц-я андроида посредством менеджера
        LocationManager locManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,5,listener);
    }
    public double getDistanceInMeters(){return this.getDistanceInMeters();}
}
