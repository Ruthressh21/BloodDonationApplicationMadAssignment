package my.edu.latestblooddonationapp.Admin

import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import my.edu.latestblooddonationapp.R
import my.edu.latestblooddonationapp.databinding.FragmentCreateBloodDonationRequestBinding


class CreateBloodDonationRequest : Fragment() {

    private var _binding: FragmentCreateBloodDonationRequestBinding? = null

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreateBloodDonationRequestBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.buttonConfirm.setOnClickListener {
            validateData()
        }

        return binding.root

    }

    private var patientName = ""
    private var bloodType = ""
    private var description = ""

    private fun validateData() {
        patientName = binding.editTextPatientName.text.toString().trim()
        bloodType = binding.spinnerBloodTypes.selectedItem.toString().trim()
        description = binding.editTextDescription.text.toString().trim()

        if (patientName.isEmpty()) {
            binding.editTextPatientName.error = "Enter the patient name"
        } else if (bloodType.isEmpty()) {
            Toast.makeText(this.context, "Choose a blood type", Toast.LENGTH_SHORT).show()
        } else if (description.isEmpty()) {
            binding.editTextDescription.error = "Enter the description"
        } else {
            createBloodDonationRequestFirebase()
        }
    }


    private fun createBloodDonationRequestFirebase() {
        progressDialog.show()

        val timestamp = System.currentTimeMillis()

        val hashMap = HashMap<String, Any>()
        hashMap["id"] = "$timestamp"
        hashMap["patientName"] = "$patientName"
        hashMap["bloodType"] = "$bloodType"
        hashMap["description"] = "$description"
        hashMap["timestamp"] = "$timestamp"
        hashMap["uid"] = "${firebaseAuth.uid}"


        val ref = Firebase.database("https://blooddonationkotlin-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("BloodDonationRequests")
        ref.child("$timestamp")
            .setValue(hashMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    progressDialog.dismiss()
                    binding.textViewStatus.text = "Successfully Created"
                } else {
                    progressDialog.dismiss()
                    binding.textViewStatus.text = "Fail to Create"
                }

            }
    }
}






