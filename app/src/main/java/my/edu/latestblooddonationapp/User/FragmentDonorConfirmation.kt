package my.edu.latestblooddonationapp.User

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import my.edu.latestblooddonationapp.databinding.FragmentDonorConfirmationBinding

class FragmentDonorConfirmation : Fragment() {

    private var _binding: FragmentDonorConfirmationBinding? = null

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    private var uid = ""
    private var name = ""
    private var gender = ""
    private var bloodType = ""
    private var phoneNum = ""

    val timestamp = System.currentTimeMillis()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDonorConfirmationBinding.inflate(inflater, container, false)

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
        binding.textViewDescription1.setText(description)

        val user = firebaseAuth.currentUser
        val uid = user!!.uid

        val ref =
            Firebase.database("https://blooddonationkotlin-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Users").child(uid)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val uid = dataSnapshot.child("uid").value as String?
                val name = dataSnapshot.child("name").value as String?
                val gender = dataSnapshot.child("gender").value as String?
                val bloodType = dataSnapshot.child("bloodType").value as String?
                val phoneNum = dataSnapshot.child("phoneNum").value as String?

                binding.textViewUID.setText(uid)
                binding.textViewName3.setText(name)
                binding.textViewGender3.setText(gender)
                binding.textViewBloodType3.setText(bloodType)
                binding.textViewPhoneNumber3.setText(phoneNum)

            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })




        binding.buttonConfirm2.setOnClickListener {
            validateData()
            binding.buttonCancel.setVisibility(View.VISIBLE)
            binding.buttonConfirm2.setVisibility(View.GONE)

        }

        binding.buttonCancel.setOnClickListener {
            binding.buttonCancel.setVisibility(View.GONE)
                binding.buttonConfirm2.setVisibility(View.VISIBLE)
            CancelDonate()

        }


        return binding.root

    }


    private fun validateData() {
        uid = binding.textViewUID.text.toString().trim()
        name = binding.textViewName3.text.toString().trim()
        gender = binding.textViewGender3.text.toString().trim()
        bloodType = binding.textViewBloodType3.text.toString().trim()
        phoneNum = binding.textViewPhoneNumber3.text.toString().trim()

        createConfirmBloodDonationFirebase()
    }

    private fun createConfirmBloodDonationFirebase() {
        progressDialog.show()
        Log.i("TimeAccept","$timestamp")
        //val timestamp = System.currentTimeMillis()
        val bbloodtype = requireArguments().getString("bloodtype").toString()
        val bdescription= requireArguments().getString("description").toString()
        val btimestamp = requireArguments().getString("ReferenceID").toString()
        val bname = requireArguments().getString("name").toString()

        val hashMap = HashMap<String, Any>()
        hashMap["id"] = "$timestamp"
        hashMap["bloodDonationRequestName"] = "$bname"
        hashMap["bloodDonationRequestReferenceID"] = "$btimestamp"
        hashMap["bloodDonationRequestDescription"] = "$bdescription"
        hashMap["bloodDonationRequestBloodType"] = "$bbloodtype"

        hashMap["uid"] = "${firebaseAuth.uid}"
        hashMap["name"] = "$name"
        hashMap["gender"] = "$gender"
        hashMap["bloodType"] = "$bloodType"
        hashMap["phoneNum"] = "$phoneNum"

        val ref = Firebase.database("https://blooddonationkotlin-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("BloodDonationInfo")
        ref.child("$timestamp")
            .setValue(hashMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    progressDialog.dismiss()
                    binding.textViewStatus1.text = "Submit Successfully "
                } else {
                    progressDialog.dismiss()
                    binding.textViewStatus1.text = "Fail to process"
                }


            }

    }
    private fun  CancelDonate() {
        Log.i("TimeCancel","$timestamp")
        progressDialog.dismiss()
        //get id of donor request to delete
        val user = firebaseAuth.currentUser
        val uid= user!!.uid
        //Firebase DB > Categories > categoryId
        val ref = Firebase.database("https://blooddonationkotlin-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference(
                "BloodDonationInfo")
        ref.child("$timestamp")
            .removeValue()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    progressDialog = ProgressDialog(context)
                    progressDialog.setTitle("Deleted")
                    progressDialog.setCanceledOnTouchOutside(false)
                    progressDialog.dismiss()
                } else {
                    progressDialog = ProgressDialog(context)
                    progressDialog.setTitle("Delete failed")
                    progressDialog.setCanceledOnTouchOutside(false)
                    progressDialog.dismiss()
                }
            }
    }

}
