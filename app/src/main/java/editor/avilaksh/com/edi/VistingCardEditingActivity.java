package editor.avilaksh.com.edi;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import editor.avilaksh.com.edi.Utils.Bitmap_Utils;
import editor.avilaksh.com.editorapp.R;
import ja.burhanrashid52.photoeditor.OnSaveBitmap;

public class VistingCardEditingActivity extends AppCompatActivity {
    Button save,share;
    CircleImageView imageView_profile;
    private Bitmap bitmap_visitingcard;
    RelativeLayout mainLayout;
    private static final int MULTIPLE_PERMISSIONS = 5;
    TextView nameTextView, designationTextView, emailTextView, phoneTextview;
    String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitingcardtemplate1);
        save = (Button) findViewById(R.id.button2);
        share=(Button)findViewById(R.id.buttonshare);

        nameTextView = (TextView) findViewById(R.id.textViewName);
        designationTextView = (TextView) findViewById(R.id.designation);
        emailTextView = (TextView) findViewById(R.id.emailid);
        phoneTextview = (TextView) findViewById(R.id.phonenumber);
        imageView_profile = (CircleImageView) findViewById(R.id.imageView4);
        mainLayout = (RelativeLayout) findViewById(R.id.vistingcard);
        SharedPreferences spf = getSharedPreferences("DATA", MODE_PRIVATE);
        String firstName = spf.getString("firstName", null);
        String lastName = spf.getString("lastName", null);
        String email = spf.getString("emailtName", null);
        String designation = spf.getString("designation", null);
        String phonenumber = spf.getString("phoneNumber", null);
        String profileImagePath = spf.getString("profileImage", null);
        nameTextView.setText(firstName + " " + lastName);
        emailTextView.setText(email);
        phoneTextview.setText(phonenumber);
        designationTextView.setText(designation);
        imageView_profile.setImageURI(Uri.parse(profileImagePath));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CardView shareLayout = (CardView) findViewById(R.id.card1);
                shareLayout.setDrawingCacheEnabled(true);
                shareLayout.buildDrawingCache();
                Bitmap bitmap = shareLayout.getDrawingCache();
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);

                try {
                    bitmap = BitmapFactory.decodeStream(getAssets().open("1024x768.jpg"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                bitmap_visitingcard = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
                saveImageFromGallery(bitmap_visitingcard);

            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (imagePath==null){
                    Toast.makeText(VistingCardEditingActivity.this,"First Save Image",Toast.LENGTH_LONG).show();
                }
                else {
                    final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Intent.EXTRA_STREAM,Uri.parse(imagePath));
                    intent.setType("image/jpg");
                    startActivity(intent);

                }



            }
        });


    }

    private void saveImageFromGallery(Bitmap saveBitmap) {

        if (ContextCompat.checkSelfPermission(VistingCardEditingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) +
                ContextCompat.checkSelfPermission(VistingCardEditingActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(VistingCardEditingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(VistingCardEditingActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Snackbar.make(VistingCardEditingActivity.this.findViewById(android.R.id.content),
                        "Please Grant Permissions",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(VistingCardEditingActivity.this,
                                        new String[]{Manifest.permission
                                                .WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                        MULTIPLE_PERMISSIONS);
                            }
                        }).show();


            } else {
                ActivityCompat.requestPermissions(VistingCardEditingActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, MULTIPLE_PERMISSIONS);

            }
        } else {

            try {
                imagePath = Bitmap_Utils.insertImage(getContentResolver(), saveBitmap
                        , System.currentTimeMillis() + "_profile.jpg", null);
                if (!TextUtils.isEmpty(imagePath)) {
                    Snackbar snackbar = Snackbar.make(mainLayout, "Image Save to gallery",
                            Snackbar.LENGTH_LONG).setAction("OPEN", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            openImage(imagePath);


                        }
                    });
                    snackbar.show();

                } else {
                    Snackbar snackbar = Snackbar.make(mainLayout, "Unable to save Image",
                            Snackbar.LENGTH_LONG);
                    snackbar.show();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    private void openImage(String path) {
        if (ContextCompat.checkSelfPermission(VistingCardEditingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) +
                ContextCompat.checkSelfPermission(VistingCardEditingActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(VistingCardEditingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(VistingCardEditingActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Snackbar.make(VistingCardEditingActivity.this.findViewById(android.R.id.content),
                        "Please Grant Permissions",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(VistingCardEditingActivity.this,
                                        new String[]{Manifest.permission
                                                .WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                        MULTIPLE_PERMISSIONS);
                            }
                        }).show();


            } else {
                ActivityCompat.requestPermissions(VistingCardEditingActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, MULTIPLE_PERMISSIONS);

            }
        } else {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(path), "image/*");
            startActivity(intent);


        }


    }

}
