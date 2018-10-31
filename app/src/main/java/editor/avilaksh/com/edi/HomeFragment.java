package editor.avilaksh.com.edi;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import editor.avilaksh.com.edi.Utils.SpacesItemDecoration;
import editor.avilaksh.com.editorapp.R;

import static java.security.AccessController.getContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    RecyclerView recyclerView_instagram, recyclerView_facebook,promotionalpostel_recyler_view,vistingcard_recylerView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView_instagram = (RecyclerView) view.findViewById(R.id.recylerviewinsta);
        recyclerView_facebook = (RecyclerView) view.findViewById(R.id.recylerviewfacebook);
        promotionalpostel_recyler_view=(RecyclerView)view.findViewById(R.id.recylerviewpromotionalbanner);
        vistingcard_recylerView=(RecyclerView)view.findViewById(R.id.vistingrecylerview);


        setUpInstaPostCard();
        setUpFbPostCard();
        setUpPromotionalPCard();
        setUpVisitingCard();

        return view;
    }

    private void setUpInstaPostCard() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView_instagram.setLayoutManager(layoutManager);
        PostItems postItems = new PostItems();
        InstagramPostsAdapter instagramPostsAdapter = new InstagramPostsAdapter(getContext(), postItems);
        SpacesItemDecoration spacesItemDecoration=new SpacesItemDecoration(15);
        recyclerView_instagram.addItemDecoration(spacesItemDecoration);
        recyclerView_instagram.setAdapter(instagramPostsAdapter);
    }

    private void setUpFbPostCard() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView_facebook.setLayoutManager(layoutManager);
        PostItems postItems = new PostItems();
        FacebookPostsAdapter facebookPostsAdapter = new FacebookPostsAdapter(getContext(), postItems);
        SpacesItemDecoration spacesItemDecoration=new SpacesItemDecoration(15);
        recyclerView_facebook.addItemDecoration(spacesItemDecoration);
        recyclerView_facebook.setAdapter(facebookPostsAdapter);
    }
    private void setUpPromotionalPCard() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
       promotionalpostel_recyler_view.setLayoutManager(layoutManager);
        PostItems postItems = new PostItems();
        PromotionalPostelAdapter promotionalPostelAdapter = new PromotionalPostelAdapter(getContext(), postItems);
        SpacesItemDecoration spacesItemDecoration=new SpacesItemDecoration(15);
        promotionalpostel_recyler_view.addItemDecoration(spacesItemDecoration);
        promotionalpostel_recyler_view.setAdapter(promotionalPostelAdapter);
    }
    private void setUpVisitingCard() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        vistingcard_recylerView.setLayoutManager(layoutManager);
        PostItems postItems = new PostItems();
        VistingCardAdapter vistingCardAdapter = new VistingCardAdapter(getContext(), postItems);
        SpacesItemDecoration spacesItemDecoration=new SpacesItemDecoration(15);
        vistingcard_recylerView.addItemDecoration(spacesItemDecoration);
        vistingcard_recylerView.setAdapter(vistingCardAdapter);
    }

}
