package com.Samaatv.samaaapp3;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

//import com.github.florent37.materialviewpager.sample.fragment.Detail_Activity;


public class RecyclerViewAdapterProgramsListingAds extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    static final String KEY_VIDEO = "videourl";
    static final int TYPE_HEADER = 0;
    static final int TYPE_HORIZONTAL = 1;
    static final int TYPE_AD = 2;
    static final int TYPE_CELL = 3;
    static final int TYPE_FOOTER = 333;
    private static final String feedURL = "https://www.samaa.tv/videos/jfeedltstprgrms/";
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    //private List<RSSItem> listItems, filterList;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    protected ImageView imageView, playbtn;
    protected TextView title, date, category;
    JSONArray contents;
    JSONArray pop_videos = null;
    String video;
    // private List<RSSItem> _itemlist;
    String image_trend;
    private String TAG = RecyclerViewAdapterEditorsAds.class.getSimpleName();
    private PublisherAdView mAdView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private Context mContext;


    //private HashMap<Integer, String> hash = new HashMap<>();
    public RecyclerViewAdapterProgramsListingAds(Context context, JSONArray contents)
    {
        this.contents = contents;
        this.mContext = context;
        //  this.listItems = new ArrayList<RSSItem>();

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
    public int getItemViewType(int position)
    {
       /* switch (position) {
            case 0:
                return TYPE_HEADER;
            case 4:
                return TYPE_AD;
            default:
                return TYPE_CELL;
        }*/
        Log.e("position", String.valueOf(position));
        Log.e("position2", String.valueOf(contents.length()));
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
        else if (position == contents.length() - 1)
        {
            return TYPE_FOOTER;
        }
        else
        {
            return TYPE_CELL;
        }
    }


 /*   @Override
    public int getViewTypeCount() {
        return 2;
    }
*/

    @Override
    public int getItemCount()
    {
        return contents.length();

        //return 4;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {


        LayoutInflater inflater = LayoutInflater.from(parent.getContext());


        //Async Task for Popular Video in Program Listings in Horizontal View
        new AsyncLoadXMLFeed().execute();

        /*final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_card_big, parent, false);*/


        if (viewType == TYPE_HEADER)
        {
            View headerView = inflater.inflate(R.layout.list_item_card_big, parent, false);
            return new RecyclerView.ViewHolder(headerView)
            {
            }; // view holder for header items
        }
        else if (viewType == TYPE_HORIZONTAL)
        {
            View horizon = inflater.inflate(R.layout.carousal, parent, false);

            return new RecyclerView.ViewHolder(horizon)
            {
            };

        }
        else if (viewType == TYPE_AD)
        {
            View ADView = inflater.inflate(R.layout.list_item_card_big_ad, parent, false);

            return new RecyclerView.ViewHolder(ADView)
            {
            };

        }
        else if (viewType == TYPE_FOOTER)
        {
            View ADView = inflater.inflate(R.layout.list_item_card_banner_adview, parent, false);

            return new RecyclerView.ViewHolder(ADView)
            {
            };

        }

        else if (viewType == TYPE_CELL)
        {
            View normalRow = inflater.inflate(R.layout.items_new, parent, false);
            return new RecyclerView.ViewHolder(normalRow)
            {
            }; // view holder for normal items
        }
        else
        {
            View view = inflater.inflate(R.layout.items_new, parent, false);
            return new RecyclerView.ViewHolder(view)
            {

            };
        }
        //return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {


        mRecyclerView = (RecyclerView) holder.itemView.findViewById(R.id.horizontal_view);
        mAdView = (PublisherAdView) holder.itemView.findViewById(R.id.ad);
        imageView = (ImageView) holder.itemView.findViewById(R.id.img);
        playbtn = (ImageView) holder.itemView.findViewById(R.id.playicon);
        title = (TextView) holder.itemView.findViewById(R.id.title);
        date = (TextView) holder.itemView.findViewById(R.id.date);
        category = (TextView) holder.itemView.findViewById(R.id.cate);

        //if(mRecyclerView!=null){
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext,
                LinearLayoutManager.HORIZONTAL, false);

        //RecyclerView for horizontal layout
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        //   }

        final int posAcu = holder.getAdapterPosition();

        PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);


        try
        {
            JSONObject pak = contents.getJSONObject(position);

            String id = pak.getString("id");
            String title1 = pak.getString("title");
            //String desc = pak.getString("desc");
            //String url = pak.getString("url");
            image_trend = pak.getString("image");
            //  hash.put(posAcu, pak.getString("videourl"));
            //hash.put(posAcu, pak.getString("image"));
            video = pak.getString("videourl");
            String pubDate = pak.getString("pubDate");
            String sharingurl = pak.getString("sharingurl");

            //String category1 = pak.getString("category");

            //category.setText("| "+category1);
            title.setText(Html.fromHtml(title1));


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

            //  long longDate = newFormat;
            // String result = DateUtils.getRelativeTimeSpanString(mContext, );
            //Setting text view date
            // date.setText(newFormat);

            String gettime = getTimeAgo(milliseconds);

            date.setText(gettime);

            //Download image using picasso library
            if (image_trend != null && image_trend.length() > 0) {
                Picasso.with(mContext.getApplicationContext())
                        .load(image_trend)
                        .error(R.drawable.logo_samaatv)
                        .placeholder(R.drawable.logo_samaatv)
                        .into(imageView);
            }



            if (video != null && !video.equals("None"))
            {
                playbtn.setVisibility(View.VISIBLE);
                /*playbtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // Starting new video
                        Intent in = new Intent(mContext, EditorVideoWeb.class);
                        //	in.putExtra(KEY_TITLE, name);
                        //	in.putExtra(KEY_ARTIST, cost);
                        in.putExtra(KEY_VIDEO, video);
                        in.putExtra("image", image_trend);
                        mContext.startActivity(in);
                    }
                });*/
            }
            else
            {
                playbtn.setVisibility(View.GONE);
            }

        }
        catch (Exception e)
        {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
            //Toast.makeText(getActivity(), "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }


        // holder.title.
        // final int pos = holder.getAdapterPosition();


        //Setting text view title
        //title.setText(Html.fromHtml(contents.getItem(position).getTitle()));


        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {


                String jsonArray = contents.toString();

                Intent in = new Intent(mContext, EditorVideoWeb.class);
                //	in.putExtra(KEY_TITLE, name);
                //	in.putExtra(KEY_ARTIST, cost);
                in.putExtra(KEY_VIDEO, video);
                //    in.putExtra(KEY_VIDEO, hash.get(posAcu));
                //  in.putExtra("image", hash.get(posAcu));
                in.putExtra("image", image_trend);
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(in);
                // Bundle bundle = new Bundle();
                //  String json_array =bundle.getString("userdata");
                //Toast.makeText(mContext, "Recycle Click" + position + Html.fromHtml(contents.getItem(position).getTitle()), Toast.LENGTH_SHORT).show();
               /* Intent intent = new Intent(mContext, Detail_Activity.class);
                intent.putExtra("jsonArray", jsonArray);
                intent.putExtra("pos", position);
                //intent.putExtra("category", cat);
				*//*intent.putExtra("link",link);
				intent.putExtra("title",lfflTitle.getText());*//*
                //feed.getItem()
                mContext.startActivity(intent);
*/
                //RecyclerView.ViewHolder holder1 = (RecyclerView.ViewHolder) view.getTag();
//                int position = holder1.getAdapterPosition();
                //  RSSItem feedItem = contents.getItem(pos);
                //  Bundle bundle = new Bundle();
                //  bundle.putSerializable("feed", contents);
                //Bundle bundle = new Bundle();
                //bundle.putSerializable("feed", contents);
                // Toast.makeText(mContext, "Recycle Click" + position + title.getText(), Toast.LENGTH_SHORT).show();
                // Intent intent = new Intent(mContext, Detail_Activity.class);
                // intent.putExtras(bundle);
                //intent.putExtra("pos", pos);
                //intent.putExtra("category", cat);
				/*intent.putExtra("link",link);
				intent.putExtra("title",lfflTitle.getText());*/
                //feed.getItem()
                //mContext.startActivity(intent);
            }
        });

        /*  switch (getItemViewType(position)) {
            case TYPE_HEADER:
                break;
            case TYPE_CELL:
                break;
        }*/
    }

    private class AsyncLoadXMLFeed extends AsyncTask<Void, Void, Void>
    {

        // ProgressDialog pDialog;


        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            //  pDialog = new ProgressDialog(mContext);
            //  pDialog.setMessage("Loading Program Videos...");
            // pDialog.setCanceledOnTouchOutside(false);
            // pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params)
        {

            ServiceHandler sh = new ServiceHandler();

            String jsonStr = sh.makeServiceCall(feedURL, ServiceHandler.GET);


            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null)
            {
                try
                {

                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node of TV Shows News
                    pop_videos = jsonObj.getJSONArray("Latest-Programs");


                }
                catch (final JSONException e)
                {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    Toast.makeText(mContext, "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
            else
            {
                Log.e("ServiceHandler", "URL'den veri alınamadı.");
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);



          /*  if (null != pDialog && pDialog.isShowing()) {
               pDialog.dismiss();*/

            mAdapter = new RecyclerViewEditorAdapter(mContext, pop_videos);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
//           }

        }

    }

   /* public void setItems(RSSFeed datas){
        contents = datas;
    }*/

  /*  public void setFilter(RSSFeed countryModels) {
        cno = new ArrayList<>();
        listItems.addAll(countryModels.getItemCount());
        notifyDataSetChanged();
    }
*/
    // Do Search...
 /* public void setFilter(List<RSSItem> countryModels) {
      listItems = new ArrayList<>();
      listItems.addAll(countryModels);
      notifyDataSetChanged();
  }*/
  /*  public static class ViewHolder extends RecyclerView.ViewHolder
            {
        private TextView titleTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView)itemView.findViewById(R.id.textView);

        }


    }*/
}