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
package hmperson1.apps.superdoughnuts

class GameState(
  val player: (Int, Int),
  val doughnuts: Set[(Int, Int, Int)],
  val maxLife: Int,
  val gridSize: (Int, Int),
  val score: Int)

object GameState {
  def apply(player: (Int, Int), doughnuts: Set[(Int, Int, Int)], maxLife: Int, gridSize: (Int, Int), score: Int): GameState =
    new GameState(player, doughnuts, maxLife, gridSize, score)
}
