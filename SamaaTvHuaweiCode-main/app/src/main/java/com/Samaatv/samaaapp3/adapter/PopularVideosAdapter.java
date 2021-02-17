package com.Samaatv.samaaapp3.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
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
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 3/31/2017.
 */
public class PopularVideosAdapter extends RecyclerView.Adapter<PopularVideosAdapter.ViewHolder>
{

    static final String KEY_VIDEO = "videourl";
    static final int TYPE_HEADER = 0;
    static final int TYPE_HORIZONTAL = 1;
    static final int TYPE_AD = 2;
    static final int TYPE_CELL = 3;
    static final int TYPE_FOOTER = 333;
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    static PublisherInterstitialAd mPublisherInterstitialAd;
    Context context;
    int type;
    AdSize adsize;
    String adunit;
    RecyclerView popular_recycle;
    String title;
    String pubDate;
    String video;
    String image_trend;
    String yvideo;
    private ArrayList<Contact> contactList;
    private PublisherAdView mAdView;
    private EditorsChoiceAdapter popular_adapter;
    private ArrayList<Contact> popular_list;
    private View parentView;

    public PopularVideosAdapter(Context context, ArrayList<Contact> contactList)
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
    public PopularVideosAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {


        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        type = getItemViewType(i); // to determine type of view.


        if (i == TYPE_HEADER)
        {
            View headerView = inflater.inflate(R.layout.list_item_card_big, viewGroup, false);
            adsize = AdSize.MEDIUM_RECTANGLE;
            adunit = context.getString(R.string.tvshows_det_en_mrec);
            return new ViewHolder(headerView)
            {
            }; // view holder for header items
        }
        else if (i == TYPE_HORIZONTAL)
        {
            View horizon = inflater.inflate(R.layout.carousal, viewGroup, false);
            adsize = AdSize.MEDIUM_RECTANGLE;
            adunit = context.getString(R.string.tvshows_det_en_mrec);
            return new ViewHolder(horizon)
            {
            };

        }
        else if (i == TYPE_AD)
        {
            View ADView = inflater.inflate(R.layout.list_item_card_big_ad, viewGroup, false);
            adsize = AdSize.MEDIUM_RECTANGLE;
            adunit = context.getString(R.string.tvshows_det_en_mrec);
            return new ViewHolder(ADView)
            {
            };

        }

        else if (i == TYPE_FOOTER)
        {
            View ADView = inflater.inflate(R.layout.list_item_card_banner_adview, viewGroup, false);
            adsize = AdSize.BANNER;
            adunit = context.getString(R.string.tvshows_en_lb_btf);
            return new ViewHolder(ADView)
            {
            };

        }
        else if (i == TYPE_CELL)
        {
            View normalRow = inflater.inflate(R.layout.items_new, viewGroup, false);
            adsize = AdSize.MEDIUM_RECTANGLE;
            adunit = context.getString(R.string.tvshows_det_en_mrec);
            return new ViewHolder(normalRow)
            {
            }; // view holder for normal items
        }
        else
        {
            View view = inflater.inflate(R.layout.items_new, viewGroup, false);
            adsize = AdSize.MEDIUM_RECTANGLE;
            adunit = context.getString(R.string.tvshows_det_en_mrec);
            return new ViewHolder(view)
            {

            };
        }
    }

    @Override
    public void onBindViewHolder(PopularVideosAdapter.ViewHolder viewHolder, final int i)
    {

        try
        {
            title = contactList.get(i).getTitle();
            viewHolder.tv_name.setText(Html.fromHtml(title));
            pubDate = contactList.get(i).getDate();
            video = contactList.get(i).getVideo();
            image_trend = contactList.get(i).getImage();
            yvideo = contactList.get(i).getYTVideo();
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

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        String newFormat = formatter.format(testDate);
        System.out.println(".....Date..." + newFormat);
        // end date format

        long milliseconds = testDate.getTime();

        String gettime = getTimeAgo(milliseconds);

        viewHolder.tv_date.setText(gettime);


        if (image_trend == null || image_trend.isEmpty())
        {

            viewHolder.imageView.setImageDrawable(null);
        }
        else
        {

            Picasso.with(context)
                    .load(image_trend)
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


        if (viewHolder.getAdapterPosition() == TYPE_FOOTER)
        {
            adsize = AdSize.BANNER;
            adunit = context.getString(R.string.tvshows_en_lb_btf);
        }

        // Create a banner ad. The ad size and ad unit ID must be set before calling loadAd.
        viewHolder.mPublisherAdView = new PublisherAdView(context);
        viewHolder.mPublisherAdView.setAdSizes(adsize);
        viewHolder.mPublisherAdView.setAdUnitId(adunit);

        // Create an ad request.
        PublisherAdRequest.Builder publisherAdRequestBuilder = new PublisherAdRequest.Builder();

        // Optionally populate the ad request builder.
        // publisherAdRequestBuilder.addTestDevice(PublisherAdRequest.DEVICE_ID_EMULATOR);

        // Add the PublisherAdView to the view hierarchy.
        viewHolder.ad.addView(viewHolder.mPublisherAdView);

//        // Start loading the ad.   mz
//        viewHolder.mPublisherAdView.loadAd(publisherAdRequestBuilder.build());


        viewHolder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (yvideo != null && !yvideo.equals("None"))
                {

                    Intent in = new Intent(context, YoutubeVideoWeb.class);
                    //	in.putExtra(KEY_TITLE, name);
                    //	in.putExtra(KEY_ARTIST, cost);
                    in.putExtra(KEY_VIDEO, yvideo);
                    //    in.putExtra(KEY_VIDEO, hash.get(posAcu));
                    //  in.putExtra("image", hash.get(posAcu));
                    // in.putExtra("image", image_trend);
                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(in);

                }
                else
                {
                    if (video != null && !video.equals("None"))
                    {

                        Intent in = new Intent(context, EditorVideoWeb.class);
                        //	in.putExtra(KEY_TITLE, name);
                        //	in.putExtra(KEY_ARTIST, cost);
                        in.putExtra(KEY_VIDEO, video);
                        //    in.putExtra(KEY_VIDEO, hash.get(posAcu));
                        //  in.putExtra("image", hash.get(posAcu));
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
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false);

        //RecyclerView for horizontal layout
        popular_recycle.setLayoutManager(layoutManager);
        popular_recycle.setHasFixedSize(true);

    }

    @Override
    public int getItemViewType(int position)
    {

        Log.e("position", String.valueOf(position));

        if (position == 0)
        {

            return TYPE_HEADER;

        }
        else if (position == 1)
        {

            return TYPE_HORIZONTAL;
        }
        else if (position == 2)
        {

            return TYPE_AD;
        }
        else if (position == 3)
        {

            return TYPE_CELL;
        }
        else if (position == contactList.size() - 1)
        {

            return TYPE_FOOTER;
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
        private PublisherAdView mPublisherAdView;


        public ViewHolder(View view)
        {
            super(view);
            popular_recycle = (RecyclerView) view.findViewById(R.id.horizontal_view);
            //mAdView = (PublisherAdView) view.findViewById(R.id.ad);
            imageView = (ImageView) view.findViewById(R.id.img);
            playbtn = (ImageView) view.findViewById(R.id.playicon);
            tv_name = (TextView) view.findViewById(R.id.title);
            tv_date = (TextView) view.findViewById(R.id.date);
            ad = (RelativeLayout) view.findViewById(R.id.ad);
        }
    }


}
