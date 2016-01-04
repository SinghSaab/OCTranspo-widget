package personal.buswidget;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Administrator on 2016-01-02.
 */

public class AsyncClass extends AsyncTask<Void, Void, String> {
    public AsyncResponse delegate = null;
    String appID = "*** Enter your appID from OCTranspo account ***";
    String appKey = "*** Enter your appKey from OCTranspo account ***";
    String stopNo = "6410";
    String routeNo = "85";

    @Override
    protected String doInBackground(Void... params) {
        String routeSummary = "https://api.octranspo1.com/v1.2/GetRouteSummaryForStop";
        String routeSummaryParam = "appID=" + appID + "&apiKey=" + appKey + "&stopNo=" + stopNo;
        String nextTrip = "https://api.octranspo1.com/v1.2/GetNextTripsForStop";
        String nextTripParam = "appID=" + appID + "&apiKey=" + appKey + "routeNo" + routeNo + "&stopNo=" + stopNo;

        try {
            Log.d("", "Crafting URL...");
            URL url = new URL(routeSummary);
            Log.d("Crafting URL", "Done");
            Log.d("", "Opening HttpsConnection String...");
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            DataOutputStream dStream = new DataOutputStream(urlConnection.getOutputStream());
            dStream.writeBytes(routeSummaryParam);
            dStream.flush();
            dStream.close();
            int responseCode = urlConnection.getResponseCode();
            final StringBuilder output = new StringBuilder("Request URL " + url);
            output.append(System.getProperty("line.separator") + "Request Parameters " + routeSummaryParam);
            output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
            Log.d("Opening HttpsConnection", "Done");
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                Log.d("OUPUT:::::", stringBuilder.toString());
                return "OUTPUT";
            } catch (Exception e) {
                e.printStackTrace();
                return "Exception Occured while StringBuilder!";
            } finally {
                Log.d("", "Closing HttpsConnection...");
                urlConnection.disconnect();
                Log.d("Closing HttpsConnection", "Done");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Exception Occured while HttpsURLConnection!";
        }
    }

    @Override
    protected void onPostExecute(String string) {
        delegate.processFinish(string);
    }
}
