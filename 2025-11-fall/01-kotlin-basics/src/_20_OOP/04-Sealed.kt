package _20_OOP

sealed class Result

data class Success(val data: String) : Result()
data class Error(val message: String) : Result()
object Loading : Result()


fun handleResult(result: Result) {
    when (result) {
        is Success -> println("Success: ${result.data}")
        is Error   -> println("Error: ${result.message}")
        Loading    -> println("Loading...")
        // non else, compiler smart
    }
}


fun main() {
    handleResult(Loading)
}