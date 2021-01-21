package com.example.news.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.news.R;
import com.example.news.util.Utils;

public class ArticleDetailActivity extends AppCompatActivity {

    private TextView tvAppbar;
    private TextView tvLink;
    private ImageView imgPhoto;
    private TextView tvPublished;
    private TextView tvTitle;
    private TextView tvSource;
    private TextView tvTime;
    private ProgressBar progressBar;
    private WebView webView;

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
        tvAppbar.setText(getIntent().getStringExtra("source"));
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
        tvAppbar = findViewById(R.id.tvAppbar);
        tvLink = findViewById(R.id.tvLink);
        imgPhoto = findViewById(R.id.imgPhoto);
        tvPublished = findViewById(R.id.tvPublished);
        tvTitle = findViewById(R.id.tvTitle);
        tvSource = findViewById(R.id.tvSource);
        tvTime = findViewById(R.id.tvTime);
        progressBar = findViewById(R.id.progressBar);
        webView = findViewById(R.id.webView);
    }
}