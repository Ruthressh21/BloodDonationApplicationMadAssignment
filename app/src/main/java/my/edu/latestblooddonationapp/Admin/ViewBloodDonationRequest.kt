package my.edu.latestblooddonationapp.Admin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import my.edu.latestblooddonationapp.R
import my.edu.latestblooddonationapp.databinding.FragmentViewBloodDonationRequestBinding

class ViewBloodDonationRequest : Fragment() {

    private var _binding: FragmentViewBloodDonationRequestBinding? = null
    private lateinit var firebaseAuth: FirebaseAuth

    //arrayList to hold categories
    private lateinit var categoryArrayList: ArrayList<ModelBloodDonationRequests>

    //adapter
    private lateinit var adapterBloodDonationRequests: AdapterBloodDonationRequests

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentViewBloodDonationRequestBinding.inflate(inflater, container, false)


        firebaseAuth = FirebaseAuth.getInstance()
        loadCategories()

        //search
        binding.editTextSearch.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //called as and when user type anything
                try{
                  adapterBloodDonationRequests.filter.filter(s)
                }
                catch (e: Exception){

                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })


        return binding.root
    }

    private fun loadCategories() {
        //init arrayList
        categoryArrayList = ArrayList()

        //get all categories from firebase database... Firebase DB > Categories
        val ref = Firebase.database("https://blooddonationkotlin-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("BloodDonationRequests")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear list before starting adding data into it
                categoryArrayList.clear()
                for (ds in snapshot.children){
                    //get data as model
                    val model = ds.getValue((ModelBloodDonationRequests::class.java))

                    //add to arrayList
                    categoryArrayList.add(model!!)
                }
                //setup adapter
                adapterBloodDonationRequests = AdapterBloodDonationRequests(context!!, categoryArrayList)

                //set adapter to recyclerView
                binding.bloodDonationRequestsView.adapter = adapterBloodDonationRequests

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonAddBloodDonationRequest.setOnClickListener{
            findNavController().navigate(R.id.action_viewBloodDonationRequest_to_createBloodDonationRequest)
        }
    }
}