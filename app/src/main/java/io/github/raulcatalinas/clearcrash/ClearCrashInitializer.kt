package io.github.raulcatalinas.clearcrash

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.util.Log

/**
 * ContentProvider that auto-initializes ClearCrash.
 * This runs before Application.onCreate(), so no manual setup is needed.
 *
 * Users don't need to know this exists - it just works automatically.
 */
class ClearCrashInitializer : ContentProvider() {
    companion object {
        private const val TAG = "ClearCrash"
    }

    override fun onCreate(): Boolean {
        return try {
            val appContext = context?.applicationContext

            if (appContext == null) {
                Log.w(TAG, "Context is null during initialization")
                return true
            }

            ClearCrash.install(appContext)
            Log.d(TAG, "ClearCrash auto-initialized successfully ✓")

            true
        } catch (e: Exception) {
            Log.e(TAG, "Failed to auto-initialize ClearCrash", e)
            true
        }
    }

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
