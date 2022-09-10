package my.edu.latestblooddonationapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import my.edu.latestblooddonationapp.databinding.FragmentLoginBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

class fragmentLogin : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    //private lateinit var auth: FirebaseAuth

    private lateinit var firebaseAuth: FirebaseAuth


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)


        firebaseAuth = FirebaseAuth.getInstance()
        binding.buttonLogin.setOnClickListener{
            login()
        }
        return binding.root
    }

    private fun login() {

        val email = binding.edittextEmail.text.toString()
        val passwd = binding.editTextPassword.text.toString()

        if (email.isEmpty()) {
            binding.edittextEmail.error = "Please enter your email correctly"
            binding.edittextEmail.requestFocus()
            return
        }
        if (email.isEmpty()) {
            binding.editTextPassword.error = "Please enter your password correctly"
            binding.editTextPassword.requestFocus()
            return
        }

        if (email.isNotEmpty() && passwd.isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email, passwd).addOnCompleteListener {
                if (it.isSuccessful) {
//                    findNavController().navigate(R.id.action_loginFragment_to_donorHome2)
//                    val intent = Intent(context, MainActivity::class.java)
//                    startActivity(intent)
                    val intent = Intent(
                        context,
                        HomeActivity::class.java
                    )
                    startActivity(intent)
                } else {
                    Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
                }
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

    override fun onDestroyView() {
        super.onDestroyView()
        firebaseAuth.signOut()
        _binding = null
    }
}