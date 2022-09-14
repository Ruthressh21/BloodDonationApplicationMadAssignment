package my.edu.latestblooddonationapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import my.edu.latestblooddonationapp.databinding.FragmentEditProfileBinding
import my.edu.latestblooddonationapp.databinding.FragmentViewProfileBinding

class viewProfileFragment : Fragment() {

    private var _binding: FragmentViewProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var user:Users
    private lateinit var uid:String

    private lateinit var auth : FirebaseAuth
    private lateinit var dbRef : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentViewProfileBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()

        if(uid.isNotEmpty()){
            getUserDetails()
        }



        return binding.root


    }

    private fun getUserDetails(){
        val databaseuser  = FirebaseDatabase.getInstance("https://blooddonationfirebase2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")
        databaseuser.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children) {
                    var nameFb = i.child("name").getValue().toString()
                    var birthFb = i.child("dateOfBirth").getValue().toString()
                    var addFb = i.child("address").getValue().toString()
                    var bloodFb = i.child("bloodGroup").getValue().toString()
                    var genderFb = i.child("gender").getValue().toString()
                    var phoneFb = i.child("phoneNumber").getValue().toString()
                    var emailFb = i.child("email").getValue().toString()
                    var passwdFb = i.child("password").getValue().toString()
                    var idFb = i.child("id").getValue().toString()

                    binding.outputName.setText(nameFb)
                    binding.outputBirth.setText(birthFb)
                    binding.outputBlood.setText(bloodFb)
                    binding.outputAddress.setText(addFb)
                    binding.outputPhone.setText(phoneFb)
                }
            }

            override fun onCancelled(error:DatabaseError){

            }

        })
    }
}