package com.cpm.upload;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.cpm.Constants.CommonString;
import com.cpm.database.pillsbury_Database;
import com.cpm.delegates.CoverageBean;
import com.cpm.delegates.GeotaggingBeans;
import com.cpm.delegates.StoreBean;
import com.cpm.message.AlertMessage;
import com.cpm.pillsbury.MainMenuActivity;
import com.cpm.pillsbury.R;

public class UploadOptionActivity extends Activity {

	private Intent intent;
	private String date;
	private SharedPreferences preferences;
	private static pillsbury_Database database;
	ArrayList<CoverageBean> cdata = new ArrayList<CoverageBean>();
	ArrayList<GeotaggingBeans> gdata = new ArrayList<GeotaggingBeans>();
	StoreBean storestatus = new StoreBean();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload_option);

		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		date = preferences.getString(CommonString.KEY_DATE, null);

		database = new pillsbury_Database(this);
		database.open();

	}

	public void onButtonClick(View v) {
		switch (v.getId()) {
		case R.id.upload_data:

			cdata = database.getCoverageData(date, null);
			gdata = database.getGeotaggingData("Y");

			if (cdata.size() == 0 && gdata.size() == 0) {

				Toast.makeText(getBaseContext(), AlertMessage.MESSAGE_NO_DATA,
						Toast.LENGTH_LONG).show();

			} else {

				if ((validate_data()) || gdata.size() > 0) {

					Intent i = new Intent(UploadOptionActivity.this,
							CopyOfUploadDataActivity.class);
					i.putExtra("UploadAll", false);
					startActivity(i);
					UploadOptionActivity.this.finish();
				}

				else {
					Toast.makeText(getBaseContext(),
							AlertMessage.MESSAGE_NO_DATA, Toast.LENGTH_LONG)
							.show();
				}

			}

			break;
		case R.id.upload_image:

			cdata = database.getCoverageData(date, null);
			gdata = database.getGeotaggingData("D");

			if (cdata.size() == 0 && gdata.size() == 0) {

				Toast.makeText(getBaseContext(), AlertMessage.MESSAGE_NO_IMAGE,
						Toast.LENGTH_LONG).show();

			}

			else {

				if (validate() || ValidateGeoTagImage() == 1) {

					intent = new Intent(this, UploadImageActivity.class);
					intent.putExtra("UploadAll", false);
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(getBaseContext(),
							AlertMessage.MESSAGE_DATA_FIRST, Toast.LENGTH_LONG)
							.show();
				}
			}
			break;

		}
	}

	private int ValidateGeoTagImage() {

		int result = 0;

		gdata = database.getGeotaggingData(CommonString.KEY_D);

		if (gdata.size() > 0) {

			result = 1;
		}
		return result;
	}

	public boolean validate_data() {
		boolean result = false;

		database.open();
		cdata = database.getCoverageData(date, null);

		for (int i = 0; i < cdata.size(); i++) {

			storestatus = database.getStoreStatus(cdata.get(i).getStoreId());

			if (!storestatus.getStatus().equalsIgnoreCase(CommonString.KEY_U)) {
				if ((storestatus.getCheckout_status().equalsIgnoreCase(
						CommonString.KEY_C)
						|| storestatus.getStatus().equalsIgnoreCase(
								CommonString.KEY_D) || storestatus.getStatus()
						.equalsIgnoreCase(CommonString.STORE_STATUS_LEAVE))) {
					result = true;
					break;

				}
			}
		}

		return result;
	}

	public boolean validate() {
		boolean result = false;

		database.open();
		cdata = database.getCoverageData(date, null);

		for (int i = 0; i < cdata.size(); i++) {

			if (cdata.get(i).getStatus().equalsIgnoreCase(CommonString.KEY_D)) {
				result = true;
				break;

			}

		}

		return result;
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		Intent i = new Intent(this, MainMenuActivity.class);
		startActivity(i);
		UploadOptionActivity.this.finish();
	}

}
