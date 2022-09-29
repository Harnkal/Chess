package chess

class Board {
    var enPassant: Int? = null
    var board = MutableList(8) {
        MutableList(8) { " " }
    }

    fun update(entities: Map<String, MutableList<Piece>>){
        board = MutableList(8) {
            MutableList(8) { " " }
        }

        for (piece in entities["white"]!!) board[piece.y][piece.x] = piece.string
        for (piece in entities["black"]!!) board[piece.y][piece.x] = piece.string
    }

    fun render() {
        println("  +---+---+---+---+---+---+---+---+")
        for (i in board.lastIndex downTo 0) {
            println(board[i].joinToString(" | ", "${i + 1} | ", " |"))
            println("  +---+---+---+---+---+---+---+---+")
        }
        println("    a   b   c   d   e   f   g   h")
    }

}