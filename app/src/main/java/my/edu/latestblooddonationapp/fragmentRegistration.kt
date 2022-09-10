package my.edu.latestblooddonationapp
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import my.edu.latestblooddonationapp.databinding.FragmentRegistrationBinding
import org.w3c.dom.Text
import java.text.DateFormat
import java.util.*

private lateinit var firebaseAuth: FirebaseAuth


class fragmentRegistration : Fragment() {


    private var _binding: FragmentRegistrationBinding? = null



    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        firebaseAuth = FirebaseAuth.getInstance()
        binding.registerButton.setOnClickListener {
            val name = binding.fullName.text.toString()
            val dateBirth = binding.birthDate.text.toString()
            val bloodGroup = binding.spinner.selectedItem.toString()
            val gender = binding.genderType.checkedRadioButtonId.toString()
            val phoneNum = binding.phoneNumber.text.toString()
            val email: String = binding.emailAddress.text.toString()
            val address: String = binding.homeAddress.text.toString()
            val password: String = binding.password.text.toString()
            var id: Int = 0

            val database = Firebase.database("https://latestblooddonationapp-default-rtdb.asia-southeast1.firebasedatabase.app/")
            val ref = database.getReference("Users")

            ref.addListenerForSingleValueEvent(object :ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    id = snapshot.childrenCount.toInt()+1
                    var idS = id.toString()
                    val newUser = Users(idS.toString(),name,email,password,
                        dateBirth,bloodGroup,gender,address,phoneNum)
                    ref.child(idS).setValue(newUser)
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })


            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this.context, "Register Successful", Toast.LENGTH_LONG)
                            .show()
                        Navigation.findNavController(it)
                            .navigate(R.id.action_registrationFragment_to_loginFragment)
                    } else {
                        Toast.makeText(
                            this.context,
                            "Invalid Email Address or Password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            binding.imageView3.setOnClickListener {
                findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}