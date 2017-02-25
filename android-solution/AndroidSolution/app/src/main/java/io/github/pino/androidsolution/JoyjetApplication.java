package io.github.pino.androidsolution;

import android.app.Application;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by nadaaver on 2017-02-24.
 */

public class JoyjetApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initDefaultArticles();
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

    public void addToArticles(Article article) {
        articles.put(article.getId(), article);
    }

    public void replaceArticleById(String id, Article replacement) {
        if (articles.containsKey(id)) {
            articles.remove(id);
            articles.put(id, replacement);
        }
    }

    private void initDefaultArticles() {
        resetArticles();
        String title1 = "Article 1";
        int cat1 = Category.MYLIFE.ordinal();
        String content1 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam imperdiet ac diam vitae viverra. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin iaculis, justo ac pulvinar egestas, eros metus malesuada ante, nec faucibus arcu ante ut nibh. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vivamus tristique mauris ac eros laoreet pretium vitae nec nisl. Phasellus in erat euismod, posuere risus eu, pretium felis. Cras vulputate euismod lobortis. Ut et iaculis libero. Donec at magna congue, feugiat justo tempor, posuere turpis. Donec rutrum, leo vitae vulputate suscipit, libero mauris lacinia justo, id commodo risus nisl non orci.\n" +
                "\n" +
                "Donec ut commodo arcu, vel accumsan leo. Vestibulum dictum mauris in eleifend eleifend. Ut et molestie mauris. Sed finibus risus ut purus efficitur semper. Fusce diam leo, congue vel blandit nec, facilisis sed elit. Suspendisse potenti. Morbi malesuada efficitur laoreet. Morbi pharetra id orci id imperdiet.\n" +
                "\n" +
                "Maecenas id arcu risus. Ut id massa suscipit, luctus ante imperdiet, vestibulum neque. Nunc auctor eros in mauris laoreet, sit amet sodales mauris hendrerit. Phasellus vestibulum lobortis nisi porttitor tempus. Vestibulum iaculis pretium mi a hendrerit. Curabitur a quam placerat, laoreet felis in, congue ligula. Suspendisse semper molestie nisi, in bibendum ligula auctor imperdiet. Morbi dapibus lacinia justo sit amet finibus. Nam laoreet nunc leo, nec posuere libero vulputate id. Nulla scelerisque euismod tellus, non commodo nisl vestibulum sed. Praesent vehicula suscipit libero, vitae malesuada nulla tincidunt semper.";
        int[] irids1 = {R.drawable.pino2};
        String date1 = DateFormat.getDateTimeInstance().format(new Date(1486041300000L));
        Article article1 = new Article(irids1, title1, cat1, content1, false, date1);
        System.out.println(date1);
        addToArticles(article1);
        int[] irids2 = {R.drawable.pino1, R.drawable.pino2};
        String title2 = "Article 2";
        int cat2 = Category.PLACES.ordinal();
        String date2 = DateFormat.getDateTimeInstance().format(new Date(1487041350000L));
        System.out.println(date2);
        Article article2 = new Article(irids2, title2, cat2, content1, false, date2);
        addToArticles(article2);
        int[] irids3 = {};
        int cat3 = cat1;
        String title3 = "Article 3";
        String date3 = DateFormat.getDateTimeInstance().format(new Date(1488041400000L));
        System.out.println(date3);
        Article article3 = new Article(irids3, title3, cat3, content1, false, date3);
        addToArticles(article3);
    }

}
