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
package hmperson1.apps.superdoughnuts.gui

import hmperson1.apps.superdoughnuts.GameState
import java.awt.Color
import javax.swing.Painter

class GamePainter(stateRetriever: () => GameState) extends Painter[Any] {
  def paint(g: java.awt.Graphics2D, ignored: Any, width: Int, height: Int): Unit = {
    val state = stateRetriever();
    g setPaint Color.CYAN
    g.fillRect(0, 0, width, height)
    g setPaint Color.RED
    g.drawRect(0, 0, width - 1, height - 1)
    g setPaint Color.BLACK

    val xScale = width.toDouble / state.gridSize._1
    val yScale = height.toDouble / state.gridSize._2

    for (d <- state.doughnuts) {
      var (x, y, life) = d
      g.setPaint(new Color(255, 165, 0, 255 * life / state.maxLife))
      g.fillOval((x * xScale).toInt, (y * yScale).toInt, xScale.toInt, yScale.toInt)
    }

    val (px, py) = state.player
    g.setPaint(new Color(0, 0, 0, 191))
    g.fillOval((px * xScale).toInt, (py * yScale).toInt, (xScale * .75).toInt, (yScale * .75).toInt)
  }
}
