package editor.avilaksh.com.editorapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yalantis.ucrop.UCrop;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import editor.avilaksh.com.editorapp.Adapter.ViewPagerAdapter;
import editor.avilaksh.com.editorapp.Interface.AddFrameListener;
import editor.avilaksh.com.editorapp.Interface.AddTextFragmentListener;
import editor.avilaksh.com.editorapp.Interface.BrushFragmentListener;
import editor.avilaksh.com.editorapp.Interface.EditImageFragmentListener;
import editor.avilaksh.com.editorapp.Interface.EmojiFragmentListener;
import editor.avilaksh.com.editorapp.Interface.FiltersListFragmentListener;
import editor.avilaksh.com.editorapp.Utils.Bitmap_Utils;
import ja.burhanrashid52.photoeditor.OnSaveBitmap;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;

public class EditingActivity extends AppCompatActivity implements FiltersListFragmentListener, EditImageFragmentListener, BrushFragmentListener, EmojiFragmentListener, AddTextFragmentListener, AddFrameListener {
    public static final String picture_name = "flash.png";

    public static final int PERMISSION_PICK_IMAGE = 1000;
    public static final int PERMISSION_INSERT_IMAGE = 1001;
    private static final int MULTIPLE_PERMISSIONS = 5;

    PhotoEditorView phoroEditorView;

    CoordinatorLayout coordinatorLayout;
    Bitmap originalBitmap, filteredBitmap, finalBitmap;

    FilterListFragment filterListFragment;
    EditImageFragment editImageFragment;
    CardView btn_filter_list, bnt_edit, btn_brush, btn_emoji, btn_add_text, btn_add_image, btn_add_frame, btn_crop;


    PhotoEditor photoEditor;


    int brightnessFinal = 0;
    float saturationFinal = 1.0f;
    float constrantFinal = 1.0f;

    Uri image_selected_uri;

    //load native image filter lib

    static {
        System.loadLibrary("NativeImageProcessor");

    }

    RelativeLayout topbarEditor;
    ImageView open_Image, save_image, undo_Image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Editor");
        topbarEditor = (RelativeLayout) findViewById(R.id.topbar_editor);
        open_Image = (ImageView) findViewById(R.id.imageViewopen);
        save_image = (ImageView) findViewById(R.id.imageViewsave);
        undo_Image = (ImageView) findViewById(R.id.imageViewundo);


        //view
        phoroEditorView = (PhotoEditorView) findViewById(R.id.image_preview);
        photoEditor = new PhotoEditor.Builder(this, phoroEditorView)
                .setPinchTextScalable(true)
                .setDefaultEmojiTypeface(Typeface.createFromAsset(getAssets(), "emojione-android.ttf"))
                .build();

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        bnt_edit = (CardView) findViewById(R.id.btn_edit);
        btn_filter_list = (CardView) findViewById(R.id.btn_filters_list);
        // btn_brush = (CardView) findViewById(R.id.btn_brunsh);

        // btn_emoji = (CardView) findViewById(R.id.btn_emoji);
        btn_add_text = (CardView) findViewById(R.id.btn_add_text);
        btn_add_image = (CardView) findViewById(R.id.btn_add_image);

        //btn_add_frame = (CardView) findViewById(R.id.btn_add_frame);

        btn_crop = (CardView) findViewById(R.id.btn_crop);
        open_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageFromGallery();
            }
        });
        save_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveImageFromGallery();
            }
        });
        undo_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                undo();
            }
        });


        btn_crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCrop(image_selected_uri);
            }
        });

        btn_filter_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filterListFragment != null) {

                    filterListFragment.show(getSupportFragmentManager(), filterListFragment.getTag());
                } else {
                    FilterListFragment filterListFragment = (FilterListFragment) FilterListFragment.getInstance(null);
                    filterListFragment.setListener(EditingActivity.this);
                    filterListFragment.show(getSupportFragmentManager(), filterListFragment.getTag());
                }

            }
        });

        bnt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditImageFragment editImageFragment = EditImageFragment.getInstance();
                editImageFragment.setListener(EditingActivity.this);
                editImageFragment.show(getSupportFragmentManager(), editImageFragment.getTag());
            }
        });


