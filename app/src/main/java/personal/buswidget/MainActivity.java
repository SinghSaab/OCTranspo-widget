package personal.buswidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class MainActivity extends AppWidgetProvider implements AsyncResponse {
    public static String asyncOutput;
    RemoteViews remoteViews;
    int widgetId;

//    public static int counter =0;
//    Static only makes one copy for counter variable and hence broadcast receiver would be changing one variable only
//    Else, it will keep the value reserving back to zero whenever onUpdate() would be called
//    A BroadcastReceiver object is only valid for the duration of the call to onReceive(Context, Intent).
//    Once your code returns from this function, the system considers the object to be finished and no longer active
//    Create instance of the AsyncTask class

    AsyncClass getResults = new AsyncClass();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        //this to set delegate/listener back to this class
        getResults.delegate = this;
        //execute the async task
        getResults.execute();
//        Update procedure for the widget
        final int count = appWidgetIds.length;
        for (int i = 0; i < count; i++) {
            widgetId = appWidgetIds[i];
            remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            remoteViews.setTextViewText(R.id.textView_Char, asyncOutput);

            Intent intent = new Intent(context, MainActivity.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.textView_No, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    public void processFinish(String output) {
        //Here you will receive the result fired from AsyncClass of onPostExecute(result) method.
        asyncOutput = output;
    }
}


