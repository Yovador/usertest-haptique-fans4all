package com.bhaptics.bhapticsandroid.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bhaptics.bhapticsandroid.App;
import com.bhaptics.bhapticsandroid.R;
import com.bhaptics.bhapticsandroid.adapters.ListViewAdapter;
import com.bhaptics.bhapticsandroid.utils.ExperimentData;
import com.bhaptics.bhapticsmanger.SdkRequestHandler;
import com.bhaptics.service.SimpleBhapticsDevice;

import org.w3c.dom.Text;

import java.util.Calendar;

public class LobbyActivity extends Activity implements View.OnClickListener {
    public static final String TAG = LobbyActivity.class.getSimpleName();

    private ListViewAdapter adapter;

    private Button scanButton, tactFileButton, exportButton, pingallButton, purgeButton;
    MediaPlayer mediaPlayer;
    TextView textView;

    private SdkRequestHandler sdkRequestHandler;

    private android.os.Handler handler = new android.os.Handler();
    private java.lang.Runnable run = new java.lang.Runnable() {

        @Override
        public void run() {
            handler.postDelayed(this, 500);

            if (adapter != null) {
                adapter.onChangeListUpdate(sdkRequestHandler.getDeviceList());
                adapter.notifyDataSetChanged();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        mediaPlayer = MediaPlayer.create(this, R.raw.beep);

        handler.postDelayed(run, 100);


        sdkRequestHandler = App.getHandler(getApplicationContext());


        adapter = new ListViewAdapter(this, sdkRequestHandler.getDeviceList());
        ListView listview = (ListView) findViewById(R.id.deviceListView) ;
        listview.setAdapter(adapter) ;

        scanButton = findViewById(R.id.scan_button);
        scanButton.setOnClickListener(this);

        textView = findViewById(R.id.jsonView);

        tactFileButton = findViewById(R.id.tact_file_button);
        tactFileButton.setOnClickListener(this);

        pingallButton = findViewById(R.id.ping_button);
        pingallButton.setOnClickListener(this);

        exportButton = findViewById(R.id.exportButton);
        exportButton.setOnClickListener(this);

        purgeButton = findViewById(R.id.purgeButton);
        purgeButton.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!hasPermissions(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            Log.e(TAG, "onResume: permission ACCESS_FINE_LOCATION"  );
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        } else {
            sdkRequestHandler.toggleScan();
        }
        if(App.jsonManager.experimentDatas.size() != 0){
            textView.setText(App.jsonManager.CreateJsonObject().toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sdkRequestHandler.quit();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.scan_button) {
            if (!hasPermissions(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.e(TAG, "onResume: permission ACCESS_FINE_LOCATION");
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
                return;
            }

            sdkRequestHandler.toggleScan();

        } else if (v.getId() == R.id.ping_button) {
            for (SimpleBhapticsDevice simpleBhapticsDevice : sdkRequestHandler.getDeviceList()) {
                if (simpleBhapticsDevice.isConnected()) {
                    sdkRequestHandler.ping(simpleBhapticsDevice.getAddress());
                }
            }
        } else if (v.getId() == R.id.tact_file_button) {
            mediaPlayer.start();
            if(App.jsonManager.experimentDatas.size() == 0) {
                App.jsonManager.AddExperiment(new ExperimentData(Calendar.getInstance().getTime(), "Init Experiment"));
            }
            startActivityForResult(new Intent(this, TactFileActivity.class), 1);
        }
        else if (v.getId() == R.id.exportButton){
            App.jsonManager.ExportJson(this);
        }
        else if (v.getId() == R.id.purgeButton){
            App.jsonManager.PurgeJSON();
            textView.setText(App.jsonManager.CreateJsonObject().toString());
        }
    }
}
