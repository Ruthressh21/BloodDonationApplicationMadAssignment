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
import my.edu.latestblooddonationapp.databinding.FragmentRowUserAccountBinding

class AdapterUserAccount :RecyclerView.Adapter<AdapterUserAccount.HolderUserAccount>,
    Filterable {

    private lateinit var progressDialog: ProgressDialog
    private val context: Context
    public var categoryArrayList: ArrayList<ModelUserAccount>
    private lateinit var binding: FragmentRowUserAccountBinding
    private var filterList: ArrayList<ModelUserAccount>


    private var filter: FilterUserAccount? = null

    //constructor
    constructor(context: Context, categoryArrayList: ArrayList<ModelUserAccount>) {
        this.context = context
        this.categoryArrayList = categoryArrayList
        this.filterList = categoryArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderUserAccount {
        binding = FragmentRowUserAccountBinding.inflate(LayoutInflater.from(context), parent, false)

        return HolderUserAccount(binding.root)
    }

    override fun onBindViewHolder(holder: HolderUserAccount, position: Int) {
        //get data
        val model = categoryArrayList[position]
        val uid = model.uid
        val fullName = model.name
        val gender = model.gender
        val bloodType = model.bloodType
        val address = model.address
        val dateOfBirth = model.dateBirth
        val phoneNumber = model.phoneNum
        val userType = model.userType
        val emailAddress = model.email
        val password = model.password


        //set data
        holder.uid.text = uid
        holder.fullName.text = fullName
        holder.gender.text = gender
        holder.bloodType.text = bloodType
        holder.address.text = address
        holder.dateOfBirth.text = dateOfBirth
        holder.phoneNumber.text = phoneNumber
        holder.userType.text = userType
        holder.emailAddress.text = emailAddress
        holder.password.text = password


        //handle click, delete category
        holder.deleteBtn.setOnClickListener{
            //confirm before delete
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete")
                .setMessage("Are you sure you want to delete this user account?")
                .setPositiveButton("Confirm"){a,d->
                    progressDialog = ProgressDialog(context)
                    progressDialog.setTitle("Deleting...")
                    progressDialog.setCanceledOnTouchOutside(false)
                    deleteUserAccount(model, holder)
                }
                .setNegativeButton("Cancel"){a,d->
                    a.dismiss()
                }
                .show()
        }
    }

    private fun deleteUserAccount(model: ModelUserAccount, holder: HolderUserAccount) {
        progressDialog.dismiss()
        val uid = model.uid
        //Firebase DB > Categories > categoryId
        val ref = Firebase.database("https://blooddonationkotlin-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")
        ref.child(uid)
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


    //ViewHolder class to hold/init UI views for fragment_row_blood_donation_request.xml
    inner class HolderUserAccount(itemView: View): RecyclerView.ViewHolder(itemView){
        //init ui views
        var uid   : TextView = binding.textViewUID
        var fullName : TextView = binding.textViewFullName
        var gender : TextView = binding.textViewGender2
        var bloodType : TextView = binding.textViewBloodType
        var address   : TextView = binding.textViewAddress
        var dateOfBirth : TextView = binding.textViewDateOfBirth
        var phoneNumber : TextView = binding.textViewPhoneNumber
        var userType : TextView = binding.textViewUserType
        var emailAddress : TextView = binding.textViewEmailAddress
        var password : TextView = binding.textViewPassword
        var deleteBtn : ImageButton = binding.imageButtonDelete2

    }

    override fun getFilter(): Filter {
        if(filter == null){
            filter = FilterUserAccount(filterList, this)
        }
        return filter as FilterUserAccount
    }

}

