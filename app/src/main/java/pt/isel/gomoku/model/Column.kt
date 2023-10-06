package pt.isel.gomoku.model

class Column private constructor(var symbol: Char) {

    var index = symbol.toColumnIdx()

    companion object {
        val values = (0 until BOARD_DIM).map { Column((it + A_CHAR_CODE).toChar()) }
        operator fun invoke(symbol: Char): Column {
            return values[symbol.toColumnIdx()]
        }
    }
}

/**
 * Transforms column's symbol into row
 */
fun Char.charToColumn(): Column {
    val colIdx = this.toColumnIdx()
    require(colIdx != -1)
    return Column.values[colIdx]
}

/**
 * Transforms column's index into row
 */
fun Int.indexToColumn(): Column {
    require(this in 0 until BOARD_DIM)
    return Column.values[this]
}

/**
 * Transforms column's symbol into index
 */
fun Char.toColumnIdx(): Int {
    val result = this.code - A_CHAR_CODE
    if (this.code !in 'a'.code..('a' + BOARD_DIM - 1).code) return -1
    return result
}