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
import android.widget.TextView;

import com.Samaatv.samaaapp3.Detail_Activity_Urdu;
import com.Samaatv.samaaapp3.R;
import com.Samaatv.samaaapp3.model.Contact;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Administrator on 4/26/2017.
 */
public class EditorsChoiceAdapterUrdu extends RecyclerView.Adapter<EditorsChoiceAdapterUrdu.ViewHolder>
{

    static final String KEY_VIDEO = "videourl";
    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;
    static final int TYPE_AD = 2;
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    //private PublisherAdView mAdView;
    Context context;
    String video;
    String image;
    String ytVideo;
    private ArrayList<Contact> contactList;


    public EditorsChoiceAdapterUrdu(Context context, ArrayList<Contact> contactList)
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
    public EditorsChoiceAdapterUrdu.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item, parent, false);
        return new ViewHolder(view)
        {

        };
    }

    @Override
    public void onBindViewHolder(EditorsChoiceAdapterUrdu.ViewHolder holder, final int position)
    {

        try
        {
            holder.tv_name.setText(contactList.get(position).getTitle());
            video = contactList.get(position).getVideo();
            ytVideo = contactList.get(position).getYTVideo();
            image = contactList.get(position).getImage();
        }
        catch (IndexOutOfBoundsException ioe)
        {
            Log.e("IOE", "index out ka bekar exception", ioe);
        }

        if (image == null || image.isEmpty())
        {

            holder.imageView.setImageDrawable(null);
        }
        else
        {

            Picasso.with(context)
                    .load(image)
                    .error(R.drawable.logo_samaatv)
                    .placeholder(R.drawable.logo_samaatv)
                    .into(holder.imageView);
        }

        if (video != null && !video.equals("None"))
        {
            holder.playbtn.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.playbtn.setVisibility(View.GONE);
        }
        /*PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);*/

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {


                Bundle bundle = new Bundle();
                bundle.putSerializable("array", contactList);
                Intent intent = new Intent(context, Detail_Activity_Urdu.class);
                intent.putExtras(bundle);
                intent.putExtra("pos", position);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount()
    {
        if (contactList != null && contactList.size() >= 8)
        {
            return 8;
        }
        else if (contactList != null && contactList.size() < 8)
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
        private TextView tv_name, tv_date, tv_api_level;

        public ViewHolder(View view)
        {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.image);
            playbtn = (ImageView) view.findViewById(R.id.play_trend);
            tv_name = (TextView) view.findViewById(R.id.title);
        }
    }

}
