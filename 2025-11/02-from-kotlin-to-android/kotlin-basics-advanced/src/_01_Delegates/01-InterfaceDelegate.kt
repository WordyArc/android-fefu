package _01_Delegates

class LoggingApi_WithDelegation(
    private val api: Api
) : Api by api {

    override fun likePost(postId: Int) {
        println("likePost($postId) [special logging]")
        api.likePost(postId)
    }
}

fun main() {
    val base: Api = FakeApi()

    val a2: Api = LoggingApi_WithDelegation(base)
    println(a2.getUser(2))
    a2.updateUser(2, "Bobby")
    println(a2.getPosts(2))

    a2.likePost(99)
    a2.deletePost(77)
}