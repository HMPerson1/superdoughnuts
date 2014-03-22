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
import java.awt.event.{ KeyAdapter, KeyEvent }
import java.util.concurrent.{ ScheduledThreadPoolExecutor, TimeUnit, ScheduledFuture }
import scala.collection.mutable
import scala.util.Random
import hmperson1.apps.superdoughnuts.GameState

class GameManager(doughnutLife: Int, dpt: Double) {
  import GameManager._

  private val doughnuts = mutable.Set[Doughnut]()
  private val player = new Point
  private val motion = new Point
  private val rand = new Random

  val keyListener = new KeyAdapter() {
    override def keyPressed(e: KeyEvent): Unit = GameManager.this.synchronized {
      e.getKeyCode match {
        case KeyEvent.VK_LEFT  => motion.x = -1
        case KeyEvent.VK_RIGHT => motion.x = 1
        case KeyEvent.VK_UP    => motion.y = -1
        case KeyEvent.VK_DOWN  => motion.y = 1
        case _                 =>
      }
    }
  }

  private var doughnutsEaten = 0
  private var toGen = 0.0

  private var lastTick = 0L
  private var cumultLag = 0L
  private var isFirstTick = true

  def tick(): Unit = synchronized {
    val now = System.nanoTime
    cumultLag += now - lastTick - 50000000
    if (isFirstTick) { cumultLag = 0; isFirstTick = false }
    if (cumultLag >= 50000000) {
      cumultLag -= 50000000
      println(">>>>> SKIPPED A TICK <<<<<")
    }
    lastTick = now

    player.translate(motion.x, motion.y)
    motion.setLocation(0, 0)

    if (player.x < 0) player.x = 0
    if (player.x > GRID_SIZE_X - 1) player.x = GRID_SIZE_X - 1
    if (player.y < 0) player.y = 0
    if (player.y > GRID_SIZE_Y - 1) player.y = GRID_SIZE_Y - 1

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

  def state = synchronized {
    GameState((player.x, player.y), doughnuts.map(_.asTuple).toSet, doughnutLife, (GRID_SIZE_X, GRID_SIZE_Y), doughnutsEaten)
  }

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
