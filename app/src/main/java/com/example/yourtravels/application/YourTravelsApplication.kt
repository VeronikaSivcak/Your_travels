package com.example.yourtravels.application

import android.app.Application
import com.example.yourtravels.data.AppContainer
import com.example.yourtravels.data.AppDataContainer

/**
 * Túto triedu som robila podľa InventoryApplication z codelabu
 */

class YourTravelsApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
