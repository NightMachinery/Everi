package Alice;/**
 * Created by evar on 8/17/17.
 */

import processing.core.PApplet;

public class A3 extends PApplet {

    public static void main(String[] args) {
        PApplet.main(A3.class.getCanonicalName());
    }

    public void settings() {
        size(800, 600);

    }

    public void draw() {
        background(255);
        stroke(0);
        ellipse(200,200,300,300);
    }

    public void setup() {
        background(255);

    }
}