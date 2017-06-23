# ImageEditor

* Support scrawl, emoji, Mongolian text, cutting, filter, mosaic, and so on.

# Usage    [go to demo](https://github.com/Saqrag/ImageEditor)

* start Editor or Cropper, Uri is starting with "Content://" and gotten from ContentProvider. and url is gotten from uri.

    ```java
        private void startEdit(Uri uri) {
            if (vRadioUri.isChecked()) {
                ImageEditor.startEdit(this, uri);
            } else if (vRadioUrl.isChecked()) {
                ImageEditor.startEdit(this, getPath(uri));
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
    ```

* result return in `onActivityForResult()` method of `Activity`

    ```java
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
    ```

* there is an `Activity` needed to produce Mongolian Text while edit image

    * AndroidManifest.xml

        ```xml
            <activity
                android:name=".TextEditActivity"
                android:exported="true">
                <intent-filter>
                    <action android:name="com.zuga.image.edit.text.editor" />
                    <category android:name="android.intent.category.DEFAULT" />
                </intent-filter>
            </activity>
        ```

    * on TextEditActivity set result

        ```java
            Intent intent = new Intent();
            intent.putExtra(ImageEditor.EXTRA_TEXT_EDITOR_CONTENT, content);
            intent.putExtra(ImageEditor.EXTRA_TEXT_EDITOR_CONTENT_COLOR, Color.RED);
            setResult(RESULT_OK, intent);
            finish();
        ```

* match theme

    ```xml
        <!--bottom clicked or selected-->
        <color name="ie_primary_light">#3a8cf1</color>
        <!--ToolBar-->
        <color name="colorPrimary">#252526</color>
    ```