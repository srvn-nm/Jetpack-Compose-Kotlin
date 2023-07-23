package com.example.splashScreenAnimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.splashScreenAnimation.ui.theme.SplashScreenAnimationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashScreenAnimationTheme {
                SplashScreenAnimation()
            }
        }
    }
}

@Composable
fun SplashScreenAnimation() {
    val scale = remember{ Animatable(0f) }

}