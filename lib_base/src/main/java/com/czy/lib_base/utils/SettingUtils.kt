package com.czy.lib_base.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri


public class SettingUtils{
    companion object{
        fun openSystemBrower(context:Context,url:String){
            val uri: Uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)
        }

        /**
         * 复制内容到剪贴板
         *
         * @param content
         * @param context
         */
        fun copyContentToClipboard(context: Context,content: String) {
            //获取剪贴板管理器：
            val cm: ClipboardManager =
                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            // 创建普通字符型ClipData
            val mClipData: ClipData = ClipData.newPlainText("Label", content)
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData)
        }
    }
}