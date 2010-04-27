package jp.mumoshu.android.app.tweetalarmclock.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.mumoshu.android.app.tweetalarmclock.preference.PreferencesRegistry;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

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

import android.content.Context;
import android.util.Log;

public class TwitterClient {
	public CommonsHttpOAuthConsumer consumer;
	public OAuthProvider provider;
	private Context context;

	public TwitterClient(Context context, CommonsHttpOAuthConsumer consumer,
			OAuthProvider provider) {
		this.context  = context;
		this.consumer = consumer;
		this.provider = provider;
	}

	public void retrieveAccessToken(String oauthVerifier) throws OAuthMessageSignerException, OAuthNotAuthorizedException, OAuthExpectationFailedException, OAuthCommunicationException {
		logInfo("oauth_token(oauthVerifier):" + oauthVerifier);
		//new PreferencesRegistry(this).loadConsumer(consumer);
		logConsumer(consumer);
		provider.retrieveAccessToken(consumer, oauthVerifier);
		new PreferencesRegistry(context).saveConsumer(consumer);
	}
	
	private void logConsumer(CommonsHttpOAuthConsumer consumer) {
		logInfo("consumer: ");
		logInfo("token: " + consumer.getToken());
		logInfo("token_secret: " + consumer.getTokenSecret());
	}

	private void logInfo(String msg){
		Log.i("OAuthEntry", msg);
	}

	public String retrieveRequestToken(CommonsHttpOAuthConsumer consumer2,
			String string) throws OAuthMessageSignerException, OAuthNotAuthorizedException, OAuthExpectationFailedException, OAuthCommunicationException {
		String requestToken = provider.retrieveRequestToken(consumer, "imaokitter:///");
		logConsumer(consumer);
		new PreferencesRegistry(context).saveConsumer(consumer);
		return requestToken;
	}

	public void tweetStatus(String status) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, ClientProtocolException, IOException {
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

	public static TwitterClient getInstance(Context context) {
		CommonsHttpOAuthConsumer consumer = new CommonsHttpOAuthConsumer(
				"Znfa4v4ZSnoV0SLk8MKLA", "HWjzwsIeFWLOpGLXLTyHC0mOiCLlDO3GSTZQ8MKzFTA");
		TwitterClient c = new TwitterClient(
        		context,
        		consumer,
        		new DefaultOAuthProvider(
        				"http://api.twitter.com/oauth/request_token",
        				"http://api.twitter.com/oauth/access_token",
        				"http://api.twitter.com/oauth/authorize"));
		new PreferencesRegistry(context).loadConsumer(consumer);
		return c;
	}
}