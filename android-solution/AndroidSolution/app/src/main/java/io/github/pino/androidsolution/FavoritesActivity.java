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
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.favorites_title);
    }

    @Override
    protected void initMenu() {
        super.initMenu();
        mMenu = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mMenu,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(R.string.favorites_title);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.drawer_title);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mMenu.addDrawerListener(mDrawerToggle);
    }
}
