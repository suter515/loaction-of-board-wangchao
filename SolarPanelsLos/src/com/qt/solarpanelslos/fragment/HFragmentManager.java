package com.qt.solarpanelslos.fragment;

import java.util.Iterator;
import java.util.Stack;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;

import com.qt.solarpanelslos.utils.CommenUtils;





/**
 * 自定义Fragment管理类
 * 
 */
public class HFragmentManager implements Cloneable {
	private static final boolean D = true;

	private FragmentManager  mMgr = null;
	private static Stack<String> mFragmentStack = null; // 管理用堆栈，根据Fragment的tag，tag为Null时默认为类名
	private int mContainerViewId = 0;
	private String mRootTag = null;

	private static HFragmentManager _instance = null;
	private static Context mContext;

	/**
	 * 单例模式
	 * 
	 * @param activity
	 * @param containerViewId
	 * @param root
	 * @return
	 */
	public static HFragmentManager instance(final FragmentActivity activity, final int containerViewId, String root) {
		if (_instance == null) {
			LogI("FM is null");
			_instance = new HFragmentManager(activity, containerViewId, root);
			mContext = activity.getApplicationContext();
		}
		return _instance;
	}

	public static HFragmentManager getInstance() {
		return _instance;
	}

	public static void setInstance() {
		_instance = null;
	}

	/**
	 * 
	 * @param activity
	 * @param containerViewId
	 * @param root
	 *            如果为Null,
	 */
	private HFragmentManager(final FragmentActivity activity, final int containerViewId, String root) {
		mMgr = activity.getSupportFragmentManager();
		mContainerViewId = containerViewId;
		mRootTag = root;
		mFragmentStack = new Stack<String>();
		mFragmentStack.clear();
	}

	/**
	 * 显示指定Fragment
	 * 
	 * @param fragment
	 */
	public synchronized void display(Fragment fragment) {
		String tag = fragment.getClass().getSimpleName();

		display(fragment, tag, true);
	}

	/**
	 * 显示指定Fragment
	 * 
	 * @param fragment
	 */
	public synchronized void display(Fragment fragment, boolean restore) {
		String tag = fragment.getClass().getSimpleName();

		display(fragment, tag, restore);
	}

	/**
	 * 显示指定Fragment
	 * 
	 * @param fragment
	 * @param tag
	 * @param restore
	 *            true:重新add，保证fragment "刷新";反之false
	 */
	public synchronized void display(Fragment fragment, String tag, boolean restore) {
		if (TextUtils.isEmpty(tag))
			tag = fragment.getClass().getSimpleName();
		FragmentTransaction ft = mMgr.beginTransaction();
		ft.replace(mContainerViewId, fragment, tag);
		sendBroadcast(tag);
		ft.addToBackStack(null);
		ft.commit();
		// ft.commitAllowingStateLoss();
		//LogI("FM display -> " + tag + " stack size = " + this.mFragmentStack.size());
		LogI("FM display -> " + tag );
	}

	private void sendBroadcast(String tag) {
		String name = null;
		int size = mFragmentStack.size();
		if (tag.equals("QuestionDetailFragment") && size - 2 >= 0) {
			name = mFragmentStack.get(size - 2);
			if (!(name.endsWith("ExpertRecoveryFragment") || name.equals("MyQuestionFragment"))) {
				name = null;
			}
		} else {
			name = tag;
		}
		Intent intent = new Intent(CommenUtils.ACTION_DISPLAY);
		intent.putExtra("name", name);
		mContext.sendBroadcast(intent);
	}

	/**
	 * 返回
	 * 
	 * @return true 返回操作完成 false 已没有可返回的fragment
	 */
	public synchronized String popBackStack() {
		if (mFragmentStack != null && mFragmentStack.size() >= 2 && !isRoot()) {
			hidden(mFragmentStack.lastElement());
			int size = mFragmentStack.size();
			String back = mFragmentStack.get(size - 1);
			LogI("FM pop back -> " + back);
			sendBroadcast(back);
			display(back);
			return back;
		} else {
			LogI("FM stack no others");
			return null;
		}
	}
	
	public synchronized String popBackWithNoStack() {
		return null;
	}

