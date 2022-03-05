package ru.sumin.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.composition.R
import com.example.composition.databinding.FragmentWelcomeBinding
import com.example.composition.presentation.ChooseLevelFragment
import java.lang.RuntimeException

class WelcomeFragment : Fragment() {

    //т.к. объект binding можно использовать в методе onDestroyView, то для этого создают
    //другую переменную _binding, которой присваивается ссылка на созданную View и затем
    //возвращается через get()
    //но управление уже идет через binding
    //затем она обнуляется в методе onDestroy()
    private var _binding: FragmentWelcomeBinding? = null
    private val binding: FragmentWelcomeBinding
        get() = _binding ?: throw RuntimeException("FragmentWelcomBinding = null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //создаем класс View, который в себе будет содержать ссылки на элементы в макете
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonUnderstand.setOnClickListener {
            launchChooseLevelFragment()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun launchChooseLevelFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, ChooseLevelFragment.newInstanse())
            .addToBackStack(ChooseLevelFragment.NAME)   //добавим в бэкстэк фрагмент с заданным именетм, который открываем, для того чтобы вернуться к нему
            .commit()
    }
}
