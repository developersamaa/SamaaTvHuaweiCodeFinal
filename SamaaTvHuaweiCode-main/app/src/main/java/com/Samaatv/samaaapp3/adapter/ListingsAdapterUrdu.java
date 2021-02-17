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

import com.Samaatv.samaaapp3.Detail_Activity_Urdu;
import com.Samaatv.samaaapp3.R;
import com.Samaatv.samaaapp3.model.Contact;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.squareup.picasso.Picasso;
import com.taboola.android.TaboolaWidget;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListingsAdapterUrdu extends RecyclerView.Adapter<ListingsAdapterUrdu.ViewHolder>
{
    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;
    static final int TYPE_AD = 2;
    static final int TYPE_FOOT = 3;
    static final int TYPE_AD_TABOOLA = 4;
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    Context context;
    String pubDate;
    String video;
    String link;
    String image;
    String categoryTag;
    int viewtype;
    String newsCatTag;
    private ArrayList<Contact> contactList;
    private PublisherAdView mAdView;

    public ListingsAdapterUrdu(Context context, ArrayList<Contact> contactList)
    {
        this.contactList = contactList;
        this.context = context;
    }

    public ListingsAdapterUrdu(Context context, ArrayList<Contact> contactList, String news_id)
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
    public ListingsAdapterUrdu.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
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
            if (i == TYPE_HEADER)
            {
                View headerView = inflater.inflate(R.layout.list_item_card_big_urdu_headview_large, viewGroup, false);
                return new ViewHolder(headerView)
                {
                }; // view holder for header items
            }

            else if (i == TYPE_AD_TABOOLA)
            {
                View ADView = inflater.inflate(R.layout.list_item_card_taboola_large_urdu, viewGroup, false);
                return new ViewHolder(ADView)
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
            else if (i == TYPE_FOOT)
            {
                View view = inflater.inflate(R.layout.list_item_card_big_urdu_new_large, viewGroup, false);
                return new ViewHolder(view)
                {
                };
            }
            else
            {
                View view = inflater.inflate(R.layout.list_item_card_big_urdu_new_large, viewGroup, false);
                return new ViewHolder(view)
                {
                };
            }
        }
        else
        {
            if (i == TYPE_HEADER)
            {
                View headerView = inflater.inflate(R.layout.list_item_card_big_urdu_headview, viewGroup, false);
                return new ViewHolder(headerView)
                {
                }; // view holder for header items
            }

            else if (i == TYPE_AD_TABOOLA)
            {
                View ADView = inflater.inflate(R.layout.list_item_card_taboola_urdu, viewGroup, false);
                return new ViewHolder(ADView)
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
            else if (i == TYPE_FOOT)
            {
                View view = inflater.inflate(R.layout.list_item_card_big_urdu_new, viewGroup, false);
                return new ViewHolder(view)
                {
                };
            }
            else
            {
                View view = inflater.inflate(R.layout.list_item_card_big_urdu_new, viewGroup, false);
                return new ViewHolder(view)
                {
                };
            }
        }
    }

    @Override
    public void onBindViewHolder(ListingsAdapterUrdu.ViewHolder viewHolder, final int i)
    {

        try
        {
            viewHolder.tv_name.setText(contactList.get(i).getTitle());
            pubDate = contactList.get(i).getDate();
            video = contactList.get(i).getVideo();
            link = contactList.get(i).getLink();
            image = contactList.get(i).getImage();
            categoryTag = contactList.get(i).getCategory();
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

        implTaboolaAd(viewHolder);
        setAndProcessingAd(viewHolder);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Bundle bundle = new Bundle();
                bundle.putSerializable("array", contactList);
                Intent intent = new Intent(context, Detail_Activity_Urdu.class);
                intent.putExtras(bundle);
                intent.putExtra("pos", i);
                intent.putExtra("newsCatTag", newsCatTag);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    private void implTaboolaAd(ViewHolder viewHolder)
    {
        viewHolder.taboolaWidget.setPublisher("samaa-androidapp-sdkstandard")
                .setMode("thumbnails-a")
                .setPlacement("App Below Article Thumbnails")
                .setPageUrl("https://play.google.com/store/apps/details?id=com.Samaatv.samaaapp3")
                .setPageType("article")
                .setTargetType("mix");
        viewHolder.taboolaWidget.setInterceptScroll(true);
        viewHolder.taboolaWidget.setMinimumHeight(viewHolder.taboolaWidget.getHeight());
        viewHolder.taboolaWidget.fetchContent();
    }

    private void setAndProcessingAd(ViewHolder viewHolder)
    {

        if (newsCatTag.contains("National") || newsCatTag.contains("national"))
        {

            PublisherAdView mPublisherAdView = new PublisherAdView(context);
            mPublisherAdView.setAdSizes(AdSize.BANNER, new AdSize(300, 100), new AdSize(300, 50), new AdSize(300, 75), new AdSize(300, 144), new AdSize(320, 100), new AdSize(320, 50));
            mPublisherAdView.setAdUnitId("/14309701/and-ur/pakistan/and-ur.pakistan.mrec-1");
            PublisherAdRequest.Builder publisherAdRequestBuilder = new PublisherAdRequest.Builder();
            viewHolder.mAdView.addView(mPublisherAdView);
            mPublisherAdView.loadAd(publisherAdRequestBuilder.build());

            PublisherAdView mPublisherAdView1 = new PublisherAdView(context);
            mPublisherAdView1.setAdSizes(AdSize.MEDIUM_RECTANGLE);
            mPublisherAdView1.setAdUnitId("/14309701/and-ur/pakistan/and-ur.pakistan.mrec-2");
            PublisherAdRequest.Builder publisherAdRequestBuilder1 = new PublisherAdRequest.Builder();
            viewHolder.ad.addView(mPublisherAdView1);
            mPublisherAdView1.loadAd(publisherAdRequestBuilder1.build());

        }

        if (newsCatTag.contains("Global") || newsCatTag.contains("global") || newsCatTag.contains("International") || newsCatTag.contains("international"))
        {

            PublisherAdView mPublisherAdView = new PublisherAdView(context);
            mPublisherAdView.setAdSizes(AdSize.BANNER, new AdSize(300, 100), new AdSize(300, 50), new AdSize(300, 75), new AdSize(300, 144), new AdSize(320, 100), new AdSize(320, 50));
            mPublisherAdView.setAdUnitId("/14309701/and-ur/global/and-ur.global.mrec-1");
            PublisherAdRequest.Builder publisherAdRequestBuilder = new PublisherAdRequest.Builder();
            viewHolder.mAdView.addView(mPublisherAdView);
            mPublisherAdView.loadAd(publisherAdRequestBuilder.build());

            PublisherAdView mPublisherAdView1 = new PublisherAdView(context);
            mPublisherAdView1.setAdSizes(AdSize.MEDIUM_RECTANGLE);
            mPublisherAdView1.setAdUnitId("/14309701/and-ur/global/and-ur.global.mrec-3");
            PublisherAdRequest.Builder publisherAdRequestBuilder1 = new PublisherAdRequest.Builder();
            viewHolder.ad.addView(mPublisherAdView1);
            mPublisherAdView1.loadAd(publisherAdRequestBuilder1.build());

        }

        if (newsCatTag.contains("Economy") || newsCatTag.contains("economy"))
        {

            PublisherAdView mPublisherAdView = new PublisherAdView(context);
            mPublisherAdView.setAdSizes(AdSize.BANNER, new AdSize(300, 100), new AdSize(300, 50), new AdSize(300, 75), new AdSize(300, 144), new AdSize(320, 100), new AdSize(320, 50));
            mPublisherAdView.setAdUnitId("/14309701/and-ur/economy/and-ur.economy.mrec-1");
            PublisherAdRequest.Builder publisherAdRequestBuilder = new PublisherAdRequest.Builder();
            viewHolder.mAdView.addView(mPublisherAdView);
            mPublisherAdView.loadAd(publisherAdRequestBuilder.build());

            PublisherAdView mPublisherAdView1 = new PublisherAdView(context);
            mPublisherAdView1.setAdSizes(AdSize.MEDIUM_RECTANGLE);
            mPublisherAdView1.setAdUnitId("/14309701/and-ur/economy/and-ur.economy.mrec-4");
            PublisherAdRequest.Builder publisherAdRequestBuilder1 = new PublisherAdRequest.Builder();
            viewHolder.ad.addView(mPublisherAdView1);
            mPublisherAdView1.loadAd(publisherAdRequestBuilder1.build());
        }

        if (newsCatTag.contains("Sports") || newsCatTag.contains("sports") || categoryTag.contains("Sports") || categoryTag.contains("sports"))
        {

            PublisherAdView mPublisherAdView = new PublisherAdView(context);
            mPublisherAdView.setAdSizes(AdSize.BANNER, new AdSize(300, 100), new AdSize(300, 50), new AdSize(300, 75), new AdSize(300, 144), new AdSize(320, 100), new AdSize(320, 50));
            mPublisherAdView.setAdUnitId("/14309701/and-ur/sports/and-ur.sports.mrec-1");
            PublisherAdRequest.Builder publisherAdRequestBuilder = new PublisherAdRequest.Builder();
            viewHolder.mAdView.addView(mPublisherAdView);
            mPublisherAdView.loadAd(publisherAdRequestBuilder.build());

            PublisherAdView mPublisherAdView1 = new PublisherAdView(context);
            mPublisherAdView1.setAdSizes(AdSize.MEDIUM_RECTANGLE);
            mPublisherAdView1.setAdUnitId("/14309701/and-ur/sports/and-ur.sports.mrec-5");
            PublisherAdRequest.Builder publisherAdRequestBuilder1 = new PublisherAdRequest.Builder();
            viewHolder.ad.addView(mPublisherAdView1);
            mPublisherAdView1.loadAd(publisherAdRequestBuilder1.build());
        }

        if (newsCatTag.contains("Entertainment") || newsCatTag.contains("entertainment") || categoryTag.contains("Culture") || categoryTag.contains("culture"))
        {

            PublisherAdView mPublisherAdView = new PublisherAdView(context);
            mPublisherAdView.setAdSizes(AdSize.BANNER, new AdSize(300, 100), new AdSize(300, 50), new AdSize(300, 75), new AdSize(300, 144), new AdSize(320, 100), new AdSize(320, 50));
            mPublisherAdView.setAdUnitId("/14309701/and-ur/entertainment/and-ur.entertainment.mrec-1");
            PublisherAdRequest.Builder publisherAdRequestBuilder = new PublisherAdRequest.Builder();
            viewHolder.mAdView.addView(mPublisherAdView);
            mPublisherAdView.loadAd(publisherAdRequestBuilder.build());

            PublisherAdView mPublisherAdView1 = new PublisherAdView(context);
            mPublisherAdView1.setAdSizes(AdSize.MEDIUM_RECTANGLE);
            mPublisherAdView1.setAdUnitId("/14309701/and-ur/entertainment/and-ur.entertainment.mrec-6");
            PublisherAdRequest.Builder publisherAdRequestBuilder1 = new PublisherAdRequest.Builder();
            viewHolder.ad.addView(mPublisherAdView1);
            mPublisherAdView1.loadAd(publisherAdRequestBuilder1.build());
        }

        if (newsCatTag.contains("Blogs") || newsCatTag.contains("blogs"))
        {

            PublisherAdView mPublisherAdView = new PublisherAdView(context);
            mPublisherAdView.setAdSizes(AdSize.BANNER, new AdSize(300, 100), new AdSize(300, 50), new AdSize(300, 75), new AdSize(300, 144), new AdSize(320, 100), new AdSize(320, 50));
            mPublisherAdView.setAdUnitId("/14309701/and-ur/blogs/and-ur.blogs.mrec-1");
            PublisherAdRequest.Builder publisherAdRequestBuilder = new PublisherAdRequest.Builder();
            viewHolder.mAdView.addView(mPublisherAdView);
            mPublisherAdView.loadAd(publisherAdRequestBuilder.build());

            PublisherAdView mPublisherAdView1 = new PublisherAdView(context);
            mPublisherAdView1.setAdSizes(AdSize.MEDIUM_RECTANGLE);
            mPublisherAdView1.setAdUnitId("/14309701/and-ur/blogs/and-ur.blogs.mrec-2");
            PublisherAdRequest.Builder publisherAdRequestBuilder1 = new PublisherAdRequest.Builder();
            viewHolder.ad.addView(mPublisherAdView1);
            mPublisherAdView1.loadAd(publisherAdRequestBuilder1.build());
        }
    }

    @Override
    public int getItemViewType(int position)
    {

        if (position == 0)
        {
            return TYPE_HEADER;
        }
//        else if ((position+1)%5==0) {
//            return TYPE_AD;
//        }
        else if ((position + 1) % 5 == 0)
        {
            if ((position + 1) == 5)
            {
                return TYPE_AD_TABOOLA;
            }
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
        RelativeLayout ad, mAdView;
        TaboolaWidget taboolaWidget;
        private TextView tv_name, tv_date, tv_api_level;

        public ViewHolder(View view)
        {
            super(view);

            imageView = (ImageView) view.findViewById(R.id.img);
            playbtn = (ImageView) view.findViewById(R.id.playicon);
            tv_name = (TextView) view.findViewById(R.id.title);
            tv_date = (TextView) view.findViewById(R.id.date);
            ad = (RelativeLayout) view.findViewById(R.id.ad);
            mAdView = (RelativeLayout) view.findViewById(R.id.ad_head);
            taboolaWidget = (TaboolaWidget) view.findViewById(R.id.taboola_view);
        }
    }

}