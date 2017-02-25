package io.github.pino.androidsolution;

import android.animation.ObjectAnimator;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.View.GONE;
import static io.github.pino.androidsolution.ArticleListActivity.ARTICLE;

public class ArticleActivity extends AppCompatActivity {

    private Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        article = this.getIntent().getParcelableExtra(ARTICLE);
        final HorizontalScrollView mScroller = (HorizontalScrollView) findViewById(R.id.scroller);
        int[] imageResourceIds = article.getImageResourceIds();
        final ImageView mFavorite = (ImageView) findViewById(R.id.favorite);
        if (imageResourceIds != null && imageResourceIds.length > 0) {
            final LinearLayout mGallery = (LinearLayout) findViewById(R.id.gallery);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            final int width = displayMetrics.widthPixels;
            for (int resource : article.getImageResourceIds()) {
                addImage(mGallery, resource, width);
            }
            mFavorite.getDrawable().setColorFilter(0xcccccc00, PorterDuff.Mode.MULTIPLY);
            if (imageResourceIds.length > 1) {
                ImageView btnLeft = (ImageView) findViewById(R.id.btnArticleLeft);
                ImageView btnRight = (ImageView) findViewById(R.id.btnArticleRight);
                btnLeft.setVisibility(View.VISIBLE);
                btnRight.setVisibility(View.VISIBLE);
                btnLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ObjectAnimator animator = ObjectAnimator.ofInt(mScroller, "scrollX", mScroller.getScrollX()-width);
                        animator.setDuration(360);
                        animator.start();
                    }
                });
                btnRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ObjectAnimator animator = ObjectAnimator.ofInt(mScroller, "scrollX", mScroller.getScrollX()+width);
                        animator.setDuration(360);
                        animator.start();
                    }
                });
            }
        } else {
            mScroller.setVisibility(GONE);
        }
        if (article.isFavorite()) {
            mFavorite.setVisibility(GONE);
        } else {
            mFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    article.setFavorite(true);
                    JoyjetApplication appState = ((JoyjetApplication) getApplication());
                    appState.replaceArticleById(article.getId(), article);
                    mFavorite.setVisibility(GONE);
                    Toast.makeText(ArticleActivity.this, R.string.favorited, Toast.LENGTH_LONG).show();
                }
            });
        }
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
