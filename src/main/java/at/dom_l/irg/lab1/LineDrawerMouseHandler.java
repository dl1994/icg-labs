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
                    drawer.ys_$eq(mouseEvent.getY());
                }), (drawers, mouseEvent) -> {}),
        FIRST_POINT((drawers, mouseEvent) -> drawers.forEach(
                drawer -> {
                    drawer.xe_$eq(mouseEvent.getX());
                    drawer.ye_$eq(mouseEvent.getY());
                }), (drawers, mouseEvent) -> drawers.forEach(
                drawer -> {
                    drawer.xe_$eq(mouseEvent.getX());
                    drawer.ye_$eq(mouseEvent.getY());
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
