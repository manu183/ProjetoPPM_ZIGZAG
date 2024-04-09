import Utils.Coord2D

import scala.sys.exit

object Utils {

  type Board = List[List[Char]]
  type Coord2D = (Int, Int) //(row, column)

  object Direction extends Enumeration {
    type Direction = Value
    val North, South, East, West,
    NorthEast, NorthWest, SouthEast, SouthWest = Value

    def addDirection(coord: Coord2D, dir: Direction): Coord2D = {
      dir match {
        case North => (coord._1 - 1, coord._2)
        case South => (coord._1 + 1, coord._2)
        case East => (coord._1, coord._2 - 1)
        case West => (coord._1, coord._2 + 1)
        case NorthEast => (coord._1 - 1, coord._2 - 1)
        case NorthWest => (coord._1 - 1, coord._2 + 1)
        case SouthEast => (coord._1 + 1, coord._2 - 1)
        case SouthWest => (coord._1 + 1, coord._2 + 1)
      }
    }
  }

  def printBoard(board: Board): Unit = {
    board.map(x => println(x.mkString("  ")))
  }

  def showTakeChoice(): Unit = {
    println("1:Iniciar Tabuleiro")
    println("2:Alterar cor das letras")
    println("3:Sair")
    scala.io.StdIn.readInt() match {
      case 1 =>
      case 2 =>
        colorMenu()
        showTakeChoice()
      case 3 => exit(1)
      case _ => showTakeChoice()
    }

  }
  def jogarMenu():Int={
    println("1-Inserir Palavra e direcao, formato-PALAVRA (y,x)")
    println("2-Reiniciar")
    println("0-Sair")
    scala.io.StdIn.readInt() match {
      case 1=>1
      case 2=>2
      case 0=>0
      case 3=>3
    }
  }
  def colorMenu(): Unit = {
    println("1:Vermelho")
    println("2:Azul")
    println("3:Verde")
    println("0:Sair")
    scala.io.StdIn.readInt() match {
      case 1 => print("\u001B[31m")
      case 2 => print("\u001B[34m")
      case 3 => print("\u001B[32m")
      case 0 =>
      case _ => colorMenu()
    }
  }
  def getPlay():(String,Coord2D)={

  }

}
