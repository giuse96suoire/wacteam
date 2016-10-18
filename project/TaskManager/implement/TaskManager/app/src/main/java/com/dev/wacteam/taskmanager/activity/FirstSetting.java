package com.dev.wacteam.taskmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.dev.wacteam.taskmanager.R;
import com.dev.wacteam.taskmanager.manager.EnumDefine;
import com.dev.wacteam.taskmanager.manager.StorageManager;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirstSetting extends AppCompatActivity {
    ImageView mIvAvatar;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://project-1106967331332325764.appspot.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_setting);
        mIvAvatar = (ImageView) findViewById(R.id.iv_avatar);
        StorageManager.mDownloadImage(mIvAvatar, "userAvatar/avatar.jpg");
        mIvAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChooseAvatar();
            }
        });
    }

    private void mChooseAvatar() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), EnumDefine.PICK_IMAGE_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EnumDefine.PICK_IMAGE_CODE && resultCode == RESULT_OK) {

        }
    }
}
;