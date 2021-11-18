package viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import models.Comment
import models.Message
import repository.MessageRespository
import repository.MessageService

class MessageViewmodel : ViewModel() {
    private val repository = MessageRespository()
    val messageLiveData: MutableLiveData<List<Message>> = repository.messageLiveData
    val commentsLiveData: MutableLiveData<List<Comment>> = repository.commentsLiveData
    val errorMessageLiveData: MutableLiveData<String> = repository.errorMessage
    val updateMessageLiveData: MutableLiveData<String> = repository.updateMessage

    init {
        reload()
    }

    fun reload() {
        repository.getPosts()
    }

    operator fun get(index: Int): Message? {
        return messageLiveData.value?.get(index)
    }

    fun add(message: Message) {
        repository.add(message)
    }

    fun delete(id: Int) {
        repository.delete(id)
    }

    fun getComments(messageId: Int) {
        repository.getAllComments(messageId)
    }

    fun deleteComments(messageId: Int, commentId: Int) {
        repository.deleteComments(messageId, commentId)
    }

    fun addComment(messageId: Int, comment: Comment) {
        repository.postComment(messageId, comment)

    }
}