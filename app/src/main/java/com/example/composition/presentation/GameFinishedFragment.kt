package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.example.composition.R
import com.example.composition.databinding.FragmentGameFinishedBinding
import com.example.composition.domain.entity.GameResult
import java.lang.RuntimeException

class GameFinishedFragment : Fragment() {

    private lateinit var gameResult: GameResult

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding = null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val callback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                retryGame()
//            }
//        }
//        //вешаем слушатель на кнопку назад на экране смартфона
//        //viewLifecycleOwner нужен для того, чтобы слушатель удалялся при уничтожении фрагмента и
//        //в следствие чего не было бы краша или утечки памяти
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
//        binding.buttonRetry.setOnClickListener {
//            retryGame()
//        }
        //displayResult()
        setupClickListeners()
        bindViews()
    }

    //мой вариант
//    fun displayResult() {
//        if (!gameResult.winner)
//            binding.emojiResult.setImageResource(R.drawable.ic_sad)
//        binding.tvRequiredAnswers.text = String.format(
//                                    requireContext().resources.getString(R.string.required_score),
//                                    gameResult.gameSettings.minCountOfRightAnswers
//                                    )
//        binding.tvScoreAnswers.text = String.format(
//                                    requireContext().resources.getString(R.string.score_answers),
//                                    gameResult.countOfRightAnswers
//                                    )
//        binding.tvRequiredPercentage.text = String.format(
//                                    requireContext().resources.getString(R.string.required_percentage),
//                                    gameResult.gameSettings.minPercentOfRightAnswers
//                                    )
//        binding.tvScorePercentage.text = String.format(
//                                    requireContext().resources.getString(R.string.score_percentage),
//                                    gameResult.percentOfRightAnswer
//        )
//    }

    //Вариант Сумина
    private fun setupClickListeners() {
//        val callback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                retryGame()
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
    }

    private fun bindViews() {
        with(binding) {
            emojiResult.setImageResource(getSmileResId())
            tvRequiredAnswers.text = String.format(
                getString(R.string.required_score),
                gameResult.gameSettings.minCountOfRightAnswers
            )
            tvScoreAnswers.text = String.format(
                getString(R.string.score_answers),
                gameResult.countOfRightAnswers
            )
            tvRequiredPercentage.text = String.format(
                getString(R.string.required_percentage),
                gameResult.gameSettings.minPercentOfRightAnswers
            )
            tvScorePercentage.text = String.format(
                getString(R.string.score_percentage),
                getPercentOfRightAnswers()
            )
        }
    }

    private fun getSmileResId(): Int {
        return if (gameResult.winner) {
            R.drawable.ic_smile
        } else {
            R.drawable.ic_sad
        }
    }

    private fun getPercentOfRightAnswers() = with(gameResult) {
        if (countOfQuestions == 0) {
            0
        } else {
            ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun parseArgs() {
        requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
            gameResult = it
        }
    }

    //переходим к тому фрагменту, который нам нужен по имени
    fun retryGame() {
        //requireActivity().supportFragmentManager.popBackStack(ChooseLevelFragment.NAME, 0)
        //в данном случае мы будем переходить в фрагмент с игрой и сразу же удалять его с помощью
        //флага FragmentManager.POP_BACK_STACK_INCLUSIVE, для того, чтобы перед нами появился
        //фрагмент, который следует перед GameFragment.NAME
//        requireActivity().supportFragmentManager
//                        .popBackStack(GameFragment.NAME,
//                                     FragmentManager.POP_BACK_STACK_INCLUSIVE)
        //реализация с помощью библиотеки jetpacknavigation
        findNavController().popBackStack()
    }

    companion object {
        const val KEY_GAME_RESULT = "key_game_result"

        fun newInstanse(gameResult: GameResult): GameFinishedFragment {
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT, gameResult)
                }
            }
        }
    }
}