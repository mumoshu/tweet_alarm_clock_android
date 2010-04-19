package jp.mumoshu.android.app.tweetalarmclock.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.mumoshu.android.app.tweetalarmclock.R;
import jp.mumoshu.android.app.tweetalarmclock.preference.PreferencesRegistry;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author mumoshu
 * AndroidManifest.xmlでこのActivityのlaunch modeをsingleInstanceにしておくこと。
 * さもなくば、ブラウザを呼び出したActivityとcallbackを受けるActivityが別になってしまう　→　(　´∀｀)＜ぬるぽ
 */
public class OAuthEntry extends Activity {
	private TextView userNameView;
	private Button proceedButton;
	private CommonsHttpOAuthConsumer consumer = new CommonsHttpOAuthConsumer("Znfa4v4ZSnoV0SLk8MKLA",
	"HWjzwsIeFWLOpGLXLTyHC0mOiCLlDO3GSTZQ8MKzFTA");;
	private OAuthProvider provider = new DefaultOAuthProvider("http://api.twitter.com/oauth/request_token",
			"http://api.twitter.com/oauth/access_token", "http://api.twitter.com/oauth/authorize");;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oauth_entry);
        userNameView = (EditText) findViewById(R.id.EditText01);
        proceedButton = (Button) findViewById(R.id.Button01);
        proceedButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				runBrowserToAuthorize();
			}
		});
        initOAuthClient();
        logInfo("onCreate");
    }

	private void initOAuthClient() {
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		logInfo("onNewIntent");
		Uri uri = intent.getData();
		verifyAuthorization(uri);
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		logInfo("onResume");
		//Uri uri = getIntent().getData();
		//verifyAuthorization(uri);
	}

	private void verifyAuthorization(Uri uri) {
		String oauthVerifier;
		if (uri == null) {
			logInfo("callback uri was null. you came back to this app without authorization on the twitter?");
			return;
		}
		oauthVerifier = uri.getQueryParameter(oauth.signpost.OAuth.OAUTH_VERIFIER);
		logInfo("oauth_token(oauthVerifier):" + oauthVerifier);
		try {
			//new PreferencesRegistry(this).loadConsumer(consumer);
			logConsumer(consumer);
			provider.retrieveAccessToken(consumer, oauthVerifier);
		} catch (Exception e) {
			Log.e("OAuthEntry", "while retrieving access token", e);
			authErrorToast();
			return;
		}
		tweetMessage();
	}
	
	private void logConsumer(CommonsHttpOAuthConsumer consumer) {
		logInfo("consumer: ");
		logInfo("token: " + consumer.getToken());
		logInfo("token_secret: " + consumer.getTokenSecret());
	}

	private void tweetMessage(){
		try {
			tweetStatus("test");
		} catch (Exception e) {
			Log.e("OAuthEntry", "while tweeting", e);
			toast("通信に失敗しました。しばらく待ってからもう一度お試しください。");
		}
	}

	private void tweetStatus(String status) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, ClientProtocolException, IOException {
		final String apiUrl = "http://api.twitter.com/1/statuses/update.json";
		HttpPost req = new HttpPost(apiUrl);
		final List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("status", status));
		req.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		// set this to avoid 417 error (Expectation Failed)  
		req.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);  
		consumer.sign(req);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(req);
		final String reason = response.getStatusLine().getReasonPhrase();
		int statusCode = response.getStatusLine().getStatusCode();
		Log.i("OAuthEntry", "statusCode: " + String.valueOf(statusCode));
		Log.i("OAuthEntry", "reason: " + reason);
	}

	protected void runBrowserToAuthorize() {
		String authUrl = null;
		try {
			authUrl = provider.retrieveRequestToken(consumer, "imaokitter:///");
			logConsumer(consumer);
			new PreferencesRegistry(this).saveConsumer(consumer);
		} catch (Exception e){
			Log.e("OAuthEntry", "while requesting a request token", e);
		}
		
		if (authUrl == null){
			authErrorToast();
			Log.e("OAuthEntry", "authUrl is unexpectedly null", new Exception());
			return;
		}
		
		logInfo("authUrl: " + authUrl);
		
		openUrlInBrowser(authUrl);
	}

	private void openUrlInBrowser(String url) {
		openUriInBrowser(Uri.parse(url));
	}

	private void openUriInBrowser(Uri uri){
		Intent i = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(i);
	}
	
	private void logInfo(String msg){
		Log.i("OAuthEntry", msg);
	}

	private void toast(String msg){
		Toast.makeText(this, msg, Toast.LENGTH_SHORT);
	}
	
	private void authErrorToast(){
		toast("認証に失敗しました。しばらく待ってからもう一度お試しください");
	}
}
