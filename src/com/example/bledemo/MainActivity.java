package com.example.bledemo;

import java.util.ArrayList;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.bluetooth.Ble;
import com.bluetooth.BleBean;
import com.bluetooth.BluetoothUtils;

public class MainActivity extends Activity {
	private Ble ble;
	private BluetoothAdapter mBtAdapter=BluetoothAdapter.getDefaultAdapter();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 if(Ble.isSurpportedBle(this))
	    	 {
			 ble=new Ble(this,mHandler);	    	
		 
		new Thread(new Runnable() {			
			@Override
			public void run() {
				ble.scanBleDevice(true);//开始扫描ble设备，若要结合蓝牙spp连接方式，可以启动时若在主线程执行该耗时任务可能会使程序卡顿黑屏								
			}
		}).start(); 
		//ble.sendCmd(" ",1);发送指令，注意Ble类里修改uuid
	    	 }
	}

	public void BleDiscovery(){
		if(!mBtAdapter.isEnabled())
			openBlueteeh();
	
		ArrayList<BleBean> devices=ble.getDevices();
		
		/*for (BleBean bluetoothDevice : devices) {	
		  LaundryMachine machine = new LaundryMachine();
		  machine.setDevice(bluetoothDevice.getDevice());
		  machine.setRssi((short)bluetoothDevice.getRssi());
		
		 addDevice(machine);
		 LogInfo("hhh", bluetoothDevice.getDevice()+"@"+bluetoothDevice.getRssi());
		}*/
	}	
	
	private void openBlueteeh() {
		try {
			mBtAdapter.enable();
			
		} catch (Exception e) {			
			    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			    startActivityForResult(enableBtIntent, 22);	
			  
		}
		
		
	}  
	//在这里写各个动作
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case BluetoothUtils.ENABLE_BLUETOOTH:
				/*Intent intent = new Intent(
						BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(intent, 1);*/
				break;

			case BluetoothUtils.DEVICE_SCAN_STARTED:

				break;

			case BluetoothUtils.DEVICE_SCAN_STOPPED:				
				break;
			case BluetoothUtils.DEVICE_SCAN_COMPLETED:
				BleDiscovery();
				break;
			case BluetoothUtils.DEVICE_CONNECTED:
				break;
			case BluetoothUtils.DEVICE_CONNECTING:

				break;
			case BluetoothUtils.DEVICE_DISCONNECTED:

				break;

			case BluetoothUtils.DATA_SENDED:

				break;
			case BluetoothUtils.DATA_READED:

				break;

			case BluetoothUtils.CHARACTERISTIC_ACCESSIBLE:

				break;

			default:
				break;
			}
		}
	};
	
	@Override
	public void onStop() {
    super.onStop();
  if(ble!=null) ble.stopGatt();  
}
}
