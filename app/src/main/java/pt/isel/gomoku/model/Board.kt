package pt.isel.gomoku.model

import pt.isel.gomoku.domain.model.Dot

class Board(
    val moves: List<Move> = listOf(
        Move(Player.WHITE,Dot(0,0)),
        Move(Player.WHITE, Dot(1,1)),
        Move(Player.WHITE, Dot(1,2)),
        Move(Player.WHITE, Dot(1,3)),
        Move(Player.BLACK, Dot(9,1)),
        Move(Player.BLACK, Dot(2,2)),
        Move(Player.WHITE, Dot(BOARD_DIM - 1,BOARD_DIM - 1)),
        Move(Player.WHITE,Dot(BOARD_DIM - 1,0)),
        Move(Player.WHITE,Dot(0,BOARD_DIM - 1))
    ),
    val turn: Player = Player.WHITE
)