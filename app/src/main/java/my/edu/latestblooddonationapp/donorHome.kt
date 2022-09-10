package my.edu.latestblooddonationapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import my.edu.latestblooddonationapp.databinding.FragmentDonorHomeBinding
import my.edu.latestblooddonationapp.databinding.FragmentLoginBinding

class donorHome : Fragment() {
    // TODO: Rename and change types of parameters

    private var _binding: FragmentDonorHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDonorHomeBinding.inflate(inflater, container, false)


        binding.button.setOnClickListener{
            findNavController().navigate(R.id.action_donorHome3_to_editProfileFragment3)
        }

        binding.btnViewProfile.setOnClickListener{
            findNavController().navigate(R.id.action_donorHome3_to_viewProfileFragment)
        }

        return binding.root
    }
}
