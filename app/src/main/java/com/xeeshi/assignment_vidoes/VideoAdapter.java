package com.xeeshi.assignment_vidoes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZEESHAN on 21/02/2018.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private List<String> vidoeUrlList;
    private Context context;

    private long interval = 2000 * 1000;

    public VideoAdapter(Context context, List<String> vidoeUrlList) {
        this.context = context;
        this.vidoeUrlList = vidoeUrlList;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_list_row_xml, parent, false);
        return new VideoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        String videoUrl = vidoeUrlList.get(position);

        switch (position) {
            case 0:
                holder.imageView.setImageResource(android.R.color.holo_blue_light);
                break;
            case 1:
                holder.imageView.setImageResource(android.R.color.holo_green_light);
                break;
            case 2:
                holder.imageView.setImageResource(android.R.color.holo_orange_light);
                break;
            default:
                holder.imageView.setImageResource(android.R.color.holo_red_light);
                break;
        }

        /*try {
            Bitmap bitmap = ImageUtil.retriveVideoFrameFromVideo(Uri.decode(videoUrl));
            if (null != bitmap) {
                bitmap = Bitmap.createScaledBitmap(bitmap, 200, 250, false);
                holder.imageView.setImageBitmap(bitmap);
            } else {
                holder.imageView.setImageResource(android.R.color.holo_green_light);
            }

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }*/


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PlayerActivity.class);
                i.putExtra("URL", videoUrl);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return vidoeUrlList.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView_video_thumbnail) ImageView imageView;

        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }

}
