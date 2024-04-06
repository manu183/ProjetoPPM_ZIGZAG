
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

  //t4
  def completeBoardRandomly(board:Board, r:MyRandom, f: MyRandom => (Char, MyRandom)):(Board, MyRandom)={
    def fillLine(line:List[Char], r:MyRandom):(List[Char], MyRandom) =
    {
      line match {
        case Nil=>(Nil,r)
        case x::xs=>
          if (x>='a'&&x<='z') { val aux=fillLine(xs,r)
            (x::aux._1,aux._2)
          }
          else {  val aux=fillLine(xs,f(r)._2)
            (f(r)._1::aux._1,aux._2)
          }
      }

    }
    def fillBoard(board:Board,r: MyRandom):(Board,MyRandom)={board match {
      case Nil=>(Nil,r)
      case x::xs=>{ val aux=fillLine(x,r)
        val aux2=fillBoard(xs,aux._2)
        (List(aux._1) ++ aux2._1,aux._2)}
    }
    }
    fillBoard(board, r)
  }
}
