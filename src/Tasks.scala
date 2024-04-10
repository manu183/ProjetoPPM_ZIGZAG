
import Utils.Direction.{Direction, addDirection}
import Utils.{Board, Coord2D}

import scala.annotation.tailrec

object Tasks {
  //t1
  def randomChar(rand: MyRandom): (Char, MyRandom) = {
    val aux = rand.nextInt(23)
    ((aux._1 + 97).toChar, aux._2)
  }

  //t2
  def fillOneCell(board: Board, letter: Char, coord: Coord2D): Board = {
    board.updated(coord._1, board(coord._1).updated(coord._2, letter))
  }

  //t3
  @tailrec
  def setBoardsWithWords(board: Board, words: List[String], positions: List[List[Coord2D]]): Board = {
    @tailrec
    def fillWord(board: Board, word: String, positions: List[Coord2D]): Board = {
      positions match {
        case Nil => board
        case _ => fillWord(fillOneCell(board, word.head, positions.head), word.tail, positions.tail)
      }
    }
    positions match {
      case Nil => board
      case _  => setBoardsWithWords(fillWord(board, words.head, positions.head), words.tail, positions.tail)
    }
  }


  //t4
  def completeBoardRandomly(board: Board, r: MyRandom, f: MyRandom => (Char, MyRandom)): (Board, MyRandom) = {
    def fillLine(line: List[Char], r: MyRandom): (List[Char], MyRandom) = {
      line match {
        case Nil => (Nil, r)
        case x :: xs =>
          if (x >= 'a' && x <= 'z') {
            val aux = fillLine(xs, r)
            (x :: aux._1, aux._2)
          }
          else {
            val aux = fillLine(xs, f(r)._2)
            (f(r)._1 :: aux._1, aux._2)
          }
      }

    }
    def fillBoard(board: Board, r: MyRandom): (Board, MyRandom) = {
      board match {
        case Nil => (Nil, r)
        case x :: xs => {
          val aux = fillLine(x, r)
          val aux2 = fillBoard(xs, aux._2)
          (List(aux._1) ++ aux2._1, aux._2)
        }
      }
    }
    fillBoard(board, r)
  }
  //t5
  def play(board:Board,palavra:String,start: Coord2D,dir:Direction):Boolean={
    //Retorna se c esta na coordenada coord no board
    def checkLetter(c:Char,coord:Coord2D):Boolean= {
      c == board(coord._1)(coord._2)
    }
    //Lista com as 8 direções
    val directions=Utils.Direction.values.toList


    def playing(palavra:String,coord: Coord2D,lst:List[Coord2D]):Boolean={
      if(palavra.isEmpty)
        true
      //verifica se a coordenada esta nos limites do tabuleiro
      else if (((coord._1>board.length-1)||(coord._2>board.length-1))||
        ((coord._1<0)||(coord._2<0)))
        false

      else if((lst.contains(coord)) || (!checkLetter(palavra.head,coord)))
        false

      else {
        val list=directions.map(x=>playing(palavra.tail,addDirection(coord,x),coord::lst))
        (false::list)reduceLeft(_||_)
      }
    }
    if (checkLetter(palavra.head,start))
      playing(palavra.tail,addDirection(start,dir),List(start))
    else
      false
  }
}