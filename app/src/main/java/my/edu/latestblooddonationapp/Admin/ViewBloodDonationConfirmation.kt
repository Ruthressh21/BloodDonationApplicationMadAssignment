package my.edu.latestblooddonationapp.Admin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
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
import my.edu.latestblooddonationapp.databinding.FragmentViewBloodDonationConfirmationBinding

class ViewBloodDonationConfirmation : Fragment() {

    private var _binding: FragmentViewBloodDonationConfirmationBinding? = null
    private lateinit var firebaseAuth: FirebaseAuth

    //arrayList to hold categories
    private lateinit var categoryArrayList: ArrayList<ModelBloodDonationConfirmation>

    //adapter
    private lateinit var adapterBloodDonationConfirmation: AdapterBloodDonationConfirmation

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
            setHasOptionsMenu(true)
        _binding = FragmentViewBloodDonationConfirmationBinding.inflate(inflater, container, false)


        firebaseAuth = FirebaseAuth.getInstance()
        loadCategories()

        //search
        binding.editTextSearch2.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //called as and when user type anything
                try{
                    adapterBloodDonationConfirmation.filter.filter(s)
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
        val ref = Firebase.database("https://blooddonationkotlin-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("BloodDonationInfo")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear list before starting adding data into it
                categoryArrayList.clear()
                for (ds in snapshot.children){
                    //get data as model
                    val model = ds.getValue((ModelBloodDonationConfirmation::class.java))

                    //add to arrayList
                    categoryArrayList.add(model!!)
                }
                //setup adapter
                adapterBloodDonationConfirmation = AdapterBloodDonationConfirmation(context!!, categoryArrayList)

                //set adapter to recyclerView
                binding.bloodDonationConfirmationView.adapter = adapterBloodDonationConfirmation

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        // log out action not visible in registration page
        menu.findItem(R.id.action_logOut).isVisible = false
    }
}