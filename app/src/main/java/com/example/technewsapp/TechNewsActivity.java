package com.example.technewsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class TechNewsActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> titles;
    ArrayList<String> URLs;
    SQLiteDatabase sqLiteDatabase;

    //    public class FetchID extends AsyncTask<Object, Void, String> {
//
//        @Override
//        protected String doInBackground(Object... objects) {
//
//            try {
//                StringBuilder stringBuilder = new StringBuilder();
//                URL url = new URL("https://hacker-news.firebaseio.com/v0/topstories.json");
//
//                BufferedReader bufferedReader;
//                bufferedReader = new BufferedReader(
//                        new InputStreamReader(
//                                url.openStream()));
//
//                String inputLine;
//                while ((inputLine = bufferedReader.readLine()) != null)
//                    stringBuilder.append(inputLine);
//
//                bufferedReader.close();
//
//                return stringBuilder.toString();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            try {
//                JSONArray jsonArray = new JSONArray(s);
//                for (int i = 15; i < 35; i++) {
//                    articleID = jsonArray.getString(i);
//                    FetchArticle fetchArticle = new FetchArticle();
//                    fetchArticle.execute("https://hacker-news.firebaseio.com/v0/item/" + articleID + ".json?print=pretty");
//
//                }
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
//
//    public class FetchArticle extends AsyncTask<Object, Void, String> {
//
//        @Override
//        protected String doInBackground(Object... objects) {
//
//            try {
//                StringBuilder stringBuilder = new StringBuilder();
//                URL url = new URL((String) objects[0]);
//
//                BufferedReader bufferedReader;
//                bufferedReader = new BufferedReader(
//                        new InputStreamReader(
//                                url.openStream()));
//
//                String inputLine;
//                while ((inputLine = bufferedReader.readLine()) != null)
//                    stringBuilder.append(inputLine);
//
//                bufferedReader.close();
//
//                return stringBuilder.toString();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            try {
//                JSONObject jsonObject = new JSONObject(s);
//                String id = jsonObject.getString("id");
//                String title = jsonObject.getString("title");
//                String url = jsonObject.getString("url");
//                sqLiteDatabase.execSQL("INSERT INTO articles(ids,titles,urls) Values(\"" + id.toString() + "\",\"" + title.toString() + "\",\"" + url.toString() + "\")");
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//    }    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_news);

        try {
            sqLiteDatabase = this.openOrCreateDatabase("articles", MODE_PRIVATE, null);
//            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS articles(ids TEXT,titles TEXT,urls TEXT)");

        } catch (Exception e) {
            e.printStackTrace();

        }
        listView = findViewById(R.id.listView);
        titles = new ArrayList<>();
        URLs = new ArrayList<>();

//        FetchID fetchID = new FetchID();
//        fetchID.execute(this);
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM articles", null);
        int titleIndex = c.getColumnIndex("titles");
        int URLIndex = c.getColumnIndex("urls");
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            titles.add(c.getString(titleIndex));
            URLs.add(c.getString(URLIndex));
            c.moveToNext();
        }

        c.close();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                intent.putExtra(WebViewActivity.URL, URLs.get(position));
                startActivity(intent);
            }
        });
    }
}