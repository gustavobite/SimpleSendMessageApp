package com.appsimple.simplesendmessageapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_SEND_SMS = 123;

    protected EditText message;
    protected EditText phone;
    protected Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        message = (EditText) findViewById(R.id.message);
        phone = (EditText) findViewById(R.id.phone);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSmsPermission();
            }
        });
    }

    private void requestSmsPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},
                    PERMISSION_SEND_SMS);
        } else {
            sendSms();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_SEND_SMS: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    sendSms();
                } else {
                    Toast.makeText(this, "Você negou a permissão para enviar SMS!", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private void sendSms(){
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phone.getText().toString(), null, message.getText().toString(), null, null);
        Toast.makeText(this, "Mensagem enviada com sucesso!", Toast.LENGTH_LONG).show();
    }
}
