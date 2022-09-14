package tarc.edu.latestblooddonation.Admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import my.edu.latestblooddonationapp.Admin.DonorRequestClass
import my.edu.latestblooddonationapp.databinding.FragmentCreateDonorRequestBinding


class CreateDonorRequestFragment : Fragment() {

    private var _binding: FragmentCreateDonorRequestBinding? = null
    private lateinit var database: DatabaseReference

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreateDonorRequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.buttonConfirm.setOnClickListener {
            val patientName = binding.editTextPatientName.text.toString()
            val bloodGroup = binding.spinnerBloodTypes.selectedItem.toString()
            val description = binding.editTextDescription.text.toString()

            if (patientName.isEmpty()){
                binding.editTextPatientName.error = "Please enter name"
            }
            if (description.isEmpty()){
                binding.editTextDescription.error = "Please enter description"
            }

            val dbRef =  Firebase.database("https://blooddonationfirebase2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Donor Requests")
            val donorRequest = DonorRequestClass(patientName, bloodGroup, description)
            dbRef.child(patientName).setValue(donorRequest).addOnSuccessListener {
                binding.editTextPatientName.text.clear()
                binding.editTextDescription.text.clear()

                Toast.makeText(context, "Successful", Toast.LENGTH_LONG).show()
            }.addOnFailureListener{
                Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show()
            }
        }
    }
}
