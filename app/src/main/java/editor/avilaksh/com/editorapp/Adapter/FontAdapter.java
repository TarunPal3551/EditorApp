package editor.avilaksh.com.editorapp.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import editor.avilaksh.com.editorapp.R;

/**
 * Created by mahmoud on 3/09/18.
 */

public class FontAdapter extends RecyclerView.Adapter<FontAdapter.FontviewHolder> {

    Context context;
    FontAdapterClickListener listener;
    List<String> fontList;
    int row_selected=-1;

    public FontAdapter(Context context, FontAdapterClickListener listener) {
        this.context = context;
        this.listener = listener;
        fontList=loadFontList();
    }

    private List<String> loadFontList() {
        List<String> result=new ArrayList<>();

        result.add("font_one.ttf");
        result.add("font_three.ttf");

        return result;
    }

    @NonNull
    @Override
    public FontviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.font_item,parent,false);
        return new FontviewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FontviewHolder holder, int position) {
        if(row_selected == position){
            holder.img_ckeck.setVisibility(View.VISIBLE);
        }else {
            holder.img_ckeck.setVisibility(View.INVISIBLE);
        }

        Typeface typeface=Typeface.createFromAsset(context.getAssets(),new StringBuilder("fonts/").append(fontList.get(position)).toString());

        holder.txt_font_name.setText(fontList.get(position));
        holder.txt_font_demo.setTypeface(typeface);

    }

    @Override
    public int getItemCount() {
        return fontList.size();
    }

    public class FontviewHolder extends RecyclerView.ViewHolder {
        TextView txt_font_name,txt_font_demo;
        ImageView img_ckeck;

        public FontviewHolder(View itemView) {
            super(itemView);
            txt_font_name=(TextView)itemView.findViewById(R.id.txt_font_name);
            txt_font_demo=(TextView)itemView.findViewById(R.id.txt_font_demo);

            img_ckeck=(ImageView)itemView.findViewById(R.id.img_ckeck);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onFontSelected(fontList.get(getAdapterPosition()));
                    row_selected=getAdapterPosition();
                    notifyDataSetChanged();

                }
            });

        }
    }


    public interface FontAdapterClickListener{
        public void onFontSelected(String fontName);

    }
}
