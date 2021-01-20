package com.example.news.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.news.R;
import com.example.news.model.Article;
import com.example.news.model.News;
import com.example.news.util.Utils;

import java.util.List;

import retrofit2.Callback;

public class TopHeadlineAdapter extends RecyclerView.Adapter<TopHeadlineAdapter.ViewHolder> {

    private Context context;
    private List<Article> articles;
    private OnClickListener onClickListener;

    public TopHeadlineAdapter(Context context, List<Article> newsList, OnClickListener onClickListener) {
        this.context = context;
        this.articles = newsList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv_topheadline, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article article = articles.get(position);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getColor());
        requestOptions.error(Utils.getColor());
        requestOptions.centerCrop();

        Glide.with(context)
                .load(article.getUrlToImage())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.imgPhoto);
        holder.tvAuthor.setText(article.getAuthor());
        holder.tvTitle.setText(article.getTitle());
        holder.tvDesc.setText(article.getDescription());
        holder.tvSource.setText(article.getSource().getName());
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgPhoto;
        private TextView tvAuthor;
        private TextView tvPublished;
        private TextView tvTitle;
        private TextView tvDesc;
        private TextView tvSource;
        private TextView tvTime;
        private ProgressBar progressBar;
        private OnClickListener onClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_photo);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvPublished = itemView.findViewById(R.id.tv_published);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_description);
            tvSource = itemView.findViewById(R.id.tv_source);
            tvTime = itemView.findViewById(R.id.tv_time);
            progressBar = itemView.findViewById(R.id.progress_load_photo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onItemClick(v, getAdapterPosition());
                }
            });
        }
    }

    public interface OnClickListener {
        void onItemClick(View view, int position);
    }
}


