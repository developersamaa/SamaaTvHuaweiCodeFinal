package com.Samaatv.samaaapp3.fragments;

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

import com.Samaatv.samaaapp3.R;
import com.Samaatv.samaaapp3.adapter.SeeAlsoAdapter;
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

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 4/14/2017.
 */
public class MoreBlogDetailFragment extends Fragment
{
    static final String KEY_VIDEO = "videourl";
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    public static String t1;
    public static String link;
    static String d1;
    static String desc1;
    static String videourl, vmob;
    ProgressBar progress;
    int imageNumber = 1; //int to check which image is displayed
    String RelNews = "";
    JSONArray rel_news = null;
    RecyclerView rel_news_recycle;
    String arrayjson;
    String cat;
    TextView title, desc, desc2, date;
    ImageView image, playbtn;
    JSONArray array = null;
    ScrollView scroller;
    TextView outputTextView;
    RecyclerView rel_recycle;
    String image_trend;
    JCVideoPlayerStandard jcVideoPlayerStandard;
    private ArrayList<Contact> array_detail;
    private View parentView;
    private RecyclerView.Adapter mAdapter_rel_news;
    private String TAG = MoreBlogDetailFragment.class.getSimpleName();
    private RelativeLayout mAdView;
    private RelativeLayout mAdView1;
    private int fPos;
    private int fPos_r;
    private SeeAlsoAdapter rel_adapter;
    private ArrayList<Contact> rel_list;
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
        jcVideoPlayerStandard = (JCVideoPlayerStandard) rootView.findViewById(R.id.videoplayer_moreblog);
        rel_recycle = (RecyclerView) rootView.findViewById(R.id.rel_watch_view);

        mAdView = (RelativeLayout) rootView.findViewById(R.id.ad_view1);
        mAdView1 = (RelativeLayout) rootView.findViewById(R.id.ad_view2);
        scroller.smoothScrollTo(0, 0);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        //RecyclerView for horizontal layout
        rel_recycle.setLayoutManager(layoutManager);
        rel_recycle.setNestedScrollingEnabled(false);
        rel_recycle.setHasFixedSize(true);


        try
        {
            String id = array_detail.get(fPos).getId();
            t1 = array_detail.get(fPos).getTitle();
            desc1 = array_detail.get(fPos).getDesc();
            link = array_detail.get(fPos).getLink();
            image_trend = array_detail.get(fPos).getImage();
            videourl = array_detail.get(fPos).getVideo();
            vmob = array_detail.get(fPos).getVmob();
            d1 = array_detail.get(fPos).getDate();


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
            String video = videourl;

            //regular expression to remove image tag
            desc1 = desc1.replaceAll("<img.+?>", "");
            //regular expression to remove caption tag
            desc1 = desc1.replaceAll("(\\[caption(?:[^\\]]*)\\](?:.*?)\\[\\/caption\\])", "");
            //replace next line keywords to break tag keyword
            desc1 = desc1.replaceAll("\r\n", "<br>");

            // replace tags for iframe
            desc1 = desc1.replaceAll("(?s)\\[iframe.+\\]", "");

            desc.setText(Html.fromHtml(desc1, imgGetter, null));

            if (id != null)
            {
                RelNews = "https://www.samaa.tv/jappfeedseemore/?news_id=" + id;
            }
            if ( image_trend != null && image_trend.length() > 0) {
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

        }
        else
        {
            videourl = "";
            jcVideoPlayerStandard.setVisibility(View.GONE);
        }

        // Related news at the bottom of every detailed ones

        MoreRelatedNews(RelNews);

        return rootView;
    }

