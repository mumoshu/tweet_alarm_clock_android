package jp.mumoshu.android.app.tweetalarmclock.controller;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.Intent;

public class ActivityWakeupper {
	private AlarmManager am = null;
	private Context context;
	
	public ActivityWakeupper(Context context){
		this.context = context;
		am = (AlarmManager) context.getSystemService(Service.ALARM_SERVICE);
	}
	
	private void setWakeup(PendingIntent sender, Calendar cal){
		am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);
	}

	public void startActivityOnceAt(Class<?> activityClass, Calendar cal) throws CanceledException{
		Intent intent= new Intent(context, activityClass);
		PendingIntent sender = PendingIntent.getActivity(context, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
		setWakeup(sender, cal);
	}
	
	public void startActivityOnceAfterSeconds(Class<?> activityClass, int seconds) throws CanceledException{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.SECOND, cal.get(Calendar.SECOND) + seconds);
		startActivityOnceAt(activityClass, cal);
	}
}
