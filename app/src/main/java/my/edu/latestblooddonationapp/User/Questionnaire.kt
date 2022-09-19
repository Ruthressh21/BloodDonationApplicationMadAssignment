package my.edu.latestblooddonationapp.User

import my.edu.latestblooddonationapp.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import my.edu.latestblooddonationapp.databinding.FragmentQuestionaireBinding

class Questionnaire : Fragment() {
    
    private var _binding: FragmentQuestionaireBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentQuestionaireBinding.inflate(inflater, container, false)

        return binding.root
    }

}