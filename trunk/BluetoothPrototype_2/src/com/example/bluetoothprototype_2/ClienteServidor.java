package com.example.bluetoothprototype_2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class ClienteServidor implements Runnable{

	private String TAG = "Cliente Servidor";
	
	private BluetoothSocket mSocket;
	private UUID uuid;
	
	private InputStream mInputStream;
	private OutputStream mOutputStream;
	
	private boolean connected = false;
	
	
	public ClienteServidor(BluetoothSocket socket) {
		
		uuid = UUID.fromString("b4c5bcd0-a9ec-11e2-9e96-0800200c9a66");
		
		this.mSocket = socket;
		
		Thread thread = new Thread(this);
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();
	}

	@Override
	public void run() {
		
        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
 
		try{
			
			this.mInputStream = mSocket.getInputStream();
			this.mOutputStream = mSocket.getOutputStream();
			Log.i(TAG, "pegar as InStream e OutStream");
			
		}catch(IOException e)
		{
			Log.i(TAG, "Erro ao tentar pegar as InStream e OutStream");
		}
		
		while(connected)
		{
			try {
                
				int avaliableBytes = mInputStream.available();
				byte[] bytes = new byte[avaliableBytes];
				mInputStream.read(bytes, 0, avaliableBytes);
				
				
				StringBuilder builder = new StringBuilder();
				for(int i = 0; i <= bytes.length; i++)
				{
					builder.append(bytes[i]);
				}
				
                Log.i(TAG, builder.toString());
                // work with the message
                
                
            } catch (IOException e) {
                break;
            }
		}
	}
	
	 public void cancel() {
        try {
            mSocket.close();
        } catch (IOException e) { }
     }
	 
	 
     public void write(byte[] bytes) {
         try {
             mOutputStream.write(bytes);
         } catch (IOException e)
         {
        	 Log.i("Cliente", "Erro ao tentar enviar mensagem");
         }
     }

}
