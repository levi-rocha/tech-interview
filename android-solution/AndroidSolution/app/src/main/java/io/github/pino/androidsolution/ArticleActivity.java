package io.github.pino.androidsolution;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        Article article = this.getIntent().getParcelableExtra(MainActivity.ARTICLE);

        final LinearLayout mGallery = (LinearLayout) findViewById(R.id.gallery);
        final HorizontalScrollView mScroller = (HorizontalScrollView) findViewById(R.id.scroller);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int width = displayMetrics.widthPixels;
        for (int resource : article.getImageResourceIds()) {
            addImage(mGallery, resource, width);
        }
        ImageView btnLeft = (ImageView) findViewById(R.id.btnArticleLeft);
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofInt(mScroller, "scrollX", mScroller.getScrollX()-width);
                animator.setDuration(360);
                animator.start();
            }
        });
        ImageView btnRight = (ImageView) findViewById(R.id.btnArticleRight);
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofInt(mScroller, "scrollX", mScroller.getScrollX()+width);
                animator.setDuration(360);
                animator.start();
            }
        });

        TextView txtTitle = (TextView) findViewById(R.id.txtArticleItemTitle);
        txtTitle.setText(article.getTitle());
        TextView txtContent = (TextView) findViewById(R.id.txtArticleContent);
        txtContent.setText(article.getContent());

    }

    private void addImage(LinearLayout mGallery, int drawable, int width) {
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(width, 720));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), drawable, null));
        mGallery.addView(imageView);
    }
}
