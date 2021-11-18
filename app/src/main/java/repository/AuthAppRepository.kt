package repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthAppRepository {
    private var auth : FirebaseAuth
    val userLiveData : MutableLiveData<FirebaseUser> = MutableLiveData<FirebaseUser>()
    val lockedOutData : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val errorMessage : MutableLiveData<String> = MutableLiveData<String>()

    init {
        userLiveData.value = FirebaseAuth.getInstance().currentUser
        auth = FirebaseAuth.getInstance()
    }

    fun register(email : String, password : String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                userLiveData.value = FirebaseAuth.getInstance().currentUser
                lockedOutData.value = false
            }else {
                errorMessage.value = task.exception?.message
                lockedOutData.value = true
            }
        }
    }

    fun signIn(email : String, password : String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                userLiveData.value = FirebaseAuth.getInstance().currentUser
                lockedOutData.value = false
            }else {
                errorMessage.value = task.exception?.message
                lockedOutData.value = true
            }
        }
    }

    fun signOut(){
        auth.signOut()
        lockedOutData.value = true
    }
}
