object Main {
  def main(args: Array[String]): Unit = {
    //println("Projeto PPM!")
    //val teste =randomChar(MyRandom(10))
    //println(teste._1)
    //println(randomChar(teste._2)._1)
    val board: Board = List(List('a', 'b', 'c'), List('d', 'e', 'f'), List('g', 'h', 'i'))
    val updatedBoard = fillOneCell(board,'x', (1,1))
    printBoard(updatedBoard)
  }

  def randomChar(rand: MyRandom): (Char, MyRandom) = {
    val aux = rand.nextInt(23)
    ((aux._1 + 97).toChar, aux._2)
  }

  type Board = List[List[Char]]
  type Coord2D = (Int, Int) //(row, column)

  object Direction extends Enumeration {
    type Direction = Value
    val North, South, East, West,
    NorthEast, NorthWest, SouthEast, SouthWest = Value
    //(…)
  }

  def fillOneCell(board: Board, letter: Char, coord: Coord2D): Board = {
    def findLine(boardx: Board, x: Int): Board = {
      x match {
        case 1 => fixLine(boardx.head, coord._2) :: boardx.tail

        case _ => boardx.head :: findLine(boardx.tail, x - 1)
      }
    }

    def fixLine(line: List[Char], y: Int): List[Char] = {
      y match {
        case 1 => letter :: line.tail
        case _ => line.head :: fixLine(line.tail, y - 1)
      }
    }

    findLine(board, coord._1)
    //val updatedRow = board(coord._1).updated(coord._2, letter)
    //board.updated(coord._1, updatedRow)
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