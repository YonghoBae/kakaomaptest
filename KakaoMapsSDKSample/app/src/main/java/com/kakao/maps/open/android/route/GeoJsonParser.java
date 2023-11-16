package com.kakao.maps.open.android.route;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.kakao.maps.open.android.ThreadTask;
import com.kakao.vectormap.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GeoJsonParser extends ThreadTask<String[], GeoJsonParser.Result[]> {
    private Context context;
    private OnCompleteListener listener;

    public interface OnCompleteListener {
        void onComplete(Result[] result);
    }

    public static class Result {
        public String name;
        public List<LatLng> coordinates;

        public Result(String name, List<LatLng> result) {
            this.name = name;
            this.coordinates = result;
        }
    }

    public GeoJsonParser(Context context, OnCompleteListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected Result[] doInBackground(String[] fileNames) {
        AssetManager assetManager= context.getAssets();

        int count = fileNames.length;
        Result[] results = new Result[count];

        for (int i =0; i <count; i++) {
            try {
                InputStream is = assetManager.open(fileNames[i]);
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(isr);

                StringBuffer buffer = new StringBuffer();
                String line = reader.readLine();
                while (line != null) {
                    buffer.append(line + "\n");
                    line = reader.readLine();
                }

                String jsonData = buffer.toString();
                JSONObject jsonObject = new JSONObject(jsonData);

                List<LatLng> coordinates = new ArrayList<>();
                String name = jsonObject.getString("name");
                JSONObject geometry = jsonObject.getJSONObject("geometry");
                JSONArray array = geometry.getJSONArray("coordinates");

                for (int j = 0; j < array.length(); j++) {
                    JSONArray latLng = array.getJSONArray(j);
                    coordinates.add(LatLng.from(latLng.getDouble(1), latLng.getDouble(0)));
                }

                results[i] = new Result(name, coordinates);
            } catch (IOException | JSONException e) {
                Log.e("###", e.getLocalizedMessage());
                return null;
            }
        }
        return results;
    }

    @Override
    protected void onPostExecute(final Result[] result) {
        if (listener != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable(){
                public void run(){
                    listener.onComplete(result);
                }
            });
        }
    }
}
