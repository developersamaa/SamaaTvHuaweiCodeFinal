package com.Samaatv.samaaapp3.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 4/8/2017.
 */
public class TVShows_EpisodeListing_Adapter extends RecyclerView.Adapter<TVShows_EpisodeListing_Adapter.ViewHolder>
{

    static final String KEY_VIDEO = "videourl";
    private static final String pop_videos = "https://www.samaa.tv/videos/jfeedltstprgrms/";
    final int TYPE_HEADER = 0;
    final int TYPE_HORIZONTAL = 1;
    final int TYPE_AD = 2;
    final int TYPE_CELL = 3;
    final int TYPE_FOOTER = 333;
    Context context;
    int type;
    AdSize adsize;
    String adunit;
    private ArrayList<Contact> contactList;
    private RecyclerView popular_recycle;
    private LinearLayoutManager layoutManager;
    private ArrayList<Contact> popular_list;
    private EditorsChoiceAdapter popular_adapter;

    public TVShows_EpisodeListing_Adapter(Context context, ArrayList<Contact> contactList)
    {
        this.contactList = contactList;
        this.context = context;
    }

    @Override
    public TVShows_EpisodeListing_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent_viewGroup, int viewType)
    {


        LayoutInflater inflater = LayoutInflater.from(parent_viewGroup.getContext());
//        type = getItemViewType(viewType); // to determine type of view.
        type = viewType; // to determine type of view.
//        returnViewTypes(inflater,parent_viewGroup,type);
//        return returnViewTypes(inflater,parent_viewGroup,viewType);

        if (type == TYPE_HEADER)
        {
            View headerView = inflater.inflate(R.layout.new_tvlist_item_card_big, parent_viewGroup, false);
            adsize = AdSize.MEDIUM_RECTANGLE;
            adunit = context.getString(R.string.tvshows_det_en_mrec);
            return new ViewHolder(headerView)
            {
            }; // view holder for header items
        }
        else if (type == TYPE_HORIZONTAL)
        {
            View horizon = inflater.inflate(R.layout.carousal_tv, parent_viewGroup, false);
            adsize = AdSize.MEDIUM_RECTANGLE;
            adunit = context.getString(R.string.tvshows_det_en_mrec);
            //Calling Popular Videos in Carousal
            popularVideos(parent_viewGroup);
            return new ViewHolder(horizon)
            {
            };

        }
        else if (type == TYPE_AD)
        {
            View ADView = inflater.inflate(R.layout.list_item_card_big_ad, parent_viewGroup, false);
            adsize = AdSize.MEDIUM_RECTANGLE;
            adunit = context.getString(R.string.tvshows_det_en_mrec);
            return new ViewHolder(ADView)
            {
            };

        }

        else if (type == TYPE_FOOTER)
        {
//            View ADView = inflater.inflate(R.layout.list_item_card_banner_adview, parent_viewGroup, false);
            View ADView = inflater.inflate(R.layout.new_tvlist_item_card_banner_adview, parent_viewGroup, false);
            adsize = AdSize.BANNER;
            adunit = context.getString(R.string.tvshows_en_lb_btf);
            return new ViewHolder(ADView)
            {
            };

        }
        else if (type == TYPE_CELL)
        {
//            View normalRow = inflater.inflate(R.layout.items_new, parent_viewGroup, false);
            View normalRow = inflater.inflate(R.layout.new_tvitems, parent_viewGroup, false);
            adsize = AdSize.MEDIUM_RECTANGLE;
            adunit = context.getString(R.string.tvshows_det_en_mrec);
            return new ViewHolder(normalRow)
            {
            }; // view holder for normal items
        }
        else
        {
//            View view = inflater.inflate(R.layout.items_new, parent_viewGroup, false);
            View view = inflater.inflate(R.layout.new_tvitems, parent_viewGroup, false);
            adsize = AdSize.MEDIUM_RECTANGLE;
            adunit = context.getString(R.string.tvshows_det_en_mrec);
            return new ViewHolder(view)
            {

            };
        }
    }

    private void popularVideos(final ViewGroup parent_viewGroup)
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
                        popular_adapter = new EditorsChoiceAdapter(context, popular_list);
                        //RecyclerView for horizontal layout
                        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                        popular_recycle = (RecyclerView) parent_viewGroup.findViewById(R.id.horizontal_view_tv_shows);
                        popular_recycle.setLayoutManager(layoutManager);
                        popular_recycle.setHasFixedSize(true);
                        popular_recycle.setAdapter(popular_adapter);

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


    @Override
    public void onBindViewHolder(TVShows_EpisodeListing_Adapter.ViewHolder holder, int position)
    {


        String title = contactList.get(position).getTitle();
        String pubDate = contactList.get(position).getDate();
        final String video = contactList.get(position).getVideo();
        final String image_trend = contactList.get(position).getImage();
        final String yvideo = contactList.get(position).getYTVideo();


//        holder.tv_name.setText(Html.fromHtml(title));
//        holder.tv_date.setText(pubDate);
//        Glide.with(context).load(image_trend)
//                           .error(R.drawable.logo_samaatv)
//                           .placeholder(R.drawable.logo_samaatv)
//                           .diskCacheStrategy(DiskCacheStrategy.ALL)
//                           .into(holder.imageView);
//        if(video!=null && !video.equals("None")) {
//            holder.playbtn.setVisibility(View.VISIBLE);
//        } else {
//            holder.playbtn.setVisibility(View.GONE);
//        }


        if (holder.getAdapterPosition() == TYPE_FOOTER)
        {
            adsize = AdSize.BANNER;
            adunit = context.getString(R.string.tvshows_en_lb_btf);
        }

        // Create a banner ad. The ad size and ad unit ID must be set before calling loadAd.
        holder.mPublisherAdView = new PublisherAdView(context);
        holder.mPublisherAdView.setAdSizes(adsize);
        holder.mPublisherAdView.setAdUnitId(adunit);
//
        // Create an ad request.
        PublisherAdRequest.Builder publisherAdRequestBuilder = new PublisherAdRequest.Builder();
        // Add the PublisherAdView to the view hierarchy.
//        holder.ad.addView(holder.mPublisherAdView);

//        // Start loading the ad.   mz
//        holder.mPublisherAdView.loadAd(publisherAdRequestBuilder.build());

        holder.itemView.setOnClickListener(new View.OnClickListener()
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
    public int getItemCount()
    {
        return contactList.size();
    }

    @Override
    public int getItemViewType(int position)
    {
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
//        return super.getItemViewType(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView tv_name, tv_date;
        ImageView imageView, playbtn;
        RelativeLayout ad;
        private PublisherAdView mPublisherAdView;

        //ye comment krdena agr  koi panga ho to
        public ViewHolder(View view)
        {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.img);
            playbtn = (ImageView) view.findViewById(R.id.playicon);
            tv_name = (TextView) view.findViewById(R.id.title_new_tv);
            tv_date = (TextView) view.findViewById(R.id.date_new_tv);
            ad = (RelativeLayout) view.findViewById(R.id.ad_new_tv);
        }
    }
}
