package com.eosrmg.apps.navigationdrawer.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eosrmg.apps.navigationdrawer.data.model.DrawerItems
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer()
{

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navController = rememberNavController()
    var selectedItemIndex by remember { mutableIntStateOf(0) }

    val items = listOf(
        DrawerItems("Send", Icons.Filled.Send),
        DrawerItems("Inbox", Icons.Filled.Email),
        DrawerItems("Outbox", Icons.Filled.MailOutline),


        )

    val screeTitle = when (selectedItemIndex){
        0 -> "Send"
        1 -> "Inbox"
        2 -> "Outbox"
        else -> "Send"
    }

    ////////////////////////////////////////////////////

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Mail",
                        modifier = Modifier.padding(10.dp)
                    )
                }

                items.forEachIndexed { index, drawerItems ->

                    NavigationDrawerItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
                            scope.launch {
                                drawerState.close()
                            }
                            val route = when (selectedItemIndex) {
                                0 -> "Send"
                                1 -> "Inbox"
                                2 -> "Outbox"
                                else -> "Send"

                            }

                            navController.navigate(route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }


                        },
                        icon = {
                            Row (
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Icon(
                                    imageVector = drawerItems.icon,
                                    contentDescription = null,
                                    modifier = Modifier.padding(10.dp),
                                )

                                Text(text = drawerItems.title)

                            }
                           },
                        label = {
                            Text(text = drawerItems.title)
                        },
                        modifier= Modifier
                            .fillMaxWidth()
                            .padding(5.dp)

                    )
                }

            }
        }


    ) {

        Scaffold(

            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = screeTitle,
                            modifier = Modifier.padding(start = 10.dp),
                            color = MaterialTheme.colorScheme.background
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.tertiary),
                    navigationIcon = {
                        OutlinedButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }

                            },
                            shape = MaterialTheme.shapes.extraSmall,

                            ) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = null,
                                tint = Color.LightGray
                            )

                        }
                    }
                )
            },





            ) {
            it //padding values
            navController.addOnDestinationChangedListener { controller, destination, arguments ->

                selectedItemIndex = when (destination.route) {
                    "Send" -> 0
                    "Inbox" -> 1
                    "Outbox" -> 2
                    else -> 0
                }

            }



            NavHost(navController = navController, startDestination = "Send") {
                composable("Send") { Screen1() }
                composable("Inbox") { Screen2() }
                composable("Outbox") { Screen3() }



            }
        }

        }
    }
