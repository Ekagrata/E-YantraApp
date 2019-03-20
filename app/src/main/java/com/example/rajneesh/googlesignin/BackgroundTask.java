package com.example.rajneesh.googlesignin;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by RAJNEESH on 13-06-2018.
 */

public class BackgroundTask extends AsyncTask<String,Void,String> {

        Context context;
        HttpURLConnection httpURLConnection;

        public BackgroundTask(Context context) {
                this.context = context;
        }

        @Override
        protected String doInBackground(String... strings)
        {
                String regUrl = "http://ec2-35-154-66-115.ap-south-1.compute.amazonaws.com/register.php",
                        signinUrl="http://ec2-35-154-66-115.ap-south-1.compute.amazonaws.com/server/public/sign",
                        authUrl="http://ec2-35-154-66-115.ap-south-1.compute.amazonaws.com/login.php",
                geturl="http://ec2-35-154-66-115.ap-south-1.compute.amazonaws.com/test_query.php", method = strings[0];
                if (method.equals("signup"))
                {

                        String name, email;
                        email = strings[1];
                        name = strings[2];


                        try {
                                URL url = new URL(signinUrl);
                                Log.i("Insert Data" + " ", "...." + url);
                                httpURLConnection = (HttpURLConnection) url.openConnection();
                                httpURLConnection.setRequestMethod("POST");
                                httpURLConnection.setDoOutput(true);
                                OutputStream outputStream = httpURLConnection.getOutputStream();
                                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                                String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                                        URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");

                                Log.i("Insert Data" + " ", "...Data..." + data);

                                bufferedWriter.write(data);
                                bufferedWriter.flush();
                                bufferedWriter.close();
                                outputStream.close();

                                InputStream is = httpURLConnection.getInputStream();
                                is.close();


                        } catch (MalformedURLException e) {
                                Log.i("Exception Called", "" + e);
                                e.printStackTrace();
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                          return "Inserted";

                }

                else if (method.equals("register"))
                {

                        String name,branch,clg,username,password,phone;
                        name = strings[1];
                        branch = strings[2];
                        clg=strings[3];
                        phone= strings[4];
                        username = strings[5];
                        password =strings[6];


                        try {
                                URL url = new URL(regUrl);
                                Log.i("Insert Data" + " ", "...." + url);
                                httpURLConnection = (HttpURLConnection) url.openConnection();
                                httpURLConnection.setRequestMethod("POST");
                                httpURLConnection.setDoOutput(true);
                                OutputStream outputStream = httpURLConnection.getOutputStream();
                                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                                String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                                        URLEncoder.encode("branch", "UTF-8") + "=" + URLEncoder.encode(branch, "UTF-8")+ "&"+
                                        URLEncoder.encode("college", "UTF-8") + "=" + URLEncoder.encode(clg, "UTF-8")+"&"+
                                        URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8")+"&"+
                                        URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")+"&"+
                                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                                Log.i("Insert Data" + " ", "...Data..." + data);

                                bufferedWriter.write(data);
                                bufferedWriter.flush();
                                bufferedWriter.close();
                                outputStream.close();

                                InputStream is = httpURLConnection.getInputStream();
                                is.close();


                        } catch (MalformedURLException e) {
                                Log.i("Exception Called", "" + e);
                                e.printStackTrace();
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                        return "Inserted";

                }

                else if (method.equals("auth"))
                {

                        String username;
                        username = strings[1];




                        try {
                                URL url = new URL(authUrl);
                                Log.i("Insert Data" + " ", "...." + url);
                                httpURLConnection = (HttpURLConnection) url.openConnection();
                                httpURLConnection.setRequestMethod("POST");
                                httpURLConnection.setDoOutput(true);
                                OutputStream outputStream = httpURLConnection.getOutputStream();
                                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");


                                Log.i("Insert Data" + " ", "...Data..." + data);

                                bufferedWriter.write(data);
                                bufferedWriter.flush();
                                bufferedWriter.close();
                                outputStream.close();

                                InputStream is = httpURLConnection.getInputStream();
                                is.close();


                        } catch (MalformedURLException e) {
                                Log.i("Exception Called", "" + e);
                                e.printStackTrace();
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                        return "Inserted";

                }


                //  return "Inserted";
                else {

                        //boolean flag=true;
                        while(true) {
                                try {
                                        URL url = new URL(authUrl);
                                        httpURLConnection = (HttpURLConnection) url.openConnection();
                                        httpURLConnection.setRequestMethod("POST");
                                        httpURLConnection.setDoInput(true);


                                        InputStream input = httpURLConnection.getInputStream();

                                        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                                        StringBuilder result = new StringBuilder();
                                        String line;

                                        while ((line = reader.readLine()) != null) {
                                                result.append(line);
                                        }

                                         //flag=false;
                                        Log.i("Insert Data" + " ", "...Data..." + result);
                                        return (result.toString());


                                } catch (IOException e) {
                                        e.printStackTrace();
                                        return "exception";
                                } finally {
                                        httpURLConnection.disconnect();
                                }

                        }
                }
        }






protected void onPreExecute() {
        super.onPreExecute();
        }

@Override
protected void onPostExecute(String result) {
       Toast.makeText(context,result,Toast.LENGTH_LONG).show();
        Log.d("get",result);
        }

@Override
protected void onProgressUpdate(Void... values) {

        super.onProgressUpdate(values);
        }
        }
