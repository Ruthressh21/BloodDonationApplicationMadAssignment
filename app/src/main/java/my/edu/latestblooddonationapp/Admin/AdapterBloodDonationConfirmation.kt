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
import my.edu.latestblooddonationapp.databinding.FragmentRowBloodDonationConfirmationBinding

class AdapterBloodDonationConfirmation :RecyclerView.Adapter<AdapterBloodDonationConfirmation.HolderBloodDonationConfirmation>,
    Filterable {

    private lateinit var progressDialog: ProgressDialog
    private val context: Context
    public var itemArrayList: ArrayList<ModelBloodDonationConfirmation>
    private lateinit var binding: FragmentRowBloodDonationConfirmationBinding
    private var filterList: ArrayList<ModelBloodDonationConfirmation>


    private var filter: FilterBloodDonationConfirmation? = null

    //constructor
    constructor(context: Context, itemArrayList: ArrayList<ModelBloodDonationConfirmation>) {
        this.context = context
        this.itemArrayList = itemArrayList
        this.filterList = itemArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderBloodDonationConfirmation {

        binding = FragmentRowBloodDonationConfirmationBinding.inflate(LayoutInflater.from(context), parent, false)

        return HolderBloodDonationConfirmation(binding.root)
    }

    override fun onBindViewHolder(holder: HolderBloodDonationConfirmation, position: Int) {
        //get data
        val model = itemArrayList[position]
        val bloodDonationRequestReferenceID  = model.bloodDonationRequestReferenceID
        val bloodDonationRequestName = model.bloodDonationRequestName
        val bloodDonationRequestBloodType = model.bloodDonationRequestBloodType
        val bloodDonationRequestDescription = model.bloodDonationRequestDescription
        val uid  = model.uid
        val name = model.name
        val gender = model.gender
        val bloodType = model.bloodType
        val phoneNum = model.phoneNum

        //set data
        holder.bloodDonationRequestReferenceID .text = bloodDonationRequestReferenceID
        holder.bloodDonationRequestName.text = bloodDonationRequestName
        holder.bloodDonationRequestBloodType.text = bloodDonationRequestBloodType
        holder.bloodDonationRequestDescription.text = bloodDonationRequestDescription
        holder.uid.text = uid
        holder.name.text = name
        holder.gender.text = gender
        holder.bloodType.text = bloodType
        holder.phoneNum.text = phoneNum
    }

    override fun getItemCount(): Int {
        return itemArrayList.size //number of items in List
    }


    inner class HolderBloodDonationConfirmation(itemView: View): RecyclerView.ViewHolder(itemView){
        //init ui views
        var bloodDonationRequestReferenceID   : TextView = binding.textViewReferenceID3
        var bloodDonationRequestName: TextView = binding.textViewPatientName3
        var bloodDonationRequestBloodType : TextView = binding.textViewBloodType4
        var bloodDonationRequestDescription : TextView = binding.textViewDescription3
        var uid   : TextView = binding.textViewUID2
        var name: TextView = binding.textViewName4
        var gender : TextView = binding.textViewGender4
        var bloodType : TextView = binding.textViewBloodType5
        var phoneNum : TextView = binding.textViewPhoneNumber5
    }

    override fun getFilter(): Filter {
        if(filter == null){
            filter = FilterBloodDonationConfirmation(filterList, this)
        }
        return filter as FilterBloodDonationConfirmation
    }
}

