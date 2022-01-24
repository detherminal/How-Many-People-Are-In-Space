package com.peseca.howmanypeopleareinspace

import android.icu.text.MessageFormat.format
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beust.klaxon.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.format.DateTimeFormat
import java.lang.Integer.parseInt
import java.lang.String.format
import java.net.URL
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


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

        val today = SimpleDateFormat("ddMMyyyy")

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
                val year = date[0]
                val month = date[1]
                val day = date[2]
                val launchday : String = day.toString() + month.toString() + year.toString()
                val jodaDate : DateTime = DateTime()
                val datetimeformatter : org.joda.time.format.DateTimeFormatter = DateTimeFormat.forPattern("ddMMyyyy")
                val jodaDateLaunch : DateTime = DateTime.parse(launchday, datetimeformatter)
                val difference : Int = (Days.daysBetween(jodaDateLaunch, jodaDate)).days
                var diff = difference.toString() + " Days In Space"
                personDaysInSpace.add(diff)
            }
            for (i in (json.lookup<String?>("people.country"))) {
                val new = i!!.capitalize()
                personWhereFrom.add(new)
            }
            withContext(Main) {
                val customadapter = RecyclerViewAdapter(this@MainActivity, personName, personPosition, personDaysInSpace, personWhereFrom)
                recycler.setLayoutManager(LinearLayoutManager(this@MainActivity))
                recycler.adapter = customadapter
            }
        }
    }
}