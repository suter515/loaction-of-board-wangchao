package com.qt.solarpanelslos.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * @ClassName: PhoneUtil
 * @Description: PhoneUtil工具类
 * @author gyg
 * @date 2015-5-29 下午14:45:01
 */
public class PhoneUtils {

	public static int getScreenWidth(Context context) {
		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getWidth();
	}

	public static int getScreenHeight(Context context) {
		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getHeight();
	}

	public static float getScreenDensity(Context context) {
		try {
			DisplayMetrics dm = new DisplayMetrics();
			WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			manager.getDefaultDisplay().getMetrics(dm);
			return dm.density;
		} catch (Exception ex) {

		}
		return 1.0f;
	}

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager mgr = (ConnectivityManager) context.getApplicationContext().getSystemService(
				Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] info = mgr.getAllNetworkInfo();
		if (info != null) {
			for (int i = 0; i < info.length; i++) {
				if (info[i].getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * 获取系统音量是否开启
	 */
	public static boolean isSysVoiceOn(Context context) {
		AudioManager mAudioManager = (AudioManager) context.getApplicationContext().getSystemService(
				Context.AUDIO_SERVICE);
		if (mAudioManager.getStreamVolume(AudioManager.STREAM_SYSTEM) == 0) {
			return false;
		} else if (mAudioManager.getStreamVolume(AudioManager.STREAM_RING) == 0) {
			return false;
		} else if (mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC) == 0) {
			return false;
		} else if (mAudioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL) == 0) {
			return false;
		} else if (mAudioManager.getStreamVolume(AudioManager.STREAM_ALARM) == 0) {
			return false;
		} else if (mAudioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION) == 0) {
			return false;
		} else if (mAudioManager.getStreamVolume(AudioManager.STREAM_DTMF) == 0) {
			return false;
		}
		return true;
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static String ping(String cmd) {
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			BufferedReader breader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			do {
				String content = breader.readLine();
				if (content == null) {
					breader.close();
					break;
				}
				return content;
			} while (true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 在SD卡上创建一个文件夹
	public void createSDCardDir() {
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			// 创建一个文件夹对象，赋值为外部存储器的目录
			File sdcardDir = Environment.getExternalStorageDirectory();
			// 得到一个路径，内容是sdcard的文件夹路径和名字
			String path = sdcardDir.getPath() + "/cardImages";
			File path1 = new File(path);
			if (!path1.exists()) {
				// 若不存在，创建目录，可以在应用启动的时候创建
				path1.mkdirs();
			}
		} else {
			return;

		}

	}
}
