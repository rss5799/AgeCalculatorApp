package com.example.agecalculatorapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.agecalculatorapp.ui.theme.AgeCalculatorAppTheme
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Find the button and set up the click listener
        val calculateButton: Button = findViewById(R.id.button)
        calculateButton.setOnClickListener {
            calculateAge()
        }
    }

    fun calculateAge() {
        // Get references to the input fields for first name, last name, and date of birth
        val firstName: EditText = findViewById(R.id.editTextText2)
        val lastName: EditText = findViewById(R.id.editTextText)
        val dateOfBirth: EditText = findViewById(R.id.editTextDate)

        // Define a regex pattern for validating the date format
        val dobPattern = Regex("^(\\d{4})(\\d{2})(\\d{2})$")

        // Get the input values from the EditText fields
        val dobString = dateOfBirth.text.toString()
        val firstString = firstName.text.toString()
        val lastString = lastName.text.toString()

        // Validate first name input
        if (firstString.isBlank()) {
            Toast.makeText(this, "Please enter your first name!", Toast.LENGTH_SHORT).show()
            return
        }

        // Validate last name input
        if (lastString.isBlank()) {
            Toast.makeText(this, "Please enter your last name!", Toast.LENGTH_SHORT).show()
            return
        }

        // Validate date of birth input
        if (dobString.isBlank()) {
            Toast.makeText(this, "Please enter your date of birth!", Toast.LENGTH_SHORT).show()
            return
        }

        // Check if the date of birth matches the expected format
        if (!dobPattern.matches(dobString)) {
            Toast.makeText(this, "Invalid date format. Please use YYYYMMDD.", Toast.LENGTH_SHORT).show()
            return
        }

        // Create a SimpleDateFormat object to parse the date
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())

        try {
            // Parse the date of birth string into a Date object
            val parsedBirthday = dateFormat.parse(dobString)
            if (parsedBirthday != null) {
                // Create Calendar instances for today and the birthday
                val todayCalendar = Calendar.getInstance()
                val birthdayCalendar = Calendar.getInstance()
                birthdayCalendar.time = parsedBirthday // Set birthday date to the calendar

                // Calculate the age based on the year difference
                var age = todayCalendar.get(Calendar.YEAR) - birthdayCalendar.get(Calendar.YEAR)

                // Display the calculated age in a Toast message
                Toast.makeText(this, "You are " + age.toString() + " years old!", Toast.LENGTH_SHORT).show()
            }
        } catch (e: ParseException) {
            // Handle parsing errors and inform the user
            Toast.makeText(this, "Please enter a valid date!", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AgeCalculatorAppTheme {
        Greeting("Android")
    }
}
