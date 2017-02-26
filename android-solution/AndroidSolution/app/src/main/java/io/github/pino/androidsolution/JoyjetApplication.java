package io.github.pino.androidsolution;

import android.app.Application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by nadaaver on 2017-02-24.
 */

public class JoyjetApplication extends Application {

    private boolean loaded = false;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private HashMap<String, Article> articles = new HashMap<String, Article>();

    public void resetArticles() {
        articles = new HashMap<String, Article>();
    }

    public List<Article> getArticles() {
        List<Article> data = new ArrayList<Article>();
        for (Article article : articles.values()) {
            data.add(article);
        }
        Collections.sort(data);
        return data;
    }

    public Article getArticleById(String id) {
        return articles.get(id);
    }

    public void addToArticles(Article article) {
        articles.put(article.getId(), article);
    }

    public void replaceArticleById(String id, Article replacement) {
        if (articles.containsKey(id)) {
            articles.remove(id);
            articles.put(id, replacement);
        }
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public boolean isLoaded() {
        return loaded;
    }

}
