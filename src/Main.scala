import Utils.{Board, Direction, jogarMenu, showTakeChoice}

import scala.sys.exit
object Main {
  def main(args: Array[String]): Unit = {

    showTakeChoice()
    val (r,tamanho,palavras,coordenadas) = Utils.readFromFile("teste.txt")
    val board= Tasks.setBoardsWithWords(List.fill(tamanho, tamanho)(0),palavras,coordenadas)
    val board1=Tasks.completeBoardRandomly(board,MyRandom(r),Tasks.randomChar)

  }
  def jogar(board: Board,procura:List[String],encontradas:List[String]):Unit={
    if (procura.isEmpty) exit(1)
    Utils.jogarMenu() match  {
      case 0 =>exit(1)
      case 1=>Utils.getPlay
      case 2=> main(Array())
    }

  }

}