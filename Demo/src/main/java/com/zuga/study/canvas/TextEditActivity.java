package com.zuga.study.canvas;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.zuga.advancedtextview.VerticalEditText;
import com.zuga.imageedit.ImageEditor;
import com.zuga.imageedit.activites.PermissionActivity;
import com.zuga.imageedit.adapter.ScrawlAdapter;
import com.zuga.keyboard.CandidateView;
import com.zuga.keyboard.ZugaKeyboardView;

/**
 * @author saqrag
 * @version 1.0
 * @see null
 * 08/06/2017
 * @since 1.0
 **/

public class TextEditActivity extends PermissionActivity implements View.OnClickListener, ScrawlAdapter.ColorChangeListener {
    private VerticalEditText vTextEditor;
    private int mColor = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.keyboard_layout);
        initView();
    }

    private void initView() {
        ZugaKeyboardView mKeyboardView = (ZugaKeyboardView) findViewById(R.id.zuga_keyboard_view);
        CandidateView mCandidateView = (CandidateView) findViewById(R.id.candidate_view);
        FrameLayout mContainer = (FrameLayout) findViewById(R.id.edit_area);
        View editor = LayoutInflater.from(this).inflate(R.layout.activity_text_editor, mContainer, true);
        vTextEditor = (VerticalEditText) editor.findViewById(R.id.ve_text_editor);
        RecyclerView vColor = (RecyclerView) editor.findViewById(R.id.rv_color);
        vColor.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this
                , LinearLayoutManager.HORIZONTAL, false);
        vColor.setLayoutManager(linearLayoutManager);
        ScrawlAdapter scrawlAdapter = new ScrawlAdapter(null, this);
        vColor.setAdapter(scrawlAdapter);
        scrawlAdapter.notifyDataSetChanged();
        ImageView tlDown = (ImageView) editor.findViewById(R.id.tb_done);
        tlDown.setOnClickListener(this);
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
        intent.putExtra(ImageEditor.EXTRA_TEXT_EDITOR_CONTENT_COLOR, mColor);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onColorChange(int color) {
        mColor = color;
        vTextEditor.setTextColor(color);
    }
}
