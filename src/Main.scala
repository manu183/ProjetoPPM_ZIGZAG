import Utils.Board

import scala.sys.exit

object Main {
  val file = "level.txt"
  def main(args: Array[String]): Unit = {

    Utils.firstMenu()
    val (r, tamanho, palavras, coordenadas) = Utils.readFromFile(file)

    val inic = Tasks.setBoardsWithWords(List.fill(tamanho, tamanho)(0), palavras, coordenadas)
    val (board,finalR)=Tasks.comleteBoard(inic, MyRandom(r), palavras)
    Utils.writeToRandomtofile(finalR.nextInt._1,file)
    jogar(board, palavras, List())
  }

  private def jogar(board: Board, procura: List[String], encontradas: List[String]): Unit = {
    print("Palavras por encontrar--")
    println(procura)
    print("Palavras encontradas--")
    println(encontradas)
    Utils.printBoard(board)
    if (procura.isEmpty) {
      println("GANHOU O JOGO NAO EXISTE MAIS PALAVRAS")
      exit(1)
    }
    Utils.jogarMenu() match {
      case 0 => exit(1)
      case 1 =>
        val (palavra, coordenada, direcao) = Utils.getPlay()
        if (procura.contains(palavra)) {
          if (Tasks.play(board, palavra, coordenada, direcao)>=1) {
            println("ACERTOU A PALAVRA---" + palavra)
            jogar(board, procura.filterNot(_ == palavra), palavra :: encontradas)
          }
          else {
            print("Errou as coordenadas ou a direcao")
            jogar(board, procura, encontradas)
          }
        }
        else
          println("A PALAVRA=" + palavra + "nao esta na lista de procura")
        jogar(board, procura, encontradas)

      case 2 => main(Array())
    }

  }

}