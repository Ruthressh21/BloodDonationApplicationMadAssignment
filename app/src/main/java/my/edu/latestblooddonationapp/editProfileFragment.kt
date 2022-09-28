package my.edu.latestblooddonationapp

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_registration.*
import my.edu.latestblooddonationapp.Admin.HomeActivity
import my.edu.latestblooddonationapp.R
import my.edu.latestblooddonationapp.User.UserHomeActivity
import my.edu.latestblooddonationapp.databinding.FragmentEditProfileBinding

class editProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
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


        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)
        retrieveUsers()

        binding.buttonUpdateProfile.setOnClickListener {
            validateData()
        }

        return binding.root
    }

    private fun retrieveUsers(){

        val database = FirebaseDatabase.getInstance("https://blooddonationkotlin-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Users")
        database.child(firebaseAuth.uid!!).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //for(ds in snapshot.children){
                   // val userName = ds.child("name").getValue(String::class.java)
                    val name = "${snapshot.child("name").value}"
                    binding.fullName.setText(name)
                val address = "${snapshot.child("address").value}"
                binding.homeAddress.setText(address)
               // }
            }
            override fun onCancelled(error: DatabaseError) {
                //  TODO("Not yet implemented")
            }


            })
    }

    private var name = ""
    private var dateBirth = ""
    private var bloodGroup = ""
    private var gender = ""
    private var phoneNum = ""
    private var email = ""
    private var address = ""
    private var password = ""
    private var uid = ""

    private fun validateData() {
        val temp: Int = binding.genderType.checkedRadioButtonId
        val rad = view?.findViewById<RadioButton>(temp)
        val genders = rad?.text.toString()
        name = binding.fullName.text.toString().trim()
        dateBirth = binding.dateBirth.text.toString().trim()
        gender = binding.genderType.checkedRadioButtonId.toString().trim()
        bloodGroup = binding.spinner.selectedItem.toString().trim()
        phoneNum = binding.phoneNumber.text.toString().trim()
        address = binding.homeAddress.text.toString().trim()
        gender = genders

        //validation
        if (name.isEmpty()) {
            binding.fullName.error = "Enter your name"
        }
        else if (address.isEmpty()) {
            binding.homeAddress.error = "Enter your home address"
        } else if (phoneNum.isEmpty()) {
            binding.phoneNumber.error = "Enter your phone number"
        } else if (!Patterns.PHONE.matcher(phoneNum).matches()) {
            binding.phoneNumber.error = "Invalid phone number format"
        } else if (email.isEmpty()) {
            binding.emailAddress.error = "Enter your email address"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailAddress.error = "Invalid email format"
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

}