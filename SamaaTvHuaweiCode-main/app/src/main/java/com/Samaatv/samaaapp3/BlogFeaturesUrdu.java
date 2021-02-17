package com.Samaatv.samaaapp3;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.Samaatv.samaaapp3.adapter.BlogsAdapterUrdu;
import com.Samaatv.samaaapp3.adapter.MyNewsAdapterUrdu;
import com.Samaatv.samaaapp3.analytics.SamaaAppAnalytics;
import com.Samaatv.samaaapp3.api.ApiService;
import com.Samaatv.samaaapp3.api.RetroClient;
import com.Samaatv.samaaapp3.model.Contact;
import com.Samaatv.samaaapp3.model.NewsCategoryList;
import com.Samaatv.samaaapp3.model.ObjectBlogsCategoryList;
import com.Samaatv.samaaapp3.utils.DataCacheUrdu;
import com.Samaatv.samaaapp3.utils.InternetConnection;
import com.Samaatv.samaaapp3.utils.SamaaFirebaseAnalytics;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BlogFeaturesUrdu extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    static final boolean GRID_LAYOUT = false;
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    /// private RecyclerView mRecyclerView;
    static FrameLayout frame_footer;
    TextView titlerecent, daterecent;
    ImageView imagerecent, playbtn_recent;
    SwipeRefreshLayout swipeLayout;
    RecyclerView.LayoutManager layoutManager;
    //    TextView more_powergames, more_socialpulse, more_glitzglams, more_foodshood, more_sportsblogs;
