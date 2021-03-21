package com.example.top10downloaderapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ListView listapps;
    private String feedurl="http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml";
    private int feedLimit=10;
    private TextView Header;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Header=(TextView) findViewById(R.id.head);
        listapps=(ListView) findViewById(R.id.xmllistview);
        //Log.d(TAG, "onCreate:Starting AsyncTask ");
        //DownloadData downloadData = new DownloadData();
        Downloadurl(String.format(feedurl,feedLimit));
        //Log.d(TAG, "onCreate:Done ");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.feeds_menu,menu);
       if(feedLimit==10)
       {
           menu.findItem(R.id.mnu10).setChecked(true);

       }
       else
       {
           menu.findItem(R.id.mnu25).setChecked(true);

       }
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.mnufree: {
                feedurl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml";
                break;
            }
            case R.id.mnupaid: {
                feedurl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplications/limit=%d/xml";
                break;
            }
            case R.id.mnusongs: {
                feedurl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=%d/xml";
                break;
            }
            case R.id.mnu10:
            case R.id.mnu25:
            {
                if(!item.isChecked())
                {
                    item.setChecked(true);
                    feedLimit=35-feedLimit;
                }
            }
            default:
                return super.onOptionsItemSelected(item);
        }
            Downloadurl(String.format(feedurl,feedLimit));
        return true;
    }


    public void Downloadurl(String feed)
    {
        Log.d(TAG, "Downldurl:Starting AsyncTask ");
        DownloadData downloadData = new DownloadData();
        downloadData.execute(feed);
        Log.d(TAG, "Downldurl:Done ");
    }

    private class DownloadData extends AsyncTask<String,Void,String>
    {

        private static final String TAG = "DownloadData";

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: parameter is" + s);

            parseApplications pa = new parseApplications();
            pa.parse(s);
            titleclass r=new titleclass();
            String z=r.getTitle().toString();
            Header.setText(z);
            // ArrayAdapter<Feedentry> arrayAdapter=new ArrayAdapter<Feedentry>(MainActivity.this,R.layout.list_view,pa.getApplications());
            FeedAdapter feedAdapter;
            feedAdapter = new FeedAdapter(MainActivity.this, R.layout.list_record, pa.getApplications());
            listapps.setAdapter(feedAdapter);

        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: starts with" + strings[0]);
            String rssfeed = downloadXML(strings[0]);
            if (rssfeed == null) {
                Log.e(TAG, "doInBackground: Error Downloading");
            }
            return rssfeed;
        }
        private String downloadXML(String urlpath)
        {
            StringBuilder xmlresult=new StringBuilder();
            try {
                URL url = new URL(urlpath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d(TAG, "downloadXML: the response code was" + response);
                //InputStream inputstream = connection.getInputStream();
                //InputStreamReader inputStreamReader = new InputStreamReader(inputstream);
                //BufferedReader reader = new BufferedReader(InputStreamReader);
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                int charsread;
                char inputBuffer[] = new char[500];
                while (true) {
                    charsread = reader.read(inputBuffer);
                    if (charsread < 0) {
                        break;
                    } else if (charsread > 0) {
                        xmlresult.append(String.copyValueOf(inputBuffer, 0, charsread));
                    }

                }
                reader.close();
                return xmlresult.toString();
            }
            catch (MalformedURLException e)
            {
                Log.e(TAG, "downloadXML: Invalid URL "+e.getMessage() );
            }
            catch (IOException e)
            {
                Log.e(TAG, "downloadXML: IO exception reading data"+e.getMessage() );
            }
            catch(SecurityException e)
            {
                Log.e(TAG, "downloadXML:Security Exception needs permission?"+e.getMessage() );
            }
                return null;
        }
    }
}

