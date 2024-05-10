
import Utils.Board
import javafx.fxml.FXML
import javafx.scene.control.{Button, Cell, Label}
import javafx.scene.layout.{AnchorPane, BorderPane, GridPane}


class BoardController {

  @FXML private var borderPane: BorderPane = _
  @FXML private var gridPane: GridPane = _
  @FXML private var topBarName: Label = _
  @FXML private var checkButton: Button = _
  @FXML private var restartButton: Button = _
  @FXML private var closeButton: Button = _


  //Print values of the board
  def printBoard(): Unit = {

  }

  def initialize(): Unit = {
    val file = "level.txt"

    val userName = "Zé"
    topBarName.setText("ZIGZAG Game - " + userName)

    val (r, tamanho, palavras, coordenadas) = Utils.readFromFile(file)

    val inic = Tasks.setBoardsWithWords(List.fill(tamanho, tamanho)(0), palavras, coordenadas)
    val (board, finalR) = Tasks.completeBoard(inic, MyRandom(r), palavras)
    Utils.writeToRandomtofile(finalR.nextInt._1, file)
    writeWordsToGUI(board, gridPane)


  }

  private def writeWordsToGUI(board: Board, gridPane: GridPane): Unit = {
    for (i <- board.indices) {
      for (j <- board(i).indices) {
        val label = new Label(board(i)(j).toString)
        gridPane.add(label, j, i)
      }
    }
  }
  def checkButtonClicked(): Unit = {
    //TODO: Implement this method
  }
  def restartButtonClicked(): Unit = {
    //TODO: Implement this method
  }
  def closeButtonClicked(): Unit = {
    System.exit(0)
  }

  def changeGridPaneColor(): Unit = {
    gridPane.getChildren.forEach(cell => {
      cell.setStyle("-fx-background-color: #FF0000")
    })
  }


}

