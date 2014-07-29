package sk.henrichg.phoneprofilesplus;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v4.content.WakefulBroadcastReceiver;

public class WifiScanBroadcastReceiver extends WakefulBroadcastReceiver {

	public static final String BROADCAST_RECEIVER_TYPE = "wifiScan";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		//GlobalData.logE("#### WifiScanBroadcastReceiver.onReceive","xxx");
		GlobalData.logE("@@@ WifiScanBroadcastReceiver.onReceive","####");

		boolean scanStarted = (WifiScanAlarmBroadcastReceiver.getStartScan(context));// ||
				              //(WifiScanAlarmBroadcastReceiver.scanResults == null);
		
		WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiScanAlarmBroadcastReceiver.scanResults = wifi.getScanResults();
		WifiScanAlarmBroadcastReceiver.unlock();
		
		/*
		for (ScanResult result : WifiScanAlarmBroadcastReceiver.scanResults)
        {
			GlobalData.logE("WifiScanBroadcastReceiver.onReceive","result.SSID="+result.SSID);
        }
        */
		
		GlobalData.loadPreferences(context);
		
		if (GlobalData.getGlobalEventsRuning(context))
		{

			if (scanStarted)
			{
				GlobalData.logE("@@@ WifiScanBroadcastReceiver.onReceive","xxx");

				if (WifiScanAlarmBroadcastReceiver.getWifiEnabledForScan(context))
				{
					GlobalData.logE("@@@ WifiScanBroadcastReceiver.onReceive","disable wifi");
					wifi.setWifiEnabled(false);
				}
				
				/*
				boolean wifiEventsExists = false;
				
				DataWrapper dataWrapper = new DataWrapper(context, false, false, 0);
				wifiEventsExists = dataWrapper.getDatabaseHandler().getTypeEventsCount(DatabaseHandler.ETYPE_WIFIINFRONT) > 0;
				GlobalData.logE("WifiScanBroadcastReceiver.onReceive","wifiEventsExists="+wifiEventsExists);
				dataWrapper.invalidateDataWrapper();
	
				if (wifiEventsExists)
				{*/
					// start service
					Intent eventsServiceIntent = new Intent(context, EventsService.class);
					eventsServiceIntent.putExtra(GlobalData.EXTRA_BROADCAST_RECEIVER_TYPE, BROADCAST_RECEIVER_TYPE);
					startWakefulService(context, eventsServiceIntent);
				//}
			}

		}
		
		WifiScanAlarmBroadcastReceiver.setStartScan(context, false);
		
	}
	
}
