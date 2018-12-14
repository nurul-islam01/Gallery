package com.example.nurulislam.gallery.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nurulislam.gallery.POJO.Hit;
import com.example.nurulislam.gallery.R;
import com.example.nurulislam.gallery.imageui.TouchImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Slide extends AppCompatActivity {
    private static final String TAG = Slide.class.getSimpleName();

    private List<Hit> hits;
    private int position;
    private ViewPager viewpager;
    private Slide.ViewPagerAdapter pagerAdapter;
    private TextView lbl_count, tagsTV, userTV;
    private ImageButton leftArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        if (Build.VERSION.SDK_INT < 16)
        {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else
        {
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        position = getIntent().getIntExtra("position", 0);
        hits = MainActivity.getHits();


        leftArrow = findViewById(R.id.leftArrow);
        viewpager = findViewById(R.id.viewpager);
        lbl_count = findViewById(R.id.lbl_count);
        userTV = findViewById(R.id.userTV);
        tagsTV = findViewById(R.id.tagsTV);

        pagerAdapter = new ViewPagerAdapter(this, (List<Hit>) hits);
        viewpager.setAdapter(pagerAdapter);
        viewpager.setCurrentItem(position);

        displayCurrnetItemPostion(position);

        viewpager.addOnPageChangeListener(viewPagerChangeListener);

        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Slide.this, MainActivity.class));
                finish();
            }
        });
    }

    ViewPager.OnPageChangeListener viewPagerChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {
        }

        @Override
        public void onPageSelected(int i) {
            displayCurrnetItemPostion(i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void displayCurrnetItemPostion(int position){
        try {
            lbl_count.setText((position + 1) + "/" + hits.size());
            Hit hit = (Hit) hits.get(position);
            userTV.setText(hit.getUser());
            tagsTV.setText(hit.getTags());

        }catch (Exception e){
            Log.d(TAG, "displayCurrnetItemPostion: "+e.getMessage());
        }

    }


    private class ViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;
        private Context context;
        private List<Hit> hits;

        public ViewPagerAdapter(Context context, List<Hit> hits) {
            this.context = context;
            this.hits = hits;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.image_fullscreen_preview, container, false);
            TouchImageView imagePreview = view.findViewById(R.id.image_preview);

            Hit hit = hits.get(position);
            Picasso.get().load(hit.getWebformatURL())
                    .placeholder(R.drawable.picture)
                    .error(R.drawable.picture)
                    .into(imagePreview);

            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return hits.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == ((View) o);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
