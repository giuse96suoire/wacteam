package com.dev.wacteam.taskmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.dev.wacteam.taskmanager.R;
import com.dev.wacteam.taskmanager.manager.EnumDefine;
import com.dev.wacteam.taskmanager.manager.StorageManager;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirstSetting extends AppCompatActivity {
    ImageView mIvAvatar;
    Spinner mSpGender;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://project-1106967331332325764.appspot.com");
    private Button btnSave, btnReset;
    private EditText etDisplayName, etGender, etDob, etAdress, etPhoneNumber, etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);
        init();
//        mSpGender = (Spinner) findViewById(R.id.sp_gender);
        final String[] dataGender = new String[]{"Male", "Female"};
//        mSpGender.setAdapter(new ArrayAdapter<String>(FirstSetting.this, android.R.layout.simple_expandable_list_item_1, dataGender));
        StorageManager.mDownloadImage(mIvAvatar, "userAvatar/avatar.jpg");
        mIvAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChooseAvatar();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSaveInfo();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResetInfo();
            }
        });
    }

    private void init() {
//        mIvAvatar = (ImageView) findViewById(R.id.iv_avatar);
//        btnSave = (Button) findViewById(R.id.btn_save);
//        btnReset = (Button) findViewById(R.id.btn_reset);
//        etDisplayName = (EditText) findViewById(R.id.et_displayName);
//        etGender = (EditText) findViewById(R.id.et_gender);
//        etDob = (EditText) findViewById(R.id.et_dob);
//        etAdress = (EditText) findViewById(R.id.et_address);
//        etPhoneNumber = (EditText) findViewById(R.id.et_phoneNumber);
//        etEmail = (EditText) findViewById(R.id.et_email);
    }

    private void mSaveInfo() {


        Intent intent = new Intent(FirstSetting.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void mResetInfo() {
        etDisplayName.setText("");
        etGender.setText("");
        etDob.setText("");
        etAdress.setText("");
        etPhoneNumber.setText("");
        etEmail.setText("");
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