package com.bhaptics.bhapticsandroid.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.bhaptics.bhapticsandroid.R;
import com.bhaptics.bhapticsandroid.adapters.EventListAdapter;
import com.bhaptics.bhapticsandroid.adapters.TactFileListAdapter;

import org.json.JSONException;

public class TactFileActivity extends Activity implements View.OnClickListener{
    public static final String TAG = TactFileActivity.class.getSimpleName();

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tact_file);

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(this);

        String tactotFileFolder = "tactFiles/tactot";
        ListView tactotFileListView = findViewById(R.id.tactot_file_list);
        tactotFileListView.setAdapter(new TactFileListAdapter(this, tactotFileFolder));

        ListView eventListView = findViewById(R.id.event_list);
        try {
            eventListView.setAdapter(new EventListAdapter(this));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (backButton.getId() == v.getId()) {
            finish();
        } else {
            Log.e(TAG, "onClick: ");
        }
    }
}
