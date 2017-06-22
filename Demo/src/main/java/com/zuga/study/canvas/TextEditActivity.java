package com.zuga.study.canvas;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.zuga.advancedtextview.VerticalEditText;
import com.zuga.imageedit.ImageEditor;
import com.zuga.imageedit.activites.PermissionActivity;
import com.zuga.keyboard.CandidateView;
import com.zuga.keyboard.ZugaKeyboardView;

/**
 * @author saqrag
 * @version 1.0
 * @see null
 * 08/06/2017
 * @since 1.0
 **/

public class TextEditActivity extends PermissionActivity implements View.OnClickListener {
    private VerticalEditText vTextEditor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keyboard_layout);
        initView();
    }

    private void initView() {
        ZugaKeyboardView mKeyboardView = (ZugaKeyboardView) findViewById(R.id.zuga_keyboard_view);
        CandidateView mCandidateView = (CandidateView) findViewById(R.id.candidate_view);
        FrameLayout mContainer = (FrameLayout) findViewById(R.id.edit_area);
        View editor = LayoutInflater.from(this).inflate(R.layout.activity_text_editor, mContainer, true);
        vTextEditor = (VerticalEditText) editor.findViewById(R.id.ve_text_editor);
        Button vCommit = (Button) editor.findViewById(R.id.bn_commit);
        vCommit.setOnClickListener(this);
        mKeyboardView.register(vTextEditor, mCandidateView);
        Intent intent = getIntent();
        if (intent == null) return;
        if (!intent.hasExtra(ImageEditor.EXTRA_TEXT_EDITOR_CONTENT_DEFAULT)) return;
        if (!intent.hasExtra(ImageEditor.EXTRA_TEXT_EDITOR_CONTENT_DEFAULT_COLOR)) return;
        vTextEditor.setTextColor(intent.getIntExtra(
                ImageEditor.EXTRA_TEXT_EDITOR_CONTENT_DEFAULT_COLOR, Color.BLACK));
        vTextEditor.setText(intent.getStringExtra(
                ImageEditor.EXTRA_TEXT_EDITOR_CONTENT_DEFAULT));
    }

    @Override
    public void onClick(View v) {
        CharSequence text = vTextEditor.getText();
        if (TextUtils.isEmpty(text)) {
            finish();
            return;
        }
        String content = String.valueOf(text);
        Intent intent = new Intent();
        intent.putExtra(ImageEditor.EXTRA_TEXT_EDITOR_CONTENT, content);
        intent.putExtra(ImageEditor.EXTRA_TEXT_EDITOR_CONTENT_COLOR, Color.RED);
        setResult(RESULT_OK, intent);
        finish();
    }
}
