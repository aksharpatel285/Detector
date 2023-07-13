package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvSms;

    private  final  static int REQUEST_CODE_PERMISSION_READ_SMS = 456;
    ArrayList<String> smsMsgList = new ArrayList<String>();
    ArrayAdapter arrayAdapter;
    public static MainActivity instance;

    public  static MainActivity Instance(){
        return instance;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;


        lvSms = findViewById(R.id.lv_msg);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,smsMsgList);
        lvSms.setAdapter(arrayAdapter);
        lvSms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                  String Selected = (String) adapterView.getItemAtPosition(i);
                  Intent inte = new Intent(getApplicationContext(),MesssageSplit.class);
                  inte.putExtra("data",Selected);
                  startActivity(inte);
            }
        });

        if (checkPermission(android.Manifest.permission.READ_SMS)){
            refreshInbox();
        }
        else {
            ActivityCompat.requestPermissions(MainActivity.this ,new String[]{
                    (Manifest.permission.READ_SMS)}, REQUEST_CODE_PERMISSION_READ_SMS);
        }

    }

    private boolean checkPermission(String permission){
        int checkPermission = ContextCompat.checkSelfPermission(this, permission);
        return checkPermission == PackageManager.PERMISSION_GRANTED;
    }

    public void refreshInbox() {
        ContentResolver cResolver = getContentResolver();
        Cursor smsInboxCursor = cResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);

        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");

        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;

        arrayAdapter.clear();
        do {
            String str = "\n"+smsInboxCursor.getString(indexAddress) + "\n\n";
            str += smsInboxCursor.getString(indexBody)+"\n";

            arrayAdapter.add(str);
        } while (smsInboxCursor.moveToNext());
    }

    public void updateList(final String smsMsg){
        arrayAdapter.insert(smsMsg,0);
        arrayAdapter.notifyDataSetChanged();
    }
    }


