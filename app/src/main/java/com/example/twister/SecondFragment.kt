package com.example.twister

import Adapters.MessageAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.twister.databinding.FragmentSecondBinding
import com.google.gson.Gson
import models.Message
import viewmodel.MessageViewmodel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val messageViewModel : MessageViewmodel by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        messageViewModel.messageLiveData.observe(viewLifecycleOwner){
            message -> binding.Recycler.adapter = MessageAdapter<Message>(message){
            val gson = Gson()
            val action = SecondFragmentDirections.actionSecondFragmentToCommentFragment(gson.toJson(it))
            findNavController().navigate(action)


        }
    }
        messageViewModel.reload()
        binding.ActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_SecondFragment_to_addMessageFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}