//        btn_brush.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                photoEditor.setBrushDrawingMode(true);
//                BrushFragment brushFragment = BrushFragment.getInstance();
//                brushFragment.setListener(EditingActivity.this);
//                brushFragment.show(getSupportFragmentManager(), brushFragment.getTag());
//            }
//        });

//        btn_emoji.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EmojiFragment emojiFragment = EmojiFragment.getInstance();
//                emojiFragment.setListener(EditingActivity.this);
//                emojiFragment.show(getSupportFragmentManager(), emojiFragment.getTag());
//            }
//        });

        btn_add_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTextFragment addTextFragment = AddTextFragment.getInstance();
                addTextFragment.setListener(EditingActivity.this);
                addTextFragment.show(getSupportFragmentManager(), addTextFragment.getTag());
            }
        });


        btn_add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addImageToPicture();
            }
        });

//        btn_add_frame.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FrameFragment frameFragment = FrameFragment.getInstance();
//                frameFragment.setListener(EditingActivity.this);
//                frameFragment.show(getSupportFragmentManager(), frameFragment.getTag());
//            }
//        });


//        loadImage();
        loadImage(R.drawable.facebook);


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
        uCrop.start(EditingActivity.this);

        //  uCrop= new UCrop.Options().setStatusBarColor(getResources().getColor(R.color.colorPrimary));


    }

    private void addImageToPicture() {
        if (ContextCompat.checkSelfPermission(EditingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) +
                ContextCompat.checkSelfPermission(EditingActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(EditingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(EditingActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Snackbar.make(EditingActivity.this.findViewById(android.R.id.content),
                        "Please Grant Permissions",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(EditingActivity.this,
                                        new String[]{Manifest.permission
                                                .WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                        MULTIPLE_PERMISSIONS);
                            }
                        }).show();


            }
            else {
                ActivityCompat.requestPermissions(EditingActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, MULTIPLE_PERMISSIONS);

            }
        }
        else {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PERMISSION_INSERT_IMAGE);


        }

//        Dexter.withActivity(this)
//                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .withListener(new MultiplePermissionsListener() {
//                    @Override
//                    public void onPermissionsChecked(MultiplePermissionsReport report) {
//                        if(report.areAllPermissionsGranted()){
//                            Intent intent=new Intent(Intent.ACTION_PICK);
//                            intent.setType("image/*");
//                            startActivityForResult(intent,PERMISSION_INSERT_IMAGE);
//                        }
//
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {
//                        Toast.makeText(EditingActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
//
//                    }
//
//                }).check();
    }

    private void loadImage(int image) {

        originalBitmap = Bitmap_Utils.decodeSampledBitmapFromResource(getResources(), image, 300, 300);
        filteredBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        finalBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        phoroEditorView.getSource().setImageBitmap(originalBitmap);


    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        filterListFragment = new FilterListFragment();
        filterListFragment.setListener(this);

        editImageFragment = new EditImageFragment();
        editImageFragment.setListener(this);


        adapter.addFragment(filterListFragment, "FILTERS");
        adapter.addFragment(editImageFragment, "EDIT");

        viewPager.setAdapter(adapter);

    }


    @Override
    public void onBrightnessChanged(int brightness) {

        brightnessFinal = brightness;
        Filter myfilter = new Filter();
        myfilter.addSubFilter(new BrightnessSubFilter(brightness));
        phoroEditorView.getSource().setImageBitmap(myfilter.processFilter(finalBitmap.copy(Bitmap.Config.ARGB_8888, true)));

    }

    @Override
    public void onSaturationChanged(float saturation) {
        saturationFinal = saturation;
        Filter myfilter = new Filter();
        myfilter.addSubFilter(new SaturationSubfilter(saturation));
        phoroEditorView.getSource().setImageBitmap(myfilter.processFilter(finalBitmap.copy(Bitmap.Config.ARGB_8888, true)));

    }

    @Override
    public void onConstrantChanged(float constrant) {
        constrantFinal = constrant;
        Filter myfilter = new Filter();
        myfilter.addSubFilter(new ContrastSubFilter(constrant));
        phoroEditorView.getSource().setImageBitmap(myfilter.processFilter(finalBitmap.copy(Bitmap.Config.ARGB_8888, true)));

    }

    @Override
    public void onEditStarted() {


    }

    @Override
    public void onEditCompleted() {
        Bitmap bitmap = filteredBitmap.copy(Bitmap.Config.ARGB_8888, true);

        Filter myFilter = new Filter();
        myFilter.addSubFilter(new BrightnessSubFilter(brightnessFinal));
        myFilter.addSubFilter(new SaturationSubfilter(saturationFinal));
        myFilter.addSubFilter(new ContrastSubFilter(constrantFinal));

        finalBitmap = myFilter.processFilter(bitmap);

    }

    @Override
    public void onFilterSelected(Filter filter) {
        resetControl();
        filteredBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        phoroEditorView.getSource().setImageBitmap(filter.processFilter(filteredBitmap));
        finalBitmap = filteredBitmap.copy(Bitmap.Config.ARGB_8888, true);


    }

    private void resetControl() {
        if (editImageFragment != null) {
            editImageFragment.resetControls();
        }
        brightnessFinal = 0;
        saturationFinal = 1.0f;
        constrantFinal = 1.0f;


    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//
//        return true;
//    }

    //
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_open) {
//            openImageFromGallery();
//            return true;
//        }
//
//        if (id == R.id.action_save) {
//            saveImageFromGallery();
//            return true;
//        }
//        if (id==R.id.action_undo){
//            undo();
//            resetControl();
//
//
//        }
//        return super.onOptionsItemSelected(item);
//    }
    public void undo() {
        photoEditor.undo();
    }

    private void saveImageFromGallery() {
        if (ContextCompat.checkSelfPermission(EditingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) +
                ContextCompat.checkSelfPermission(EditingActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(EditingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(EditingActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Snackbar.make(EditingActivity.this.findViewById(android.R.id.content),
                        "Please Grant Permissions",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(EditingActivity.this,
                                        new String[]{Manifest.permission
                                                .WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                        MULTIPLE_PERMISSIONS);
                            }
                        }).show();


            } else {
                ActivityCompat.requestPermissions(EditingActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, MULTIPLE_PERMISSIONS);

            }
        } else {
            photoEditor.saveAsBitmap(new OnSaveBitmap() {
                @Override
                public void onBitmapReady(Bitmap saveBitmap) {
                    try {

                        phoroEditorView.getSource().setImageBitmap(saveBitmap);
                        final String path = Bitmap_Utils.insertImage(getContentResolver(), saveBitmap
                                , System.currentTimeMillis() + "_profile.jpg", null);
                        if (!TextUtils.isEmpty(path)) {
                            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Image Save to gallery",
                                    Snackbar.LENGTH_LONG).setAction("OPEN", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    openImage(path);

                                }
                            });
                            snackbar.show();

                        } else {
                            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Unable to save Image",
                                    Snackbar.LENGTH_LONG);
                            snackbar.show();

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Exception e) {

                }
            });


        }


//        Dexter.withActivity(this)
//                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .withListener(new MultiplePermissionsListener() {
//                    @Override
//                    public void onPermissionsChecked(MultiplePermissionsReport report) {
//                        if(report.areAllPermissionsGranted()){
//                            photoEditor.saveAsBitmap(new OnSaveBitmap() {
//                                @Override
//                                public void onBitmapReady(Bitmap saveBitmap) {
//                                    try {
//
//                                        phoroEditorView.getSource().setImageBitmap(saveBitmap);
//                                        final String path=Bitmap_Utils.insertImage(getContentResolver(),saveBitmap
//                                                ,System.currentTimeMillis()+"_profile.jpg",null);
//                                        if (!TextUtils.isEmpty(path)) {
//                                            Snackbar snackbar=Snackbar.make(coordinatorLayout,"Image Save to gallery",
//                                                    Snackbar.LENGTH_LONG).setAction("OPEN", new View.OnClickListener() {
//                                                @Override
//                                                public void onClick(View view) {
//                                                    openImage(path);
//
//                                                }
//                                            });
//                                            snackbar.show();
//
//                                        }
//                                        else {
//                                            Snackbar snackbar=Snackbar.make(coordinatorLayout,"Unable to save Image",
//                                                    Snackbar.LENGTH_LONG);
//                                            snackbar.show();
//
//                                        }
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Exception e) {
//
//                                }
//                            });
//                        }else {
//                            Toast.makeText(EditingActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {
//                        token.continuePermissionRequest();
//                    }
//                }).check();


    }

    private void openImage(String path) {
        if (ContextCompat.checkSelfPermission(EditingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) +
                ContextCompat.checkSelfPermission(EditingActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(EditingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(EditingActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Snackbar.make(EditingActivity.this.findViewById(android.R.id.content),
                        "Please Grant Permissions",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(EditingActivity.this,
                                        new String[]{Manifest.permission
                                                .WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                        MULTIPLE_PERMISSIONS);
                            }
                        }).show();


            } else {
                ActivityCompat.requestPermissions(EditingActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, MULTIPLE_PERMISSIONS);

            }
        } else {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(path), "image/*");
            startActivity(intent);


        }


    }

    private void openImageFromGallery() {

        if (ContextCompat.checkSelfPermission(EditingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) +
                ContextCompat.checkSelfPermission(EditingActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(EditingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(EditingActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Snackbar.make(EditingActivity.this.findViewById(android.R.id.content),
                        "Please Grant Permissions",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(EditingActivity.this,
                                        new String[]{Manifest.permission
                                                .WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                        MULTIPLE_PERMISSIONS);
                            }
                        }).show();


            } else {
                ActivityCompat.requestPermissions(EditingActivity.this,
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

//        Dexter.withActivity(this)
//                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .withListener(new MultiplePermissionsListener() {
//                    @Override
//                    public void onPermissionsChecked(MultiplePermissionsReport report) {
//                        if(report.areAllPermissionsGranted()){
//
//                        }else {
//                            Toast.makeText(EditingActivity.this, "Permission denied !", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {
//                        token.continuePermissionRequest();
//                    }
//                }).check();

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
                        Snackbar.make(EditingActivity.this.findViewById(android.R.id.content),
                                "Please Grant Permissions",
                                Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ActivityCompat.requestPermissions(EditingActivity.this,
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

                Bitmap bitmap = Bitmap_Utils.getBitmapFromGallery(this, data.getData(), 800, 800);

                image_selected_uri = data.getData();

                //clear bitmap memory
                originalBitmap.recycle();
                finalBitmap.recycle();
                filteredBitmap.recycle();
                originalBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                finalBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
                filteredBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
                phoroEditorView.getSource().setImageBitmap(originalBitmap);

                bitmap.recycle();

                //fix crush

                filterListFragment = (FilterListFragment) FilterListFragment.getInstance(originalBitmap);
                filterListFragment.setListener(this);

            } else if (requestCode == PERMISSION_INSERT_IMAGE) {
                Bitmap bitmap = Bitmap_Utils.getBitmapFromGallery(this, data.getData(), 300, 300);
                photoEditor.addImage(bitmap);

            } else if (requestCode == UCrop.REQUEST_CROP) {
                handleCropResult(data);
            } else if (requestCode == UCrop.RESULT_ERROR) {
                handleCropError(data);
            }
        }
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
            phoroEditorView.getSource().setImageURI(resultUri);

        } else {
            Toast.makeText(this, "Cant retrive crop image", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onBrushSizeChangeListener(Float size) {
        photoEditor.setBrushSize(size);
    }

    @Override
    public void onBrushopacityChangeListener(int opacity) {

        photoEditor.setOpacity(opacity);

    }

    @Override
    public void onBrushColorChangedListener(int color) {
        photoEditor.setBrushColor(color);

    }

    @Override
    public void onBrushStateChangedListener(boolean isEraser) {
        if (isEraser)
            photoEditor.brushEraser();
        else
            photoEditor.setBrushDrawingMode(true);
    }

    @Override
    public void onEmojiSelected(String emoji) {
        photoEditor.addEmoji(emoji);
    }


    @Override
    public void OnAddTextButtonClick(Typeface typeface, String text, int color) {
        photoEditor.addText(typeface, text, color);
    }

    @Override
    public void onAddFrame(int frame) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), frame);
        photoEditor.addImage(bitmap);
    }
}
