package com.example.top10downloaderapp;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class parseApplications {
    private static final String TAG = "parseApplications";
    private ArrayList<Feedentry> applications;

    public parseApplications() {
        this.applications = new ArrayList<>();
    }

    public ArrayList<Feedentry> getApplications() {
        return applications;
    }

    public boolean parse(String xmlData) {
        boolean status = true;
        Feedentry currentRecord=null;
        titleclass tii=null;
        boolean InEntry = false;
        String textValue = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(xmlData));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname=xpp.getName();
                switch(eventType)
                {
                    case XmlPullParser.START_TAG: {
                        Log.d(TAG, "parse: Starting tag for " + tagname);
                        if ("entry".equalsIgnoreCase(tagname)) {
                            InEntry = true;
                            currentRecord = new Feedentry();
                        }
                        else if("title".equalsIgnoreCase(tagname)&&InEntry==false)
                        {
                            tii=new titleclass();
                        }
                        break;
                    }

                        case XmlPullParser.TEXT:
                        {
                            textValue=xpp.getText();
                            break;
                        }

                    case XmlPullParser.END_TAG:
                    {
                        Log.d(TAG, "parse: Ending tag for "+tagname);
                        if(InEntry)
                        {
                            if("entry".equalsIgnoreCase(tagname))
                            {
                                applications.add(currentRecord);
                                InEntry=false;

                            }
                            else if ("name".equalsIgnoreCase(tagname))
                            {
                                currentRecord.setName(textValue);
                            }
                            else if("artist".equalsIgnoreCase(tagname))
                            {
                                currentRecord.setArtist(textValue);
                            }
                            else if("releasedate".equalsIgnoreCase(tagname))
                            {
                                currentRecord.setReleaseDate(textValue);

                            }
                            else if("imageurl".equalsIgnoreCase(tagname))
                            {
                                currentRecord.setImageurl(textValue);
                            }
                            else if("summary".equalsIgnoreCase(tagname))
                            {
                                currentRecord.setSummary(textValue);
                            }

                        }
                        else if("title".equalsIgnoreCase(tagname)&&InEntry==false)
                        {
                            tii.setTitle(textValue);
                            Log.d(TAG, "parse: title is "+tii.getTitle());
                        }
                        break;

                    }

                }
                eventType=xpp.next();
                for(Feedentry app:applications)
                {
                    Log.d(TAG, "**************8");
                    Log.d(TAG,app.toString());
                }
            }
        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        }
        return status;

    }
}
