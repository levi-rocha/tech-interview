package io.github.pino.androidsolution;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public final static String ARTICLE = "io.github.pino.watchthisandroid.ARTICLE";

    private DrawerLayout mMenu;
    private ListView mMenuList;
    private String[] mMenuListItems;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActionBar();
        initMenu();
        initDefaultArticles();
        updateArticles();
    }

    private void initDefaultArticles() {
        JoyjetApplication appState = ((JoyjetApplication) this.getApplication());
        appState.resetArticles();
        String title1 = "Article 1";
        int cat1 = Category.MYLIFE.ordinal();
        String content1 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam imperdiet ac diam vitae viverra. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin iaculis, justo ac pulvinar egestas, eros metus malesuada ante, nec faucibus arcu ante ut nibh. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vivamus tristique mauris ac eros laoreet pretium vitae nec nisl. Phasellus in erat euismod, posuere risus eu, pretium felis. Cras vulputate euismod lobortis. Ut et iaculis libero. Donec at magna congue, feugiat justo tempor, posuere turpis. Donec rutrum, leo vitae vulputate suscipit, libero mauris lacinia justo, id commodo risus nisl non orci.\n" +
                "\n" +
                "Donec ut commodo arcu, vel accumsan leo. Vestibulum dictum mauris in eleifend eleifend. Ut et molestie mauris. Sed finibus risus ut purus efficitur semper. Fusce diam leo, congue vel blandit nec, facilisis sed elit. Suspendisse potenti. Morbi malesuada efficitur laoreet. Morbi pharetra id orci id imperdiet.\n" +
                "\n" +
                "Maecenas id arcu risus. Ut id massa suscipit, luctus ante imperdiet, vestibulum neque. Nunc auctor eros in mauris laoreet, sit amet sodales mauris hendrerit. Phasellus vestibulum lobortis nisi porttitor tempus. Vestibulum iaculis pretium mi a hendrerit. Curabitur a quam placerat, laoreet felis in, congue ligula. Suspendisse semper molestie nisi, in bibendum ligula auctor imperdiet. Morbi dapibus lacinia justo sit amet finibus. Nam laoreet nunc leo, nec posuere libero vulputate id. Nulla scelerisque euismod tellus, non commodo nisl vestibulum sed. Praesent vehicula suscipit libero, vitae malesuada nulla tincidunt semper.";
        int[] irids1 = {R.drawable.pino2};
        Article article1 = new Article(irids1, title1, cat1, content1, false);
        appState.addToArticles(article1);
        int[] irids2 = {R.drawable.pino1, R.drawable.pino2};
        String title2 = "Article 2";
        int cat2 = Category.PLACES.ordinal();
        Article article2 = new Article(irids2, title2, cat2, content1, false);
        appState.addToArticles(article2);
        int[] irids3 = {};
        int cat3 = cat1;
        String title3 = "Article 3";
        Article article3 = new Article(irids3, title3, cat3, content1, false);
        appState.addToArticles(article3);
    }

    private void updateArticles() {
        JoyjetApplication appState = ((JoyjetApplication) this.getApplication());
        List<ListItem> items = new ArrayList<ListItem>();
        List<Article> articles = appState.getArticles();
        for (Article article : articles) {
            System.out.println(article.getTitle());
        }
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

    private void initActionBar() {
        getSupportActionBar().setTitle(R.string.home_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void initMenu() {
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

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectMenuItem(position);
        }
    }

    private void selectMenuItem(int position) {
        if (position == 1) {
            // TODO: change to Favorites
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
