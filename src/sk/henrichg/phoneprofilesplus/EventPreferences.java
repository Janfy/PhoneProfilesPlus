package sk.henrichg.phoneprofilesplus;

import android.content.Context;
import android.content.SharedPreferences;

public class EventPreferences {
	
	public Event _event;
	public int _preferencesResourceID;
	public int _iconResourceID;
	
	public EventPreferences()
	{
		
	}
	
	public EventPreferences(Event event)
	{
		_event = event;
	}
	
	public void copyPreferences(Event fromEvent)
	{
		
	}
	
	public boolean isRunnable()
	{
		return true;
	}
	
	public void loadSharedPrefereces(SharedPreferences preferences)
	{
	}

	public void saveSharedPrefereces(SharedPreferences preferences)
	{
	}
	
	public String getPreferencesDescription(String description, Context context)
	{
		return description;
	}

	public void setSystemRunningEvent(Context context)
	{
		
	}

	public void setSystemPauseEvent(Context context)
	{
		
	}
	
	public void removeAllSystemEvents(Context context)
	{
		
	}
	
}
