package com.example.twister

import Adapters.CommentAdapter
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.twister.databinding.FragmentAddCommentBinding
import com.google.gson.Gson
import models.Comment
import models.Message
import viewmodel.LogInViewmodel
import viewmodel.MessageViewmodel

class CommentFragment : Fragment() {
    private var _binding: FragmentAddCommentBinding? = null
    private val binding get() = _binding!!
    val message: MutableLiveData<Message> = MutableLiveData<Message>()
    val comment: MutableLiveData<Comment> = MutableLiveData<Comment>()
    val messageViewModel: MessageViewmodel by activityViewModels()
    val logInViewmodel: LogInViewmodel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddCommentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeDown.setOnRefreshListener{
            messageViewModel.getComments(message.value?.id!!)
            binding.swipeDown.isRefreshing = false
        }
        val gson = Gson()
        message.value = gson.fromJson(arguments?.getString("comment"), Message::class.java)
        messageViewModel.getComments(message.value?.id!!)
        binding.DeleteCommentButton.setOnClickListener{
            if (logInViewmodel.userLiveData.value?.email==message.value?.user){
                messageViewModel.delete(message.value?.id!!)
                findNavController().popBackStack()
            }

        }
        binding.AddCommentButton.setOnClickListener{
            val builder : AlertDialog.Builder = AlertDialog.Builder(requireContext())
            val input = EditText(requireContext())
            builder.setView(input)
            builder.setMessage("Add comment?").setPositiveButton("OK", { dialog, id ->
                messageViewModel.addComment(message.value?.id!!,Comment(1, message.value?.id!!,input.text.toString(),logInViewmodel.userLiveData.value?.email.toString() ))
                messageViewModel.getComments(message.value?.id!!)
            }).setNegativeButton("Cancel", { dialog, id ->
                dialog.cancel()
            })
            builder.show()
        }
        message.observe(viewLifecycleOwner,{
            binding.TextMessage.text=it.content
        })
        messageViewModel.commentsLiveData.observe(viewLifecycleOwner,{
            binding.RecyclerComment.adapter=CommentAdapter<Comment>(it){
                if (logInViewmodel.userLiveData.value?.email==it.user){
                    messageViewModel.deleteComments(message.value?.id!!,it.id)
                }
            }

        })
    }
}