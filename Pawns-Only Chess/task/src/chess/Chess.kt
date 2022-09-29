package chess

import java.lang.Exception
import kotlin.math.absoluteValue


class Chess {

    private val firstPlayer: String
    private val secondPlayer: String

    private var board = Board()
    private val entities = mapOf ("white" to mutableListOf<Piece>(), "black" to mutableListOf<Piece>())

    private val validInput = "([a-h][1-8]){2}".toRegex()

    var state: State

    init {
        println("Pawns-Only Chess")
        println("First Player's name:")
        firstPlayer = readln()
        println("Second Player's name:")
        secondPlayer = readln()

        for (i in 0..7) {
            entities["white"]!!.add(Pawn(i, 1, Piece.Color.WHITE, 'W'))
            entities["black"]!!.add(Pawn(i, 6, Piece.Color.BLACK, 'B'))
        }

        board.update(entities)
        board.render()

        state = State.FIRST_PLAYER_TURN
    }

    fun tick() {
        val validMoves = getValidMoves(if (state == State.FIRST_PLAYER_TURN) "white" else "black", board)
        if (validMoves.isEmpty()) {
            println("Stalemate!\nBye!")
            state = State.GAME_ENDED
            return
        }

        println("${if (state == State.FIRST_PLAYER_TURN) firstPlayer else secondPlayer}'s turn:")
        val input = readln()

        if (input == "exit") {
            println("Bye!")
            state = State.GAME_ENDED
            return
        }

        if (!validInput.matches(input)) {
            println("Invalid Input")
            return
        }

        try {
            findEntity(input, if (state == State.FIRST_PLAYER_TURN) "white" else "black")
        } catch (e: Exception) {
            println("No ${if (state == State.FIRST_PLAYER_TURN) "white" else "black"} pawn at ${input.substring(0..1)}")
            return
        }

        if (input !in validMoves) {
            println("Invalid Input")
            return
        }

        val response = move(input, if (state == State.FIRST_PLAYER_TURN) "white" else "black")

        board.enPassant = null

        board.update(entities)
        board.render()

        if (response == 8) {
            println("${if (state == State.FIRST_PLAYER_TURN) "White" else "Black"} Wins!")
            println("Bye!")
            state = State.GAME_ENDED
            return
        } else if (response in 0..7) {
            board.enPassant = response
        }

        if (entities[if (state == State.FIRST_PLAYER_TURN) "black" else "white"]!!.isEmpty()) {
            println("${if (state == State.FIRST_PLAYER_TURN) "White" else "Black"} Wins!")
            println("Bye!")
            state = State.GAME_ENDED
        }

        state = if (state == State.FIRST_PLAYER_TURN) State.SECOND_PLAYER_TURN else State.FIRST_PLAYER_TURN
    }

    private fun getValidMoves(color: String, board: Board): List<String> {
        return List(entities[color]!!.size) {
            entities[color]!![it].listMoves(board)
        }.flatten()
    }

    private fun move(input: String, color: String): Int{
        val entity = findEntity(input, color)
        val (x0, y0, x1, y1) = Piece.inputToCoordinate(input)

        entity.x = x1
        entity.y = y1

        if (entity is Pawn) {
            if (color == "white" && y0 == 4 && y1 == 5 && x1 == board.enPassant) {
                entities["black"]!!.remove(findEntity(x1, y0, "black"))
            } else if (color == "black" && y0 == 3 && y1 == 2 && x1 == board.enPassant) {
                entities["white"]!!.remove(findEntity(x1, y0, "white"))
            }
            if (color == "white" && (x1 - x0).absoluteValue == 1 && board.board[y1][x1] == "B") {
                entities["black"]!!.remove(findEntity(x1, y1, "black"))
            }
            if (color == "black" && (x1 - x0).absoluteValue == 1 && board.board[y1][x1] == "W") {
                entities["white"]!!.remove(findEntity(x1, y1, "white"))
            }

            if ((y1 - y0).absoluteValue == 2) return x1

            if (y1 == 7 || y1 == 0) return 8
        }

        return -1
    }

    private fun findEntity(input: String, color: String): Piece {
        for (entity in entities[color]!!) {
            if (entity.isInOrigin(input)) return entity
        }
        throw Exception("you shouldn't be here!")
    }

    private fun findEntity(x: Int, y: Int, color: String): Piece {
        for (entity in entities[color]!!) {
            if (entity.isInOrigin(x, y)) return entity
        }
        throw Exception("you shouldn't be here!")
    }

    enum class State {
        FIRST_PLAYER_TURN,
        SECOND_PLAYER_TURN,
        GAME_ENDED
    }
}

