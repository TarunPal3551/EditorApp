package editor.avilaksh.com.editorapp;


import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import editor.avilaksh.com.editorapp.Adapter.EmojiAdapter;
import editor.avilaksh.com.editorapp.Interface.EmojiFragmentListener;
import ja.burhanrashid52.photoeditor.PhotoEditor;


/**
 * A simple {@link Fragment} subclass.
 */
public class EmojiFragment extends BottomSheetDialogFragment implements EmojiAdapter.EmojiAdapterListener {

    RecyclerView recycler_emoji;
    static  EmojiFragment instance;
    EmojiFragmentListener listener;

    public void setListener(EmojiFragmentListener listener) {
        this.listener = listener;
    }

    public static EmojiFragment getInstance() {
        if(instance == null)
            instance=new EmojiFragment();
        return instance;
    }

    public EmojiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemview= inflater.inflate(R.layout.fragment_emoji, container, false);

        recycler_emoji =(RecyclerView) itemview.findViewById(R.id.recycler_emoji);
        recycler_emoji.setHasFixedSize(true);
        recycler_emoji.setLayoutManager(new GridLayoutManager(getActivity(),5));
        EmojiAdapter adapter=new EmojiAdapter(getContext(), PhotoEditor.getEmojis(getContext()),this);
        recycler_emoji.setAdapter(adapter);

        return  itemview;

    }

    @Override
    public void onEmojiItemSelected(String emoji) {
        listener.onEmojiSelected(emoji);

    }
}
