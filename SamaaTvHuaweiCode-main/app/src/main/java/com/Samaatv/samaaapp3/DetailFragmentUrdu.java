package com.Samaatv.samaaapp3;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.Samaatv.samaaapp3.adapter.SeeAlsoAdapterUrdu;
import com.Samaatv.samaaapp3.analytics.SamaaAppAnalytics;
import com.Samaatv.samaaapp3.api.ApiService;
import com.Samaatv.samaaapp3.api.RetroClient;
import com.Samaatv.samaaapp3.model.Contact;
import com.Samaatv.samaaapp3.model.NewsCategoryList;
import com.Samaatv.samaaapp3.utils.InternetConnection;
import com.Samaatv.samaaapp3.utils.SamaaFirebaseAnalytics;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.squareup.picasso.Picasso;
import com.taboola.android.TaboolaWidget;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFragmentUrdu extends Fragment
{
    static final String KEY_VIDEO = "videourl";
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    static String t1, d1, desc1, videourl, link, vmob;
    String ytvideo;
    ProgressBar progress;
    int imageNumber = 1; //int to check which image is displayed
    String RelNews = "";
    JSONArray rel_news = null;
    RecyclerView rel_news_recycle;
    String arrayjson;
    ScrollView scroller;
    String cat;
    TextView title, desc, desc2, date;
    ImageView image, playbtn;
    JSONArray array = null;
    String newsCatTag;
    RecyclerView rel_recycle;
    String image_trend;
    TextView outputTextView;
    JCVideoPlayerStandard jcVideoPlayerStandard;
    private ArrayList<Contact> array_detail;
    private View parentView;
    private RecyclerView.Adapter mAdapter_rel_news;
    private String TAG = DetailFragmentUrdu.class.getSimpleName();
    private RelativeLayout mAdView;
    private RelativeLayout mAdView1;
    private int fPos;
    private SeeAlsoAdapterUrdu rel_adapter;
    private ArrayList<Contact> rel_list;
    //    private static JWPlayerView mPlayerView;
    private TaboolaWidget taboolaWidget;
    private Html.ImageGetter imgGetter = new Html.ImageGetter()
    {

        public Drawable getDrawable(String source)
        {
            Drawable drawable = null;
            if (imageNumber == 1)
            {
                drawable = getResources().getDrawable(R.drawable.logo_samaatv);
                ++imageNumber;
            }
            else
            {
                drawable = getResources().getDrawable(R.drawable.logo_samaatv);
            }
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
                    .getIntrinsicHeight());

            return drawable;
        }
    };

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
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        array_detail = (ArrayList<Contact>) getArguments().getSerializable("array");
        fPos = getArguments().getInt("pos");
        newsCatTag = getArguments().getString("newsCatTag");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        ViewGroup rootView = checkScreenAndSetLayout(inflater, container);

        scroller = (ScrollView) rootView.findViewById(R.id.parent_view);
        progress = (ProgressBar) rootView.findViewById(R.id.progressBar2);
        title = (TextView) rootView.findViewById(R.id.title_detail);
        desc = (TextView) rootView.findViewById(R.id.desc_detail);
        desc2 = (TextView) rootView.findViewById(R.id.desc_detail2);
        date = (TextView) rootView.findViewById(R.id.date_detail);
        image = (ImageView) rootView.findViewById(R.id.img_detail);
        playbtn = (ImageView) rootView.findViewById(R.id.play_vdo);
        parentView = rootView.findViewById(R.id.parent_view);
        outputTextView = (TextView) rootView.findViewById(R.id.output);
        jcVideoPlayerStandard = (JCVideoPlayerStandard) rootView.findViewById(R.id.videoplayer_urdu);

        mAdView = (RelativeLayout) rootView.findViewById(R.id.ad_view1);
        mAdView1 = (RelativeLayout) rootView.findViewById(R.id.ad_view2);

        taboolaWidget = (TaboolaWidget) rootView.findViewById(R.id.taboola_view);
        rel_recycle = (RecyclerView) rootView.findViewById(R.id.rel_watch_view);


        scroller.smoothScrollTo(0, 0);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        //RecyclerView for horizontal layout
        rel_recycle.setLayoutManager(layoutManager);
        rel_recycle.setNestedScrollingEnabled(false);
        rel_recycle.setHasFixedSize(true);


        try
        {
            // JSONObject obj = array.getJSONObject(fPos);
            String id = array_detail.get(fPos).getId();
            t1 = array_detail.get(fPos).getTitle();
            desc1 = array_detail.get(fPos).getDesc();
            link = array_detail.get(fPos).getLink();
            image_trend = array_detail.get(fPos).getImage();

            videourl = array_detail.get(fPos).getVideo();
            vmob = array_detail.get(fPos).getVmob();
            ytvideo = "";

            d1 = array_detail.get(fPos).getDate();
            //String category = obj.getString("category");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
            Date testDate = null;
            try
            {
                testDate = sdf.parse(d1);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
            String newFormat = formatter.format(testDate);
            System.out.println(".....Date..." + newFormat);


            long milliseconds = testDate.getTime();

            String gettime = getTimeAgo(milliseconds);

            date.setText(gettime);

            title.setText(t1);

            //regular expression to remove image tag
            desc1 = desc1.replaceAll("<img.+?>", "");

            //regular expression to remove caption tag
            desc1 = desc1.replaceAll("(\\[caption(?:[^\\]]*)\\](?:.*?)\\[\\/caption\\])", "");


            //replace next line keywords to break tag keyword
            desc1 = desc1.replaceAll("\r\n", "<br>");

            // extract youtube video url from iframe tag before removing it
            int ifr_idx = desc1.indexOf("[iframe");

            if (ifr_idx > 0)
            {
                String iframe_text = desc1.substring(ifr_idx, desc1.lastIndexOf(']'));
                String s = iframe_text.substring(iframe_text.indexOf("src=")+5);
                ytvideo = s.substring(0, s.indexOf('\"'));
            }

            // replace tags for iframe
            desc1 = desc1.replaceAll("(?s)\\[iframe.+\\]", "");

            desc.setText(Html.fromHtml(desc1, imgGetter, null));

            if (id != null)
            {
                RelNews = "https://www.samaa.tv/urdu/jfeedurduseemore/?news_id=" + id;
            }
            if (image_trend != null && image_trend.length() > 0) {
                Picasso.with(getActivity())
                        .load(image_trend)
                        .error(R.drawable.logo_samaatv)
                        .placeholder(R.drawable.logo_samaatv)
                        .into(image);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        implTaboolaAd();
        setAndProcessingAd();

        if (vmob != null && !vmob.equals("None"))
        {
            videourl = vmob;
        }
        else
        {
            vmob = "";
        }

        if (videourl != null && !videourl.equals("None") && !videourl.equals(""))
        {

//            mPlayerView.setVisibility(View.VISIBLE);
            jcVideoPlayerStandard.setVisibility(View.VISIBLE);

            if (videourl.contains("http://vod.samaa.tv/"))
            {
                videourl = videourl.replace("http://vod.samaa.tv/", "https://samaa-vod.scaleengine.net/");
            }

            if (videourl.contains("https://vod.samaa.tv/"))
            {
                videourl = videourl.replace("https://vod.samaa.tv/", "https://samaa-vod.scaleengine.net/");
            }

            image.setVisibility(View.GONE);
            playVideos(videourl, image_trend);
//            playbtn.setVisibility(View.GONE);

        }
        else
        {
            videourl = "";
//            playbtn.setVisibility(View.GONE);
//            mPlayerView.setVisibility(View.GONE);
            jcVideoPlayerStandard.setVisibility(View.GONE);
        }

        // add handling of youtube video
        if (ytvideo != null && !ytvideo.equals(""))
        {
            playbtn.setVisibility(View.VISIBLE);
            image.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Context context = getContext();
                    Intent in = new Intent(context, YoutubeVideoWeb.class);
                    in.putExtra(KEY_VIDEO, ytvideo);
                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(in);
                }
            });
        }
        else
        {
            playbtn.setVisibility(View.GONE);
        }

        //Webservice for related news at the bottom of every detail news

        if (InternetConnection.checkConnection(getActivity()))
        {

            //Creating an object of our api interface
            ApiService api = RetroClient.getApiService();

            /**
             * Calling JSON
             */
            Call<NewsCategoryList> call = api.getSeeURLUrdu(RelNews);

            /**
             * Enqueue Callback will be call when get response...
             */
            call.enqueue(new Callback<NewsCategoryList>()
            {
                @Override
                public void onResponse(Call<NewsCategoryList> call, Response<NewsCategoryList> response)
                {


                    if (response.isSuccessful())
                    {
                        /**
                         * Got Successfully
                         */
                        rel_list = response.body().getSeeMore();

                        if (rel_list != null)
                        {
                            rel_adapter = new SeeAlsoAdapterUrdu(getActivity(), rel_list, newsCatTag);
                            rel_recycle.setAdapter(rel_adapter);
                            progress.setVisibility(View.GONE);
                        }
                        else
                        {
                            rel_list = new ArrayList<Contact>();
                        }

                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onFailure(Call<NewsCategoryList> call, Throwable t)
                {
                    progress.setVisibility(View.GONE);
                    if (t instanceof NullPointerException)
                    {
                        Toast.makeText(getActivity(), "Something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                    if (t instanceof InternalError)
                    {
                        Toast.makeText(getActivity(), "Oops , something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                    if (t instanceof InterruptedException)
                    {
                        Toast.makeText(getActivity(), "Sorry , Time Out !", Toast.LENGTH_SHORT).show();
                    }
                    if (t instanceof IllegalStateException)
                    {
                        Toast.makeText(getActivity(), "Something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else
        {
            Toast.makeText(getActivity(), "No internet connection !", Toast.LENGTH_SHORT).show();
        }

        //Ends here


        return rootView;
    }

    private void implTaboolaAd()
    {
        taboolaWidget.setPublisher("samaa-androidapp-sdkstandard")
                .setMode("thumbnails-a")
                .setPlacement("App Below Article Thumbnails")
                .setPageUrl("https://play.google.com/store/apps/details?id=com.Samaatv.samaaapp3")
                .setPageType("article")
                .setTargetType("mix");
        taboolaWidget.setInterceptScroll(true);
        taboolaWidget.setMinimumHeight(taboolaWidget.getHeight());
        taboolaWidget.fetchContent();
    }

    private void setAndProcessingAd()
    {

        if (newsCatTag.contains("National") || newsCatTag.contains("national"))
        {

            PublisherAdView mPublisherAdView = new PublisherAdView(getActivity());
            mPublisherAdView.setAdSizes(AdSize.MEDIUM_RECTANGLE, new AdSize(300, 50), new AdSize(300, 100), new AdSize(320, 50), new AdSize(320, 100));
            mPublisherAdView.setAdUnitId("/14309701/and-ur/pakistan/and-ur.pakistan-det.mrec-1");
            PublisherAdRequest.Builder publisherAdRequestBuilder = new PublisherAdRequest.Builder();
            mAdView.addView(mPublisherAdView);
            mPublisherAdView.loadAd(publisherAdRequestBuilder.build());

            PublisherAdView mPublisherAdView1 = new PublisherAdView(getActivity());
            mPublisherAdView1.setAdSizes(AdSize.MEDIUM_RECTANGLE, new AdSize(300, 50), new AdSize(300, 100), new AdSize(320, 50), new AdSize(320, 100));
            mPublisherAdView1.setAdUnitId("/14309701/and-ur/pakistan/and-ur.pakistan-det.mrec-2");
            PublisherAdRequest.Builder publisherAdRequestBuilder1 = new PublisherAdRequest.Builder();
            mAdView1.addView(mPublisherAdView1);
            mPublisherAdView1.loadAd(publisherAdRequestBuilder1.build());
        }

        if (newsCatTag.contains("Global") || newsCatTag.contains("global") || newsCatTag.contains("International") || newsCatTag.contains("international"))
        {

            PublisherAdView mPublisherAdView = new PublisherAdView(getActivity());
            mPublisherAdView.setAdSizes(AdSize.MEDIUM_RECTANGLE, new AdSize(300, 50), new AdSize(300, 100), new AdSize(320, 50), new AdSize(320, 100));
            mPublisherAdView.setAdUnitId("/14309701/and-ur/global/and-ur.global-det.mrec-1");
            PublisherAdRequest.Builder publisherAdRequestBuilder = new PublisherAdRequest.Builder();
            mAdView.addView(mPublisherAdView);
            mPublisherAdView.loadAd(publisherAdRequestBuilder.build());

            PublisherAdView mPublisherAdView1 = new PublisherAdView(getActivity());
            mPublisherAdView1.setAdSizes(AdSize.MEDIUM_RECTANGLE, new AdSize(300, 50), new AdSize(300, 100), new AdSize(320, 50), new AdSize(320, 100));
            mPublisherAdView1.setAdUnitId("/14309701/and-ur/global/and-ur.global-det.mrec-2");
            PublisherAdRequest.Builder publisherAdRequestBuilder1 = new PublisherAdRequest.Builder();
            mAdView1.addView(mPublisherAdView1);
            mPublisherAdView1.loadAd(publisherAdRequestBuilder1.build());
        }

        if (newsCatTag.contains("Economy") || newsCatTag.contains("economy"))
        {

            PublisherAdView mPublisherAdView = new PublisherAdView(getActivity());
            mPublisherAdView.setAdSizes(AdSize.MEDIUM_RECTANGLE, new AdSize(300, 50), new AdSize(300, 100), new AdSize(320, 50), new AdSize(320, 100));
            mPublisherAdView.setAdUnitId("/14309701/and-ur/economy/and-ur.economy-det.mrec-1");
            PublisherAdRequest.Builder publisherAdRequestBuilder = new PublisherAdRequest.Builder();
            mAdView.addView(mPublisherAdView);
            mPublisherAdView.loadAd(publisherAdRequestBuilder.build());

            PublisherAdView mPublisherAdView1 = new PublisherAdView(getActivity());
            mPublisherAdView1.setAdSizes(AdSize.MEDIUM_RECTANGLE, new AdSize(300, 50), new AdSize(300, 100), new AdSize(320, 50), new AdSize(320, 100));
            mPublisherAdView1.setAdUnitId("/14309701/and-ur/economy/and-ur.economy-det.mrec-2");
            PublisherAdRequest.Builder publisherAdRequestBuilder1 = new PublisherAdRequest.Builder();
            mAdView1.addView(mPublisherAdView1);
            mPublisherAdView1.loadAd(publisherAdRequestBuilder1.build());
        }

        if (newsCatTag.contains("Sports") || newsCatTag.contains("sports"))
        {

            PublisherAdView mPublisherAdView = new PublisherAdView(getActivity());
            mPublisherAdView.setAdSizes(AdSize.MEDIUM_RECTANGLE, new AdSize(300, 50), new AdSize(300, 100), new AdSize(320, 50), new AdSize(320, 100));
            mPublisherAdView.setAdUnitId("/14309701/and-ur/sports/and-ur.sports-det.mrec-1");
            PublisherAdRequest.Builder publisherAdRequestBuilder = new PublisherAdRequest.Builder();
            mAdView.addView(mPublisherAdView);
            mPublisherAdView.loadAd(publisherAdRequestBuilder.build());

            PublisherAdView mPublisherAdView1 = new PublisherAdView(getActivity());
            mPublisherAdView1.setAdSizes(AdSize.MEDIUM_RECTANGLE, new AdSize(300, 50), new AdSize(300, 100), new AdSize(320, 50), new AdSize(320, 100));
            mPublisherAdView1.setAdUnitId("/14309701/and-ur/sports/and-ur.sports-det.mrec-2");
            PublisherAdRequest.Builder publisherAdRequestBuilder1 = new PublisherAdRequest.Builder();
            mAdView1.addView(mPublisherAdView1);
            mPublisherAdView1.loadAd(publisherAdRequestBuilder1.build());
        }

        if (newsCatTag.contains("Entertainment") || newsCatTag.contains("entertainment") || array_detail.get(fPos).getCategory().contains("Culture") || array_detail.get(fPos).getCategory().contains("culture"))
        {

            PublisherAdView mPublisherAdView = new PublisherAdView(getActivity());
            mPublisherAdView.setAdSizes(AdSize.MEDIUM_RECTANGLE, new AdSize(300, 50), new AdSize(300, 100), new AdSize(320, 50), new AdSize(320, 100));
            mPublisherAdView.setAdUnitId("/14309701/and-ur/entertainment/and-ur.entertainment-det.mrec-1");
            PublisherAdRequest.Builder publisherAdRequestBuilder = new PublisherAdRequest.Builder();
            mAdView.addView(mPublisherAdView);
            mPublisherAdView.loadAd(publisherAdRequestBuilder.build());

            PublisherAdView mPublisherAdView1 = new PublisherAdView(getActivity());
            mPublisherAdView1.setAdSizes(AdSize.MEDIUM_RECTANGLE, new AdSize(300, 50), new AdSize(300, 100), new AdSize(320, 50), new AdSize(320, 100));
            mPublisherAdView1.setAdUnitId("/14309701/and-ur/entertainment/and-ur.entertainment-det.mrec-2");
            PublisherAdRequest.Builder publisherAdRequestBuilder1 = new PublisherAdRequest.Builder();
            mAdView1.addView(mPublisherAdView1);
            mPublisherAdView1.loadAd(publisherAdRequestBuilder1.build());
        }

        if (newsCatTag.contains("Blogs") || newsCatTag.contains("blogs"))
        {

            PublisherAdView mPublisherAdView = new PublisherAdView(getActivity());
            mPublisherAdView.setAdSizes(AdSize.MEDIUM_RECTANGLE, new AdSize(300, 50), new AdSize(300, 100), new AdSize(320, 50), new AdSize(320, 100));
            mPublisherAdView.setAdUnitId("/14309701/and-ur/blogs/and-ur.blogs-det.mrec-1");
            PublisherAdRequest.Builder publisherAdRequestBuilder = new PublisherAdRequest.Builder();
            mAdView.addView(mPublisherAdView);
            mPublisherAdView.loadAd(publisherAdRequestBuilder.build());

            PublisherAdView mPublisherAdView1 = new PublisherAdView(getActivity());
            mPublisherAdView1.setAdSizes(AdSize.MEDIUM_RECTANGLE, new AdSize(300, 50), new AdSize(300, 100), new AdSize(320, 50), new AdSize(320, 100));
            mPublisherAdView1.setAdUnitId("/14309701/and-ur/blogs/and-ur.blogs-det.mrec-2");
            PublisherAdRequest.Builder publisherAdRequestBuilder1 = new PublisherAdRequest.Builder();
            mAdView1.addView(mPublisherAdView1);
            mPublisherAdView1.loadAd(publisherAdRequestBuilder1.build());
        }
    }

    private ViewGroup checkScreenAndSetLayout(LayoutInflater inflater, ViewGroup container)
    {

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
        ViewGroup rootView;
        if (diagonalInches >= 6.5)
        {
            // 6.5inch device or bigger
            rootView = (ViewGroup) inflater.inflate(R.layout.detail_fragment_urdu_large, container, false);
        }
        else
        {
            // smaller device
            rootView = (ViewGroup) inflater.inflate(R.layout.detail_fragment_urdu, container, false);
        }
        return rootView;
    }

    private void playVideos(String videourl, String image_trend)
    {
//        mPlayerView.addOnFullscreenListener(this);
//        new KeepScreenOnHandler(mPlayerView, getActivity().getWindow());
//        mEventHandler = new JWEventHandler(mPlayerView, outputTextView);
//
//        List<PlaylistItem> playlist = new ArrayList<PlaylistItem>();
//        // Build a PlaylistItem and assign adSchedule
//        PlaylistItem video = new PlaylistItem.Builder()
//                .image(image_trend)
//                .file(videourl)
//                .build();
//        playlist.add(video);
//
//        // Create your player config
//        PlayerConfig playerConfig = new PlayerConfig.Builder()
//                .playlist(playlist)
//                .autostart(true)
//                .build();
//
//        mPlayerView.setup(playerConfig);

        jcVideoPlayerStandard.thumbImageView.setImageURI(Uri.parse(image_trend));
        jcVideoPlayerStandard.setUp(videourl, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        // Set fullscreen when the device is rotated to landscape
//        mPlayerView.setFullscreen(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE, true);
        super.onConfigurationChanged(newConfig);
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if(isVisibleToUser){
//
//            if(mPlayerView!=null) {
//                mPlayerView.onResume();
//            }
//
//        }else{
//            if(mPlayerView!=null) {
//                mPlayerView.onPause();
//            }
//        }
//    }

    @Override
    public void onResume()
    {
        // Let JW Player know that the app has returned from the background
//        mPlayerView.onResume();
        super.onResume();
        SamaaAppAnalytics.getInstance().trackScreenView("Detail SubScreen News Urdu");
        SamaaFirebaseAnalytics.syncSamaaAnalytics(getActivity(), "Detail SubScreen News Urdu");
    }

    @Override
    public void onPause()
    {
        // Let JW Player know that the app is going to the background
//        mPlayerView.onPause();
        super.onPause();
    }


}
