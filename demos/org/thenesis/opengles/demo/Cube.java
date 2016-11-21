/*
 * MIDPath - Copyright (C) 2006-2008 Guillaume Legris, Mathieu Legris
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version
 * 2 only, as published by the Free Software Foundation. 
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License version 2 for more details. 
 * 
 * You should have received a copy of the GNU General Public License
 * version 2 along with this work; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA 
 */

package org.thenesis.opengles.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.thenesis.opengles.SurfaceHolder;

public class Cube extends AbstractCube {

	public Cube(SwingSurfaceHolder holder) {
		super(holder);
	}

	public static void main(final String[] args) {
		SwingSurfaceHolder swingSurfaceHolder = new SwingSurfaceHolder(320, 240);
		Cube c = new Cube(swingSurfaceHolder);
		Thread t = new Thread(c);
		t.start();
	}

	@Override
	void drawSceneBackground() {
		// Draw a green square using MIDP
		surfaceHolder.lock();
		((SwingSurfaceHolder) surfaceHolder).getGraphics().setColor(Color.GREEN);
		((SwingSurfaceHolder) surfaceHolder).getGraphics().fillRect(20, 20, width - 40, height - 40);
		surfaceHolder.unlock();

	}

	@Override
	void drawSceneForeground() {
		// Draw a red square 
		surfaceHolder.lock();
		((SwingSurfaceHolder) surfaceHolder).getGraphics().setColor(Color.RED);
		((SwingSurfaceHolder) surfaceHolder).getGraphics().fillRect((width / 2) - 25, (height / 2) - 25, 50, 50);
		surfaceHolder.unlock();
	}
}

class SwingSurfaceHolder implements SurfaceHolder {

	private int surfaceWidth;
	private int surfaceHeight;
	private BufferedImage screenImage;
	private int[] screenImageData;
	private Graphics screenGraphics;

	private JPanel panel;
	private JFrame frame;

	public SwingSurfaceHolder(int width, int height) {

		this.surfaceWidth = width;
		this.surfaceHeight = height;

		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					createUI();
				}
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private void createUI() {

		screenImage = new BufferedImage(surfaceWidth, surfaceHeight, BufferedImage.TYPE_INT_ARGB_PRE);
		screenImageData = ((DataBufferInt) screenImage.getRaster().getDataBuffer()).getData();
		screenGraphics = screenImage.getGraphics();

		final Dimension dimension = new Dimension(surfaceWidth, surfaceHeight);
		panel = new JPanel() {

			public Dimension getPreferredSize() {
				return dimension;
			}

			public void paintComponent(java.awt.Graphics g) {
				super.paintComponent(g);
				g.drawImage(screenImage, 0, 0, null);
			}
		};

		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		//frame.addWindowListener(this);
		frame.add(panel, BorderLayout.CENTER);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null); // Center the frame (has to be called after pack)
		frame.setVisible(true);
		panel.requestFocusInWindow();

	}

	public int getWidth() {
		return screenImage.getWidth();
	}

	public int getHeight() {
		return screenImage.getHeight();
	}

	public int[] getData() {
		return screenImageData;
	}

	public Graphics getGraphics() {
		return screenGraphics;
	}

	public void flush() {
		panel.repaint();
	}

	public void lock() {
		// TODO Auto-generated method stub
	}

	public void unlock() {
		// TODO Auto-generated method stub
	}

}
