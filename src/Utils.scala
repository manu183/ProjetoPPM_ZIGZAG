
object Utils {

  type Board = List[List[Char]]
  type Coord2D = (Int, Int) //(row, column)

  object Direction extends Enumeration {
    type Direction = Value
    val North, South, East, West,
    NorthEast, NorthWest, SouthEast, SouthWest = Value
    //(…)
  }


  def printBoard(board: Board) = {
    def printRow[E](row: List[E]): Unit = {
      row match {
        case Nil => println()
        case head :: tail => {
          print(head + "  ")
          printRow(tail)
        }
      }
    }

    def printBoardRows(board: Board): Unit = board match {
      case Nil =>
      case row :: tail =>
        printRow(row)
        printBoardRows(tail)
    }

    printBoardRows(board)
  }

}
