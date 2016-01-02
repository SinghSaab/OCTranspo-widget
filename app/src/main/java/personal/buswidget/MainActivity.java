package personal.buswidget;

import android.app.Application;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class MainActivity extends AppWidgetProvider {
    String title;
    String reachURL = "http://www.ocbustracker.com/BusData.aspx?stopno=6441&busnum0=85&direction0=Eastbound&agency=OC";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        Log.d("OnUpdate", "Before ASyncExecute");

//        new getTimings().execute(reachURL);

        Log.d("OnUpdate", "After ASyncExecute");


        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];
            Log.d("", "Getting Remote View");
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_layout);
            remoteViews.setTextViewText(R.id.textView_Char, "hello");

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
        @Override
        protected String doInBackground(String... params) {
            try {
                Document document = Jsoup.connect(params[0]).get();
                title = document.title();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return title;
        }
        @Override
        protected void onPostExecute(String string) {
            super.onPostExecute(title);

        }
    }
}


