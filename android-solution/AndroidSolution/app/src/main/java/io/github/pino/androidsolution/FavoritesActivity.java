package io.github.pino.androidsolution;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends ArticleListActivity {

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

    @Override
    protected void initActionBar() {
        super.initActionBar();
        // Display favorites title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.favorites_title);
    }

    @Override
    protected void initMenu() {
        super.initMenu();
        // Override drawer menu setup to display favorites title
        mMenu = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mMenu, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(R.string.favorites_title);
            }
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.drawer_title);
            }
        };
        mMenu.addDrawerListener(mDrawerToggle);
    }
}
