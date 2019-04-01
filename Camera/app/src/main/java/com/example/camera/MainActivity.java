package com.example.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAMERA = 1;
    static final int REQUEST_VIDEO_CAPTURE = 101;

    String currentPhotoPath;
    String currentVideoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private void takePictureIntent(){
        // Create Picture Intent
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePicture.resolveActivity(getPackageManager())!=null){
            File photoFile = null;
            try{
                photoFile =createImageFile();
            }catch(IOException ex){
                ex.getMessage();
            }
            if(photoFile!=null){
                Uri providePhoto = FileProvider.getUriForFile(getApplicationContext(),"com.example.android.fileProvider",photoFile);
                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, providePhoto);
                startActivityForResult(takePicture, REQUEST_IMAGE_CAMERA);
            }
        }

    }
    private File createVideoFile() throws IOException{
        // Create an video file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "VIDEO_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File video = File.createTempFile(
                imageFileName,  /* prefix */
                ".mp4",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentVideoPath = video.getAbsolutePath();
        return video;
    }

    private void takeVideoIntent(){
        // Creating a video intent
        Intent takeVideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if(takeVideo.resolveActivity(getPackageManager())!=null){
            File videoFile = null;
            try{
                videoFile = createVideoFile();
            }catch(IOException ex){
                ex.getMessage();
            }
            if(videoFile!=null){
                Uri provideVideo = FileProvider.getUriForFile(getApplicationContext(),"com.example.android.fileProvider",videoFile);
                takeVideo.putExtra(MediaStore.EXTRA_OUTPUT, provideVideo);
                startActivityForResult(takeVideo, REQUEST_VIDEO_CAPTURE);
            }
            }
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView imageFrag = this.findViewById(R.id.imageFrag);
        imageFrag.setBackgroundColor(Color.GRAY);
        final TextView videoFrag = this.findViewById(R.id.videoFrag);
        videoFrag.setBackgroundColor(Color.WHITE);
        final RelativeLayout imageLayout = this.findViewById(R.id.imageLayout);

        final RelativeLayout videoLayout = this.findViewById(R.id.videoLayout);
        imageFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(Color.GRAY);
                videoFrag.setBackgroundColor(Color.WHITE);
                videoLayout.setVisibility(View.GONE);
                imageLayout.setVisibility(View.VISIBLE);
            }
        });
        videoFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(Color.GRAY);
                imageFrag.setBackgroundColor(Color.WHITE);
                imageLayout.setVisibility(View.GONE);
                videoLayout.setVisibility(View.VISIBLE);
            }
        });

        Button cameraButton = this.findViewById(R.id.cameraButton);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    takePictureIntent();
            }
        });
        Button videoButton = this.findViewById(R.id.videoButton);
        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeVideoIntent();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent intent){
        if(requestcode==REQUEST_IMAGE_CAMERA){
            File imageFile = new File(currentPhotoPath);
            if(imageFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                ImageView myImage = this.findViewById(R.id.imageViewID);
                myImage.setImageBitmap(myBitmap);
            }
            }if (requestcode == REQUEST_VIDEO_CAPTURE && resultcode == RESULT_OK) {
            Uri videoUri = intent.getData();
            final VideoView videoView = this.findViewById(R.id.videoViewID);

            videoView.setVideoURI(videoUri);
            videoView.requestFocus();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    videoView.start();
                }
            });
        }

        }
    }



