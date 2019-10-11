package com.tacke.paio.utils

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore

import java.net.URISyntaxException

object PathUtil {
    /*
     * Gets the file path of the given Uri.
     */
    @SuppressLint("NewApi")
    @Throws(URISyntaxException::class)
    fun getPath(context: Context, uri: Uri): String? {
        val needToCheckUri = Build.VERSION.SDK_INT >= 19
        val selection: String? = null
        val selectionArgs: Array<String>? = null
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        // deal with different Uris.
        if (needToCheckUri && DocumentsContract.isDocumentUri(context.applicationContext, uri)) {
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
            }
            //            else if (isMediaDocument(uri)) {
            //                final String docId = DocumentsContract.getDocumentId(uri);
            //                final String[] split = docId.split(":");
            //                final String type = split[0];
            //                if ("image".equals(type)) {
            //                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            //                } else if ("video".equals(type)) {
            //                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            //                } else if ("audio".equals(type)) {
            //                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            //                }
            //                selection = "_id=?";
            //                selectionArgs = new String[]{ split[1] };
            //            }
        }
        if ("content".equals(uri.scheme!!, ignoreCase = true)) {
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            var cursor: Cursor? = null
            try {
                cursor =
                    context.contentResolver.query(uri, projection, selection, selectionArgs, null)
                val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index)
                }
            } catch (e: Exception) {
            }

            cursor!!.close()
        } else if ("file".equals(uri.scheme!!, ignoreCase = true)) {
            return uri.path
        }
        return null
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }


    //    /**
    //     * @param uri The Uri to check.
    //     * @return Whether the Uri authority is MediaProvider.
    //     */
    //    public static boolean isMediaDocument(Uri uri) {
    //        return "com.android.providers.media.documents".equals(uri.getAuthority());
    //    }
}