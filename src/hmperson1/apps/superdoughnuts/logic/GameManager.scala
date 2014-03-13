/*
 * Copyright (C) 2014 HMPerson1 <hmperson1@gmail.com>
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
import java.util.concurrent.{ ScheduledThreadPoolExecutor, TimeUnit, ScheduledFuture }
import scala.collection.mutable
import scala.util.Random
import hmperson1.apps.superdoughnuts.GameState

class GameManager(doughnutLife: Int, dpt: Double) {
  import GameManager._

  private val doughnuts = mutable.Set[Doughnut]()
  private val player = new Point
  private val rand = new Random

  private var doughnutsEaten = 0
  private var toGen = 0.0

  private var lastTick = 0L

  def tick(): Unit = {
    var now = System.nanoTime
    val diff = now - lastTick
    if (diff > 51000000) {
      println("LAAAG!!!!@" + System.currentTimeMillis)
      println(diff)
      println()
    }
    lastTick = now

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

  def stop() = GameManager.stop(this)

  def state = GameState((player.x, player.y), doughnuts.map(_.asTuple).toSet, doughnutLife, (GRID_SIZE_X, GRID_SIZE_Y))

  private class Doughnut(val x: Int, val y: Int) {
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

  private val timer = new ScheduledThreadPoolExecutor(1)
  private val startedGMs = mutable.Map[GameManager, ScheduledFuture[_ <: Any]]()

  def create(difficulty: Int): GameManager = {
    val gm = new GameManager(DOUGHNUT_LIFE(difficulty), DPT(0))
    val future = timer.scheduleAtFixedRate(gm, 0, 50, TimeUnit.MILLISECONDS)
    startedGMs(gm) = future
    gm
  }

  private def stop(gm: GameManager): Unit = {
    startedGMs(gm).cancel(false)
    startedGMs -= gm
  }

  private implicit def runGameManager(x: GameManager): Runnable = new Runnable() { def run() = x.tick() }
}
