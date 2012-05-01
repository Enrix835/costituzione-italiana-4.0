package com.zephyrteam.costituzione.util;

import java.io.File;

import com.zephyrteam.costituzione.Constants;
import com.zephyrteam.costituzione.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;

public class InitDatabaseTask extends AsyncTask<Object, Object, Object>{
	Context context;
	AlertDialog.Builder mDialog;
	
	public InitDatabaseTask(Context context) {
		this.context = context;
	}
	
	public void init() {
		this.execute();
	}
	
	@Override
	public void onPreExecute() {
		
	}
	
	@SuppressWarnings("unused")
	@Override
	protected Object doInBackground(Object...params) {
		File database = context.getDatabasePath(Constants.DB_NAME);
		boolean exists = database.exists();
   	 	
		if (!exists) {
   	 		IO.copyDatabase(context);
   	 		
		} else if (exists && Constants.DB_SHOULD_UPDATE) {
			int versionCode;
			
			try {
				versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
			} catch (NameNotFoundException e) {
				versionCode = -1;
				e.printStackTrace();
			}
			String key = Constants.PREF_DB_UPDATED + versionCode;
			
			if (!context.getSharedPreferences(Constants.PREF_KEY, Context.MODE_PRIVATE).getBoolean(key, false)) {
				mDialog = new AlertDialog.Builder(context)
					.setTitle(R.string.warning_dbtoupdate_title)
					.setMessage(R.string.warning_dbtoupdate_msg)
					.setIcon(android.R.drawable.alert_light_frame)
					.setPositiveButton(android.R.string.yes, new ResponseListener(key, 0))
					.setNegativeButton(android.R.string.no, new ResponseListener(key, 1))
					.setNeutralButton(R.string.warning_dbtoupdate_never, new ResponseListener(key, 2));
				mDialog.create().show();
			}
		}
		
		return true;
	}
	
	public class ResponseListener implements DialogInterface.OnClickListener {
		public final int YES = 0;
		public final int NO = 1;
		public final int NEVER = 2;
		
		private int mType = NO;
		private String mKey;
		
		public ResponseListener(String key, int type) {
			mType = type;
			mKey = key;
		}
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (mType) {
				case YES:
					IO.copyDatabase(context);
					context.getSharedPreferences(Constants.PREF_KEY, Context.MODE_PRIVATE).edit().putBoolean(mKey, true);
					break;
				case NEVER:
					context.getSharedPreferences(Constants.PREF_KEY, Context.MODE_PRIVATE).edit().putBoolean(mKey, true);
					break;
				case NO:
					dialog.dismiss();
					break;
				default:
					dialog.dismiss();
					break;
			}
		}
		
	}

}
