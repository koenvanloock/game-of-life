package game_of_life

import java.util.{Timer, TimerTask}

import game_of_life.view.{DrawModes, GameOfLifeCanvas}

import scala.util.Try
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.{Button, TextField}
import view.Shapes._
import scalafx.scene.layout._

object GameOfLifeRunner extends JFXApp {
  private val DEFAULT_WIDTH = "88"
  private val DEFAULT_HEIGHT = "66"

  private val canvas = new GameOfLifeCanvas(800, 600)
  private var timer = new Timer()
  private val buttons = new VBox()
  private val dimensionsPane = new HBox()
  private val widthBox = new TextField()
  private val heightBox = new TextField()

  widthBox.setMaxWidth(80)
  heightBox.setMaxWidth(80)

  widthBox.text.onChange((_, _, newVal) => Board.updateWidth(inputToNumber(newVal, 800)))
  heightBox.text.onChange((_, _, newVal) => Board.updateHeight(inputToNumber(newVal, 600)))

  widthBox.text = DEFAULT_WIDTH
  heightBox.text = DEFAULT_HEIGHT
  dimensionsPane.children.addAll(widthBox, heightBox)

  buttons.style = "-fx-background-color: #336699"
  buttons.padding = Insets(25)
  private val playButton = new Button("Play")
  playButton.setOnMouseClicked(_ => task())

  private val pauseButton = new Button("Pauze")
  pauseButton.setOnMouseClicked(_ => timer.cancel())

  private val clearButton = new Button("Clear")
  clearButton.setOnMouseClicked(_ => Board.clear)

  private val placeGliderButton = new Button("Glider"){
    onMouseClicked = _ => Board.drawing(DrawModes.STAMPING, GLIDER)
  }

  buttons.children.addAll(playButton, pauseButton, clearButton, placeGliderButton, dimensionsPane)

  stage = new PrimaryStage {
    scene = new Scene {
      root = new BorderPane {
        padding = Insets(0)
        center = canvas
        right = buttons
      }
    }
  }

  stage.onCloseRequest = _ => timer.cancel()

  def inputToNumber(text: String, canvasDimension: Int): Int = Try {
    val number = text.toInt
    if (number < 1) 1 else if (number > canvasDimension / 4) canvasDimension / 4 else number
  }.toOption.getOrElse(1)

  private def task(): Unit = {
    timer = new Timer()
    val task = new TimerTask() {
      override def run(): Unit = Board.step
    }
    timer.scheduleAtFixedRate(task, 0, 300)
  }

}