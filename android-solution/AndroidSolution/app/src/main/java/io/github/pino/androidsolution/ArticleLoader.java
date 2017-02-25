package io.github.pino.androidsolution;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

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
        //TODO get articles
        return null;
    }

    @Override
    protected void onPostExecute(List<Article> articles) {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        //TODO handle results
    }

}
