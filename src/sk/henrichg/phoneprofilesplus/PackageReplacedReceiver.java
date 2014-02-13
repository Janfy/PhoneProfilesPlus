package sk.henrichg.phoneprofilesplus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PackageReplacedReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		Log.e("PackageReplacedReceiver.onReceive","xxx");
		
		if (GlobalData.getApplicationStarted(context))
		{
			GlobalData.grantRoot();
				
			DataWrapper dataWrapper = new DataWrapper(context, false, false, 0);
			dataWrapper.firstStartEvents();
			dataWrapper.invalidateDataWrapper();
		}
	}

}
