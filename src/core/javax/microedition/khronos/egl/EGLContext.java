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

import javax.microedition.khronos.opengles.GL;


/**
 * A class encapsulating the EGL context. The class also contains
 * static methods used to obtain instances of the <code>EGL</code>
 * and <code>GL</code> interfaces.
 *
 * <p> An <code>EGLContext</code> is obtained from EGL using the
 * <code>eglCreateContext</code> function, and destroyed using the
 * <code>eglDestroyContext</code> function.  Its main role is as a
 * parameter to <code>eglMakeCurrent</code>.
 */
public abstract class EGLContext {

	protected EGLContext() {
    }

    /**
     * Returns an <code>EGL</code> object.
     *
     * @return an <code>EGL</code> instance.
     */
    public static synchronized EGL getEGL() {
    	return EGLToolkit.getInstance().getEGL();
    }

    /**
     * Returns a <code>GL</code> object associated with this EGL
     * context.
     *
     * <p>To call extension methods, cast the returned object to the
     * interface type that contains the methods of the extension.
     *
     * @return a <code>GL</code> instance that implements all
     * available extension interfaces.
     */
    public abstract GL getGL();
}