//    TextView topblogs_head1,topblogs_head2, recentblogs_head1, recentblogs_head2, picblogs_head1, picblogs_head2, powergames_head1, powergames_head2,
//            videoblogs_head1, videoblogs_head2, socialpulse_head1, socialpulse_head2, glitzglams_head1, glitzglams_head2,foodshood_head1, foodshood_head2, sportsblogs_head1, sportsblogs_head2;
    String videourl_recent;
    CardView cardView;
    RecyclerView topblogs_recycle, picblogs_recycle, powergames_recycle, videoblogs_recycle, socialpulse_recycle, glitzglams_recycle, foodshood_recycle, sportsblogs_recycle;
    RelativeLayout blog_ad_head, blog_ad_below;
    NewsCategoryList tempList;
    String image;
    String d1;
    private BlogsAdapterUrdu powergames_adapter, socialpulse_adapter, glitzglams_adapter, foodshood_adapter, sportsblogs_adapter;
    private MyNewsAdapterUrdu topblogs_adapter, picblogs_adapter, videoblogs_adapter;
    private ArrayList<Contact> blog_list;
    private View parentView;
    private ArrayList<Contact> recent_blog;
    private ArrayList<Contact> top_blogs;
    private ArrayList<Contact> pic_blogs;
    private ArrayList<Contact> power_games;
    private ArrayList<Contact> video_blogs;
    private ArrayList<Contact> social_pulse;
    private ArrayList<Contact> glitz_glams;
    private ArrayList<Contact> food_shood;
    private ArrayList<Contact> sports_blogs;
    private Boolean isStarted = false;
    private Boolean isVisible = false;

    public BlogFeaturesUrdu() {
        // Required empty public constructor
    }

    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "Just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        isStarted = true;
        if (isVisible && isStarted) {
            fetchBlogsListPreference();
        }
    }

    private void fetchBlogsListPreference() {
        recent_blog = new ArrayList<>();
        top_blogs = new ArrayList<>();
        pic_blogs = new ArrayList<>();
        power_games = new ArrayList<>();
        video_blogs = new ArrayList<>();
        social_pulse = new ArrayList<>();
        glitz_glams = new ArrayList<>();
        food_shood = new ArrayList<>();
        sports_blogs = new ArrayList<>();

        tempList = DataCacheUrdu.retrieveBlogList(getActivity());

        if (tempList != null) {
            recent_blog = tempList.getRecentBlogs();
            top_blogs = tempList.getTopBlogs();
            pic_blogs = tempList.getPicBlogs();
            power_games = tempList.getPowerGames();
            video_blogs = tempList.getVideoBlogs();
            social_pulse = tempList.getSocialPulse();
            glitz_glams = tempList.getGlitzGlams();
            food_shood = tempList.getFoodShood();
            sports_blogs = tempList.getSportBlogs();

            //Setting value for single recent blogs news
            try {
                if (!recent_blog.isEmpty()) {
                    cardView.setVisibility(View.VISIBLE);
                    Log.d("TAGGG", "RECENT BLOGS NOT EMPTY");
                    titlerecent.setText(recent_blog.get(0).getTitle());
                    image = recent_blog.get(0).getImage();
                    d1 = recent_blog.get(0).getDate();
                    videourl_recent = recent_blog.get(0).getVideo();

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
                    Date testDate = null;
                    testDate = sdf.parse(d1);

                    if (testDate != null) {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
                        String newFormat = formatter.format(testDate);
                        System.out.println(".....Date..." + newFormat);

                        long milliseconds = testDate.getTime();
                        String gettime = getTimeAgo(milliseconds);
                        daterecent.setText(gettime);

                        if (image != null && image.length() > 0) {
                            Picasso.with(getActivity())
                                    .load(image)
                                    .error(R.drawable.logo_samaatv)
                                    .placeholder(R.drawable.logo_samaatv)
                                    .into(imagerecent);
                        }

                        if (videourl_recent != null && !videourl_recent.equals("None")) {
                            playbtn_recent.setVisibility(View.VISIBLE);
                        } else {
                            playbtn_recent.setVisibility(View.GONE);
                        }
                        cardView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                Bundle bundle = new Bundle();
                                bundle.putSerializable("array", recent_blog);
                                Intent intent = new Intent(getActivity(), Detail_Activity_Urdu.class);
                                intent.putExtras(bundle);
                                intent.putExtra("pos", 0);
                                getActivity().startActivity(intent);


                            }
                        });

                        //Settings Adapters to different categories of blogs

                        topblogs_adapter = new MyNewsAdapterUrdu(getActivity(), top_blogs, "blogs");
                        topblogs_recycle.setAdapter(topblogs_adapter);

                        picblogs_adapter = new MyNewsAdapterUrdu(getActivity(), pic_blogs, "blogs");
                        picblogs_recycle.setAdapter(picblogs_adapter);

                        powergames_adapter = new BlogsAdapterUrdu(getActivity(), power_games);
                        powergames_recycle.setAdapter(powergames_adapter);

                        videoblogs_adapter = new MyNewsAdapterUrdu(getActivity(), video_blogs, "blogs");
                        videoblogs_recycle.setAdapter(videoblogs_adapter);

                        socialpulse_adapter = new BlogsAdapterUrdu(getActivity(), social_pulse);
                        socialpulse_recycle.setAdapter(socialpulse_adapter);

                        glitzglams_adapter = new BlogsAdapterUrdu(getActivity(), glitz_glams);
                        glitzglams_recycle.setAdapter(glitzglams_adapter);

                        foodshood_adapter = new BlogsAdapterUrdu(getActivity(), food_shood);
                        foodshood_recycle.setAdapter(foodshood_adapter);

                        sportsblogs_adapter = new BlogsAdapterUrdu(getActivity(), sports_blogs);
                        sportsblogs_recycle.setAdapter(sportsblogs_adapter);
                    }
                } else {
                    cardView.setVisibility(View.GONE);
                    Log.d("IOE", "Recent Blogs Data are Empty");
                }

            } catch (Exception ioe) {
                Log.e("IOE", "index out ka bekar exception", ioe);
            }

//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
//            Date testDate = null;
//            try {
//                testDate = sdf.parse(d1);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }


            // end date format

