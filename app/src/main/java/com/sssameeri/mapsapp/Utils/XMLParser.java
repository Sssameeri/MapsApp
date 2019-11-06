package com.sssameeri.mapsapp.Utils;

import android.content.Context;
import android.util.Log;

import com.sssameeri.mapsapp.Models.Region;
import com.sssameeri.mapsapp.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class XMLParser {

    private Region region, child;
    private Context context;

    public XMLParser(Context context) {
        this.context = context;
    }

    public void parseXM() throws IOException, XmlPullParserException{
        region = new Region();

        int depth = 1;

        region.setDepth(depth);

            XmlPullParser parser = context.getResources().getXml(R.xml.regions);
            int eventType = parser.getEventType();

            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT: {
                        Log.d(Prefs.TAG, "START_DOCUMENT");
                        break;
                    }
                    case XmlPullParser.START_TAG : {

                        if(parser.getDepth() > region.getDepth() + 1) {
                            region = child;
                        }
                        if (parser.getDepth() == region.getDepth() + 1) {
                            child = new Region();
                            child.setParent(region);
                            child.setDepth(parser.getDepth());

                            for (int i = 0; i < parser.getAttributeCount(); i++) {
                                if (parser.getAttributeName(i).equals("name")) {
                                    child.setName(parser.getAttributeValue(i));
                                    Log.d(Prefs.TAG, parser.getDepth() + " " + parser.getAttributeValue(i));
                                }
                                else if (parser.getAttributeName(i).equals("map")) {
                                    Log.d(Prefs.TAG, parser.getDepth() + " " + parser.getAttributeValue(i));
                                }
                                else if (parser.getAttributeName(i).equals("type")) {
                                    child.setMapAvailable(false);
                                }

                            }

                            region.addChildren(child);
                        }

                        break;
                    }
                    case XmlPullParser.END_TAG: {

                        if (parser.getDepth() == region.getDepth()) {
                            child = region;
                            region = region.getParent();
                        }

                        break;

                    }

                    case XmlPullParser.TEXT: {
                        Log.d(Prefs.TAG, "text = " + parser.getText());
                        break;
                    }

                    default:
                        break;
                }
                eventType = parser.next();
            }
    }


}
