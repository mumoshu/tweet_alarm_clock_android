package jp.mumoshu.android.app.tweetalarmclock.activity;

import java.util.List;

import jp.mumoshu.android.app.tweetalarmclock.R;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class HomeMenu extends ListActivity {

	private List<HomeMenuItem> menuItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_menu);
		
		HomeMenuItemListBuilder b = new HomeMenuItemListBuilder()
			.add("時刻設定", new OnClickHandler(){
				@Override
				public void onClick(){
					startActivityFromThis(ApplicationHome.class);
				}
			})
			.add("Twitterアカウント", new OnClickHandler(){
				@Override
				public void onClick(){
					startActivityFromThis(OAuthEntry.class);
				}
			});
		
		menuItems = b.build();
		setListAdapter(new HomeMenuItemListAdapter(
				this,
				menuItems
		));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		menuItems.get(position).onClick();
	}

	protected void startActivityFromThis(Class<?> activityClass){
		startActivity(new Intent(this, activityClass));
	}
}
