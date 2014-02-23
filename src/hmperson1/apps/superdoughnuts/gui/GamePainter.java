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
package hmperson1.apps.superdoughnuts.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.Painter;

/**
 *
 * @author HMPerson1
 */
class GamePainter implements Painter<Object> {

    @Override
    public void paint(Graphics2D g, Object object, int width, int height) {
        g.setPaint(Color.CYAN);
        g.fillRect(0, 0, width, height);
        g.setPaint(Color.RED);
        g.drawRect(0, 0, width - 1, height - 1);
        g.setPaint(Color.BLACK);
        g.drawChars("Hello!".toCharArray(), 0, 6, 0, g.getFontMetrics().getAscent());

    }

}
