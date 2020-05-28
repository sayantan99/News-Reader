package com.example.newsreader;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {


    private ArrayList<NewsItem> news = new ArrayList<>();
    private Context context;

    public NewsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.News_title.setText(news.get(position).getTitle());
        holder.News_description.setText(news.get(position).getDescription());
        holder.News_date.setText(news.get(position).getDate());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(context, website_activity.class);
                intent.putExtra("url", news.get(position).getLink());
                context.startActivity(intent);





            }
        });

    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public void setNews(ArrayList<NewsItem> news) {
        this.news = news;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView News_title, News_description, News_date;
        private CardView parent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            News_title = itemView.findViewById(R.id.news_Title);
            News_description = itemView.findViewById(R.id.news_content);
            News_date = itemView.findViewById(R.id.news_date);

            parent = itemView.findViewById(R.id.parent);
        }
    }

}



