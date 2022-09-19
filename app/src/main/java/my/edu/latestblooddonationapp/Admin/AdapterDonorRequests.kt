package my.edu.latestblooddonationapp.Admin

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
import my.edu.latestblooddonationapp.databinding.FragmentEditDonorRequestBinding
import my.edu.latestblooddonationapp.databinding.FragmentRowDonorRequestBinding

class AdapterDonorRequests :RecyclerView.Adapter<AdapterDonorRequests.HolderDonorRequests>,
    Filterable {

    private lateinit var progressDialog: ProgressDialog
    private val context: Context
    public var categoryArrayList: ArrayList<ModelDonorRequests>
    private lateinit var binding: FragmentRowDonorRequestBinding
    private var filterList: ArrayList<ModelDonorRequests>

    private var filter: FilterDonorRequests? = null

    //constructor
    constructor(context: Context, categoryArrayList: ArrayList<ModelDonorRequests>) {
        this.context = context
        this.categoryArrayList = categoryArrayList
        this.filterList = categoryArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderDonorRequests {
        //inflate/bind fragment_row_donor_request.xml
        binding = FragmentRowDonorRequestBinding.inflate(LayoutInflater.from(context), parent, false)

        return HolderDonorRequests(binding.root)
    }

    override fun onBindViewHolder(holder: HolderDonorRequests, position: Int) {
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
                    deleteDonorRequest(model, holder)
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
                        editDonorRequest(model, holder)
                    }.setNegativeButton("Cancel"){a,d->
                        a.dismiss()
                    }
                    .show()
            }

        }

   @SuppressLint("ResourceType")
    private fun editDonorRequest(model: ModelDonorRequests, holder: AdapterDonorRequests.HolderDonorRequests) {
       //get patientName,bloodType, description
        val patientName = model.patientName
        val bloodType = model.bloodType
        val description = model.description

                val activity = context as AppCompatActivity
                val fragmentManager = activity.supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                val fragment = EditDonorRequest()

                val bundle = Bundle()
                fragment.arguments = bundle
                bundle.putString("patientName", patientName)
                bundle.putString("bloodType", bloodType)
                bundle.putString("description", description)
                fragmentTransaction.replace(R.id.recycleView, fragment).addToBackStack(null).commit()

    }


    private fun deleteDonorRequest(model: ModelDonorRequests, holder: HolderDonorRequests) {
        progressDialog.dismiss()
        //get id of donor request to delete
        val id = model.id
        //Firebase DB > Categories > categoryId
        val ref = Firebase.database("https://blooddonationkotlin-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("DonorRequests")
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
        return categoryArrayList.size //number of items in List
    }


    //ViewHolder class to hold/init UI views for fragment_row_donor_request.xml
    inner class HolderDonorRequests(itemView: View): RecyclerView.ViewHolder(itemView){
        //init ui views
        var patientName : TextView = binding.textViewPatientName2
        var bloodType : TextView = binding.textViewBloodTypes2
        var description : TextView = binding.textViewDescription2
        var deleteBtn : ImageButton = binding.imageButtonDelete
        var editBtn : ImageButton = binding.imageButtonEdit

    }

    override fun getFilter(): Filter {
        if(filter == null){
            filter = FilterDonorRequests(filterList, this)
        }
        return filter as FilterDonorRequests
    }

}

