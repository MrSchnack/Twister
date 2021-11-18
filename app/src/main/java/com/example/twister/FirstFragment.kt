package com.example.twister

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.twister.databinding.FragmentFirstBinding
import viewmodel.LogInViewmodel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {


    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val authenticatorViewmodel: LogInViewmodel by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.LoginButton.setOnClickListener {
            val email = binding.EmailTekst.text.toString().trim()
            val password = binding.PasswordTekst.text.toString().trim()

            if (email.isNullOrEmpty()){
                binding.EmailTekst.error = "Ikke korrekt input"
                return@setOnClickListener
            }
            if (password.isNullOrEmpty()) {
                binding.PasswordTekst.error = "Ikke korrekt input"
                return@setOnClickListener
            }

            authenticatorViewmodel.signIn(email, password)
            if (authenticatorViewmodel.loggedOut.value == false) {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }

        }
        binding.CreateButton.setOnClickListener{
            val email = binding.EmailTekst.text.toString().trim()
            val password = binding.PasswordTekst.text.toString().trim()

            if (email.isNullOrEmpty()){
                binding.EmailTekst.error = "Ikke korrekt input"
                return@setOnClickListener
            }
            if (password.isNullOrEmpty()) {
                binding.PasswordTekst.error = "Ikke korrekt input"
                return@setOnClickListener
            }
            authenticatorViewmodel.createUser(email, password)
            if (authenticatorViewmodel.loggedOut.value == false) {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
         }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}