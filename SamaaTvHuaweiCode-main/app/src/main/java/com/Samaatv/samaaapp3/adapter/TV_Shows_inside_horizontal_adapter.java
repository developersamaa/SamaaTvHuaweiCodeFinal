package com.Samaatv.samaaapp3.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Samaatv.samaaapp3.EditorVideoWeb;
import com.Samaatv.samaaapp3.R;
import com.Samaatv.samaaapp3.YoutubeVideoWeb;
import com.Samaatv.samaaapp3.model.Contact;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Administrator on 4/12/2017.
 */
public class TV_Shows_inside_horizontal_adapter extends RecyclerView.Adapter<TV_Shows_inside_horizontal_adapter.ViewHolder>
{

    static final String KEY_VIDEO = "videourl";
    String video;
    String ytVideo;
    String image;
    private ArrayList<Contact> contactList;
    private Context context;

    public TV_Shows_inside_horizontal_adapter(ArrayList<Contact> contactList, Context context)
    {
        this.contactList = contactList;
        this.context = context;
    }


    @Override
    public TV_Shows_inside_horizontal_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return checkScreenAndSetLayout(inflater, parent, context);
    }

    private ViewHolder checkScreenAndSetLayout(LayoutInflater inflater, ViewGroup parent, Context context)
    {

        float yInches = context.getResources().getDisplayMetrics().heightPixels / context.getResources().getDisplayMetrics().ydpi;
        float xInches = context.getResources().getDisplayMetrics().widthPixels / context.getResources().getDisplayMetrics().xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
        View view;
        if (diagonalInches >= 6.5)
        {
            view = inflater.inflate(R.layout.item_horizontal_row_large, parent, false);
        }
        else
        {
            view = inflater.inflate(R.layout.item_horizontal_row, parent, false);
        }
        return new ViewHolder(view)
        {
        };
    }

    @Override
    public void onBindViewHolder(TV_Shows_inside_horizontal_adapter.ViewHolder holder, int position)
    {

        try
        {
            holder.tv_name.setText(contactList.get(position).getTitle());
            video = contactList.get(position).getVideo();
            image = contactList.get(position).getImage();
            ytVideo = contactList.get(position).getYTVideo();
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

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (ytVideo != null && !ytVideo.equals("None"))
                {

                    Intent in = new Intent(context, YoutubeVideoWeb.class);
                    in.putExtra(KEY_VIDEO, ytVideo);
                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(in);

                }
                else
                {
                    if (video != null && !video.equals("None"))
                    {

                        Intent in = new Intent(context, EditorVideoWeb.class);
                        in.putExtra(KEY_VIDEO, video);
                        in.putExtra("image", image);
                        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(in);
                    }
                    else
                    {
                        Toast.makeText(context, "Sorry, video not available !", Toast.LENGTH_SHORT).show();
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

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tv_name;
        private ImageView imageView, playbtn;

        public ViewHolder(View view)
        {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.image);
            playbtn = (ImageView) view.findViewById(R.id.play_trend);
            tv_name = (TextView) view.findViewById(R.id.title);
        }
    }
}
