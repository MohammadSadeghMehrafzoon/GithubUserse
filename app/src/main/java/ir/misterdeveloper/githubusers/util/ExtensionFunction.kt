package ir.misterdeveloper.githubusers.util

import android.content.Context
import android.widget.Toast
import ir.misterdeveloper.githubusers.R


fun Context.toast(context: Context, text:String) {
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}

