package _01_Delegates

interface Api {
    fun getUser(id: Int): String
    fun updateUser(id: Int, name: String)
    fun getPosts(userId: Int): List<String>
    fun likePost(postId: Int)
    fun deletePost(postId: Int)
}



class FakeApi : Api {
    private val users = mutableMapOf(1 to "Alice", 2 to "Bob")
    private val posts = mutableMapOf(
        1 to mutableListOf("Hello", "Kotlin"),
        2 to mutableListOf("Jetpack", "Compose")
    )

    override fun getUser(id: Int): String = users[id] ?: "<no user>"

    override fun updateUser(id: Int, name: String) {
        users[id] = name
    }

    override fun getPosts(userId: Int): List<String> =
        posts[userId]?.toList() ?: emptyList()

    override fun likePost(postId: Int) {
        println("(server) liked postId=$postId")
    }

    override fun deletePost(postId: Int) {
        println("(server) deleted postId=$postId")
    }
}






class LoggingApi_NoDelegation(
    private val api: Api
) : Api {

    override fun getUser(id: Int): String {
        println("getUser($id)")
        return api.getUser(id)
    }

    override fun updateUser(id: Int, name: String) {
        println("updateUser($id, $name)")
        api.updateUser(id, name)
    }

    override fun getPosts(userId: Int): List<String> {
        println("getPosts($userId)")
        return api.getPosts(userId)
    }

    override fun likePost(postId: Int) {
        println("likePost($postId)")
        api.likePost(postId)
    }

    override fun deletePost(postId: Int) {
        println("deletePost($postId)")
        api.deletePost(postId)
    }
}


fun main() {
    val base: Api = FakeApi()

    val a1: Api = LoggingApi_NoDelegation(base)
    println(a1.getUser(1))
    a1.updateUser(1, "Alicia")
    println(a1.getPosts(1))
    a1.likePost(42)
    a1.deletePost(13)

}