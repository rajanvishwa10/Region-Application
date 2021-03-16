package com.android.regionapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.regionapplication.db.AppDatabase;
import com.android.regionapplication.db.*;
import com.android.regionapplication.db.OfflineRegionAdapter;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Region> regionList;
    RecyclerView recyclerView;
    RegionAdapter regionAdapter;
    OfflineRegionAdapter offlineRegionAdapter;
    Toolbar toolbar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        regionList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        toolbar = findViewById(R.id.toolbar);
        textView = findViewById(R.id.textView);

        if (isNetworkAvailable()) {
            getData();
            toolbar.setOnMenuItemClickListener(null);
        } else {
            Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show();
            setSupportActionBar(toolbar);
            loadRegion();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete Data")
            .setMessage("Do you want to delete the data")
            .setPositiveButton("Yes", (dialog, which) -> {
                AppDatabase.getInstance(getApplicationContext()).regionDao().deleteRegion();
                offlineRegionAdapter.notifyDataSetChanged();
                finish();
                overridePendingTransition( 0, 0);
                startActivity(getIntent());
                overridePendingTransition( 0, 0);
                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                textView.setVisibility(View.VISIBLE);
            }).setNegativeButton("Cancel", null).create().show();

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getData() {
        String url = "https://restcountries.eu/rest/v2/region/asia";
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    String capital = jsonObject.getString("capital");
                    String flag = jsonObject.getString("flag");
                    String region = jsonObject.getString("region");
                    String subregion = jsonObject.getString("subregion");
                    String population = jsonObject.getString("population");
                    JSONArray borderArray = jsonObject.getJSONArray("borders");
                    ArrayList<String> borderList = new ArrayList<>();
                    for (int j = 0; j < borderArray.length(); j++) {
                        borderList.add(borderArray.getString(j));
                    }
                    JSONArray languageArray = jsonObject.getJSONArray("languages");
                    ArrayList<String> languageList = new ArrayList<>();
                    for (int j = 0; j < languageArray.length(); j++) {
                        JSONObject object = languageArray.getJSONObject(j);
                        languageList.add(object.getString("name"));
                    }
                    regionList.add(new Region(name, capital, flag, region, subregion, population, borderList, languageList));
                    regionAdapter = new RegionAdapter(getApplicationContext(), regionList, MainActivity.this);
                    recyclerView.setAdapter(regionAdapter);

                    AppDatabase database = AppDatabase.getInstance(this);

                    RegionModel regionModel = new RegionModel();
                    regionModel.name = name;
                    regionModel.capital = capital;
                    regionModel.flag = flag;
                    regionModel.region = region;
                    regionModel.subregion = subregion;
                    regionModel.population = population;
                    regionModel.border = borderList.toString();
                    regionModel.languages = languageList.toString();

                    database.regionDao().insertRegion(regionModel);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });
        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        requestQueue1.add(jsonObjectRequest);
    }

    private void loadRegion() {
        AppDatabase database = AppDatabase.getInstance(this.getApplicationContext());
        List<RegionModel> regionModelList = database.regionDao().getRegion();
        offlineRegionAdapter = new OfflineRegionAdapter(getApplicationContext(), regionModelList, MainActivity.this);
        recyclerView.setAdapter(offlineRegionAdapter);
        offlineRegionAdapter.notifyDataSetChanged();
        if (regionModelList.size()<1){
            textView.setVisibility(View.VISIBLE);
        }
    }
}