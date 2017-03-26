package socialwebclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

public class UserGUI {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 720;
   
    private final JFrame mFrame;
    
    private final JLabel mUsernameLabel;
    private final JTextField mUsernameField;
    
    private final JLabel mPasswordLabel;
    private final JTextField mPasswordField;
    
    private final JLabel mEmailLabel;
    private final JTextField mEmailField;
    
    private final JLabel mFullNameLabel;
    private final JTextField mFullNameField;
    
    private final JLabel mWorkLabel;
    private final JTextField mWorkField;
    
    private final JLabel mBirthdayLabel;
    private final JTextField mBirthdayDayField;
    private final JTextField mBirthdayMonthField;
    private final JTextField mBirthdayYearField;
    
    private final JLabel mInterestsLabel;
    private final JTree mInterestsTree;
    private final DefaultMutableTreeNode mRootInterest;
    private final JButton mAddInterestButton;
    
    private final JTextArea mInterestTextArea;
    
    private final JButton mRegistrationButton;
    private final JButton mLoginButton;
    private final JButton mCancelRegistrationButton;
    private final JButton mAcceptRegistrationButton;
    
    private final List<JComponent> mComponents = new ArrayList<>();
    
    private final ClickListener mClickListener;
    
    private final Controller mController;
    
    private String mUserName = null;
    private List<Pair> mInterests;
    
    public UserGUI(final Controller controller) {
        mController = controller;
        mClickListener = new ClickListener();

        mFrame = new JFrame("Социальная сеть");
        mFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mFrame.setSize(WIDTH, HEIGHT);
        mFrame.setLayout(null);
        
        mUsernameLabel = new JLabel("Имя аккаунта");
        mUsernameLabel.setBounds(50, 20, 120, 20);
        mComponents.add(mUsernameLabel);
        mUsernameField = new JTextField();
        mUsernameField.setBounds(200, 20, 100, 20);
        mComponents.add(mUsernameField);
        
        mPasswordLabel = new JLabel("Пароль");
        mPasswordLabel.setBounds(50, 60, 120, 20);
        mComponents.add(mPasswordLabel);
        mPasswordField = new JTextField();
        mPasswordField.setBounds(200, 60, 100, 20);
        mComponents.add(mPasswordField);
        
        mEmailLabel = new JLabel("Электронная почта");
        mEmailLabel.setBounds(50, 100, 120, 20);
        mComponents.add(mEmailLabel);
        mEmailField = new JTextField();
        mEmailField.setBounds(200, 100, 100, 20);
        mComponents.add(mEmailField);
        
        mFullNameLabel = new JLabel("Полное имя");
        mFullNameLabel.setBounds(50, 140, 120, 20);
        mComponents.add(mFullNameLabel);
        mFullNameField = new JTextField();
        mFullNameField.setBounds(200, 140, 100, 20);
        mComponents.add(mFullNameField);
        
        mWorkLabel = new JLabel("Место учебы/работы");
        mWorkLabel.setBounds(50, 180, 120, 20);
        mComponents.add(mWorkLabel);
        mWorkField = new JTextField();
        mWorkField.setBounds(200, 180, 100, 20);
        mComponents.add(mWorkField);
        
        mBirthdayLabel = new JLabel("День рождения");
        mBirthdayLabel.setBounds(50, 220, 120, 20);
        mComponents.add(mBirthdayLabel);
        mBirthdayDayField = new JTextField();
        mBirthdayDayField.setBounds(200, 220, 30, 20);
        mComponents.add(mBirthdayDayField);
        mBirthdayMonthField = new JTextField();
        mBirthdayMonthField.setBounds(235, 220, 30, 20);
        mComponents.add(mBirthdayMonthField);
        mBirthdayYearField = new JTextField();
        mBirthdayYearField.setBounds(270, 220, 30, 20);
        mComponents.add(mBirthdayYearField);
        
        mAcceptRegistrationButton = new JButton("Подтвердить");
        mAcceptRegistrationButton.setBounds(50, 600, 250, 30);
        mComponents.add(mAcceptRegistrationButton);
        mAcceptRegistrationButton.addActionListener(mClickListener);
        
        mCancelRegistrationButton = new JButton("Отмена");
        mCancelRegistrationButton.setBounds(50, 640, 250, 30);
        mComponents.add(mCancelRegistrationButton);
        mCancelRegistrationButton.addActionListener(mClickListener);
        
        mLoginButton = new JButton("Вход");
        mLoginButton.setBounds(50, 100, 200, 30);
        mComponents.add(mLoginButton);
        mLoginButton.addActionListener(mClickListener);
        
        mRegistrationButton = new JButton("Регистрация");
        mRegistrationButton.setBounds(50, 140, 200, 30);
        mComponents.add(mRegistrationButton);
        mRegistrationButton.addActionListener(mClickListener);
         
        mInterests = mController.getInterestsTree();
        mInterestsLabel = new JLabel("Интересы:");
        mInterestsLabel.setBounds(50, 260, 120, 20);
        mComponents.add(mInterestsLabel);
        mRootInterest = new DefaultMutableTreeNode("Интересы");
        final List<String> i = new ArrayList<>();
        final Map<String, DefaultMutableTreeNode> nodes = new HashMap<>();
        while (i.size() != mInterests.size()) {
            for (final Pair interest : mInterests) {
                if (!i.contains(interest.first) &&
                        (i.contains(interest.second) || interest.second.isEmpty())) {
                    final DefaultMutableTreeNode node = new DefaultMutableTreeNode(interest.first);
                    nodes.put(interest.first, node);
                    if (interest.second.isEmpty()) {
                       mRootInterest.add(node);
                    } else {
                        nodes.get(interest.second).add(node);
                    }
                    i.add(interest.first);
                }
            }
        }
        
        mInterestsTree = new JTree(mRootInterest);
        mInterestsTree.setBounds(50, 285, 250, 300);
        
        mAddInterestButton = new JButton("Добавить");
        mAddInterestButton.setBounds(300, 285, 150, 30);
        mComponents.add(mAddInterestButton);
        mAddInterestButton.addActionListener(mClickListener);
        
        mInterestTextArea = new JTextArea();
        mInterestTextArea.setBounds(300, 315, 150, 150);
        mComponents.add(mInterestTextArea);
        
        mComponents.add(mInterestsTree);
    }
    
