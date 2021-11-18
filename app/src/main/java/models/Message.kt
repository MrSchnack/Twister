package models

import org.w3c.dom.Comment

data class Message(var id: Int, var content: String, var user: String, var totalComments: Int) {
    override fun toString(): String {
        return "$user : $content : $totalComments"
    }
}