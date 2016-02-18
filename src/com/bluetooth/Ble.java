package com.bluetooth;


import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;

public class Ble extends Activity{
	
	public long recvBytes;        // 当前接收的字节数
	public long sendBytes;        // 当前发送的字节数
	public StringBuilder mData;   
	public BluetoothUtils mBluetoothUtils;
	public Context mcontext;
	public  int count;

	    public Ble(Context context,Handler mHandler){
	    	mcontext=context;
	    	  mBluetoothUtils = new BluetoothUtils(mcontext, mHandler);
		        mBluetoothUtils.initialize();
		        mData = new StringBuilder();

		        recvBytes = 0;
		        sendBytes = 0;
	    }
   
	    public void sendCmd(String cmd) {
		   
	            byte[] buf = mBluetoothUtils.stringToBytes(cmd);
	            sendBytes += buf.length;
	            mBluetoothUtils.sendToBuf(buf, buf.length);
	            //mBluetoothUtils.writeData(buf);
	            Log.e("kea", cmd+"!0");
	        }
	    
	    public void sendCmd(String cmd,int count) {
			   this.count=count;
            byte[] buf = mBluetoothUtils.stringToBytes(cmd);
            sendBytes += buf.length;
            mBluetoothUtils.sendToBuf(buf, buf.length);
            //mBluetoothUtils.writeData(buf);
            Log.e("kea", cmd+" #send"+count);
        }
	 public int  getSendCount(){
		   return count;
	   }
	/**
	 *  获取设备返回到的指令
	 * @return
	 */
	    public String revData() {
	    	mData=new StringBuilder();
	    	int len = mBluetoothUtils.getDataLen();
	    	recvBytes += len;
	    	byte[] buf = new byte[len];
	    	
	    	mBluetoothUtils.getData(buf, len);	    	
	            String s = mBluetoothUtils.bytesToString(buf);
	            mData.append(s);	
	         //   Log.e("kea", mData.toString().replace(" ","")+" #rev"+count);
	      
	            return mData.toString().replace(" ","");
	    }
	    /**
	     * 根据不同设备名设置不同uuid
	     * @param device
	     */
	 public void connect(BluetoothDevice device){
		// Log.i("aaa", device.getName()+" ble con");
		 if(device.getName().contains("machine1")){
			 setUUid( "0000ff00-0000-1000-8000-00805f9b34fb", "0000ff01-0000-1000-8000-00805f9b34fb",  "0000ff02-0000-1000-8000-00805f9b34fb");
		 }
		 else
			 setUUid("e7810a71-73ae-499d-8c15-faa9aef0c3f2", "bef8d6c9-9c21-4c9e-b632-bd58c1009f9f"); 
				 
		 mBluetoothUtils.connect(device);
	 }
	 public boolean isConnected(BluetoothDevice device){
		 return mBluetoothUtils.isConnected(device);
	 }
	 public void scanBleDevice(Boolean f){
		 mBluetoothUtils.scanBleDevice(f);
	 	}
	 public  ArrayList<BleBean> getDevices(){
		 
		 return  mBluetoothUtils.mLeDevices;
	 }
	/**
	 * 
	 * @param serviceUuid0
	 * @param characterUuid0 该uuid要可读可写可通知才行
	 */
	 public void setUUid(String serviceUuid0,String characterUuid0){
		 BluetoothUtils.serviceUuid=serviceUuid0;
		 BluetoothUtils.characterUuid=characterUuid0;  
		 BluetoothUtils.characterUuidNotice=characterUuid0;
     }
	 /**
	  * 若不知道这几个值，可下载BleTool连接后获取
	  * @param serviceUuid0 
	  * @param characterUuid0
	  * @param characterUuidNotice0   该uuid要可通知才行
	  */
	 public void setUUid(String serviceUuid0,String characterUuid0,String characterUuidNotice0){
		 BluetoothUtils.serviceUuid=serviceUuid0;
		 BluetoothUtils.characterUuid=characterUuid0; 
		 BluetoothUtils.characterUuidNotice=characterUuidNotice0;
     }
	 public void checkBluetoothEnabled(){
		  mBluetoothUtils.checkBluetoothEnabled();
	 }
	 public void stopGatt(){
		 mBluetoothUtils.checkDeviceScanning();
	        mBluetoothUtils.checkGattConnected();
	 }
	 public BluetoothUtils getBleUtils(){
		 return mBluetoothUtils;
	 }
	 
	 /* @Override
	    protected void onResume() {
	        super.onResume();
	        mBluetoothUtils.checkBluetoothEnabled();
	    }
	 		@Override
	    protected void onStop() {
	        super.onStop();
	        mBluetoothUtils.checkDeviceScanning();
	        mBluetoothUtils.checkGattConnected();
	        if (timer != null) {
	            isTimerEnable = false;
	        }
	    }*/
	 	
	 		/**
	 		 * 判断是否支持BLE
	 		 * @param context
	 		 * @return
	 		 */
	 		@SuppressLint("InlinedApi")
			public static boolean isSurpportedBle(Context context){
	 			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2)
	 				return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
	 			else {
	 				return false;
	 			}
	 		}	
	 		
	
	    }

