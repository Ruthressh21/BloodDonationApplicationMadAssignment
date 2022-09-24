package my.edu.latestblooddonationapp.User

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

        _binding = FragmentQuestionnaireBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.buttonSubmit.setOnClickListener(){
            validateData()
        }

        return binding.root
    }

    private var question1 = ""
    private var question2 = ""
    private var question3 = ""
    private var question4 = ""

    private fun validateData() {

        question1 = binding.editTextQuestion1.text.toString().trim()
        question2 = binding.editTextQuestion2.text.toString().trim()
        question3 = binding.editTextQuestion3.text.toString().trim()
        question4 = binding.editTextQuestion4.text.toString().trim()

        if (question1.isEmpty()) {
            binding.editTextQuestion1.error = "Question 1 is required"
        } else if (question2.isEmpty()) {
            binding.editTextQuestion2.error = "Question 2 is required"
        } else if (question3.isEmpty()) {
            binding.editTextQuestion3.error = "Question 3 is required"
        }  else if (question4.isEmpty()) {
            binding.editTextQuestion4.error = "Question 4 is required"
        } else {
            createQuestionnaireFirebase()
        }
    }


    private fun createQuestionnaireFirebase() {
        progressDialog.show()

        val timestamp = System.currentTimeMillis()

        val hashMap = HashMap<String, Any>()
        hashMap["id"] = "$timestamp"
        hashMap["question1"] = "$question1"
        hashMap["question2"] = "$question2"
        hashMap["question3"] = "$question3"
        hashMap["question4"] = "$question4"
        hashMap["timestamp"] = "$timestamp"
        hashMap["uid"] = "${firebaseAuth.uid}"


        val ref = Firebase.database("https://blooddonationkotlin-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Questionnaires")
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
}