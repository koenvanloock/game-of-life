package game_of_life

import javafx.animation.AnimationTimer

class MyAnimationTimer(f: () => Unit) extends AnimationTimer {

  var lastTime = 0L

  override def handle(now: Long): Unit = {

    if(now - lastTime > Board.tickRate() * 1000000) {
      f()
      lastTime = now
    }
  }
}
