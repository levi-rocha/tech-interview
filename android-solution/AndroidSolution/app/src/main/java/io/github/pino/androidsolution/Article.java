package io.github.pino.androidsolution;

import android.graphics.drawable.Drawable;

/**
 * Created by nadaaver on 2017-02-23.
 */

public class Article {

    private Drawable image;
    private String title;
    private Category category;
    private StringBuffer content;

    public Article(Drawable image, String title, Category category, StringBuffer content) {
        this.image = image;
        this.title = title;
        this.category = category;
        this.content = content;
    }
}
