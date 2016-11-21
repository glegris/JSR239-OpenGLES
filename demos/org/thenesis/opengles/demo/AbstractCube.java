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

import java.nio.ByteBuffer;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;

import org.thenesis.opengles.SurfaceHolder;

abstract class AbstractCube implements Runnable {

	private static final byte[] s_cubeVertices = { -10, 10, 10, 10, -10, 10, 10, 10, 10, -10, -10, 10,

	-10, 10, -10, 10, -10, -10, 10, 10, -10, -10, -10, -10,

	-10, -10, 10, 10, -10, -10, 10, -10, 10, -10, -10, -10,

	-10, 10, 10, 10, 10, -10, 10, 10, 10, -10, 10, -10,

	10, -10, 10, 10, 10, -10, 10, 10, 10, 10, -10, -10,

	-10, -10, 10, -10, 10, -10, -10, 10, 10, -10, -10, -10 };
	private static final byte[] s_cubeColors = { (byte) 40, (byte) 80, (byte) 160, (byte) 255, (byte) 40, (byte) 80, (byte) 160, (byte) 255,
			(byte) 40, (byte) 80, (byte) 160, (byte) 255, (byte) 40, (byte) 80, (byte) 160, (byte) 255,

			(byte) 40, (byte) 80, (byte) 160, (byte) 255, (byte) 40, (byte) 80, (byte) 160, (byte) 255, (byte) 40, (byte) 80, (byte) 160, (byte) 255,
			(byte) 40, (byte) 80, (byte) 160, (byte) 255,

			(byte) 128, (byte) 128, (byte) 128, (byte) 255, (byte) 128, (byte) 128, (byte) 128, (byte) 255, (byte) 128, (byte) 128, (byte) 128,
			(byte) 255, (byte) 128, (byte) 128, (byte) 128, (byte) 255,

			(byte) 128, (byte) 128, (byte) 128, (byte) 255, (byte) 128, (byte) 128, (byte) 128, (byte) 255, (byte) 128, (byte) 128, (byte) 128,
			(byte) 255, (byte) 128, (byte) 128, (byte) 128, (byte) 255,

			(byte) 255, (byte) 110, (byte) 10, (byte) 255, (byte) 255, (byte) 110, (byte) 10, (byte) 255, (byte) 255, (byte) 110, (byte) 10,
			(byte) 255, (byte) 255, (byte) 110, (byte) 10, (byte) 255,

			(byte) 255, (byte) 70, (byte) 60, (byte) 255, (byte) 255, (byte) 70, (byte) 60, (byte) 255, (byte) 255, (byte) 70, (byte) 60, (byte) 255,
			(byte) 255, (byte) 70, (byte) 60, (byte) 255 };
	private static final byte[] s_cubeIndices = { 0, 3, 1, 2, 0, 1, /* front  */
	6, 5, 4, 5, 7, 4, /* back   */
	8, 11, 9, 10, 8, 9, /* top    */
	15, 12, 13, 12, 14, 13, /* bottom */
	16, 19, 17, 18, 16, 17, /* right  */
	23, 20, 21, 20, 22, 21 /* left   */
	};
	private static final byte[] s_cubeNormals = { 0, 0, 127, 0, 0, 127, 0, 0, 127, 0, 0, 127,

	0, 0, -128, 0, 0, -128, 0, 0, -128, 0, 0, -128,

	0, -128, 0, 0, -128, 0, 0, -128, 0, 0, -128, 0,

	0, 127, 0, 0, 127, 0, 0, 127, 0, 0, 127, 0,

	127, 0, 0, 127, 0, 0, 127, 0, 0, 127, 0, 0,

	-128, 0, 0, -128, 0, 0, -128, 0, 0, -128, 0, 0 };
	boolean initialized = false;
	boolean running = true;
	int frame = 0;
	float time = 0.0f;
	SurfaceHolder surfaceHolder;
	int width;
	int height;
	AbstractCube cube;
	EGL10 egl;
	GL10 gl;
	EGLConfig eglConfig;
	EGLDisplay eglDisplay;
	EGLSurface eglWindowSurface;
	EGLContext eglContext;
	ByteBuffer cubeVertices;
	ByteBuffer cubeColors;
	ByteBuffer cubeNormals;
	ByteBuffer cubeIndices;

	public AbstractCube(SurfaceHolder holder) {
		this.surfaceHolder = holder;
		this.width = holder.getWidth();
		this.height = holder.getHeight();
	}

	private int getProperty(String propName, int def) {
		String s = null;// = cube.getAppProperty(propName);
		int val = (s == null) ? def : Integer.parseInt(s);

		return val;
	}

