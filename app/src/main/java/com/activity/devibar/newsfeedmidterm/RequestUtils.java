package com.activity.devibar.newsfeedmidterm;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by namai on 2/5/2017.
 */

public class RequestUtils {
    public RequestUtils(){}

    public static List<News> fetchNewsDate(String requestUrl) throws IOException {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;

        jsonResponse = makeHttpRequest(url);

        List<News> news = extractNewsFromJson(jsonResponse);
        return  news;
    }

    private static URL createUrl(String stringUrl){
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null){
            return  jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection!=null){
                urlConnection.disconnect();
            }
            if (inputStream!=null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static  String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream!=null){
            InputStreamReader inputStreamReader =new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line!=null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static List<News> extractNewsFromJson(String newsJSON){
        if (TextUtils.isEmpty(newsJSON)){
            return null;
        }

        List<News> news = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(newsJSON);
            JSONArray newsArray = baseJsonResponse.getJSONArray("articles");

            for (int i = 0; i < newsArray.length() ; i++) {
                JSONObject currentNews = newsArray.getJSONObject(i);

                String title = currentNews.getString("title");
                String author = currentNews.getString("author");
                String description = currentNews.getString("description");
                String publishedAt = currentNews.getString("publishedAt");
                String url = currentNews.getString("url");
                String urlToImage = currentNews.getString("urlToImage");

                News newsObject = new News(author, title, description,url,urlToImage,publishedAt);

                news.add(newsObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  news;
    }
}

