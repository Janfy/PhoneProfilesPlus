package sk.henrichg.phoneprofiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 18;

	// Database Name
	private static final String DATABASE_NAME = "phoneProfilesManager";

	// Profiles table name
	private static final String TABLE_PROFILES = "profiles";
	
	// import/export
	private final String EXPORT_DBPATH = "/PhoneProfiles";
	private final String EXPORT_FILENAME = DATABASE_NAME + ".backup";
	private final String DB_FILEPATH = "/data/" + PhoneProfilesActivity.PACKAGE_NAME + "/databases";

	
	// Profiles Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_ICON = "icon";
	private static final String KEY_CHECKED = "checked";
	private static final String KEY_PORDER = "porder";
	private static final String KEY_VOLUME_RINGER_MODE = "volumeRingerMode";
	private static final String KEY_VOLUME_RINGTONE = "volumeRingtone";
	private static final String KEY_VOLUME_NOTIFICATION = "volumeNotification";
	private static final String KEY_VOLUME_MEDIA = "volumeMedia";
	private static final String KEY_VOLUME_ALARM = "volumeAlarm";
	private static final String KEY_VOLUME_SYSTEM = "volumeSystem";
	private static final String KEY_VOLUME_VOICE = "volumeVoice";
	private static final String KEY_SOUND_RINGTONE_CHANGE = "soundRingtoneChange";
	private static final String KEY_SOUND_RINGTONE = "soundRingtone";
	private static final String KEY_SOUND_NOTIFICATION_CHANGE = "soundNotificationChange";
	private static final String KEY_SOUND_NOTIFICATION = "soundNotification";
	private static final String KEY_SOUND_ALARM_CHANGE = "soundAlarmChange";
	private static final String KEY_SOUND_ALARM = "soundAlarm";
	private static final String KEY_DEVICE_AIRPLANE_MODE = "deviceAirplaneMode";
	private static final String KEY_DEVICE_WIFI = "deviceWiFi";
	private static final String KEY_DEVICE_BLUETOOTH = "deviceBluetooth";
	private static final String KEY_DEVICE_SCREEN_TIMEOUT = "deviceScreenTimeout";
	private static final String KEY_DEVICE_BRIGHTNESS = "deviceBrightness";
	private static final String KEY_DEVICE_WALLPAPER_CHANGE = "deviceWallpaperChange";
	private static final String KEY_DEVICE_WALLPAPER = "deviceWallpaper";
	
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_PROFILES_TABLE = "CREATE TABLE " + TABLE_PROFILES + "("
				+ KEY_ID + " INTEGER PRIMARY KEY,"
				+ KEY_NAME + " TEXT,"
				+ KEY_ICON + " TEXT," 
				+ KEY_CHECKED + " INTEGER," 
				+ KEY_PORDER + " INTEGER," 
				+ KEY_VOLUME_RINGER_MODE + " INTEGER,"
				+ KEY_VOLUME_RINGTONE + " TEXT,"
				+ KEY_VOLUME_NOTIFICATION + " TEXT,"
				+ KEY_VOLUME_MEDIA + " TEXT,"
				+ KEY_VOLUME_ALARM + " TEXT,"
				+ KEY_VOLUME_SYSTEM + " TEXT,"
				+ KEY_VOLUME_VOICE + " TEXT,"
				+ KEY_SOUND_RINGTONE_CHANGE + " INTEGER,"
				+ KEY_SOUND_RINGTONE + " TEXT,"
				+ KEY_SOUND_NOTIFICATION_CHANGE + " INTEGER,"
				+ KEY_SOUND_NOTIFICATION + " TEXT,"
				+ KEY_SOUND_ALARM_CHANGE + " INTEGER,"
				+ KEY_SOUND_ALARM + " TEXT,"
				+ KEY_DEVICE_AIRPLANE_MODE + " INTEGER,"
				+ KEY_DEVICE_WIFI + " INTEGER,"
				+ KEY_DEVICE_BLUETOOTH + " INTEGER,"
				+ KEY_DEVICE_SCREEN_TIMEOUT + " INTEGER,"
				+ KEY_DEVICE_BRIGHTNESS + " TEXT,"
				+ KEY_DEVICE_WALLPAPER_CHANGE + " INTEGER,"
				+ KEY_DEVICE_WALLPAPER + " TEXT"
				+ ")";
		db.execSQL(CREATE_PROFILES_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		Log.d("DatabaseHandler.onUpgrade", "xxxx");
		
		/*
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILES);

		// Create tables again
		onCreate(db);
		*/
		
		if (oldVersion < 16)
		{
			// pridame nove stlpce
			db.execSQL("ALTER TABLE " + TABLE_PROFILES + " ADD COLUMN " + KEY_DEVICE_WALLPAPER_CHANGE + " INTEGER");
			db.execSQL("ALTER TABLE " + TABLE_PROFILES + " ADD COLUMN " + KEY_DEVICE_WALLPAPER + " TEXT");
			
			// updatneme zaznamy
			db.execSQL("UPDATE " + TABLE_PROFILES + " SET " + KEY_DEVICE_WALLPAPER_CHANGE + "=0");
			db.execSQL("UPDATE " + TABLE_PROFILES + " SET " + KEY_DEVICE_WALLPAPER + "='-'");
		}
		if (oldVersion < 18)
		{
			db.execSQL("UPDATE " + TABLE_PROFILES + " SET " + KEY_ICON + "=replace(" + KEY_ICON + ",':','|')");
			db.execSQL("UPDATE " + TABLE_PROFILES + " SET " + KEY_VOLUME_RINGTONE + "=replace(" + KEY_VOLUME_RINGTONE + ",':','|')");
			db.execSQL("UPDATE " + TABLE_PROFILES + " SET " + KEY_VOLUME_NOTIFICATION + "=replace(" + KEY_VOLUME_NOTIFICATION + ",':','|')");
			db.execSQL("UPDATE " + TABLE_PROFILES + " SET " + KEY_VOLUME_MEDIA + "=replace(" + KEY_VOLUME_MEDIA + ",':','|')");
			db.execSQL("UPDATE " + TABLE_PROFILES + " SET " + KEY_VOLUME_ALARM + "=replace(" + KEY_VOLUME_ALARM + ",':','|')");
			db.execSQL("UPDATE " + TABLE_PROFILES + " SET " + KEY_VOLUME_SYSTEM + "=replace(" + KEY_VOLUME_SYSTEM + ",':','|')");
			db.execSQL("UPDATE " + TABLE_PROFILES + " SET " + KEY_VOLUME_VOICE + "=replace(" + KEY_VOLUME_VOICE + ",':','|')");
			db.execSQL("UPDATE " + TABLE_PROFILES + " SET " + KEY_DEVICE_BRIGHTNESS + "=replace(" + KEY_DEVICE_BRIGHTNESS + ",':','|')");
			db.execSQL("UPDATE " + TABLE_PROFILES + " SET " + KEY_DEVICE_WALLPAPER + "=replace(" + KEY_DEVICE_WALLPAPER + ",':','|')");
			
/*			String selectQuery = "SELECT " + 
		             KEY_ID + "," +
                     KEY_ICON + "," +
	         		 KEY_VOLUME_RINGTONE + "," +
	         		 KEY_VOLUME_NOTIFICATION + "," +
	         		 KEY_VOLUME_MEDIA + "," +
	         		 KEY_VOLUME_ALARM + "," +
	         		 KEY_VOLUME_SYSTEM + "," +
	         		 KEY_VOLUME_VOICE + "," +
	         		 KEY_DEVICE_BRIGHTNESS + "," +
	         		 KEY_DEVICE_WALLPAPER +
	         		 " FROM " + TABLE_PROFILES;
			Cursor cursor = db.rawQuery(selectQuery, null);
			long id;
			String icon;
			String volumeRingtone;
			String volumeNotification;
			String volumeMedia;
			String volumeAlarm;
			String volumeSystem;
			String volumeVoice;
			String deviceBrightness;
			String deviceWallpaper;

			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					id = Long.parseLong(cursor.getString(0));
					icon = cursor.getString(1);
					volumeRingtone = cursor.getString(2).replaceAll(":", "|");
					volumeNotification = cursor.getString(3).replaceAll(":", "|");
					volumeMedia = cursor.getString(4).replaceAll(":", "|");
					volumeAlarm = cursor.getString(5).replaceAll(":", "|");
					volumeSystem = cursor.getString(6).replaceAll(":", "|");
					volumeVoice = cursor.getString(7).replaceAll(":", "|");
					deviceBrightness = cursor.getString(8).replaceAll(":", "|");
					deviceWallpaper = cursor.getString(9).replaceAll(":", "|");
					
				} while (cursor.moveToNext());
			}

			cursor.close(); */
			
		}
	}
	
	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new profile
	void addProfile(Profile profile) {
	
		//int porder = getMaxPOrder() + 1;

		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, profile.getName()); // Profile Name
		values.put(KEY_ICON, profile.getIcon()); // Icon
		values.put(KEY_CHECKED, (profile.getChecked()) ? 1 : 0); // Checked
		//values.put(KEY_PORDER, porder); // POrder
		values.put(KEY_PORDER, profile.getPOrder()); // POrder
		values.put(KEY_VOLUME_RINGER_MODE, profile.getVolumeRingerMode());
		values.put(KEY_VOLUME_RINGTONE, profile.getVolumeRingtone());
		values.put(KEY_VOLUME_NOTIFICATION, profile.getVolumeNotification());
		values.put(KEY_VOLUME_MEDIA, profile.getVolumeMedia());
		values.put(KEY_VOLUME_ALARM, profile.getVolumeAlarm());
		values.put(KEY_VOLUME_SYSTEM, profile.getVolumeSystem());
		values.put(KEY_VOLUME_VOICE, profile.getVolumeVoice());
		values.put(KEY_SOUND_RINGTONE_CHANGE, (profile.getSoundRingtoneChange()) ? 1 : 0);
		values.put(KEY_SOUND_RINGTONE, profile.getSoundRingtone());
		values.put(KEY_SOUND_NOTIFICATION_CHANGE, (profile.getSoundNotificationChange()) ? 1 : 0);
		values.put(KEY_SOUND_NOTIFICATION, profile.getSoundNotification());
		values.put(KEY_SOUND_ALARM_CHANGE, (profile.getSoundAlarmChange()) ? 1 : 0);
		values.put(KEY_SOUND_ALARM, profile.getSoundAlarm());
		values.put(KEY_DEVICE_AIRPLANE_MODE, profile.getDeviceAirplaneMode());
		values.put(KEY_DEVICE_WIFI, profile.getDeviceWiFi());
		values.put(KEY_DEVICE_BLUETOOTH, profile.getDeviceBluetooth());
		values.put(KEY_DEVICE_SCREEN_TIMEOUT, profile.getDeviceScreenTimeout());
		values.put(KEY_DEVICE_BRIGHTNESS, profile.getDeviceBrightness());
		values.put(KEY_DEVICE_WALLPAPER_CHANGE, (profile.getDeviceWallpaperChange()) ? 1 : 0);
		values.put(KEY_DEVICE_WALLPAPER, profile.getDeviceWallpaper());

		// Inserting Row
		long id = db.insert(TABLE_PROFILES, null, values);
		db.close(); // Closing database connection
		
		profile.setID(id);
		//profile.setPOrder(porder);
	}

	// Getting single profile
	Profile getProfile(long profile_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_PROFILES, 
				                 new String[] { KEY_ID, 
												KEY_NAME, 
												KEY_ICON, 
												KEY_CHECKED, 
												KEY_PORDER, 
												KEY_VOLUME_RINGER_MODE,
								         		KEY_VOLUME_RINGTONE,
								         		KEY_VOLUME_NOTIFICATION,
								         		KEY_VOLUME_MEDIA,
								         		KEY_VOLUME_ALARM,
								         		KEY_VOLUME_SYSTEM,
								         		KEY_VOLUME_VOICE,
								         		KEY_SOUND_RINGTONE_CHANGE,
								         		KEY_SOUND_RINGTONE,
								         		KEY_SOUND_NOTIFICATION_CHANGE,
								         		KEY_SOUND_NOTIFICATION,
								         		KEY_SOUND_ALARM_CHANGE,
								         		KEY_SOUND_ALARM,
								         		KEY_DEVICE_AIRPLANE_MODE,
								         		KEY_DEVICE_WIFI,
								         		KEY_DEVICE_BLUETOOTH,
								         		KEY_DEVICE_SCREEN_TIMEOUT,
								         		KEY_DEVICE_BRIGHTNESS,
								         		KEY_DEVICE_WALLPAPER_CHANGE,
								         		KEY_DEVICE_WALLPAPER
												}, 
				                 KEY_ID + "=?",
				                 new String[] { String.valueOf(profile_id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Profile profile = new Profile(Long.parseLong(cursor.getString(0)),
				                      cursor.getString(1), 
				                      cursor.getString(2),
				                      (Integer.parseInt(cursor.getString(3)) == 1) ? true : false,
				                      Integer.parseInt(cursor.getString(4)),
				                      Integer.parseInt(cursor.getString(5)),
				                      cursor.getString(6),
				                      cursor.getString(7),
				                      cursor.getString(8),
				                      cursor.getString(9),
				                      cursor.getString(10),
				                      cursor.getString(11),
				                      (Integer.parseInt(cursor.getString(12)) == 1) ? true : false,
				                      cursor.getString(13),
				                      (Integer.parseInt(cursor.getString(14)) == 1) ? true : false,
				                      cursor.getString(15),
				                      (Integer.parseInt(cursor.getString(16)) == 1) ? true : false,
				                      cursor.getString(17),
				                      Integer.parseInt(cursor.getString(18)),
				                      Integer.parseInt(cursor.getString(19)),
				                      Integer.parseInt(cursor.getString(20)),
				                      Integer.parseInt(cursor.getString(21)),
				                      cursor.getString(22),
				                      (Integer.parseInt(cursor.getString(23)) == 1) ? true : false,
				                      cursor.getString(24)
				                      );

		cursor.close();
		db.close();

		// return profile
		return profile;
	}
	
	// Getting All Profiles
	public List<Profile> getAllProfiles() {
		List<Profile> profileList = new ArrayList<Profile>();
		// Select All Query
		String selectQuery = "SELECT " + KEY_ID + "," +
				                         KEY_NAME + "," +
				                         KEY_ICON + "," +
				                         KEY_CHECKED + "," +
				                         KEY_PORDER + "," +
										 KEY_VOLUME_RINGER_MODE + "," +
						         		 KEY_VOLUME_RINGTONE + "," +
						         		 KEY_VOLUME_NOTIFICATION + "," +
						         		 KEY_VOLUME_MEDIA + "," +
						         		 KEY_VOLUME_ALARM + "," +
						         		 KEY_VOLUME_SYSTEM + "," +
						         		 KEY_VOLUME_VOICE + "," +
						         		 KEY_SOUND_RINGTONE_CHANGE + "," +
						         		 KEY_SOUND_RINGTONE + "," +
						         		 KEY_SOUND_NOTIFICATION_CHANGE + "," +
						         		 KEY_SOUND_NOTIFICATION + "," +
						         		 KEY_SOUND_ALARM_CHANGE + "," +
						         		 KEY_SOUND_ALARM + "," +
						         		 KEY_DEVICE_AIRPLANE_MODE + "," +
						         		 KEY_DEVICE_WIFI + "," +
						         		 KEY_DEVICE_BLUETOOTH + "," +
						         		 KEY_DEVICE_SCREEN_TIMEOUT + "," +
						         		 KEY_DEVICE_BRIGHTNESS + "," +
						         		 KEY_DEVICE_WALLPAPER_CHANGE + "," +
						         		 KEY_DEVICE_WALLPAPER +
		                     " FROM " + TABLE_PROFILES + " ORDER BY " + KEY_PORDER;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Profile profile = new Profile();
				profile.setID(Long.parseLong(cursor.getString(0)));
				profile.setName(cursor.getString(1));
				profile.setIcon(cursor.getString(2));
				profile.setChecked((Integer.parseInt(cursor.getString(3)) == 1) ? true : false);
				profile.setPOrder(Integer.parseInt(cursor.getString(4)));
				profile.setVolumeRingerMode(Integer.parseInt(cursor.getString(5)));
				profile.setVolumeRingtone(cursor.getString(6));
				profile.setVolumeNotification(cursor.getString(7));
                profile.setVolumeMedia(cursor.getString(8));
                profile.setVolumeAlarm(cursor.getString(9));
                profile.setVolumeSystem(cursor.getString(10));
                profile.setVolumeVoice(cursor.getString(11));
                profile.setSoundRingtoneChange((Integer.parseInt(cursor.getString(12)) == 1) ? true : false);
                profile.setSoundRingtone(cursor.getString(13));
                profile.setSoundNotificationChange((Integer.parseInt(cursor.getString(14)) == 1) ? true : false);
                profile.setSoundNotification(cursor.getString(15));
                profile.setSoundAlarmChange((Integer.parseInt(cursor.getString(16)) == 1) ? true : false);
                profile.setSoundAlarm(cursor.getString(17));
                profile.setDeviceAirplaneMode(Integer.parseInt(cursor.getString(18)));
                profile.setDeviceWiFi(Integer.parseInt(cursor.getString(19)));
                profile.setDeviceBluetooth(Integer.parseInt(cursor.getString(20)));
                profile.setDeviceScreenTimeout(Integer.parseInt(cursor.getString(21)));
                profile.setDeviceBrightness(cursor.getString(22));
                profile.setDeviceWallpaperChange((Integer.parseInt(cursor.getString(23)) == 1) ? true : false);
                profile.setDeviceWallpaper(cursor.getString(24));
				// Adding contact to list
				profileList.add(profile);
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();
		
		// return profile list
		return profileList;
	}

	// Updating single profile
	public int updateProfile(Profile profile) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, profile.getName());
		values.put(KEY_ICON, profile.getIcon());
		values.put(KEY_CHECKED, (profile.getChecked()) ? 1 : 0);
		values.put(KEY_PORDER, profile.getPOrder());
		values.put(KEY_VOLUME_RINGER_MODE, profile.getVolumeRingerMode());
		values.put(KEY_VOLUME_RINGTONE, profile.getVolumeRingtone());
		values.put(KEY_VOLUME_NOTIFICATION, profile.getVolumeNotification());
		values.put(KEY_VOLUME_MEDIA, profile.getVolumeMedia());
		values.put(KEY_VOLUME_ALARM, profile.getVolumeAlarm());
		values.put(KEY_VOLUME_SYSTEM, profile.getVolumeSystem());
		values.put(KEY_VOLUME_VOICE, profile.getVolumeVoice());
		values.put(KEY_SOUND_RINGTONE_CHANGE, (profile.getSoundRingtoneChange()) ? 1 : 0);
		values.put(KEY_SOUND_RINGTONE, profile.getSoundRingtone());
		values.put(KEY_SOUND_NOTIFICATION_CHANGE, (profile.getSoundNotificationChange()) ? 1 : 0);
		values.put(KEY_SOUND_NOTIFICATION, profile.getSoundNotification());
		values.put(KEY_SOUND_ALARM_CHANGE, (profile.getSoundAlarmChange()) ? 1 : 0);
		values.put(KEY_SOUND_ALARM, profile.getSoundAlarm());
		values.put(KEY_DEVICE_AIRPLANE_MODE, profile.getDeviceAirplaneMode());
		values.put(KEY_DEVICE_WIFI, profile.getDeviceWiFi());
		values.put(KEY_DEVICE_BLUETOOTH, profile.getDeviceBluetooth());
		values.put(KEY_DEVICE_SCREEN_TIMEOUT, profile.getDeviceScreenTimeout());
		values.put(KEY_DEVICE_BRIGHTNESS, profile.getDeviceBrightness());
		values.put(KEY_DEVICE_WALLPAPER_CHANGE, (profile.getDeviceWallpaperChange()) ? 1 : 0);
		values.put(KEY_DEVICE_WALLPAPER, profile.getDeviceWallpaper());

		// updating row
		int r = db.update(TABLE_PROFILES, values, KEY_ID + " = ?",
				        new String[] { String.valueOf(profile.getID()) });
        db.close();
        
		return r;
	}

	// Deleting single profile
	public void deleteProfile(Profile profile) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_PROFILES, KEY_ID + " = ?",
				new String[] { String.valueOf(profile.getID()) });
		db.close();
	}

	// Deleting all profile2
	public void deleteAllProfiles() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_PROFILES, null,	null);
		db.close();
	}

	// Getting profiles Count
	public int getProfilesCount() {
		String countQuery = "SELECT  * FROM " + TABLE_PROFILES;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		// return count
		int r = cursor.getCount();

		cursor.close();
		db.close();
		
		return r;
	}
	
