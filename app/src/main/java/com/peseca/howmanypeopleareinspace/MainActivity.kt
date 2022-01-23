package com.peseca.howmanypeopleareinspace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL



class MainActivity : AppCompatActivity() {

    lateinit var numbertext : TextView
    var numberint : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numbertext = findViewById(R.id.number)

        CoroutineScope(Default).launch {
            var result = URL("https://www.howmanypeopleareinspacerightnow.com/peopleinspace.json").readText()
            val parser : Parser = Parser()
            val stringBuilder: StringBuilder = StringBuilder(result)
            val json: JsonObject = parser.parse(stringBuilder) as JsonObject
            numberint = json.get("number") as Int
            setnumber(numberint)
        }
    }

    suspend fun setnumber(number : Int) {
        withContext(Main) {
            numbertext.setText(numberint?.toString())
        }
    }
}