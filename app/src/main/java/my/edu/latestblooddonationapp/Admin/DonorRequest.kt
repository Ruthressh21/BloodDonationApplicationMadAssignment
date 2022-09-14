package my.edu.latestblooddonationapp.Admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import my.edu.latestblooddonationapp.R
import my.edu.latestblooddonationapp.databinding.FragmentDonorRequestBinding

class DonorRequest : Fragment() {

    private var _binding: FragmentDonorRequestBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDonorRequestBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnViewDonorRequest.setOnClickListener(){
            findNavController().navigate(R.id.action_donorRequest_to_viewDonorRequest)
        }
        binding.btnCreateDonorRequest.setOnClickListener(){
            findNavController().navigate(R.id.action_donorRequest_to_createDonorRequest)
        }
        binding.btnEditDonorRequest.setOnClickListener(){
            findNavController().navigate(R.id.action_donorRequest_to_editDonorRequest)
        }
        binding.btnDeleteDonorRequest.setOnClickListener(){
            findNavController().navigate(R.id.action_donorRequest_to_deleteDonorRequest)
        }
    }

}