package personal.buswidget;

/**
 * Created by Administrator on 2016-01-02.
 */

//  This interface will be responsible for transferring the AsyncTask output to the MainActivity

public interface AsyncResponse {
    void processFinish(String output);
}
