package com.demo.example.emoji.html;

import android.os.AsyncTask;
import java.io.IOException;
import org.jsoup.Jsoup;


public class LoadImage extends AsyncTask<String, Void, String> {
    
    public String doInBackground(String... strArr) {
        try {
            return Jsoup.connect(strArr[0]).get().select("div.singlecontainer").select("div.iphone6image").select("p").select("img").first().attr("src");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