/*	// Getting max(porder)
	public int getMaxPOrder() {
		String countQuery = "SELECT MAX(PORDER) FROM " + TABLE_PROFILES;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		int r;
		
		if (cursor.getCount() == 0)
			r = 0;
		else
		{	
			if (cursor.moveToFirst())
				// return max(porder)
				r = cursor.getInt(0);
			else
				r = 0;
		}

		cursor.close();
		db.close();
		
		return r;
		
	}
*/	
	public void activateProfile(Profile profile)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		// update all profiles checked to false
		ContentValues valuesAll = new ContentValues();
		valuesAll.put(KEY_CHECKED, 0);

		db.update(TABLE_PROFILES, valuesAll, null, null);

		// updating checked = true for profile
		profile.setChecked(true);
		
		ContentValues values = new ContentValues();
		values.put(KEY_CHECKED, (profile.getChecked()) ? 1 : 0);

		db.update(TABLE_PROFILES, values, KEY_ID + " = ?",
				        new String[] { String.valueOf(profile.getID()) });
		
        db.close();
		
	}
	
	public Profile getActivatedProfile()
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Profile profile;

		Cursor cursor = db.query(TABLE_PROFILES, 
				                 new String[] { KEY_ID, 
												KEY_NAME, 
												KEY_ICON, 
												KEY_CHECKED, 
												KEY_PORDER,
												KEY_VOLUME_RINGER_MODE,
								         		KEY_VOLUME_RINGTONE,
								         		KEY_VOLUME_NOTIFICATION,
								         		KEY_VOLUME_MEDIA,
								         		KEY_VOLUME_ALARM,
								         		KEY_VOLUME_SYSTEM,
								         		KEY_VOLUME_VOICE,
								         		KEY_SOUND_RINGTONE_CHANGE,
								         		KEY_SOUND_RINGTONE,
								         		KEY_SOUND_NOTIFICATION_CHANGE,
								         		KEY_SOUND_NOTIFICATION,
								         		KEY_SOUND_ALARM_CHANGE,
								         		KEY_SOUND_ALARM,
								         		KEY_DEVICE_AIRPLANE_MODE,
								         		KEY_DEVICE_WIFI,
								         		KEY_DEVICE_BLUETOOTH,
								         		KEY_DEVICE_SCREEN_TIMEOUT,
								         		KEY_DEVICE_BRIGHTNESS,
								         		KEY_DEVICE_WALLPAPER_CHANGE,
								         		KEY_DEVICE_WALLPAPER
												}, 
				                 KEY_CHECKED + "=?",
				                 new String[] { "1" }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		
		int rc = cursor.getCount();
		
		if (rc == 1)
		{

			profile = new Profile(Long.parseLong(cursor.getString(0)),
					                      cursor.getString(1), 
					                      cursor.getString(2),
					                      (Integer.parseInt(cursor.getString(3)) == 1) ? true : false,
					                      Integer.parseInt(cursor.getString(4)),
					                      Integer.parseInt(cursor.getString(5)),
					                      cursor.getString(6),
					                      cursor.getString(7),
					                      cursor.getString(8),
					                      cursor.getString(9),
					                      cursor.getString(10),
					                      cursor.getString(11),
					                      (Integer.parseInt(cursor.getString(12)) == 1) ? true : false,
					                      cursor.getString(13),
					                      (Integer.parseInt(cursor.getString(14)) == 1) ? true : false,
					                      cursor.getString(15),
					                      (Integer.parseInt(cursor.getString(16)) == 1) ? true : false,
					                      cursor.getString(17),
					                      Integer.parseInt(cursor.getString(18)),
					                      Integer.parseInt(cursor.getString(19)),
					                      Integer.parseInt(cursor.getString(20)),
					                      Integer.parseInt(cursor.getString(21)),
					                      cursor.getString(22),
					                      (Integer.parseInt(cursor.getString(23)) == 1) ? true : false,
					                      cursor.getString(24)
					                      );
		}
		else
			profile = null;

		cursor.close();
		db.close();

		// return profile
		return profile;
		
	}
	
	public void setPOrder(List<Profile> list)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
		Profile profile;
		ContentValues values = new ContentValues();
		
		for (int i = 0; i < list.size(); i++)
		{

			profile = list.get(i);
			
			values.put(KEY_PORDER, profile.getPOrder());

			db.update(TABLE_PROFILES, values, KEY_ID + " = ?",
				        new String[] { String.valueOf(profile.getID()) });
		}
		
        db.close();
	}
	
	public void setChecked(List<Profile> list)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
		Profile profile;
		ContentValues values = new ContentValues();
		
		for (int i = 0; i < list.size(); i++)
		{

			profile = list.get(i);
			
			values.put(KEY_CHECKED, profile.getChecked());

			db.update(TABLE_PROFILES, values, KEY_ID + " = ?",
				        new String[] { String.valueOf(profile.getID()) });
		}
		
        db.close();
	}
	
	//@SuppressWarnings("resource")
	public int importDB()
	{
		int ret = 0;
		
		// Close SQLiteOpenHelper so it will commit the created empty
		// database to internal storage
		//close();

		try {
			
			File sd = Environment.getExternalStorageDirectory();
			//File data = Environment.getDataDirectory();
			
			//File dataDB = new File(data, DB_FILEPATH + "/" + DATABASE_NAME);
			File exportedDB = new File(sd, EXPORT_DBPATH + "/" + EXPORT_FILENAME);
			
			if (exportedDB.exists())
			{
				// zistenie verzie zalohy
				SQLiteDatabase exportedDBObj = SQLiteDatabase.openDatabase(exportedDB.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
				Log.d("DatabaseHandler.importDB", "databaseVersion="+exportedDBObj.getVersion());
				//if (exportedDBObj.getVersion() == DATABASE_VERSION)
				if (exportedDBObj.getVersion() <= DATABASE_VERSION)
				{	
					
					// db z SQLiteOpenHelper
					SQLiteDatabase db = this.getWritableDatabase();
					
					try {
						db.beginTransaction();
						
						db.execSQL("DELETE FROM " + TABLE_PROFILES);
						
						// cusor na data exportedDB
						Cursor cursor = exportedDBObj.rawQuery("SELECT * FROM "+TABLE_PROFILES, null);
						String[] columnNames = cursor.getColumnNames();
						ContentValues values = new ContentValues();
	
						if (cursor.moveToFirst()) {
							do {
									values.clear();
									for (int i = 0; i < columnNames.length; i++)
									{
										values.put(columnNames[i], cursor.getString(i));
										Log.d("DatabaseHandler.importDB", "cn="+columnNames[i]+" val="+cursor.getString(i));
									}
									// Inserting Row do db z SQLiteOpenHelper
									db.insert(TABLE_PROFILES, null, values);
							} while (cursor.moveToNext());
						}
	
						cursor.close();
						
						db.setTransactionSuccessful();
						
						ret = 1;
					}
					finally {
						db.endTransaction();
					}
					db.close();
					
					//FileChannel src = new FileInputStream(exportedDB).getChannel();
					//FileChannel dst = new FileOutputStream(dataDB).getChannel();
					//dst.transferFrom(src, 0, src.size());
					//src.close();
					//dst.close();
					
					// Access the copied database so SQLiteHelper will cache it and mark
					// it as created
					//getWritableDatabase().close();
				}
				else
				{
					Log.w("DatabaseHandler.importDB", "wrong exported db version");
				}
			}
		} catch (Exception e) {
			Log.e("DatabaseHandler.importDB", e.getMessage());
		}
		
		return ret;
	}
	
	@SuppressWarnings("resource")
	public int exportDB()
	{
		int ret = 0;
		
		try {
			
			File sd = Environment.getExternalStorageDirectory();
			File data = Environment.getDataDirectory();
			
			File dataDB = new File(data, DB_FILEPATH + "/" + DATABASE_NAME);
			File exportedDB = new File(sd, EXPORT_DBPATH + "/" + EXPORT_FILENAME);
			
			Log.d("DatabaseHandler.exportDB", "dataDB="+dataDB.getAbsolutePath());
			Log.d("DatabaseHandler.exportDB", "exportedDB="+exportedDB.getAbsolutePath());
			
			if (dataDB.exists())
			{
				
				File exportDir = new File(sd, EXPORT_DBPATH);
				if (!(exportDir.exists() && exportDir.isDirectory()))
				{
					exportDir.mkdirs();
					Log.d("DatabaseHandler.exportDB", "mkdir="+exportDir.getAbsolutePath());
				}
				
				FileChannel src = new FileInputStream(dataDB).getChannel();
				FileChannel dst = new FileOutputStream(exportedDB).getChannel();
				dst.transferFrom(src, 0, src.size());
				src.close();
				dst.close();
				
				ret = 1;
			}
		} catch (Exception e) {
			Log.e("DatabaseHandler.exportDB", e.getMessage());
		}

		return ret;
	}
}
