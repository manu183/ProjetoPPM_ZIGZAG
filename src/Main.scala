import Utils.{Board}
object Main {
  def main(args: Array[String]): Unit = {
    //println("Projeto PPM!")
    //val teste =randomChar(MyRandom(10))
    //println(teste._1)
    //println(randomChar(teste._2)._1)
    val board: Utils.Board = List(List('a', 'b', 'c'), List('d', 'e', 'f'), List('g', 'h', 'i'))

    val updatedBoard = Tasks.fillOneCell(board,'x', (1,1))
    Utils.printBoard(updatedBoard)
  }

}