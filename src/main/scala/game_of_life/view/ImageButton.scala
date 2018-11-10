package game_of_life.view

import game_of_life.Board

import scalafx.scene.control.Button
import scalafx.scene.image.ImageView

class ImageButton(shape: Shape)  extends Button("", new ImageView(shape.image)){
  onMouseClicked = _ => Board.drawing(DrawModes.STAMPING, shape.array)
}