//            if (testDate != null) {
//                SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
//                String newFormat = formatter.format(testDate);
//                System.out.println(".....Date..." + newFormat);
//
//                long milliseconds = testDate.getTime();
//                String gettime = getTimeAgo(milliseconds);
//                daterecent.setText(gettime);
//
//                Picasso.with(getActivity())
//                        .load(image)
//                        .error(R.drawable.logo_samaatv)
//                        .placeholder(R.drawable.logo_samaatv)
//                        .into(imagerecent);
//
//                if (videourl_recent != null && !videourl_recent.equals("None")) {
//                    playbtn_recent.setVisibility(View.VISIBLE);
//                } else {
//                    playbtn_recent.setVisibility(View.GONE);
//                }
//                cardView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("array", recent_blog);
//                        Intent intent = new Intent(getActivity(), Detail_Activity_Urdu.class);
//                        intent.putExtras(bundle);
//                        intent.putExtra("pos", 0);
//                        getActivity().startActivity(intent);
//
//
//                    }
//                });
//
//                //Settings Adapters to different categories of blogs
//
//                topblogs_adapter = new MyNewsAdapterUrdu(getActivity(), top_blogs, "blogs");
//                topblogs_recycle.setAdapter(topblogs_adapter);
//
//                picblogs_adapter = new MyNewsAdapterUrdu(getActivity(), pic_blogs, "blogs");
//                picblogs_recycle.setAdapter(picblogs_adapter);
//
//                powergames_adapter = new BlogsAdapterUrdu(getActivity(), power_games);
//                powergames_recycle.setAdapter(powergames_adapter);
//
//                videoblogs_adapter = new MyNewsAdapterUrdu(getActivity(), video_blogs, "blogs");
//                videoblogs_recycle.setAdapter(videoblogs_adapter);
//
//                socialpulse_adapter = new BlogsAdapterUrdu(getActivity(), social_pulse);
//                socialpulse_recycle.setAdapter(socialpulse_adapter);
//
//                glitzglams_adapter = new BlogsAdapterUrdu(getActivity(), glitz_glams);
//                glitzglams_recycle.setAdapter(glitzglams_adapter);
//
//                foodshood_adapter = new BlogsAdapterUrdu(getActivity(), food_shood);
//                foodshood_recycle.setAdapter(foodshood_adapter);
//
//                sportsblogs_adapter = new BlogsAdapterUrdu(getActivity(), sports_blogs);
//                sportsblogs_recycle.setAdapter(sportsblogs_adapter);
//            }

        } else {
            cardView.setVisibility(View.GONE);
            tempList = new NewsCategoryList();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //MainActivity.frameticker.setVisibility(View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = checkScreenAndSetLayout(inflater, container);
        return rootView;

    }

    private ViewGroup checkScreenAndSetLayout(LayoutInflater inflater, ViewGroup container) {

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
        ViewGroup rootView;
        if (diagonalInches >= 6.5) {
            // 6.5inch device or bigger
            rootView = (ViewGroup) inflater.inflate(R.layout.blogs_layout_urdu_large, container, false);
        } else {
            // smaller device
            rootView = (ViewGroup) inflater.inflate(R.layout.blogs_layout_urdu, container, false);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        playbtn_recent = (ImageView) view.findViewById(R.id.play_vdo);
        daterecent = (TextView) view.findViewById(R.id.date_recblog);
        imagerecent = (ImageView) view.findViewById(R.id.img_recblogs);
        titlerecent = (TextView) view.findViewById(R.id.title_recblog);
        topblogs_recycle = (RecyclerView) view.findViewById(R.id.list_topblogs);
        picblogs_recycle = (RecyclerView) view.findViewById(R.id.list_picblogs);
        powergames_recycle = (RecyclerView) view.findViewById(R.id.list_powergames);
        videoblogs_recycle = (RecyclerView) view.findViewById(R.id.list_videoblogs);
        socialpulse_recycle = (RecyclerView) view.findViewById(R.id.list_socialpulse);
        glitzglams_recycle = (RecyclerView) view.findViewById(R.id.list_glitzglams);
        foodshood_recycle = (RecyclerView) view.findViewById(R.id.list_foodshood);
        sportsblogs_recycle = (RecyclerView) view.findViewById(R.id.list_sportsblogs);
        cardView = (CardView) view.findViewById(R.id.card_view1);

//        recentblogs_head1=(TextView)view.findViewById(R.id.recblog_head1);
//        recentblogs_head2=(TextView)view.findViewById(R.id.recblog_head2);
//
//        topblogs_head1=(TextView)view.findViewById(R.id.topblogs_head1);
//        topblogs_head2=(TextView)view.findViewById(R.id.topblogs_head2);
//
//        picblogs_head1=(TextView)view.findViewById(R.id.picblogs_head1);
//        picblogs_head2=(TextView)view.findViewById(R.id.picblogs_head2);
//
//        powergames_head1=(TextView)view.findViewById(R.id.power_head1);
//        powergames_head2=(TextView)view.findViewById(R.id.power_head2);
//
//
//        videoblogs_head1=(TextView)view.findViewById(R.id.videoblogs_head1);
//        videoblogs_head2=(TextView)view.findViewById(R.id.videoblogs_head2);
//
//        socialpulse_head1=(TextView)view.findViewById(R.id.socialpulse_head1);
//        socialpulse_head2=(TextView)view.findViewById(R.id.socialpulse_head2);
//
//        glitzglams_head1=(TextView)view.findViewById(R.id.glitzglams_head1);
//        glitzglams_head2=(TextView)view.findViewById(R.id.glitzglams_head2);
//
//        foodshood_head1=(TextView)view.findViewById(R.id.foodshood_head1);
//        foodshood_head2=(TextView)view.findViewById(R.id.foodshood_head2);
//
//        sportsblogs_head1=(TextView)view.findViewById(R.id.sportsblogs_head1);
//        sportsblogs_head2=(TextView)view.findViewById(R.id.sportsblogs_head2);

        blog_ad_head = (RelativeLayout) view.findViewById(R.id.blog_ad_head);
        blog_ad_below = (RelativeLayout) view.findViewById(R.id.blog_ad_below);

//        more_powergames = (TextView) view.findViewById(R.id.more_powergames);
//        more_socialpulse = (TextView) view.findViewById(R.id.more_socialpulse);
//
//        more_glitzglams = (TextView) view.findViewById(R.id.more_glitzglams);
//        more_foodshood = (TextView) view.findViewById(R.id.more_foodshood);
//
//        more_sportsblogs = (TextView) view.findViewById(R.id.more_sportsblogs);
        //more_sports = (TextView) rootView.findViewById(R.id.more_sport);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        LinearLayoutManager layoutManager4 = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);

        LinearLayoutManager layoutManager5 = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        LinearLayoutManager layoutManager6 = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        LinearLayoutManager layoutManager7 = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        LinearLayoutManager layoutManager8 = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        //topblogs horizontal layout
        topblogs_recycle.setLayoutManager(layoutManager);
        // topblogs_recycle.setNestedScrollingEnabled(false);
        topblogs_recycle.setHasFixedSize(true);

        //picblogs horizontal layout
        picblogs_recycle.setLayoutManager(layoutManager2);
        //  picblogs_recycle.setNestedScrollingEnabled(false);
        picblogs_recycle.setHasFixedSize(true);

        //powergames
        powergames_recycle.setLayoutManager(layoutManager3);
        powergames_recycle.setNestedScrollingEnabled(false);
        powergames_recycle.setHasFixedSize(true);

        //videoblogs horizontal layout
        videoblogs_recycle.setLayoutManager(layoutManager4);
        //  videoblogs_recycle.setNestedScrollingEnabled(false);
        videoblogs_recycle.setHasFixedSize(true);

        //socialpulse
        socialpulse_recycle.setLayoutManager(layoutManager5);
        socialpulse_recycle.setNestedScrollingEnabled(false);
        socialpulse_recycle.setHasFixedSize(true);

        //glitzglams
        glitzglams_recycle.setLayoutManager(layoutManager6);
        glitzglams_recycle.setNestedScrollingEnabled(false);
        glitzglams_recycle.setHasFixedSize(true);

        //foodshood
        foodshood_recycle.setLayoutManager(layoutManager7);
        foodshood_recycle.setNestedScrollingEnabled(false);
        foodshood_recycle.setHasFixedSize(true);

        //sportsblogs
        sportsblogs_recycle.setLayoutManager(layoutManager8);
        sportsblogs_recycle.setNestedScrollingEnabled(false);
        sportsblogs_recycle.setHasFixedSize(true);

//        //Applying two fonts for news headings
//        Typeface face= Typeface.createFromAsset(getActivity().getAssets(), "font/Heading1.ttf");
//        recentblogs_head1.setTypeface(face);
//
//        Typeface face1= Typeface.createFromAsset(getActivity().getAssets(), "font/Heading2.ttf");
//        recentblogs_head2.setTypeface(face1);
//
//        Typeface face3= Typeface.createFromAsset(getActivity().getAssets(), "font/Heading1.ttf");
//        topblogs_head1.setTypeface(face3);
//
//        Typeface face4= Typeface.createFromAsset(getActivity().getAssets(), "font/Heading2.ttf");
//        topblogs_head2.setTypeface(face4);
//
//        Typeface face5= Typeface.createFromAsset(getActivity().getAssets(), "font/Heading1.ttf");
//        picblogs_head1.setTypeface(face5);
//
//        Typeface face6= Typeface.createFromAsset(getActivity().getAssets(), "font/Heading2.ttf");
//        picblogs_head2.setTypeface(face6);
//
//        Typeface face7= Typeface.createFromAsset(getActivity().getAssets(), "font/Heading1.ttf");
//        powergames_head1.setTypeface(face7);
//
//        Typeface face8= Typeface.createFromAsset(getActivity().getAssets(), "font/Heading2.ttf");
//        powergames_head2.setTypeface(face8);
//
//        Typeface face9= Typeface.createFromAsset(getActivity().getAssets(), "font/Heading1.ttf");
//        videoblogs_head1.setTypeface(face9);
//
//        Typeface face10= Typeface.createFromAsset(getActivity().getAssets(), "font/Heading2.ttf");
//        videoblogs_head2.setTypeface(face10);
//
//        Typeface face11= Typeface.createFromAsset(getActivity().getAssets(), "font/Heading1.ttf");
//        socialpulse_head1.setTypeface(face11);
//
//        Typeface face12= Typeface.createFromAsset(getActivity().getAssets(), "font/Heading2.ttf");
//        socialpulse_head2.setTypeface(face12);
//
//        Typeface face13= Typeface.createFromAsset(getActivity().getAssets(), "font/Heading1.ttf");
//        glitzglams_head1.setTypeface(face13);
//
//        Typeface face14= Typeface.createFromAsset(getActivity().getAssets(), "font/Heading2.ttf");
//        glitzglams_head2.setTypeface(face14);
//
//        Typeface face15= Typeface.createFromAsset(getActivity().getAssets(), "font/Heading1.ttf");
//        foodshood_head1.setTypeface(face15);
//
//        Typeface face16= Typeface.createFromAsset(getActivity().getAssets(), "font/Heading2.ttf");
//        foodshood_head2.setTypeface(face16);
//
//        Typeface face17= Typeface.createFromAsset(getActivity().getAssets(), "font/Heading1.ttf");
//        sportsblogs_head1.setTypeface(face17);
//
//        Typeface face18= Typeface.createFromAsset(getActivity().getAssets(), "font/Heading2.ttf");
//        sportsblogs_head2.setTypeface(face18);


//        more_powergames.setOnClickListener(new View.OnClickListener()
//        {
//            public void onClick(View view)
//            {
//
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("array", power_games);
//                Intent intent = new Intent(getActivity(), Detail_Activity_MoreBlogsUrdu.class);
//                intent.putExtras(bundle);
//                intent.putExtra("blog_cat", "power_games");
//                startActivity(intent);
//            }
//        });
//
//        more_socialpulse.setOnClickListener(new View.OnClickListener()
//        {
//            public void onClick(View view)
//            {
//
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("array", social_pulse);
//                Intent intent = new Intent(getActivity(), Detail_Activity_MoreBlogsUrdu.class);
//                intent.putExtras(bundle);
//                intent.putExtra("blog_cat", "social_pulse");
//                startActivity(intent);
//            }
//        });
//
//        more_glitzglams.setOnClickListener(new View.OnClickListener()
//        {
//            public void onClick(View view)
//            {
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("array", glitz_glams);
//                Intent intent = new Intent(getActivity(), Detail_Activity_MoreBlogsUrdu.class);
//                intent.putExtras(bundle);
//                intent.putExtra("blog_cat", "glitz_glams");
//                startActivity(intent);
//            }
//        });
//
//        more_foodshood.setOnClickListener(new View.OnClickListener()
//        {
//            public void onClick(View view)
//            {
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("array", food_shood);
//                Intent intent = new Intent(getActivity(), Detail_Activity_MoreBlogsUrdu.class);
//                intent.putExtras(bundle);
//                intent.putExtra("blog_cat", "food_shood");
//                startActivity(intent);
//            }
//        });
//
//        more_sportsblogs.setOnClickListener(new View.OnClickListener()
//        {
//            public void onClick(View view)
//            {
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("array", sports_blogs);
//                Intent intent = new Intent(getActivity(), Detail_Activity_MoreBlogsUrdu.class);
//                intent.putExtras(bundle);
//                intent.putExtra("blog_cat", "sports_blogs");
//                startActivity(intent);
//            }
//        });

        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeLayout.setOnRefreshListener(this);

        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.toolbar_layoutcolor),
                getResources().getColor(R.color.heading1_black),
                getResources().getColor(R.color.heading2_black),
                getResources().getColor(R.color.blue));

        frame_footer = (FrameLayout) view.findViewById(R.id.footer);
        setAd_load();
    }

    private void setAd_load() {
        PublisherAdView mPublisherAdView = new PublisherAdView(getActivity());
        mPublisherAdView.setAdSizes(AdSize.BANNER, new AdSize(300, 50), new AdSize(300, 100), new AdSize(320, 50), new AdSize(320, 100));
        mPublisherAdView.setAdUnitId("/14309701/and-ur/blogs/and-ur.blogs.mrec-1");
        PublisherAdRequest.Builder publisherAdRequestBuilder = new PublisherAdRequest.Builder();
        blog_ad_head.addView(mPublisherAdView);
        mPublisherAdView.loadAd(publisherAdRequestBuilder.build());

        PublisherAdView mPublisherAdView1 = new PublisherAdView(getActivity());
        mPublisherAdView1.setAdSizes(AdSize.MEDIUM_RECTANGLE, new AdSize(300, 50), new AdSize(300, 100), new AdSize(320, 50), new AdSize(320, 100));
        mPublisherAdView1.setAdUnitId("/14309701/and-ur/blogs/and-ur.blogs.mrec-2");
        PublisherAdRequest.Builder publisherAdRequestBuilder1 = new PublisherAdRequest.Builder();
        blog_ad_below.addView(mPublisherAdView1);
        mPublisherAdView1.loadAd(publisherAdRequestBuilder1.build());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager = new GridLayoutManager(getActivity(), 2);
        } else {
            layoutManager = new LinearLayoutManager(getActivity());
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        isVisible = isVisibleToUser;
        if (isStarted && isVisible) {

            fetchBlogsListPreference();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SamaaAppAnalytics.getInstance().trackScreenView("Blogs Screen Urdu");
                SamaaFirebaseAnalytics.syncSamaaAnalytics(getActivity(), "Blogs Screen Urdu");
            }
        }, 6000);

    }

    @Override
    public void onRefresh() {
        fetchBlogList();
    }

    private void fetchBlogList() {
        if (InternetConnection.checkConnection(getActivity())) {

            ApiService api = RetroClient.getApiService();
            Call<ObjectBlogsCategoryList> call = api.getBlogDataUrdu();
            call.enqueue(new Callback<ObjectBlogsCategoryList>() {
                @Override
                public void onResponse(Call<ObjectBlogsCategoryList> call, Response<ObjectBlogsCategoryList> response) {

                    if (response.isSuccessful()) {
                        tempList = response.body().getMy_blogs();

                        if (tempList != null) {
                            DataCacheUrdu.saveBlogList(getActivity(), tempList);
                            recent_blog = tempList.getRecentBlogs();
                            top_blogs = tempList.getTopBlogs();
                            pic_blogs = tempList.getPicBlogs();
                            power_games = tempList.getPowerGames();
                            video_blogs = tempList.getVideoBlogs();
                            social_pulse = tempList.getSocialPulse();
                            glitz_glams = tempList.getGlitzGlams();
                            food_shood = tempList.getFoodShood();
                            sports_blogs = tempList.getSportBlogs();

                            //Setting value for single recent blogs news
                            try {
                                if (recent_blog != null && recent_blog.size() > 0) {
                                    titlerecent.setText(recent_blog.get(0).getTitle());
                                    image = recent_blog.get(0).getImage();
                                    d1 = recent_blog.get(0).getDate();
                                    videourl_recent = recent_blog.get(0).getVideo();


                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
                                    Date testDate = null;
                                    try {
                                        testDate = sdf.parse(d1);
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }

                                    SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
                                    String newFormat = formatter.format(testDate);
                                    System.out.println(".....Date..." + newFormat);
                                    // end date format

                                    long milliseconds = testDate.getTime();
                                    String gettime = getTimeAgo(milliseconds);
                                    daterecent.setText(gettime);
                                    if (image != null && image.length() > 0) {
                                        Picasso.with(getActivity())
                                                .load(image)
                                                .error(R.drawable.logo_samaatv)
                                                .placeholder(R.drawable.logo_samaatv)
                                                .into(imagerecent);
                                    }


                                    if (videourl_recent != null && !videourl_recent.equals("None")) {
                                        playbtn_recent.setVisibility(View.VISIBLE);
                                    } else {
                                        playbtn_recent.setVisibility(View.GONE);
                                    }
                                    cardView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Bundle bundle = new Bundle();
                                            bundle.putSerializable("array", recent_blog);
                                            Intent intent = new Intent(getActivity(), Detail_Activity_Urdu.class);
                                            intent.putExtras(bundle);
                                            intent.putExtra("pos", 0);
                                            getActivity().startActivity(intent);
                                        }
                                    });

                                }

                                //Settings Adapters to different categories of blogs

                                if (top_blogs != null && top_blogs.size() > 0) {
                                    topblogs_adapter = new MyNewsAdapterUrdu(getActivity(), top_blogs, "blogs");
                                    topblogs_recycle.setAdapter(topblogs_adapter);
                                }

                                if (pic_blogs != null && pic_blogs.size() > 0) {
                                    picblogs_adapter = new MyNewsAdapterUrdu(getActivity(), pic_blogs, "blogs");
                                    picblogs_recycle.setAdapter(picblogs_adapter);
                                }
                                if (power_games != null && power_games.size() > 0) {
                                    powergames_adapter = new BlogsAdapterUrdu(getActivity(), power_games);
                                    powergames_recycle.setAdapter(powergames_adapter);
                                }
                                if (video_blogs != null && video_blogs.size() > 0) {
                                    videoblogs_adapter = new MyNewsAdapterUrdu(getActivity(), video_blogs, "blogs");
                                    videoblogs_recycle.setAdapter(videoblogs_adapter);
                                }
                                if (social_pulse != null && social_pulse.size() > 0) {
                                    socialpulse_adapter = new BlogsAdapterUrdu(getActivity(), social_pulse);
                                    socialpulse_recycle.setAdapter(socialpulse_adapter);
                                }
                                if (glitz_glams != null && glitz_glams.size() > 0) {
                                    glitzglams_adapter = new BlogsAdapterUrdu(getActivity(), glitz_glams);
                                    glitzglams_recycle.setAdapter(glitzglams_adapter);
                                }
                                if (food_shood != null && food_shood.size() > 0) {
                                    foodshood_adapter = new BlogsAdapterUrdu(getActivity(), food_shood);
                                    foodshood_recycle.setAdapter(foodshood_adapter);
                                }
                                if (sports_blogs != null && sports_blogs.size() > 0) {
                                    sportsblogs_adapter = new BlogsAdapterUrdu(getActivity(), sports_blogs);
                                    sportsblogs_recycle.setAdapter(sportsblogs_adapter);
                                }
                                swipeLayout.setRefreshing(false);
                            } catch (Exception ioe) {
                                Log.e("IOE", "index out ka bekar exception", ioe);
                            }

                        } else {
                            tempList = new NewsCategoryList();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onFailure(Call<ObjectBlogsCategoryList> call, Throwable t) {

                    if (t instanceof NullPointerException) {
                        Toast.makeText(getActivity(), "Something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                    if (t instanceof InternalError) {
                        Toast.makeText(getActivity(), "Oops , something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                    if (t instanceof InterruptedException) {
                        Toast.makeText(getActivity(), "Sorry , Time Out !", Toast.LENGTH_SHORT).show();
                    }
                    if (t instanceof IllegalStateException) {
                        Toast.makeText(getActivity(), "Something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(getActivity(), "Internet Connection Not Available !", Toast.LENGTH_SHORT).show();
        }
    }
}