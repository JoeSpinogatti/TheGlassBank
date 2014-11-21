package app.com.cue;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.annotation.SuppressLint;

public class DataFromJSON {
	private String result;
		private String urlString = null;

	public volatile boolean parsingComplete = true;

	public DataFromJSON(String url) {
		this.urlString = url;
	}

	@SuppressLint("NewApi")
	public void readAndParseJSON(String data) {
		try {
			JSONObject reader = new JSONObject(data);

			result = reader.getString("cue");


			parsingComplete = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void fetchJSON() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					URL url = new URL(urlString);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setReadTimeout(10000 /* milliseconds */);
					conn.setConnectTimeout(15000 /* milliseconds */);
					conn.setRequestMethod("GET");
					conn.setDoInput(true);
					// Starts the query
					conn.connect();
					InputStream stream = conn.getInputStream();

					String data = convertStreamToString(stream);

					readAndParseJSON(data);
					stream.close();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		thread.start();
	}

	static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}
	
	public String getResult() {
		return result;
	}

	
}
