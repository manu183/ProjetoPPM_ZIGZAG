import Utils.Direction.Direction
import Utils.{Board, Coord2D, Direction}
import javafx.animation.{Animation, KeyFrame, Timeline}
import javafx.fxml.FXML
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.{Alert, Button, Label, TextField, ToggleButton}
import javafx.scene.layout.{BorderPane, GridPane}

import scala.concurrent.duration.Duration




class BoardController {

  @FXML private var borderPane: BorderPane = _
  @FXML private var gridPane: GridPane = _
  @FXML private var topBarName: Label = _
  @FXML private var checkButton: Button = _
  @FXML private var restartButton: Button = _
  @FXML private var closeButton: Button = _
  private var playerName: String = _
  @FXML private var textField: TextField = _
  private var currentBoardAnswer: List[(ToggleButton, Coord2D)] = List()
  private var mainBoard: Board = _


  def setPlayerName(name: String): Unit = {
    playerName = name
    topBarName.setText("ZIGZAG Game - " + playerName)
  }

  private def updateTextField(): Unit = {
    var currentAnswer = ""
    if(currentBoardAnswer.isEmpty) currentAnswer = " "
    else
      for ((toggleButton,_) <- currentBoardAnswer) {
        //println(toggleButton.getText)
        currentAnswer += toggleButton.getText
      }
    textField.setText(currentAnswer)
  }


  def initialize(): Unit = {
    val file = "level.txt"

    val (r, tamanho, palavras, coordenadas) = Utils.readFromFile(file)

    val inic = Tasks.setBoardsWithWords(List.fill(tamanho, tamanho)(0), palavras, coordenadas)
    val (board, finalR) = Tasks.completeBoard(inic, MyRandom(r), palavras)
    mainBoard= board
    Utils.writeToRandomtofile(finalR.nextInt._1, file)
    writeWordsToGUI(mainBoard, gridPane)

  }

  private def writeWordsToGUI(board: Board, gridPane: GridPane): Unit = {
    for (i <- board.indices) {
      for (j <- board(i).indices) {
        val toggleButton = new ToggleButton(board(i)(j).toString)
        addToggleButtonToGUI(j, i, toggleButton)
      }
    }
  }

  private def addToggleButtonToGUI(column: Int, row: Int, toggleButton: ToggleButton): Unit = {
    toggleButton.setMaxWidth(Double.MaxValue)
    toggleButton.setMaxHeight(Double.MaxValue)
    toggleButton.setOnAction(_ => onClickMatrixButton(toggleButton, column, row))
    gridPane.add(toggleButton, column, row) // Aqui trocamos j por column e i por row
  }



  private def onClickMatrixButton(toggleButton: ToggleButton, column: Int, row: Int): Unit = {
    val coord = (column, row)
    currentBoardAnswer = if (currentBoardAnswer.map(_._1).contains(toggleButton)) {
      currentBoardAnswer.filterNot(_._1 == toggleButton)
    } else {
      currentBoardAnswer :+ (toggleButton, coord)
    }
    updateTextField()
  }


  private def getInitialDirection(): Direction = {
    val (x1, y1) = currentBoardAnswer.head._2 // Coord2D of first char
    val (x2, y2) = currentBoardAnswer(1)._2 // Coord2D of second char
    Utils.getDirection((y1, x1), (y2, x2))
  }

  def checkButtonClicked(): Unit = {
    println("Checking...")
    println(textField.getText)
    println(currentBoardAnswer.head._2)
    println(getInitialDirection())

    val res = Tasks.play(mainBoard, textField.getText,currentBoardAnswer.head._2, getInitialDirection())
    //val res = Tasks.play(mainBoard, "carrossel",(0, 0):Coord2D, Direction.South)
    println(res)
    if(res >= 1){

      //print board buttons to green
      currentBoardAnswer.foreach {
        case (toggleButton, _) =>
          toggleButton.setStyle("-fx-background-color: green")
      }
      acertouPalavra()
    }

    else {
      currentBoardAnswer.foreach {
        case (toggleButton, _) =>
          toggleButton.setStyle("-fx-background-color: red")
      }
      errouPalavra()
      currentBoardAnswer.foreach {
        case (toggleButton, _) =>
          toggleButton.setStyle("-fx-background-color: grey")
      }
    }
  }

  private def acertouPalavra(): Unit = {
    val alert = new Alert(AlertType.INFORMATION)
    alert.setTitle("Acertou!!!")
    alert.setHeaderText(null)
    alert.setContentText("Acertou a palavra " + textField.getText + "!")
    alert.showAndWait();
  }
  private def errouPalavra(): Unit = {
    val alert = new Alert(AlertType.INFORMATION)
    alert.setTitle("Mensagem")
    alert.setHeaderText(null)
    alert.setContentText("Errou a palavra ! Recomece!")
    alert.showAndWait();
    restartButtonClicked()
  }



  def restartButtonClicked(): Unit = {
    println("Restart")
    currentBoardAnswer = List()
    updateTextField()
    gridPane.getChildren.forEach {
      case toggleButton: ToggleButton =>
        //println(toggleButton.getText.toString)
        toggleButton.setSelected(false)
      case _ =>
    }

  }



  def closeButtonClicked(): Unit = {
    System.exit(0)
  }


}

