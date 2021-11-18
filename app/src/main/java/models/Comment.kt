package models

data class Comment(val id: Int,val messageId: Int, val content: String, val user: String) {

    override fun toString(): String {
        return "$user : $content"
    }
}