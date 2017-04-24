package at.dom_l.irg.common;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

public class GLContext {

    private final int width;
    private final int height;
    private final String windowName;
    private final List<GLDrawer> drawers = new ArrayList<>();
    private final List<KeyListener> keyListeners = new ArrayList<>();
    private final List<MouseListener> mouseListeners = new ArrayList<>();
    private final List<MouseWheelListener> mouseWheelListeners = new ArrayList<>();
    private final List<MouseMotionListener> mouseMotionListeners = new ArrayList<>();

    public GLContext() {
        this(640, 480, "OpenGL window");

        GLProfile.initSingleton();
    }

    public GLContext(int width, int height, String windowName) {
        this.width = width;
        this.height = height;
        this.windowName = windowName;
    }

    public void addDrawer(GLDrawer drawer) {
        this.drawers.add(drawer);
    }

    public void addKeyListener(KeyListener listener) {
        this.keyListeners.add(listener);
    }

    public void addMouseListener(MouseListener listener) {
        this.mouseListeners.add(listener);
    }

    public void addMouseWheelListener(MouseWheelListener listener) {
        this.mouseWheelListeners.add(listener);
    }

    public void addMouseMotionListener(MouseMotionListener listener) {
        this.mouseMotionListeners.add(listener);
    }

    public void start() {
        SwingUtilities.invokeLater(() -> {
            GLProfile glProfile = GLProfile.getDefault();
            GLCapabilities glCapabilities = new GLCapabilities(glProfile);
            GLCanvas glCanvas = new GLCanvas((glCapabilities));

            // TODO add mouse and keyboard selectors
            this.mouseMotionListeners.add(new MouseAdapter() {

                @Override
                public void mouseMoved(MouseEvent e) {
                    glCanvas.display();
                }
            });

            this.keyListeners.forEach(glCanvas::addKeyListener);
            this.mouseListeners.forEach(glCanvas::addMouseListener);
            this.mouseWheelListeners.forEach(glCanvas::addMouseWheelListener);
            this.mouseMotionListeners.forEach(glCanvas::addMouseMotionListener);

            glCanvas.addGLEventListener(new GLEventListener() {

                @Override
                public void init(GLAutoDrawable drawable) {}

                @Override
                public void dispose(GLAutoDrawable drawable) {}

                @Override
                public void display(GLAutoDrawable drawable) {
                    GL2 gl2 = drawable.getGL().getGL2();

                    int width = drawable.getSurfaceWidth();
                    int height = drawable.getSurfaceHeight();

                    gl2.glClear(GL.GL_COLOR_BUFFER_BIT);
                    gl2.glLoadIdentity();

                    drawers.forEach(d -> d.draw(gl2, width, height));
                }

                @Override
                public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
                    GL2 gl2 = drawable.getGL().getGL2();

                    gl2.glMatrixMode(GL2.GL_PROJECTION);
                    gl2.glLoadIdentity();

                    GLU glu = new GLU();

                    glu.gluOrtho2D(0.0f, width, 0.0f, height);

                    gl2.glMatrixMode(GL2.GL_MODELVIEW);
                    gl2.glLoadIdentity();
                    gl2.glViewport(0, 0, width, height);
                }
            });

            JFrame jFrame = new JFrame(this.windowName);

            jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            jFrame.getContentPane().add(glCanvas, BorderLayout.CENTER);
            jFrame.setSize(this.width, this.height);
            jFrame.setVisible(true);

            glCanvas.requestFocusInWindow();
        });
    }
}
