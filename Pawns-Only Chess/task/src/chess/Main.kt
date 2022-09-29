package chess

fun main() {
    val game = Chess()
    while (game.state != Chess.State.GAME_ENDED) {
        game.tick()
    }
}