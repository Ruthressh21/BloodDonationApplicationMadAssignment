package my.edu.latestblooddonationapp.Admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import my.edu.latestblooddonationapp.databinding.FragmentViewDonorRequestBinding

class ViewDonorRequest : Fragment() {

    private var _binding: FragmentViewDonorRequestBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentViewDonorRequestBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }
}