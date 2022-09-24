package my.edu.latestblooddonationapp

import android.app.ProgressDialog
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
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import my.edu.latestblooddonationapp.R
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
        val temp: Int = binding.genderType.checkedRadioButtonId
        val rad = view?.findViewById<RadioButton>(temp)
        val genders = rad?.text.toString()
        name = binding.fullName.text.toString().trim()
        dateBirth = binding.birthDate.text.toString().trim()
        gender = binding.genderType.checkedRadioButtonId.toString().trim()
        bloodGroup = binding.spinner.selectedItem.toString().trim()
        phoneNum = binding.phoneNumber.text.toString().trim()
        address = binding.homeAddress.text.toString().trim()
        email = binding.emailAddress.text.toString().trim()
        gender = genders

        //validation
        if (name.isEmpty()) {
            binding.fullName.error = "Enter your name"
        } else if (dateBirth.isEmpty()) {
            binding.birthDate.error = "Enter your date birth"
        } else if (bloodGroup.isEmpty()) {
            Toast.makeText(this.context, "Choose your blood group", Toast.LENGTH_SHORT).show()
        } else if (binding.radioButtonMale.isChecked) {
            binding.textViewGenderError.text = ""
        } else if (binding.radioButtonFemale.isChecked) {
            binding.textViewGenderError.text = ""
        } else if ((binding.radioButtonMale.isChecked == false) || (binding.radioButtonFemale.isChecked == false)) {
            binding.textViewGenderError.text = "choose your gender"
        }
        if (address.isEmpty()) {
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