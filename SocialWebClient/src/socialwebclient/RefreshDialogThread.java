/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socialwebclient;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

class RefreshDialogThread extends Thread{
    private static final long DELAY = 3000;
    
    private boolean mIsBroken = false;
    
    private String mFirst;
    private String mSecond = "nobody";
    private Controller mController;
    private JTextArea mTextArea;
    
    public RefreshDialogThread(final String first, final JTextArea textArea, final Controller controller) {
        mFirst = first;
        mTextArea = textArea;
        mController = controller;
    }
    
    public void setSecond(final String second) {
        mSecond = second;
    }
    
    @Override
    public void run() {
        while (!mIsBroken) {
            mTextArea.setText(mController.getMessages(mFirst, mSecond));
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException ex) {
                mIsBroken = true;
            }
        }
    }
    
    public void breakRefresh() {
        mIsBroken = true;
    }
}
