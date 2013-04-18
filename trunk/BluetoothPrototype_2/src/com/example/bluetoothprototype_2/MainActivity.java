package com.example.bluetoothprototype_2;

import java.util.ArrayList;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {

	BluetoothAdapter btAdapter;
	
	private final int REQUEST_ENABLE_BT = 1; 
	Button activateButton;
	Button desactivateButton;
	ArrayList<String> devices;
	ArrayAdapter<String> mArrayAdapter;
	boolean hasBluetoothRadio = false;
	private BroadcastReceiver mBroadcastReceiver;
	private BroadcastReceiver bluetoothState;
	private Spinner devicesList;
	private void ShowMessage(String text)
	{
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("Activity", "Start");
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(com.example.bluetoothprototype_2.R.layout.activity_main);
		this.btAdapter = BluetoothAdapter.getDefaultAdapter();
		this.devices = new ArrayList<String>();
		this.devicesList = (Spinner) findViewById(R.id.btDevicesSpinner);
		this.devicesList.setBackgroundColor(Color.BLACK);
		
		activateButton = (Button) findViewById(R.id.activateBluetooth);
		
		if(this.btAdapter.isEnabled())
		{
			activateButton.setText("Scan");
		}else
		{			
			activateButton.setText("Ligar Bluetooth");
		}
		desactivateButton = (Button) findViewById(R.id.desactivateBluetooth);
		desactivateButton.setText("Desligar Bluetooth");
		
	}
	
	private void Scan()
	{
		this.btAdapter.startDiscovery();
		this.devices.clear();	
		Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
		
		if (pairedDevices.size() > 0) {
		    
		    for (BluetoothDevice device : pairedDevices) {
		    	
		        devices.add(device.getName() + "\n" + device.getAddress());
		    }
		}
		
		
		
		mBroadcastReceiver = new BroadcastReceiver() {
			 
			@Override
		    public void onReceive(Context context, Intent intent) {
		        String action = intent.getAction();
		       
		        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
		            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		            devices.add(device.getName() + "\n" + device.getAddress()); 
		            Log.i("Bluetooth Scan", "+1 dispositivo pareado");
		            
		        }
		    }
		};
		
		IntentFilter foundDevices = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(mBroadcastReceiver, foundDevices); // Don't forget to unregister during onDestroy
		Log.i("devices pegados", "" + this.devices.size());
		this.mArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_single_choice, this.devices);
		this.devicesList.setAdapter(this.mArrayAdapter);
		
		
	}
	private void verificarEstadoBluetooth()
	{
	    // Bluetooth State
//		bluetoothState = new BroadcastReceiver()
//		{
//			@Override
//			public void onReceive(Context context, Intent intent) {
//				String action = intent.getAction();
//				if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(action))
//				{
//					int btState = intent.getParcelableExtra(BluetoothAdapter.EXTRA_STATE);
//					switch(btState)
//					{
//					case BluetoothAdapter.STATE_CONNECTING:
//						//ShowMessage("conectando");
//						Log.i("Bluetooth Prototype", "Connecting");
//						break;
//					case BluetoothAdapter.STATE_CONNECTED:
//						//ShowMessage("Conectado");
//						Log.i("Bluetooth Prototype", "Connected");
//						break;
//						
//					case BluetoothAdapter.STATE_TURNING_ON:
//						//ShowMessage("Conectando...");
//						
//						Log.i("Bluetooth Prototype", "Turning Bluetooth on");
//						break;
//					case BluetoothAdapter.STATE_ON:
//						//ShowMessage("Conectado");
//						Log.i("Bluetooth Prototype", "Bluetooth in on");
//						break;
//					case BluetoothAdapter.STATE_TURNING_OFF:
//						//ShowMessage("Desconectando");
//						Log.i("Bluetooth Prototype", "Turning Bluetooth off");
//						break;
//					case BluetoothAdapter.STATE_OFF:
//						//ShowMessage("Desconectado");
//						Log.i("Bluetooth Prototype", "Bluetooth is off");
//						break;
//					}
//				}
//				
//			}
//			
//			
//		};
//		
//		IntentFilter bluetoothStateFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
//		registerReceiver(bluetoothState, bluetoothStateFilter); // Don't forget to unregister during onDestroy
	}
	
	public void onClick_ActivateBluetooth(View v)
	{
		this.btAdapter = BluetoothAdapter.getDefaultAdapter();
		if(this.btAdapter != null)
		{
			this.hasBluetoothRadio = true;
			//this.mArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), com.example.bluetoothprototype_2.R.id.btDevicesSpinner, this.devices );
			
			ShowMessage("Parabens! Seu telefone possui Bluetooth!");
			if (!this.btAdapter.isEnabled()) {
			    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
			    this.activateButton.setText("Scan");
			}else{
			Scan();
			}
		}
		else
		{
			ShowMessage("Este telefone não tem bluetooth");
		}
	}
	public void onClick_DesactivateBluetooth(View v)
	{	
		this.btAdapter.cancelDiscovery();
		this.btAdapter.disable();
	}
	
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK)
		{
			if(requestCode != this.REQUEST_ENABLE_BT)
			{
				//this.finish();
				//return;
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(com.example.bluetoothprototype_2.R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onDestroy()
	{
		unregisterReceiver(this.mBroadcastReceiver);
		unregisterReceiver(this.bluetoothState);
	}
}
