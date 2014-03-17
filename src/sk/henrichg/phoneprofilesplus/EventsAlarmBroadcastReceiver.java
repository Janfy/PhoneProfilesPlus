package sk.henrichg.phoneprofilesplus;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

public class EventsAlarmBroadcastReceiver extends WakefulBroadcastReceiver {

	public static final String BROADCAST_RECEIVER_TYPE = "eventsAlarm";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		GlobalData.logE("#### EventsAlarmBroadcastReceiver.onReceive","xxx");
		
		GlobalData.loadPreferences(context);
		
		long eventId = intent.getLongExtra(GlobalData.EXTRA_EVENT_ID, 0);
		boolean startEvent = intent.getBooleanExtra(GlobalData.EXTRA_START_SYSTEM_EVENT, true);
		
		GlobalData.logE("EventsAlarmBroadcastReceiver.onReceive","eventId="+eventId);
		GlobalData.logE("EventsAlarmBroadcastReceiver.onReceive","startEvent="+startEvent);
		
		int eventsServiceProcedure;
		if (startEvent)
			eventsServiceProcedure = EventsService.ESP_START_EVENT;
		else
			eventsServiceProcedure = EventsService.ESP_PAUSE_EVENT;
		
		DataWrapper dataWrapper = new DataWrapper(context, false, false, 0);
		Event event = dataWrapper.getEventById(eventId);
		
		if (event != null)
		{
			event._eventPreferencesTime.removeSystemEvent(context);
			
			
			Intent eventsServiceIntent = new Intent(context, EventsService.class);
			eventsServiceIntent.putExtra(GlobalData.EXTRA_EVENT_ID, eventId);
			eventsServiceIntent.putExtra(GlobalData.EXTRA_EVENTS_SERVICE_PROCEDURE, eventsServiceProcedure);
			eventsServiceIntent.putExtra(GlobalData.EXTRA_BROADCAST_RECEIVER_TYPE, BROADCAST_RECEIVER_TYPE);
			startWakefulService(context, eventsServiceIntent);
			
		}
		
		dataWrapper.invalidateDataWrapper();
			
	}

}
