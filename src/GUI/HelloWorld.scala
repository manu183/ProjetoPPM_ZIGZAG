import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

class HelloWorld extends Application {
  override def start(primaryStage: Stage): Unit = {
    primaryStage.setTitle("My Hello World App")
    val fxmlLoader = new FXMLLoader(getClass.getResource("GUI/MainWindow.fxml"))
    val mainViewRoot: Parent = fxmlLoader.load()
    val scene = new Scene(mainViewRoot)
    primaryStage.setScene(scene)
    primaryStage.setMinWidth(650)
    primaryStage.setMinHeight(450)
    primaryStage.show()
  }
}

object FxApp {
  def main(args: Array[String]): Unit = {
    Application.launch(classOf[HelloWorld], args: _*)
  }
}
