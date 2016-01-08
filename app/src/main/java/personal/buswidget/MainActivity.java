package personal.buswidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

public class MainActivity extends AppWidgetProvider implements AsyncResponse {
    public static String asyncOutput = "";
    private static final String settingClicked = "SettingsButton";
    AppWidgetManager appWidgetManager;
    RemoteViews remoteViews;
    int widgetId;
//    public static int counter =0;
//    Static only makes one copy for counter variable and hence broadcast receiver would be changing one variable only
//    Else, it will keep the value reserving back to zero whenever onUpdate() would be called
//    A BroadcastReceiver object is only valid for the duration of the call to onReceive(Context, Intent).
//    Once your code returns from this function, the system considers the object to be finished and no longer active
//    Create instance of the AsyncTask class

    AsyncClass getResults = new AsyncClass();

    //As soon as the widget is place on screen, this method will run
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        //this to set delegate/listener back to this class
        getResults.delegate = this;
        //execute the async task
        getResults.execute();

        final int count = appWidgetIds.length;
        for (int i = 0; i < count; i++) {
            widgetId = appWidgetIds[i];
            remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            if (!asyncOutput.isEmpty()) {
                Log.d("OUTPUT", asyncOutput);
                String[] populateTextView = asyncOutput.split("@");
                remoteViews.setTextViewText(R.id.busNo, populateTextView[2]);
                remoteViews.setTextViewText(R.id.busDest, populateTextView[3]);
                remoteViews.setTextViewText(R.id.stopNo, populateTextView[0]);
                remoteViews.setTextViewText(R.id.stopName, populateTextView[1]);
                remoteViews.setTextViewText(R.id.speed, populateTextView[4] + "Km/h");
                remoteViews.setTextViewText(R.id.etaTime, populateTextView[5] + "  ETA");
            }

//            Intent for updating widget whenever changes occurs
            Intent updateIntent = new Intent(context, MainActivity.class);
            updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            PendingIntent pendingUpdateIntent = PendingIntent.getBroadcast(context, 0, updateIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.refreshButton, pendingUpdateIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);

//            Intent for updating widget whenever changes occurs
            Intent configIntent = new Intent(context, widgetConfig.class);
            configIntent.setAction("SettingsButton");
            PendingIntent pendingConfigIntent = PendingIntent.getActivity(context, 0,
                    configIntent, 0);
            ;
            remoteViews.setOnClickPendingIntent(R.id.settingButton, pendingConfigIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

//    onReceive the broadcast to configure the widget. There's an entry in Manifest file as well.
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (settingClicked.equals(intent.getAction())) {
            PendingIntent pendingConfigIntent = PendingIntent.getActivity(context, 0,
                    intent, 0);
            try {
                pendingConfigIntent.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
                Log.d("pendingIntent", "Config Activity Canceled");
            }
        }
    }

    //    Here you will receive the result fired from AsyncClass of onPostExecute(result) method.
    public void processFinish(String output) {
        asyncOutput = output;
    }
}



