import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.{Button, TextField}
import javafx.stage.Stage
import javafx.scene.{Parent, Scene}
import javafx.fxml.FXMLLoader


class MainController {
  @FXML private var startGameButton: Button = _
  @FXML private var playerName : TextField = _

  def onStartGameButtonClicked(): Unit = {
    println("Player name: " + playerName.getText)
  }

  @FXML
  private def openSecondWindow(event: ActionEvent): Unit = {
    val loader = new FXMLLoader(getClass.getResource("BoardWindow.fxml"))
    val root: Parent = loader.load()
    val stage = new Stage()
    stage.setScene(new Scene(root))
    stage.setMinWidth(650)
    stage.setMinHeight(450)
    stage.show()
  }
}
