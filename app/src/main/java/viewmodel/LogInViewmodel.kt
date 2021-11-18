package viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import repository.AuthAppRepository

class LogInViewmodel: ViewModel() {
    private val repo: AuthAppRepository = AuthAppRepository()
    val userLiveData: MutableLiveData<FirebaseUser> = MutableLiveData<FirebaseUser>()
    val errorMessage: MutableLiveData<String> = MutableLiveData<String>()
    val loggedOut: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    public fun createUser(email : String, password : String){
        repo.register(email, password)
        userLiveData.value = repo.userLiveData.value
        errorMessage.value = repo.errorMessage.value
        loggedOut.value = repo.lockedOutData.value
    }

    public fun signIn(email: String, password: String){
        repo.signIn(email,password)
        userLiveData.value = repo.userLiveData.value
        errorMessage.value = repo.errorMessage.value
        loggedOut.value = repo.lockedOutData.value
    }
    public fun signOut(){
        repo.signOut()
        loggedOut.value = repo.lockedOutData.value
    }
}