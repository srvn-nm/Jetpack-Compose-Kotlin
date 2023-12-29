package com.example.weatherApp.jsonHandler

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

fun readJSONFromAssets(context: Context, path: String): String {
    return try {
        val file = context.assets.open(path)
//        val size: Int = file.available()
//        println(size)
//        val buffer = ByteArray(size)
//        println(buffer)
//        file.read(buffer)
//        file.close()
//        println("meow")
//        String(buffer, Charsets.UTF_8)
        println(file)
        val bufferedReader = BufferedReader(InputStreamReader(file))
        println(bufferedReader)
        val stringBuilder = StringBuilder()
        println(stringBuilder)
        bufferedReader.useLines { lines ->
            lines.forEach {
                stringBuilder.append(it)
            }
            println(lines)
        }
        println("meow")
        stringBuilder.toString()
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}