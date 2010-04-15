package jp.mumoshu.android.app.tweetalarmclock.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jp.mumoshu.android.app.tweetalarmclock.R;
import jp.mumoshu.android.app.tweetalarmclock.controller.ActivityWakeupper;
import jp.mumoshu.android.app.tweetalarmclock.preference.PreferencesRegistry;
import android.app.Activity;
import android.app.PendingIntent.CanceledException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.AdapterView.OnItemSelectedListener;

public class ApplicationHome extends Activity {
    private Button okButton;
	private Spinner fileSpinner;
	private TimePicker timePicker;
	private int fileIndex = 0;
	private String[] files;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_home);
        fileSpinner = findSpinnerById(R.id.Spinner01);
        initSpinners();
        okButton = findButtonById(R.id.Button02);
        okButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onClickOKButton();
			}
		});
        timePicker = (TimePicker)findViewById(R.id.TimePicker01);
    }
    
    private void initSpinners(){
    	files = getMovieFilePathes();
    	ArrayAdapter<String> fileAdapter = new ArrayAdapter<String>(this, R.layout.file, files);
    	fileSpinner.setAdapter(fileAdapter);
    	fileSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {


			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				fileIndex = position;
				Log.i("ApplicationHome", "selected position: " + Integer.toString(position));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
    }
    
    private void putPreferences(){
    	String file = files[fileIndex];
    	Log.i("ApplicationHome", "selected file: " + file);
    	new PreferencesRegistry(this)
    		.putTime(timePicker)
    		.putFile(file);
    }
    
    private List<String> getMovieFiles(){
    	ArrayList<String> directories = new ArrayList<String>();
    	ArrayList<String> files = new ArrayList<String>();
    	// SD カードのFileを取得
    	File externalStorage = Environment.getExternalStorageDirectory();
    	directories.add(externalStorage.getPath());

    	// SD カード内のファイルを検索。
    	int m = 0;
    	int n = 0;
    	while(directories.size() > m){
    		File subDir = new File(directories.get(m));
    		String subFileName[] = subDir.list();
    		n = 0;
    		while(subFileName.length > n){
    			File subFile = new File(subDir.getPath() + "/" + subFileName[n]);
    			if(subFile.isDirectory()){
    				directories.add(subDir.getPath() + "/" + subFileName[n]);
    			}else if(subFile.getName().endsWith("mp4") || subFile.getName().endsWith("MP4")){
    				files.add(subDir.getPath() + "/" + subFileName[n]);
    			}
    			n++;
    		}
    		m++;
    	}
    	return files;
    }
    
    private String[] getMovieFilePathes(){
    	return getMovieFiles().toArray(new String[]{});
    }
    
    private Spinner findSpinnerById(int id){
    	return (Spinner)findViewById(id);
    }
    private Button findButtonById(int id){
    	return (Button)findViewById(id);
    }
    
    private void throwIntent(){
    	try {
			new ActivityWakeupper(this)
				.startActivityOnceAfterSeconds(VideoPlayer.class, 5);
		} catch (CanceledException e) {
			Log.e(this.getClass().toString(), "CanceledException", e);
		}
    }
    
    public void onClickOKButton(){
    	putPreferences();
    	throwIntent();
    	finish();
    }
}
