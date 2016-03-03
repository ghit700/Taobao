package com.wu.taobao.manager;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetManager {

	private ConnectivityManager mConnectivityManager;
	private NetworkInfo mNetworkInfo;

	public NetManager(Context mContext) {
		super();
		mConnectivityManager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
	}

	public boolean hasNetworkConnection() {
		boolean connected = (null != mNetworkInfo)
				&& mNetworkInfo.isConnected();

		if (!connected) {
			return false;
		}

//		boolean routeExits;
//		try {
//			InetAddress host = InetAddress.getByName("8.8.8.8");
//			Socket socket = new Socket();
//			socket.connect(new InetSocketAddress(host, 53), 5000);
//			routeExits = true;
//			socket.close();
//		} catch (Exception e) {
//			routeExits = false;
//		}

		return (connected );
	}

	public boolean isWifiReachable() {
		
		if (mNetworkInfo == null) {
			return false;
		}
		
		return (mNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI);
	}
}
