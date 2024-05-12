
import Utils.Direction.{Direction, addDirection}
import Utils.{Board, Coord2D, printBoard}

import scala.annotation.tailrec

object Tasks {
  //t1
  private def randomChar(rand: MyRandom): (Char, MyRandom) = {
    val aux = rand.nextInt(23)
    ((aux._1 + 97).toChar, aux._2)
  }

  //t2
  private def fillOneCell(board: Board, letter: Char, coord: Coord2D): Board = {
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
  @tailrec
  def completeBoard(inic:Board, r:MyRandom, palavras:List[String]):(Board,MyRandom)={
    val (boardFim, random) = completeBoardRandomly(inic, r, Tasks.randomChar)
    if (Tasks.checkBoard(boardFim,palavras)) {
      (boardFim,r)
    } else {
      completeBoard(inic,random,palavras)
    }
  }
  //t4
  private def completeBoardRandomly(board: Board, r: MyRandom, f: MyRandom => (Char, MyRandom)): (Board, MyRandom) = {
    def checkCell(c:Char,r:MyRandom):(Char, MyRandom)={
      if (c >= 'a' && c <= 'z')
        (c,r)
      else
        f(r)
    }
    def completeLine(line: List[Char], r: MyRandom): (List[Char], MyRandom) = {
      val aux=line.scanLeft(('a',r))((a,b)=>checkCell(b,a._2))
      (aux.tail.map(_._1),aux.last._2)
    }
    board match {
      case Nil => (Nil, r)
      case x => val line=completeLine(x.head,r)
        val nextLines=completeBoardRandomly(x.tail,line._2,f)
        (line._1::nextLines._1,nextLines._2)
      }
  }

  //t5
  def play(board:Board,palavra:String,start: Coord2D,dir:Direction):Int={
    //Retorna se c esta na coordenada coord no board
    def checkLetter(c:Char,coord:Coord2D):Boolean= {
      c == board(coord._1)(coord._2)
    }
    //Lista com as 8 direções
    val directions=Utils.Direction.values.toList
    def playing(palavra:String,coord: Coord2D,lst:List[Coord2D]):Int={
      if(palavra.isEmpty)
        1
      //verifica se a coordenada esta nos limites do tabuleiro
      else if (((coord._1>board.length-1)||(coord._2>board.length-1))||
        ((coord._1<0)||(coord._2<0)))
        0

      else if(lst.contains(coord) || (!checkLetter(palavra.head,coord)))
        0

      else {
        val list=directions.map(x=>playing(palavra.tail,addDirection(coord,x),coord::lst))
        (0::list)reduceLeft(_ + _)
      }
    }
    if(!checkLetter(palavra.head,start))
      0
    else
      playing(palavra.tail,addDirection(start,dir),List(start))


  }
  //T6
  private def checkBoard(board: Board, palavras:List[String]):Boolean={
    val directions=Utils.Direction.values.toList
    def addLists(a:List[Int],b:List[Int])=a.zip(b).map(x=>x._1+x._2)
    def sum(xs:List[Int])=(xs foldLeft 0)(_+_)

    def checkCell(coord: Coord2D):List[Int]={
    palavras.map(p=>sum(directions.map(d=>play(board,p,coord,d))))
    }
    def aux(coord:Coord2D):List[Int]={
      coord match {
        case (0,0) => checkCell(0,0)
        case (x,0) => addLists(checkCell(x,0),aux(x-1,board.length-1))
        case(x,y) =>  addLists(checkCell(x,y),aux(x,y-1))
      }
    }
    val vector= aux(board.length-1,board.length-1)
    (vector.length==palavras.length)&&(vector foldLeft true)((x,y)=>(x,y)==(true,8))

  }
}