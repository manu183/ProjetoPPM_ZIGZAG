import Utils.{Board, Direction}
object Main {
  def main(args: Array[String]): Unit = {
    //println("Projeto PPM!")
    //val teste =Tasks.randomChar(MyRandom(10))
    //println(teste._1)
    //println(randomChar(teste._2)._1)
    val board: Utils.Board = List(List('s', 'b', 'c'), List('d', 'l', 'f'), List('b', 'h', 'i'))
    print(Tasks.play(board,"slb",(0,0),Direction.SouthWest))
    //val board1= Tasks.fillOneCell(board,'l',(2,0))
    //Utils.printBoard1(board1)
    //val updatedBoard = Tasks.fillOneCell(board,'x', (1,0))
    //Utils.printBoard(updatedBoard)
  }

}