package editor.avilaksh.com.editorapp;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import editor.avilaksh.com.editorapp.Adapter.ColorAdapter;
import editor.avilaksh.com.editorapp.Adapter.FontAdapter;
import editor.avilaksh.com.editorapp.Interface.AddTextFragmentListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddTextFragment extends BottomSheetDialogFragment implements ColorAdapter.ColorAdapterListener, FontAdapter.FontAdapterClickListener {

    int colorSelected = Color.parseColor("#000000");

    AddTextFragmentListener listener;

    EditText edit_add_text;
    Button btn_done;
    SeekBar spacingSeekbar, textsizeSeekbar;
    TextView textViewTextSize, textViewSpacing, textViewSpacingNumberView, textViewTextSizeNumberView;
    Spinner fontStyleSpinner;
    ImageView textAligmentImage, textBoldImage, textItalicImage, textCapsImage, colorImageView;
    RelativeLayout seekbarSpacingLayout, seekbarTextSizelayout,textEditLayout;
    Typeface typefaceSelected = Typeface.DEFAULT;
    int textAlligmentCount=0;


    static AddTextFragment instance;

    public static AddTextFragment getInstance() {
        if (instance == null)
            instance = new AddTextFragment();
        return instance;
    }

    public void setListener(AddTextFragmentListener listener) {
        this.listener = listener;
    }

    public AddTextFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemview = inflater.inflate(R.layout.fragment_add_text, container, false);
        textEditLayout=(RelativeLayout)itemview.findViewById(R.id.textEditingLayout);
        textEditLayout.setVisibility(View.VISIBLE);
        edit_add_text = (EditText) itemview.findViewById(R.id.edt_add_text);
        btn_done = (Button) itemview.findViewById(R.id.buttonaddtext);
        spacingSeekbar = (SeekBar) itemview.findViewById(R.id.seekBarspacing);
        textsizeSeekbar = (SeekBar) itemview.findViewById(R.id.seekBartextsize);
        textViewTextSize = (TextView) itemview.findViewById(R.id.fontsize);
        textViewSpacing = (TextView) itemview.findViewById(R.id.textViewspacing);
        textViewSpacingNumberView = (TextView) itemview.findViewById(R.id.textViewspacingnumber);
        textViewTextSizeNumberView = (TextView) itemview.findViewById(R.id.textViewtextsizenumber);
        textAligmentImage = (ImageView) itemview.findViewById(R.id.textaligment);
        textBoldImage = (ImageView) itemview.findViewById(R.id.textbold);
        textItalicImage = (ImageView) itemview.findViewById(R.id.textitalic);
        textCapsImage = (ImageView) itemview.findViewById(R.id.textcaps);
        colorImageView = (ImageView) itemview.findViewById(R.id.colorpallete);
        seekbarSpacingLayout = (RelativeLayout) itemview.findViewById(R.id.spacingSeekbarLayout);
        seekbarTextSizelayout = (RelativeLayout) itemview.findViewById(R.id.textsizelayout);
        seekbarTextSizelayout.setVisibility(View.GONE);
        seekbarSpacingLayout.setVisibility(View.GONE);

        fontStyleSpinner = (Spinner) itemview.findViewById(R.id.spinnerfonttype);
        final String fontStyle[] = {"Default", "Poppin", "Sans-Sarif", "Ubantu", "Monstreat", "Casual"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, fontStyle);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        fontStyleSpinner.setAdapter(spinnerArrayAdapter);
        fontStyleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selctedFont = fontStyle[position];///FontStyle
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        textViewTextSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekbarTextSizelayout.setVisibility(View.VISIBLE);

            }
        });
        textsizeSeekbar.setMax(100);
        textsizeSeekbar.setProgress(14);
        textsizeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewTextSizeNumberView.setText(String.valueOf(progress));
                textViewTextSize.setText(String.valueOf(progress));
                int textSize = progress;//////Size of text

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekbarTextSizelayout.setVisibility(View.GONE);

            }
        });
        spacingSeekbar.setMax(100);
        spacingSeekbar.setProgress(14);
        textViewSpacing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekbarSpacingLayout.setVisibility(View.VISIBLE);
            }
        });
        spacingSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewSpacingNumberView.setText(String.valueOf(progress));
               // textViewSpacing.setText(String.valueOf(progress));
                int textSpacing = progress;//////Text Spacing

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekbarSpacingLayout.setVisibility(View.GONE);

            }
        });
        colorImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textEditLayout.setVisibility(View.GONE);
                //// oolor Pallete dalni hai


            }
        });
        if (textAlligmentCount==0){
            //textAligmentImage.setImageResource("");/////RightSideAligmentImage
            textAlligmentCount=1;
        }
        else if(textAlligmentCount==1){
            //////leftSideAligmentImage
            textAlligmentCount=2;
        }
        else if (textAlligmentCount==2){
            /////textAlligmentCenterImage
            textAlligmentCount=0;
        }
        textAligmentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textAlligmentCount==0){
                    ///setImageofLeftAligment
                    textAlligmentCount=1;
                    String textAligment="Left";
                }
                else if (textAlligmentCount==1){
                    textAlligmentCount=2;
                    String textAligment="Center";
                }
                else if (textAlligmentCount==2){
                    textAlligmentCount=0;
                    String textAligment="Right";
                }



            }
        });


//        edit_add_text=(EditText)itemview.findViewById(R.id.edt_add_text);
//        btn_done=(Button)itemview.findViewById(R.id.btn_done);
//        recyclerView_color=(RecyclerView)itemview.findViewById(R.id.recycler_color);
//        recyclerView_color.setHasFixedSize(true);
//        recyclerView_color.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
//
//
//        recyclerView_font=(RecyclerView)itemview.findViewById(R.id.recycler_font);
//        recyclerView_font.setHasFixedSize(true);
//        recyclerView_font.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
//
//        ColorAdapter colorAdapter =new ColorAdapter(getActivity(),this);
//        recyclerView_color.setAdapter(colorAdapter);
//
//        FontAdapter fontAdapter=new FontAdapter(getContext(),this);
//        recyclerView_font.setAdapter(fontAdapter);
//
//
//
//        btn_done.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                listener.OnAddTextButtonClick(typefaceSelected,edit_add_text.getText().toString(),colorSelected);
//            }
//        });

        return itemview;
    }

    @Override
    public void onColorSelected(int color) {
        colorSelected = color;

    }

    @Override
    public void onFontSelected(String fontName) {
        typefaceSelected = Typeface.createFromAsset(getContext().getAssets(), new StringBuilder("fonts/")
                .append(fontName).toString());

    }
}
