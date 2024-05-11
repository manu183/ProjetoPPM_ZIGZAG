import Utils.Coord2D

import java.io.{File, PrintWriter}
import scala.annotation.tailrec
import scala.io.Source
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
        case East => (coord._1, coord._2 + 1)
        case West => (coord._1, coord._2 - 1)
        case NorthEast => (coord._1 - 1, coord._2 + 1)
        case NorthWest => (coord._1 - 1, coord._2 - 1)
        case SouthEast => (coord._1 + 1, coord._2 + 1)
        case SouthWest => (coord._1 + 1, coord._2 - 1)
      }
    }


  }

def getDirection(coord1: Coord2D, coord2: Coord2D): Direction.Direction = {
  //As coordenadas são (y,x)!!!!!!!!!

  if (coord1 == coord2) throw new IllegalArgumentException("Coordenadas inválidas. Direção não encontrada.")

  if (coord2._1 < coord1._1) {
    if (coord1._2 > coord2._2) Direction.NorthWest
    else if (coord1._2 < coord2._2) Direction.NorthEast
    else Direction.North
  }
  else if (coord2._1 > coord1._1) {
    if (coord1._2 > coord2._2) Direction.SouthWest
    else if (coord1._2 < coord2._2) Direction.SouthEast
    else Direction.South
  }
  else if (coord1._1 == coord2._1) {
    if (coord1._2 > coord2._2) Direction.West
    else if (coord1._2 < coord2._2) Direction.East
    else throw new IllegalArgumentException("Coordenadas inválidas. Direção não encontrada.")
  }
  else throw new IllegalArgumentException("Coordenadas inválidas. Direção não encontrada.")
}


  def printBoard(board: Board): Unit = {
    board.map(x => println(x.mkString("  ")))
  }

  def firstMenu(): Unit = {
    println("1:Iniciar Tabuleiro")
    println("2:Alterar cor das letras")
    println("3:Sair")
    scala.io.StdIn.readInt() match {
      case 1 =>
      case 2 =>
        colorMenu()
        firstMenu()
      case 3 => exit(1)
      case _ => println("Opcao Invalida")
        firstMenu()
    }

  }

  def jogarMenu(): Int = {
    println("1-Inserir Palavra")
    println("2-Reiniciar")
    println("0-Sair")
    scala.io.StdIn.readInt() match {
      case 1 => 1
      case 2 => 2
      case 0 => 0
      case _ => println("Opcao Invalida")
        jogarMenu()
    }
  }

  @tailrec
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

  def getPlay(): (String, Coord2D, Direction.Direction) = {
    println(Direction.values)
    println("Formato da jogada -PALAVRA (y,x) 0-7")
    val line = scala.io.StdIn.readLine()
    val parts = line.split(" ")
    val Array(x, y) = parts(1).stripPrefix("(").stripSuffix(")").split(",")
    val directions = Utils.Direction.values.toList

    (parts.head, (x.toInt, y.toInt), directions(parts(2).toInt))
  }

  def readFromFile(file: String): (Int, Int, List[String], List[List[Coord2D]]) = {
    val bufferedSource = Source.fromFile(file)
    val lines = bufferedSource.getLines().toList
    bufferedSource.close()

    val myRandom = lines.head.toInt
    val boardSize = lines(1).toInt

    def readLine(line: String): (String, List[Coord2D]) = {
      val parts = line.split(" ").toList
      val word = parts.head
      val coordinates = parts.tail.map(str => {
        val Array(x, y) = str.stripPrefix("(").stripSuffix(")").split(",")
        (x.toInt, y.toInt)
      })
      (word, coordinates)
    }

    val words_Coordinates = lines.drop(2).map { line =>
      readLine(line)
    }.unzip

    (myRandom, boardSize, words_Coordinates._1, words_Coordinates._2)

  }

  def writeToRandomtofile(r: Int, file: String): Unit = {
    val bufferedSource = Source.fromFile(file)
    val linhas = bufferedSource.getLines.toList
    bufferedSource.close()

    val restFile = r +: linhas.drop(1)

    val pw = new PrintWriter(new File(file))
    try {
      restFile.foreach(pw.println)
    } finally {
      pw.close()
    }
  }

  def main(args: Array[String]): Unit = {
    //Teste getDirection
    //As coordenadas são (y,x)
    println(Utils.getDirection((0, 0), (1, 1))) //NorthEast
    println(Utils.getDirection((0, 0), (1, 0))) //North
    println(Utils.getDirection((0, 0), (1, -1))) //Northwest
    println(Utils.getDirection((0, 0), (0, -1))) //West
    println(Utils.getDirection((0, 0), (-1, -1))) //Southwest
    println(Utils.getDirection((0, 0), (-1, 0))) //South
    println(Utils.getDirection((0, 0), (-1, 1))) //SouthEast
    println(Utils.getDirection((0, 0), (0, 1))) //East
    //println(Utils.getDirection((0, 0), (0, 0))) //Error
    println(Utils.getDirection((0, 0), (1, 2))) //NorthEast
    println(Utils.getDirection((0, 0), (2, 1))) //NorthEast

  }
}
