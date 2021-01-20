package com.example.news.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
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

public class MainActivity extends AppCompatActivity implements TopHeadlineAdapter.OnClickListener {

    public static final String API_KEY = "5aae723102704e4196795c697674cc15";
    private RecyclerView rvTopHeadline;
    private List<Article> articleList;
    private TopHeadlineAdapter topHeadlineAdapter;

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
    }

    private void loadArticles() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<News> call = apiInterface.getNews(API_KEY, "us");
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body() != null) {
                    articleList = response.body().getArticles();
                    topHeadlineAdapter = new TopHeadlineAdapter(MainActivity.this, articleList, MainActivity.this);
                    rvTopHeadline.setAdapter(topHeadlineAdapter);
                    topHeadlineAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}