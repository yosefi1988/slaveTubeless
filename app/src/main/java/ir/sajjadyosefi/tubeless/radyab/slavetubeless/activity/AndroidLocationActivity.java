package ir.sajjadyosefi.tubeless.radyab.slavetubeless.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Timer;

import ir.sajjadyosefi.tubeless.radyab.slavetubeless.R;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.AppLocationService;

public class AndroidLocationActivity extends Activity {

	Button btnGPSShowLocation;
	Button btnNWShowLocation;

	AppLocationService appLocationService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_location);
		appLocationService = new AppLocationService(AndroidLocationActivity.this);

		btnGPSShowLocation = (Button) findViewById(R.id.btnGPSShowLocation);
		btnGPSShowLocation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {

				Location gpsLocation = appLocationService .getLocation(LocationManager.GPS_PROVIDER);

				if (gpsLocation != null) {
					double latitude = gpsLocation.getLatitude();
					double longitude = gpsLocation.getLongitude();
					Toast.makeText(
							getApplicationContext(),
							"Mobile Location (GPS): \nLatitude: " + latitude
									+ "\nLongitude: " + longitude,
							Toast.LENGTH_LONG).show();
				} else {
					showSettingsAlert("GPS");
				}

			}
		});

		btnNWShowLocation = (Button) findViewById(R.id.btnNWShowLocation);
		btnNWShowLocation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

					//return true;
				}else {
					Location nwLocation = appLocationService
							.getLocation(LocationManager.NETWORK_PROVIDER);

					if (nwLocation != null) {
						double latitude = nwLocation.getLatitude();
						double longitude = nwLocation.getLongitude();
						Toast.makeText(
								getApplicationContext(),
								"Mobile Location (NW): \nLatitude: " + latitude
										+ "\nLongitude: " + longitude,
								Toast.LENGTH_LONG).show();
					} else {
						showSettingsAlert("NETWORK");
					}
				}
			}
		});



		test1();
	}

	private void test1() {


	}


	public void showSettingsAlert(String provider) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				AndroidLocationActivity.this);

		alertDialog.setTitle(provider + " SETTINGS");

		alertDialog
				.setMessage(provider + " is not enabled! Want to go to settings menu?");

		alertDialog.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						AndroidLocationActivity.this.startActivity(intent);
					}
				});

		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		alertDialog.show();
	}

	private void showAlert() {
		final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("Enable Location")
				.setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
						"use this app")
				.setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface paramDialogInterface, int paramInt) {
						Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						startActivity(myIntent);
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface paramDialogInterface, int paramInt) {
					}
				});
		dialog.show();
	}

}
