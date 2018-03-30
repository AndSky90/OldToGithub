package com.hfad.joke;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import android.os.Handler;

public class DelayedMessageService extends IntentService {
    public static final String EXTRA_MESSAGE = "message";
    private Handler handler;

    public DelayedMessageService() {
        super("DelayedMessageService");//служба записи в журнал
    }
    @Override public int onStartCommand(Intent intent, int flags, int startId){
        handler=new Handler() {};
        return super.onStartCommand(intent,flags,startId);
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
        handler.post(new Runnable(){
            @Override public void run(){
                Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();
                //1й параметр - выбирается активное окно щас
                Log.v("DelayedMessageService", "The message is:" + text);
            }
        });
    }
}
//всё выполняется в фоне, тост должен быть в основном потоке, для этого используем хандлер
//онстарткомманд выполняется в основном потове перед всеми, поэтому хендлер объявляем там