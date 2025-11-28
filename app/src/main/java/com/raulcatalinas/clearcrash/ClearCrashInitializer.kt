package com.raulcatalinas.clearcrash

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

/**
 * ContentProvider that auto-initializes ClearCrash.
 * This runs before Application.onCreate(), so no manual setup is needed.
 */
class ClearCrashInitializer : ContentProvider() {

    override fun onCreate(): Boolean {
        context?.let { ctx ->
            ClearCrash.install(ctx.applicationContext)
        }
        return true
    }

    // These methods are not used, but required by ContentProvider
    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? = null

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun delete(
        uri: Uri,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int = 0

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int = 0
}
