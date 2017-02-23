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

        List<ListItem> items = new ArrayList<ListItem>();
        items.add(new Header("Category 1"));
        int[] irids1 = {R.drawable.pino1};
        String title1 = "Article 1";
        int cat1 = Category.MYLIFE.ordinal();
        String content1 = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA \n" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA \n\n" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
        items.add(new Article(irids1, title1, cat1, content1));
        int[] irids2 = {R.drawable.pino1, R.drawable.pino2};
        String title2 = "Article 2";
        int cat2 = Category.MYLIFE.ordinal();
        String content2 = "BBBB BBBBBBBBB BBBBBBBBB BBBB BBBBBBB \n" +
                "BBBBBB BBBBBBBBB BBBBBB BBBB BBBBBBBB \n\n" +
                "BBBBB BBBBBBBBB BBBB BBB BBBBBBBBBBBB \n\n" +
                "BBBBBBBB BBBBBBB BBB BBBBBBBBBBBBBBB \n\n" +
                "BBBBBBBBBBBBB BBBBBBBBBBBBBBBBBBBB \n\n" +
                "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB \n\n" +
                "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB \n\n" +
                "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB \n\n" +
                "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB \n\n" +
                "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB \n\n" +
                "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB \n\n" +
                "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB \n\n" +
                "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB \n\n" +
                "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB \n\n" +
                "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB \n\n" +
                "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB \n\n" +
                "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB \n\n" +
                "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
        items.add(new Article(irids2, title2, cat2, content2));
        items.add(new Header("Category 2"));
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
        // TODO: change activities

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


}
