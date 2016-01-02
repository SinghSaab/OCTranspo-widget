package personal.buswidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class MainActivity extends AppWidgetProvider {
    String reachURL = "http://www.google.com";
    RemoteViews remoteViews;
    int widgetId;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;
        for (int i = 0; i < count; i++) {
            widgetId = appWidgetIds[i];
            remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_layout);
            new getTimings(remoteViews).execute(reachURL);
//            remoteViews.setTextViewText(R.id.textView_Char, returnTitle);
//            Update widget Intent
            Intent intent = new Intent(context, MainActivity.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
//            To request a manual update when the update button is clicked, we use a PendingIntent.
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.textView_No, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }

    }


    public class getTimings extends AsyncTask<String, Void, String> {
        public String title = "Default Text";
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
}


