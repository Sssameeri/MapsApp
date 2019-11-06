package com.sssameeri.mapsapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sssameeri.mapsapp.Adapters.CountryAdapter;
import com.sssameeri.mapsapp.Models.Region;
import com.sssameeri.mapsapp.Network.NetworkService;
import com.sssameeri.mapsapp.Utils.DeviceMemory;
import com.sssameeri.mapsapp.R;
import com.sssameeri.mapsapp.Utils.Prefs;
import com.sssameeri.mapsapp.Utils.XMLParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String FREE_MEMORY_TEXT = "Free: ";
    private TextView freeDeviceMemoryTextView;
    private SeekBar freeMemorySeekbar;
    private RecyclerView recyclerView;
    private CountryAdapter adapter;
    private List<Region> regions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        freeDeviceMemoryTextView = findViewById(R.id.freeMemoryTextView);
        freeMemorySeekbar = findViewById(R.id.freeMemorySeekbar);
        recyclerView = findViewById(R.id.recyclerView);

        getDeviceMemory();

        XMLParser parser = new XMLParser(getBaseContext());

        try {
            regions = parser.parseXML();
            adapter = new CountryAdapter(regions, this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

        }catch (Exception e) {
            e.fillInStackTrace();
        }

    }

    public void getDeviceMemory() {
        String availableMemory = DeviceMemory.getAvailableInternalMemorySize();
           freeDeviceMemoryTextView.setText(FREE_MEMORY_TEXT + availableMemory);
           freeMemorySeekbar.setEnabled(false);
           freeMemorySeekbar.setProgress(Integer.parseInt(availableMemory.replaceAll("\\D", "")));
    }




}
