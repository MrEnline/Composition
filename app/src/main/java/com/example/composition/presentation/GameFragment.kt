package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.composition.R
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.domain.entity.GameResult
import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level
import java.lang.RuntimeException

class GameFragment : Fragment() {

    private lateinit var level: Level
    private lateinit var gameViewModel: GameViewModel

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding = null")
    private var buttons: Array<TextView>? = null
    private var length: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        buttons = arrayOf(
            binding.tvOption1,
            binding.tvOption2,
            binding.tvOption3,
            binding.tvOption4,
            binding.tvOption5,
            binding.tvOption6
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]
        gameViewModel.getGameSettings(level)
        gameViewModel.question.observe(viewLifecycleOwner) {
            binding.tvSum.setText(it.sum)
            binding.tvLeftNumber.setText(it.visibleNumber)
            length = buttons?.size?.minus(1)!!
            for(i in 0..length!!) {
                buttons?.get(i)?.setText(it.options[i+1])
            }
        }
        addClickListeners()
//        binding.tvOption1.setOnClickListener {
//            launchGameFinishedFragment(
//                GameResult(true,
//                    0,
//                       0,
//                        GameSettings(0, 0, 0, 0)
//                )
//            )
//        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

   fun addClickListeners() {
       for (i in 0..length) {
           buttons?.get(i)?.setOnClickListener {
               gameViewModel.generateQuestion()
           }
       }
   }

    private fun parseArgs() {
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstanse(gameResult))
            .addToBackStack(null)
            .commit()
    }

    companion object {

        const val NAME = "GameFragment"
        private const val KEY_LEVEL = "level"

        fun newInstanse(level: Level): GameFragment {
            return GameFragment().apply {
                this.arguments = Bundle().apply {
                    this.putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }
}