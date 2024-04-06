import Utils.{Board, Coord2D}
object Main {
  def main(args: Array[String]): Unit = {
    //println("Projeto PPM!")
    //val teste =randomChar(MyRandom(10))
    //println(teste._1)
    //println(randomChar(teste._2)._1)
    val board: Utils.Board = List(List('a', 'b', 'c'), List('d', 'e', 'f'), List('g', 'h', 'i'))

    val updatedBoard = Tasks.fillOneCell(board,'x', (1,0))
    val updated2 = Tasks.fillWord(board, "tes", List((0,0),(0,1),(0,2)))


    val updated3 = Tasks.setBoardsWithWords(board, List("Tes","Rei","Til"), List(
      List[Coord2D]((0,0),(0,1),(0,2)),
      List[Coord2D]((1,0),(1,1),(1,2)),
      List[Coord2D]((2,0),(2,1),(2,2))))
    Utils.printBoard(updatedBoard)
    println()
    Utils.printBoard(updated2)
    println()
    Utils.printBoard(updated3)
    println("-"*32)

  }

}