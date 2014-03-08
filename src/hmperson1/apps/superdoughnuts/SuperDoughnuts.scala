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

import hmperson1.apps.superdoughnuts.gui.GuiManager
import hmperson1.apps.superdoughnuts.logic.GameManager
import javax.swing.JPanel

object SuperDoughnuts {

  final val KEY_PLAYERNAME = "playerName"
  final val KEY_DIFFICLUTY = "difficluty"

  var startPanel: JPanel = null
  var gamePanel: JPanel = null
  var gameManager: GameManager = null
  /**
   * @param args the command line arguments
   */
  def main(args: Array[String]): Unit = {
    GuiManager.doOnEdt(() => {
      startPanel = GuiManager.createStartPanel(
        (map: Map[String, String]) => {
          println("You pressed the start button! Good Job!")
          println("PlayerName: " + map(KEY_PLAYERNAME))
          println("Difficulty: " + map(KEY_DIFFICLUTY))
          gameManager = GameManager create map(KEY_DIFFICLUTY).toInt
          GuiManager showPanel gamePanel
        },
        () => System.exit(0))

      gamePanel = GuiManager.createGamePanel(
        () => GuiManager showPanel startPanel,
        () => gameManager.state)

      GuiManager renameFrame "Doughnuts"
      GuiManager showPanel startPanel
    })
  }
}
