package sk.henrichg.phoneprofilesplus;

//import java.util.Calendar;

//import android.app.AlarmManager;
//import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class PhoneProfilesServiceSheduler extends BroadcastReceiver {

	// Restart service every 30 seconds
	//private static final long REPEAT_TIME = 1000 * 30;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		//Log.d("PhoneProfilesServiceSheduler.onResume","xxx");
		
		if (GlobalData.applicationStartOnBoot)
		{	

		/*	AlarmManager service = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	
		    Intent i = new Intent(context, PhoneProfilesServiceStarter.class);
		    PendingIntent pending = PendingIntent.getBroadcast(context, <uniquie_alarm_id>, i, PendingIntent.FLAG_CANCEL_CURRENT);
	
		    Calendar cal = Calendar.getInstance();
		    // Start 30 seconds after boot completed
		    cal.add(Calendar.SECOND, 30);
		    //
		    // Fetch every 30 seconds
		    // InexactRepeating allows Android to optimize the energy consumption
		    service.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), REPEAT_TIME, pending);
		    // service.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), REPEAT_TIME, pending);
		*/
			
			Intent service = new Intent(context, PhoneProfilesService.class);
			context.startService(service);
			
		}
	}
	
}