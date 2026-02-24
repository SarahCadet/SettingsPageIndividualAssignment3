@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.settingspage_individualassignment3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.TopAppBarDefaults.pinnedScrollBehavior

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScrollModifierNode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.settingspage_individualassignment3.ui.theme.SettingsPageIndividualAssignment3Theme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SettingsPageIndividualAssignment3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SettingsScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsScreen( modifier: Modifier = Modifier) {
    // top app bar
    Column(verticalArrangement = Arrangement.Top){
        Column(){
            CenterAlignedTopAppBar(
                modifier = Modifier.fillMaxWidth().background(color = MaterialTheme.colorScheme.primary),
                title = { Text(modifier = Modifier, text = "Settings") },
                navigationIcon = {
                    IconButton(onClick = { println("back button clicked")}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                scrollBehavior = pinnedScrollBehavior()
            )
            HorizontalDivider(modifier = Modifier.border(5.dp, Color(0xFF000000)))
        }
        SettingsContent(Modifier.fillMaxWidth())
    }



}

@Composable
fun SettingsContent(modifier: Modifier = Modifier){
    // Settings for reddit or something
// CheckBox; Switch; Slider for textSize?; App Bar to display settings title
    //chips -> interests
    //date picker -> birthday
    //divider between settings
    val settingRowHeight = 56.dp
    val SettingsRowModifier = modifier.padding(12.dp, 5.dp).height(settingRowHeight)
    val divider: @Composable () -> Unit = { HorizontalDivider(modifier = Modifier.height(5.dp).padding(12.dp, 0.dp))
    }


    var allowDMs by rememberSaveable { mutableStateOf(false) }
    val DMsSwitch : @Composable () -> Unit = { Switch(checked = allowDMs, onCheckedChange = {value -> allowDMs = value})}


    var pronouns by rememberSaveable{ mutableStateOf("")}
    val pronounsInput: @Composable () -> Unit = {
        TextField(modifier = Modifier.width(150.dp).height(settingRowHeight), value = pronouns, onValueChange = {value -> pronouns = value; println(value)})
    }

    var textSize by rememberSaveable { mutableStateOf(20f) }
    val textSizeSlider : @Composable () -> Unit = {
        Slider(modifier= Modifier.width(200.dp), value = textSize, onValueChange = {value -> textSize = value; }, valueRange = 20f..30f, steps = 10)
    }


    Column(modifier = modifier){
        SettingsRow(textSize = textSize, modifier = SettingsRowModifier, label = "Pronouns", controlUI = pronounsInput)
        divider()
        SettingsRow(textSize = textSize, modifier = SettingsRowModifier, label = "Allow Direct Messages", controlUI = DMsSwitch)
        divider()
        NotificationSettingsSection(textSize = textSize, modifier = Modifier)
        divider()
        SettingsRow(textSize = textSize, modifier = SettingsRowModifier, label = "Text Size", controlUI = textSizeSlider)
        divider()
        Button(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = {}){
            Text(fontSize = textSize.sp, text = "Save Changes")
        }
    }
}

@Composable
fun SettingsRow(modifier: Modifier = Modifier, textSize: Float = 15f, label: String = "Default Label", controlUI : @Composable () -> Unit = {Text("Default Control UI")}){
    // Label and controlUI
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
        Text(text = label, fontSize = textSize.sp)
        controlUI()
    }
}

@Composable
fun NotificationSettingsSection(modifier: Modifier = Modifier, textSize : Float = 15f) {

    var commentsNotif by rememberSaveable { mutableStateOf(true) }
    var mentionsNotif by rememberSaveable { mutableStateOf(false) }
    var messagesNotif by rememberSaveable { mutableStateOf(true) }

    val commentsCheckbox : @Composable () -> Unit = { Checkbox(checked = commentsNotif, onCheckedChange = {value -> commentsNotif = value})}
    val mentionsCheckbox : @Composable () -> Unit = { Checkbox(checked = mentionsNotif, onCheckedChange = {value -> mentionsNotif = value})}
    val messagesCheckbox : @Composable () -> Unit = { Checkbox(checked = messagesNotif, onCheckedChange = {value -> messagesNotif = value})}




    Column(modifier = modifier.padding(12.dp)) {

        Text(
            text = "Notification Types",
            fontWeight = FontWeight.Bold,
            fontSize = textSize.sp
        )

        SettingsRow(
            textSize = textSize,
            label = "Comments",
            controlUI = commentsCheckbox
        )

        SettingsRow(
            textSize = textSize,
            label = "Mentions",
            controlUI = mentionsCheckbox
        )

        SettingsRow(
            textSize = textSize,
            label = "Direct Messages",
            controlUI = messagesCheckbox
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ScreenPreview() {
    SettingsPageIndividualAssignment3Theme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SettingsScreen(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

/// used AI to get more examples of certain composables in use and descripstions of their parameters and how they are usually used in prod
// autofill suggestions
