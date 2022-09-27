package my.edu.latestblooddonationapp.Admin

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import my.edu.latestblooddonationapp.databinding.FragmentEditBloodDonationRequestBinding


class EditBloodDonationRequest : Fragment() {

    private var _binding: FragmentEditBloodDonationRequestBinding? = null

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    private var patientName = ""
    private var bloodType = ""
    private var description = ""

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEditBloodDonationRequestBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)


        var id = requireArguments().getString("id").toString()
        var patientName = requireArguments().getString("patientName").toString()
        var bloodType = requireArguments().getString("bloodType").toString()
        var description =  requireArguments().getString("description").toString()

        binding.textViewID3.setText(id)
        binding.editTextPatientName.setText(patientName)

        if(bloodType == "A"){
            binding.spinnerBloodTypes.setSelection(0)
        } else if(bloodType == "B"){
            binding.spinnerBloodTypes.setSelection(1)
        } else if(bloodType == "O"){
            binding.spinnerBloodTypes.setSelection(2)
        } else {
            binding.spinnerBloodTypes.setSelection(3)
        }


        binding.editTextDescription.setText(description)

        binding.buttonConfirm.setOnClickListener {
            validateData()
        }
        return binding.root

    }


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
        editBloodDonationRequestFirebase()
    }
}


private fun editBloodDonationRequestFirebase() {
    progressDialog.dismiss()
    progressDialog = ProgressDialog(context)
    progressDialog.setMessage("Editing Blood Donation Request...")
    progressDialog.show()

    val timestamp = System.currentTimeMillis()

    val hashMap = HashMap<String, Any>()
    hashMap["id"] = "$timestamp"
    hashMap["patientName"] = "$patientName"
    hashMap["bloodType"] = "$bloodType"
    hashMap["description"] = "$description"
    hashMap["timestamp"] = "$timestamp"
    hashMap["uid"] = "${firebaseAuth.uid}"

    val ref =
        Firebase.database("https://blooddonationkotlin-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("BloodDonationRequests")
    ref.child("$timestamp")
        .updateChildren(hashMap)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                progressDialog.dismiss()
                binding.textViewStatus.text = "Successfully Edited"
            } else {
                progressDialog.dismiss()
                binding.textViewStatus.text = "Fail to Edit"
            }

        }
}
}