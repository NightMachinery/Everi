package Alice; /**
 * Created by evar on 8/17/17.
 */

import processing.core.PApplet;
import com.hamoid.*;

public class A5 extends PApplet {

    VideoExport videoExport;
    boolean recording = false;
    float[] t = new float[1000];
    float k;

    public static void main(String[] args) {
        PApplet.main(A5.class.getCanonicalName());
    }

    public void settings() {
        size(500, 500);
    }

    public void draw() {
        background(51);

        begin(50 * abs(cos(k)) + 100, 50 * abs(sin(k)) + 100, 998);
        k = k + PI / 128;
        if (recording) {
            videoExport.saveFrame();
        }
    }

    public void setup() {
        noFill();
        strokeCap(SQUARE);
        videoExport = new VideoExport(this);
        videoExport.startMovie();
        System.out.println(sketchPath("a.mp4"));
    }

    public void keyPressed() {
        if (key == 'r' || key == 'R') {
            recording = !recording;
            println("Recording is " + (recording ? "ON" : "OFF"));
        }
        if (key == 'q') {
            videoExport.endMovie();
            exit();
        }
    }

    void begin(float R, float r, float n) {
        // background(0);
        strokeWeight(5);
        stroke(255);
        translate(width / 2, height / 2);
        for (int i = 0; i <= n; i = i + 1) {
            t[i] = 2 * i * PI / n;
            if (i % 2 == 0) {
                arc(0, 0, 2 * R, 2 * R, t[i], t[i + 1]);
            } else {
                arc(0, 0, 2 * r, 2 * r, t[i], t[i + 1]);
            }
        }
    }
}