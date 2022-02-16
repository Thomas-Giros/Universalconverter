package com.example.universalconverter

import android.R.layout.simple_list_item_1
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity



private var coefficient: Double = 1.0
private var units = arrayOf<String>()

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        units = resources.getStringArray(R.array.Units)
        val unitToConvertFrom = findViewById<AutoCompleteTextView>(R.id.unit_to_convert_from)
        val unitToConvertTo = findViewById<AutoCompleteTextView>(R.id.unit_to_convert_to)

        val adapter = ArrayAdapter(this,
            simple_list_item_1, units)

        unitToConvertFrom.setAdapter(adapter)
        unitToConvertTo.setAdapter(adapter)

        val convertButton = findViewById<Button>(R.id.button)
        val initialValue = findViewById<TextView>(R.id.initialValue)
        val resultValue = findViewById<TextView>(R.id.Result)

        convertButton.setOnClickListener {
            convertAction(unitToConvertFrom.text.toString(),unitToConvertTo.text.toString())

            val result: Double = initialValue.text.toString().toDouble() * coefficient
            resultValue.text = result.toString()
        }

    }

    private fun convertAction(unitToConvertFromString: String, unitToConvertToString: String) {
        // On s'assure que les unitées choisies existent
        if (!units.contains(unitToConvertFromString)
            || !units.contains(unitToConvertToString))
        {
            Toast.makeText(applicationContext,"Invalid Unit ! Choose from proposed",Toast.LENGTH_SHORT).show()
        }
        else
        {
            // on initialise le coefficient à 1. et ensuite on le modifia via ConvertCoefficient
            coefficient = 1.0
            convertCoefficient(unitToConvertFromString,unitToConvertToString)
        }
    }

    fun convertCoefficient(convertFromString: String, convertToString: String) {

        // on utilise une conversion au metre pour les unités metrique pour ensuite convertir
        // dans n'importe quelle distance a partir du metre
        when(convertFromString)
        {
            "gigameter" -> coefficient *= 1000000000.0
            "megameter" -> coefficient *= 1000000.0
            "kilometer" -> coefficient *= 1000.0
            "hectometer" -> coefficient *= 100.0
            "dekameter" -> coefficient *= 10.0
            "meter" -> coefficient *= 1.0
            "decimeter" -> coefficient *= 0.1
            "centimeter" -> coefficient *= 0.01
            "millimeter" -> coefficient *= 0.001
            "micrometer" -> coefficient *= 0.000001
            "nanometer" -> coefficient *= 0.000000001

            "mile" -> coefficient *= 1600.0
            "furlong" -> coefficient *= 200.0
            "chain" -> coefficient *= 20.0
            "rod" -> coefficient *= 5.0
            "pole" -> coefficient *= 5.0
            "perch" -> coefficient *= 5.0
            "fathom" -> coefficient *= 1.8
            "yard" -> coefficient *= 0.91
            "foot" -> coefficient *= 0.3
            "hand" -> coefficient *= 0.1
            "inch" -> coefficient *= 0.025

        }

        // A partir de la conversion en metre on peut convertir dans les autres unités de distance
        when(convertToString)
        {
            "gigameter" -> coefficient *= 0.000000001
            "megameter" -> coefficient *= 0.000001
            "kilometer" -> coefficient *= 0.001
            "hectometer" -> coefficient *= 0.01
            "dekameter" -> coefficient *= 0.1
            "meter" -> coefficient *= 1.0
            "decimeter" -> coefficient *= 10.0
            "centimeter" -> coefficient *= 100.0
            "millimeter" -> coefficient *= 1000.0
            "micrometer" -> coefficient *= 1000000.0
            "nanometer" -> coefficient *= 1000000000.0

            "mile" -> coefficient *= 1/1600.0
            "furlong" -> coefficient *= 1/200.0
            "chain" -> coefficient *= 1/20.0
            "rod" -> coefficient *= 1/5.0
            "pole" -> coefficient *= 1/5.0
            "perch" -> coefficient *= 1/5.0
            "fathom" -> coefficient *= 1/1.8
            "yard" -> coefficient *= 1/0.91
            "foot" -> coefficient *= 1/0.3
            "hand" -> coefficient *= 1/0.1
            "inch" -> coefficient *= 1/0.025
        }
    }
}
