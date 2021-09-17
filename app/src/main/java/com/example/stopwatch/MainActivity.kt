package com.example.stopwatch

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.util.Log.ASSERT
import android.widget.Button
import android.widget.Chronometer

class MainActivity : AppCompatActivity() {
    lateinit var buttonStart : Button
    lateinit var buttonReset : Button
    lateinit var stopWatch : Chronometer

    var isRunning = false
    var timeWhenStopped = 0L

    // making a classwide stating constant in Kotlin
    companion object {
        // all your "static" constants go here
        val TAG = "MainActivity"
        val BUNDLE_DISPLAYED_TIME = "Displayed Time"
        val RUNNING = "Is Running"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wireWidgets()

        timeWhenStopped = savedInstanceState?.getLong(BUNDLE_DISPLAYED_TIME) ?: 0L
        stopWatch.setBase(SystemClock.elapsedRealtime() + timeWhenStopped)
        isRunning = savedInstanceState?.getBoolean(RUNNING) ?: false

        if(isRunning) {
            buttonStart.text = "Stop"
            buttonStart.setBackgroundColor(Color.RED)
            stopWatch.setBase(SystemClock.elapsedRealtime() + timeWhenStopped)
            isRunning = true
            stopWatch.start()
        }

        buttonStart.setOnClickListener() {
            if(!isRunning) {
                buttonStart.text = "Stop"
                buttonStart.setBackgroundColor(Color.RED)
                stopWatch.setBase(SystemClock.elapsedRealtime() + timeWhenStopped)
                isRunning = true
                stopWatch.start()

            }
            else {
                buttonStart.text = "Start"
                buttonStart.setBackgroundColor(Color.argb(255,139, 195, 74))
                updateDisplayedTime()
                isRunning = false
                stopWatch.stop()
            }
        }

        buttonReset.setOnClickListener() {
            stopWatch.setBase(SystemClock.elapsedRealtime())
            timeWhenStopped = 0L
        }
    }

    private fun wireWidgets() {
        buttonStart = findViewById(R.id.buttonStart)
        buttonReset = findViewById(R.id.buttonReset)
        stopWatch = findViewById(R.id.chronometer_main_stopwatch)
    }

    fun updateDisplayedTime() {
        if(isRunning) {
            timeWhenStopped = (stopWatch.getBase() - SystemClock.elapsedRealtime())
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        updateDisplayedTime()
        outState.putLong("Displayed Time", timeWhenStopped)
        outState.putBoolean("Is Running", isRunning)
    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.v(TAG, "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.w(TAG, "onDestroy: ")
    }
}