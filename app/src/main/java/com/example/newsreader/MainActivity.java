package com.example.newsreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<NewsItem> news;

    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        news= new ArrayList<>();

        recyclerView = findViewById(R.id.recView);
        adapter= new NewsAdapter(this);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new GetNews().execute();
    }

    private class GetNews extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            InputStream inputStream = getInputStream();
            if (null != inputStream) {
                try {
                    initXMLPullParser(inputStream);
                } catch (XmlPullParserException | IOException e) {
                    e.printStackTrace();
                }
            }
            return null;

        }
            @Override
            protected void onPostExecute (Void aVoid){
                super.onPostExecute(aVoid);
                adapter.setNews(news);
            }


        private void initXMLPullParser(InputStream inputStream) throws XmlPullParserException, IOException {
            Log.d(TAG, "initXMLPullParser:  ");

            XmlPullParser parser= Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false); // to distinguish between different similar XML tags
            parser.setInput(inputStream,null);

            //extracting the data, right now the parser is at the beginning of the XML file

            parser.next();
            parser.require(XmlPullParser.START_TAG,null,"rss");
            while(parser.next() !=XmlPullParser.END_TAG)
            {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }

                parser.require(XmlPullParser.START_TAG,null,"channel");
                while(parser.next() != XmlPullParser.END_TAG){

                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }


                    if( parser.getName().equals("item")){
                        parser.require(XmlPullParser.START_TAG,null,"item");

                        String title="";
                        String description="";
                        String link="";
                        String pubdate="";
                        while(parser.next() != XmlPullParser.END_TAG)
                        {
                            if (parser.getEventType() != XmlPullParser.START_TAG) {
                                continue;
                            }

                            String tagnName= parser.getName();
                            if(tagnName.equals("title"))
                            {
                                title=getContent(parser,"title");
                            }
                            else if(tagnName.equals("description"))
                            {
                                description=getContent(parser,"description");
                            }
                            else if(tagnName.equals("link"))
                            {
                                link= getContent(parser,"link");

                            }
                            else if(tagnName.equals("pubdate"))
                            {
                                pubdate=getContent(parser, "pubdate");

                            }
                            else
                            {
                                    SkipTag(parser);
                            }


                        }
                        NewsItem item= new NewsItem(title,description,link,pubdate);
                        news.add(item);
                    }

                    else {
                        SkipTag(parser);

                    }


                }
            }


        }


        private InputStream getInputStream() {

            try {
                URL url= new URL("https://www.autosport.com/rss/feed/f1");

                HttpURLConnection connection= (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                return connection.getInputStream();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }
        private  String getContent(XmlPullParser parser, String tagName) throws IOException, XmlPullParserException {
           String content="";
            parser.require(XmlPullParser.START_TAG, null, tagName);

            if(parser.next() == XmlPullParser.TEXT)
            {
                content=parser.getText();
                parser.next();
            }
            return content;
        }
        private void SkipTag(XmlPullParser parser) throws XmlPullParserException, IOException {
            if (parser.getEventType() != XmlPullParser.START_TAG) {

                throw  new IllegalStateException();

            }
            int num= 1;

            while(num !=0)
            {
                switch(parser.next())
                {
                    case XmlPullParser.START_TAG:
                        num++;
                        break;
                    case XmlPullParser.END_TAG:
                        num--;
                        break;
                    default:
                        break;
                }
            }
        }
    }
}


