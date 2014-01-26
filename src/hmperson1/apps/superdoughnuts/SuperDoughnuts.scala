/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hmperson1.apps.superdoughnuts

import hmperson1.apps.superdoughnuts.gui.StartPanel

object SuperDoughnuts {
  /**
   * @param args the command line arguments
   */
  def main(args: Array[String]): Unit = {
    StartPanel.start((panel: StartPanel) => { println("You pressed the start button! Good Job!") }, () => { System.exit(0) })
  }
}
