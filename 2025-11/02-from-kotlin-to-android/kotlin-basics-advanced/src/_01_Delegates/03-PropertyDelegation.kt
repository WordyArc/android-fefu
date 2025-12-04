package _01_Delegates

import java.util.UUID

class UserService_NoLazy {
    private var _token: String? = null

    val token: String
        get() {
            if (_token == null) {
                println("Fetching token...")
                _token = "token_${UUID.randomUUID()}"
            }
            return _token!!
        }
}


class UserService {
    val token: String by lazy {
        println("Fetching token...")
        "token_${UUID.randomUUID()}"
    }
}

fun main() {
    val sn = UserService_NoLazy()
    println(sn.token)
    println(sn.token)


    val s = UserService()
    println(s.token)
    println(s.token) // возьмётся из кеша
}
