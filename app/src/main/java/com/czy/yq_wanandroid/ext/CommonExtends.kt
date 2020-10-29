package com.yangqing.record.ext

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Activity.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun Fragment.toast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}




