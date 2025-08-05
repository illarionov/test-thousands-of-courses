package com.example.thcourses.core.ui.internationalization.message

import android.content.res.Resources

public fun Resources.getString(message: LocalizedMessage): String = if (message.args.isEmpty()) {
    getString(message.resourceId)
} else {
    getString(message.resourceId, *message.args.toTypedArray())
}
