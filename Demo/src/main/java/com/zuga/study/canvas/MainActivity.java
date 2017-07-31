package com.zuga.study.canvas;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.zuga.imageedit.ImageEditor;
import com.zuga.imageedit.activites.PermissionActivity;
import com.zuga.imageedit.utils.BitmapUtils;
import com.zuga.imageedit.utils.IntentUtil;
import com.zuga.imageedit.utils.UiUtil;
import com.zuga.keyboard.helpers.KeyboardManager;

public class MainActivity extends PermissionActivity {
    private final int REQUEST_CODE_GALLERY = 8747;
    private Type mType;
    private ImageView vImageView;
    private RadioButton vRadioUri;
    private RadioButton vRadioUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vImageView = (ImageView) findViewById(R.id.iv_after);
        vRadioUri = (RadioButton) findViewById(R.id.rb_uri);
        vRadioUrl = (RadioButton) findViewById(R.id.rb_url);
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
            vImageView.setImageBitmap(BitmapUtils.resizeImg(
                    resultPath,
                    UiUtil.getScreenHeight(this),
                    UiUtil.getScreenHeight(this)).bitmap
            );
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
        if (vRadioUri.isChecked()) {
            ImageEditor.startEdit(this, uri, R.mipmap.back, R.mipmap.done);
        } else if (vRadioUrl.isChecked()) {
            ImageEditor.startEdit(this, getPath(uri), R.mipmap.back, R.mipmap.done);
        }
    }

    private void startCrop(Uri uri) {
        if (vRadioUri.isChecked()) {
            ImageEditor.startCrop(this, uri);
        } else if (vRadioUrl.isChecked()) {
            ImageEditor.startCrop(this, getPath(uri));
        }
    }

    private String getPath(Uri uri) {
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};

        Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
        if (cursor == null) return null;
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
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
