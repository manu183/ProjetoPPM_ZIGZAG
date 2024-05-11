import Utils.Board
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.{Button, Label, TextField}
import javafx.stage.Stage
import javafx.scene.{Parent, Scene}
import javafx.fxml.FXMLLoader
import javafx.scene.layout.{BorderPane, GridPane}


class MainController {
  //MainWindow
  @FXML private var startGameButton: Button = _
  @FXML private var playerName : TextField = _
  @FXML private var borderPane: BorderPane = _



  //MainWindow
  def onStartGameButtonClicked(): Unit = {
    println("Player name: " + playerName.getText)
  }

  @FXML
  private def openSecondWindow(event: ActionEvent): Unit = {
    //Esta forma implementa duas janelas, uma para o menu inicial e outra para o tabuleiro
    /*val loader = new FXMLLoader(getClass.getResource("BoardWindow.fxml"))
    val root: Parent = loader.load()
    val stage = new Stage()
    stage.initOwner(startGameButton.getScene.getWindow)
    stage.setScene(new Scene(root))
    stage.setMinWidth(650)
    stage.setMinHeight(450)
    stage.show()*/
    //Esta forma implementa uma janela
    val loader = new FXMLLoader(getClass.getResource("BoardWindow.fxml"))
    val root: Parent = loader.load()
    val stage = borderPane.getScene.getWindow.asInstanceOf[Stage]
    val boardBoardPane = loader.getController[BoardController]
    boardBoardPane.setPlayerName(playerName.getText)

    stage.setScene(new Scene(root))
    stage.show()
  }
}
