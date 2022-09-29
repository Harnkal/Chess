package chess

abstract class Piece (var x: Int, var y: Int, private val color: Color, type: Char) {
    val string = "$type"

    enum class Color {
        WHITE,
        BLACK
    }

    fun coordinatesToInput(x0: Int, y0: Int, x1: Int, y1: Int): String {
        val input = mutableListOf<Char>()
        input.add((x0 + 97).toChar())
        input.add(Character.forDigit(y0 + 1, 10))
        input.add((x1 + 97).toChar())
        input.add(Character.forDigit(y1 + 1, 10))

        return input.joinToString("")
    }

    fun isInOrigin(input: String): Boolean {
        val (x0, y0, _, _) = inputToCoordinate(input)
        if (x0 != x) return false
        if (y0 != y) return false

        return true
    }

    fun isInOrigin(x0: Int, y0: Int): Boolean {
        if (x0 != x) return false
        if (y0 != y) return false

        return true
    }

    fun listMoves(board: Board): List<String> {
        return when (color) {
            Color.WHITE -> listMovesWhite(board)
            Color.BLACK -> listMovesBlack(board)
        }
    }

    abstract fun listMovesWhite(board: Board): List<String>
    abstract fun listMovesBlack(board: Board): List<String>

    companion object {
        fun inputToCoordinate(input: String): Array<Int> {
            val x0 = input[0].code - 97
            val y0 = input[1].digitToInt() - 1
            val x1 = input[2].code - 97
            val y1 = input[3].digitToInt() - 1

            return arrayOf(x0, y0, x1, y1)
        }
    }
}