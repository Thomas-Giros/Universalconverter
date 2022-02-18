package com.example.universalconverter

import android.R.layout.simple_list_item_1
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.universalconverter.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText


private var coefficient: Double = 1.0
private var units = arrayOf<String>()
private var volumeUnits = arrayOf<String>()
private var distanceUnits = arrayOf<String>()

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        volumeUnits = resources.getStringArray(R.array.volume_Units)
        distanceUnits = resources.getStringArray(R.array.distance_Units)
        units = distanceUnits
        units = units.plus(volumeUnits)



        // We list all the units from the list of units
        binding.unitToConvertFrom.setAdapter(ArrayAdapter(this, simple_list_item_1, units))

        binding.unitToConvertFrom.onItemClickListener = AdapterView.OnItemClickListener{
                parent,view,position,id->
            val selectedItem = parent.getItemAtPosition(position).toString()
            // We set the list of units to convert to depending on family of units choosen in units to convert from
            if (distanceUnits.contains(selectedItem))
            {
                binding.unitToConvertTo.setAdapter(ArrayAdapter(this, simple_list_item_1, distanceUnits))
            } else if (volumeUnits.contains(selectedItem))
            {
                binding.unitToConvertTo.setAdapter(ArrayAdapter(this, simple_list_item_1, volumeUnits))
            }
        }


        // Here we calculate the coefficient and we apply it to the value then we screen it
        binding.button.setOnClickListener {
            convertAction()

            val valueToconvertFrom = binding.initialValue.text.toString().toDoubleOrNull()
            if (valueToconvertFrom == null) {
                binding.result.text = "0.0"
            } else {
                val result: Double = valueToconvertFrom * coefficient
                binding.result.text = result.toString()
            }

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


        if (distanceUnits.contains(binding.unitToConvertFrom.text.toString()))
        {
            // on utilise une conversion au metre  pour ensuite convertir
            // dans n'importe quelle distance a partir du metre
            when(binding.unitToConvertFrom.text.toString())
            {
                "gigameter" -> coefficient *= 1_000_000_000.0
                "megameter" -> coefficient *= 1_000_000.0
                "kilometer" -> coefficient *= 1_000.0
                "hectometer" -> coefficient *= 100.0
                "dekameter" -> coefficient *= 10.0
                "meter" -> coefficient *= 1.0
                "decimeter" -> coefficient *= 0.1
                "centimeter" -> coefficient *= 0.01
                "millimeter" -> coefficient *= 0.001
                "micrometer" -> coefficient *= 0.000_001
                "nanometer" -> coefficient *= 0.000_000_001

                "mile" -> coefficient *= 1609.344
                "furlong" -> coefficient *= 200.0
                "chain" -> coefficient *= 20.1168
                "rod" -> coefficient *= 5.0292
                "pole" -> coefficient *= 5.0292
                "perch" -> coefficient *= 5.0292
                "fathom" -> coefficient *= 1.8
                "yard" -> coefficient *= 0.9144
                "foot" -> coefficient *= 0.3048
                "hand" -> coefficient *= 0.1
                "inch" -> coefficient *= 0.0254

            }

            // A partir de la conversion en metre on peut convertir dans les autres unités de distance
            when(binding.unitToConvertTo.text.toString())
            {
                "gigameter" -> coefficient *= 0.000_000_001
                "megameter" -> coefficient *= 0.000_001
                "kilometer" -> coefficient *= 0.001
                "hectometer" -> coefficient *= 0.01
                "dekameter" -> coefficient *= 0.1
                "meter" -> coefficient *= 1.0
                "decimeter" -> coefficient *= 10.0
                "centimeter" -> coefficient *= 100.0
                "millimeter" -> coefficient *= 1_000.0
                "micrometer" -> coefficient *= 1_000_000.0
                "nanometer" -> coefficient *= 1_000_000_000.0

                "mile" -> coefficient *= 1/1609.344
                "furlong" -> coefficient *= 1/200.0
                "chain" -> coefficient *= 1/20.1168
                "rod" -> coefficient *= 1/5.0292
                "pole" -> coefficient *= 1/5.0292
                "perch" -> coefficient *= 1/5.0292
                "fathom" -> coefficient *= 1/1.8
                "yard" -> coefficient *= 1/0.9144
                "foot" -> coefficient *= 1/0.3048
                "hand" -> coefficient *= 1/0.1
                "inch" -> coefficient *= 1/0.0254
            }
        } else if (volumeUnits.contains(binding.unitToConvertFrom.text.toString()))
        {
            // on utilise une conversion au metre cube pour pour ensuite convertir
            // dans n'importe quelle distance a partir du metre cube
            when(binding.unitToConvertFrom.text.toString())
            {
                "cubic kilometer" -> coefficient *= 1_000_000_000
                "cubic hectometer" -> coefficient *= 1_000_000
                "cubic dekameter" -> coefficient *= 1_000
                "cubic meter" -> coefficient *= 1.0
                "cubic decimeter" -> coefficient *= 0.001
                "cubic centimeter" -> coefficient *= 0.000_001
                "cubic millimeter" -> coefficient *= 0.000_000_001

                "gigaliter" -> coefficient *= 1_000_000
                "megaliter" -> coefficient *= 1_000
                "kiloliter" -> coefficient *= 1.0
                "hectoliter" -> coefficient *= 0.1
                "dekaliter" -> coefficient *= 0.01
                "liter" -> coefficient *= 0.001
                "deciliter" -> coefficient *= 0.000_1
                "centiliter" -> coefficient *= 0.000_01
                "milliliter" -> coefficient *= 0.000_001
                "microliter" -> coefficient *= 0.000_000_001
                "stere" -> coefficient *= 1.0


                "cubic mile" -> coefficient *= 4_168_181_825.440
                "acre foot" -> coefficient *= 1_233.481_837_547_52
                "cubic yard" -> coefficient *= 0.764_554_857_984
                "cubic foot" -> coefficient *= 0.028_316_846_592
                "board foot" -> coefficient *= 0.002_359_737_216
                "cubic inch" -> coefficient *= 0.000_016_387_064

                "barrel USbbl" -> coefficient *= 0.119_240_471_196
                "barrel oilbbl" -> coefficient *= 0.158_987_294_928
                "beer barrel" -> coefficient *= 0.117_347_765_304
                "US gallon" -> coefficient *= 0.003_785_411_784
                "quart" -> coefficient *= 0.000_946_352_946
                "pint" -> coefficient *= 0.000_473_176_473
                "gill" -> coefficient *= 0.000_118_294_118
                "fluid ounce" -> coefficient *= 0.000_029_573_529_562
                "cup" -> coefficient *= 0.000_236_588_236_5
                "tablespoon" -> coefficient *= 0.000_014_786_764_8
                "teaspoon" -> coefficient *= 0.000_004_928_921_6
            }

            // A partir de la conversion en metre on peut convertir dans les autres unités de distance
            when(binding.unitToConvertTo.text.toString())
            {
                "cubic kilometer" -> coefficient *= 1/1_000_000_000
                "cubic hectometer" -> coefficient *= 1/1_000_000
                "cubic dekameter" -> coefficient *= 1/1_000
                "cubic meter" -> coefficient *= 1/1.0
                "cubic decimeter" -> coefficient *= 1/0.001
                "cubic centimeter" -> coefficient *= 1/0.000_001
                "cubic millimeter" -> coefficient *= 1/0.000_000_001

                "gigaliter" -> coefficient *= 1/1_000_000
                "megaliter" -> coefficient *= 1/1_000
                "kiloliter" -> coefficient *= 1/1.0
                "hectoliter" -> coefficient *= 1/0.1
                "dekaliter" -> coefficient *= 1/0.01
                "liter" -> coefficient *= 1/0.001
                "deciliter" -> coefficient *= 1/0.000_1
                "centiliter" -> coefficient *= 1/0.000_01
                "milliliter" -> coefficient *= 1/0.000_001
                "microliter" -> coefficient *= 1/0.000_000_001
                "stere" -> coefficient *= 1/1.0


                "cubic mile" -> coefficient *= 1/4_168_181_825.440
                "acre foot" -> coefficient *= 1/1_233.481_837_547_52
                "cubic yard" -> coefficient *= 1/0.764_554_857_984
                "cubic foot" -> coefficient *= 1/0.028_316_846_592
                "board foot" -> coefficient *= 1/0.002_359_737_216
                "cubic inch" -> coefficient *= 1/0.000_016_387_064

                "barrel USbbl" -> coefficient *= 1/0.119_240_471_196
                "barrel oilbbl" -> coefficient *= 1/0.158_987_294_928
                "beer barrel" -> coefficient *= 1/0.117_347_765_304
                "US gallon" -> coefficient *= 1/0.003_785_411_784
                "quart" -> coefficient *= 1/0.000_946_352_946
                "pint" -> coefficient *= 1/0.000_473_176_473
                "gill" -> coefficient *= 1/0.000_118_294_118
                "fluid ounce" -> coefficient *= 1/0.000_029_573_529_562
                "cup" -> coefficient *= 1/0.000_236_588_236_5
                "tablespoon" -> coefficient *= 1/0.000_014_786_764_8
                "teaspoon" -> coefficient *= 1/0.000_004_928_921_6
            }
        }
    }
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}
