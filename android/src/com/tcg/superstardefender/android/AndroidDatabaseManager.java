package com.tcg.superstardefender.android;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.tcg.superstardefender.Game;
import com.tcg.superstardefender.managers.DatabaseManager;

public class AndroidDatabaseManager implements DatabaseManager {

	public static final int CONNECTION_TIMEOUT = 1000 * 15;
	public static final String SERVER_ADDRESS = "https://tinycountrygames.com/phpInGames/";
	
	@Override
	public void mergeScore(int[] highscores) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean login(String username, String password) {
		ArrayList<NameValuePair> dataToSend = new ArrayList<NameValuePair>();
		dataToSend.add(new BasicNameValuePair("username", username));
		dataToSend.add(new BasicNameValuePair("password", password));
		
		HttpParams httpRequestParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);
		
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(SERVER_ADDRESS + "login.php");
		
		try {
			post.setEntity(new UrlEncodedFormEntity(dataToSend));
			HttpResponse httpResponse = client.execute(post);
			
			HttpEntity entity = httpResponse.getEntity();
			String result = EntityUtils.toString(entity);
			JSONArray jObject = new JSONArray(result);
			
			if(jObject.length() == 0) {
				return false;
			} else {
				System.out.println("test");
				Game.USER_ID = jObject.getJSONObject(0).getInt("id");
				System.out.println(Game.USER_ID);
				return true;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

}
