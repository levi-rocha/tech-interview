package io.github.pino.androidsolution;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public abstract class ArticleListActivity extends AppCompatActivity {

    public final static String ARTICLE = "io.github.pino.watchthisandroid.ARTICLE";

    protected DrawerLayout mMenu;
    protected ListView mMenuList;
    protected String[] mMenuListItems;
    protected ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        initActionBar();
        initMenu();
        updateArticles();
    }

    protected void updateArticles() {
        JoyjetApplication appState = ((JoyjetApplication) this.getApplication());
        List<ListItem> items = new ArrayList<ListItem>();
        List<Article> articles = appState.getArticles();
        Category[] categories = Category.values();
        for (Category category : categories) {
            items.add(new Header(category.toString()));
            for (Article article : articles) {
                if (article.getCategoryId() == category.ordinal()) {
                    items.add(article);
                }
            }
        }
        ArticleArrayAdapter adapter = new ArticleArrayAdapter(this, items);
        ListView list = (ListView) findViewById(R.id.articleList);
        list.setAdapter(adapter);
    }

    /* Action bar and menu setup */

    protected void initActionBar() {
        getSupportActionBar().setTitle(R.string.home_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    protected void initMenu() {
        mMenuListItems = getResources().getStringArray(R.array.drawer_array);
        mMenu = (DrawerLayout) findViewById(R.id.drawer_layout);
        mMenuList = (ListView) findViewById(R.id.left_drawer_list);

        // Set up menu list
        mMenuList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mMenuListItems));
        mMenuList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mMenu,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(R.string.home_title);
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

    protected class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectMenuItem(position);
        }
    }

    protected void selectMenuItem(int position) {
        Intent intent;
        switch (position) {
            case 0:
                if (!this.getClass().equals(HomeActivity.class)) {
                    mMenu.closeDrawer(Gravity.LEFT);
                    intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                    //finish();
                } else {
                    mMenu.closeDrawer(Gravity.LEFT);
                }
                break;
            case 1:
                if (!this.getClass().equals(FavoritesActivity.class)) {
                    mMenu.closeDrawer(Gravity.LEFT);
                    intent = new Intent(this, FavoritesActivity.class);
                    startActivity(intent);
                    //finish();
                } else {
                    mMenu.closeDrawer(Gravity.LEFT);
                }
                break;
            default:
                // pass
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Check for drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateArticles();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateArticles();
    }
}
