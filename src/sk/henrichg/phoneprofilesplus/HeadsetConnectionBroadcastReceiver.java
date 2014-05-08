package sk.henrichg.phoneprofilesplus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.content.WakefulBroadcastReceiver;

public class HeadsetConnectionBroadcastReceiver extends WakefulBroadcastReceiver {

	public static final String BROADCAST_RECEIVER_TYPE = "headsetConnection";
	
	public static final String[] HEADPHONE_ACTIONS = {
        Intent.ACTION_HEADSET_PLUG,
        "android.bluetooth.headset.action.STATE_CHANGED",
        "android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED"
    };
	
	@Override
	public void onReceive(Context context, Intent intent) {
		GlobalData.logE("#### HeadsetConnectionBroadcastReceiver.onReceive","xxx");
		
		GlobalData.loadPreferences(context);
		
		if (GlobalData.getGlobalEventsRuning(context))
		{
			boolean broadcast = false;
			
			boolean connectedHeadphones = false;
			boolean connectedMicrophone = false;
			boolean bluetoothHeadset = false;

	        // Wired headset monitoring
	        if (intent.getAction().equals(HEADPHONE_ACTIONS[0]))
	        {
	            connectedHeadphones = (intent.getIntExtra("state", 0) == 1);
				connectedMicrophone = (intent.getIntExtra("microphone", 0) == 1);
				bluetoothHeadset = false;

	            broadcast = true;
	        }

	        // Bluetooth monitoring
	        // Works up to and including Honeycomb
	        if (intent.getAction().equals(HEADPHONE_ACTIONS[1]))
	        {
	            connectedHeadphones = (intent.getIntExtra("android.bluetooth.headset.extra.STATE", 0) == 2);
				//connectedMicrophone = false;
				bluetoothHeadset = true;
	            
	            broadcast = true;
	        }

	        // Works for Ice Cream Sandwich
	        if (intent.getAction().equals(HEADPHONE_ACTIONS[2]))
	        {
	            connectedHeadphones = (intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0) == 2);
				//connectedMicrophone = false;
				bluetoothHeadset = true;
	            
	            broadcast = true;
	        }

	        if (broadcast)
	        {
				
				DataWrapper dataWrapper = new DataWrapper(context, false, false, 0);
				boolean peripheralEventsExists = dataWrapper.getDatabaseHandler().getTypeEventsCount(DatabaseHandler.ETYPE_PERIPHERAL) > 0;
				dataWrapper.invalidateDataWrapper();
		
				if (peripheralEventsExists)
				{
					SharedPreferences preferences = context.getSharedPreferences(GlobalData.APPLICATION_PREFS_NAME, Context.MODE_PRIVATE);
					Editor editor = preferences.edit();
					editor.putBoolean(GlobalData.PREF_EVENT_HEADSET_CONNECTED, connectedHeadphones);
					editor.putBoolean(GlobalData.PREF_EVENT_HEADSET_MICROPHONE, connectedMicrophone);
					editor.putBoolean(GlobalData.PREF_EVENT_HEADSET_BLUETOOTH, bluetoothHeadset);
					editor.commit();
					
					// start service
					Intent eventsServiceIntent = new Intent(context, EventsService.class);
					eventsServiceIntent.putExtra(GlobalData.EXTRA_EVENT_ID, 0L);
					eventsServiceIntent.putExtra(GlobalData.EXTRA_BROADCAST_RECEIVER_TYPE, BROADCAST_RECEIVER_TYPE);
					startWakefulService(context, eventsServiceIntent);
				}
	        }
			
		}
	}
}