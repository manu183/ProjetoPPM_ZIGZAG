
object Utils {

  type Board = List[List[Char]]
  type Coord2D = (Int, Int) //(row, column)

  object Direction extends Enumeration {
    type Direction = Value
    val North, South, East, West,
    NorthEast, NorthWest, SouthEast, SouthWest = Value
    def addDirection(coord:Coord2D,dir:Direction):Coord2D={
      dir match {
        case North=>(coord._1-1,coord._2)
        case South=>(coord._1+1,coord._2)
        case East=> (coord._1,coord._2-1)
        case West=>(coord._1,coord._2+1)
        case NorthEast=>(coord._1-1,coord._2-1)
        case NorthWest=>(coord._1-1,coord._2+1)
        case SouthEast=>(coord._1+1,coord._2-1)
        case SouthWest=>(coord._1+1,coord._2+1)
      }
    }
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
  def printBoard1(board:Board):Unit={
    board.map(x=>println(x.mkString("  ")))
  }
}
