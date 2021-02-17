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
import com.Samaatv.samaaapp3.api.ApiService;
import com.Samaatv.samaaapp3.api.RetroClient;
import com.Samaatv.samaaapp3.model.Contact;
import com.Samaatv.samaaapp3.model.NewsCategoryList;
import com.Samaatv.samaaapp3.utils.InternetConnection;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowsAdapterEpisodeListing extends RecyclerView.Adapter<TVShowsAdapterEpisodeListing.ViewHolder>
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
    private static final String pop_videos = "https://www.samaa.tv/videos/jfeedltstprgrms/";
    static PublisherInterstitialAd mPublisherInterstitialAd;
    Context context;
    int type;
    AdSize adsize;
    String adunit;
    String title;
    String pubDate;
    String video;
    String image_trend;
    String yvideo;
    private ArrayList<Contact> contactList;
    private PublisherAdView mAdView;
    private RecyclerView popular_recycle;
    private LinearLayoutManager layoutManager;
    private EditorsChoiceAdapter popular_adapter;
    private TV_Shows_inside_horizontal_adapter six_pop_videos_horizontal_adapter;
    private ArrayList<Contact> popular_list;
    private View parentView;

    public TVShowsAdapterEpisodeListing(Context context, ArrayList<Contact> contactList)
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
    public TVShowsAdapterEpisodeListing.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {

        TVShowsAdapterEpisodeListing.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return checkScreenAndSetLayout(inflater, viewGroup, i, context, viewHolder);
    }

    private ViewHolder checkScreenAndSetLayout(LayoutInflater inflater, ViewGroup viewGroup, int i, Context context, ViewHolder viewHolder)
    {

        float yInches = context.getResources().getDisplayMetrics().heightPixels / context.getResources().getDisplayMetrics().ydpi;
        float xInches = context.getResources().getDisplayMetrics().widthPixels / context.getResources().getDisplayMetrics().xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
        type = getItemViewType(i); // to determine type of view.

        if (diagonalInches >= 6.5)
        {
            if (i == TYPE_HEADER)
            {
                View headerView = inflater.inflate(R.layout.list_item_card_big_tvshows, viewGroup, false);
                adsize = AdSize.MEDIUM_RECTANGLE;
                adunit = "/14309701/and-en/programs/and-en.programs.mrec-1";
                PopularVideos(viewGroup);   //Calling Popular Videos in Carousal
                viewHolder = new ViewHolder(headerView);
            }

            else
            {
                View view = inflater.inflate(R.layout.items_new, viewGroup, false);
                adsize = AdSize.MEDIUM_RECTANGLE;
                adunit = context.getString(R.string.tvshows_det_en_mrec);
                viewHolder = new ViewHolder(view);
            }

            return viewHolder;

        }
        else
        {
            if (i == TYPE_HEADER)
            {
                View headerView = inflater.inflate(R.layout.list_item_card_big_tvshows, viewGroup, false);
                adsize = AdSize.MEDIUM_RECTANGLE;
                adunit = "/14309701/and-en/programs/and-en.programs.mrec-1";
                PopularVideos(viewGroup);   //Calling Popular Videos in Carousal
                viewHolder = new ViewHolder(headerView);
            }

            else
            {
                View view = inflater.inflate(R.layout.items_new, viewGroup, false);
                adsize = AdSize.MEDIUM_RECTANGLE;
                adunit = context.getString(R.string.tvshows_det_en_mrec);
                viewHolder = new ViewHolder(view);
            }

            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(TVShowsAdapterEpisodeListing.ViewHolder viewHolder, final int i)
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


        if (image_trend == null)
        {

            viewHolder.imageView.setImageDrawable(null);
        }
        else
        {
            if (!image_trend.isEmpty()) {
                Picasso.with(context)
                        .load(image_trend)
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

        // Add the PublisherAdView to the view hierarchy.
        viewHolder.ad.addView(viewHolder.mPublisherAdView);

//        // Start loading the ad.   mz
//        viewHolder.mPublisherAdView.loadAd(publisherAdRequestBuilder.build());

        PublisherAdView mPublisherAdView1 = new PublisherAdView(context);
        mPublisherAdView1.setAdSizes(AdSize.MEDIUM_RECTANGLE);
        mPublisherAdView1.setAdUnitId("/14309701/and-en/programs/and-en.programs-det.mrec");
        PublisherAdRequest.Builder publisherAdRequestBuilder1 = new PublisherAdRequest.Builder();
        viewHolder.mAdView.addView(mPublisherAdView1);
        mPublisherAdView1.loadAd(publisherAdRequestBuilder1.build());


        viewHolder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

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
                        Toast.makeText(context, "Sorry, video not available !", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

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

    public void PopularVideos(final ViewGroup viewGroup)
    {

        //Popular Video in Program Listings in Horizontal View
        if (InternetConnection.checkConnection(context))
        {

            ApiService api = RetroClient.getApiService();
            Call<NewsCategoryList> call = api.getPopularVideosURL(pop_videos);
            call.enqueue(new Callback<NewsCategoryList>()
            {
                @Override
                public void onResponse(Call<NewsCategoryList> call, Response<NewsCategoryList> response)
                {

                    if (response.isSuccessful())
                    {

                        popular_list = response.body().getAlsoWatch();
//                        popular_adapter = new EditorsChoiceAdapter(context, popular_list);
                        six_pop_videos_horizontal_adapter = new TV_Shows_inside_horizontal_adapter(popular_list, context);
                        //RecyclerView for horizontal layout
                        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                        popular_recycle = (RecyclerView) viewGroup.findViewById(R.id.horizontal_view);
                        popular_recycle.setLayoutManager(layoutManager);
                        popular_recycle.setHasFixedSize(true);
                        popular_recycle.setAdapter(six_pop_videos_horizontal_adapter);
                        six_pop_videos_horizontal_adapter.notifyDataSetChanged();
                        notifyDataSetChanged();
//
                    }
                    else
                    {
                        Toast.makeText(context, "Something is wrong !", Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onFailure(Call<NewsCategoryList> call, Throwable t)
                {
                    if (t instanceof NullPointerException)
                    {
                        Toast.makeText(context.getApplicationContext(), "Something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                    if (t instanceof InternalError)
                    {
                        Toast.makeText(context.getApplicationContext(), "Oops , something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                    if (t instanceof InterruptedException)
                    {
                        Toast.makeText(context.getApplicationContext(), "Sorry , Time Out !", Toast.LENGTH_SHORT).show();
                    }
                    if (t instanceof IllegalStateException)
                    {
                        Toast.makeText(context.getApplicationContext(), "Something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else
        {
            Toast.makeText(context, "Internet Connection Not Available !", Toast.LENGTH_SHORT).show();
        }


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

            //mAdView = (PublisherAdView) view.findViewById(R.id.ad);
            imageView = (ImageView) view.findViewById(R.id.img);
            playbtn = (ImageView) view.findViewById(R.id.playicon);
            tv_name = (TextView) view.findViewById(R.id.title);
            tv_date = (TextView) view.findViewById(R.id.date);
            ad = (RelativeLayout) view.findViewById(R.id.ad);
            mAdView = (RelativeLayout) view.findViewById(R.id.ad_foot);

        }
    }

}