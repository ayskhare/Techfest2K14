package info.androidhive.login;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.androidhive.dashboard.AndroidDashboardDesignActivity;
import com.androidhive.dashboard.SessionManager;

import android.R.array;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidhive.dashboard.*;


public class Login extends Activity {

		// Progress Dialog
		private ProgressDialog pDialog;

		// Session Manager Class
		SessionManager session;
		
		JSONParser jsonParser = new JSONParser();
		Button btnLogin;
		Button btnLinkToRegister;
		EditText inputEmail;
		EditText inputPassword;
		TextView loginErrorMsg;
		
		// url to create new product
		private static String url_create_product = "http://techfest.orgfree.com/techfest/create_product.php";

		// JSON Node names
		private static final String TAG_SUCCESS = "success";

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.login);
			// Importing all assets like buttons, text fields
			inputEmail = (EditText) findViewById(R.id.loginEmail);
			inputPassword = (EditText) findViewById(R.id.loginPassword);
			btnLogin = (Button) findViewById(R.id.btnLogin);
			btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
			loginErrorMsg = (TextView) findViewById(R.id.login_error);

			
			 // Session Manager
	        session = new SessionManager(getApplicationContext());   
			
			
	    	// Login button Click Event
			btnLogin.setOnClickListener(new View.OnClickListener() {

				public void onClick(View view) {
					
					
					new CreateRegister().execute();
					
				}
			});

			// Link to Register Screen
			btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

				public void onClick(View view) {
					Intent i = new Intent(getApplicationContext(),
							RegisterActivity.class);
					startActivity(i);
					finish();
				}
			});
		}

		/**
		 * Background Async Task to Create new product
		 * */
		class CreateRegister extends AsyncTask<String, String, String> {

			/**
			 * Before starting background thread Show Progress Dialog
			 * */
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				pDialog = new ProgressDialog(Login.this);
				pDialog.setMessage("Registering..");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			}

			/**
			 * Creating product
			 * */
			protected String doInBackground(String... args) {
				
				String username = inputEmail.getText().toString();
				String password = inputPassword.getText().toString();
				
				
				// Check if username, password is filled				
				if(username.trim().length() > 0 && password.trim().length() > 0){
					// For testing puspose username, password is checked with sample data
					// username = test
					// password = test
					if(username.equals("test") && password.equals("test")){
						
						// Creating user login session
						// For testing i am stroing name, email as follow
						// Use user real data
						session.createLoginSession("Android Hive", "anroidhive@gmail.com");
						
						// successfully created product
						Intent i = new Intent(getApplicationContext(), AndroidDashboardDesignActivity.class);
						startActivity(i);
						
					}			
				}
				
				
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("username", username));
				params.add(new BasicNameValuePair("password", password));
				
				
				Log.d("passwordhint", params.toString());	
				
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
						Intent i = new Intent(getApplicationContext(), AndroidDashboardDesignActivity.class);
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