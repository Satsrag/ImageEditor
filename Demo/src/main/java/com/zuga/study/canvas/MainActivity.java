package com.zuga.study.canvas;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.zuga.imageedit.activites.ImageEditActivity;
import com.zuga.imageedit.activites.PermissionActivity;
import com.zuga.imageedit.utils.IntentUtil;
import com.zuga.keyboard.helpers.KeyboardManager;
import com.zuga.log.LogSa;

public class MainActivity extends PermissionActivity {

    private final int REQUEST_CODE_EDIT = 8837;
    private final int REQUEST_CODE_GALLERY = 8747;
    private View vView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        KeyboardManager.init(this);
        vView = findViewById(R.id.tv_test);
        LogSa.d("onCreate");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogSa.d("onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogSa.d("onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogSa.d("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogSa.d("onDestroy");
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        LogSa.d("onPause");
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */
    @Override
    protected void onResume() {
        super.onResume();
        LogSa.d("onResume");
    }

    public void invisible(View v) {
        vView.setVisibility(View.INVISIBLE);
    }

    public void visible(View v) {
        vView.setVisibility(View.VISIBLE);
    }

    public void gone(View v) {
        vView.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_EDIT) {
            Toast.makeText(this, "onActivityResult", Toast.LENGTH_SHORT).show();
        } else if (requestCode == REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            if (c == null) return;
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            final String imagePath = c.getString(columnIndex);
            c.close();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                askPermission(0, new PermissionCallback() {
                    @Override
                    public void isAllAccess(boolean isAllAccess) {
                        Intent intent = new Intent(MainActivity.this, ImageEditActivity.class);
                        intent.putExtra(ImageEditActivity.EXTRA_IMG_PATH, imagePath);
                        startActivityForResult(intent, REQUEST_CODE_EDIT);
                    }
                }, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE});
            } else {
                Intent intent = new Intent(this, ImageEditActivity.class);
                intent.putExtra(ImageEditActivity.EXTRA_IMG_PATH, imagePath);
                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        }
    }

    public void chooseImage(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (IntentUtil.checkIntent(intent, this)) {
            startActivityForResult(intent, REQUEST_CODE_GALLERY);
        }
    }
}
