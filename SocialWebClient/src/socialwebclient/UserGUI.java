package socialwebclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

public class UserGUI {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 720;
   
    private final JFrame mFrame;
    
    private final Screen mRegistrationScreen = new Screen();
    private final Screen mAutorizationScreen = new Screen();
    private final Screen mMainScreen = new Screen();
    private final Screen mMessagesScreen = new Screen();
    private final Screen mCommunitiesScreen = new Screen();
    private final Screen mCommunityCreateScreen = new Screen();
    private final Screen mCommunitiesViewScreen = new Screen();
    
    private final List<JComponent> mComponents = new ArrayList<>();
    
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
    
    private final JButton mSendMessageButton;
    private final JLabel mMessageToLabel;
    private final JTextField mMessageToField;
    private final JTextArea mMessageToArea;
    private final JTextArea mMessageDialog;
    private final JScrollPane mMessageDialogScroll;
    private final JList mRecentMessageList;
    private final JLabel mRecentMessageLabel;
    private final DefaultListModel<String> mRecentMessageModel = new DefaultListModel<>();
    private final JScrollPane mDialogsScroll;
    
    private final JLabel mInterestsLabel;
    private final JTree mInterestsTree;
    private final DefaultMutableTreeNode mRootInterest;
    private final JButton mAddInterestButton;
    
    private final JTextArea mInterestTextArea;
    
    private final JButton mRegistrationButton;
    private final JButton mLoginButton;
    private final JButton mCancelRegistrationButton;
    private final JButton mAcceptRegistrationButton;
    
    private final JButton mLogOffButton;
    
    private final JButton mMessagesButton;
    private final JButton mBackFromMessagesButton;
    private final JButton mShowDialogButton;
    
    private final JButton mCommunityButton;
    private final JButton mCommunityCreateButton;
    private final JButton mCommunitiesShow;
    
    private final JLabel mCommunityCreateNameLabel;
    private final JTextField mCommunityCreateNameField;
    private final JLabel mCommunityCreateModeratorLabel;
    private final JTextField mCommunityCreateModeratorField;
    private final JButton mCommunityCreateConfirmButton;
    private final JButton mCommunityCreateCancelButton;
    
    private final JList<String> mCommunitiesList;
    private final JLabel mCommunitiesNameLabel;
    private final JTextField mCommunitiesNameField;
    private final JLabel mCommunitiesModeratorLabel;
    private final JTextField mCommunitiesModeratorField;
    
    private final ClickListener mClickListener;
    private final ListListener mListListener;
    
    private final Controller mController;
    
    private String mUserName = null;
    private List<Pair> mInterests;
    
    private RefreshDialogThread mRefreshDialogThread = null;
    
