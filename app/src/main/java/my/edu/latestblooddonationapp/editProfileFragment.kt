package my.edu.latestblooddonationapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import my.edu.latestblooddonationapp.databinding.FragmentDonorHomeBinding
import my.edu.latestblooddonationapp.databinding.FragmentEditProfileBinding

class editProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)

        val sharedPref : SharedPreferences? = this.activity?.getSharedPreferences(
            "kotlinsharedpreference", Context.MODE_PRIVATE)
        var name: String =sharedPref?.getString("name","No Data").toString()
        var id: String =sharedPref?.getString("id","No Data").toString()
        //var id: String =Users

            //val nameNew = binding.editTextNewName.text.toString()
           // val phoneNew = binding.editTextNewPhone.text.toString()
           // val addressNew = binding.editTextNewAddress.text.toString()

        val database = Firebase.database("https://latestblooddonationapp-default-rtdb.asia-southeast1.firebasedatabase.app/")
            val ref = database.getReference("Users")

                        ref.addValueEventListener(object :ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for (user in snapshot.children) {
                                    val nameFB = user.child("name").getValue().toString()
                                    val addressFB = user.child("address").getValue().toString()
                                    val phoneNumFB = user.child("phone").getValue().toString()
                                    var idFB = user.child("id").getValue().toString()

                                    Log.i("MainActivity", "" + nameFB)//test only
                                    if (id.equals(idFB)) {
                                        Log.i("MainActivity", "3")//test only

                                        binding.editTextNewName.setText(nameFB)
                                        binding.editTextNewAddress.setText(addressFB)
                                        binding.editTextNewPhone.setText(phoneNumFB)
                                        binding.buttonUpdateProfile.setOnClickListener {
                                            ref.child(id).child("name")
                                                .setValue(binding.editTextNewName.text.toString())
                                            ref.child(id).child("phone")
                                                .setValue(binding.editTextNewPhone.text.toString())
                                            ref.child(id).child("address")
                                                .setValue(binding.editTextNewAddress.text.toString())
                                            Toast.makeText(context, "Updated Successful", Toast.LENGTH_LONG)


                                        }
                                    }
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {

                            }

                        })


        return binding.root
    }
    }