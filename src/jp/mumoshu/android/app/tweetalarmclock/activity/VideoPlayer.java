package jp.mumoshu.android.app.tweetalarmclock.activity;

import jp.mumoshu.android.app.tweetalarmclock.R;
import jp.mumoshu.android.app.tweetalarmclock.preference.PreferencesRegistry;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.VideoView;

public class VideoPlayer extends Activity implements OnClickListener {

	private VideoView videoView;
	private Button okButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_player);
		videoView = (VideoView)findViewById(R.id.VideoView01);
        videoView.setVideoURI(Uri.parse("file://" + getFile()));
        videoView.start();
        okButton = (Button)findViewById(R.id.Button01);
        okButton.setOnClickListener(this);
	}
	
	private String getFile(){
		return new PreferencesRegistry(this).getFile();
	}

	@Override
	public void onClick(View v) {
		finish();
	}
	
}
