package com.example.powerreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

   lateinit var wifiManager: WifiManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        wifi_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // The switch is enabled/checked
                wifiManager.setWifiEnabled(true)
                wifi_switch.text = "WIFI IS ON"
            } else {
                // The switch is disabled
                wifiManager.setWifiEnabled(false)
                wifi_switch.text = "WIFI IS OFF"

            }
        }


    }

    override fun onStart() {
        super.onStart()

        val intentFilter = IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        registerReceiver(wifiStateReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(wifiStateReceiver)
    }

    val wifiStateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            var wifiStateExtra: Int = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                WifiManager.WIFI_STATE_UNKNOWN)

            when(wifiStateExtra) {
                WifiManager.WIFI_STATE_ENABLED -> {
                    wifi_switch.isChecked = true
                    wifi_switch.text = "WIIF IS ON"
                }

                WifiManager.WIFI_STATE_DISABLED -> {
                    wifi_switch.isChecked = false
                    wifi_switch.text = "WIIF IS OFF"
                }
            }

        }
    }



}
