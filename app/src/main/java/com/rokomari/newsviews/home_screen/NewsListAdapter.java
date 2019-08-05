package com.rokomari.newsviews.home_screen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rokomari.newsviews.NewsDetailsActivity;
import com.rokomari.newsviews.R;
import com.rokomari.newsviews.model.NewsDetails;
import com.rokomari.newsviews.utils.AppSingleTon;
import com.rokomari.newsviews.utils.Constants;
import com.rokomari.newsviews.utils.Methods;

import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsListViewHolder> {

    private Context context;
    private List<NewsDetails> newsList;

    NewsListAdapter(Context context, List<NewsDetails> newsList){
        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_row, parent, false);
        return new NewsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsListViewHolder holder, int position) {

        NewsDetails newsDetails = newsList.get(position);
        AppSingleTon.getRequestManager(context.getApplicationContext()).load(newsDetails.getUrlToImage()).into(holder.news_image);
        holder.tv_news_title.setText(newsDetails.getTitle());
        String publishedAt = "Published: " + Methods.formatMessageTime(newsDetails.getPublishedAt(), context);
        holder.tv_news_date.setText(publishedAt);
        String author = "Author: " + newsDetails.getAuthor();
        holder.tv_news_author.setText(author);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class NewsListViewHolder extends RecyclerView.ViewHolder{

        ImageView news_image;
        TextView tv_news_date, tv_news_title, tv_news_author;

        public NewsListViewHolder(@NonNull View itemView) {
            super(itemView);

            news_image = itemView.findViewById(R.id.news_image);
            tv_news_title = itemView.findViewById(R.id.tv_news_title);
            tv_news_date = itemView.findViewById(R.id.tv_news_date);
            tv_news_author = itemView.findViewById(R.id.tv_news_author);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final NewsDetails newsDetails = newsList.get(getAdapterPosition());

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(context.getString(R.string.load_in_browser));
                    builder.setMessage(context.getString(R.string.load_news_msg));
                    builder.setPositiveButton(context.getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsDetails.getUrl()));
                            context.startActivity(browserIntent);
                        }
                    });
                    builder.setNegativeButton(context.getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(context, NewsDetailsActivity.class);
                            intent.putExtra(Constants.NEWS_LINK, newsDetails.getUrl());
                            context.startActivity(intent);
                        }
                    });
                    builder.show();
                }
            });
        }
    }
}
