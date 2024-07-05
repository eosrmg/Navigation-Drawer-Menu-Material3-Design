package com.eosrmg.apps.navigationdrawer


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.eosrmg.apps.navigationdrawer.ui.theme.NavigationDrawerTheme
import com.eosrmg.apps.navigationdrawer.ui.view.NavigationDrawer


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationDrawerTheme {

                NavigationDrawer()
            }
        }
    }
}

