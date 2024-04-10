import Utils.{Board, Direction, getPlay, jogarMenu}

import scala.sys.exit

object Main {
  val file = "level.txt"
  val home = System.getProperty("user.home")
  val directory = s"${home}/IdeaProjects/ProjetoPPM_ZIGZAG/src/${file}"

  def main(args: Array[String]): Unit = {

    Utils.firstMenu()
    val (r, tamanho, palavras, coordenadas) = Utils.readFromFile(directory)

    val inic = Tasks.setBoardsWithWords(List.fill(tamanho, tamanho)(0), palavras, coordenadas)
    val (board, random) = Tasks.completeBoardRandomly(inic, MyRandom(r), Tasks.randomChar)
    Utils.changeR(random.nextInt._1, directory)
    jogar(board, palavras, List())
  }

  def jogar(board: Board, procura: List[String], encontradas: List[String]): Unit = {
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
          if (Tasks.play(board, palavra, coordenada, direcao)) {
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