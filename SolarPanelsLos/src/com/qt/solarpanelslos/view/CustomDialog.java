package com.qt.solarpanelslos.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qt.solarpanelslos.R;
import com.qt.solarpanelslos.R.style;
import com.qt.solarpanelslos.utils.StringUtils;



public class CustomDialog extends AlertDialog {

	private Context context = null;
	private TextView mTvContent = null;
	private Button mBtnLeft = null;
	private Button mBtnRight = null;

	private String content = null;
	private String leftMessage = null;
	private String rightMessage = null;
	private OnClickListener onLeftListener = null;
	private OnClickListener onRightListener = null;
	private View contentView = null;
	
	protected CustomDialog(Context context) {
		super(context, style.dialog_defult);
		this.context = context;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCanceledOnTouchOutside(false);
		this.initView();
		this.initData();
	}

	private void initView() {
		View view = LayoutInflater.from(context).inflate(
				R.layout.dialog_deflut, null);
		mTvContent = (TextView) view.findViewById(R.id.tv_dialog_content);
		mBtnLeft = (Button) view.findViewById(R.id.btn_ok);
		mBtnRight = (Button) view.findViewById(R.id.btn_cancel);
		setContentView(view);
	}

	private void initData() {
		if (!StringUtils.isBlank(content)) {
			mTvContent.setText(content);
		}
		if (!StringUtils.isBlank(leftMessage)) {
			mBtnLeft.setText(leftMessage);
		}
		if (!StringUtils.isBlank(rightMessage)) {
			mBtnRight.setText(rightMessage);
		}
		if (this.onLeftListener != null) {
			mBtnLeft.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					onLeftListener.onClick(CustomDialog.this, mBtnLeft.getId());
				}
			});
		} else {
			mBtnLeft.setVisibility(View.GONE);
		}
		if (this.onRightListener != null) {
			mBtnRight.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					CustomDialog.this.dismiss();
					onRightListener.onClick(CustomDialog.this, mBtnLeft.getId());
				}
			});
		} else {
			mBtnRight.setVisibility(View.GONE);
		}
		
	}

	private void setDialogView(View view) {
		this.contentView = view;
	}

	private void setDialogMessage(int message) {
		this.content = context.getString(message);
	}
	private void setDialogMessage(String message) {
		this.content = message;
	}

	private void setLeftButtonText(String leftStr) {
		this.leftMessage = leftStr;
	}

	private void setLeftButtonListener(OnClickListener listener) {
		this.onLeftListener = listener;
	}

	private void setRightButtonText(String rightStr) {
		this.rightMessage = rightStr;
	}

	private void setRightButtonListener(OnClickListener listener) {
		this.onRightListener = listener;
	}

	/**
	 * 显示两个按钮
	 * 
	 * @param context
	 * @param title
	 *            dialog标题
	 * @param view
	 *            显示视图
	 * @param isCancelable
	 *            点返回是否关闭对话框，true关闭，false不关闭
	 * @param leftButtonText
	 *            null为确定
	 * @param onLeftClickListener
	 *            null左边按钮隐藏
	 * @param rightButtonText
	 *            null为取消
	 * @param onRightClickListener
	 *            null右边按钮隐藏
	 * @return 当前显示对话框
	 */
	public static CustomDialog showDialog(Context context,
			View view, boolean isCancelable, String content,String leftButtonText,
			OnClickListener onLeftClickListener, String rightButtonText,
			OnClickListener onRightClickListener) {
		CustomDialog dialog = new CustomDialog(context);
		dialog.setDialogView(view);
		dialog.setDialogMessage(content);
		dialog.setCancelable(isCancelable);
		dialog.setLeftButtonText(leftButtonText);
		dialog.setLeftButtonListener(onLeftClickListener);
		dialog.setRightButtonText(rightButtonText);
		dialog.setRightButtonListener(onRightClickListener);
		dialog.show();
		return dialog;
	}

}
