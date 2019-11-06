package com.sssameeri.mapsapp.Models;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class Region {

   private String name;
   private String continent;
   private boolean isMapAvailable = false;
   private int depth;
   private Region parent;

    public Region getParent() {
        return parent;
    }

    public void setParent(Region parent) {
        this.parent = parent;
    }

    public Region(String name, String continent, boolean isMapAvailable, int depth) {
        this.name = name;
        this.continent = continent;
        this.isMapAvailable = isMapAvailable;
        this.depth = depth;
    }

    public Region() {
    }

    public boolean isMapAvailable() {
        return isMapAvailable;
    }

    public void setMapAvailable(boolean mapAvailable) {
        isMapAvailable = mapAvailable;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public Region(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
