package my.edu.latestblooddonationapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.*
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

        //enable menu item
        setHasOptionsMenu(true)
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

                val ref = FirebaseDatabase.getInstance().getReference("Users")
                ref.child(firebaseUser.uid)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            progressDialog.dismiss()
                            val uid = snapshot.child("uid").value
                            val name = snapshot.child("name").value
                            val dateBirth = snapshot.child("dateBirth").value
                            val bloodType = snapshot.child("bloodType").value
                            val gender = snapshot.child("gender").value
                            val address = snapshot.child("address").value
                            val phoneNum = snapshot.child("phoneNum").value
                            val email = snapshot.child("email").value

                            Bundle().apply {
                                putString("uid",uid.toString())
                                putString("name",name.toString())
                                putString("dateBirth",dateBirth.toString())
                                putString("bloodType",bloodType.toString())
                                putString("gender",gender.toString())
                                putString("address",address.toString())
                                putString("phoneNum",phoneNum.toString())
                                putString("email",email.toString())
                            }

                            val userType = snapshot.child("userType").value
                            if (userType == "Admin") {
                                val intent = Intent(
                                    context,
                                    HomeActivity::class.java
                                )
                                startActivity(intent)
                                Toast.makeText(context, "Welcome admin", Toast.LENGTH_SHORT).show()
                            } else if (userType == "User") {
                                val intent = Intent(
                                    context,
                                    UserHomeActivity::class.java
                                )
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        // log out action not visible in login fragment
        menu.findItem(R.id.action_logOut).isVisible = false
    }
}