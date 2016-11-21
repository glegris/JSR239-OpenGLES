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
package org.thenesis.opengles.jgl;

import javax.microedition.khronos.opengles.GL;

import jgl.context.GLContext;

import org.thenesis.opengles.AbstractJavaEGLContext;

public class JavaEGLContext extends AbstractJavaEGLContext {


	private GLContext jglContext;

	public JavaEGLContext() {
		jglContext = new GLContext();
	}

	public GL getGL() {
		synchronized (this) {
			if (gl == null) {
				gl = new JavaGL10(this);

			}
			return gl;
		}
	}

	/**
	 * @return the jglContext
	 */
	GLContext getJGLContext() {
		return jglContext;
	}
}
