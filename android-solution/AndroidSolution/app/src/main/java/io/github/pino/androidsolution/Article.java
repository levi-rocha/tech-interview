package io.github.pino.androidsolution;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static io.github.pino.androidsolution.ArticleListActivity.ARTICLE;

public class Article implements ListItem, Comparable<Article> {

    private List<Bitmap> gallery;
    private String title;
    private String category;
    private String content;
    private boolean favorite;
    private String id;
    private String date;

    public Article(List<Bitmap> gallery, String title, String category, String content, boolean favorite) {
        this.gallery = gallery;
        this.title = title;
        this.category = category;
        this.content = content;
        this.favorite = favorite;
        id = UUID.randomUUID().toString();
        date = DateFormat.getDateTimeInstance().format(new Date());
    }

    /* List Item interface */

    @Override
    public int isListHeaderItem() {
        return 0;
    }

    @Override
    public View getListItemView(final LayoutInflater inflater, View convertView) {
        final Article article = this;
        View view = (View) inflater.inflate(R.layout.article_list_item, null);
        if (gallery.size() > 0) {
            initGallery(inflater, view);
        }
        Button btnOpen = (Button) view.findViewById(R.id.btnArticleListOpen);
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inflater.getContext(), ArticleActivity.class);
                intent.putExtra(ARTICLE, article.getId());
                System.out.println(article.getTitle() + " clicked ");
                Activity parent = (Activity)inflater.getContext();
                parent.startActivity(intent);
            }
        });
        initText(inflater, view);
        return view;
    }

    private void initGallery(LayoutInflater inflater, View view) {
        final LinearLayout mGallery = (LinearLayout) view.findViewById(R.id.small_gallery);
        final HorizontalScrollView mScroller = (HorizontalScrollView) view.findViewById(R.id.small_scroller);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) inflater.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int width = displayMetrics.widthPixels;
        for (Bitmap image : gallery) {
            addImage(inflater, mGallery, image, width);
        }
        if (gallery.size() > 1) {
            // More than one image to display in scroller
            setupScrolling(view, mScroller, width);
        }
    }

    private void addImage(LayoutInflater inflater, LinearLayout mGallery, Bitmap image, int width) {
        ImageView imageView = new ImageView(inflater.getContext());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(width, 720));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageBitmap(image);
        mGallery.addView(imageView);
    }

    private void setupScrolling(View view, final HorizontalScrollView mScroller, final int width) {
        ImageView btnLeft = (ImageView) view.findViewById(R.id.btnArticleListLeft);
        ImageView btnRight = (ImageView) view.findViewById(R.id.btnArticleListRight);
        btnLeft.setVisibility(View.VISIBLE);
        btnRight.setVisibility(View.VISIBLE);
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofInt(mScroller, "scrollX", mScroller.getScrollX() - width);
                animator.setDuration(360);
                animator.start();
            }
        });
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofInt(mScroller, "scrollX", mScroller.getScrollX() + width);
                animator.setDuration(360);
                animator.start();
            }
        });
    }

    private void initText(LayoutInflater inflater, View view) {
        TextView txtTitle = (TextView) view.findViewById(R.id.txtArticleListItemTitle);
        txtTitle.setText(this.title);
        Typeface custom_font = Typeface.createFromAsset(inflater.getContext().getAssets(),  "fonts/Montserrat-SemiBold.otf");
        txtTitle.setTypeface(custom_font);
        TextView txtPreview = (TextView) view.findViewById(R.id.txtArticleListItemPreview);
        if (content.length() > 60) {
            txtPreview.setText(content.substring(0, 60) + "...");
        } else {
            txtPreview.setText(content);
        }
        custom_font = Typeface.createFromAsset(inflater.getContext().getAssets(),  "fonts/Montserrat-Regular.otf");
        txtPreview.setTypeface(custom_font);
    }

    /* Comparable interface */

    @Override
    public int compareTo(Article o) {
        DateFormat format = DateFormat.getDateInstance();
        try {
            Date myDate = format.parse(date);
            Date theirDate = format.parse(o.getDate());
            if (myDate.after(theirDate)) {
                return 1;
            } else if (myDate.before(theirDate)){
                return -1;
            } else {
                return 0;
            }
        } catch (ParseException e) {
            return 0;
        }
    }

    /* Getters and setters */

    public List<Bitmap> getGallery() {
        return gallery;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
