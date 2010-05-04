package jp.mumoshu.android.app.tweetalarmclock.activity;

import jp.mumoshu.android.app.tweetalarmclock.R;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;

public class ApplicationPreference extends PreferenceActivity implements OnSharedPreferenceChangeListener {
	private static final String KEY_MESSAGE_PREFERENCE = "message";
	private static final String KEY_TWITTER_PREFERENCE = "twitter";
	private EditTextPreference mMessagePreference;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.pref);
		mMessagePreference = (EditTextPreference)getPreferenceScreen().findPreference(KEY_MESSAGE_PREFERENCE);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (key.equals(KEY_MESSAGE_PREFERENCE)) {
			refreshMessagePreferenceSummary();
		} else if (key.equals(KEY_TWITTER_PREFERENCE)) {
			
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		
		refreshMessagePreferenceSummary();
		getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		
		getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}

	private SharedPreferences getSharedPreferences() {
		return getPreferenceScreen().getSharedPreferences();
	}
	
	private void refreshMessagePreferenceSummary() {
		mMessagePreference.setSummary(getSharedPreferences().getString(KEY_MESSAGE_PREFERENCE, ""));
	}
}
