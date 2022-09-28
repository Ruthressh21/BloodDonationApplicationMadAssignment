
//DonorVerifyRt.kt
//binding.//package my.edu.latestblooddonationapp.User
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import my.edu.latestblooddonationapp.R
//
//
//class DonorVerifyRt : Fragment() {
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }}

package my.edu.latestblooddonationapp.User

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import my.edu.latestblooddonationapp.databinding.FragmentCreateBloodDonationRequestBinding
import my.edu.latestblooddonationapp.databinding.FragmentDonorVerifyRtBinding

class DonorVerifyRt : Fragment() {

    private lateinit var binding :FragmentDonorVerifyRtBinding

    private lateinit var database : DatabaseReference



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDonorVerifyRtBinding.inflate(inflater, container, false)







        binding.button3.setOnClickListener {
            val ID: String = binding.etusername.text.toString()
            if (ID.isNotEmpty()) {
                readData(ID)
            } else {
                Toast.makeText(context, "Please enter the UserID", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }
    private fun readData(ID: String){
        database = FirebaseDatabase.getInstance().getReference("BloodDonationRequests")
        database.child(ID).get().addOnSuccessListener {
            if (it.exists()){
                val patientName = it.child("patientName").value
                val bloodType = it.child("bloodType").value
                val description = it.child("description").value

                val timestamp = it.child("timestamp").value
                val uid = it.child("uid").value

                Toast.makeText(context,"Successful", Toast.LENGTH_SHORT).show()
                binding.etusername.text.clear()
                binding.toPatientName.text = patientName .toString()
                binding.toBloodType.text=bloodType.toString()
                binding.toDescription.text=description.toString()




            }else{
                Toast.makeText(context,"User Doesn't Exist ", Toast.LENGTH_SHORT).show()
            }

        }.addOnFailureListener{
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }

    }
}
