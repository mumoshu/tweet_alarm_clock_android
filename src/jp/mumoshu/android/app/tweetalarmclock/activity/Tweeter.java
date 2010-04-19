package jp.mumoshu.android.app.tweetalarmclock.activity;

import jp.mumoshu.android.app.tweetalarmclock.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TimePicker;

public class Tweeter extends Activity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_home);
    }
}
