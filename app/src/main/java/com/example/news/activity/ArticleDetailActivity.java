package com.example.news.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

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
    private String source;
    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        initView();
        getData();
    }

    private void getData() {
        source = getIntent().getStringExtra("source");
        link = getIntent().getStringExtra("url");
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_article_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.share:
                Intent intentShare = new Intent(Intent.ACTION_SEND);
                intentShare.setType("text/plain");
                intentShare.putExtra(Intent.EXTRA_SUBJECT, source);
                startActivity(Intent.createChooser(intentShare, "share with: "));
                return true;
            case R.id.web_browser:
                Intent intentView = new Intent(Intent.ACTION_VIEW);
                intentView.setData(Uri.parse(link));
                startActivity(intentView);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}













