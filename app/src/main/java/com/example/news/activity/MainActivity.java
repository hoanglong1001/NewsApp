package com.example.news.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.news.R;
import com.example.news.adapter.TopHeadlineAdapter;
import com.example.news.api.ApiClient;
import com.example.news.api.ApiInterface;
import com.example.news.model.Article;
import com.example.news.model.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements TopHeadlineAdapter.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String API_KEY = "5aae723102704e4196795c697674cc15";
    private RecyclerView rvTopHeadline;
    private List<Article> articleList;
    private TopHeadlineAdapter topHeadlineAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        loadArticles();
    }

    private void initView() {
        rvTopHeadline = findViewById(R.id.rv_top_headline);
        rvTopHeadline.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvTopHeadline.setHasFixedSize(true);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void loadArticles() {
        swipeRefreshLayout.setRefreshing(true);
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<News> call = apiInterface.getNews(API_KEY, "us");
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body() != null) {
                    swipeRefreshLayout.setRefreshing(false);
                    articleList = response.body().getArticles();
                    topHeadlineAdapter = new TopHeadlineAdapter(MainActivity.this, articleList, MainActivity.this);
                    rvTopHeadline.setAdapter(topHeadlineAdapter);
                    topHeadlineAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position, ImageView imageView) {
        Article article = articleList.get(position);
        Intent intent = new Intent(MainActivity.this, ArticleDetailActivity.class);
        intent.putExtra("source", article.getSource().getName());
        intent.putExtra("url", article.getUrl());
        intent.putExtra("image", article.getUrlToImage());
        intent.putExtra("time", article.getPublishedAt());
        intent.putExtra("title", article.getTitle());
        intent.putExtra("author", article.getAuthor());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, imageView, ViewCompat.getTransitionName(imageView));
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void onRefresh() {
        loadArticles();
    }
}