	public void init() {


		this.egl = (EGL10) EGLContext.getEGL();

		this.eglDisplay = egl.eglGetDisplay(egl.EGL_DEFAULT_DISPLAY);

		int[] major_minor = new int[2];
		egl.eglInitialize(eglDisplay, major_minor);

		int[] num_config = new int[1];

		egl.eglGetConfigs(eglDisplay, null, 0, num_config);
		System.out.println("num_config[0] = " + num_config[0]);

		int redSize = getProperty("jsr239.redSize", 8);
		int greenSize = getProperty("jsr239.greenSize", 8);
		int blueSize = getProperty("jsr239.blueSize", 8);
		int alphaSize = getProperty("jsr239.alphaSize", 0);
		int depthSize = getProperty("jsr239.depthSize", 32);
		int stencilSize = getProperty("jsr239.stencilSize", EGL10.EGL_DONT_CARE);

		int[] s_configAttribs = { EGL10.EGL_RED_SIZE, redSize, EGL10.EGL_GREEN_SIZE, greenSize, EGL10.EGL_BLUE_SIZE, blueSize, EGL10.EGL_ALPHA_SIZE,
				alphaSize, EGL10.EGL_DEPTH_SIZE, depthSize, EGL10.EGL_STENCIL_SIZE, stencilSize, EGL10.EGL_NONE };

		EGLConfig[] eglConfigs = new EGLConfig[num_config[0]];
		egl.eglChooseConfig(eglDisplay, s_configAttribs, eglConfigs, eglConfigs.length, num_config);
		System.out.println("num_config[0] = " + num_config[0]);

		this.eglConfig = eglConfigs[0];

		this.eglContext = egl.eglCreateContext(eglDisplay, eglConfig, EGL10.EGL_NO_CONTEXT, null);

		this.gl = (GL10) eglContext.getGL();

		this.eglWindowSurface = egl.eglCreateWindowSurface(eglDisplay, eglConfig, surfaceHolder, null);

		// Initialize data Buffers
		this.cubeVertices = ByteBuffer.allocateDirect(s_cubeVertices.length);
		cubeVertices.put(s_cubeVertices);
		cubeVertices.rewind();

		this.cubeColors = ByteBuffer.allocateDirect(s_cubeColors.length);
		cubeColors.put(s_cubeColors);
		cubeColors.rewind();

		this.cubeNormals = ByteBuffer.allocateDirect(s_cubeNormals.length);
		cubeNormals.put(s_cubeNormals);
		cubeNormals.rewind();

		this.cubeIndices = ByteBuffer.allocateDirect(s_cubeIndices.length);
		cubeIndices.put(s_cubeIndices);
		cubeIndices.rewind();

		this.initialized = true;
	}

	private void perspective(float fovy, float aspect, float zNear, float zFar) {
		float xmin;
		float xmax;
		float ymin;
		float ymax;

		ymax = zNear * (float) Math.tan((fovy * Math.PI) / 360.0);
		ymin = -ymax;
		xmin = ymin * aspect;
		xmax = ymax * aspect;

		gl.glFrustumf(xmin, xmax, ymin, ymax, zNear, zFar);
	}

	private void updateState(int width, int height) {
		float[] light_position = { -50.f, 50.f, 50.f, 0.f };
		float[] light_ambient = { 0.125f, 0.125f, 0.125f, 1.f };
		float[] light_diffuse = { 1.0f, 1.0f, 1.0f, 1.f };
		float[] material_spec = { 1.0f, 1.0f, 1.0f, 0.f };
		float[] zero_vec4 = { 0.0f, 0.0f, 0.0f, 0.f };

		float aspect = (height != 0) ? ((float) width / (float) height) : 1.0f;

		gl.glViewport(0, 0, width, height);
		gl.glScissor(0, 0, width, height);

		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();

		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, light_position, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, light_ambient, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, light_diffuse, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, zero_vec4, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, material_spec, 0);

		gl.glEnable(GL10.GL_NORMALIZE);
		gl.glEnable(GL10.GL_LIGHTING);
		gl.glEnable(GL10.GL_LIGHT0);
		gl.glEnable(GL10.GL_COLOR_MATERIAL);
		gl.glEnable(GL10.GL_CULL_FACE);

		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glDisable(GL10.GL_DITHER);

		// Clear background to blue
		gl.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();

		perspective(90.f, aspect, 0.1f, 100.f);

		gl.glFinish();
	}
	
	abstract void drawSceneBackground();
	abstract void drawSceneForeground();

	private void drawScene() {
		// Make the context current on this thread
		egl.eglMakeCurrent(eglDisplay, eglWindowSurface, eglWindowSurface, eglContext);

		// Perform setup and clear background using GL
		egl.eglWaitNative(EGL10.EGL_CORE_NATIVE_ENGINE, surfaceHolder);

		updateState(width, height);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glFinish();

		// Wait for GL to complete
		egl.eglWaitGL();

		drawSceneBackground();

		// Draw the scene using GL
		egl.eglWaitNative(EGL10.EGL_CORE_NATIVE_ENGINE, surfaceHolder);

		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();

		gl.glTranslatef(0.f, 0.f, -30.f);
		gl.glRotatef((float) (time * 29.77f), 1.0f, 2.0f, 0.0f);
		gl.glRotatef((float) (time * 22.311f), -0.1f, 0.0f, -5.0f);

		gl.glVertexPointer(3, GL10.GL_BYTE, 0, cubeVertices);
		gl.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, 0, cubeColors);
		gl.glNormalPointer(GL10.GL_BYTE, 0, cubeNormals);

		gl.glDrawElements(GL10.GL_TRIANGLES, 6 * 6, GL10.GL_UNSIGNED_BYTE, cubeIndices);
		gl.glFinish();

		time += 0.1f;

		egl.eglWaitGL();

		// Release the context
		egl.eglMakeCurrent(eglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);

		drawSceneForeground();
	}

	public void shutdown() {
		egl.eglMakeCurrent(eglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
		egl.eglDestroyContext(eglDisplay, eglContext);
		egl.eglDestroySurface(eglDisplay, eglWindowSurface);
		egl.eglTerminate(eglDisplay);
	}

	public void run() {
		if (!initialized) {
			init();
		}

		try {
			while (running) {
				Thread.sleep(20);
				drawScene();
				surfaceHolder.flush();
				//flushGraphics();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		shutdown();
	}

}
