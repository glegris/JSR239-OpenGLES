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

package org.thenesis.opengles;

import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL;



public abstract class AbstractJavaEGLContext extends EGLContext implements ContextAccess {

	protected GL gl = null;

	private Thread boundThread = null;
	private JavaEGLDisplay display = null;
	private EGLSurface drawSurface = null;
	private EGLSurface readSurface = null;
	private boolean destroyed = false;


	public Thread getBoundThread() {
		return boundThread;
	}

	public void setBoundThread(Thread boundThread) {
		this.boundThread = boundThread;
	}

	public EGLDisplay getDisplay() {
		return display;
	}

	public void setDisplay(JavaEGLDisplay display) {
		this.display = display;
	}

	public EGLSurface getDrawSurface() {
		return drawSurface;
	}

	public void setDrawSurface(JavaEGLSurface drawSurface) {
		this.drawSurface = drawSurface;
	}

	public EGLSurface getReadSurface() {
		return readSurface;
	}

	JavaEGLSurface getDrawSurfaceImpl() {
		return (JavaEGLSurface) getDrawSurface();
	}

	public void setReadSurface(JavaEGLSurface readSurface) {
		this.readSurface = readSurface;
	}

	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}

	public boolean isDestroyed() {
		return destroyed;
	}

	/**
	 * For debugging purposes, prints the native context ID.
	 */
	//    public String toString() {
	//  	return "EGLContextImpl[" + nativeId + "]";
	//    }
	public void dispose() {

	}

}
