package chess

class Pawn(x: Int, y: Int, color: Color, type: Char): Piece(x, y, color, type) {

    override fun listMovesWhite(board: Board): List<String> {
        val moves = mutableListOf<String>()

        if (board.board[y + 1][x] == " ") moves.add(coordinatesToInput(x, y, x, y + 1))
        if (x + 1 <= 7 && board.board[y + 1][x + 1] == "B") moves.add(coordinatesToInput(x, y, x + 1, y + 1))
        if (x - 1 >= 0 && board.board[y + 1][x - 1] == "B") moves.add(coordinatesToInput(x, y, x - 1, y + 1))
        if (y == 1 && board.board[y + 1][x] == " " && board.board[y + 2][x] == " ") moves.add(coordinatesToInput(x, y, x, y + 2))
        if (y == 4) {
            if (board.enPassant == x + 1) moves.add(coordinatesToInput(x, y, x + 1, y + 1))
            if (board.enPassant == x - 1) moves.add(coordinatesToInput(x, y, x - 1, y + 1))
        }

        return moves
    }

    override fun listMovesBlack(board: Board): List<String> {
        val moves = mutableListOf<String>()

        if (board.board[y - 1][x] == " ") moves.add(coordinatesToInput(x, y, x, y - 1))
        if (x + 1 <= 7 && board.board[y - 1][x + 1] == "W") moves.add(coordinatesToInput(x, y, x + 1, y - 1))
        if (x - 1 >= 0 && board.board[y - 1][x - 1] == "W") moves.add(coordinatesToInput(x, y, x - 1, y - 1))
        if (y == 6 && board.board[y - 1][x] == " " && board.board[y - 2][x] == " ") moves.add(coordinatesToInput(x, y, x, y - 2))
        if (y == 3) {
            if (board.enPassant == x + 1) moves.add(coordinatesToInput(x, y, x + 1, y - 1))
            if (board.enPassant == x - 1) moves.add(coordinatesToInput(x, y, x - 1, y - 1))
        }

        return moves
    }
}