    private void setAndProcessingAd()
    {

        if (array_detail.get(fPos).getCategory().contains("Health") || array_detail.get(fPos).getCategory().contains("health"))
        {

            PublisherAdView mPublisherAdView = new PublisherAdView(getActivity());
            mPublisherAdView.setAdSizes(AdSize.MEDIUM_RECTANGLE);
            mPublisherAdView.setAdUnitId("/14309701/and-en/health/and-en.health-det.mrec-1");
            PublisherAdRequest.Builder publisherAdRequestBuilder = new PublisherAdRequest.Builder();
            mAdView.addView(mPublisherAdView);
            mPublisherAdView.loadAd(publisherAdRequestBuilder.build());

            PublisherAdView mPublisherAdView1 = new PublisherAdView(getActivity());
            mPublisherAdView1.setAdSizes(AdSize.MEDIUM_RECTANGLE);
            mPublisherAdView1.setAdUnitId("/14309701/and-en/health/and-en.health-det.mrec-2");
            PublisherAdRequest.Builder publisherAdRequestBuilder1 = new PublisherAdRequest.Builder();
            mAdView1.addView(mPublisherAdView1);
            mPublisherAdView1.loadAd(publisherAdRequestBuilder1.build());
        }

        if (array_detail.get(fPos).getCategory().contains("Fashion &amp; Life Style") || array_detail.get(fPos).getCategory().contains("Life Style") || array_detail.get(fPos).getCategory().contains("lifestyle"))
        {

            PublisherAdView mPublisherAdView = new PublisherAdView(getActivity());
            mPublisherAdView.setAdSizes(AdSize.MEDIUM_RECTANGLE);
            mPublisherAdView.setAdUnitId("/14309701/and-en/lifestyle/and-en.lifestyle-det.mrec-1");
            PublisherAdRequest.Builder publisherAdRequestBuilder = new PublisherAdRequest.Builder();
            mAdView.addView(mPublisherAdView);
            mPublisherAdView.loadAd(publisherAdRequestBuilder.build());

            PublisherAdView mPublisherAdView1 = new PublisherAdView(getActivity());
            mPublisherAdView1.setAdSizes(AdSize.MEDIUM_RECTANGLE);
            mPublisherAdView1.setAdUnitId("/14309701/and-en/lifestyle/and-en.lifestyle-det.mrec-2");
            PublisherAdRequest.Builder publisherAdRequestBuilder1 = new PublisherAdRequest.Builder();
            mAdView1.addView(mPublisherAdView1);
            mPublisherAdView1.loadAd(publisherAdRequestBuilder1.build());
        }

        if (array_detail.get(fPos).getCategory().contains("Social Buzz") || array_detail.get(fPos).getCategory().contains("social"))
        {

            PublisherAdView mPublisherAdView = new PublisherAdView(getActivity());
            mPublisherAdView.setAdSizes(AdSize.MEDIUM_RECTANGLE);
            mPublisherAdView.setAdUnitId("/14309701/and-en/socialbuzz/and-en.socialbuzz-det.mrec-1");
            PublisherAdRequest.Builder publisherAdRequestBuilder = new PublisherAdRequest.Builder();
            mAdView.addView(mPublisherAdView);
            mPublisherAdView.loadAd(publisherAdRequestBuilder.build());

            PublisherAdView mPublisherAdView1 = new PublisherAdView(getActivity());
            mPublisherAdView1.setAdSizes(AdSize.MEDIUM_RECTANGLE);
            mPublisherAdView1.setAdUnitId("/14309701/and-en/socialbuzz/and-en.socialbuzz-det.mrec-2");
            PublisherAdRequest.Builder publisherAdRequestBuilder1 = new PublisherAdRequest.Builder();
            mAdView1.addView(mPublisherAdView1);
            mPublisherAdView1.loadAd(publisherAdRequestBuilder1.build());
        }

        if (array_detail.get(fPos).getCategory().contains("Editor's Choice") || array_detail.get(fPos).getCategory().contains("SCI-TECH") || array_detail.get(fPos).getCategory().contains("Global"))
        {

            PublisherAdView mPublisherAdView = new PublisherAdView(getActivity());
            mPublisherAdView.setAdSizes(AdSize.MEDIUM_RECTANGLE);
            mPublisherAdView.setAdUnitId("/14309701/and-en/scitech/and-en.scitech-det.mrec-1");
            PublisherAdRequest.Builder publisherAdRequestBuilder = new PublisherAdRequest.Builder();
            mAdView.addView(mPublisherAdView);
            mPublisherAdView.loadAd(publisherAdRequestBuilder.build());

            PublisherAdView mPublisherAdView1 = new PublisherAdView(getActivity());
            mPublisherAdView1.setAdSizes(AdSize.MEDIUM_RECTANGLE);
            mPublisherAdView1.setAdUnitId("/14309701/and-en/scitech/and-en.scitech-det.mrec-2");
            PublisherAdRequest.Builder publisherAdRequestBuilder1 = new PublisherAdRequest.Builder();
            mAdView1.addView(mPublisherAdView1);
            mPublisherAdView1.loadAd(publisherAdRequestBuilder1.build());
        }

        if (array_detail.get(fPos).getCategory().contains("Entertainment") || array_detail.get(fPos).getCategory().contains("Weird"))
        {

            PublisherAdView mPublisherAdView = new PublisherAdView(getActivity());
            mPublisherAdView.setAdSizes(AdSize.MEDIUM_RECTANGLE);
            mPublisherAdView.setAdUnitId("/14309701/and-en/weird/and-en.weird-det.mrec-1");
            PublisherAdRequest.Builder publisherAdRequestBuilder = new PublisherAdRequest.Builder();
            mAdView.addView(mPublisherAdView);
            mPublisherAdView.loadAd(publisherAdRequestBuilder.build());

            PublisherAdView mPublisherAdView1 = new PublisherAdView(getActivity());
            mPublisherAdView1.setAdSizes(AdSize.MEDIUM_RECTANGLE);
            mPublisherAdView1.setAdUnitId("/14309701/and-en/weird/and-en.weird-det.mrec-2");
            PublisherAdRequest.Builder publisherAdRequestBuilder1 = new PublisherAdRequest.Builder();
            mAdView1.addView(mPublisherAdView1);
            mPublisherAdView1.loadAd(publisherAdRequestBuilder1.build());
        }
    }

    private void MoreRelatedNews(String relNews)
    {
        if (InternetConnection.checkConnection(getActivity()))
        {

            //Creating an object of our api interface
            ApiService api = RetroClient.getApiService();
            Call<NewsCategoryList> call = api.getSeeURL(relNews);
            call.enqueue(new Callback<NewsCategoryList>()
            {
                @Override
                public void onResponse(Call<NewsCategoryList> call, Response<NewsCategoryList> response)
                {

                    if (response.isSuccessful())
                    {

                        rel_list = response.body().getSeeMore();

                        if (rel_list != null)
                        {

                            rel_adapter = new SeeAlsoAdapter(getActivity(), rel_list);
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
            rootView = (ViewGroup) inflater.inflate(R.layout.detail_fragment_more_blog_large, container, false);
        }
        else
        {
            rootView = (ViewGroup) inflater.inflate(R.layout.detail_fragment_more_blog, container, false);
        }
        return rootView;
    }

    private void playVideos(String videourl, String image_trend)
    {
        jcVideoPlayerStandard.thumbImageView.setImageURI(Uri.parse(image_trend));
        jcVideoPlayerStandard.setUp(videourl, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");

    }

    @Override
    public void onResume()
    {

        SamaaAppAnalytics.getInstance().trackScreenView("More News Detail Screen");
        SamaaFirebaseAnalytics.syncSamaaAnalytics(getActivity(), "More News Detail Screen");
        super.onResume();

    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

}

