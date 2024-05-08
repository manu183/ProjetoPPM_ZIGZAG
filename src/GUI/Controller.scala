import javafx.fxml.FXML
import javafx.scene.control.{Button, TextField}

class Controller {
  @FXML private var startGameButton: Button = _
  @FXML private var playerName : TextField = _

  def onStartGameButtonClicked(): Unit = {
    println("Player name: " + playerName.getText)
  }
}