	/**
	 * 清除除当前以外的fragment
	 */
	public synchronized void clearExceptCurrent() {
		if (mFragmentStack == null || mFragmentStack.isEmpty() || mFragmentStack.size() <= 1)
			return;

		FragmentTransaction ft = mMgr.beginTransaction();
		int size = mFragmentStack.size();// 管理栈中的个数必须固定，否则删除的时候会有变化
		for (int i = 0; i < size - 1; i++) { // 保留新增元素，故此mFragmentStack.size()-1
			String s = mFragmentStack.remove(0);// remove第0个元素，而不是第i个元素，因为i在最上层
			LogI("clear > " + s);
			Fragment fragment = mMgr.findFragmentByTag(s);
			if (fragment != null)
				ft.detach(fragment);
			fragment = null;
		}
		ft.commitAllowingStateLoss();
		LogI("FM clearExceptcurrent stack size = " + mFragmentStack.size());
	}

	/**
	 * 取得缓存堆栈
	 * 
	 * @return
	 */
	public final Stack<String> getCacheStack() {
		return mFragmentStack;
	}

	/**
	 * 重置缓存堆栈
	 * 
	 * @return
	 */
	public void setCacheStack() {
		mFragmentStack = new Stack<String>();
	}

	/**
	 * 当前fragment的tag
	 * 
	 * @return
	 */
	public String currentFragmentTag() {
		return (mFragmentStack == null || mFragmentStack.isEmpty()) ? null : mFragmentStack.lastElement();
	}

	/**
	 * 默认根节点tag，相当于主fragment的tag
	 * 
	 * @return
	 */
	public String rootDefTag() {
		return this.mRootTag;
	}

	/**
	 * 释放资源
	 */
	public synchronized void recycle() {
		if (mMgr != null) {
			mMgr = null;
		}
		if (mFragmentStack != null && !mFragmentStack.isEmpty()) {
			mFragmentStack.clear();
			mFragmentStack = null;
		}
		_instance = null;
	}

	protected synchronized void display(String tag) {
		LogI("FM display -> " + tag);
		FragmentTransaction ft = mMgr.beginTransaction();
		Fragment tmp = hiddenOthers(ft, tag);
		LogI("FM display -> ft=" + ft + " tmp=" + tag);
		if (tmp != null) {
			ft.attach(tmp);
		} else {
			ft.replace(mContainerViewId, tmp, tag).attach(tmp);
		}
		mFragmentStack.add(tag);
		ft.commitAllowingStateLoss();;
	}

	/** 堆栈栈顶的元素是否是主界面 */
	private boolean isRoot() {
		if (mRootTag == null)
			return false;
		return mFragmentStack.lastElement().equals(mRootTag);
	}

	private void hidden(String tag) {
		Fragment fragment = mMgr.findFragmentByTag(tag);
		if (fragment != null) {
			mMgr.beginTransaction().detach(fragment).commitAllowingStateLoss();;
			mFragmentStack.remove(tag);
		}
	}

	/**
	 * 隐藏当前缓存的Fragment（非Activity管理Fragment堆栈），并且返回标记为tag的Fragment对象
	 * 
	 * @param ft
	 * @param tag
	 * @return
	 */
	private Fragment hiddenOthers(FragmentTransaction ft, String tag) {
		Fragment tagFragment = null;
		String item = null;
		Iterator<String> iterator = mFragmentStack.iterator();
		while (iterator.hasNext()) {
			item = iterator.next();
			LogI("cache -> " + item);
			Fragment t = mMgr.findFragmentByTag(item);
			if (t != null) {
				if (!t.isDetached()) {
					LogI("------" + item + " no detached-------");
					ft.detach(t);
				} else {
					LogI("------" + item + " is detached-------");
				}
				if (tag.equals(item))
					tagFragment = t;
			} else {
				LogI("------" + item + " is null-------");
			}
		}
		if (tagFragment != null) {
			mFragmentStack.remove(tag); // 从缓存移除
			LogI("FM remove -> " + tag);
		}
		return tagFragment;
	}

	private static void LogI(String text) {
		if (D) {
			Log.i(CommenUtils.TAG, text);
		}
	}

	@Override
	public HFragmentManager clone() {
		HFragmentManager o = null;
		try {
			/*
			 * o.mContainerViewId = this.mContainerViewId; o.mMgr = this.mMgr;
			 */
			o = (HFragmentManager) super.clone();
			o.mFragmentStack = mFragmentStack;
			o._instance = _instance;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}
}
