package my.edu.latestblooddonationapp

import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import my.edu.latestblooddonationapp.R
import my.edu.latestblooddonationapp.databinding.FragmentEditProfileBinding

class editProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog
    private lateinit var userId:String

    private lateinit var firebaseUser: FirebaseUser
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()

        val sharedPref: SharedPreferences? = this.activity?.getSharedPreferences(
            "kotlinsharedpreference", Context.MODE_PRIVATE
        )
        var username: String = sharedPref?.getString("name", "No Data").toString()
        var uid: String = sharedPref?.getString("uid", "No Data").toString()

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        userId = firebaseUser.uid

        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        getUserDetails()

        binding.buttonUpdateProfile.setOnClickListener {
            validateData()
        }

        return binding.root
    }

    private var name = ""
    private var dateBirth = ""
    private var bloodGroup = ""
    private var gender = ""
    private var phoneNum = ""
    private var email = ""
    private var address = ""

    private fun validateData() {
        name = binding.fullName.text.toString().trim()
        dateBirth = binding.birthDate.text.toString().trim()
        phoneNum = binding.phoneNumber.text.toString().trim()
        address = binding.homeAddress.text.toString().trim()

        //validation
        if (name.isEmpty()) {
            binding.fullName.error = "Enter your name"
        } else if (dateBirth.isEmpty()) {
            binding.birthDate.error = "Enter your date birth"
        } else if (bloodGroup.isEmpty()) {
            Toast.makeText(this.context, "Choose your blood group", Toast.LENGTH_SHORT).show()
        }
        if (address.isEmpty()) {
            binding.homeAddress.error = "Enter your home address"
        } else if (phoneNum.isEmpty()) {
            binding.phoneNumber.error = "Enter your phone number"
        } else if (!Patterns.PHONE.matcher(phoneNum).matches()) {
            binding.phoneNumber.error = "Invalid phone number format"
        } else {
            editUserAccount()
        }
    }

    private fun editUserAccount() {
        progressDialog.dismiss()
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Editing User Profile...")
        progressDialog.show()

        val timestamp = System.currentTimeMillis()

        val hashMap = HashMap<String, Any>()
        hashMap["uid"] = "${firebaseAuth.uid}"
        hashMap["timestamp"] = timestamp
        hashMap["email"] = email
        hashMap["name"] = name
        hashMap["dateBirth"] = dateBirth
        hashMap["bloodGroup"] = bloodGroup
        hashMap["gender"] = gender
        hashMap["address"] = address
        hashMap["phoneNum"] = phoneNum

        val ref =
            Firebase.database("https://blooddonationkotlin-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Users")
        ref.child("${firebaseAuth.uid}")
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

    private fun getUserDetails() {
        val databaseuser =
            FirebaseDatabase.getInstance("https://blooddonationkotlin-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Users")
        databaseuser.child(userId)
        databaseuser.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children) {
                    var nameFb = i.child("name").getValue().toString()
                    var birthFb = i.child("dateBirth").getValue().toString()
                    var uid = i.child("uid").value
//                    var addFb = i.child("address").getValue().toString()
//                    var bloodFb = i.child("bloodGroup").getValue().toString()
//                    var genderFb = i.child("gender").getValue().toString()
//                    var phoneFb = i.child("phoneNumber").getValue().toString()
//                    var emailFb = i.child("email").getValue().toString()
//                    var passwdFb = i.child("password").getValue().toString()
//                    var idFb = i.child("id").getValue().toString()

                    binding.fullName.setText(nameFb)
                    binding.birthDate.setText(birthFb)
//                  binding.birthDate.setText(birthFb)
//                    //binding.outputBlood.setText(bloodFb)
//                    binding.homeAddress.setText(addFb)
//                    binding.phoneNumber.setText(phoneFb)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}