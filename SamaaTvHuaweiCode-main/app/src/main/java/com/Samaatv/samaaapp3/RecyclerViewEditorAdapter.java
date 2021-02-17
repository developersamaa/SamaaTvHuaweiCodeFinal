package com.Samaatv.samaaapp3;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

//import com.github.florent37.materialviewpager.sample.fragment.Detail_Activity;


public class RecyclerViewEditorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    // private List<RSSItem> _itemlist;
    static final String KEY_VIDEO = "videourl";
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;

    //private List<RSSItem> listItems, filterList;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    protected ImageView imageView, playbtn;
    protected TextView title, date;
    String image, video;
    JSONArray contents;
    private String TAG = RecyclerViewEditorAdapter.class.getSimpleName();
    private Context mContext;

    // static final int TYPE_HEADER = 0;
    // static final int TYPE_CELL = 1;

    public RecyclerViewEditorAdapter(Context context, JSONArray contents)
    {
        this.contents = contents;
        this.mContext = context;
        //  this.listItems = new ArrayList<RSSItem>();

    }

    /*@Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }*/

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
    public int getItemCount()
    {
        return 8;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.item, parent, false);
       /*final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_card_big, parent, false);*/

        return new RecyclerView.ViewHolder(view)
        {

        };


        //return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        // filterList = new ArrayList<ListItem>();
        //   listItems = new ArrayList<>(contents);

        //  RSSItem listItem = contents.getItem(position);

        // final CountryModel model = mCountryModel.get(i);
        //   holde.bind(model);

        imageView = (ImageView) holder.itemView.findViewById(R.id.image);
        playbtn = (ImageView) holder.itemView.findViewById(R.id.play_trend);
        title = (TextView) holder.itemView.findViewById(R.id.title);
        //date = (TextView) holder.itemView.findViewById(R.id.date);
        try
        {
            JSONObject pak = contents.getJSONObject(position);

            // String id = pak.getString("id");
            String title1 = pak.getString("title");
            //String desc = pak.getString("desc");
            //String link = pak.getString("link");
            image = pak.getString("image");

            video = pak.getString("videourl");
            // String pubDate = pak.getString("pubDate");
            //String category1 = pak.getString("category");

            //category.setText("| "+category1);
            title.setText(Html.fromHtml(title1));


           /* // Setting date with formatted elapsed time
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
            Date testDate = null;
            try {
                testDate = sdf.parse(pubDate);
            }catch(Exception ex){
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
*/
            //Download image using picasso library

            if (image != null && image.length() > 0) {
                Picasso.with(mContext.getApplicationContext())
                        .load(image)
                        .error(R.drawable.logo_samaatv)
                        .placeholder(R.drawable.logo_samaatv)
                        .into(imageView);
            }


            if (video != null && !video.equals("None"))
            {
                playbtn.setVisibility(View.VISIBLE);
                playbtn.setOnClickListener(new View.OnClickListener()
                {

                    @Override
                    public void onClick(View view)
                    {
                        // Starting new video
                        Intent in = new Intent(mContext, EditorVideoWeb.class);
                        //	in.putExtra(KEY_TITLE, name);
                        //	in.putExtra(KEY_ARTIST, cost);
                        in.putExtra(KEY_VIDEO, video);
                        in.putExtra("image", image);
                        mContext.startActivity(in);
                    }
                });
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


        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (video != null && !video.equals("None"))
                {
                    Intent in = new Intent(mContext, EditorVideoWeb.class);
                    //	in.putExtra(KEY_TITLE, name);
                    //	in.putExtr a(KEY_ARTIST, cost);
                    in.putExtra(KEY_VIDEO, video);
                    in.putExtra("image", image);
                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(in);
                }
                else
                {
                    Toast.makeText(mContext, "Video not available !", Toast.LENGTH_SHORT).show();
                }
                //  RecyclerView.ViewHolder holder1 = (RecyclerView.ViewHolder) view.getTag();
                //  int position = holder1.getAdapterPosition();
                //  RSSItem feedItem = contents.getItem(pos);
                //  Bundle bundle = new Bundle();
                //  bundle.putSerializable("feed", contents);
                //Bundle bundle = new Bundle();
                //bundle.putSerializable("feed", contents);
                //  Toast.makeText(mContext, "Recycle Click" + position + title.getText(), Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(mContext, Detail_Activity.class);
                //  intent.putExtras(bundle);
                //intent.putExtra("pos", pos);
                //intent.putExtra("category", cat);
				/*intent.putExtra("link",link);
				intent.putExtra("title",lfflTitle.getText());*/
                //feed.getItem()
                //  mContext.startActivity(intent);
            }
        });

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
/*
    public static class ViewHolder extends RecyclerView.ViewHolder
            {
        private TextView titleTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView)itemView.findViewById(R.id.textView);

        }


    }*/
}