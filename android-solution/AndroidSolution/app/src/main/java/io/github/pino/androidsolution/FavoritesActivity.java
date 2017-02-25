package io.github.pino.androidsolution;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends ArticleListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateArticles();
    }

    @Override
    protected void updateArticles() {
        JoyjetApplication appState = ((JoyjetApplication) this.getApplication());
        List<ListItem> items = new ArrayList<ListItem>();
        List<Article> articles = appState.getArticles();
        for (Article article : articles) {
            if (article.isFavorite()) {
                    items.add(article);
            }
        }
        ArticleArrayAdapter adapter = new ArticleArrayAdapter(this, items);
        ListView list = (ListView) findViewById(R.id.articleList);
        list.setAdapter(adapter);
    }
}
