package my.edu.latestblooddonationapp.User

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import my.edu.latestblooddonationapp.R
import my.edu.latestblooddonationapp.databinding.FragmentAdminHomeBinding
import my.edu.latestblooddonationapp.databinding.FragmentRowDonateBloodBinding

class AdapterDonateBlood :RecyclerView.Adapter<AdapterDonateBlood.HolderDonateBlood>,
    Filterable {

    private lateinit var progressDialog: ProgressDialog
    private val context: Context
    public var categoryArrayList: ArrayList<ModelDonateBlood>
    private lateinit var binding: FragmentRowDonateBloodBinding
    private var filterList: ArrayList<ModelDonateBlood>

    private var filter: FilterDonateBlood? = null

    //constructor
    constructor(context: Context, categoryArrayList: ArrayList<ModelDonateBlood>) {
        this.context = context
        this.categoryArrayList = categoryArrayList
        this.filterList = categoryArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderDonateBlood {
        //inflate/bind fragment_row_donate_blood.xml
        binding = FragmentRowDonateBloodBinding.inflate(LayoutInflater.from(context), parent, false)

        return HolderDonateBlood(binding.root)
    }

    override fun onBindViewHolder(holder: HolderDonateBlood, position: Int) {
        //get data
        val model = categoryArrayList[position]
        val id = model.id
        val patientName = model.patientName
        val bloodType = model.bloodType
        val description = model.description
        val uid = model.uid
        val timestamp = model.timestamp

        //set data
        holder.patientName.text = patientName
        holder.bloodType.text = bloodType
        holder.description.text = description

        holder.donate.setOnClickListener {
            //confirm before create
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Edit")
                .setMessage("Are you sure you want to donate?")
                .setPositiveButton("Confirm"){a,d->
                    progressDialog = ProgressDialog(context)
                    progressDialog.setTitle("Please wait...")
                    progressDialog.setCanceledOnTouchOutside(false)
                    createDonateBlood(model, holder)
                }.setNegativeButton("Cancel"){a,d->
                    a.dismiss()
                }
                .show()
        }

    }

    @SuppressLint("ResourceType")
    private fun createDonateBlood(model: ModelDonateBlood, holder: AdapterDonateBlood.HolderDonateBlood) {
        //get patientName,bloodType, description
        val patientName = model.patientName
        val bloodType = model.bloodType
        val description = model.description

        val activity = context as AppCompatActivity
        val fragmentManager = activity.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = CreateDonateBlood()

        val bundle = Bundle()
        fragment.arguments = bundle
        bundle.putString("patientName", patientName)
        bundle.putString("bloodType", bloodType)
        bundle.putString("description", description)
        fragmentTransaction.replace(R.id.recycleView, fragment).addToBackStack(null).commit()

    }

    override fun getItemCount(): Int {
        return categoryArrayList.size //number of items in List
    }


    //ViewHolder class to hold/init UI views for fragment_row_blood_donation_request.xml
    inner class HolderDonateBlood(itemView: View): RecyclerView.ViewHolder(itemView){
        //init ui views
        var patientName : TextView = binding.textViewPatientName2
        var bloodType : TextView = binding.textViewBloodTypes2
        var description : TextView = binding.textViewDescription2
        var donate : Button = binding.buttonDonateBlood

    }

    override fun getFilter(): Filter {
        if(filter == null){
            filter = FilterDonateBlood(filterList, this)
        }
        return filter as FilterDonateBlood
    }

}

