package com.example.universalconverter

import android.R.layout.simple_list_item_1
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.universalconverter.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText


private var coefficient: Double = 1.0
private var units = arrayOf<String>()

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        units = resources.getStringArray(R.array.Units)

        val adapter = ArrayAdapter(this,
            simple_list_item_1, units)


        val unitsToConvertFromView = findViewById<AutoCompleteTextView>(R.id.unit_to_convert_from)
        val unitsToConvertFromTo = findViewById<AutoCompleteTextView>(R.id.unit_to_convert_to)

        unitsToConvertFromView.setAdapter(adapter)
        unitsToConvertFromTo.setAdapter(adapter)

        binding.button.setOnClickListener {
            convertAction()

            val result: Double = binding.initialValue.text.toString().toDouble() * coefficient
            binding.result.text = result.toString()
        }

    }

    private fun convertAction() {
        // On s'assure que les unitées choisies existent
        if (!units.contains(binding.unitToConvertFrom.text.toString())
            || !units.contains(binding.unitToConvertTo.text.toString()))
        {
            Toast.makeText(applicationContext,"Invalid Unit ! Choose from proposed",Toast.LENGTH_SHORT).show()
        }
        else
        {
            // on initialise le coefficient à 1. et ensuite on le modifia via ConvertCoefficient
            coefficient = 1.0
            convertCoefficient()
        }
    }

    fun convertCoefficient() {

        // on utilise une conversion au metre pour les unités metrique pour ensuite convertir
        // dans n'importe quelle distance a partir du metre
        when(binding.unitToConvertFrom.text.toString())
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
        when(binding.unitToConvertTo.text.toString())
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
