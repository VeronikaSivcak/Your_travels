package com.example.yourtravels

import android.app.Application
import com.example.yourtravels.data.AppContainer
import com.example.yourtravels.data.AppDataContainer


//InventoryApplication
class YourTravelsApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
