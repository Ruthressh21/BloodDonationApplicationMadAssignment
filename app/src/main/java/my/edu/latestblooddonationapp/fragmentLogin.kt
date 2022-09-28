package my.edu.latestblooddonationapp

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
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
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import my.edu.latestblooddonationapp.Admin.HomeActivity
import my.edu.latestblooddonationapp.User.UserHomeActivity
import my.edu.latestblooddonationapp.databinding.FragmentLoginBinding



class fragmentLogin : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.buttonLogin.setOnClickListener {
            /*Steps
           1) Input Data
           2) Validate Data
           3) Login - Firebase Auth
           4) Check user type - Fire Auth
              If User - Move to User Page
              If Admin - Move to Admin Page
            */
            validateData()
        }
        return binding.root
    }

    private var email = ""
    private var password = ""

    private fun validateData() {

        email = binding.edittextEmail.text.toString().trim()
        password = binding.editTextPassword.text.toString().trim()

        if (email.isEmpty()) {
            binding.edittextEmail.error = "Enter your email"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edittextEmail.error = "Invalid email format"
        } else if (password.isEmpty()) {
            binding.editTextPassword.error = "Enter your password"
        } else {
            loginUser()
        }
    }

    private fun loginUser() {
        progressDialog.setMessage("Logging In")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                //checkUser
                progressDialog.setMessage("Checking User")

                val firebaseUser = firebaseAuth.currentUser!!

                val ref = FirebaseDatabase.getInstance("https://blooddonationkotlin-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference("Users")
                ref.child(firebaseUser.uid)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            progressDialog.dismiss()
                            val name = snapshot.child("name").value.toString()
                            val birth = snapshot.child("dateBirth").value.toString()
                            val email = snapshot.child("email").value.toString()
                            val gender = snapshot.child("gender").value.toString()
                            val houseAdd = snapshot.child("address").value.toString()
                            val userType = snapshot.child("userType").value.toString()
                            val uid = snapshot.child("uid").value.toString()
                            if (userType == "Admin") {
                                val intent = Intent(
                                    context,
                                    HomeActivity::class.java
                                )
                                val sharedPrefFile = "kotlinsharedpreference"
                                val sharedPref: SharedPreferences? =
                                    activity?.getSharedPreferences(
                                        sharedPrefFile, Context.MODE_PRIVATE
                                    )
                                val editor: SharedPreferences.Editor? =
                                    sharedPref?.edit()
                                editor?.putString("userType", userType)
                                editor?.putString("name", name)
                                editor?.putString("email", email)
                                editor?.putString("dateBirth", birth)
                                editor?.putString("gender", gender)
                                editor?.putString("address", houseAdd)
                                editor?.putString("uid", uid)
                                editor?.apply()
                                startActivity(intent)
                                Toast.makeText(context, "Welcome admin", Toast.LENGTH_SHORT).show()
                            } else if (userType == "User") {
                                val intent = Intent(
                                    context,
                                    UserHomeActivity::class.java
                                )
                                val sharedPrefFile = "kotlinsharedpreference"
                                val sharedPref: SharedPreferences? =
                                    activity?.getSharedPreferences(
                                        sharedPrefFile, Context.MODE_PRIVATE
                                    )
                                val editor: SharedPreferences.Editor? =
                                    sharedPref?.edit()
                                editor?.putString("userType", userType)
                                editor?.putString("name", name)
                                editor?.putString("email", email)
                                editor?.putString("dateBirth", birth)
                                editor?.putString("gender", gender)
                                editor?.putString("address", houseAdd)
                                editor?.putString("uid", uid)
                                editor?.apply()
                                startActivity(intent)
                                Toast.makeText(context, "Welcome user", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })
            } else {
                progressDialog.dismiss()
                binding.textViewError.text = "Incorrect email or password,\n"+
                                             "reenter again !!"
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonRegisterNew.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        binding.forgotPassword.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_fragmentForgotPassword)
        }
    }
}