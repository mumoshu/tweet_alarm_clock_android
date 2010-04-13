package jp.mumoshu.android.app.tweetalarmclock.activity;

import jp.mumoshu.android.app.tweetalarmclock.R;
import jp.mumoshu.android.app.tweetalarmclock.R.id;
import jp.mumoshu.android.app.tweetalarmclock.R.layout;
import jp.mumoshu.android.app.tweetalarmclock.R.raw;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.VideoView;

public class AlarmClock extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player);
    }

	@Override
	public void onClick(View v) {
		finish();
	}
}