package sk.henrichg.phoneprofilesplus;

import java.util.List;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Handler;

public class EventsService extends IntentService
{
	Context context;
	DataWrapper dataWrapper;
	List<EventTimeline> eventTimelineList;
	int procedure;
	String broadcastReceiverType;
	
	public static final int ESP_START_EVENT = 1;
	public static final int ESP_PAUSE_EVENT = 2;
	
	public EventsService() {
		super("EventsService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		context = getBaseContext();

		GlobalData.logE("EventsService.onHandleIntent","-- start --------------------------------");

		if (!GlobalData.getApplicationStarted(context))
			// application is not started
			return;
		
		if (!GlobalData.getGlobalEventsRuning(context))
			// events are globally stopped
			return;

		GlobalData.loadPreferences(context);
		
		dataWrapper = new DataWrapper(context, false, false, 0);
		
		// create a handler to post messages to the main thread
	    Handler handler = new Handler(getMainLooper());
	    dataWrapper.setToastHandler(handler);
		
		eventTimelineList = dataWrapper.getEventTimelineList();

		GlobalData.logE("EventsService.onHandleIntent","eventTimelineList.size()="+eventTimelineList.size());
		
		procedure = intent.getIntExtra(GlobalData.EXTRA_EVENTS_SERVICE_PROCEDURE, 0);
		broadcastReceiverType = intent.getStringExtra(GlobalData.EXTRA_BROADCAST_RECEIVER_TYPE);
		
		GlobalData.logE("EventsService.onHandleIntent","procedure="+procedure);
		
		// in intent is event_id
		long event_id = intent.getLongExtra(GlobalData.EXTRA_EVENT_ID, 0);
		GlobalData.logE("EventsService.onHandleIntent","event_id="+event_id);
		Event event = dataWrapper.getEventById(event_id);
		
		List<Event> eventList = dataWrapper.getEventList();
		
		if (event == null)
		{
			for (Event _event : eventList)
			{
				GlobalData.logE("EventsService.onHandleIntent","event._id="+_event._id);
				GlobalData.logE("EventsService.onHandleIntent","event.getStatus()="+_event.getStatus());
				
				if (_event.getStatus() != Event.ESTATUS_STOP)
				{
					dataWrapper.doEventService(_event, !broadcastReceiverType.equals(PowerConnectionReceiver.BROADCAST_RECEIVER_TYPE), (procedure == ESP_START_EVENT));
				}
			}
		}
		else
		if (event.getStatus() != Event.ESTATUS_STOP)
			dataWrapper.doEventService(event, !broadcastReceiverType.equals(PowerConnectionReceiver.BROADCAST_RECEIVER_TYPE), (procedure == ESP_START_EVENT));

		doEndService(intent);

		dataWrapper.invalidateDataWrapper();

		GlobalData.logE("EventsService.onHandleIntent","-- end --------------------------------");
		
	}

	private void doEndService(Intent intent)
	{
		// completting wake
		if (broadcastReceiverType.equals(EventsAlarmBroadcastReceiver.BROADCAST_RECEIVER_TYPE))
			EventsAlarmBroadcastReceiver.completeWakefulIntent(intent);
		else
		if (broadcastReceiverType.equals(BatteryEventsAlarmBroadcastReceiver.BROADCAST_RECEIVER_TYPE))
			BatteryEventsAlarmBroadcastReceiver.completeWakefulIntent(intent);
		else
		if (broadcastReceiverType.equals(PowerConnectionReceiver.BROADCAST_RECEIVER_TYPE))
			PowerConnectionReceiver.completeWakefulIntent(intent);
	}
}
