package my.edu.latestblooddonationapp.User

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
import kotlinx.android.synthetic.main.fragment_donor_info.view.*
import my.edu.latestblooddonationapp.databinding.FragmentDonorInfoBinding

class Fragment_donorInfo : Fragment() {

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

        binding.textViewPatient.setText(name)
        binding.textViewBloodType.setText(bloodtype)
        binding.textViewReferenceID.setText(timestamp)



        if(bloodtype== "A"){
            binding.spinnerDonorInfoBloodType.setSelection(0)
        } else if(bloodtype == "B"){
            binding.spinnerDonorInfoBloodType.setSelection(1)
        } else if(bloodtype == "O"){
            binding.spinnerDonorInfoBloodType.setSelection(2)
        } else {
            binding.spinnerDonorInfoBloodType.setSelection(3)
        }


        binding.textViewDescription1.setText(description)




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
        donorBloodType = binding.spinnerDonorInfoBloodType.selectedItem.toString().trim()
        donorDescription = binding.etUserID2.text.toString().trim()

        if (name.isEmpty()) {
            binding.etDonorName.error = "Enter the patient name"
        } else if (donorBloodType.isEmpty()) {
            Toast.makeText(this.context, "Choose a blood type", Toast.LENGTH_SHORT).show()
        } else if ( donorDescription.isEmpty()) {
            binding.etUserID2.error = "Enter the description"
        } else {
            createBloodDonationRequestFirebase()

        }
    }

    private fun createBloodDonationRequestFirebase() {
        progressDialog.show()

        val timestamp = System.currentTimeMillis()
        val pbloodtype = requireArguments().getString("bloodtype").toString()
        val pdescription= requireArguments().getString("description").toString()
        val ptimestamp = requireArguments().getString("ReferenceID").toString()
        val pname = requireArguments().getString("name").toString()



        val hashMap = HashMap<String, Any>()
        hashMap["id"] = "$timestamp"
        hashMap["donorName"] = "$name"
        hashMap["donorBloodType"] = "$donorBloodType"
        hashMap["donorDescription"] = "$donorDescription"
        hashMap["patientName"] = "$pname"
        hashMap["patientReferenceID"] = "$ptimestamp"
        hashMap["patientDescription"] = "$pdescription"
        hashMap["patientBloodType"] = "$pbloodtype"

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
