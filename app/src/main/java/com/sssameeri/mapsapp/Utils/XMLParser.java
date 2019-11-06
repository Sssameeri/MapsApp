package com.sssameeri.mapsapp.Utils;

import android.content.Context;
import android.text.LoginFilter;
import android.util.Log;

import com.sssameeri.mapsapp.Models.Region;
import com.sssameeri.mapsapp.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {

    private List<Region> regions;
    private Context context;
    private Region region;
    private Region child;

    public XMLParser(Context context) {
        this.context = context;
        regions = new ArrayList<>();
    }

    public List<Region> parseXML() throws IOException, XmlPullParserException {


        XmlPullParser parser = context.getResources().getXml(R.xml.regions);
        int eventType = parser.getEventType();
        int depth = 1;
        region = new Region();
        region.setDepth(depth);

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tagName = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    Log.d(Prefs.TAG, "START_DOCUMENT");
                break;

                case XmlPullParser.START_TAG :

                    if(parser.getDepth() == region.getDepth() + 2) {
                        Region mainRegion = new Region();
                        if (parser.getName().equalsIgnoreCase("region")) {
                            for(int i = 0; i < parser.getAttributeCount(); i++) {
                                if(parser.getAttributeName(i).equalsIgnoreCase("name")) {
                                    mainRegion.setName(capitalize(parser.getAttributeValue(i)));
                                    mainRegion.setDownloadUrl(capitalize(parser.getAttributeValue(i)));
                                }
                            }
                        }
                        regions.add(mainRegion);
                    }

                    break;

                case XmlPullParser.END_TAG :




                    break;
            }
            eventType = parser.next();
        }
        return regions;
    }

    private String capitalize(final String line) {
        if (line.length() == 0) { return line; }
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

}
