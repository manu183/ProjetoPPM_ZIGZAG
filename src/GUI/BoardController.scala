package GUI

import Utils.Board
import javafx.collections.ListChangeListener
import javafx.fxml.FXML
import javafx.scene.control.Cell
import javafx.scene.layout.{AnchorPane, GridPane}

class BoardController {

  @FXML private var anchorPane: AnchorPane = _
  @FXML private var board: GridPane = _

  def loadBoard(board: Board): Unit = {
    board.addListener((_: ListChangeListener.Change[_]) => {
      for (i <- 0 until board.size) {
        for (j <- 0 until board.size) {
          val cell = new Cell(board(i)(j))
          this.board.add(cell, j, i)
        }
      }
    })
  }
}
