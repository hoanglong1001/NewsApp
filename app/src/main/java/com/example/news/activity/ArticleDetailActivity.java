package com.example.news.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.news.R;
import com.example.news.util.Utils;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class ArticleDetailActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private TextView tvToolbar;
    private TextView tvLink;
    private ImageView imgPhoto;
    private TextView tvPublished;
    private TextView tvTitle;
    private TextView tvSource;
    private TextView tvTime;
    private ProgressBar progressBar;
    private WebView webView;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBar;
    private LinearLayout llPublished;
    private LinearLayout llToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        initView();
        getData();
    }

    private void getData() {
        tvSource.setText(getIntent().getStringExtra("source"));
        tvLink.setText(getIntent().getStringExtra("url"));
        Glide.with(this).load(getIntent().getStringExtra("image"))
                .into(imgPhoto);
        tvTitle.setText(getIntent().getStringExtra("title"));
        tvToolbar.setText(getIntent().getStringExtra("source"));
        tvPublished.setText(Utils.getPublishTime(getIntent().getStringExtra("time")));
        tvTime.setText("\u2022 " + Utils.getTimeAgo(getIntent().getStringExtra("time")));
        setUpWebView(getIntent().getStringExtra("url"));
    }

    private void setUpWebView(String url) {
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }

    private void initView() {
        tvToolbar = findViewById(R.id.tvToolbar);
        tvLink = findViewById(R.id.tvLink);
        imgPhoto = findViewById(R.id.imgPhoto);
        tvPublished = findViewById(R.id.tvPublished);
        tvTitle = findViewById(R.id.tvTitle);
        tvSource = findViewById(R.id.tvSource);
        tvTime = findViewById(R.id.tvTime);
        progressBar = findViewById(R.id.progressBar);
        webView = findViewById(R.id.webView);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbar = findViewById(R.id.collapsingToolbar);
        collapsingToolbar.setTitle("");
        appBar = findViewById(R.id.appbar);
        appBar.addOnOffsetChangedListener(this);
        llPublished = findViewById(R.id.llPublished);
        llToolbar = findViewById(R.id.llToolbar);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset / maxScroll);
        if (percentage == 1f ) {
            llToolbar.setVisibility(View.VISIBLE);
            llPublished.setVisibility(View.GONE);
        } else {
            llToolbar.setVisibility(View.GONE);
            llPublished.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}













