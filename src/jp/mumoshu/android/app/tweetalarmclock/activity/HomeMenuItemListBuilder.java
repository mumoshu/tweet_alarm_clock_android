package jp.mumoshu.android.app.tweetalarmclock.activity;

import java.util.ArrayList;
import java.util.List;

public class HomeMenuItemListBuilder {
	
	private ArrayList<HomeMenuItem> items;

	public HomeMenuItemListBuilder(){
		this.items = new ArrayList<HomeMenuItem>();
	}

	public HomeMenuItemListBuilder add(String text, OnClickHandler onClickHandler) {
		items.add(new HomeMenuItem(text, onClickHandler));
		return this;
	}

	public List<HomeMenuItem> build() {
		return this.items;
	}

}
