package com.platinum.zryan.easycurrency;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;


public class MainActivity extends Activity {
    String address = "You own website";
    Button button;
    double britishPound=0;
    double euro=0;
    double usd=0;
    double iq=0;
    Button clear;
    DecimalFormat df = new DecimalFormat("###.##");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new fetchTheAddress().execute(address);
        button= (Button) findViewById(R.id.convert);
        final EditText usEdit= (EditText) findViewById(R.id.usdEdit);
        final EditText gbEdit= (EditText) findViewById(R.id.britishEdit);
        final EditText eurEdit= (EditText) findViewById(R.id.euroEdit);
        final  EditText iqd= (EditText) findViewById(R.id.iqEdit);

        clear= (Button) findViewById(R.id.button);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usEdit.setText("");
                eurEdit.setText("");
                gbEdit.setText("");
                iqd.setText("");
            }
        });
        eurEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!eurEdit.getText().toString().equals("")) {
                    eurEdit.setText("");
                }

            }
        });
        gbEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!gbEdit.getText().toString().equals("")) {
                    gbEdit.setText("");
                }

            }
        });
        usEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!usEdit.getText().toString().equals("")) {
                    usEdit.setText("");

                }


            }
        });
        iqd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!iqd.getText().toString().equals("")) {
                    iqd.setText("");

                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (gbEdit.getText().toString().equals("") &&eurEdit.getText().toString().equals("")&&iqd.getText().toString().equals("")) {
                    String usdAmount = usEdit.getText().toString();
                    double usdInt = Double.parseDouble(usdAmount);
                    double theResultEuro= usdInt*euro;
                    double theResultGbp= usdInt*britishPound;
                    double theResulrIq= usdInt*iq;
                    gbEdit.setText(""+ df.format(theResultGbp));
                    eurEdit.setText(""+df.format(theResultEuro ));
                    iqd.setText(""+df.format((theResulrIq)));

                }
                else if(gbEdit.getText().toString().equals("") &&usEdit.getText().toString().equals("")&&iqd.getText().toString().equals("")){
                    String euroAmount = eurEdit.getText().toString();
                    double euorInt = Double.parseDouble(euroAmount);

                    double convert=britishPound*(1/euro);
                    double convertUs=usd*(1/euro);
                    double convertIq=iq*(1/euro);
                    double theResultUsd= euorInt*convertUs;
                    double theResultGbp= euorInt*convert;
                    double theResultIq= euorInt*convertIq;
                    ;

                    usEdit.setText(""+ df.format(theResultUsd ));
                    gbEdit.setText("" + df.format(theResultGbp));
                    iqd.setText(""+df.format(theResultIq ));
                }
                else if(usEdit.getText().toString().equals("") && eurEdit.getText().toString().equals("")&&iqd.getText().toString().equals("")){
                    String gbAmount = gbEdit.getText().toString();
                    double gbInt = Double.parseDouble(gbAmount);


                    double convert=euro*(1/britishPound);
                    double convertGb=usd*(1/britishPound);
                    double convertIq=iq*(1/britishPound);
                    double theResultIq= gbInt*convertIq;
                    double theResultUsd= gbInt*convertGb;
                    double theResultEu= convert*gbInt;
                    usEdit.setText(""+ df.format(theResultUsd));
                    eurEdit.setText(""+df.format(theResultEu));
                    iqd.setText(""+df.format(theResultIq));
                }
                else  if(usEdit.getText().toString().equals("") && eurEdit.getText().toString().equals("")&&gbEdit.getText().toString().equals("")){
                    String iqAmount = iqd.getText().toString();
                    double iqInt = Double.parseDouble(iqAmount);


                    double convert=euro*(1/iq);
                    double convertGb=usd*(1/iq);
                    double convertIq=britishPound*(1/iq);
                    double theResultIq= iqInt*convertIq;
                    double theResultUsd= iqInt*convertGb;
                    double theResultEu= convert*iqInt;
                    usEdit.setText(""+ df.format(theResultUsd));
                    eurEdit.setText(""+df.format(theResultEu));
                    gbEdit.setText(""+df.format(theResultIq));
                }

            }
        });








    }

    public static String fetchWebsite(String address) {
        StringBuffer stringBuffer = new StringBuffer();

        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream rawData = connection.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(rawData);

            int c;
            while ((c = bufferedInputStream.read()) != -1) {
                stringBuffer.append((char) c);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();


    }
    public class fetchTheAddress extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            return fetchWebsite(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject objectJSON= new JSONObject(s);
                JSONObject objectSub= objectJSON.getJSONObject("rates");
                britishPound= objectSub.getDouble("GBP");
                euro= objectSub.getDouble("EUR");
                usd=objectSub.getDouble("USD");
                iq=objectSub.getDouble("IQD");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }




}

