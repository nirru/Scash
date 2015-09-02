package asy;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Fetch_data {
	String page;
	String str = "";

	/* Error */
	public String executeHttpGet(String paramString) throws java.lang.Exception {

		InputStream inputStream = null;

		try {
			// 1. create HttpClient
			HttpClient httpclient = new DefaultHttpClient();
			// 2. create HttpGet object:
			HttpGet httpGet = new HttpGet(paramString);
			// 3. finally make call to server and get the httpResponse:
			// httpResponse = httpclient.execute(httpGet);
			// 4. Execute POST request to the given URL
			HttpResponse httpResponse = httpclient.execute(httpGet);
			Log.e("RESPONSE MESSAGE", ""
					+ httpResponse.getStatusLine().getStatusCode());
			// 5. receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();
			// 6. convert inputstream to string
			if (inputStream != null)
				str = convertInputStreamToString(inputStream);
			else
				str = "Did not work!";

		} catch (Exception e) {
			e.printStackTrace();
		}// end catch..
//		Log.e("result: ", str);
		// 7. return result
		return str;
	}

	public String executeHttpPost(HashMap<String, String> hm, String url) {
		InputStream inputStream = null;
		String result = "";
		try {

			// 1. create HttpClient
			HttpClient httpclient = new DefaultHttpClient();

			// 2. make POST request to the given URL
			HttpPost httpPost = new HttpPost(url);

			String json = "";

			Set set = hm.entrySet();
			// Get an iterator
			Iterator i = set.iterator();

			JSONObject jsonObject = new JSONObject();
			// Display elements
			while(i.hasNext()) {
				Map.Entry me = (Map.Entry)i.next();
				System.out.print(me.getKey() + ": ");
//				System.out.println(me.getValue());
				jsonObject.accumulate(me.getKey().toString(),me.getValue());
			}

			// 4. convert JSONObject to JSON to String
			json = jsonObject.toString();

			// ** Alternative way to convert Person object to JSON string usin
			// Jackson Lib
			// ObjectMapper mapper = new ObjectMapper();
			// json = mapper.writeValueAsString(person);

			// 5. set json to StringEntity
			StringEntity se = new StringEntity(json);

			// 6. set httpPost Entity
			httpPost.setEntity(se);

			// 7. Set some headers to inform server about the type of the
			// content
//			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");

			// 8. Execute POST request to the given URL
			HttpResponse httpResponse = httpclient.execute(httpPost);
			Log.e("RESPONSE FROM SERVER", ""
					+ httpResponse.getStatusLine().getStatusCode());

			// 9. receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();

			// 10. convert inputstream to string
			if (inputStream != null)
				result = convertInputStreamToString(inputStream);
			else
				result = "Did not work!";

		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}

		// 11. return result
		Log.e("RESULT", "" + result);
		return result;
	}

    public String makePostRequest(HashMap<String, String> hm, String url) {
        InputStream inputStream = null;
        String result = "";
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);

        try {
            // Add your data
            Set set = hm.entrySet();
            // Get an iterator
            Iterator i = set.iterator();
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(hm.size());
            while(i.hasNext()) {
                Map.Entry me = (Map.Entry)i.next();
                System.out.print(me.getKey() + ": ");
//				System.out.println(me.getValue());
                nameValuePairs.add(new BasicNameValuePair(me.getKey().toString(), me.getValue().toString()));
            }
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            inputStream = response.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            Log.d("InputStream", e.getLocalizedMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.d("InputStream", e.getLocalizedMessage());
        }

		Log.e("RESULT","" + result);
        return result;
    }

    public String makePostRequest1( String url) {
        InputStream inputStream = null;
        String result = "";
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);

        try {
            // Add your data

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            inputStream = response.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            Log.d("InputStream", e.getLocalizedMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }


    private String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;
	}
}
