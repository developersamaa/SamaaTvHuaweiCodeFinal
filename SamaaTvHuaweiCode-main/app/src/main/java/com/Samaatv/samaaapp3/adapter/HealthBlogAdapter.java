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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Samaatv.samaaapp3.R;
import com.Samaatv.samaaapp3.activities.DetailActivity_MoreBlogs;
import com.Samaatv.samaaapp3.model.Contact;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 4/14/2017.
 */
public class HealthBlogAdapter extends RecyclerView.Adapter<HealthBlogAdapter.ViewHolder>
{

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;
    static final int TYPE_AD = 2;
    static final int TYPE_FOOT = 3;
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    Context context;
    String pubDate;
    String video;
    String link;
    String image;
    int viewtype;
    private ArrayList<Contact> contactList;


    public HealthBlogAdapter(ArrayList<Contact> contactList, Context context)
    {
        this.contactList = contactList;
        this.context = context;
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
    public HealthBlogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return checkScreenAndSetLayout(inflater, viewType, parent, context);
    }

    private ViewHolder checkScreenAndSetLayout(LayoutInflater inflater, int i, ViewGroup viewGroup, Context context)
    {

        float yInches = context.getResources().getDisplayMetrics().heightPixels / context.getResources().getDisplayMetrics().ydpi;
        float xInches = context.getResources().getDisplayMetrics().widthPixels / context.getResources().getDisplayMetrics().xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);

        if (diagonalInches >= 6.5)
        {
            View rootView = inflater.inflate(R.layout.list_item_card_big_large, viewGroup, false);
            return new ViewHolder(rootView)
            {
            };
        }
        else
        {
            View view = inflater.inflate(R.layout.list_item_card_big, viewGroup, false);
            return new ViewHolder(view)
            {
            };
        }
    }

    @Override
    public void onBindViewHolder(HealthBlogAdapter.ViewHolder holder, final int position)
    {

        try
        {
            pubDate = contactList.get(position).getDate();
            video = contactList.get(position).getVideo();
            link = contactList.get(position).getLink();
            image = contactList.get(position).getImage();
            holder.tv_name.setText(contactList.get(position).getTitle());
        }
        catch (IndexOutOfBoundsException ioe)
        {
            Log.e("IOE", "index out ka bekar exception", ioe);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        Date testDate = null;
        try
        {
            testDate = sdf.parse(pubDate);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        long milliseconds = testDate.getTime();

        String gettime = getTimeAgo(milliseconds);

        holder.tv_date.setText(gettime);

        if (image == null || image.isEmpty())
        {

            holder.imageView.setImageDrawable(null);
        }
        else
        {

            Picasso.with(context)
                    .load(image)
                    .error(R.drawable.logo_samaatv)
                    .placeholder(R.drawable.logo_samaatv)
                    .into(holder.imageView);
        }


        if (video != null && !video.equals("None"))
        {
            holder.playbtn.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.playbtn.setVisibility(View.GONE);
        }

        // Create a banner ad. The ad size and ad unit ID must be set before calling loadAd.
        holder.mPublisherAdView = new PublisherAdView(context);
        holder.mPublisherAdView.setAdSizes(AdSize.MEDIUM_RECTANGLE);
        holder.mPublisherAdView.setAdUnitId(context.getString(R.string.pak_en_mrec1));
        holder.ad.addView(holder.mPublisherAdView);
        PublisherAdRequest.Builder publisherAdRequestBuilder = new PublisherAdRequest.Builder();
        holder.mPublisherAdView.loadAd(publisherAdRequestBuilder.build());

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Bundle bundle = new Bundle();
                bundle.putSerializable("array", contactList);
                Intent intent = new Intent(context, DetailActivity_MoreBlogs.class);
                intent.putExtras(bundle);
                intent.putExtra("pos", position);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount()
    {
        if (contactList != null)
        {
            return contactList.size();
        }
        else
        {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView, playbtn;
        RelativeLayout ad;
        private TextView tv_name, tv_date, tv_api_level;
        private PublisherAdView mPublisherAdView;

        public ViewHolder(View view)
        {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.img);
            playbtn = (ImageView) view.findViewById(R.id.playicon);
            tv_name = (TextView) view.findViewById(R.id.title);
            tv_date = (TextView) view.findViewById(R.id.date);
            ad = (RelativeLayout) view.findViewById(R.id.ad);
        }
    }
}
