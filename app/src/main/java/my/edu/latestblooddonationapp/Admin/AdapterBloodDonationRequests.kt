package my.edu.latestblooddonationapp.Admin

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
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import my.edu.latestblooddonationapp.R
import my.edu.latestblooddonationapp.databinding.FragmentAdminHomeBinding
import my.edu.latestblooddonationapp.databinding.FragmentRowBloodDonationRequestBinding

class AdapterBloodDonationRequests :RecyclerView.Adapter<AdapterBloodDonationRequests.HolderBloodDonationRequests>,
    Filterable {

    private lateinit var progressDialog: ProgressDialog
    private val context: Context
    public var itemArrayList: ArrayList<ModelBloodDonationRequests>
    private lateinit var binding: FragmentRowBloodDonationRequestBinding
    private var filterList: ArrayList<ModelBloodDonationRequests>

    private var filter: FilterBloodDonationRequests? = null

    //constructor
    constructor(context: Context, itemArrayList: ArrayList<ModelBloodDonationRequests>) {
        this.context = context
        this.itemArrayList = itemArrayList
        this.filterList = itemArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderBloodDonationRequests {
        //inflate/bind fragment_row_blood_donation_request.xml
        binding = FragmentRowBloodDonationRequestBinding.inflate(LayoutInflater.from(context), parent, false)

        return HolderBloodDonationRequests(binding.root)
    }

    override fun onBindViewHolder(holder: HolderBloodDonationRequests, position: Int) {
        //get data
        val model = itemArrayList[position]
        val id = model.id
        val patientName = model.patientName
        val bloodType = model.bloodType
        val description = model.description

        //set data
        holder.id.text = id
        holder.patientName.text = patientName
        holder.bloodType.text = bloodType
        holder.description.text = description

        //handle click, delete category
        holder.deleteBtn.setOnClickListener{
            //confirm before delete
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete")
                .setMessage("Are you sure you want to delete this donor request?")
                .setPositiveButton("Confirm"){a,d->
                    progressDialog = ProgressDialog(context)
                    progressDialog.setTitle("Deleting...")
                    progressDialog.setCanceledOnTouchOutside(false)
                    deleteBloodDonationRequest(model, holder)
                }
                .setNegativeButton("Cancel"){a,d->
                    a.dismiss()
                }
                .show()
        }

        holder.editBtn.setOnClickListener {
            //confirm before edit
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Edit")
                .setMessage("Are you sure you want to edit this donor request?")
                .setPositiveButton("Confirm"){a,d->
                    progressDialog = ProgressDialog(context)
                    progressDialog.setTitle("Editing...")
                    progressDialog.setCanceledOnTouchOutside(false)
                    editBloodDonationRequest(model, holder)
                }.setNegativeButton("Cancel"){a,d->
                    a.dismiss()
                }
                .show()
        }

    }

    private fun editBloodDonationRequest(model: ModelBloodDonationRequests, holder: AdapterBloodDonationRequests.HolderBloodDonationRequests) {
        val id = model.id
        val patientName = model.patientName
        val bloodType = model.bloodType
        val description = model.description

        findNavController(holder.editBtn).navigate(R.id.action_viewBloodDonationRequest_to_editBloodDonationRequest, Bundle().apply {
            putString("id",id.toString())
            putString("patientName",patientName.toString())
            putString("bloodType",bloodType.toString())
            putString("description",description.toString())
        })
    }


    private fun deleteBloodDonationRequest(model: ModelBloodDonationRequests, holder: HolderBloodDonationRequests) {
        progressDialog.dismiss()
        //get id of donor request to delete
        val id = model.id
        //Firebase DB > Categories > categoryId
        val ref = Firebase.database("https://blooddonationkotlin-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("BloodDonationRequests")
        ref.child(id)
            .removeValue()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    progressDialog = ProgressDialog(context)
                    progressDialog.setTitle("Deleted")
                    progressDialog.setCanceledOnTouchOutside(false)
                    progressDialog.dismiss()
                } else {
                    progressDialog = ProgressDialog(context)
                    progressDialog.setTitle("Delete failed")
                    progressDialog.setCanceledOnTouchOutside(false)
                    progressDialog.dismiss()
                }
            }
    }

    override fun getItemCount(): Int {
        return itemArrayList.size //number of items in List
    }

    //ViewHolder class to hold/init UI views for fragment_row_blood_donation_request.xml
    inner class HolderBloodDonationRequests(itemView: View): RecyclerView.ViewHolder(itemView){
        //init ui views
        var id   : TextView = binding.textViewID
        var patientName : TextView = binding.textViewPatientName2
        var bloodType : TextView = binding.textViewBloodTypes2
        var description : TextView = binding.textViewDescription2
        var deleteBtn : ImageButton = binding.imageButtonDelete
        var editBtn : ImageButton = binding.imageButtonEdit

    }

    override fun getFilter(): Filter {
        if(filter == null){
            filter = FilterBloodDonationRequests(filterList, this)
        }
        return filter as FilterBloodDonationRequests
    }

}

