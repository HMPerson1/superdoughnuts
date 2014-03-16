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

import java.awt.event.{ KeyListener, KeyEvent }

class KeyListenerDelegate(var delegatee: KeyListener) extends KeyListener {
  def keyPressed(e: KeyEvent) = delegatee.keyPressed(e)
  def keyReleased(e: KeyEvent) = delegatee.keyReleased(e)
  def keyTyped(e: KeyEvent) = delegatee.keyTyped(e)
}