    public void show() {
        mFrame.setVisible(true);
        showStartScreen();
    } 
    
    public void showStartScreen() {
        clear();
        mFrame.add(mUsernameLabel);
        mFrame.add(mPasswordLabel);
        mFrame.add(mUsernameField);
        mFrame.add(mPasswordField);
        mFrame.add(mRegistrationButton);
        mFrame.add(mLoginButton);
        mFrame.repaint();
    }
    
    public void showAuthorizationScreen() {
        clear();
        //mFrame.add(mSignUpButton);
        //mFrame.add(mShowStatusButton);
        //mFrame.add(mLogoutButton);
        mFrame.repaint();
    }
    
    public void showRegistrationScreen() {
        clear();
        mFrame.add(mUsernameLabel);
        mFrame.add(mUsernameField);
        
        mFrame.add(mPasswordLabel);
        mFrame.add(mPasswordField);
        
        mFrame.add(mEmailLabel);
        mFrame.add(mEmailField);
        
        mFrame.add(mFullNameLabel);
        mFrame.add(mFullNameField);
        
        mFrame.add(mWorkLabel);
        mFrame.add(mWorkField);
        
        mFrame.add(mBirthdayLabel);
        mFrame.add(mBirthdayDayField);
        mFrame.add(mBirthdayMonthField);
        mFrame.add(mBirthdayYearField);
        
        mFrame.add(mInterestsLabel);
        mFrame.add(mInterestsTree);
        
        mFrame.add(mAddInterestButton);
        mFrame.add(mInterestTextArea);
        
        mFrame.add(mAcceptRegistrationButton);
        
        mFrame.add(mCancelRegistrationButton);
        
        mFrame.repaint();
    }
    
    public void clear() {
        mComponents.stream().forEach((component) -> {
            mFrame.remove(component);
        });
        mFrame.repaint();
    }
    
    public void clearTable(JTable table) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);
    }
    
    
    private class ClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == mRegistrationButton) {
                showRegistrationScreen();
            } else if (e.getSource() == mLoginButton) {
                if (!mUsernameField.getText().isEmpty()) {
                    if (mPasswordField.getText().length() >= 8) {
                        if (mController.authorization(mUsernameField.getText(),
                                mPasswordField.getText())) {
                            mUserName = mUsernameField.getText();
                            JOptionPane.showMessageDialog(mFrame,
                                "Authorization completed!");
                            showAuthorizationScreen();
                        } else {
                            JOptionPane.showMessageDialog(mFrame,
                                "Authorization failed!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(mFrame,
                                "Password must have 8 symbols at least!");
                    }
                } else {
                    JOptionPane.showMessageDialog(mFrame, "Username is empty!");
                }
            } else if (e.getSource() == mAddInterestButton) {
                final Object selected = mInterestsTree.getLastSelectedPathComponent();
                if (selected != null && !mInterestTextArea.getText().contains(selected.toString())) {
                    mInterestTextArea.setText( mInterestTextArea.getText().isEmpty() ?
                            selected.toString() :
                            mInterestTextArea.getText() + "\n" + selected.toString());
                }
            } else if (e.getSource() == mAcceptRegistrationButton) {
                if (!mUsernameField.getText().isEmpty()) {
                    if (mPasswordField.getText().length() >= 8) {
                        final String birthday = mBirthdayDayField.getText() + "." +
                                 mBirthdayMonthField.getText() + "." + 
                                 mBirthdayYearField.getText();
                        final List<String> interests = new ArrayList<>();
                        final String[] interestsLines = mInterestTextArea.getText().split("\n");
                        for (final String interest : interestsLines) {
                            interests.add(interest);
                        }
                        if (mController.registration(mUsernameField.getText(),
                                mPasswordField.getText(), mEmailField.getText(),
                                mFullNameField.getText(), mWorkField.getText(),
                                birthday, interests)) {
                            JOptionPane.showMessageDialog(mFrame,
                                "Registration completed!");
                        } else {
                            JOptionPane.showMessageDialog(mFrame,
                                "Registration failed!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(mFrame,
                                "Password must have 8 symbols at least!");
                    }
                } else {
                    JOptionPane.showMessageDialog(mFrame, "Username is empty!");
                }
            } else if (e.getSource() == mCancelRegistrationButton) {
                showStartScreen();
            }
        }
    }
}