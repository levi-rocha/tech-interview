package io.github.pino.androidsolution;

import android.content.Intent;
import android.os.Bundle;

public class HomeActivity extends ArticleListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JoyjetApplication appState = (JoyjetApplication) getApplication();
        if (!appState.isLoaded()) {
            ArticleLoader loader = new ArticleLoader(this);
            String url = "https://cdn.joyjet.com/tech-interview/mobile-test-one.json";
            loader.execute(url);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void refresh() {
        updateArticles();
    }
}
