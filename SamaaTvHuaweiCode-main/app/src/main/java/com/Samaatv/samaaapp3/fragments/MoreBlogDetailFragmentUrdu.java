package com.Samaatv.samaaapp3.fragments;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.Samaatv.samaaapp3.R;
import com.Samaatv.samaaapp3.adapter.SeeAlsoAdapter;
import com.Samaatv.samaaapp3.model.Contact;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Administrator on 4/15/2017.
 */
public class MoreBlogDetailFragmentUrdu extends Fragment
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
    static String videourl;
    static String vmob;
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
    RecyclerView rel_recycle;
    String image_trend;
    JCVideoPlayerStandard jcVideoPlayerStandard;
    private ArrayList<Contact> array_detail;
    private View parentView;
    private RecyclerView.Adapter mAdapter_rel_news;
    private String TAG = MoreBlogDetailFragment.class.getSimpleName();
    private PublisherAdView mAdView;
    private PublisherAdView mAdView1;
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
        title = (TextView) rootView.findViewById(R.id.title_detail);
        desc = (TextView) rootView.findViewById(R.id.desc_detail);
        desc2 = (TextView) rootView.findViewById(R.id.desc_detail2);
        date = (TextView) rootView.findViewById(R.id.date_detail);
        image = (ImageView) rootView.findViewById(R.id.img_detail);
        playbtn = (ImageView) rootView.findViewById(R.id.play_vdo);
        parentView = rootView.findViewById(R.id.parent_view);
        mAdView = (PublisherAdView) rootView.findViewById(R.id.ad_view1);
        mAdView1 = (PublisherAdView) rootView.findViewById(R.id.ad_view2);

        jcVideoPlayerStandard = (JCVideoPlayerStandard) rootView.findViewById(R.id.videoplayer_moreblog_urdu);
//        mPlayerView = (JWPlayerView)rootView.findViewById(R.id.jwplayer);
//        mPlayerView.getRootView().setOnKeyListener(new KeyDownListner());

        scroller.smoothScrollTo(0, 0);

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
            String video = videourl;
//            if(video!=null){
//                playbtn.setVisibility(View.VISIBLE);
//            }
//            desc1 = desc1.replaceAll("<br>", "");    // ye comment hataya he mene abhi

            //regular expression to remove image tag
            desc1 = desc1.replaceAll("<img.+?>", "");

            //regular expression to remove caption tag
            desc1 = desc1.replaceAll("(\\[caption(?:[^\\]]*)\\](?:.*?)\\[\\/caption\\])", "");

            //replace next line keywords to break tag keyword
            desc1 = desc1.replaceAll("\r\n", "<br>");

            // replace tags for iframe
            desc1 = desc1.replaceAll("(?s)\\[iframe.+\\]", "");

            // tvText.setText(Html.fromHtml(testContent, imgGetter, null));
            desc.setText(Html.fromHtml(desc1, imgGetter, null));

            if (id != null)
            {
                RelNews = "https://www.samaa.tv/jappfeedseemore/?news_id=" + id;    // ye part nae smjh aya
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

        if (link != null)
        {
//            PublisherAdRequest adRequest = new PublisherAdRequest.Builder().setContentUrl(link).build();
//            // Start loading the ad in the background.
//            mAdView.loadAd(adRequest);
//            mAdView1.loadAd(adRequest); mz
        }
        else
        {
//            PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
//            // Start loading the ad in the background.
//            mAdView.loadAd(adRequest);
//            mAdView1.loadAd(adRequest);  mz
        }

//        if(videourl!=null && !videourl.equals("None")) {
//            playbtn.setVisibility(View.VISIBLE);
//            playbtn.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View view) {
//                    // Starting new video
//                    Intent in = new Intent(getActivity(), EditorVideoWeb.class);
//                    in.putExtra(KEY_VIDEO, videourl);
//                    in.putExtra("image", image_trend);
//                    startActivity(in);
//                }
//            });
//        }
//        else {
//            playbtn.setVisibility(View.GONE);
//        }

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
//            playVideos(videourl,image_trend);
//            playbtn.setVisibility(View.GONE);
            image.setVisibility(View.GONE);
            playVideos(videourl, image_trend);

        }
        else
        {
            videourl = "";
            jcVideoPlayerStandard.setVisibility(View.GONE);
        }

        return rootView;
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
            rootView = (ViewGroup) inflater.inflate(R.layout.detail_fragment_more_blog_urdu_large, container, false);
        }
        else
        {
            rootView = (ViewGroup) inflater.inflate(R.layout.detail_fragment_more_blog_urdu, container, false);

        }
        return rootView;

    }

    private void playVideos(String videourl, String image_trend)
    {
//        mPlayerView.addOnFullscreenListener((VideoPlayerEvents.OnFullscreenListener) getActivity());
//        new KeepScreenOnHandler(mPlayerView, getActivity().getWindow());
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


}
