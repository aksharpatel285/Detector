package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MesssageSplit extends AppCompatActivity {

    TextView tvMSg;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messsage_split);
        tvMSg = findViewById(R.id.tvMsg);

        Intent in = getIntent();
        String msg = in.getStringExtra("data");
       if(msg != null){
            tvMSg.setText(msg);
            String res = pullLinks(msg);
          if (res.equals("")){

            }else {
              String result = res.replace("http://","").replace("https://","").replace("www.","");
               CheckUrl(result);
              //Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            }
        }
        else {

        }

    }
    public String pullLinks(String text)
    {
        String links="";

        String regex1 = "[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";
        String regex = "\\(?\\b(https?://|www[.]|ftp://)[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);

        Pattern p2 = Pattern.compile(regex1);
        Matcher m2 = p2.matcher(text);



        while(m.find())
        {
            String urlStr = m.group();

            if (urlStr.startsWith("(") && urlStr.endsWith(")"))
            {
                urlStr = urlStr.substring(1, urlStr.length() - 1);
            }

           links+=urlStr;
        }

        if (links.equals("")){
            while (m2.find()){
                String urlStr = m2.group();

                if (urlStr.startsWith("(") && urlStr.endsWith(")"))
                {
                    urlStr = urlStr.substring(1, urlStr.length() - 1);
                }

                links+=urlStr;
            }
        }
        return links;
    }


    //this is for check valid url or not
    public void CheckUrl(String linkurl) {

        String Link = linkurl;
        OkHttpClient okhttp = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("url", Link).build();
        Request request = new Request.Builder().url("http://192.168.213.132:5000/akshar").post(formBody).build();
        okhttp.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> Toast.makeText(MesssageSplit.this, e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                String res = response.body().string();
                String lI = String.valueOf(res.lastIndexOf("d"));
                String[] val = {"3", "4"};
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (lI.equals(val[0])) {
                            @SuppressLint("ResourceType") AlertDialog.Builder alert = new AlertDialog.Builder(MesssageSplit.this);
                            alert.setMessage("This might be a fake URL Don't Click it");
                            alert.setTitle("Alert !");
                            alert.setIcon(android.R.drawable.presence_busy);
                            alert.setPositiveButton("OK", (DialogInterface.OnClickListener) (dialog, which) -> {
                                dialog.cancel();
                            });
                            AlertDialog alertDialog = alert.create();
                            alertDialog.show();

                        } else if (lI.equals(val[1])) {
                            Toast.makeText(MesssageSplit.this, "It is Safe You can Click it", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MesssageSplit.this, lI, Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}