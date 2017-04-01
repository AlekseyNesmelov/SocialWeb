/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socialwebclient;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class Screen {
    private final List<JComponent> mComponents = new ArrayList<>();
    
    public void add(final JComponent conponent) {
        mComponents.add(conponent);
    }
    
    public void show(final JFrame frame) {
        mComponents.stream().forEach((JComponent component) -> {
            frame.add(component);
        });
        frame.repaint();
    }
    
    public List<JComponent> getComponents() {
        return mComponents;
    }
}
