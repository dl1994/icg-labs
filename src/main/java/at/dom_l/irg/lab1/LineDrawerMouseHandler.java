/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * The MIT License (MIT)                                                           *
 *                                                                                 *
 * Copyright © 2017 Domagoj Latečki                                                *
 *                                                                                 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy    *
 * of this software and associated documentation files (the "Software"), to deal   *
 * in the Software without restriction, including without limitation the rights    *
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell       *
 * copies of the Software, and to permit persons to whom the Software is           *
 * furnished to do so, subject to the following conditions:                        *
 *                                                                                 *
 * The above copyright notice and this permission notice shall be included in all  *
 * copies or substantial portions of the Software.                                 *
 *                                                                                 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR      *
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,        *
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE     *
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER          *
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,   *
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE   *
 * SOFTWARE.                                                                       *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package at.dom_l.irg.lab1;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class LineDrawerMouseHandler implements MouseMotionListener, MouseListener {

    private enum State {
        DRAW_INITIAL((drawers, mouseEvent) -> drawers.forEach(
                drawer -> {
                    drawer.xs_$eq(mouseEvent.getX());
                    drawer.ys_$eq(480 - mouseEvent.getY());
                }), (drawers, mouseEvent) -> {}),
        FIRST_POINT((drawers, mouseEvent) -> drawers.forEach(
                drawer -> {
                    drawer.xe_$eq(mouseEvent.getX());
                    drawer.ye_$eq(480 - mouseEvent.getY());
                }), (drawers, mouseEvent) -> drawers.forEach(
                drawer -> {
                    drawer.xe_$eq(mouseEvent.getX());
                    drawer.ye_$eq(480 - mouseEvent.getY());
                })),
        SECOND_POINT((drawers, mouseEvent) -> drawers.forEach(LineDrawer::reset),
                (drawers, mouseEvent) -> {});

        private State nextState;
        private final BiConsumer<List<LineDrawer>, MouseEvent> updateFunction;
        private final BiConsumer<List<LineDrawer>, MouseEvent> moveFunction;

        State(BiConsumer<List<LineDrawer>, MouseEvent> updateFunction,
              BiConsumer<List<LineDrawer>, MouseEvent> moveFunction) {
            this.updateFunction = updateFunction;
            this.moveFunction = moveFunction;
        }

        static {
            DRAW_INITIAL.nextState = FIRST_POINT;
            FIRST_POINT.nextState = SECOND_POINT;
            SECOND_POINT.nextState = DRAW_INITIAL;
        }

        private State getNextState() {
            return this.nextState;
        }

        public void update(List<LineDrawer> drawers, MouseEvent mouseEvent) {
            this.updateFunction.accept(drawers, mouseEvent);
        }

        public void handleMove(List<LineDrawer> drawers, MouseEvent mouseEvent) {
            this.moveFunction.accept(drawers, mouseEvent);
        }
    }

    private State state = State.DRAW_INITIAL;
    private final List<LineDrawer> drawers = new ArrayList<>();

    public void addDrawer(LineDrawer drawer) {
        drawers.add(drawer);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.state.update(this.drawers, e);
        this.state = this.state.getNextState();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.state.handleMove(this.drawers, e);
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}
}
