package ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.networkLayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.basic.BasicObject;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.response.GooglePushResponse;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.other.JsonDateDeserializer;

import static ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.networkLayout.Url.TUBELESS_FCM_KEY;


/**
 * Created by sajjad on 7/16/2017.
 */

 public class HttpUtils {


    //request = "phoneNumber=" + phoneNumber + "&message=" + message
    public static Object PostDataToServer(String request, String Url, Class classType) {
        return PostDataToServer(null,request,Url,BasicObject.class);
    }

    public static GooglePushResponse PostRequestToFCM (Context context, String json, Class classType) {
        try {
            HttpURLConnection httpcon = (HttpURLConnection) ((new URL("https://fcm.googleapis.com/fcm/send").openConnection()));
            httpcon.setDoOutput(true);
            httpcon.setRequestProperty("Content-Type", "application/json");
            httpcon.setRequestProperty ("Authorization", "key=" + TUBELESS_FCM_KEY);

            httpcon.setRequestMethod("POST");
            httpcon.connect();
            System.out.println("Connected!");

            byte[] outputBytes = json.getBytes("UTF-8");
            OutputStream os = httpcon.getOutputStream();
            os.write(outputBytes);
            os.close();



            int responseCode = httpcon.getResponseCode(); //can call this instead of con.connect()
            if (responseCode >= 400 && responseCode <= 499) {
                //throw new Exception("Bad authentication status: " + responseCode); //provide a more meaningful exception message
            }
            else {


                // Reading response
                InputStream input = httpcon.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(input);
                String result = null;
                GooglePushResponse googlePushResponse = new GooglePushResponse();
                try {
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    for (String line; (line = reader.readLine()) != null; ) {
                        System.out.println(line);
                        result = line;
                        System.out.println(result);
                    }
                    System.out.println(result);
                } catch (Exception ex) {

                }

                Gson gson = new Gson();
                googlePushResponse = gson.fromJson(result, GooglePushResponse.class);
                System.out.println("Http POST request sent!");
                return googlePushResponse;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Object PostRequestToFCM1 (Context context, String json,  Class classType) {

        URL url = null;
        String request = json ;

        HttpURLConnection httpConn = null;
        URLConnection urlConn = null;
        Object serverResponse = null;

        try {
            url = new URL(Url.POST_PUSH);
            urlConn = url.openConnection();
            httpConn = (HttpURLConnection) urlConn;

            if(urlConn != null && httpConn != null) {
                httpConn.setAllowUserInteraction(false);
                httpConn.setInstanceFollowRedirects(true);
                httpConn.setDoInput(true);
                httpConn.setDoOutput(true);
                httpConn.setRequestProperty ("Authorization", "key=" + TUBELESS_FCM_KEY);
                httpConn.setRequestProperty("Content-Type", "application/json");
                httpConn.setRequestMethod("POST");
            }

            OutputStream os = httpConn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            writer.write(request);
            writer.flush();
            writer.close();
            os.close();


            int status = httpConn.getResponseCode();
            InputStream in;
            int aaaaaaaaaaa ;
            String aaaaaaaaaaaa;
            if(status >= HttpURLConnection.HTTP_OK) {
                in = httpConn.getErrorStream();
            }else
                in = httpConn.getInputStream();



            //Gson gson = new Gson();
            Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDateDeserializer()).create();
            StringBuilder sb = getStringBuilder(httpConn);

            serverResponse = gson.fromJson(sb.toString(), classType);

            int a = 5 + 56 ;
            a += 5 ;
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            if (context != null) {
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
            e.printStackTrace();
        }
        return serverResponse;
    }
    public static Object PostDataToServer(Context context, String request, String Url, Class classType) {

        URL url = null;
        HttpURLConnection httpConn = null;
        URLConnection urlConn = null;
        Object serverResponse = null;

        try {
            url = new URL(Url);
            urlConn = url.openConnection();
            httpConn = (HttpURLConnection) urlConn;

            if(urlConn != null && httpConn != null) {
                httpConn.setAllowUserInteraction(false);
                httpConn.setInstanceFollowRedirects(true);
                httpConn.setDoInput(true);
                httpConn.setDoOutput(true);
                httpConn.setRequestMethod("POST");
            }

            OutputStream os = httpConn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            writer.write(request);
            writer.flush();
            writer.close();
            os.close();


            int status = httpConn.getResponseCode();
            InputStream in;
            int aaaaaaaaaaa ;
            String aaaaaaaaaaaa;
            if(status >= HttpURLConnection.HTTP_OK) {
                in = httpConn.getErrorStream();
            }else
                in = httpConn.getInputStream();



            //Gson gson = new Gson();
            Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDateDeserializer()).create();
            StringBuilder sb = getStringBuilder(httpConn);

            serverResponse = gson.fromJson(sb.toString(), classType);

            int a = 5 + 56 ;
            a += 5 ;
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            if (context != null) {
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
            e.printStackTrace();
        }
        return serverResponse;
    }



    public static Object GetDataFromServer(Context context, String request, String Url) {
        URL url = null;
        HttpURLConnection httpConn = null;
        URLConnection urlConn = null;
        Object serverResponse = null;

        try {
            url = new URL(Url);
            urlConn = url.openConnection();
            httpConn = (HttpURLConnection) urlConn;

            if(urlConn != null && httpConn != null) {
//                httpConn.setAllowUserInteraction(false);
//                httpConn.setInstanceFollowRedirects(true);
//                httpConn.setDoInput(true);
//                httpConn.setDoOutput(true);
                httpConn.setRequestMethod("GET");

//            httpConn.setRequestProperty("User-Agent", Statics.USER_AGENT);
//            httpConn.setRequestProperty("Host", "localhost");
//            httpConn.setRequestProperty("Cookie", cookie);
            }

            httpConn.connect();
            if (CheckResCode(httpConn)) return null;
            StringBuilder sb = getStringBuilder(httpConn);
            //Gson gson = new Gson();
            Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDateDeserializer()).create();


//            OutputStream os = httpConn.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
//
//            writer.write(request);
//            writer.flush();
//            writer.close();
//            os.close();
//
//
//            int status = httpConn.getResponseCode();
//            InputStream in;
//            if(status >= HttpURLConnection.HTTP_OK)
//                in = httpConn.getErrorStream();
//            else
//                in = httpConn.getInputStream();

            serverResponse = gson.fromJson(sb.toString() ,BasicObject.class);

            int a = 5 + 56 ;
            a += 5 ;
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            if (context != null) {
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
            e.printStackTrace();
        }
        return serverResponse;

    }


    private static boolean CheckResCode(HttpURLConnection httpConn) throws IOException {
        int resCode = httpConn.getResponseCode();
        if (resCode != HttpURLConnection.HTTP_OK) {
            return true;
        }
        return false;
    }

    @NonNull
    public static StringBuilder getStringBuilder(HttpURLConnection httpConn) throws IOException {
        InputStream in = httpConn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb;
    }
}
