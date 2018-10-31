package editor.avilaksh.com.edi;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import editor.avilaksh.com.edi.Utils.Bitmap_Utils;
import editor.avilaksh.com.editorapp.R;

public class InputProfile extends AppCompatActivity {
    EditText editTextfirstName, editTextLastname, editTextEmail, editTextphonenumber, editTextDesignation;
    Button saveInformation;
    ImageView editButton, profileimage;
    public static final int PERMISSION_INSERT_IMAGE = 1001;
    private static final int MULTIPLE_PERMISSIONS = 5;
    Context mContext;
    SharedPreferences.Editor sharedPrefence;
    private static final String TAG = "InputProfile";
    public static final int PERMISSION_PICK_IMAGE = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_profile);
        editTextfirstName = (EditText) findViewById(R.id.editTextfirstname);
        editTextLastname = (EditText) findViewById(R.id.editTextlastname);
        editTextEmail = (EditText) findViewById(R.id.edittextemail);
        editTextphonenumber = (EditText) findViewById(R.id.edittextphonenumber);
        editTextDesignation = (EditText) findViewById(R.id.edittextdesignation);
        saveInformation = (Button) findViewById(R.id.buttonsaveall);
        editButton = (ImageView) findViewById(R.id.id_edit);
        profileimage = (ImageView) findViewById(R.id.profilePic);
        mContext = InputProfile.this;
        sharedPrefence = getSharedPreferences("DATA", MODE_PRIVATE).edit();

        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageFromGallery();

            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageFromGallery();
            }
        });

        saveInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = editTextfirstName.getText().toString();
                String lastName = editTextLastname.getText().toString();
                String email = editTextEmail.getText().toString();
                String designationName = editTextDesignation.getText().toString();
                String phonenumber = editTextphonenumber.getText().toString();
                if (firstName.equals("") || lastName.equals("") || email.equals("") || editTextphonenumber.equals("") || designationName.equals("")) {
                    Toast.makeText(InputProfile.this, "All field are required", Toast.LENGTH_LONG).show();
                } else {

                    sharedPrefence.putString("firstName", firstName);
                    sharedPrefence.putString("lastName", lastName);
                    sharedPrefence.putString("emailtName", email);
                    sharedPrefence.putString("designation", designationName);
                    sharedPrefence.putString("phoneNumber", phonenumber);
                    sharedPrefence.apply();
                    SharedPreferences spf = getSharedPreferences("DATA", MODE_PRIVATE);
                    String string1 = spf.getString("firstName", null);
                    String string2 = spf.getString("lastName", null);
                    String profile = spf.getString("profileImage", null);
                    if (profile != null) {
                        if (string1.equals(firstName) && string2.equals(lastName)) {
                            Toast.makeText(InputProfile.this, "Profile Info saved", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(InputProfile.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(InputProfile.this, "Try Again", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Toast.makeText(InputProfile.this, "Select profile picture,Try Again", Toast.LENGTH_LONG).show();


                    }


                }

            }
        });


    }

    private void openImageFromGallery() {

        if (ContextCompat.checkSelfPermission(InputProfile.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) +
                ContextCompat.checkSelfPermission(InputProfile.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(InputProfile.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(InputProfile.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Snackbar.make(InputProfile.this.findViewById(android.R.id.content),
                        "Please Grant Permissions",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(InputProfile.this,
                                        new String[]{Manifest.permission
                                                .WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                        MULTIPLE_PERMISSIONS);
                            }
                        }).show();


            } else {
                ActivityCompat.requestPermissions(InputProfile.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, MULTIPLE_PERMISSIONS);

            }
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            // intent.setType("image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, PERMISSION_PICK_IMAGE);


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS:
                if (grantResults.length > 0) {
                    boolean writePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readExternalFile = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (writePermission && readExternalFile) {
                        // write your logic here
                    } else {
                        Snackbar.make(InputProfile.this.findViewById(android.R.id.content),
                                "Please Grant Permissions",
                                Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ActivityCompat.requestPermissions(InputProfile.this,
                                                new String[]{Manifest.permission
                                                        .WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                                MULTIPLE_PERMISSIONS);
                                    }
                                }).show();
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PERMISSION_PICK_IMAGE) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                String filesPath = data.getData().getPath();
                Uri fileUri = data.getData();
                startCrop(fileUri);
                cursor.close();


//                Bitmap bitmap = Bitmap_Utils.getBitmapFromGallery(this, data.getData(), 800, 800);
////
////                image_selected_uri = data.getData();
////
////                //clear bitmap memory
////                originalBitmap.recycle();
////                finalBitmap.recycle();
////                filteredBitmap.recycle();
////                originalBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
////                finalBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
////                filteredBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
////                phoroEditorView.getSource().setImageBitmap(originalBitmap);
////
////                bitmap.recycle();
////
////                //fix crush
////
////                filterListFragment = (FilterListFragment) FilterListFragment.getInstance(originalBitmap);
////                filterListFragment.setListener(this);

            } else if (requestCode == UCrop.REQUEST_CROP) {
                handleCropResult(data);
            } else if (requestCode == UCrop.RESULT_ERROR) {
                handleCropError(data);
            }
        }
    }

    private void startCrop(Uri uri) {
        UCrop.Options options = new UCrop.Options();
//        options.setCropFrameColor(getResources().getColor(R.color.colorAccent));
        options.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        //options.setToolbarWidgetColor(getResources().getColor(R.color.colorgreen));
        options.setActiveWidgetColor(getResources().getColor(R.color.colorPrimary));
        options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
        options.setRootViewBackgroundColor(getResources().getColor(R.color.selectedfilter));

//        options.setFreeStyleCropEnabled(true);
//        options.setToolbarCancelDrawable(R.drawable.aboutus);
//        options.setToolbarCropDrawable(R.drawable.ic_template);
        //options.setCropGridColor(getResources().getColor(R.color.colorPrimary));
        // options.setDimmedLayerColor(getResources().getColor(R.color.colorPrimary));

        String destinationFileName = new StringBuilder(UUID.randomUUID().toString()).append(".jpg").toString();

        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));
        uCrop.withOptions(options);
        uCrop.start(InputProfile.this);


        //  uCrop= new UCrop.Options().setStatusBarColor(getResources().getColor(R.color.colorPrimary));


    }

    private void handleCropError(Intent data) {
        final Throwable cropError = UCrop.getError(data);
        if (cropError != null) {
            Toast.makeText(this, "" + cropError.getMessage(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Unexpected Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleCropResult(Intent data) {
        final Uri resultUri = UCrop.getOutput(data);
        if (resultUri != null) {


            // Bitmap photo_gallery = BitmapFactory.decodeFile(getRealPathFromURI(resultUri,InputProfile.this));
            sharedPrefence.putString("profileImage", String.valueOf(resultUri));
            Log.d(TAG, "handleCropResult: " + resultUri);
            profileimage.setImageURI(resultUri);
            sharedPrefence.putString("profileImage", String.valueOf(resultUri));

        } else {
            Toast.makeText(this, "Cant retrive crop image", Toast.LENGTH_SHORT).show();
        }

    }

    public String getRealPathFromURI(Uri contentURI, Activity context) {
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = context.managedQuery(contentURI, projection, null,
                null, null);
        if (cursor == null)
            return null;
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        if (cursor.moveToFirst()) {
            String s = cursor.getString(column_index);
            // cursor.close();
            return s;
        }
        // cursor.close();
        return null;
    }
}
