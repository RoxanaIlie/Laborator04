package ro.pub.cs.systems.eim.lab04.contactsmanager;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ContactsManagerActivity extends Activity {
	
	private Button showHideButton = null;
	private EditText nameText = null;
	private EditText phoneText = null;
	private EditText emailText = null;
	private EditText addressText = null;
	private Button saveButton = null;
	private Button cancelButton = null;
	private EditText jobText = null;
	private EditText companyText = null;
	private EditText websiteText = null;
	private EditText imText = null;
	private LinearLayout container2 = null;
	
	private ShowHideButtonListener showHideButtonListener = new ShowHideButtonListener();
	private SaveButtonListener saveButtonListener = new SaveButtonListener();
	private CancelButtonListener cancelButtonListener = new CancelButtonListener();

	private class ShowHideButtonListener implements View.OnClickListener {
		
		 @Override
	        public void onClick(View view) {
				if (container2.getVisibility() == View.VISIBLE) {
					container2.setVisibility(View.INVISIBLE);
					showHideButton.setText(getResources().getString(R.string.show_info));
				} else if (container2.getVisibility() == View.INVISIBLE) {
					container2.setVisibility(View.VISIBLE);
					showHideButton.setText(getResources().getString(R.string.hide_info));
		        }
		 }
	}
	
	private class SaveButtonListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
			intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
			
			String name = nameText.getText().toString();
			String phone = phoneText.getText().toString();
			String email = emailText.getText().toString();
			String address = addressText.getText().toString();
			String jobTitle = jobText.getText().toString();
			String company = companyText.getText().toString();
			String website = websiteText.getText().toString();
			String im = imText.getText().toString();
			
			if (name != null) {
			  intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
			}
			if (phone != null) {
			  intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
			}
			if (email != null) {
			  intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
			}
			if (address != null) {
			  intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
			}
			if (jobTitle != null) {
			  intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
			}
			if (company != null) {
			  intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
			}
			ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
			if (website != null) {
			  ContentValues websiteRow = new ContentValues();
			  websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
			  websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
			  contactData.add(websiteRow);
			}
			if (im != null) {
			  ContentValues imRow = new ContentValues();
			  imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
			  imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
			  contactData.add(imRow);
			}
			intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
			startActivityForResult(intent, Constants.CONTACTS_MANAGER_REQUEST_CODE);
		}
	}
	
	private class CancelButtonListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			setResult(Activity.RESULT_CANCELED, new Intent());
			finish();
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts_manager);
		
		showHideButton = (Button)findViewById(R.id.show_hide);
		showHideButton.setOnClickListener(showHideButtonListener);
		saveButton = (Button)findViewById(R.id.save);
		saveButton.setOnClickListener(saveButtonListener);
		cancelButton = (Button)findViewById(R.id.cancel);
		cancelButton.setOnClickListener(cancelButtonListener);
		
		container2 = (LinearLayout)findViewById(R.id.container2);
		
		nameText = (EditText)findViewById(R.id.name);
		phoneText = (EditText)findViewById(R.id.phone);
		emailText = (EditText)findViewById(R.id.email);
		addressText = (EditText)findViewById(R.id.address);
		jobText = (EditText)findViewById(R.id.job);
		companyText = (EditText)findViewById(R.id.company);
		websiteText = (EditText)findViewById(R.id.website);
		imText = (EditText)findViewById(R.id.im);
		
		Intent intent = getIntent();
		if (intent != null) {
			String phone = intent.getStringExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY");
			if (phone != null) {
				phoneText.setText(phone);
			} else {
				Toast.makeText(this, getResources().getString(R.string.phone_number_error), Toast.LENGTH_LONG).show();
			}
		} 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contacts_manager, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
