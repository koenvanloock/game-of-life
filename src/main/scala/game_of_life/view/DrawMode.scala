package game_of_life.view

object DrawModes {
  val STAMPING = DrawMode("Stamping")
  val NORMAL = DrawMode("Normal")
}


case class DrawMode(drawType: String) {}
