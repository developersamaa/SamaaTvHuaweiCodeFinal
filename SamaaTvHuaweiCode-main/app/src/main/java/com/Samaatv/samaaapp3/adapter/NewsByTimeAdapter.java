package com.Samaatv.samaaapp3.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Samaatv.samaaapp3.Detail_Activity;
import com.Samaatv.samaaapp3.R;
import com.Samaatv.samaaapp3.model.Contact;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewsByTimeAdapter extends RecyclerView.Adapter<NewsByTimeAdapter.ViewHolder>
{
    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;
    static final int TYPE_AD = 2;
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    Context context;
    ImageView imageView, playbtn;
    private ArrayList<Contact> contactList;
    private PublisherAdView mAdView;

    public NewsByTimeAdapter(Context context, ArrayList<Contact> contactList)
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
    public NewsByTimeAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {

        // View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_card_big, viewGroup, false);
        //return new ViewHolder(view);

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());


        if (i == TYPE_HEADER)
        {
            View headerView = inflater.inflate(R.layout.list_item_card_big_bytime, viewGroup, false);
            return new ViewHolder(headerView)
            {
            }; // view holder for header items
        }
        else if (i == TYPE_AD)
        {
            View ADView = inflater.inflate(R.layout.list_item_card_big_adview_bytime, viewGroup, false);
            return new ViewHolder(ADView)
            {
            };
        }
        else
        {
            View view = inflater.inflate(R.layout.list_item_card_big_bytime, viewGroup, false);
            return new ViewHolder(view)
            {

            };
        }
    }

    @Override
    public void onBindViewHolder(NewsByTimeAdapter.ViewHolder viewHolder, final int i)
    {

/*
        viewHolder.progressplay.setMax(dataItem.getMax());
        viewHolder.progressplay.setProgress(dataItem.getPosition());*/

        viewHolder.tv_name.setText(contactList.get(i).getTitle());
        String pubDate = contactList.get(i).getDate();
        String video = contactList.get(i).getVideo();
        String link = contactList.get(i).getLink();
        String image = contactList.get(i).getImage();
        String category = contactList.get(i).getCategory();

        // Setting date with formatted elapsed time

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

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        String newFormat = formatter.format(testDate);
        System.out.println(".....Date..." + newFormat);
        // end date format

        long milliseconds = testDate.getTime();

        String gettime = getTimeAgo(milliseconds);

        viewHolder.tv_date.setText(gettime);

        //Setting category values
        viewHolder.tv_cat.setText(" | " + category);

        if (image != null && image.length() > 0) {
            Picasso.with(context)
                    .load(image)
                    .error(R.drawable.logo_samaatv)
                    .placeholder(R.drawable.logo_samaatv)
                    .into(imageView);
        }


        if (video != null && !video.equals("None"))
        {
            playbtn.setVisibility(View.VISIBLE);
        }
        else
        {
            playbtn.setVisibility(View.GONE);
        }
        if (link != null)
        {
            //mz
//            PublisherAdRequest adRequest = new PublisherAdRequest.Builder().setContentUrl(link).build();
//            // Start loading the ad in the background.
//            mAdView.loadAd(adRequest);
        }

        else
        {
//            PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
//            // Start loading the ad in the background.
//            mAdView.loadAd(adRequest);
        }

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
                context.startActivity(intent);

            }
        });
        //viewHolder.tv_api_level.setText(contactList.get(i).getAddress());
    }

    @Override
    public int getItemViewType(int position)
    {
        if (position == 0)
        {
            return TYPE_HEADER;
        }
        else if ((position + 1) % 4 == 0)
        {
            return TYPE_AD;
        }
        else
        {
            return TYPE_HEADER;
        }

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
        private TextView tv_name, tv_date, tv_cat;


        public ViewHolder(View view)
        {
            super(view);
            mAdView = (PublisherAdView) view.findViewById(R.id.ad);
            imageView = (ImageView) view.findViewById(R.id.img);
            playbtn = (ImageView) view.findViewById(R.id.playicon);
            tv_name = (TextView) view.findViewById(R.id.title);
            tv_date = (TextView) view.findViewById(R.id.date);
            tv_cat = (TextView) view.findViewById(R.id.cate);
            // tv_api_level = (TextView)view.findViewById(R.id.tv_api_level);

        }
    }

}