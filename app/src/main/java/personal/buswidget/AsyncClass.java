package personal.buswidget;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Administrator on 2016-01-02.
 */

public class AsyncClass extends AsyncTask<Void, Void, String> {
    public AsyncResponse delegate = null;
    String OCTranspoAppID = "*** Enter your appID from OCTranspo account ***";
    String OCTranspoAppKey = "*** Enter your appKey from OCTranspo account ***";
    String stopNo = "6441";
    String routeNo = "85";

    @Override
    protected String doInBackground(Void... params) {
        String routeSummary = "https://api.octranspo1.com/v1.2/GetRouteSummaryForStop";
        String routeSummaryParam = "appID=" + appID + "&apiKey=" + appKey + "&stopNo=" + stopNo;
        String nextTrip = "https://api.octranspo1.com/v1.2/GetNextTripsForStop";
        String nextTripParam = "appID=" + appID + "&apiKey=" + appKey + "&routeNo=" + routeNo + "&stopNo=" + stopNo;

        try {
            URL url = new URL(nextTrip);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            DataOutputStream dStream = new DataOutputStream(urlConnection.getOutputStream());
            dStream.writeBytes(nextTripParam);
            dStream.flush();
            dStream.close();
            int responseCode = urlConnection.getResponseCode();
            final StringBuilder output = new StringBuilder("Request URL " + url);
            output.append(System.getProperty("line.separator") + "Request Parameters " + nextTripParam);
            output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                Log.d("OUPUT:::::", stringBuilder.toString());
                InputStream ip = new ByteArrayInputStream(stringBuilder.toString().getBytes());
                ParseXML parseXML = new ParseXML();
                return parseXML.parseXML(ip);
            } catch (Exception e) {
                e.printStackTrace();
                return "Exception Occured while StringBuilder!";
            } finally {
                urlConnection.disconnect();
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
