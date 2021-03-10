/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.backgroundColor
import com.example.androiddevchallenge.ui.theme.purple700

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    val context = LocalContext.current
    var startTime = 60000L
    val interval = 1000L
    val startText = "00:00"
    val startAction = "START"
    val stopAction = "STOP"

    lateinit var countDownTimer: CountDownTimer
    var started = false
    var timeInMillis: Long
    var action by remember { mutableStateOf(startAction) }
    var time by remember { mutableStateOf(startText) }

//    var colorState by remember { mutableStateOf(true) }
//    val currentColor by animateColorAsState(if (colorState) backgroundColor else Color.White)

    Surface(
        color = backgroundColor,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Select timer duration:",
                modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 24.dp),
                style = TextStyle(
                    color = purple700,
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                ),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        SelectMinutes(
                            text = "1min",
                            onClick = {
                                startTime = 60000L * 1
                                time = "01:00"
                            },
                        )
                        SelectMinutes(
                            text = "2min",
                            onClick = {
                                startTime = 60000L * 2
                                time = "02:00"
                            }
                        )
                        SelectMinutes(
                            text = "3min",
                            onClick = {
                                startTime = 60000L * 3
                                time = "03:00"
                            }
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp, vertical = 18.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        SelectMinutes(
                            text = "5min",
                            onClick = {
                                startTime = 60000L * 5
                                time = "05:00"
                            }
                        )
                        SelectMinutes(
                            text = "10min",
                            onClick = {
                                startTime = 60000L * 10
                                time = "10:00"
                            }
                        )
                        SelectMinutes(
                            text = "15min",
                            onClick = {
                                startTime = 60000L * 15
                                time = "15:00"
                            }
                        )
                    }
                }
            }

            Box(
                modifier = Modifier.fillMaxHeight(0.4f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = time,
                    style = TextStyle(
                        color = purple700,
                        fontSize = 96.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.ExtraLight,
                        textAlign = TextAlign.Center
                    )
                )
            }

            TimerAction(
                action,
                onClick = {
                    if (started) {
                        countDownTimer.cancel()
                        started = false
                        time = startText
                        action = startAction
                    } else {
                        countDownTimer =
                            object : CountDownTimer(startTime, interval) {
                                override fun onFinish() {
                                    started = false
                                    action = startAction
                                    Toast.makeText(context, "Done!", Toast.LENGTH_SHORT).show()
                                }

                                override fun onTick(remaining: Long) {
                                    timeInMillis = remaining
                                    val min = (timeInMillis / 1000) / 60
                                    val sec = (timeInMillis / 1000) % 60

                                    time =
                                        if (min < 10 && sec < 10) {
                                            "0$min:0$sec"
                                        } else if (min < 10 && sec > 10) {
                                            "0$min:$sec"
                                        } else if (min > 10 && sec < 10) {
                                            "$min:0$sec"
                                        } else {
                                            "$min:$sec"
                                        }
                                }
                            }
                        countDownTimer.start()
                        started = true
                        action = stopAction
                    }
                }
            )
        }
    }
}

@Composable
private fun TimerAction(text: String, onClick: () -> Unit) {
    Button(
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.White,
            contentColor = purple700
        ),
        // shape = RoundedCornerShape(16.dp), // Type mismatch, required corner size
        border = BorderStroke(1.dp, purple700),
        onClick = onClick,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 36.dp)
            .fillMaxHeight(0.4f)
            .fillMaxWidth(0.9f)

    ) {
        Text(
            text = text,
            style = TextStyle(
                color = purple700,
                fontSize = 36.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            ),
        )
    }
}

@Composable
private fun SelectMinutes(text: String, onClick: () -> Unit) {

    // val clicked = remember { mutableStateOf(false) }

    Button(
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.White, // if (clicked.value) Color.Blue else Color.Gray,
            contentColor = purple700
        ),
        // shape = RoundedCornerShape(16.dp), // Type mismatch, required corner size
        border = BorderStroke(1.dp, purple700),
        onClick = onClick,
//        onClick = {
//            clicked.value = !clicked.value
//        },
        modifier = Modifier
            .padding(2.dp)
            .width(100.dp)

    ) {
        Text(
            text = text,
            style = TextStyle(
                color = purple700,
                fontSize = 18.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            ),
        )
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
