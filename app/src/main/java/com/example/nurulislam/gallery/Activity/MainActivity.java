package com.example.nurulislam.gallery.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.nurulislam.gallery.ADAPTER.GalleryAdapter;
import com.example.nurulislam.gallery.INTRFACE.GalleryApi;
import com.example.nurulislam.gallery.POJO.Gallery;
import com.example.nurulislam.gallery.POJO.Hit;
import com.example.nurulislam.gallery.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private final String BASE_URL = "https://pixabay.com/api/";
    private GalleryApi galleryApi;
    private RecyclerView recycler_view;
    private RecyclerView.Adapter adapter;
    private GridLayoutManager layoutManager;
    private static List<Hit> hits;
    private ProgressBar progress_circular;
    private Toolbar toolbar;

    public static List<Hit> getHits() {
        return hits;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)){
            }else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 201);
            }
        }

        toolbar = findViewById(R.id.toolbar);
        progress_circular = findViewById(R.id.progress_circular);
        recycler_view = findViewById(R.id.recycler_view);
        layoutManager = new GridLayoutManager(this, 2);
        recycler_view.setLayoutManager(layoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());

        getImages();
    }



    private void getImages(){
        progress_circular.setVisibility(View.VISIBLE);
        recycler_view.setVisibility(View.GONE);
        String apikey = getString(R.string.apikey);
        String custiomUrl = String.format("?key=%s&q=yellow+flowers&image_type=photo&pretty=true", apikey);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        galleryApi = retrofit.create(GalleryApi.class);
        Call<Gallery> call = galleryApi.getGalleryUrl(custiomUrl);
        call.enqueue(new Callback<Gallery>() {
            @Override
            public void onResponse(Call<Gallery> call, Response<Gallery> response) {
                if (response.isSuccessful()){
                    if (response != null){
                        try {
                            Gallery gallery = (Gallery) response.body();
                            hits = gallery.getHits();
                            if (hits.size() <= 0){
                                progress_circular.setVisibility(View.VISIBLE);
                                recycler_view.setVisibility(View.GONE);
                            }else {
                                progress_circular.setVisibility(View.GONE);
                                recycler_view.setVisibility(View.VISIBLE);
                            }
                            adapter = new GalleryAdapter(MainActivity.this , hits);
                            recycler_view.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }catch (Exception e){
                            Log.d(TAG, "onResponse: "+e.getMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Gallery> call, Throwable t) {

            }
        });

    }
}
