package Alice;/**
 * Created by evar on 8/17/17.
 */

import processing.core.PApplet;

import java.util.ArrayList;

public class A2 extends PApplet {

    public static void main(String[] args) {
        PApplet.main(A2.class.getCanonicalName());
    }

    public void settings() {
        size(500, 500);
//        smooth(8);
    }

    // Watercolor
// Levente Sandor, 2013

    ArrayList<Brush> brushes;

   public void setup() {
        background(255);
        brushes = new ArrayList<Brush>();
        for (int i = 0; i < 50; i++) {
            brushes.add(new Brush());
        }
    }

    public void draw() {
        for (Brush brush : brushes) {
            brush.paint();
        }
    }

 public    void mouseClicked() {
        setup();
    }

    class Brush {

        float angle;
        int components[];
        float x, y;
        int clr;
        Brush() {
            angle = random(TWO_PI);
            x = random(width);
            y = random(height);
            clr = color(random(255), random(255), random(255), 5);
            components = new int[2];
            for (int i = 0; i < 2; i++) {
                components[i] = (int)(random(1, 5));
            }
        }

        void paint() {
            float a = 0;
            float r = 0;
            float x1 = x;
            float y1 = y;
            float u = random((float)0.5,(float) 1.0);

            fill(clr);
            noStroke();

            beginShape();
            while (a < TWO_PI) {
                vertex(x1, y1);
                float v = random((float)0.85,(float) 1);
                x1 = x + r * cos(angle + a) * u * v;
                y1 = y + r * sin(angle + a) * u * v;
                a += PI / 180;
                for (int i = 0; i < 2; i++) {
                    r += sin(a * components[i]);
                }
            }
            endShape(CLOSE);

            if (x < 0 || x > width ||y < 0 || y > height) {
                angle += HALF_PI;
            }

            x += 2 * cos(angle);
            y += 2 * sin(angle);
            angle += random((float)-0.15, (float)0.15);
        }
    }
}