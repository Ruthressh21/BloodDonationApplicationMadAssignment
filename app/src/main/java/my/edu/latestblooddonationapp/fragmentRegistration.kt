package my.edu.latestblooddonationapp

import android.app.ProgressDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import my.edu.latestblooddonationapp.databinding.FragmentRegistrationBinding
import kotlin.collections.HashMap
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_registration.*


class fragmentRegistration : Fragment() {


    private var _binding: FragmentRegistrationBinding? = null
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    var sharedPreferences: SharedPreferences? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.registerButton.setOnClickListener {
            validateData()
        }

        return binding.root
    }


    private var name = ""
    private var dateBirth = ""
    private var bloodType = ""
    private var gender = ""
    private var phoneNum = ""
    private var email = ""
    private var address = ""
    private var password = ""
    private var userType = ""


    private fun validateData() {
        val temp : Int  = binding.genderType.checkedRadioButtonId
        val rad = view?.findViewById<RadioButton>(temp)
        val genders = rad?.text.toString()
        name = binding.fullName.text.toString().trim()
        dateBirth = binding.dateBirth.text.toString().trim()
        gender = binding.genderType.checkedRadioButtonId.toString().trim()
        bloodType = binding.spinner.selectedItem.toString().trim()
        phoneNum = binding.phoneNumber.text.toString().trim()
        address = binding.homeAddress.text.toString().trim()
        email = binding.emailAddress.text.toString().trim()
        password = binding.password.text.toString().trim()
        gender = genders
        val cPassword = binding.confirmPassword.text.toString().trim()
        userType = binding.spinnerUserType.selectedItem.toString().trim()

        //validation
        if (name.isEmpty()) {
            binding.fullName.error = "Enter your name"
        } else if (dateBirth.isEmpty()) {
            binding.dateBirth.error = "Enter your date birth"
        } else if (bloodType.isEmpty()) {
            Toast.makeText(this.context, "Choose your blood group", Toast.LENGTH_SHORT).show()
        } else if (binding.radioButtonMale.isChecked) {
            binding.textViewGenderError.text = ""
        } else if(binding.radioButtonFemale.isChecked){
            binding.textViewGenderError.text = ""
        } else if (genderType.checkedRadioButtonId == -1){
            binding.textViewGenderError.text = "Please choose your gender"
        }
          if(address.isEmpty()) {
            binding.homeAddress.error = "Enter your home address"
        } else if (phoneNum.isEmpty()) {
            binding.phoneNumber.error = "Enter your phone number"
        } else if (!Patterns.PHONE.matcher(phoneNum).matches()) {
            binding.phoneNumber.error = "Invalid phone number format"
        } else if (email.isEmpty()) {
            binding.emailAddress.error = "Enter your email address"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailAddress.error = "Invalid email format"
        } else if (password.isEmpty()) {
            binding.password.error = "Enter your password"
        } else if (cPassword.isEmpty()) {
            binding.confirmPassword.error = "Enter your confirm password"
        } else if (password != cPassword) {
            binding.confirmPassword.error = "Password doesn't match"
        } else if (userType.isEmpty()) {
            Toast.makeText(this.context, "Choose a user type", Toast.LENGTH_SHORT).show()
        } else {
            createUserAccount()
        }
    }

    private fun createUserAccount() {
        progressDialog.setMessage("Creating Account")
        progressDialog.show()
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            //updateUserAccount
            if (task.isSuccessful) {
                progressDialog.setMessage(("Saving user info"))
                progressDialog.show()

                //timestamp
                val timestamp = System.currentTimeMillis()

                //get current user uid, since user is registered so we can get it now
                val uid = firebaseAuth.uid

                //setup data to add in db
                val hashMap: HashMap<String, Any?> = HashMap()
                hashMap["uid"] = uid
                hashMap["timestamp"] = timestamp
                hashMap["email"] = email
                hashMap["password"] = password
                hashMap["name"] = name
                hashMap["dateBirth"] = dateBirth
                hashMap["bloodType"] = bloodType
                hashMap["gender"] = gender
                hashMap["address"] = address
                hashMap["phoneNum"] = phoneNum
                hashMap["userType"] = userType // possible values are user/admin, will change value to admin manually on firebase db

                //set data to db
                val ref =
                    Firebase.database("https://blooddonationkotlin-default-rtdb.asia-southeast1.firebasedatabase.app/")
                        .getReference("Users")
                ref.child(uid!!)
                    .setValue(hashMap)
                    .addOnCompleteListener { task2 ->
                        if (task2.isSuccessful) {
                            /*val edits: SharedPreferences.Editor = sharedPreferences!!.edit()
                            edits.putString("name",name)
                            edits.putString("email",email)
                            edits.putString("dateBirth",dateBirth)
                            edits.putString("gender",gender)
                            edits.putString("address",phoneNum)
                            edits.putString("userType",userType)
                            edits?.apply()*/
                            progressDialog.dismiss()
                            Toast.makeText(this.context, "Register successful", Toast.LENGTH_LONG)
                                .show()
                            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
                        } else {
                            Toast.makeText(
                                this.context,
                                "Registration failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        binding.imageView3.setOnClickListener() {
                            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
                        }
                    }
            } else {
                progressDialog.dismiss()
                Toast.makeText(this.context, "Failed creating account", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}


