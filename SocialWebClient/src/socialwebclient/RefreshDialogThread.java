/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socialwebclient;

import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.ListModel;

class RefreshDialogThread extends Thread{
    private static final long DELAY = 3000;
    
    private boolean mIsBroken = false;
    
    private String mFirst;
    private String mSecond = "nobody";
    private Controller mController;
    private JTextArea mTextArea;
    private DefaultListModel mRecentList;
    
    public RefreshDialogThread(final String first, final JTextArea textArea, 
            final DefaultListModel recentList, final Controller controller) {
        mFirst = first;
        mTextArea = textArea;
        mController = controller;
        mRecentList = recentList;
    }
    
    public void setSecond(final String second) {
        mSecond = second;
    }
    
    @Override
    public void run() {
        while (!mIsBroken) {
            mTextArea.setText(mController.getMessages(mFirst, mSecond));
            
            final List<String> dialogs = mController.getDialogs(mFirst);
           
            mRecentList.clear();
            dialogs.stream().forEach((dialog) -> {
                mRecentList.addElement(dialog);
            });
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
