/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hmperson1.apps.superdoughnuts

import hmperson1.apps.superdoughnuts.gui.GuiManager
import javax.swing.JPanel

object SuperDoughnuts {

  final val KEY_PLAYERNAME = "playerName"
  final val KEY_DIFFICLUTY = "difficluty"

  var startPanel: JPanel = null
  var gamePanel: JPanel = null
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
          GuiManager showPanel gamePanel
        },
        () => { System.exit(0) })
      gamePanel = GuiManager.createGamePanel(() => { GuiManager showPanel startPanel });

      GuiManager renameFrame "Doughnuts"
      GuiManager showPanel startPanel
    })
  }
}
