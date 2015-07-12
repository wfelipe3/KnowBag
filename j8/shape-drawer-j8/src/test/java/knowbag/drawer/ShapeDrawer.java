package knowbag.drawer;

/**
 * Created by feliperojas on 29/03/15.
 */
public class ShapeDrawer {

    public void givenStartPointAndEndPoint_WhenALineIsDrawn_ThenDrawLineInCanvas() {
        Canvas canvas = new Canvas(3,4);
        Shape line = ShapeFactory.createShape("line", new Point[]{new Point(1,2), new Point(1,4)});
        Canvas drawnCanvas = canvas.draw(line);
        assert drawnCanvas.toString() == "-------";
    }
}
