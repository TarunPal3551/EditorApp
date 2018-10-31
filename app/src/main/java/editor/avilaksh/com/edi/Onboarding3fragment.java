package editor.avilaksh.com.editorapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Onboarding3fragment extends android.support.v4.app.Fragment {
    TextView texthead,textdescp;
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.onboarding3, container, false);
        texthead=(TextView)rootView.findViewById(R.id.head3);
        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.poppins);
        texthead.setTypeface(typeface);

        textdescp=(TextView)rootView.findViewById(R.id.desp3);
        textdescp.setTypeface(typeface);
        imageView=(ImageView)rootView.findViewById(R.id.image3rd);
       // imageView.setImageResource(R.drawable.onboardinimage1);



        return rootView;
    }
}
