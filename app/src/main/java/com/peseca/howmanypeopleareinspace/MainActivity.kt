package com.peseca.howmanypeopleareinspace

import android.app.Person
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beust.klaxon.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Integer.parseInt
import java.net.URL
import java.text.SimpleDateFormat


class MainActivity : AppCompatActivity() {

    lateinit var numbertext : TextView
    lateinit var recycler : RecyclerView
    var numberint : Int = 0
    var personName : ArrayList<String> = ArrayList()
    var personWhereFrom : ArrayList<String> = ArrayList()
    var personPosition : ArrayList<String> = ArrayList()
    var personDaysInSpace : ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numbertext = findViewById(R.id.number)
        recycler = findViewById(R.id.recycler)

        CoroutineScope(Default).launch {
            var result = URL("https://www.howmanypeopleareinspacerightnow.com/peopleinspace.json").readText()
            val parser : Parser = Parser()
            val stringBuilder: StringBuilder = StringBuilder(result)
            val json: JsonObject = parser.parse(stringBuilder) as JsonObject
            numberint = json.get("number") as Int
            withContext(Main) {
                numbertext.setText(numberint?.toString())
            }
            for (i in (json.lookup<String?>("people.name"))) {
                i?.let { personName.add(it) }
            }
            for (i in (json.lookup<String?>("people.title"))) {
                i?.let { personPosition.add(it) }
            }
            for (i in (json.lookup<String?>("people.launchdate"))) {
                val date = i.toString().split("-")
                val year = parseInt(date[0])
                val month = parseInt(date[1])
                val day = parseInt(date[2])
                val currentYear = parseInt(SimpleDateFormat("yyyy").toString())
                val currentMonth = parseInt(SimpleDateFormat("MM").toString())
                val currentDay = parseInt(SimpleDateFormat("dd").toString())
                var daysInSpace : Int = 0
            }
            for (i in (json.lookup<String?>("people.country"))) {
                i?.capitalize()
                i?.let { personWhereFrom.add(it) }
            }
            val customadapter = RecyclerViewAdapter(this@MainActivity, personName, personPosition, personDaysInSpace, personWhereFrom)
            recycler.setLayoutManager(LinearLayoutManager(this@MainActivity))
            recycler.adapter = customadapter
        }
    }

}