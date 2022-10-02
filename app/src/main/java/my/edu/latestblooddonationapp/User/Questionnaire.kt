package my.edu.latestblooddonationapp.User

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.view.*
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import my.edu.latestblooddonationapp.R
import my.edu.latestblooddonationapp.databinding.FragmentQuestionnaireBinding

class Questionnaire : Fragment() {

    private var _binding: FragmentQuestionnaireBinding? = null

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentQuestionnaireBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.buttonSubmit.setOnClickListener() {
            validateData()
        }

        return binding.root
    }

    private var question1 = ""
    private var question2 = ""
    private var question3 = ""
    private var question4 = ""

    private fun validateData() {
        val temp1: Int = binding.radioGroup1.checkedRadioButtonId
        val temp2: Int = binding.radioGroup2.checkedRadioButtonId
        val temp3: Int = binding.radioGroup3.checkedRadioButtonId
        val temp4: Int = binding.radioGroup4.checkedRadioButtonId
        val rad1 = view?.findViewById<RadioButton>(temp1)
        val rad2 = view?.findViewById<RadioButton>(temp2)
        val rad3 = view?.findViewById<RadioButton>(temp3)
        val rad4 = view?.findViewById<RadioButton>(temp4)
        val questions1 = rad1?.text.toString()
        val questions2 = rad2?.text.toString()
        val questions3 = rad3?.text.toString()
        val questions4 = rad4?.text.toString()
        question1 = binding.radioGroup1.checkedRadioButtonId.toString().trim()
        question2 = binding.radioGroup2.checkedRadioButtonId.toString().trim()
        question3 = binding.radioGroup3.checkedRadioButtonId.toString().trim()
        question4 = binding.radioGroup4.checkedRadioButtonId.toString().trim()
        question1 = questions1
        question2 = questions2
        question3 = questions3
        question4 = questions4


        if (binding.radioButtonYes1.isChecked) {
            binding.textViewError1.text = ""
        } else if (binding.radioButtonNo1.isChecked) {
            binding.textViewError1.text = ""
        } else {
            binding.textViewError1.text = "question 1 is required"
        }

        if (binding.radioButtonYes2.isChecked) {
            binding.textViewError2.text = ""
        } else if (binding.radioButtonNo2.isChecked) {
            binding.textViewError2.text = ""
        } else {
            binding.textViewError2.text = "question 2 is required"
        }

        if (binding.radioButtonYes3.isChecked) {
            binding.textViewError3.text = ""
        } else if (binding.radioButtonNo3.isChecked) {
            binding.textViewError3.text = ""
        } else {
            binding.textViewError3.text = "question 3 is required"
        }

        if (binding.radioButtonYes4.isChecked) {
            binding.textViewError4.text = ""
        } else if (binding.radioButtonNo4.isChecked) {
            binding.textViewError4.text = ""
        } else {
            binding.textViewError4.text = "question 4 is required"
        }

        if (binding.radioButtonYes1.isChecked && binding.radioButtonYes2.isChecked && binding.radioButtonNo3.isChecked && binding.radioButtonNo4.isChecked) {
            createQuestionnaireFirebase()
        } else if (binding.radioButtonNo1.isChecked || binding.radioButtonNo2.isChecked || binding.radioButtonYes3.isChecked || binding.radioButtonYes4.isChecked) {

            //confirm before create
            val builder = AlertDialog.Builder(context)

            builder.setTitle("Requirements not applicable")
                .setMessage("Please answer the question properly.")
                .setPositiveButton("Go Back") { a,b  ->
                    progressDialog = ProgressDialog(context)
                    progressDialog.setTitle("Please wait...")
                    progressDialog.setCanceledOnTouchOutside(false)
                    Toast.makeText(context, "Answer again", Toast.LENGTH_SHORT).show()


                }.setNegativeButton("Cancel") { a, d ->
                    a.dismiss()
                }
                .show()


        }



    }





    private fun createQuestionnaireFirebase() {
        progressDialog.show()
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        val timestamp = System.currentTimeMillis()

        val hashMap = HashMap<String, Any>()
        hashMap["id"] = "$timestamp"
        hashMap["question1"] = "$question1"
        hashMap["question2"] = "$question2"
        hashMap["question3"] = "$question3"
        hashMap["question4"] = "$question4"
        hashMap["timestamp"] = "$timestamp"
        hashMap["uid"] = "${firebaseAuth.uid}"


        val ref =
            Firebase.database("https://blooddonationkotlin-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Questionnaires")
        ref.child("$timestamp")
            .setValue(hashMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    progressDialog.dismiss()
                    findNavController().navigate(R.id.action_questionnaire_to_viewDonateBlood)
                } else {

                }

            }
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        // log out action not visible in login fragment
        menu.findItem(R.id.action_logOut).isVisible = false
    }
}