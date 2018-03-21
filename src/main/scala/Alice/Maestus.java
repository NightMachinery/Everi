package Alice;/**
 * Created by evar on 8/18/17.
 */

import com.hamoid.VideoExport;
import processing.core.PApplet;
import processing.core.PFont;
import java.awt.*;
import java.io.IOException;

public class Maestus extends PApplet { //Maestus is latin for darksome. XD

    VideoExport videoExport;
    boolean recording = false;
    PFont f;

    public Maestus() throws IOException {
    }

    public static void main(String[] args) {
        PApplet.main(Maestus.class.getCanonicalName());
    }

    public void settings() {
        size(800, 600);
        f = new PFont(new Font("Arial", Font.PLAIN,10), true);

    }

    public void draw() {
        background(255);
        textFont(f, 14);
        fill(Color.decode("#98faff").getRGB());
        text("Maestus", 10  ,10);
        for (int i = 0; i < 20; i++) {
            textFont(f, i+5);
            text("Maestus", mouseX, mouseY);
        }
        if (recording) {
            videoExport.saveFrame();
        }
    }

    public void setup() {
        background(255);
        videoExport = new VideoExport(this);
        videoExport.startMovie();
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
}