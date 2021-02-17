package com.Samaatv.samaaapp3.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Samaatv.samaaapp3.Detail_Activity;
import com.Samaatv.samaaapp3.R;
import com.Samaatv.samaaapp3.YoutubeVideoWeb;
import com.Samaatv.samaaapp3.model.Contact;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyNewsAdapter extends RecyclerView.Adapter<MyNewsAdapter.ViewHolder>
{
    static final String KEY_VIDEO = "videourl";
    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;
    static final int TYPE_AD = 2;
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    //private PublisherAdView mAdView;
    Context context;
    String video;
    String image;
    String yvideo;
    String newsId;
    private ArrayList<Contact> contactList;

    public MyNewsAdapter(Context context, ArrayList<Contact> contactList)
    {
        this.contactList = contactList;
        this.context = context;
    }

    public MyNewsAdapter(Context context, ArrayList<Contact> contactList, String newsID)
    {
        this.contactList = contactList;
        this.context = context;
        this.newsId = newsID;
    }

    public static String getTimeAgo(long time)
    {
        if (time < 1000000000000L)
        {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0)
        {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS)
        {
            return "Just now";
        }
        else if (diff < 2 * MINUTE_MILLIS)
        {
            return "a minute ago";
        }
        else if (diff < 50 * MINUTE_MILLIS)
        {
            return diff / MINUTE_MILLIS + " minutes ago";
        }
        else if (diff < 90 * MINUTE_MILLIS)
        {
            return "an hour ago";
        }
        else if (diff < 24 * HOUR_MILLIS)
        {
            return diff / HOUR_MILLIS + " hours ago";
        }
        else if (diff < 48 * HOUR_MILLIS)
        {
            return "yesterday";
        }
        else
        {
            return diff / DAY_MILLIS + " days ago";
        }
    }

    @Override
    public MyNewsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return checkScreenAndSetLayout(inflater, viewGroup, context);

    }

    private ViewHolder checkScreenAndSetLayout(LayoutInflater inflater, ViewGroup viewGroup, Context context)
    {

        float yInches = context.getResources().getDisplayMetrics().heightPixels / context.getResources().getDisplayMetrics().ydpi;
        float xInches = context.getResources().getDisplayMetrics().widthPixels / context.getResources().getDisplayMetrics().xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);

        if (diagonalInches >= 6.5)
        {

            View view = inflater.inflate(R.layout.item_large, viewGroup, false);
            return new ViewHolder(view)
            {
            };
        }
        else
        {
            View view = inflater.inflate(R.layout.item, viewGroup, false);
            return new ViewHolder(view)
            {
            };
        }
    }

    @Override
    public void onBindViewHolder(MyNewsAdapter.ViewHolder viewHolder, final int i)
    {

        try
        {
            viewHolder.tv_name.setText(contactList.get(i).getTitle());
            // String pubDate = contactList.get(i).getDate();
            video = contactList.get(i).getVideo();
            image = contactList.get(i).getImage();
            yvideo = contactList.get(i).getYTVideo();
        }
        catch (IndexOutOfBoundsException ioe)
        {
            Log.e("IOE", "index out ka bekar exception", ioe);
        }

        if (image == null || image.isEmpty())
        {

            viewHolder.imageView.setImageDrawable(null);
        }
        else
        {

            Picasso.with(context)
                    .load(image)
                    .error(R.drawable.logo_samaatv)
                    .placeholder(R.drawable.logo_samaatv)
                    .into(viewHolder.imageView);
        }


        if (video != null && !video.equals("None"))
        {
            viewHolder.playbtn.setVisibility(View.VISIBLE);
        }
        else
        {
            viewHolder.playbtn.setVisibility(View.GONE);
        }


        if (yvideo != null && !yvideo.equals("None"))
        {
            viewHolder.playbtn.setVisibility(View.VISIBLE);
            viewHolder.playbtn.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View view)
                {
                    // Starting new video
                    Intent in = new Intent(context, YoutubeVideoWeb.class);
                    //	in.putExtra(KEY_TITLE, name);
                    //	in.putExtra(KEY_ARTIST, cost);
                    in.putExtra(KEY_VIDEO, yvideo);

                    context.startActivity(in);
                }
            });
        }

        /*PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);*/

        viewHolder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {


                Bundle bundle = new Bundle();
                bundle.putSerializable("array", contactList);
                Intent intent = new Intent(context, Detail_Activity.class);
                intent.putExtras(bundle);
                intent.putExtra("pos", i);
                intent.putExtra("newsCatTag", newsId);
                context.startActivity(intent);

            }
        });
        //viewHolder.tv_api_level.setText(contactList.get(i).getAddress());
    }

    @Override
    public int getItemCount()
    {
        if (contactList != null && contactList.size() >= 8)
        {
            return 8;
        }
        else if (contactList != null && contactList.size() < 8)
        {

            return contactList.size();
        }
        else
        {
            return 0;
        }
    }

    public void SwapItems(ArrayList<Contact> contactList)
    {
        this.contactList = contactList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView, playbtn;
        private TextView tv_name, tv_date, tv_api_level;

        public ViewHolder(View view)
        {
            super(view);
            // mAdView = (PublisherAdView) view.findViewById(R.id.ad);
            imageView = (ImageView) view.findViewById(R.id.image);
            playbtn = (ImageView) view.findViewById(R.id.play_trend);
            tv_name = (TextView) view.findViewById(R.id.title);
            //  tv_date = (TextView)view.findViewById(R.id.date);
            // tv_api_level = (TextView)view.findViewById(R.id.tv_api_level);

        }
    }

}