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
import com.Samaatv.samaaapp3.model.Contact;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.ViewHolder>
{
    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;
    static final int TYPE_AD = 2;
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    Context context;
    String pubDate;
    String video;
    String image;
    String newsCatTag;
    private ArrayList<Contact> contactList;
    private PublisherAdView mAdView;

    public TrendingAdapter(Context context, ArrayList<Contact> contactList)
    {
        this.contactList = contactList;
        this.context = context;
    }

    public TrendingAdapter(Context context, ArrayList<Contact> contactList, String news_id)
    {
        this.contactList = contactList;
        this.context = context;
        this.newsCatTag = news_id;
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
    public TrendingAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {

        // View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_card_big, viewGroup, false);
        //return new ViewHolder(view);

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        return checkScreenAndSetLayout(inflater, viewGroup, i, context);
//        if (i == TYPE_HEADER) {
//            View headerView = inflater.inflate(R.layout.list_item_card_big, viewGroup, false);
//            return new ViewHolder(headerView) {
//            }; // view holder for header items
//        } else if (i == TYPE_CELL) {
//            View normalRow = inflater.inflate(R.layout.items_new, viewGroup, false);
//            return new ViewHolder(normalRow) {
//            }; // view holder for normal items
//        }
//        else {  View view = inflater.inflate(R.layout.list_item_card_big, viewGroup, false);
//            return new ViewHolder(view){
//            };
//        }
    }

    private ViewHolder checkScreenAndSetLayout(LayoutInflater inflater, ViewGroup viewGroup, int i, Context context)
    {

        float yInches = context.getResources().getDisplayMetrics().heightPixels / context.getResources().getDisplayMetrics().ydpi;
        float xInches = context.getResources().getDisplayMetrics().widthPixels / context.getResources().getDisplayMetrics().xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);

        if (diagonalInches >= 6.5)
        {
            // 6.5inch device or bigger
            if (i == TYPE_HEADER)
            {
                View headerView = inflater.inflate(R.layout.list_item_card_big_large, viewGroup, false);
                return new ViewHolder(headerView)
                {
                }; // view holder for header items
            }
            else if (i == TYPE_CELL)
            {
                View normalRow = inflater.inflate(R.layout.items_new_large, viewGroup, false);
                return new ViewHolder(normalRow)
                {
                }; // view holder for normal items
            }
            else
            {
                View view = inflater.inflate(R.layout.list_item_card_big_large, viewGroup, false);
                return new ViewHolder(view)
                {
                };
            }

        }
        else
        {
            // smaller device

            if (i == TYPE_HEADER)
            {
                View headerView = inflater.inflate(R.layout.list_item_card_big, viewGroup, false);
                return new ViewHolder(headerView)
                {
                }; // view holder for header items
            }
            else if (i == TYPE_CELL)
            {
                View normalRow = inflater.inflate(R.layout.items_new, viewGroup, false);
                return new ViewHolder(normalRow)
                {
                }; // view holder for normal items
            }
            else
            {
                View view = inflater.inflate(R.layout.list_item_card_big, viewGroup, false);
                return new ViewHolder(view)
                {
                };
            }
        }

    }

    @Override
    public void onBindViewHolder(TrendingAdapter.ViewHolder viewHolder, final int i)
    {

        try
        {
            viewHolder.tv_name.setText(contactList.get(i).getTitle());
            pubDate = contactList.get(i).getDate();
            video = contactList.get(i).getVideo();
            image = contactList.get(i).getImage();
            //  String link = contactList.get(i).getLink();
        }
        catch (IndexOutOfBoundsException ioe)
        {
            Log.e("IOE", "index out ka bekar exception", ioe);
        }

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
        /*if(link!=null){
            PublisherAdRequest adRequest = new PublisherAdRequest.Builder().setContentUrl(link).build();
            // Start loading the ad in the background.
            mAdView.loadAd(adRequest);
        }

        else
        {
            PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
            // Start loading the ad in the background.
            mAdView.loadAd(adRequest);
        }*/

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
                intent.putExtra("newsCatTag", newsCatTag);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemViewType(int position)
    {
        switch (position)
        {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount()
    {
        if (contactList != null && contactList.size() >= 4)
        {
            return 4;
        }
        else if (contactList != null && contactList.size() < 4)
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
        private TextView tv_name, tv_date, tv_api_level;

        public ViewHolder(View view)
        {
            super(view);
            // mAdView = (PublisherAdView) view.findViewById(R.id.ad);
            imageView = (ImageView) view.findViewById(R.id.img);
            playbtn = (ImageView) view.findViewById(R.id.playicon);
            tv_name = (TextView) view.findViewById(R.id.title);
            tv_date = (TextView) view.findViewById(R.id.date);
            // tv_api_level = (TextView)view.findViewById(R.id.tv_api_level);

        }
    }

}