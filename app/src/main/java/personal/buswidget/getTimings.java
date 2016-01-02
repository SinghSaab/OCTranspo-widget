package personal.buswidget;

import android.os.AsyncTask;
import android.widget.RemoteViews;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by Administrator on 2016-01-01.
 */
public class getTimings extends AsyncTask<String, Void, String> {
    String title;
    private RemoteViews remoteView;
    public getTimings(RemoteViews remoteViews) {
        this.remoteView = remoteViews;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            Document document = Jsoup.connect(params[0]).get();
            title = "Document.title()";
        } catch (Exception e) {
            title = "Error Fetching";
            e.printStackTrace();
        }
        return title;
    }

    @Override
    protected void onPostExecute(String string) {
        remoteView.setTextViewText(R.id.textView_Char, string);
    }
}