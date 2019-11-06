package com.sssameeri.mapsapp.Network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.sssameeri.mapsapp.Models.Region;
import com.sssameeri.mapsapp.Utils.Prefs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private Context context;

    public NetworkService(Context context) {
        this.context = context;
    }

    public void downloadFile(final Region region) {
//
        final String url = "http://download.osmand.net/download.php?standard=yes&file=" ;

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://download.osmand.net/");
        Retrofit retrofit = builder.build();

        RestAPI api = retrofit.create(RestAPI.class);

        Call<ResponseBody> call =
                api.downloadFileWithDynamicUrlSync(url + region.getDownloadUrl());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                Toast.makeText(context, "File downloading started", Toast.LENGTH_SHORT).show();

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        boolean success = writeResponseBodyToDisk(response.body(), region);
                        return null;
                    }
                }.execute();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean writeResponseBodyToDisk(ResponseBody body, Region region) {
        try {
            File futureStudioIconFile = new File(context.getFilesDir(),  region.getName() + Prefs.urlEnd);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(Prefs.TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
