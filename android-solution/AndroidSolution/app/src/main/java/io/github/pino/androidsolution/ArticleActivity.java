package io.github.pino.androidsolution;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        Article article = this.getIntent().getParcelableExtra(MainActivity.ARTICLE);
        TextView txtTitle = (TextView) findViewById(R.id.txtArticleItemTitle);
        txtTitle.setText(article.getTitle());
    }
}
