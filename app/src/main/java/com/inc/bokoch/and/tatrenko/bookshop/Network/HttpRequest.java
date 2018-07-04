package com.inc.bokoch.and.tatrenko.bookshop.Network;


import android.util.Log;

import com.google.gson.Gson;
import com.inc.bokoch.and.tatrenko.bookshop.Model.Transaction;
import com.inc.bokoch.and.tatrenko.bookshop.Utils.ConstUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import javax.net.ssl.HttpsURLConnection;

public  class HttpRequest {

    public static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e("create Url", "Error with creating URL", exception);
            return null;
        }
        return url;
    }
    public static String makeHttpRequestGET(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);

            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("make HTTP request", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("make HTTP request", "makeHttpRequestGET", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    public static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static String makeHttpRequestPOST(URL url, int id, int amount) throws IOException {
        String jsonResponse = "";
        //HttpURLConnection urlConnection = null;
        //InputStream inputStream = null;
        try {
            Gson gson = new Gson();
            HttpClient httpClient    = HttpClientBuilder.create().build();
            HttpPost     post          = new HttpPost(ConstUtils.BASIC_URL);
            StringEntity postingString = new StringEntity(gson.toJson(new Transaction(id, amount)));
            post.setEntity(postingString);
            post.setHeader("Content-type", "application/json");
            HttpResponse response = httpClient.execute(post);
            /*urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setReadTimeout(10000 /* milliseconds */
/*            urlConnection.setConnectTimeout(15000 /* milliseconds */

  /*          JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);
            jsonObject.put("amount", amount);

            OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
            wr.write(jsonObject.toString());
            wr.flush();
            StringBuilder sb = new StringBuilder();*/
           // int httpResult = urlConnection.getResponseCode();
            if (response.getStatusLine().getStatusCode() == HttpsURLConnection.HTTP_OK) {
                    return "Success";
            } else {
                return "Failture";
                //Log.e("make HTTP request", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("make HTTP request", "makeHttpRequestGET", e);
        }
        return jsonResponse;
    }
}
