/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hmperson1.apps.superdoughnuts

import hmperson1.apps.superdoughnuts.gui.GuiManager

object SuperDoughnuts {
  /**
   * @param args the command line arguments
   */
  def main(args: Array[String]): Unit = {
    GuiManager.doOnEdt(() => {
      GuiManager renameFrame "Doughnuts"
      GuiManager showPanel GuiManager.createStartPanel(
        (map: Map[String, String]) => {
          println("You pressed the start button! Good Job!")
          println("PlayerName: " + map("playerName"))
          println("Difficulty: " + map("difficulty"))
        },
        () => { System.exit(0) })
    })
  }
}
