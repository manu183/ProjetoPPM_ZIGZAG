import Utils.{Board, Direction}
object Main {
  def main(args: Array[String]): Unit = {
    //println("Projeto PPM!")
    //val teste =Tasks.randomChar(MyRandom(10))
    //println(teste._1)
    //println(randomChar(teste._2)._1)
    val board: Utils.Board = List(List('a', 'b', 'c'), List('d', 'e', 'f'), List('g', 'h', 'i'))
    val teste=Tasks.play(board,"aea",(0,0),Direction.SouthWest)
    print(teste)
    //val updatedBoard = Tasks.fillOneCell(board,'x', (1,0))
    //Utils.printBoard(updatedBoard)
  }

}