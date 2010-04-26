package jp.mumoshu.android.app.tweetalarmclock.activity;

public class HomeMenuItem {

	private OnClickHandler onClickHandler;
	private String text;

	public HomeMenuItem(String text, OnClickHandler onClickHandler) {
		this.text = text;
		this.onClickHandler = onClickHandler;
	}

	public void onClick() {
		this.onClickHandler.onClick();
	}

	public CharSequence getText() {
		return text;
	}

}
