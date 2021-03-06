package sk.henrichg.phoneprofilesplus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

public class ApplicationsCache {

	private class PackInfo {
		private String appLabel = "";
		private String packageName = "";
		//private String versionName = "";
		//private int versionCode = 0;
		private Drawable icon;
	}
	
	private class SortList implements Comparator<PackInfo> {

		public int compare(PackInfo lhs, PackInfo rhs) {
			return GUIData.collator.compare(lhs.appLabel, rhs.appLabel);
		}
		
	}
	
	private ArrayList<PackInfo> applicationsList;
	private boolean cached;
	
	public ApplicationsCache()
	{
		applicationsList = new ArrayList<PackInfo>();
		cached = false;
	}
	
	public void getApplicationsList(Context context)
	{
		if (cached) return;
		
		applicationsList.clear();
		
		PackageManager packageManager = context.getPackageManager();
		List<PackageInfo> packs = packageManager.getInstalledPackages(0);
		
		for (int i = 0; i < packs.size(); i++)
		{
			PackageInfo packageInfo = packs.get(i);

			if (packageManager.getLaunchIntentForPackage(packageInfo.packageName) != null)
			{
				PackInfo newInfo = new PackInfo();
				
				newInfo.appLabel = packageInfo.applicationInfo.loadLabel(packageManager).toString();
				newInfo.packageName = packageInfo.packageName;
				//newInfo.versionName = packageInfo.versionName;
				//newInfo.versionCode = packageInfo.versionCode;
				newInfo.icon = packageInfo.applicationInfo.loadIcon(packageManager);
				
				applicationsList.add(newInfo);
			}
		}
		
		Collections.sort(applicationsList, new SortList());
		
		cached = true;
	}
	
	public int getLength()
	{
		if (cached) 
			return applicationsList.size();
		else
			return 0;
	}
	
	public String getPackageName(int position)
	{
		if (cached) 
			return applicationsList.get(position).packageName;
		else
			return "";
	}
	
	public String getApplicationLabel(int position)
	{
		if (cached) 
			return applicationsList.get(position).appLabel;
		else
			return "";
	}
	
	public Drawable getApplicationIcon(int position)
	{
		if (cached) 
			return applicationsList.get(position).icon;
		else
			return null;
	}
	
	public void clearCache()
	{
		applicationsList.clear();
		applicationsList = null;
		cached = false;
	}
	
	public boolean isCached()
	{
		return cached;
	}
}