    public UserGUI(final Controller controller) {
        mController = controller;
        mClickListener = new ClickListener();
        mListListener = new ListListener();

        mFrame = new JFrame("Социальная сеть");
        mFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mFrame.setSize(WIDTH, HEIGHT);
        mFrame.setLayout(null);
        
        mUsernameLabel = new JLabel("Имя аккаунта");
        mUsernameLabel.setBounds(50, 20, 120, 20);
        mRegistrationScreen.add(mUsernameLabel);
        mAutorizationScreen.add(mUsernameLabel);
        mUsernameField = new JTextField();
        mUsernameField.setBounds(200, 20, 100, 20);
        mRegistrationScreen.add(mUsernameField);
        mAutorizationScreen.add(mUsernameField);
        
        mPasswordLabel = new JLabel("Пароль");
        mPasswordLabel.setBounds(50, 60, 120, 20);
        mRegistrationScreen.add(mPasswordLabel);
        mAutorizationScreen.add(mPasswordLabel);
        mPasswordField = new JTextField();
        mPasswordField.setBounds(200, 60, 100, 20);
        mRegistrationScreen.add(mPasswordField);
        mAutorizationScreen.add(mPasswordField);
        
        mEmailLabel = new JLabel("Электронная почта");
        mEmailLabel.setBounds(50, 100, 120, 20);
        mRegistrationScreen.add(mEmailLabel);
        mEmailField = new JTextField();
        mEmailField.setBounds(200, 100, 100, 20);
        mRegistrationScreen.add(mEmailField);
        
        mFullNameLabel = new JLabel("Полное имя");
        mFullNameLabel.setBounds(50, 140, 120, 20);
        mRegistrationScreen.add(mFullNameLabel);
        mFullNameField = new JTextField();
        mFullNameField.setBounds(200, 140, 100, 20);
        mRegistrationScreen.add(mFullNameField);
        
        mWorkLabel = new JLabel("Место учебы/работы");
        mWorkLabel.setBounds(50, 180, 120, 20);
        mRegistrationScreen.add(mWorkLabel);
        mWorkField = new JTextField();
        mWorkField.setBounds(200, 180, 100, 20);
        mRegistrationScreen.add(mWorkField);
        
        mBirthdayLabel = new JLabel("День рождения");
        mBirthdayLabel.setBounds(50, 220, 120, 20);
        mRegistrationScreen.add(mBirthdayLabel);
        mBirthdayDayField = new JTextField();
        mBirthdayDayField.setBounds(200, 220, 30, 20);
        mRegistrationScreen.add(mBirthdayDayField);
        mBirthdayMonthField = new JTextField();
        mBirthdayMonthField.setBounds(235, 220, 30, 20);
        mRegistrationScreen.add(mBirthdayMonthField);
        mBirthdayYearField = new JTextField();
        mBirthdayYearField.setBounds(270, 220, 30, 20);
        mRegistrationScreen.add(mBirthdayYearField);
        
        mAcceptRegistrationButton = new JButton("Подтвердить");
        mAcceptRegistrationButton.setBounds(50, 600, 250, 30);
        mRegistrationScreen.add(mAcceptRegistrationButton);
        mAcceptRegistrationButton.addActionListener(mClickListener);
        
        mCancelRegistrationButton = new JButton("Отмена");
        mCancelRegistrationButton.setBounds(50, 640, 250, 30);
        mRegistrationScreen.add(mCancelRegistrationButton);
        mCancelRegistrationButton.addActionListener(mClickListener);
        
        mLoginButton = new JButton("Вход");
        mLoginButton.setBounds(50, 100, 200, 30);
        mAutorizationScreen.add(mLoginButton);
        mLoginButton.addActionListener(mClickListener);
        
        mRegistrationButton = new JButton("Регистрация");
        mRegistrationButton.setBounds(50, 140, 200, 30);
        mAutorizationScreen.add(mRegistrationButton);
        mRegistrationButton.addActionListener(mClickListener);
         
        mInterests = mController.getInterestsTree();
        mInterestsLabel = new JLabel("Интересы:");
        mInterestsLabel.setBounds(50, 260, 120, 20);
        mRegistrationScreen.add(mInterestsLabel);
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
        mRegistrationScreen.add(mAddInterestButton);
        mAddInterestButton.addActionListener(mClickListener);
        
        mInterestTextArea = new JTextArea();
        mInterestTextArea.setBounds(300, 315, 150, 150);
        mRegistrationScreen.add(mInterestTextArea);
        
        mRegistrationScreen.add(mInterestsTree);
        
        mLogOffButton = new JButton("Выход");
        mLogOffButton.setBounds(50, 50, 150, 30);
        mMainScreen.add(mLogOffButton);
        mLogOffButton.addActionListener(mClickListener);
        
        mMessagesButton = new JButton("Сообщения");
        mMessagesButton.setBounds(50, 90, 150, 30);
        mMainScreen.add(mMessagesButton);
        mMessagesButton.addActionListener(mClickListener);
        
        mBackFromMessagesButton = new JButton("Назад");
        mBackFromMessagesButton.setBounds(50, 50, 150, 30);
        mMessagesScreen.add(mBackFromMessagesButton);
        mBackFromMessagesButton.addActionListener(mClickListener);
        
        mMessageToLabel = new JLabel("Кому");
        mMessageToLabel.setBounds(50, 100, 120, 20);
        mMessagesScreen.add(mMessageToLabel);
        mMessageToField = new JTextField();
        mMessageToField.setBounds(200, 100, 100, 20);
        mMessagesScreen.add(mMessageToField);
        
        mMessageToArea = new JTextArea();
        mMessageToArea.setBounds(50, 140, 300, 100);
        mMessageToArea.setLineWrap(true);
        mMessagesScreen.add(mMessageToArea);
        
        mRecentMessageLabel = new JLabel("Диалоги");
        mRecentMessageLabel.setBounds(450, 100, 120, 20);
        mMessagesScreen.add(mRecentMessageLabel);
        
        mRecentMessageList = new JList(mRecentMessageModel);
        mDialogsScroll = new JScrollPane (mRecentMessageList, 
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mDialogsScroll.setBounds(450, 130, 120, 190);
        mMessagesScreen.add(mDialogsScroll);
        final ListSelectionModel listSelectionModel = mRecentMessageList.getSelectionModel();
        listSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                final ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                if (!lsm.isSelectionEmpty()) {
                    int minIndex = lsm.getMinSelectionIndex();
                    int maxIndex = lsm.getMaxSelectionIndex();
                    for (int i = minIndex; i <= maxIndex; i++) {
                        if (lsm.isSelectedIndex(i) && i > -1 && i < mRecentMessageModel.getSize()) {
                            final String name = mRecentMessageModel.get(i);
                            mMessageToField.setText(name);
                            mMessageDialog.setText(mController.getMessages(mUserName, name));
                            if (mRefreshDialogThread != null) {
                                mRefreshDialogThread.setSecond(name);
                            }
                        }
                    }
                }
            }
        });
        
        mSendMessageButton = new JButton("Отправить");
        mSendMessageButton.setBounds(50, 260, 140, 30);
        mMessagesScreen.add(mSendMessageButton);
        mSendMessageButton.addActionListener(mClickListener);
        
        mMessageDialog = new JTextArea();
        mMessageDialog.setLineWrap(true);
        
        mMessageDialogScroll = new JScrollPane (mMessageDialog, 
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mMessageDialogScroll.setBounds(50, 340, 350, 200);
        mMessagesScreen.add(mMessageDialogScroll);
        
        mShowDialogButton = new JButton("Показать диалог");
        mShowDialogButton.setBounds(210, 260, 140, 30);
        mMessagesScreen.add(mShowDialogButton);
        mShowDialogButton.addActionListener(mClickListener);
        
        mCommunityButton = new JButton("Сообщества");
        mCommunityButton.setBounds(50, 130, 150, 30);
        mMainScreen.add(mCommunityButton);
        mCommunityButton.addActionListener(mClickListener);
        
        mCommunityCreateButton = new JButton("Создать сообщество");
        mCommunityCreateButton.setBounds(50, 130, 150, 30);
        mCommunitiesScreen.add(mCommunityCreateButton);
        mCommunityCreateButton.addActionListener(mClickListener);
        
        mCommunityCreateNameLabel = new JLabel("Название");
        mCommunityCreateNameLabel.setBounds(50, 20, 120, 20);
        mCommunityCreateScreen.add(mCommunityCreateNameLabel);
        mCommunityCreateNameField = new JTextField();
        mCommunityCreateNameField.setBounds(200, 20, 100, 20);
        mCommunityCreateScreen.add(mCommunityCreateNameField);
        
        mCommunityCreateModeratorLabel = new JLabel("Модератор");
        mCommunityCreateModeratorLabel.setBounds(50, 50, 120, 20);
        mCommunityCreateScreen.add(mCommunityCreateModeratorLabel);
        mCommunityCreateModeratorField = new JTextField();
        mCommunityCreateModeratorField.setBounds(200, 50, 100, 20);
        mCommunityCreateScreen.add(mCommunityCreateModeratorField);
        
        mCommunityCreateConfirmButton = new JButton("OK");
        mCommunityCreateConfirmButton.setBounds(50, 130, 150, 30);
        mCommunityCreateScreen.add(mCommunityCreateConfirmButton);
        mCommunityCreateConfirmButton.addActionListener(mClickListener);
        
        mCommunityCreateCancelButton = new JButton("Отмена");
        mCommunityCreateCancelButton.setBounds(250, 130, 150, 30);
        mCommunityCreateScreen.add(mCommunityCreateCancelButton);
        mCommunityCreateCancelButton.addActionListener(mClickListener);
        
        mCommunitiesShow = new JButton("Найти сообщество");
        mCommunitiesShow.setBounds(50, 90, 150, 30);
        mCommunitiesScreen.add(mCommunitiesShow);
        mCommunitiesShow.addActionListener(mClickListener);
        
        mCommunitiesList = new JList<>();
        mCommunitiesList.setBounds(50, 50, 150, 200);
        mCommunitiesViewScreen.add(mCommunitiesList);
        mCommunitiesList.addListSelectionListener(mListListener);
        
        mCommunitiesNameLabel = new JLabel("Название");
        mCommunitiesNameLabel.setBounds(250, 20, 120, 20);
        mCommunitiesViewScreen.add(mCommunitiesNameLabel);
        mCommunitiesNameField = new JTextField();
        mCommunitiesNameField.setBounds(400, 20, 120, 20);
        mCommunitiesNameField.setEnabled(false);
        mCommunitiesViewScreen.add(mCommunitiesNameField);
        
        mCommunitiesModeratorLabel = new JLabel("Модератор");
        mCommunitiesModeratorLabel.setBounds(250, 50, 120, 20);
        mCommunitiesViewScreen.add(mCommunitiesModeratorLabel);
        mCommunitiesModeratorField = new JTextField();
        mCommunitiesModeratorField.setBounds(400, 50, 100, 20);
        mCommunitiesViewScreen.add(mCommunitiesModeratorField);
        
        mComponents.addAll(mAutorizationScreen.getComponents());
        mComponents.addAll(mRegistrationScreen.getComponents());
        mComponents.addAll(mMainScreen.getComponents());
        mComponents.addAll(mMessagesScreen.getComponents());
        mComponents.addAll(mCommunitiesScreen.getComponents());
        mComponents.addAll(mCommunityCreateScreen.getComponents());
        mComponents.addAll(mCommunitiesViewScreen.getComponents());
    }
    
    public void show() {
        mFrame.setVisible(true);
        mAutorizationScreen.show(mFrame);
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
                clear();
                mRegistrationScreen.show(mFrame);
            } else if (e.getSource() == mLoginButton) {
                if (!mUsernameField.getText().isEmpty()) {
                    if (mPasswordField.getText().length() >= 8) {
                        if (mController.authorization(mUsernameField.getText(),
                                mPasswordField.getText())) {
                            mUserName = mUsernameField.getText();
                            JOptionPane.showMessageDialog(mFrame,
                                "Вход выполнен!");
                            clear();
                            mMainScreen.show(mFrame);
                        } else {
                            JOptionPane.showMessageDialog(mFrame,
                                "Имя аккаунта или пароль не верен!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(mFrame,
                                "Пароль должен иметь минимум 8 символов");
                    }
                } else {
                    JOptionPane.showMessageDialog(mFrame, "Введите имя аккаунта");
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
                                "Регистрация завершена успешно!");
                        } else {
                            JOptionPane.showMessageDialog(mFrame,
                                "Имя аккаунта уже занято!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(mFrame,
                                "Пароль должен иметь минимум 8 символов");
                    }
                } else {
                    JOptionPane.showMessageDialog(mFrame, "Введите имя аккаунта");
                }
            } else if (e.getSource() == mCancelRegistrationButton) {
                clear();
                mAutorizationScreen.show(mFrame);
            } else if (e.getSource() == mLogOffButton) {
                clear();
                mAutorizationScreen.show(mFrame);
            } else if (e.getSource() == mMessagesButton) {
                clear();
                mMessagesScreen.show(mFrame);
                if (mRefreshDialogThread != null) {
                    mRefreshDialogThread.breakRefresh();
                }
                mRefreshDialogThread = new RefreshDialogThread(mUserName, mMessageDialog,
                        mRecentMessageModel, mController);
                mRefreshDialogThread.start();
            } else if (e.getSource() == mBackFromMessagesButton) {
                clear();
                mMainScreen.show(mFrame);
                if (mRefreshDialogThread != null) {
                    mRefreshDialogThread.breakRefresh();
                }
            } else if (e.getSource() == mSendMessageButton) {
                if (mMessageToField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(mFrame, "Введите имя получателя!");
                } else {
                    if (mMessageToArea.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(mFrame, "Введите сообщение!");
                    } else {
                        if (mController.sendMessage(mUserName, mMessageToField.getText(),
                                mMessageToArea.getText())) {
                            JOptionPane.showMessageDialog(mFrame,
                                        "Сообщение отправлено успешно!");
                        } else {
                            JOptionPane.showMessageDialog(mFrame,
                                        "Сообщение не отправлено!");
                        }
                    }
                }
            } else if (e.getSource() == mShowDialogButton) {
                if (mMessageToField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(mFrame, "Введите имя получателя!");
                } else {
                    mMessageDialog.setText(mController.getMessages(mUserName, mMessageToField.getText()));
                    if (mRefreshDialogThread != null) {
                        mRefreshDialogThread.setSecond(mMessageToField.getText());
                    }
                }           
            } else if (e.getSource() == mCommunityButton) {
                clear();
                mCommunitiesScreen.show(mFrame);
            } else if (e.getSource() == mCommunityCreateButton) {
                clear();
                mCommunityCreateScreen.show(mFrame);
            } else if (e.getSource() == mCommunityCreateCancelButton) {
                clear();
                mCommunitiesScreen.show(mFrame);
            } else if (e.getSource() == mCommunitiesShow) {
                clear();
                mCommunitiesList.setListData(mController.getCommunities());
                mCommunitiesViewScreen.show(mFrame);
            }
        }
    }
    
    private class ListListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getSource() == mCommunitiesList) {
                String[] communityInfo = mController.getCommunity(mCommunitiesList.getSelectedValue());
                if(communityInfo.length == 0)
                    return;
                mCommunitiesNameField.setText(communityInfo[0]);
                mCommunitiesModeratorField.setText(communityInfo[1]);
                if(communityInfo[1].equals(mUserName))
                    mCommunitiesModeratorField.setEnabled(true);
                else mCommunitiesModeratorField.setEnabled(false);
            }
        }
    }
}