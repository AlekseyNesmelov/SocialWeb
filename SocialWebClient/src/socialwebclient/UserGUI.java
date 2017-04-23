package socialwebclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
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
    
    private final JButton mCommunityCreateButton;
    private final JButton mCommunitiesShow;
    
    private final JLabel mCommunityNameLabel;
    private final JTextField mCommunityNameField;
    private final JLabel mCommunityModeratorLabel;
    private final JTextField mCommunityModeratorField;
    private final ButtonGroup mCommunityMethod;
    private final JRadioButton freeRB;
    private final JRadioButton restrictedRB;
    private final JButton mCommunityCreateConfirmButton;
    private final JButton mCommunityCreateCancelButton;
    private final JButton mCommunityEditButton;
    private final JTree mCommunityInterestsTree;
    private final JButton mCommunityAddInterestButton;
    private final JButton mCommunityDeleteInterestButton;
    private final JTextArea mCommunityInterestTextArea;
    private final JList<String> mCommunitiesList;
    private final JTextArea mCommunityMembers;
    private final JButton mCommunityJoinButton;
    private final JButton mCommunityQuitButton;
    private final JTextField mCommunityInviteField;
    private final JButton mCommunityInviteButton;
    
    private final JButton mMainButton;
    
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
        mRootInterest = new DefaultMutableTreeNode();
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
        mInterestTextArea.setEditable(false);
        mRegistrationScreen.add(mInterestTextArea);
        
        mRegistrationScreen.add(mInterestsTree);
        
        mLogOffButton = new JButton("Выход");
        mLogOffButton.setBounds(200, 100, 200, 50);
        mMainScreen.add(mLogOffButton);
        mLogOffButton.addActionListener(mClickListener);
        
        mMessagesButton = new JButton("Сообщения");
        mMessagesButton.setBounds(200, 170, 200, 50);
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
        
        mCommunityCreateButton = new JButton("Создать сообщество");
        mCommunityCreateButton.setBounds(200, 240, 200, 50);
        mMainScreen.add(mCommunityCreateButton);
        mCommunityCreateButton.addActionListener(mClickListener);
        
        mCommunityNameLabel = new JLabel("Название");
        mCommunityNameLabel.setBounds(50, 20, 120, 20);
        mCommunityCreateScreen.add(mCommunityNameLabel);
        mCommunitiesViewScreen.add(mCommunityNameLabel);
        mCommunityNameField = new JTextField();
        mCommunityNameField.setBounds(200, 20, 100, 20);
        mCommunityCreateScreen.add(mCommunityNameField);
        mCommunitiesViewScreen.add(mCommunityNameField);
        
        mCommunityModeratorLabel = new JLabel("Модератор");
        mCommunityModeratorLabel.setBounds(50, 50, 120, 20);
        mCommunityCreateScreen.add(mCommunityModeratorLabel);
        mCommunitiesViewScreen.add(mCommunityModeratorLabel);
        mCommunityModeratorField = new JTextField();
        mCommunityModeratorField.setBounds(200, 50, 100, 20);
        mCommunityCreateScreen.add(mCommunityModeratorField);
        mCommunitiesViewScreen.add(mCommunityModeratorField);
        
        mCommunityMethod = new ButtonGroup();
        freeRB = new JRadioButton("Открытое");
        freeRB.setBounds(50, 80, 100, 20);
        freeRB.setSelected(true);
        restrictedRB = new JRadioButton("Закрытое");
        restrictedRB.setBounds(170, 80, 100, 20);
        mCommunityMethod.add(freeRB);
        mCommunityMethod.add(restrictedRB);
        mCommunityCreateScreen.add(freeRB);
        mCommunityCreateScreen.add(restrictedRB);
        mCommunitiesViewScreen.add(freeRB);
        mCommunitiesViewScreen.add(restrictedRB);
        
        mCommunityInterestsTree = new JTree(mRootInterest);
        mCommunityInterestsTree.setBounds(50, 150, 200, 300);
        mCommunityCreateScreen.add(mCommunityInterestsTree);
        mCommunitiesViewScreen.add(mCommunityInterestsTree);
        
        mCommunityAddInterestButton = new JButton("Добавить");
        mCommunityAddInterestButton.setBounds(260, 150, 120, 30);
        mCommunityCreateScreen.add(mCommunityAddInterestButton);
        mCommunitiesViewScreen.add(mCommunityAddInterestButton);
        mCommunityAddInterestButton.addActionListener(mClickListener);
        
        mCommunityDeleteInterestButton = new JButton("Удалить");
        mCommunityDeleteInterestButton.setBounds(260, 190, 120, 30);
        mCommunityCreateScreen.add(mCommunityDeleteInterestButton);
        mCommunitiesViewScreen.add(mCommunityDeleteInterestButton);
        mCommunityDeleteInterestButton.addActionListener(mClickListener);
        
        mCommunityInterestTextArea = new JTextArea();
        mCommunityInterestTextArea.setBounds(260, 230, 120, 220);
        mCommunityInterestTextArea.setEditable(false);
        mCommunityCreateScreen.add(mCommunityInterestTextArea);
        mCommunitiesViewScreen.add(mCommunityInterestTextArea);
        
        mCommunityCreateConfirmButton = new JButton("OK");
        mCommunityCreateConfirmButton.setBounds(50, 480, 150, 30);
        mCommunityCreateScreen.add(mCommunityCreateConfirmButton);
        mCommunityCreateConfirmButton.addActionListener(mClickListener);
        
        mCommunityCreateCancelButton = new JButton("Отмена");
        mCommunityCreateCancelButton.setBounds(250, 480, 150, 30);
        mCommunityCreateScreen.add(mCommunityCreateCancelButton);
        mCommunityCreateCancelButton.addActionListener(mClickListener);
        
        mCommunitiesShow = new JButton("Найти сообщество");
        mCommunitiesShow.setBounds(200, 310, 200, 50);
        mMainScreen.add(mCommunitiesShow);
        mCommunitiesShow.addActionListener(mClickListener);
        
        mCommunityEditButton = new JButton("Редактировать");
        mCommunityEditButton.setBounds(50, 480, 150, 30);
        mCommunitiesViewScreen.add(mCommunityEditButton);
        mCommunityEditButton.addActionListener(mClickListener);
        
        mCommunitiesList = new JList<>();
        mCommunitiesList.setBounds(400, 50, 175, 200);
        mCommunitiesViewScreen.add(mCommunitiesList);
        mCommunitiesList.addListSelectionListener(mListListener);
        
        mCommunityMembers = new JTextArea();
        mCommunityMembers.setBounds(50, 550, 500, 75);
        mCommunityMembers.setEditable(false);
        mCommunitiesViewScreen.add(mCommunityMembers);
        
        mCommunityJoinButton = new JButton("Вступить в сообщество");
        mCommunityJoinButton.setBounds(400, 270, 175, 90);
        mCommunityJoinButton.setEnabled(false);
        mCommunitiesViewScreen.add(mCommunityJoinButton);
        mCommunityJoinButton.addActionListener(mClickListener);
        
        mCommunityQuitButton = new JButton("Выйти из сообщества");
        mCommunityQuitButton.setBounds(400, 370, 175, 90);
        mCommunityQuitButton.setEnabled(false);
        mCommunitiesViewScreen.add(mCommunityQuitButton);
        mCommunityQuitButton.addActionListener(mClickListener);
        
        mCommunityInviteField = new JTextField();
        mCommunityInviteField.setBounds(250, 480, 150, 20);
        mCommunityInviteField.setEnabled(false);
        mCommunitiesViewScreen.add(mCommunityInviteField);
        
        mCommunityInviteButton = new JButton("Пригласить");
        mCommunityInviteButton.setBounds(250, 510, 150, 30);
        mCommunityInviteButton.setEnabled(false);
        mCommunitiesViewScreen.add(mCommunityInviteButton);
        mCommunityInviteButton.addActionListener(mClickListener);
        
        mMainButton = new JButton("Главная страница");
        mMainButton.setBounds(10, HEIGHT - 70, WIDTH - 30, 30);
        mMessagesScreen.add(mMainButton);
        mCommunityCreateScreen.add(mMainButton);
        mCommunitiesViewScreen.add(mMainButton);
        mMainButton.addActionListener(mClickListener);
        
        mComponents.addAll(mAutorizationScreen.getComponents());
        mComponents.addAll(mRegistrationScreen.getComponents());
        mComponents.addAll(mMainScreen.getComponents());
        mComponents.addAll(mMessagesScreen.getComponents());
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
            } else if (e.getSource() == mMainButton) {
                clear();
                mMainScreen.show(mFrame);
            } else if (e.getSource() == mCommunityCreateButton) {
                mCommunityNameField.setText("");
                mCommunityModeratorField.setText(mUserName);
                freeRB.setSelected(true);
                restrictedRB.setSelected(false);
                mCommunityNameField.setEnabled(true);
                mCommunityModeratorField.setEnabled(false);
                freeRB.setEnabled(true);
                restrictedRB.setEnabled(true);
                mCommunityAddInterestButton.setEnabled(true);
                mCommunityDeleteInterestButton.setEnabled(true);
                mCommunityInterestTextArea.setText("");
                clear();
                mCommunityCreateScreen.show(mFrame);
            } else if (e.getSource() == mCommunityAddInterestButton) {
                final Object selected = mCommunityInterestsTree.getLastSelectedPathComponent();
                if (selected != null && !mCommunityInterestTextArea.getText().contains(selected.toString())) {
                    mCommunityInterestTextArea.setText(mCommunityInterestTextArea.getText().isEmpty() ?
                            selected.toString() :
                            mCommunityInterestTextArea.getText() + "\n" + selected.toString());
                }
            } else if (e.getSource() == mCommunityDeleteInterestButton) {
                final Object selected = mCommunityInterestsTree.getLastSelectedPathComponent();
                String text = mCommunityInterestTextArea.getText();
                if (selected != null && text.contains(selected.toString())) {
                    if(text.equals(selected.toString())) text = "";
                    else if(text.endsWith(selected.toString())) text = text.replaceFirst("\n" + selected.toString(), "");
                    else text = text.replaceFirst(selected.toString() + "\n", "");
                    mCommunityInterestTextArea.setText(text);
                }
            } else if (e.getSource() == mCommunityCreateConfirmButton) {
                boolean result = mController.createCommunity(mCommunityNameField.getText(),
                        mCommunityModeratorField.getText(),
                        freeRB.isSelected() ? Constants.FREE_METHOD : Constants.RESTRICTED_METHOD,
                        Arrays.asList(mCommunityInterestTextArea.getText().split("\n")));
                if (result) {
                    clear();
                    mMainScreen.show(mFrame);
                    JOptionPane.showMessageDialog(mFrame, "Успешно");
                }
                else JOptionPane.showMessageDialog(mFrame, "Ошибка при создании сообщества");
            } else if (e.getSource() == mCommunityCreateCancelButton) {
                clear();
                mMainScreen.show(mFrame);
            } else if (e.getSource() == mCommunitiesShow) {
                clear();
                mCommunityNameField.setText("");
                mCommunityModeratorField.setText("");
                freeRB.setSelected(true);
                restrictedRB.setSelected(false);
                mCommunityNameField.setEnabled(false);
                mCommunityModeratorField.setEnabled(false);
                freeRB.setEnabled(false);
                restrictedRB.setEnabled(false);
                mCommunityAddInterestButton.setEnabled(false);
                mCommunityDeleteInterestButton.setEnabled(false);
                mCommunityEditButton.setEnabled(false);
                mCommunityInviteField.setEnabled(false);
                mCommunityInviteButton.setEnabled(false);
                mCommunitiesList.setListData(mController.getCommunities());
                mCommunitiesList.setSelectedIndex(-1);
                mCommunityInterestTextArea.setText("");
                mCommunitiesViewScreen.show(mFrame);
            } else if (e.getSource() == mCommunityEditButton) {
                boolean result = mController.editCommunity(mCommunityNameField.getText(),
                        freeRB.isSelected() ? Constants.FREE_METHOD : Constants.RESTRICTED_METHOD,
                        Arrays.asList(mCommunityInterestTextArea.getText().split("\n")));
                if (result) {
                    JOptionPane.showMessageDialog(mFrame, "Успешно");
                }
                else JOptionPane.showMessageDialog(mFrame, "Ошибка при редактировании сообщества");
            } else if (e.getSource() == mCommunityJoinButton) {
                if (mController.joinTheCommunity(mCommunityNameField.getText(), mUserName)) {
                    mCommunityMembers.setText("Участники : " + String.join(", ", mController.getCommunityMembers(mCommunityNameField.getText())));
                }
                else JOptionPane.showMessageDialog(mFrame, "Вы уже тут");
            } else if (e.getSource() == mCommunityQuitButton) {
                String result = mController.quitTheCommunity(mCommunityNameField.getText(), mUserName);
                switch (result) {
                    case Constants.SUCCESS:
                        mCommunityMembers.setText("Участники : " + String.join(", ", mController.getCommunityMembers(mCommunityNameField.getText())));
                        break;
                    case Constants.YOU_ARE_A_MODERATOR:
                        JOptionPane.showMessageDialog(mFrame, "Вы модератор");
                        break;
                    case Constants.NOT_IN_COMMUNITY:
                        JOptionPane.showMessageDialog(mFrame, "Вам не надо выходить из сообщества, если вы не вступали в него");
                        break;
                    case Constants.FAIL:
                        JOptionPane.showMessageDialog(mFrame, "Что-то пошло не так");
                        break;
                }
            } else if (e.getSource() == mCommunityInviteButton) {
                if (mController.joinTheCommunity(mCommunityNameField.getText(), mCommunityInviteField.getText())) {
                    mCommunityMembers.setText("Участники : " + String.join(", ", mController.getCommunityMembers(mCommunityNameField.getText())));
                }
                else JOptionPane.showMessageDialog(mFrame, "Ошибка");
            }
        }
    }
    
    private class ListListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getSource() == mCommunitiesList) {
                if(mCommunitiesList.getSelectedIndex() >= mCommunitiesList.getModel().getSize()) {
                    mCommunitiesList.setSelectedIndex(-1);
                    return;
                }
                if(mCommunitiesList.getSelectedIndex() == -1)
                    return;
                String[] communityInfo = mController.getCommunity(mCommunitiesList.getSelectedValue());
                if(communityInfo.length == 0)
                    return;
                String[] communityMembers = mController.getCommunityMembers(communityInfo[0]);
                mCommunityNameField.setText(communityInfo[0]);
                mCommunityNameField.setEnabled(false);
                mCommunityModeratorField.setText(communityInfo[1]);
                mCommunityModeratorField.setEnabled(false);
                mCommunityMembers.setText("Участники : " + String.join(", ", communityMembers));
                if(communityInfo[2].equals(Constants.FREE_METHOD)) {
                    freeRB.setSelected(true);
                    restrictedRB.setSelected(false);
                }
                else {
                    freeRB.setSelected(false);
                    restrictedRB.setSelected(true);
                }
                if(communityInfo.length > 2)
                    mCommunityInterestTextArea.setText(String.join("\n", Arrays.copyOfRange(communityInfo, 3, communityInfo.length)));
                else mCommunityInterestTextArea.setText("");
                if(communityInfo[1].equals(mUserName)) {
                    freeRB.setEnabled(true);
                    restrictedRB.setEnabled(true);
                    mCommunityAddInterestButton.setEnabled(true);
                    mCommunityDeleteInterestButton.setEnabled(true);
                    mCommunityEditButton.setEnabled(true);
                    mCommunityInviteField.setEnabled(true);
                    mCommunityInviteButton.setEnabled(true);
                }
                else {
                    freeRB.setEnabled(false);
                    restrictedRB.setEnabled(false);
                    mCommunityAddInterestButton.setEnabled(false);
                    mCommunityDeleteInterestButton.setEnabled(false);
                    mCommunityEditButton.setEnabled(false);
                    mCommunityInviteField.setEnabled(false);
                    mCommunityInviteButton.setEnabled(false);
                }
                if(communityInfo[2].equals(Constants.FREE_METHOD)) {
                    mCommunityJoinButton.setEnabled(true);
                }
                else {
                    mCommunityJoinButton.setEnabled(false);
                }
                    mCommunityQuitButton.setEnabled(true);
            }
        }
    }
}