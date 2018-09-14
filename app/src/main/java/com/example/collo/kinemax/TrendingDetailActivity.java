package com.example.collo.kinemax;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class TrendingDetailActivity extends AppCompatActivity {

    TextView mTitleTv, mDetailTv;
    ImageView mImageIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending_detail);


        //action bar and title
        ActionBar actionbar=getSupportActionBar();

        //action bar title
        actionbar.setTitle("Trending Detail");

        //
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);


        //initialize views
        mTitleTv=findViewById(R.id.titletv);
        mDetailTv=findViewById(R.id.descriptionTv);
        mImageIv=findViewById(R.id.imageView);


        //get data from Intent
        byte [] bytes= getIntent().getByteArrayExtra("image");
        String title=getIntent().getStringExtra("title");
        String desc=getIntent().getStringExtra("description");

        Bitmap bmp = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

        //get data to views
        mTitleTv.setText(title);
        mDetailTv.setText(desc);
        mImageIv.setImageBitmap(bmp);


    }

    @Override
    public boolean onSupportNavigateUp() {

        //Going back to previous activity
        onBackPressed();
        return true;
    }
}
