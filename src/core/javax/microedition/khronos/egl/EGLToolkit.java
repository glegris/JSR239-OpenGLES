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

package javax.microedition.khronos.egl;

public abstract class EGLToolkit {

	private static EGLToolkit instance;

	protected static EGLToolkit getInstance() {
//		if (instance == null) {
//			try {
//				Class c = Class.forName("org.thenesis.opengles.jgl.JGLToolkit");
//				instance = (EGLToolkit)c.newInstance();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return instance;
		return new org.thenesis.opengles.jgl.JGLToolkit();
		
	}

	protected abstract EGL getEGL();

	protected abstract EGLContext getNoContext();

	protected abstract EGLDisplay getNoDisplay();

	protected abstract EGLSurface getNoSurface();

}
