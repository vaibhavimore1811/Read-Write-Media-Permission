package com.vaibhavi.example;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

     // declare view
    Button btnReadMediaImages,btnReadMediaVideo,btnReadMediaAudio;

    String[] IMAGE_PERMISSION= new String[]
            {
                    Manifest.permission.READ_MEDIA_IMAGES
            };
    String[] VIDEO_PERMISSION= new String[]
            {
                    Manifest.permission.READ_MEDIA_VIDEO
            };
    String[] AUDIO_PERMISSION= new String[]
            {
                    Manifest.permission.READ_MEDIA_AUDIO
            };

    boolean is_video_permission = false;
    boolean is_audio_permission = false;
    boolean is_image_permission = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        // find id of view
        btnReadMediaImages = findViewById(R.id.btnReadMediaImages);
        btnReadMediaVideo = findViewById(R.id.btnReadMediaVideo);
        btnReadMediaAudio = findViewById(R.id.btnReadMediaAudio);

        btnReadMediaImages.setOnClickListener(this);
        btnReadMediaVideo.setOnClickListener(this);
        btnReadMediaAudio.setOnClickListener(this);


        // assign on click Listener
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnReadMediaImages:
                   getImagePermission();

              break;
            case R.id.btnReadMediaVideo:
                getImagePermission();
                break;
            case R.id.btnReadMediaAudio:
                getAudioPermission();
                break;
        }

    }

    private void getImagePermission() {
        if(IsImagePermissionDone())
        {
            btnReadMediaImages.setText("Image Permission done");
        }else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // Code to run if SDK version is Marshmallow (API level 33) or higher
                activityResultLauncherImage.launch(IMAGE_PERMISSION[0]);
            } else {
                // Code to run if SDK version is lower than TIRAMISU
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
            }

        }

    }

    private void getAudioPermission() {
        if(IsImagePermissionDone())
        {
            btnReadMediaAudio.setText("Audio Permission done");
        }else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // Code to run if SDK version is Marshmallow (API level 33) or higher
                activityResultLauncherAudio.launch(AUDIO_PERMISSION[0]);
            } else {
                // Code to run if SDK version is lower than TIRAMISU
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.RECORD_AUDIO},101);
            }

        }

    }



    private boolean IsImagePermissionDone() {
        return ContextCompat.checkSelfPermission(MainActivity.this,IMAGE_PERMISSION[0] )== PackageManager.PERMISSION_GRANTED;}

    private ActivityResultLauncher<String> activityResultLauncherImage = registerForActivityResult(new ActivityResultContracts.RequestPermission(),isGranted->{

        if(isGranted)
        {
            btnReadMediaImages.setText("Image Permission done");
            btnReadMediaVideo.setText("Video Permission done");
        }
    });


    private boolean IsAudioPermissionDone() {
        return ContextCompat.checkSelfPermission(MainActivity.this,AUDIO_PERMISSION[0] )== PackageManager.PERMISSION_GRANTED;}

    private ActivityResultLauncher<String> activityResultLauncherAudio = registerForActivityResult(new ActivityResultContracts.RequestPermission(),isGranted->{

        if(isGranted)
        {
            btnReadMediaAudio.setText("Audio Permission done");
        }
    });


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                btnReadMediaImages.setText("Image Permission done");
                btnReadMediaVideo.setText("Video Permission done");
                btnReadMediaAudio.setText("Audio Permission done");
            } else {

            }
        }
        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                btnReadMediaAudio.setText("Audio Permission done");
            } else {

            }
        }
    }
}
