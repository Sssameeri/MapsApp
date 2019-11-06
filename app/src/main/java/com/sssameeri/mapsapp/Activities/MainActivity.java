package com.sssameeri.mapsapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sssameeri.mapsapp.Models.Region;
import com.sssameeri.mapsapp.Utils.DeviceMemory;
import com.sssameeri.mapsapp.R;
import com.sssameeri.mapsapp.Utils.Prefs;
import com.sssameeri.mapsapp.Utils.XMLParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class MainActivity extends AppCompatActivity {

    private static final String FREE_MEMORY_TEXT = "Free: ";
    private static final String LOG_TAG = "myTag";
    private static final String REGION_TAG = "region";
    private TextView freeDeviceMemoryTextView;
    private SeekBar freeMemorySeekbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        freeDeviceMemoryTextView = findViewById(R.id.freeMemoryTextView);
        freeMemorySeekbar = findViewById(R.id.freeMemorySeekbar);
        getDeviceMemory();

        XMLParser xmlParser = new XMLParser(getBaseContext());

        try {
            xmlParser.parseXM();
        } catch (Exception ex)
        {
            ex.fillInStackTrace();
        }

    }

    public void getDeviceMemory() {
       String availableMemory = DeviceMemory.getAvailableInternalMemorySize();
       if(!TextUtils.isEmpty(availableMemory)) {
           freeDeviceMemoryTextView.setText(FREE_MEMORY_TEXT + availableMemory);
           freeMemorySeekbar.setEnabled(false);
           freeMemorySeekbar.setProgress(Integer.parseInt(availableMemory.replaceAll("[\\D]", "")));
       } else {
           Toast.makeText(getBaseContext(), "Something went wrong!((", Toast.LENGTH_SHORT).show();
       }
    }

}
