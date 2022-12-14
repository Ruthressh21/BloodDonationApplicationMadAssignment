package my.edu.latestblooddonationapp

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Patterns
import android.view.*
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_registration.*
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
        //enable menu item bar
        setHasOptionsMenu(true)
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        val user = firebaseAuth.currentUser
        val uid = user!!.uid

        val ref =
            Firebase.database("https://blooddonationkotlin-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Users").child(uid)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val name = dataSnapshot.child("name").value as String?
                val dateBirth = dataSnapshot.child("dateBirth").value as String?
                val bloodType = dataSnapshot.child("bloodType").value as String?
                val gender = dataSnapshot.child("gender").value as String?
                val address = dataSnapshot.child("address").value as String?
                val phoneNum = dataSnapshot.child("phoneNum").value as String?
                val email = dataSnapshot.child("email").value as String?

                binding.fullName.setText(name)
                binding.dateOfBirth.setText(dateBirth)
                binding.gender.setText(gender)
                binding.bloodType.setText(bloodType)
                binding.homeAddress.setText(address)
                binding.phoneNumber.setText(phoneNum)
                binding.emailAddress.setText(email)

            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })

        binding.buttonUpdateProfile.setOnClickListener {
            validateData()
        }

        return binding.root
    }

    private var name = ""
    private var dateBirth = ""
    private var bloodType= ""
    private var gender = ""
    private var phoneNum = ""
    private var email = ""
    private var address = ""
    private var password = ""
    private var uid = ""

    private fun validateData() {
        name = binding.fullName.text.toString().trim()
        dateBirth = binding.dateOfBirth.text.toString().trim()
        gender = binding.gender.text.toString()
        bloodType = binding.bloodType.text.toString()
        phoneNum = binding.phoneNumber.text.toString().trim()
        address = binding.homeAddress.text.toString().trim()
        email = binding.emailAddress.text.toString().trim()
        // gender = genders

            editUserAccount()
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
        hashMap["bloodType"] = bloodType
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        // log out action not visible in edit profile page
        menu.findItem(R.id.action_logOut).isVisible = false
    }

}