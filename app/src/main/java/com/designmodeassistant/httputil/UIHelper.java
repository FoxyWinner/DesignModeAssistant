package com.designmodeassistant.httputil;


import android.app.Dialog;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.designmodeassistant.R;


/**
 * 应用程序UI工具包：封装UI相关的一些操作
 * @author AA
 * @Date 2014-11-23
 */
public class UIHelper {
	
	/** 加载数据对话框 */
	private static Dialog mLoadingDialog;
	//单例模式
	
	
	/**
	 * 显示加载对话框
	 * @param context 上下文
	 * @param msg 对话框显示内容
	 * @param cancelable 对话框是否可以取消
	 */
	public static void showDialogForLoading(Activity context, String msg, boolean cancelable)
	{
		View view = LayoutInflater.from(context).inflate(R.layout.layout_loading, null);
		TextView loadingText = (TextView)view.findViewById(R.id.id_tv_loading_dialog_text);
		loadingText.setText(" " +msg);
		mLoadingDialog = new Dialog(context, R.style.loading_dialog_style);
		mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
		mLoadingDialog.setCanceledOnTouchOutside(false);
		mLoadingDialog.show();
	}
	
	/**
	 * 关闭加载对话框
	 */
	public static void hideDialogForLoading() {
		if(mLoadingDialog != null && mLoadingDialog.isShowing()) {
			mLoadingDialog.cancel();
		}
	}

}
