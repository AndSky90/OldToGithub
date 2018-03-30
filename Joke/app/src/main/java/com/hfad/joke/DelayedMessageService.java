package com.hfad.joke;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.app.TaskStackBuilder;
import android.content.Intent;


public class DelayedMessageService extends IntentService {
    public static final String EXTRA_MESSAGE = "message";
    private static final int NOT_ID=5453;

    public DelayedMessageService() {
        super("DelayedMessageService");//служба записи в журнал ?конструктор?
    }
    @Override protected void onHandleIntent(Intent intent) {//handler может передавать выполнение кода в др поток
        synchronized (this) {
            try{
                wait(10000);
            } catch (InterruptedException e){e.printStackTrace();}
        }
        String text = intent.getStringExtra(EXTRA_MESSAGE);
        showText(text);
    }
    private void showText(final String text){
        Intent intent =new Intent(this, MainActivity.class); //создаем интент для мейна
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(intent); //создаем стэк-билдер задачи и он добавляет этоот интент в стек возврата мейна
            PendingIntent pendingIntent =
                    stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);//отложенный интент
            Notification notification = new Notification.Builder(this) //создаем нотификацию и даем ей отложенный интент
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(getString(R.string.app_name))
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setContentIntent(pendingIntent)
                    .setContentText(text)
                    .build();
            NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(NOT_ID,notification);
        }
}
//всё выполняется в фоне, тост должен быть в основном потоке, для этого используем хандлер
//онстарткомманд выполняется в основном потове перед всеми, поэтому хендлер объявляем там