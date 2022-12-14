package my.edu.latestblooddonationapp

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import my.edu.latestblooddonationapp.databinding.FragmentForgotPasswordBinding
import my.edu.latestblooddonationapp.databinding.FragmentRegistrationBinding

class fragmentForgotPassword : Fragment() {

    private var _binding: FragmentForgotPasswordBinding? = null
    private lateinit var auth: FirebaseAuth;

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //enable menu item
        setHasOptionsMenu(true)
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)

        auth= Firebase.auth

        val buttonForgot = binding.btnForgot

        buttonForgot.setOnClickListener{
            val email = binding.editTextEmail.text.toString().trim{it <= ' '}
            if(email.isEmpty()){
                Toast.makeText(this.context, "Please enter you email address", Toast.LENGTH_LONG).show()
            }else{
                auth.sendPasswordResetEmail(email).addOnCompleteListener{
                    if(it.isSuccessful){

                        Toast.makeText(this.context, "Email was sent successfully", Toast.LENGTH_LONG).show()


                    }
                }
            }
        }
        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        // log out action not visible in forgot password fragment
        menu.findItem(R.id.action_logOut).isVisible = false
    }
}