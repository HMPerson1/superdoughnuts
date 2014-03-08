/*
 * Copyright (C) 2014 hmperson1@gmail.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package hmperson1.apps.superdoughnuts.logic

import java.awt.Point
import scala.collection.mutable
import scala.util.Random
import hmperson1.apps.superdoughnuts.GameState

class GameManager(doughnutLife: Int, dpt: Double) {
  import GameManager._

  final val doughnuts = mutable.Set[Doughnut]()
  final val player = new Point
  final val rand = new Random

  var doughnutsEaten = 0
  var toGen = 0.0

  def tick(): Unit = {
    for (d <- doughnuts) {
      if (player.x == d.x && player.y == d.y) {
        doughnutsEaten += 1
        doughnuts -= d
      } else {
        d.life -= 1
        if (d.life <= 0) doughnuts -= d
      }
    }
    toGen += dpt
    while (toGen > 0) {
      doughnuts += new Doughnut(rand nextInt GRID_SIZE_X, rand nextInt GRID_SIZE_Y)
      toGen -= doughnutVal()
    }
  }

  private def doughnutVal() = rand.nextGaussian / 3 + 1

  def state = GameState((player.x, player.y), doughnuts.map(_.asTuple).toSet)

  class Doughnut(val x: Int, val y: Int) {
    var life = doughnutLife
    def asTuple = (x, y, life)
  }
}

object GameManager {
  final val TICKS_PER_SECOND = 20
  final val SECONDS_PER_TICK = 1.0 / TICKS_PER_SECOND

  val GRID_SIZE_X = 20;
  val GRID_SIZE_Y = 20;

  val DOUGHNUT_LIFE = Array(9, 7, 5, 3).map(_ * TICKS_PER_SECOND)
  val DPT = Array(60, 30, 20, 10).map(_ * SECONDS_PER_TICK / 60.0)

  def create(difficulty: Int): GameManager = {
    val gm = new GameManager(DOUGHNUT_LIFE(difficulty), DPT(0))
    gm
  }
}
