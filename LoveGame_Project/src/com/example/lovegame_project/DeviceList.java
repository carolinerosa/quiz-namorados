package com.example.lovegame_project;

import java.util.ArrayList;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat.Action;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class DeviceList extends Activity {

	private static final String TAG = "DEVICE LIST";
	private static final int ENABLE_BT_REQUEST = 1;
	private static final int HANDLER_DEVICES_THREAD = 1;

	private boolean canUpdate = true;

	private BluetoothAdapter btAdapter;

	private ListView pairedDevicesList;
	private ListView newDevicesList;

	public ListView getNewDevicesList()
	{
		return this.newDevicesList;
	}
	public ListView getPairedDevicesList()
	{
		return this.pairedDevicesList;
	}

	private ArrayList<String> pairedDevicesData;
	private ArrayList<String> newDevicesData;

	private ArrayList<BluetoothDevice> pairedDevices;
	private ArrayList<BluetoothDevice> newDevices;

	private boolean IsBroadcastReceiverRunning = false;

	private BroadcastReceiver mBroadcastReceiver;
	private Handler handler;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Screen adjustments
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		setContentView(R.layout.activity_device_list);
		MinhasCoisas.setCurrentActivity(this);

		btAdapter = BluetoothAdapter.getDefaultAdapter();

		this.pairedDevicesList = (ListView)findViewById(R.id.paired_devices);
		this.newDevicesList = (ListView)findViewById(R.id.new_devices);

		this.pairedDevicesData = new ArrayList<String>();
		this.newDevicesData = new ArrayList<String>();

		pairedDevices = new ArrayList<BluetoothDevice>();  
		newDevices = new ArrayList<BluetoothDevice>();

		Intent discoverableIntent = new
				Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 60);

		startActivity(discoverableIntent);

		BluetoothConnectionManager btManager = new BluetoothConnectionManager(getApplicationContext()); 

		// Select device on Paired List View
		this.pairedDevicesList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				MinhasCoisas.Show("Aguarde para entrar no chat.");
				Cliente cliente = new Cliente(pairedDevices.get(position), getApplicationContext(), true);
				MinhasCoisas.setCliente(cliente);

			}
		});

		// Select device on New List View
		this.newDevicesList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				MinhasCoisas.Show("Aguarde para começar o jogo.");
				Cliente cliente = new Cliente(newDevices.get(position), getApplicationContext(), true);
				MinhasCoisas.setCliente(cliente);


			}
		});

		mBroadcastReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();

				if (BluetoothDevice.ACTION_FOUND.equals(action)) {
					// Desliga a list view para a atualizar
					((DeviceList)MinhasCoisas.getCurrentActivity()).getNewDevicesList().setEnabled(false);
					((DeviceList)MinhasCoisas.getCurrentActivity()).canUpdate = false;
					// Atualiza
					BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
					newDevicesData.add(device.getName() + "\n" + device.getAddress());
					newDevices.add(device);
					Log.i("Bluetooth Scan", "+1 dispositivo pareado");
					// Religa a list view
					((DeviceList)MinhasCoisas.getCurrentActivity()).canUpdate = true;
					((DeviceList)MinhasCoisas.getCurrentActivity()).getNewDevicesList().setEnabled(true);

				}
			}

		};

		this.handler = new Handler()
		{
			@Override
			public void handleMessage(Message msg) {

				if(msg.what == HANDLER_DEVICES_THREAD)
				{
					((DeviceList) MinhasCoisas.getCurrentActivity()).AtualizarNewDevicesList();
				}

				super.handleMessage(msg);
			}
		};

	}

	public void AtualizarNewDevicesList()
	{
		if(this.canUpdate){
			this.newDevicesList.setAdapter(new ArrayAdapter<String>(this, R.layout.list, this.newDevicesData));
		}
		Log.i(TAG, "Atualizou a lista de device");
	}

	public void onClick_Scan(View v)
	{
		Scan();
	}
	private void Scan()
	{
		this.pairedDevicesData.clear();

		if(!btAdapter.isEnabled()){

			MinhasCoisas.Show("Por favor, ligue o bluetooth");
			Intent enableBt = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBt, REQUEST.REQUEST_ENABLE_BT);
		}else{
			this.btAdapter.startDiscovery();

			// +++ Dispositivos escaneados +++
			IntentFilter foundDevices = new IntentFilter(BluetoothDevice.ACTION_FOUND);
			registerReceiver(mBroadcastReceiver, foundDevices); // Não esquecer de desregistrar antes de destruir

			// +++ Dispositivos já pareados +++
			Set<BluetoothDevice> alreadyDevices = btAdapter.getBondedDevices();
			if (alreadyDevices.size() > 0) {

				for (BluetoothDevice device : alreadyDevices) {
					pairedDevicesData.add(device.getName() + "\n" + device.getAddress());
					pairedDevices.add(device);

				}
			}
			MinhasCoisas.Show( String.valueOf(pairedDevicesData.size()));

			pairedDevicesList.setAdapter(new ArrayAdapter<String>(this, R.layout.list , this.pairedDevicesData));

			//MinhasCoisas.Show("terminou");

			this.IsBroadcastReceiverRunning = true;

			this.newDevicesList.setAdapter(new ArrayAdapter<String>(this, R.layout.list, this.newDevicesData));

			Thread thread = new Thread(new Runnable()
			{
				@Override
				public void run() {
					float cronometro = 0;
					float searchTime = 10;
					float updateInterval = 2;
					float updateTime = updateInterval;
					while(cronometro <= searchTime)
					{
						cronometro += TimeManager.getInstance().getDeltaTime()/1000;

						Log.i(TAG, String.valueOf(cronometro));

						if(cronometro >= updateTime)
						{
							updateTime += updateInterval;
							Message msg = new Message();
							msg.what = HANDLER_DEVICES_THREAD;
							handler.sendMessage(msg);
						}
					}


				}
			});
			thread.start();

		}
	}

	private void verificarEstadoBluetooth(){
		//	
		//	    Bluetooth State
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

	@Override
	public void onDestroy()
	{
		super.onDestroy();

		this.btAdapter.disable();

		if(btAdapter.isDiscovering())
			btAdapter.cancelDiscovery();

		if(this.IsBroadcastReceiverRunning)
			unregisterReceiver(mBroadcastReceiver);

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.device_list, menu);
		return true;
	}

}

