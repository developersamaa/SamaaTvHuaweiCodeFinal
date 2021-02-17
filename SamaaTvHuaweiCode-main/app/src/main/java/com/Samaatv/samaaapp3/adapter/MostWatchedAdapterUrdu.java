package com.Samaatv.samaaapp3.adapter;


import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Samaatv.samaaapp3.EditorVideoWeb;
import com.Samaatv.samaaapp3.R;
import com.Samaatv.samaaapp3.YoutubeVideoWeb;
import com.Samaatv.samaaapp3.model.Contact;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MostWatchedAdapterUrdu extends RecyclerView.Adapter<MostWatchedAdapterUrdu.ViewHolder>
{
    static final int TYPE_HEADER = 0;
    static final int TYPE_AD = 1;
    static final int TYPE_CELL = 2;
    static final int TYPE_FOOT = 3;
    static final String KEY_VIDEO = "videourl";
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    Context context;
    String video;
    String image;
    String timings;
    String yt_video;
    private ArrayList<Contact> contactList;
    private PublisherAdView mAdView;

    public MostWatchedAdapterUrdu(Context context, ArrayList<Contact> contactList)
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
    public MostWatchedAdapterUrdu.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return checkScreenAndSetLayout(inflater, viewGroup, i, context);
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
                View headerView = inflater.inflate(R.layout.list_item_card_big_urdu_large, viewGroup, false);
                return new ViewHolder(headerView)
                {
                };
            }
            else if (i == TYPE_AD)
            {
                View ADView = inflater.inflate(R.layout.list_item_card_big_adview_urdu_large, viewGroup, false);
                return new ViewHolder(ADView)
                {
                };
            }
            else if (i == TYPE_CELL)
            {
                View view = inflater.inflate(R.layout.list_item_card_big_urdu_large, viewGroup, false);
                return new ViewHolder(view)
                {
                };
            }

            else if (i == TYPE_FOOT)
            {
                View view = inflater.inflate(R.layout.list_item_card_foot_urdu_large, viewGroup, false);
                return new ViewHolder(view)
                {
                };
            }
            else
            {
                View view = inflater.inflate(R.layout.list_item_card_foot_urdu_large, viewGroup, false);
                return new ViewHolder(view)
                {
                };
            }

        }
        else
        {
            if (i == TYPE_HEADER)
            {
                View headerView = inflater.inflate(R.layout.list_item_card_big_urdu, viewGroup, false);
                return new ViewHolder(headerView)
                {
                };
            }
            else if (i == TYPE_AD)
            {
                View ADView = inflater.inflate(R.layout.list_item_card_big_adview_urdu, viewGroup, false);
                return new ViewHolder(ADView)
                {
                };
            }
            else if (i == TYPE_CELL)
            {
                View view = inflater.inflate(R.layout.list_item_card_big_urdu, viewGroup, false);
                return new ViewHolder(view)
                {
                };
            }

            else if (i == TYPE_FOOT)
            {
                View view = inflater.inflate(R.layout.list_item_card_foot_urdu, viewGroup, false);
                return new ViewHolder(view)
                {
                };
            }
            else
            {
                View view = inflater.inflate(R.layout.list_item_card_foot_urdu, viewGroup, false);
                return new ViewHolder(view)
                {
                };
            }
        }
    }

    @Override
    public void onBindViewHolder(MostWatchedAdapterUrdu.ViewHolder viewHolder, final int i)
    {

        try
        {
            viewHolder.tv_name.setText(contactList.get(i).getTitle());
            video = contactList.get(i).getVideo();
            image = contactList.get(i).getImage();
            timings = contactList.get(i).getTimings();
            yt_video = contactList.get(i).getYTVideo();
        }
        catch (IndexOutOfBoundsException ioe)
        {
            Log.e("IOE", "index out ka bekar exception", ioe);
        }

        viewHolder.tv_date.setText(timings);


        if (image == null)
        {

            viewHolder.imageView.setImageDrawable(null);
        }
        else
        {

            if (image.equals(""))
            {
                viewHolder.imageView.setImageResource(R.drawable.logo_samaatv);
            }
            else
            {
                Picasso.with(context)
                        .load(image)
                        .error(R.drawable.logo_samaatv)
                        .placeholder(R.drawable.logo_samaatv)
                        .into(viewHolder.imageView);
            }
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
        mPublisherAdView.setAdSizes(AdSize.MEDIUM_RECTANGLE);
        mPublisherAdView.setAdUnitId(context.getString(R.string.live_ur_mrec2));
        PublisherAdRequest.Builder publisherAdRequestBuilder = new PublisherAdRequest.Builder();
        viewHolder.ad.addView(mPublisherAdView);
        mPublisherAdView.loadAd(publisherAdRequestBuilder.build());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                showTheSelectedVideo(contactList, i);
            }
        });
        //viewHolder.tv_api_level.setText(contactList.get(i).getAddress());
    }

    private void showTheSelectedVideo(ArrayList<Contact> contactList, int i)
    {

        final String yvideo = contactList.get(i).getYTVideo();
        final String video = contactList.get(i).getVideo();
        final String image_trend = contactList.get(i).getImage();

        if (yvideo != null && !yvideo.equals("None"))
        {

            Intent in = new Intent(context, YoutubeVideoWeb.class);
            in.putExtra(KEY_VIDEO, yvideo);
            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(in);

        }
        else
        {
            if (video != null && !video.equals("None"))
            {

                Intent in = new Intent(context, EditorVideoWeb.class);
                in.putExtra(KEY_VIDEO, video);
                in.putExtra("image", image_trend);
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(in);
            }
            else
            {
                Toast.makeText(context, "Sorry, video not available !", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public int getItemViewType(int position)
    {
        if (position == 0)
        {
            return TYPE_HEADER;
        }
        else if ((position + 1) % 3 == 0)
        {
            return TYPE_AD;
        }
        else if (position == 5)
        {
            return TYPE_FOOT;
        }
        else
        {
            return TYPE_CELL;
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
        RelativeLayout ad;
        private TextView tv_name, tv_date, tv_api_level;

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