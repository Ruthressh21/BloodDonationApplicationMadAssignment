package my.edu.latestblooddonationapp.Admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import my.edu.latestblooddonationapp.R
import my.edu.latestblooddonationapp.databinding.FragmentAdminHomeBinding

class Home : Fragment() {

    private var _binding: FragmentAdminHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAdminHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageViewDonorRequest.setOnClickListener{
            findNavController().navigate(R.id.action_nav_home_to_donorRequest)
        }
    }

}