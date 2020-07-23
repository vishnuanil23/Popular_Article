package com.example.sampletest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    ArrayList<String> array_image;
    ArrayList<String> array_publishedby;
    ArrayList<String> array_published_date;
    ArrayList<String> array_subtitle;
    ArrayList<String> array_title;
    ArrayList<String> array_detailview;
    private Context context ;


    public Adapter(Context ctx, ArrayList<String> array_img1, ArrayList<String> array_publishedby1,ArrayList<String> array_published_date1,ArrayList<String> array_title1,ArrayList<String> array_subtitle1,ArrayList<String> array_detailview1) {
        this.array_image = array_img1;
        this.array_publishedby = array_publishedby1;
        this.array_published_date = array_published_date1;
        this.array_title = array_title1;
        this.array_subtitle = array_subtitle1;
        this.array_detailview=array_detailview1;
        context=ctx;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.title.setText(array_title.get(position));
        Picasso.get().load(array_image.get(position)).into(holder.title_image);
        holder.subtiltle.setText(array_subtitle.get(position));
        holder.publishedby.setText(array_publishedby.get(position));
        holder.published_date.setText(array_published_date.get(position));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(context,DetailActivity.class);
//                i.putExtra("YourValueKey", array_detailview.get(position));

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("YourValueKey", array_detailview.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

//                view.getContext().startActivity(new Intent(view.getContext(), DetailActivity.class));
//                context.startActivity(new Intent(context, DetailActivity.class));


            }
        });



    }

    @Override
    public int getItemCount() {
        return array_image.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,subtiltle,publishedby,published_date;
        ImageView title_image;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title= itemView.findViewById(R.id.news_title);
            subtiltle=itemView.findViewById(R.id.news_subtitle);
            publishedby=itemView.findViewById(R.id.news_publisher);
            published_date=itemView.findViewById(R.id.news_publisherdate);
            title_image=itemView.findViewById(R.id.news_imageview);
            cardView=itemView.findViewById(R.id.card_view_news);

        }
    }
}
