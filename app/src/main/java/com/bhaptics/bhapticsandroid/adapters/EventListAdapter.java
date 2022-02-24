package com.bhaptics.bhapticsandroid.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bhaptics.bhapticsandroid.App;
import com.bhaptics.bhapticsandroid.R;
import com.bhaptics.bhapticsandroid.models.TactFile;
import com.bhaptics.bhapticsandroid.utils.EventData;
import com.bhaptics.bhapticsandroid.utils.ExperimentData;
import com.bhaptics.bhapticsandroid.utils.FileUtils;
import com.bhaptics.bhapticsandroid.utils.JSONManager;
import com.bhaptics.bhapticsmanger.SdkRequestHandler;

import org.json.JSONException;

import java.util.Calendar;
import java.util.List;

public class EventListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private int layout;
    private List<EventData> eventData;

    public EventListAdapter(Activity context) throws JSONException {
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layout = R.layout.list_event;
        eventData = App.jsonManager.GetEventData(context);

    }

    @Override
    public int getCount() {
        return eventData.size();
    }

    @Override
    public Object getItem(int position) {
        return eventData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final EventData event = eventData.get(position);

        if(convertView==null){
            convertView=inflater.inflate(layout,parent,false);
            Button eventButton = convertView.findViewById(R.id.event_button);
            eventButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    App.jsonManager.AddExperiment(new ExperimentData(event.logData));
                }
            });
        }


        TextView deviceName = convertView.findViewById(R.id.event_name);
        deviceName.setText(event.displayName);
        return convertView;
    }
}
