package jp.mumoshu.android.app.tweetalarmclock.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.TimePicker;

public class PreferencesRegistry {
	private SharedPreferences p;
	public PreferencesRegistry(Context c){
		p = c.getSharedPreferences("TweetAlarmClock", Context.MODE_PRIVATE);
	}
	public Editor getEditor(){
    	return p.edit();
	}
	public PreferencesRegistry putTime(TimePicker timePicker){
		getEditor()
			.putInt("hour", timePicker.getCurrentHour())
    		.putInt("minute", timePicker.getCurrentMinute())
    		.commit();
		return this;
	}
	public PreferencesRegistry putFile(String file){
		getEditor()
			.putString("play", file)
			.commit();
		return this;
	}
	public String getFile() {
		return p.getString("play", "");
	}
}
