package io.github.pino.androidsolution;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nadaaver on 2017-02-25.
 */

public class ArticleLoader extends AsyncTask<String, String, List<Article>> {

    private Activity mParent;
    private ProgressDialog mProgressDialog;

    public ArticleLoader(Activity parent) {
        mParent = parent;
        mProgressDialog = new ProgressDialog(mParent);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog.setTitle("Loading articles");
        mProgressDialog.setMessage("Fetching articles from Joyjet...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    @Override
    protected List<Article> doInBackground(String[] params) {
        String inputUrl = params[0];
        List<Article> articles = new ArrayList<Article>();
        try {
            InputStream inputStream = new URL(inputUrl).openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer buffer = new StringBuffer();
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            inputStream.close();
            int done = 0;
            JSONArray jArray = new JSONArray(buffer.toString());
            for(int i = 0; i < jArray.length(); i++) {
                JSONObject fullObject = jArray.getJSONObject(i);
                String category = fullObject.getString("category");
                JSONArray itemsArray = fullObject.getJSONArray("items");
                for (int j = 0; j < itemsArray.length(); j++) {
                    JSONObject itemObject = itemsArray.getJSONObject(j);
                    String title = itemObject.getString("title");
                    String content = itemObject.getString("description");
                    List<Bitmap> gallery = new ArrayList<Bitmap>();
                    JSONArray urlArray = itemObject.getJSONArray("galery");
                    for (int k = 0; k < urlArray.length(); k++) {
                        String url = urlArray.getString(k);
                        InputStream in = new URL(url).openStream();
                        Bitmap bmp = BitmapFactory.decodeStream(in);
                        gallery.add(bmp);
                    }
                    Article article = new Article(gallery, title, category, content, false);
                    articles.add(article);
                    done++;
                    publishProgress("Loading article " + done);
                }
            }
            return articles;
        } catch (IOException e) {
            System.out.println("Error retrieving JSON");
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... progress) {
        if (mProgressDialog != null) {
            mProgressDialog.setMessage(progress[0]);
        }
    }

    @Override
    protected void onPostExecute(List<Article> articles) {
        JoyjetApplication appState = (JoyjetApplication) mParent.getApplication();
        for (Article article : articles) {
            appState.addToArticles(article);
        }
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        appState.setLoaded(true);
        ((HomeActivity) mParent).refresh();
    }

}
