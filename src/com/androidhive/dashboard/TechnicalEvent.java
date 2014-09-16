package com.androidhive.dashboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import androidhive.dashboard.*;

public class TechnicalEvent extends Activity {
	
	// Creating JSON Parser object
		JSONParser jParser = new JSONParser();

		
		// url to get all products list
		private static String url_all_products = "http://techfest.orgfree.com/techfest/get_all_event.php?type=1";

		// JSON Node names
		private static final String TAG_SUCCESS = "success";
		private static final String TAG_PRODUCTS = "products";
		private static final String TAG_PID = "eid";
		private static final String TAG_NAME = "eventname";

		// products JSONArray
		JSONArray products = null;
		
	
	static final String KEY_ID = "uid";
	static final String KEY_TITLE = "username";
	static final String KEY_ARTIST = "username";
	static final String KEY_DURATION = "username";
	
	
	ListView list;
    LazyAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_feed_layout);
		


		ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

		// Building Parameters
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					// getting JSON string from URL
					JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
					
					

					try {
						// Checking for SUCCESS TAG
						int success = json.getInt(TAG_SUCCESS);

						if (success == 1) {
							// products found
							// Getting Array of Products
							products = json.getJSONArray(TAG_PRODUCTS);

							// looping through All Products
							for (int i = 0; i < products.length(); i++) {
								JSONObject c = products.getJSONObject(i);

								// Storing each json item in variable
								String id = c.getString(TAG_PID);
								String name = c.getString(TAG_NAME);

								// creating new HashMap
								HashMap<String, String> map = new HashMap<String, String>();

								// adding each child node to HashMap key => value
						
								map.put(KEY_ID, id);
								map.put(KEY_TITLE,name );
								map.put(KEY_ARTIST, name);
								map.put(KEY_DURATION, name);
								
								// adding HashList to ArrayList
								songsList.add(map);
							}
						} else {
							// no products found
						
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

		
		
		

		list=(ListView)findViewById(R.id.list);
		
		// Getting adapter by passing xml data ArrayList
        adapter=new LazyAdapter(this, songsList);        
        list.setAdapter(adapter);
        

        // Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {

        	@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String pid = ((TextView) view.findViewById(R.id.pid)).getText()
						.toString();

				// Starting new intent
				Intent in = new Intent(getApplicationContext(),
						ShowEvent.class);
				// sending pid to next activity
				in.putExtra(TAG_PID, pid);
				
				// starting new activity and expecting some response back
				startActivityForResult(in, 100);
			}
		});		
	}	
}