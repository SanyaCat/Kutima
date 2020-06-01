package by.mrc.android.habit_manager.settings

enum class SettingsThemeEnum {
    GREEN{ override fun get(): String = "Green" },
    DARK_GREEN{ override fun get(): String = "Dark Green" },
    ORANGE{ override fun get(): String = "Orange" },
    DARK_ORANGE{ override fun get(): String = "Dark Orange" },
    PURPLE{ override fun get(): String = "Purple" },
    DARK_PURPLE{ override fun get(): String = "Dark Purple" };

    abstract fun get(): String
}