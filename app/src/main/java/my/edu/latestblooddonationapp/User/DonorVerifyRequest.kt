package my.edu.latestblooddonationapp.User

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import my.edu.latestblooddonationapp.R


class DonorVerifyRequest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_verify_request)

//        val buttonAccept: Button = findViewById(R.id.btnAccept)
//        val buttonReject: Button = findViewById(R.id.btnReject)
//
//        val patientName: TextView = findViewById(R.id.outputPatientName)
//        val bloodType: TextView = findViewById(R.id.outputBloodType)
//        val description: TextView = findViewById(R.id.outputDescription)
//
//
//            val databaseuser =
//                FirebaseDatabase.getInstance("https://blooddonationkotlin-default-rtdb.asia-southeast1.firebasedatabase.app/")
//                    .getReference("DonorRequests")
//
//
//
//            databaseuser.addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    for (i in snapshot.children) {
//                        val nameFb = i.child("patientName").getValue().toString()
//                        val bloodFb = i.child("bloodType").getValue().toString()
//                        val descriptionFb = i.child("description").getValue().toString()
//
//
//                        patientName.setText(nameFb)
//                        bloodType.setText(bloodFb)
//                        description.setText(descriptionFb)
//
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//
//                }
//
//            })
//        }
    }}