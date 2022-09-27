package my.edu.latestblooddonationapp

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import my.edu.latestblooddonationapp.databinding.FragmentCreateBloodDonationRequestBinding
import my.edu.latestblooddonationapp.databinding.FragmentDonorInfoBinding
import java.lang.ref.Reference

class fragment_donorInfo : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentDonorInfoBinding? = null

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

        _binding = FragmentDonorInfoBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)


        val root: View = binding.root
        var bloodtype = requireArguments().getString("bloodtype").toString()
        var description= requireArguments().getString("description").toString()
        var timestamp = requireArguments().getString("ReferenceID").toString()
        var name = requireArguments().getString("name").toString()

        binding.textViewBloodType.setText(bloodtype)
        binding.textViewDescription1.setText(description)
        binding.textViewPatient.setText(name)
        binding.textViewReferenceID.setText(timestamp)

        binding.btnSubmit.setOnClickListener {
            validateData()

        }

        return binding.root

    }

    private var name = ""
    private var donorBloodType = ""
    private var donorDescription = ""

    private fun validateData() {
      name = binding.etDonorName.text.toString().trim()
        donorBloodType = binding.etBloodType.text.toString().trim()
        donorDescription = binding.etUserID2.text.toString().trim()

        if (name.isEmpty()) {
            binding.etDonorName.error = "Enter the patient name"
        } else if (donorBloodType.isEmpty()) {
            Toast.makeText(this.context, "Choose a blood type", Toast.LENGTH_SHORT).show()
        } else if ( donorDescription.isEmpty()) {
            binding.etUserID2.error = "Enter the description"
        } else {
            createBloodDonationRequestFirebase()

            Bundle().apply {
                putString("name",binding.etDonorName.text.toString())}
        }
    }

    private fun createBloodDonationRequestFirebase() {
        progressDialog.show()

        val timestamp = System.currentTimeMillis()


        val hashMap = HashMap<String, Any>()
        hashMap["id"] = "$timestamp"
        hashMap["donorName"] = "$name"
        hashMap["bloodType"] = "$donorBloodType"
        hashMap["description"] = "$donorDescription"



        hashMap["uid"] = "${firebaseAuth.uid}"


        val ref = Firebase.database("https://blooddonationkotlin-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Donor")
        ref.child("$timestamp")
            .setValue(hashMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    progressDialog.dismiss()
                    binding.textViewStatus1.text = "Successfully Created"
                } else {
                    progressDialog.dismiss()
                    binding.textViewStatus1.text = "Fail to Create"
                }

            }
    }
}
