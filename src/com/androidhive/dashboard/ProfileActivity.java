package com.androidhive.dashboard;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidhive.dashboard.R;


public class ProfileActivity extends Activity{
	
	Button btnViewProducts;
	
	// Session Manager Class
			SessionManager session;
			 // Button Logout
			Button btnLogout;
			
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photos_layout);
		
		// Buttons
		btnViewProducts = (Button) findViewById(R.id.btnViewProducts);
		
		

	        // Session class instance
	        session = new SessionManager(getApplicationContext());
	        
	        TextView lblName = (TextView) findViewById(R.id.lblName);
	        TextView lblEmail = (TextView) findViewById(R.id.lblEmail);
	        
	        // Button logout
	        btnLogout = (Button) findViewById(R.id.btnLogout);
			
	        
	        /**
	         * Call this function whenever you want to check user login
	         * This will redirect user to LoginActivity is he is not
	         * logged in
	         * */
	        session.checkLogin();
	        
	        // get user data from session
	        HashMap<String, String> user = session.getUserDetails();
	        
	        // name
	        String name = user.get(SessionManager.KEY_NAME);
	        
	        // email
	        String email = user.get(SessionManager.KEY_EMAIL);
	        
	        // displaying user data
	        lblName.setText(Html.fromHtml("Name: <b>" + name + "</b>"));
	        lblEmail.setText(Html.fromHtml("Email: <b>" + email + "</b>"));
	        
	        
	        /**
	         * Logout button click event
	         * */
		// view products click event
		btnViewProducts.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// Launching create new product activity
				Intent i = new Intent(getApplicationContext(), CreateEvent.class);
				startActivity(i);
				
			}
		});
		 btnLogout.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// Clear the session data
					// This will clear all session data and 
					// redirect user to LoginActivity
					session.logoutUser();
				}
			});
	}
	
	        
	       
	       
	    
	        
}
