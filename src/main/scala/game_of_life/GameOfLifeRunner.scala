package game_of_life

import com.jfoenix.controls.{JFXButton, JFXTextField}
import de.jensd.fx.glyphs.fontawesome.{FontAwesomeIcon, FontAwesomeIconView}
import game_of_life.view.{GameOfLifeCanvas, ImageButton, Shapes}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.layout._

import scala.util.Try

object GameOfLifeRunner extends JFXApp {
  private val MIN_SQUARE_WIDTH = 4
  private val DEFAULT_WIDTH = "88"
  private val DEFAULT_HEIGHT = "66"
  private val DEFAULT_RATE = "50"

  private val canvas = new GameOfLifeCanvas(800, 600)
  val timer = new MyAnimationTimer(() => Board.step)
  private val buttons = new VBox()
  private val dimensionsPane = new HBox()
  private val widthBox = new JFXTextField()
  private val heightBox = new JFXTextField()
  private val tickRateBox = new JFXTextField()

  widthBox.setMaxWidth(80)
  heightBox.setMaxWidth(80)
  tickRateBox.setMaxWidth(160)

  tickRateBox.textProperty.addListener((_, _, newVal) => Board.tickRate.update(inputToNumber(newVal, 1000)))
  widthBox.textProperty.addListener((_, _, newVal) => Board.updateWidth(inputToNumber(newVal, 800)))
  heightBox.textProperty.addListener((_, _, newVal) => Board.updateHeight(inputToNumber(newVal, 600)))

  widthBox.setText(DEFAULT_WIDTH)
  heightBox.setText(DEFAULT_HEIGHT)
  tickRateBox.setText(DEFAULT_RATE)
  dimensionsPane.children.addAll(widthBox, heightBox)

  buttons.style = "-fx-background-color: #336699;"
  buttons.padding = Insets(25)
  private val playButton = new JFXButton()
  val play = new FontAwesomeIconView(FontAwesomeIcon.PLAY)
  playButton.setGraphic(play)
  playButton.onMouseClickedProperty().setValue(_ => timer.start())

  private val pauseButton = new JFXButton()
  val pause = new FontAwesomeIconView(FontAwesomeIcon.PAUSE)
  pauseButton.onMouseClickedProperty().setValue(_ => timer.stop())
  pauseButton.setGraphic(pause)

  private val clearButton = new JFXButton()
  val clear = new FontAwesomeIconView(FontAwesomeIcon.TRASH)
  clearButton.onMouseClickedProperty().setValue(_ => Board.clear)
  clearButton.setGraphic(clear)

  val orchestrationbuttons = new HBox(20)
  orchestrationbuttons.children.addAll(playButton, pauseButton, clearButton)

  val imagesButtons = new VBox()
  imagesButtons.margin = Insets(20.0)
  Shapes.all().map(new ImageButton(_)).map(imagesButtons.children.add(_))


  buttons.children.addAll(
    orchestrationbuttons,
    dimensionsPane,
    tickRateBox,
    imagesButtons)

  stage = new PrimaryStage {
    scene = new Scene {
      stylesheets = List(
        getClass.getResource("/css/jfoenix-fonts.css").toExternalForm,
        getClass.getResource("/css/jfoenix-design.css").toExternalForm,
        getClass.getResource("/css/main.css").toExternalForm)
      root = new BorderPane {
        padding = Insets(0)
        center = canvas
        right = buttons
      }
    }
  }

  stage.onCloseRequest = _ => timer.stop()

  def inputToNumber(text: String, canvasDimension: Int): Int = Try {
    val number = text.toInt
    if (number < 1) 1 else {
      if (number > canvasDimension / MIN_SQUARE_WIDTH) canvasDimension / MIN_SQUARE_WIDTH else number
    }
  }.toOption.getOrElse(1)

}