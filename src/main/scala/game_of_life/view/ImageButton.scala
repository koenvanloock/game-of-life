package game_of_life.view

import com.jfoenix.controls.JFXButton
import game_of_life.Board
import scalafx.scene.image.ImageView

class ImageButton(shape: Shape) extends JFXButton("", new ImageView(shape.image)) {
  setOnMouseClicked(_ => Board.drawing(DrawModes.STAMPING, shape.array))
}
