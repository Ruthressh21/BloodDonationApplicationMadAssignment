package my.edu.latestblooddonationapp.User

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import my.edu.latestblooddonationapp.R

class DonorVerifyRequest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_verify_request)

        val buttonAccept: Button = findViewById(R.id.btnAccept)
        val buttonReject: Button = findViewById(R.id.btnReject)

        val patientName: TextView = findViewById(R.id.outputPatientName)
        val bloodType: TextView = findViewById(R.id.outputBloodType)
        val description: TextView = findViewById(R.id.outputDescription)


    }
}