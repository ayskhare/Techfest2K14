package com.androidhive.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import androidhive.dashboard.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import android.widget.AdapterView.OnItemSelectedListener;
public class CreateEvent extends Activity implements OnItemSelectedListener{

	// Progress Dialog
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();
	EditText eventName;
	Spinner spinner1,spinner2;
	EditText description;
	EditText dateTime;
	EditText fees1;
	EditText venue;

	// url to create new product
	private static String url_create_product = "http://techfest.orgfree.com/techfest/create_event.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_product);

		// Edit Text
		eventName = (EditText) findViewById(R.id.eventName);
		
		description = (EditText) findViewById(R.id.description);
		dateTime = (EditText) findViewById(R.id.dateTime);
		fees1 = (EditText) findViewById(R.id.fees);
		venue = (EditText) findViewById(R.id.venue);

		// Create button
		Button btnCreateProduct = (Button) findViewById(R.id.btnCreateProduct);

		// button click event
		btnCreateProduct.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				
				// creating new product in background thread
				new CreateNewProduct().execute();
			}
		});
		 // Spinner element
         spinner1 = (Spinner) findViewById(R.id.college_name);
        
        // Spinner click listener
        spinner1.setOnItemSelectedListener(this);
        
        // Spinner Drop down elements
        List<String> college = new ArrayList<String>();
        college.add("TIT-M");
        college.add("TIT-S");
        college.add("TIT-E");
        college.add("TIT-C");
        
        // Creating adapter for spinner
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, college);
		
		// Drop down layout style - list view with radio button
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		// attaching data adapter to spinner
		spinner1.setAdapter(dataAdapter);
		
				
		// Spinner2 element
         spinner2 = (Spinner) findViewById(R.id.event_category);
        
        // Spinner click listener
        spinner2.setOnItemSelectedListener(this);
        
        // Spinner Drop down elements
        List<String> event = new ArrayList<String>();
        event.add("Technical");
        event.add("Cultural");
        event.add("Sports");
        event.add("FunZone");
        
        // Creating adapter for spinner
		ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, event);
		
		// Drop down layout style - list view with radio button
		dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		// attaching data adapter to spinner
		spinner2.setAdapter(dataAdapter2);
		
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		// On selecting a spinner item
		String item = parent.getItemAtPosition(position).toString();
		
		
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Background Async Task to Create new product
	 * */
	class CreateNewProduct extends AsyncTask<String, String, String> {
		
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(CreateEvent.this);
			pDialog.setMessage("Creating event..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {
			String eventname = eventName.getText().toString();
			String collegename = spinner1.getSelectedItem().toString();
			String eventcategory = spinner2.getSelectedItem().toString();
			String datetime = dateTime.getText().toString();
			
			String venue = dateTime.getText().toString();
			String fees = fees1.getText().toString();

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			params.add(new BasicNameValuePair("eventname", eventname));
			params.add(new BasicNameValuePair("collegename", collegename));
			params.add(new BasicNameValuePair("eventcategory", eventcategory));
			params.add(new BasicNameValuePair("datetime",datetime));
			params.add(new BasicNameValuePair("fees", fees));
			params.add(new BasicNameValuePair("venue", venue));

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_create_product,
					"GET", params);
			
			// check log cat fro response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// successfully created product
					Intent i = new Intent(getApplicationContext(), TechnicalEvent.class);
					startActivity(i);
					
					// closing this screen
					finish();
				} else {
					// failed to create product
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
		}

	}
}
