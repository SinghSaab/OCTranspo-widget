package personal.buswidget;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-01-04.
 */
public class widgetConfig extends Activity implements AdapterView.OnItemSelectedListener {

    private widgetConfig context;
    private int widgetID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widgetconfig_layout);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = -100;
        params.height = 670;
        params.width = 760;
        params.y = -50;
        this.getWindow().setAttributes(params);

//        If the user cancels the configure wizard, widget will not be created
        setResult(RESULT_CANCELED);
        context  = this;

        TextView textView_S= (TextView) findViewById(R.id.stopNo);
        TextView textView_R= (TextView) findViewById(R.id.routeNo);
        TextView textView_D= (TextView) findViewById(R.id.chooseDest);
        final EditText editText_S= (EditText) findViewById(R.id.chooseStop);
        final EditText editText_R= (EditText) findViewById(R.id.chooseRoute);
        Button createWidget = (Button) findViewById(R.id.createWidget);

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.routeDest);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Automobile");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Education");
        categories.add("Personal");
        categories.add("Travel");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);



//      This will return the extra value from the intent
        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
//            To make sure that the widget id is returned to us for making changes
            widgetID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
//        get the context of AppWidgetManager to  make changes toforce widget
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.activity_main);

        createWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("OnCLickLIstener", "Button Clicked");
                Bundle configBundle = new Bundle();
                configBundle.putString("stopNoFromConfig", String.valueOf(editText_S.getText()));
                configBundle.putString("routeNoFromConfig", String.valueOf(editText_R.getText()));
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Log.d("", "Selected:" + item);
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
