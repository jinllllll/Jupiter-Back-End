package external;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;


public class GitHubClient {
	private static final String URL_TEMPLATE = "https://jobs.github.com/positions.json?description=%s&lat=%s&long=%s";
	private static final String DEFAULT_KEYWORD = "developer";
	
	public JSONArray search(double lat, double lon, String keyword) {
		//prepare HTTP request parameter
		if (keyword==null) {
			keyword = DEFAULT_KEYWORD;
		} try {
			//corner case: encode the keyword, in case there's " "
			keyword=URLEncoder.encode(keyword, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String url = String.format(URL_TEMPLATE, keyword, lat ,lon);
		
		//send HTTP request
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		try {
			//this is what we got from sending get request to gitHub API
			CloseableHttpResponse response = httpClient.execute(new HttpGet(url));
			
			//if not successful just end it
			if (response.getStatusLine().getStatusCode() !=200) {
				return new JSONArray();
			}
			
			//if the response entity is null just end it
			HttpEntity entity = response.getEntity();
			if (entity==null) {
				return new JSONArray();
			}
			//return the data we got from github
			//use this inputStreamReader to read the content as stream -- letter by letter is too slow, read all at one time may result error
			//buffer reader, read per line, not slow, not too risky
			BufferedReader reader = new BufferedReader (new InputStreamReader(entity.getContent()));
			StringBuilder responseBody = new StringBuilder();
			String line = null;
			//not null -> append
			while ((line=reader.readLine())!=null) {
				responseBody.append(line);
			}
			return new JSONArray(responseBody.toString());
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//get HTTP response body
		
		return new JSONArray();
	}

}
