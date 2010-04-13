package jp.mumoshu.android.webapi.youtube;

import java.io.IOException;

import jp.mumoshu.http.StringHttp;
import android.net.Uri;
import android.net.Uri.Builder;

public class VideoInfoSearch {
	private Video video;

	public VideoInfoSearch(Video video) {
		this.setVideo(video);
	}

	public VideoInfo execute() throws IOException{
		String in = null;
		
		in = StringHttp.get(getRequestUriString());
		return VideoInfo.parse(in);
	}

	private String getRequestUriString() {
		return getRequestUri().toString();
	}

	private Uri getRequestUri() {
		Builder b = new Builder();
		b.scheme("http")
			.authority("www.youtube.com")
			.path("get_video_info")
			.appendQueryParameter("video_id", video.getVideoId());
		return b.build();
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public Video getVideo() {
		return video;
	}
}
