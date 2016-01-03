package personal.buswidget;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by Administrator on 2016-01-02.
 */
public class AsyncClass extends AsyncTask<String, Void, String> {
    public AsyncResponse delegate = null;
    String title;

    @Override
    protected String doInBackground(String... params) {
        try {
            Document document = Jsoup.connect(params[0]).get();
            title = document.title();
        } catch (Exception e) {
            title = "Error Fetching";
            e.printStackTrace();
        }
        return title;
    }

    @Override
    protected void onPostExecute(String string) {
        delegate.processFinish(string);
    }
}
