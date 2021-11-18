package com.example.twister

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.twister.databinding.FragmentAddMessageBinding
import models.Message
import viewmodel.LogInViewmodel
import viewmodel.MessageViewmodel


class AddMessageFragment : Fragment() {
    val messageViewmodel: MessageViewmodel by activityViewModels()
    val logInViewmodel: LogInViewmodel by activityViewModels()
    private var _binding : FragmentAddMessageBinding? = null
    private val binding get()= _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddMessageBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.PostMessage.setOnClickListener{
            val message = binding.MessageText.text.toString().trim()
            if (message.isNullOrEmpty()){
                binding.MessageText.error = "Du har ikke skrevet en besked"
                return@setOnClickListener
            } else {
                val addMessage = Message(1, message, logInViewmodel.userLiveData.value?.email.toString(),0 )
                messageViewmodel.add(addMessage)
                findNavController().popBackStack()
            }
        }
    }
}