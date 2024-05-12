import Utils.Direction.Direction
import Utils.{Board, Coord2D}
import javafx.fxml.FXML
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.{Alert, Label, TextField, ToggleButton}
import javafx.scene.layout.GridPane
import javafx.animation.{Animation, KeyFrame, Timeline}
import javafx.event.ActionEvent
import javafx.util.Duration


class BoardController {

  @FXML private var gridPane: GridPane = _
  @FXML private var topBarName: Label = _
  private var playerName: String = _
  @FXML private var textField: TextField = _
  private var currentBoardAnswer: List[(ToggleButton, Coord2D)] = List()
  private var mainBoard: Board = _
  private var wordsTofind: List[String] = Nil
  private var wordsFounded: List[String] = Nil
  private var secondsPassed: Int = 0


  @FXML private var timer: Label = _


  def setPlayerName(name: String): Unit = {
    playerName = name
    topBarName.setText("ZIGZAG Game - " + playerName)
  }

  def passOneSecond(t: String): String = {
    secondsPassed += 1
    val time = t.split(":")
    var hour = time(0).toInt
    var minute = time(1).toInt
    var second = time(2).toInt

    if (second + 1 < 60)
      return hour.toString + ":" + minute.toString + ":" + (second + 1).toString
    else {
      second = 0
      if (minute + 1 < 60)
        return hour.toString + ":" + (minute + 1).toString + ":00"
      else {
        minute = 0
        hour += 1
        return hour.toString + ":00:00"
      }
    }
  }


  private def updateTextField(): Unit = {
    var currentAnswer = ""
    if (currentBoardAnswer.isEmpty) currentAnswer = " "
    else
      for ((toggleButton, _) <- currentBoardAnswer) {
        currentAnswer += toggleButton.getText
      }
    textField.setText(currentAnswer)
  }


  def initialize(): Unit = {
    val file = "level.txt"

    val (r, tamanho, palavras, coordenadas) = Utils.readFromFile(file)
    wordsTofind = palavras
    val inic = Tasks.setBoardsWithWords(List.fill(tamanho, tamanho)(0), wordsTofind, coordenadas)
    val (board, finalR) = Tasks.completeBoard(inic, MyRandom(r), wordsTofind)
    mainBoard = board
    Utils.writeToRandomtofile(finalR.nextInt._1, file)
    writeWordsToGUI(mainBoard, gridPane)

    timer.setText("00:00:00")
    val timeline = new Timeline(
      new KeyFrame(Duration.seconds(1), (_: ActionEvent) => {
        val currentTime = timer.getText()
        timer.setText(passOneSecond(currentTime))
      })
    )
    timeline.setCycleCount(Animation.INDEFINITE)
    timeline.play()

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
    if (!Utils.adjacentCoordinates(currentBoardAnswer.map(_._2))) {
      popup("TEM QUE ESCOLHER COORDENADAS ADJACENTES")
      apagarButtonClicked()
      return
    }
    if (!wordsTofind.contains(textField.getText)) {
      popup("ERROU!")
      apagarButtonClicked()
      return
    }

    val res = Tasks.play(mainBoard, textField.getText, currentBoardAnswer.head._2, getInitialDirection())
    if (res >= 1) {

      //print board buttons to green
      currentBoardAnswer.foreach {
        case (toggleButton, _) =>
          toggleButton.setStyle("-fx-background-color: green")
      }
      acertouPalavra()

    }
    else
      errouPalavra()
    apagarButtonClicked()
  }

  private def acertouPalavra(): Unit = {
    val alert = new Alert(AlertType.INFORMATION)
    alert.setTitle("Acertou!!!")
    alert.setHeaderText(null)
    alert.setContentText("Acertou a palavra " + textField.getText + "!")
    alert.showAndWait()
    wordsFounded = wordsFounded :+ textField.getText
    wordsTofind = wordsTofind.filterNot(_ == textField.getText)
    if (wordsTofind.isEmpty) {
      gameWon()
    }
  }

  private def errouPalavra(): Unit = {
    val alert = new Alert(AlertType.INFORMATION)
    alert.setTitle("Errou")
    alert.setHeaderText(null)
    alert.setContentText("Errou a palavra ! Recomece!")
    alert.showAndWait()
  }


  private def popup(message: String): Unit = {
    val alert = new Alert(AlertType.INFORMATION)
    alert.setTitle("Mensagem")
    alert.setHeaderText(null)
    alert.setContentText(message)
    alert.showAndWait()
  }


  def apagarButtonClicked(): Unit = {
    currentBoardAnswer = List()
    updateTextField()
    gridPane.getChildren.forEach {
      case toggleButton: ToggleButton =>
        toggleButton.setSelected(false)
      case _ =>
    }

  }

  def newGameButtonClicked(): Unit = {
    apagarButtonClicked()
    initialize()
  }


  def closeButtonClicked(): Unit = {
    System.exit(0)
  }

  private def gameWon(): Unit = {
    val alert = new Alert(AlertType.INFORMATION)
    alert.setTitle("Parabens")
    alert.setHeaderText(null)
    val text ="Ganhou o jogo! O seu score foi de " + (wordsFounded.length * 100 - secondsPassed) + "!"
    val words = "\nAcertou as palavras: " + wordsFounded.mkString(", ")
    val tempo ="\nTempo: " + timer.getText()
    alert.setContentText(text+ words+tempo)
    alert.showAndWait()
    System.exit(0)
  }


}

