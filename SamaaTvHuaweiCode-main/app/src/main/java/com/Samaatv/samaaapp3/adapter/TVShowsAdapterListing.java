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

import com.Samaatv.samaaapp3.Detail_Activity_TvShows;
import com.Samaatv.samaaapp3.R;
import com.Samaatv.samaaapp3.model.Contact;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TVShowsAdapterListing extends RecyclerView.Adapter<TVShowsAdapterListing.ViewHolder>
{
    static final int TYPE_HEADER = 0;
    static final int TYPE_AD = 1;
    static final int TYPE_FOOT = 2;
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    static PublisherInterstitialAd mPublisherInterstitialAd;
    Context context;
    String video;
    String link;
    String timings;
    String url;
    String image;
    int viewtype;
    private ArrayList<Contact> contactList;
    private PublisherAdView mAdView;

    public TVShowsAdapterListing(Context context, ArrayList<Contact> contactList)
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
    public TVShowsAdapterListing.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return checkScreenAndSetLayout(inflater, viewGroup, i, context);
    }

    private ViewHolder checkScreenAndSetLayout(LayoutInflater inflater, ViewGroup viewGroup, int i, Context context)
    {

        float yInches = context.getResources().getDisplayMetrics().heightPixels / context.getResources().getDisplayMetrics().ydpi;
        float xInches = context.getResources().getDisplayMetrics().widthPixels / context.getResources().getDisplayMetrics().xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);

        viewtype = getItemViewType(i);

        if (diagonalInches >= 6.5)
        {
            // 6.5inch device or bigger
            if (i == TYPE_HEADER)
            {
                View headerView = inflater.inflate(R.layout.list_item_card_big_headview_large, viewGroup, false);
                return new ViewHolder(headerView)
                {
                };
            }
            else if (i == TYPE_AD)
            {
                View ADView = inflater.inflate(R.layout.list_item_card_big_adview_large, viewGroup, false);
                return new ViewHolder(ADView)
                {
                };
            }
            else if (i == TYPE_FOOT)
            {
                View view = inflater.inflate(R.layout.list_item_card_big_large, viewGroup, false);
                return new ViewHolder(view)
                {
                };
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
            if (i == TYPE_HEADER)
            {
                View headerView = inflater.inflate(R.layout.list_item_card_big_headview, viewGroup, false);
                return new ViewHolder(headerView)
                {
                };
            }
            else if (i == TYPE_AD)
            {
                View ADView = inflater.inflate(R.layout.list_item_card_big_adview, viewGroup, false);
                return new ViewHolder(ADView)
                {
                };

            }
            else if (i == TYPE_FOOT)
            {
                View view = inflater.inflate(R.layout.list_item_card_big, viewGroup, false);
                return new ViewHolder(view)
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
    }

    @Override
    public void onBindViewHolder(final TVShowsAdapterListing.ViewHolder viewHolder, int i)
    {


        // Create the InterstitialAd and set the adUnitId.
        mPublisherInterstitialAd = new PublisherInterstitialAd(context);
        // Defined in res/values/strings.xml
        mPublisherInterstitialAd.setAdUnitId(context.getString(R.string.interstitial_unit_id_eng));

        try
        {
            viewHolder.tv_name.setText(contactList.get(viewHolder.getAdapterPosition()).getTitle());
            //String pubDate = contactList.get(i).getDate();
            video = contactList.get(viewHolder.getAdapterPosition()).getVideo();
            link = contactList.get(viewHolder.getAdapterPosition()).getLink();
            timings = contactList.get(viewHolder.getAdapterPosition()).getTimings();
            if (timings.contains("<")) {
                timings = timings.substring(0, timings.indexOf(":"));
                timings += ".";
            }
            url = contactList.get(viewHolder.getAdapterPosition()).getUrl();
            image = contactList.get(viewHolder.getAdapterPosition()).getImage();
            // Setting date with formatted elapsed time
            if (image.contains("http://www.samaa.tv/"))
            {
                image = image.replace("http://www.samaa.tv/", "https://www.samaa.tv/");
            }
        }
        catch (Exception e)
        {
            Log.e("IOE", "index out ka bekar exception");
        }

        viewHolder.tv_date.setText(timings);


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

        // Create a banner ad. The ad size and ad unit ID must be set before calling loadAd.
        PublisherAdView mPublisherAdView = new PublisherAdView(context);
        mPublisherAdView.setAdSizes(AdSize.BANNER, new AdSize(300, 50), new AdSize(300, 100), new AdSize(320, 50), new AdSize(320, 100));
        mPublisherAdView.setAdUnitId("/14309701/and-en/programs/and-en.programs.mrec-1");
        PublisherAdRequest.Builder publisherAdRequestBuilder = new PublisherAdRequest.Builder();
        viewHolder.mAdView.addView(mPublisherAdView);
        mPublisherAdView.loadAd(publisherAdRequestBuilder.build());

        // Create a banner ad. The ad size and ad unit ID must be set before calling loadAd.
        PublisherAdView mPublisherAdView1 = new PublisherAdView(context);
        mPublisherAdView1.setAdSizes(AdSize.MEDIUM_RECTANGLE, new AdSize(300, 50), new AdSize(300, 100), new AdSize(320, 50), new AdSize(320, 100));
        mPublisherAdView1.setAdUnitId("/14309701/and-en/programs/and-en.programs.mrec-4");
        PublisherAdRequest.Builder publisherAdRequestBuilder1 = new PublisherAdRequest.Builder();
        viewHolder.ad.addView(mPublisherAdView1);
        mPublisherAdView1.loadAd(publisherAdRequestBuilder1.build());


//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (mPublisherInterstitialAd != null && mPublisherInterstitialAd.isLoaded()) {
//                    mPublisherInterstitialAd.show();
//                } else {
//
//                }
//
////                Bundle bundle = new Bundle();
////                bundle.putSerializable("array", contactList);
////                Intent intent = new Intent(context, Detail_Activity_TvShows.class);
////                intent.putExtras(bundle);
////                intent.putExtra("pos", viewHolder.getLayoutPosition());
////                intent.putExtra("url", url);
////                context.startActivity(intent);
//
//            }
//        });

    }

    @Override
    public int getItemViewType(int position)
    {
        if (position == 0)
        {
            return TYPE_HEADER;
        }
        else if ((position + 1) % 5 == 0)
        {
            return TYPE_AD;
        }
        else
        {
            return TYPE_FOOT;
        }
    }

    @Override
    public int getItemCount()
    {
        return contactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView, playbtn;
        RelativeLayout ad, mAdView;
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
            mAdView = (RelativeLayout) view.findViewById(R.id.ad_head);

            view.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    if (mPublisherInterstitialAd != null && mPublisherInterstitialAd.isLoaded())
                    {
                        mPublisherInterstitialAd.show();
                    }

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("array", contactList);
                    Intent intent = new Intent(context, Detail_Activity_TvShows.class);
                    intent.putExtras(bundle);
                    intent.putExtra("pos", getAdapterPosition());
                    intent.putExtra("url", contactList.get(getAdapterPosition()).getUrl());
                    context.startActivity(intent);
                }
            });
        }
    }

}