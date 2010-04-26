package jp.mumoshu.android.app.tweetalarmclock.activity;

import java.util.List;

import jp.mumoshu.android.app.tweetalarmclock.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HomeMenuItemListAdapter extends BaseAdapter {
	
	private List<HomeMenuItem> menuItems;
	private Context context;

	public HomeMenuItemListAdapter(Context context, List<HomeMenuItem> menuItems){
		this.menuItems = menuItems;
		this.context   = context;
	}

	@Override
	public int getCount() {
		return menuItems.size();
	}

	@Override
	public Object getItem(int position) {
		return get(position);
	}

	private HomeMenuItem get(int position){
		return menuItems.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HomeMenuItem item = get(position);
		View view = convertView;
		if (convertView == null) {
			view = inflateView();
		}
		TextView textView = (TextView) view.findViewById(R.id.TextView01);
		textView.setText(item.getText());
		return view;
	}

	protected View inflateView(){
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		return inflater.inflate(R.layout.home_menu_item, null);
	}

}
