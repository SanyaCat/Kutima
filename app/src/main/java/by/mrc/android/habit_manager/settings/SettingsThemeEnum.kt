package by.mrc.android.habit_manager.settings

enum class SettingsThemeEnum {
    GREEN{ override fun get(): String = "Green" },
    ORANGE{ override fun get(): String = "Orange" },
    PURPLE{ override fun get(): String = "Purple" };

    abstract fun get(): String
}