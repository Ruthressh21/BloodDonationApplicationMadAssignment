package my.edu.latestblooddonationapp.Admin

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import my.edu.latestblooddonationapp.R

class ViewModelBloodDonationRequests (application: Application) :
    AndroidViewModel(application) {

    var modelBloodDonationRequests: ModelBloodDonationRequests = ModelBloodDonationRequests()

    init {
        val preferences = application.getSharedPreferences(
            application.applicationContext.packageName.toString(), Context.MODE_PRIVATE
        )

        if (preferences.contains(application.resources.getString(R.string.patientName))) {
            modelBloodDonationRequests.patientName = preferences.getString("patientName", "").toString()
        }
        if (preferences.contains(application.resources.getString(R.string.bloodType))) {
            modelBloodDonationRequests.bloodType = preferences.getString("bloodType", "").toString()
        }
        if (preferences.contains(application.resources.getString(R.string.description))) {
            modelBloodDonationRequests.description = preferences.getString("description", "").toString()
        }
    }

    fun syncBloodDonationRequests(id: String){

        val ref =
            Firebase.database("https://blooddonationkotlin-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("BloodDonationRequests")
    }
}