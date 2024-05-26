package com.fikrialfian.aplikasicatatan

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.fikrialfian.aplikasicatatan.ui.theme.AplikasiCatatanTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AplikasiCatatanTheme {
                Notes()
            }
        }
    }
}