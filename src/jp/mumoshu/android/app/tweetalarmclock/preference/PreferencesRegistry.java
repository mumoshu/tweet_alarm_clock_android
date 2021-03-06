package jp.mumoshu.android.app.tweetalarmclock.preference;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.widget.TimePicker;

public class PreferencesRegistry {
	private SharedPreferences p;
	private Context context;
	public PreferencesRegistry(Context c){
		this.context = c;
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
	public PreferencesRegistry saveConsumer(OAuthConsumer consumer){
		getEditor()
			.putString("consumer_key", consumer.getConsumerKey())
			.putString("consumer_secret", consumer.getConsumerSecret())
			.putString("token", consumer.getToken())
			.putString("token_secret", consumer.getTokenSecret())
			.commit();
		return this;
	}
	public OAuthConsumer loadConsumer(){
		return loadConsumer(
				new CommonsHttpOAuthConsumer(
					"Znfa4v4ZSnoV0SLk8MKLA",
					"HWjzwsIeFWLOpGLXLTyHC0mOiCLlDO3GSTZQ8MKzFTA"
					));
	}
	public OAuthConsumer loadConsumer(OAuthConsumer consumer){
		consumer.setTokenWithSecret(
				p.getString("token", ""),
				p.getString("token_secret", ""));
		return consumer;
	}
	public String getMessage() {
		return PreferenceManager.getDefaultSharedPreferences(context).getString("message", "�ނ���B");
	}
}
