package editor.avilaksh.com.editorapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class VistingCardAdapter extends RecyclerView.Adapter<VistingCardAdapter.ViewHolder> {
    private static final String TAG = "InstagramPostsAdapter";
    Context mContext;
    List<PostItems> postItems;

    public VistingCardAdapter(Context mContext, PostItems postItem) {
        this.mContext = mContext;
        this.postItems = postItems;
    }

    @NonNull
    @Override
    public VistingCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.vistingcarditem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VistingCardAdapter.ViewHolder holder, final int position) {
//        holder.cityname.setText(InternshipCategoryItem.cityname[position]);
//        holder.inteenshipCiyimage.setImageResource(InternshipCategoryItem.cityimage[position]);
//        holder.countInternship.setText(InternshipCategoryItem.norintership[position]);
        //holder.post_images.setImageResource(PostItems.instagram_post_items[position]);
    }

    @Override
    public int getItemCount() {

        return 6;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView post_images;


        public ViewHolder(View itemView) {
            super(itemView);
            post_images = (ImageView) itemView.findViewById(R.id.imageViewvisitingcard);


        }

    }
}
