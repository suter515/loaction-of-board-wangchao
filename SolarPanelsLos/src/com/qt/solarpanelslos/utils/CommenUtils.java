package com.qt.solarpanelslos.utils;


import android.content.Context;
import android.content.DialogInterface;

import com.qt.solarpanelslos.view.CustomDialog;

/**
 * Created by chenleicpp on 2015/5/5.
 */
public class CommenUtils {

    public static String TAG = "FragmentBase";

    public static String ACTION_REPORT = "action_report";
    public static String ACTION_DISPLAY = "action_display";

    public static void exit(final Context context) {
        CustomDialog.showDialog(context, null, false, "是否退出软件？", "确定",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				AppManagerUtils.getAppManager().AppExit(context);
			}
		}, "取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0 || cs.equals("null")) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !CommenUtils.isBlank(cs);
    }
}
