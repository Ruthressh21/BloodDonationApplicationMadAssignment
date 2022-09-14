package my.edu.latestblooddonationapp.Admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import my.edu.latestblooddonationapp.databinding.FragmentCreateDonorRequestBinding
import my.edu.latestblooddonationapp.R
import my.edu.latestblooddonationapp.Users

class CreateDonorRequest : Fragment() {

    private var _binding: FragmentCreateDonorRequestBinding? = null
    private lateinit var firebaseAuth: FirebaseAuth


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

        firebaseAuth = FirebaseAuth.getInstance()
        binding.buttonConfirm.setOnClickListener {
            val patientName = binding.editTextPatientName.text.toString().trim()
            val bloodType = binding.spinnerBloodTypes.selectedItem.toString().trim()
            val description = binding.editTextDescription.text.toString().trim()
            var donorRequestId: Int = 0

            if (patientName.isEmpty()) {
                binding.editTextPatientName.error = "This field is required"
            }
            if (description.isEmpty()) {
                binding.editTextDescription.error = "This field is required"
            }

            val database2 =
                Firebase.database("https://blooddonationfirebase2-default-rtdb.asia-southeast1.firebasedatabase.app/")
            val ref2 = database2.getReference("Donor Requests")

            ref2.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    donorRequestId = snapshot.childrenCount.toInt() + 1
                    var donorRequestIdS = donorRequestId.toString()
                    val newDonorRequest = DonorRequestClass(
                        donorRequestIdS.toString(),
                        patientName,
                        bloodType,
                        description
                    )
                    ref2.child(donorRequestIdS).setValue(newDonorRequest)
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }
    }
}






