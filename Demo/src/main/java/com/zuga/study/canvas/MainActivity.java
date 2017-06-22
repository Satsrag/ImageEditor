package com.zuga.study.canvas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.zuga.imageedit.ImageEditor;
import com.zuga.imageedit.activites.PermissionActivity;
import com.zuga.imageedit.utils.BitmapUtils;
import com.zuga.imageedit.utils.IntentUtil;
import com.zuga.keyboard.helpers.KeyboardManager;

public class MainActivity extends PermissionActivity {
    private final int REQUEST_CODE_GALLERY = 8747;
    private Type mType;
    private ImageView vImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vImageView = (ImageView) findViewById(R.id.iv_after);
        KeyboardManager.init(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (resultCode != RESULT_OK) return;
        if (requestCode == REQUEST_CODE_GALLERY && data != null && mType != null) {
            switch (mType) {
                case EDIT:
                    startEdit(data.getData());
                    break;
                case CROP:
                    startCrop(data.getData());
                    break;
            }
        } else if (requestCode == ImageEditor.REQUEST_CODE_CROP || requestCode == ImageEditor.REQUEST_CODE_EDIT) {
            String resultPath = ImageEditor.getResultPath(data, requestCode);
            vImageView.setImageBitmap(BitmapUtils.resizeImg(resultPath, 500, 500).bitmap);
        }
    }

    public void edit(View v) {
        mType = Type.EDIT;
        chooseImage();
    }

    public void crop(View v) {
        mType = Type.CROP;
        chooseImage();
    }

    private void startEdit(Uri uri) {
        ImageEditor.startEdit(this, uri);
    }

    private void startCrop(Uri uri) {
        ImageEditor.startCrop(this, uri);
    }

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (IntentUtil.checkIntent(intent, this)) {
            startActivityForResult(intent, REQUEST_CODE_GALLERY);
        }
    }

    private enum Type {
        EDIT, CROP
    }
}
