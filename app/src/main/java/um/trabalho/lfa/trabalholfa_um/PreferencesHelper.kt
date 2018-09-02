package um.trabalho.lfa.trabalholfa_um

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * @author Daniel Monteiro
 *
 * @since on 02/09/2018.
 */

class PreferencesHelper(context: Context) {
    var preferences: SharedPreferences? = null

    init {
        preferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    @SuppressLint("CommitPrefEdits")
    fun saveObject(key: String, v: String) {
        preferences!!.edit().putString(key, v).apply()
    }

    fun getObject(key: String): String {
        return preferences!!.getString(key, "")
    }

}