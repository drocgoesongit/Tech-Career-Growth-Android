package com.randomappsinc.techcareergrowth.persistence

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.randomappsinc.techcareergrowth.models.LessonType
import com.randomappsinc.techcareergrowth.theme.ThemeMode

class PreferencesManager(context: Context) {

    companion object {

        const val KEY_HAS_SEEN_SLIDESHOW = "KEY_HAS_SEEN_SLIDESHOW"

        const val THEME_MODE = "KEY_THEME_MODE"
        const val NUM_APP_OPENS = "NUM_APP_OPENS"

        const val APP_OPENS_FOR_RATING_UPSELL = 5
    }

    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    var hasSeenSlideshow: Boolean
        get() = prefs.getBoolean(KEY_HAS_SEEN_SLIDESHOW, false)
        set(newHasSeenSlideshow) {
            prefs.edit().putBoolean(KEY_HAS_SEEN_SLIDESHOW, newHasSeenSlideshow).apply()
        }

    var themeMode: Int
        get() = prefs.getInt(THEME_MODE, ThemeMode.FOLLOW_SYSTEM)
        set(newThemeMode) {
            prefs.edit().putInt(THEME_MODE, newThemeMode).apply()
        }

    fun getContentOrder(): MutableList<LessonType> {
        return mutableListOf(
            LessonType.INTERVIEWING,
            LessonType.RESUME,
            LessonType.PRODUCTIVITY,
            LessonType.PROMOTION,
            LessonType.LEARNING_QUICKLY
        )
    }

    fun getLessonCompletionStatus(lessonId: String): Boolean {
        return prefs.getBoolean(lessonId, false)
    }

    fun onLessonCompleted(lessonId: String) {
        prefs.edit().putBoolean(lessonId, true).apply()
    }

    fun logAppOpenAndCheckForRatingUpsell(): Boolean {
        val currentAppOpens = prefs.getInt(NUM_APP_OPENS, 0) + 1
        prefs.edit().putInt(NUM_APP_OPENS, currentAppOpens).apply()
        return currentAppOpens == APP_OPENS_FOR_RATING_UPSELL
    }
}
