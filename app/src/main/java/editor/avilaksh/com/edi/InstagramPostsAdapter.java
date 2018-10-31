package editor.avilaksh.com.edi;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import editor.avilaksh.com.editorapp.R;
import ja.burhanrashid52.photoeditor.PhotoEditor;

public class InstagramPostsAdapter extends RecyclerView.Adapter<InstagramPostsAdapter.ViewHolder> {
    private static final String TAG = "InstagramPostsAdapter";
    Context mContext;
    List<PostItems> postItems;

    public InstagramPostsAdapter(Context mContext, PostItems postItem) {
        this.mContext = mContext;
        this.postItems = postItems;
    }

    @NonNull
    @Override
    public InstagramPostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.instagram_layout_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InstagramPostsAdapter.ViewHolder holder, final int position) {
//        holder.cityname.setText(InternshipCategoryItem.cityname[position]);
//        holder.inteenshipCiyimage.setImageResource(InternshipCategoryItem.cityimage[position]);
//        holder.countInternship.setText(InternshipCategoryItem.norintership[position]);
          holder.post_images.setImageResource(PostItems.instagram_post_items[0]);

        holder.post_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,EditingActivity.class);
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {

        return 6;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView post_images;


        public ViewHolder(View itemView) {
            super(itemView);
            post_images = (ImageView) itemView.findViewById(R.id.imageView);


        }

    }
}
