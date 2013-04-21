package com.example.bluetoothprototype_2;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private BluetoothAdapter mBT;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("Activity", "Start");
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_main);
		
		mBT = BluetoothAdapter.getDefaultAdapter();
		if(mBT != null){
			MeuToast.Show(getApplicationContext(), "Este telefone possui tecnologia Bluetooth");
			if(mBT.isEnabled())
			{
				Button b = (Button) findViewById(R.id.activateBtButton);
				b.setText(R.string.turn_bt_off);
			}
			
		}else
		{
			MeuToast.Show(getApplicationContext(), "Este telefone não possui tecnologia Bluetooth");
			finish();
		}
		
		
		
	}
	
	public void onClick_ActivateBluetooth(View v)
	{
		
		if (!mBT.isEnabled()) {
		    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    startActivityForResult(enableBtIntent, REQUEST.REQUEST_ENABLE_BT);
		}else
		{
			mBT.disable();
			Button b = (Button) findViewById(R.id.activateBtButton);
			b.setText(R.string.turn_bt_on);
			
			MeuToast.Show(getApplicationContext(),"Bluetooth desligado");
		}
		
	}
	
	public void onClick_leave(View v)
	{
		this.finish();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			if(REQUEST.REQUEST_ENABLE_BT == requestCode)
			{
				MeuToast.Show(getApplicationContext(),"Bluetooth ligado");
				Button b = (Button) findViewById(R.id.activateBtButton);
				b.setText(R.string.turn_bt_off);
			}
		}else
		{
			MeuToast.Show(getApplicationContext(),"erro ao tentar ligar Bluetooth");
		}
	}
	
	public void onClick_Scan(View v)
	{
		Intent scanIntent = new Intent(this, DeviceList.class);
		startActivity(scanIntent);
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
		if(mBT.isEnabled())
		this.mBT.disable();
		
	}
}
