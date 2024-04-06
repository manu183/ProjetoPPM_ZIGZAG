import Utils.{Board, Coord2D}

object Tasks {
  //t1
  def randomChar(rand: MyRandom): (Char, MyRandom) = {
    val aux = rand.nextInt(23)
    ((aux._1 + 97).toChar, aux._2)
  }

  //t2
  def fillOneCell(board: Board, letter: Char, coord: Coord2D): Board = {
    def findLine(boardx: Board, x: Int): Board = {
      x match {
        case 0 => fixLine(boardx.head, coord._2) :: boardx.tail

        case _ => boardx.head :: findLine(boardx.tail, x - 1)
      }
    }

    def fixLine(line: List[Char], y: Int): List[Char] = {
      y match {
        case 0 => letter :: line.tail
        case _ => line.head :: fixLine(line.tail, y - 1)
      }
    }

    findLine(board, coord._1)
    //val updatedRow = board(coord._1).updated(coord._2, letter)
    //board.updated(coord._1, updatedRow)
  }
}
