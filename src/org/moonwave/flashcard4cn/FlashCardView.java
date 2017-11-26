/*
 * ============================================================================
 * GNU Lesser General Public License
 * ============================================================================
 *
 * flashcard4cn - FlashCard for Chinese Characters
 * Copyright (C) Jonathan Luo
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307, USA.
 *
 */

package org.moonwave.flashcard4cn;

import java.awt.Font;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.SpinnerNumberModel;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import org.moonwave.util.ImageUtil;

/**
 * The application's main frame.
 * 
 * @author Jonathan Luo
 */
public class FlashCardView extends FrameView {

    int numberOfWordTested = 0;
    int noWordsPerTest = 3;
    double defaultWordDisplayStopWatch = 3;
    StopWatch stopWatch;
    ResourceMap resourceMap = null;
    TextFlashCardEntity textFlashCard = new TextFlashCardEntity();
    ImageFlashCardEntity imageFlashCard = new ImageFlashCardEntity();
    RandomGenerator randomGR = new RandomGenerator();

    private String gradeId;
    private int endIdx = 45;
    private int imageCount = 45;

    // default settings
    private void postInit(SingleFrameApplication app) {
        this.lblCountDown.setText(String.valueOf(defaultWordDisplayStopWatch));
        this.lblTestCount.setText("0");
        this.spinWordsPerTest.setValue(noWordsPerTest);

        btnImage.setIcon(null);
        btnImage.setText("");

        javax.swing.SpinnerNumberModel model = new javax.swing.SpinnerNumberModel();
        model.setMinimum(1.0d);
        model.setMaximum(20.0d);
        model.setStepSize(0.5d);
        model.setValue(3.0d);
        this.spinDisplayInterval.setModel(model);
        spinDisplayInterval.setValue(model.getValue());

        textFlashCard.loadFlashCardKeys();
        textFlashCard.loadFlashCards();
        textFlashCard.loadFlashCard(textFlashCard.getFlashCardNames().get(0));

        int wordsAvailable = textFlashCard.getFlashCard().length;
        System.out.println(textFlashCard.getFlashCardNames().get(0) + " words available: " + wordsAvailable);
        if (noWordsPerTest >= wordsAvailable) {
            noWordsPerTest = wordsAvailable;
        }
        SpinnerNumberModel spinWordsModel = new SpinnerNumberModel(noWordsPerTest, 0, wordsAvailable, 1);
        this.spinWordsPerTest.setModel(spinWordsModel);

        cbFlashCard.setModel(new javax.swing.DefaultComboBoxModel(textFlashCard.getFlashCardNames().toArray()));

        imageFlashCard.loadFlashCardKeys();
        imageFlashCard.createAndSortFlashCardNames();

        resourceMap = app.getContext().getResourceManager().getResourceMap(FlashCardApp.class);

        // pre-load initial images
        ImageUtil.clearAll(); 
        endIdx = imageCount - 1;

        initDisplayFields();
        // initialize font and font size drop down boxs
        initFont();
        initFontSize();
        setDisplayControlIdleSize();
    }

    private void initDisplayFields() {
        txtDisplay.setVisible(false);
        btnImage.setVisible(false);
        lblDisplay.setVisible(true);
    }

    private void initFont() {
        this.textFlashCard.getCHNFontNames();
        List<String> fontNames = textFlashCard.getFontNames();
        for (String fontName : fontNames) {
            cbFont.addItem(fontName);
        }
    }

    private void initFontSize() {
        cbFontSize.addItem("16");
        cbFontSize.addItem("18");
        cbFontSize.addItem("20");
        cbFontSize.addItem("22");
        cbFontSize.addItem("24");
        cbFontSize.addItem("26");
        cbFontSize.addItem("28");
        cbFontSize.addItem("32");
        cbFontSize.addItem("36");
        cbFontSize.addItem("40");
        cbFontSize.addItem("44");
        cbFontSize.addItem("48");
        cbFontSize.addItem("54");
        cbFontSize.addItem("60");
        cbFontSize.addItem("66");
        cbFontSize.addItem("72");
        cbFontSize.addItem("80");
        cbFontSize.addItem("88");
        cbFontSize.addItem("96");
        cbFontSize.addItem("104");
        cbFontSize.addItem("112");
        cbFontSize.addItem("120");
        cbFontSize.addItem("128");
        cbFontSize.addItem("144");
        cbFontSize.addItem("152");
        cbFontSize.addItem("160");
        cbFontSize.addItem("200");
        cbFontSize.addItem("216");
        cbFontSize.addItem("232");
        cbFontSize.addItem("240");
        cbFontSize.addItem("256");
        cbFontSize.addItem("272");
        cbFontSize.addItem("288");
        cbFontSize.addItem("304");
        cbFontSize.addItem("320");
        cbFontSize.addItem("336");
        cbFontSize.addItem("352");
        cbFontSize.addItem("368");
        cbFontSize.addItem("384");
        cbFontSize.addItem("400");
        cbFontSize.setSelectedItem("256");
    }

    public FlashCardView(SingleFrameApplication app) {
        super(app);
        initComponents();
        ckRandom.setSelected(true);

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });

        //
        postInit(app);
    }
    
    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = FlashCardApp.getApplication().getMainFrame();
            aboutBox = new DlgAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        FlashCardApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        txtDisplay = new javax.swing.JTextField();
        lblDisplay = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblCountDown = new javax.swing.JLabel();
        lblTestCount = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        cbFlashCard = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbFont = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        spinWordsPerTest = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        spinDisplayInterval = new javax.swing.JSpinner();
        ckContrast = new javax.swing.JCheckBox();
        btnStartStop = new javax.swing.JButton();
        cbFontSize = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        ckImageFlashCard = new javax.swing.JCheckBox();
        ckRandom = new javax.swing.JCheckBox();
        btnImage = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        toolsMenu = new javax.swing.JMenu();
        jMenuItemConfig = new javax.swing.JMenuItem();
        jmenuConfigImageFlashCard = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.moonwave.flashcard4cn.FlashCardApp.class).getContext().getResourceMap(FlashCardView.class);
        txtDisplay.setText(resourceMap.getString("txtDisplay.text")); // NOI18N
        txtDisplay.setName("txtDisplay"); // NOI18N

        lblDisplay.setText(resourceMap.getString("lblDisplay.text")); // NOI18N
        lblDisplay.setName("lblDisplay"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N

        lblCountDown.setFont(resourceMap.getFont("lblCountDown.font")); // NOI18N
        lblCountDown.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblCountDown.setText(resourceMap.getString("lblCountDown.text")); // NOI18N
        lblCountDown.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblCountDown.setName("lblCountDown"); // NOI18N

        lblTestCount.setFont(resourceMap.getFont("lblTestCount.font")); // NOI18N
        lblTestCount.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblTestCount.setText(resourceMap.getString("lblTestCount.text")); // NOI18N
        lblTestCount.setName("lblTestCount"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTestCount, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblCountDown, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lblCountDown, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblTestCount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel6, lblTestCount});

        jPanel2.setName("jPanel2"); // NOI18N

        cbFlashCard.setName("cbFlashCard"); // NOI18N
        cbFlashCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFlashCardActionPerformed(evt);
            }
        });

        jLabel5.setLabelFor(cbFlashCard);
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        cbFont.setName("cbFont"); // NOI18N
        cbFont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFontActionPerformed(evt);
            }
        });

        jLabel1.setLabelFor(spinWordsPerTest);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        spinWordsPerTest.setName("spinWordsPerTest"); // NOI18N
        spinWordsPerTest.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinWordsPerTestStateChanged(evt);
            }
        });

        jLabel2.setLabelFor(spinDisplayInterval);
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        spinDisplayInterval.setName("spinDisplayInterval"); // NOI18N
        spinDisplayInterval.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinDisplayIntervalStateChanged(evt);
            }
        });

        ckContrast.setText(resourceMap.getString("ckContrast.text")); // NOI18N
        ckContrast.setName("ckContrast"); // NOI18N
        ckContrast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckContrastActionPerformed(evt);
            }
        });

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(org.moonwave.flashcard4cn.FlashCardApp.class).getContext().getActionMap(FlashCardView.class, this);
        btnStartStop.setAction(actionMap.get("startOrStop")); // NOI18N
        btnStartStop.setText(resourceMap.getString("btnStartStop.text")); // NOI18N
        btnStartStop.setName("btnStartStop"); // NOI18N

        cbFontSize.setName("cbFontSize"); // NOI18N
        cbFontSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFontSizeActionPerformed(evt);
            }
        });

        jLabel3.setLabelFor(cbFontSize);
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        ckImageFlashCard.setText(resourceMap.getString("ckImageFlashCard.text")); // NOI18N
        ckImageFlashCard.setName("ckImageFlashCard"); // NOI18N
        ckImageFlashCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckImageFlashCardActionPerformed(evt);
            }
        });

        ckRandom.setText(resourceMap.getString("ckRandom.text")); // NOI18N
        ckRandom.setActionCommand(resourceMap.getString("ckRandom.actionCommand")); // NOI18N
        ckRandom.setName("ckRandom"); // NOI18N
        ckRandom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckRandomActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(ckImageFlashCard)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbFont, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbFontSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(btnStartStop, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbFlashCard, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addGap(2, 2, 2)
                        .addComponent(spinWordsPerTest, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinDisplayInterval, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ckContrast)
                        .addGap(18, 18, 18)
                        .addComponent(ckRandom)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cbFlashCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinWordsPerTest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(spinDisplayInterval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ckContrast)
                    .addComponent(ckRandom)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbFontSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(btnStartStop)
                    .addComponent(ckImageFlashCard)
                    .addComponent(jLabel4)
                    .addComponent(cbFont, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        ckRandom.getAccessibleContext().setAccessibleName(resourceMap.getString("ckRandom.AccessibleContext.accessibleName")); // NOI18N

        btnImage.setText(resourceMap.getString("btnImage.text")); // NOI18N
        btnImage.setName("btnImage"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(lblDisplay)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnImage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 563, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnImage))
                    .addComponent(lblDisplay)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 162, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setMnemonic('f');
        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        toolsMenu.setMnemonic('T');
        toolsMenu.setText(resourceMap.getString("toolsMenu.text")); // NOI18N
        toolsMenu.setName("toolsMenu"); // NOI18N
        toolsMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolsMenuActionPerformed(evt);
            }
        });

        jMenuItemConfig.setMnemonic('C');
        jMenuItemConfig.setText(resourceMap.getString("jMenuItemConfig.text")); // NOI18N
        jMenuItemConfig.setName("jMenuItemConfig"); // NOI18N
        jMenuItemConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemConfigActionPerformed(evt);
            }
        });
        toolsMenu.add(jMenuItemConfig);

        jmenuConfigImageFlashCard.setMnemonic('m');
        jmenuConfigImageFlashCard.setText(resourceMap.getString("jmenuConfigImageFlashCard.text")); // NOI18N
        jmenuConfigImageFlashCard.setName("jmenuConfigImageFlashCard"); // NOI18N
        jmenuConfigImageFlashCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmenuConfigImageFlashCardActionPerformed(evt);
            }
        });
        toolsMenu.add(jmenuConfigImageFlashCard);

        menuBar.add(toolsMenu);

        helpMenu.setMnemonic('h');
        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 963, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 779, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void spinDisplayIntervalStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinDisplayIntervalStateChanged
        Object displayInSeconds = spinDisplayInterval.getValue();      
        if (displayInSeconds instanceof Double) {
            double wordDisplayedInSeconds = ((Double)displayInSeconds);
            lblCountDown.setText(String.valueOf(wordDisplayedInSeconds));
            if (wordDisplayedInSeconds < 1)
                spinDisplayInterval.setValue(1.0d);
        }
    }//GEN-LAST:event_spinDisplayIntervalStateChanged

    private void spinWordsPerTestStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinWordsPerTestStateChanged
        // TODO add your handling code here:
        Object numberWordsPerTest = spinWordsPerTest.getValue();      
        noWordsPerTest = ((Integer)numberWordsPerTest).intValue();
        if (noWordsPerTest < 1)
            spinWordsPerTest.setValue(1);
    }//GEN-LAST:event_spinWordsPerTestStateChanged

    @Action
    public void openDlgOptions() {
        //DlgOptions dlg = new DlgOptions(FlashCardApp.getApplication().getMainFrame(), true);
        //dlg.setVisible(true);        
    }

    private void cbFlashCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFlashCardActionPerformed
        randomGR.clearGeneratedList();
        
        JComboBox cbFlasgCard = (JComboBox) evt.getSource();
        String flashCardId = (String) cbFlasgCard.getSelectedItem();
        gradeId = flashCardId;
        boolean imageFlashCard = ckImageFlashCard.isSelected();        
        if (AppState.isVerbose())
            System.out.println("You selected: " + flashCardId);
        if (imageFlashCard) {
            // Pre-load images for selected grade
            this.btnStartStop.setEnabled(false);
            ImageUtil.clearAll();
            gradeId = this.imageFlashCard.getFlashcardFolder(flashCardId);
            imageCount = ImageUtil.preLoad(this.imageFlashCard.getFlashcardFolder(flashCardId));
            endIdx = imageCount - 1;

            System.out.println(flashCardId + " images available: " + imageCount);
            if (noWordsPerTest >= imageCount) {
                noWordsPerTest = imageCount;
            }
            SpinnerNumberModel spinWordsModel = new SpinnerNumberModel(noWordsPerTest, 0, imageCount, 1);
            this.spinWordsPerTest.setModel(spinWordsModel);
            this.btnStartStop.setEnabled(true);
            
        } else {
            int wordsAvailable = textFlashCard.getFlashCard().length;
            System.out.println(flashCardId + " words available: " + wordsAvailable);
            if (noWordsPerTest >= wordsAvailable) {
                noWordsPerTest = wordsAvailable;
            }
            SpinnerNumberModel spinWordsModel = new SpinnerNumberModel(noWordsPerTest, 0, wordsAvailable, 1);
            this.spinWordsPerTest.setModel(spinWordsModel);
            this.btnStartStop.setEnabled(false);
            textFlashCard.loadFlashCard(flashCardId);
            this.btnStartStop.setEnabled(true);
        }
    }//GEN-LAST:event_cbFlashCardActionPerformed

    private void ckContrastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckContrastActionPerformed
        JCheckBox ckContrast = (JCheckBox) evt.getSource();
        if (!this.ckImageFlashCard.isSelected()) {
            if (ckContrast.isSelected()) {
                this.txtDisplay.setVisible(true);
                this.lblDisplay.setVisible(false);
            } else {
                this.txtDisplay.setVisible(false);
                this.lblDisplay.setVisible(true);
            }
        }
    }//GEN-LAST:event_ckContrastActionPerformed

    private void setDisplayControl() {
        String fontName = (String)this.cbFont.getSelectedItem();
        String fontSize = (String)this.cbFontSize.getSelectedItem();
        int size = 10;
        if (fontSize != null)
            size = Integer.valueOf(fontSize);
        Font cnFont = new Font(fontName, Font.BOLD, size);

        this.lblDisplay.setFont(cnFont);
        this.txtDisplay.setFont(cnFont);
    }

    private void setDisplayControlIdleSize() {
        int size = 88;
        Font cnFont = this.lblDisplay.getFont();
        //cnFont = cnFont.deriveFont(size);
        cnFont = new Font(cnFont.getFontName(), Font.BOLD, size);
        this.lblDisplay.setFont(cnFont);
        this.txtDisplay.setFont(cnFont);
    }

    private void cbFontActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFontActionPerformed
        JComboBox cb = (JComboBox)evt.getSource();
        String fontName = (String)cb.getSelectedItem();

        String fontSize = (String)this.cbFontSize.getSelectedItem();
        int size = 10;
        if (fontSize != null)
            size = Integer.valueOf(fontSize);
        Font cnFont = new Font(fontName, Font.BOLD, size);

        this.lblDisplay.setFont(cnFont);
        this.txtDisplay.setFont(cnFont);
}//GEN-LAST:event_cbFontActionPerformed

    private void cbFontSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFontSizeActionPerformed
        JComboBox cb = (JComboBox)evt.getSource();
        String fontSize = (String)cb.getSelectedItem();
        int size = 10;
        if (fontSize != null)
            size = Integer.valueOf(fontSize);
        String fontName = (String)this.cbFont.getSelectedItem();
        Font cnFont = new Font(fontName, Font.BOLD, size);
        this.lblDisplay.setFont(cnFont);
        this.txtDisplay.setFont(cnFont);
}//GEN-LAST:event_cbFontSizeActionPerformed

    private void jMenuItemConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemConfigActionPerformed
        DlgConfigTextFlashCard dlg = null;
        JFrame mainFrame = FlashCardApp.getApplication().getMainFrame();
        dlg = new DlgConfigTextFlashCard(FlashCardApp.getApplication().getMainFrame(), true, textFlashCard);
        dlg.setLocationRelativeTo(mainFrame);
        FlashCardApp.getApplication().show(dlg);
        dlg.dispose();

        textFlashCard.loadFlashCardKeys();
        cbFlashCard.setModel(new javax.swing.DefaultComboBoxModel(textFlashCard.getFlashCardNames().toArray()));
        String flashCardId = (String) cbFlashCard.getSelectedItem();
        textFlashCard.loadFlashCard(flashCardId);
        ckImageFlashCard.setSelected(false);
}//GEN-LAST:event_jMenuItemConfigActionPerformed

    private void toolsMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toolsMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_toolsMenuActionPerformed

    private void jmenuConfigImageFlashCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmenuConfigImageFlashCardActionPerformed

        DlgConfigImageFlashCard dlg = null;
        JFrame mainFrame = FlashCardApp.getApplication().getMainFrame();
        dlg = new DlgConfigImageFlashCard(FlashCardApp.getApplication().getMainFrame(), true, imageFlashCard);
        dlg.setLocationRelativeTo(mainFrame);
        FlashCardApp.getApplication().show(dlg);
        dlg.dispose();

        textFlashCard.loadFlashCardKeys();
        cbFlashCard.setModel(new javax.swing.DefaultComboBoxModel(textFlashCard.getFlashCardNames().toArray()));
        String flashCardId = (String) cbFlashCard.getSelectedItem();
        textFlashCard.loadFlashCard(flashCardId);
        ckImageFlashCard.setSelected(false);
    }//GEN-LAST:event_jmenuConfigImageFlashCardActionPerformed

    private void ckImageFlashCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckImageFlashCardActionPerformed
        JCheckBox ckImgFlashCard = (JCheckBox) evt.getSource();
        if (ckImgFlashCard.isSelected()) {
            imageFlashCard.loadFlashCardKeys();
            cbFlashCard.setModel(new javax.swing.DefaultComboBoxModel(imageFlashCard.getFlashCardNames().toArray()));
            String flashCardId = (String) cbFlashCard.getSelectedItem();
            String imageFolder = imageFlashCard.getFlashcardFolder(flashCardId);
            gradeId = imageFolder;
            ImageUtil.clearAll();
            imageCount = ImageUtil.preLoad(imageFolder);
            System.out.println(gradeId + " images available: " + imageCount);
            endIdx = imageCount - 1;
            if (noWordsPerTest >= imageCount) {
                noWordsPerTest = imageCount;
            }
            SpinnerNumberModel spinWordsModel = new SpinnerNumberModel(noWordsPerTest, 0, imageCount, 1);
            this.spinWordsPerTest.setModel(spinWordsModel);
        } else {
            textFlashCard.loadFlashCardKeys();
            cbFlashCard.setModel(new javax.swing.DefaultComboBoxModel(textFlashCard.getFlashCardNames().toArray()));
            String flashCardId = (String) cbFlashCard.getSelectedItem();
            textFlashCard.loadFlashCard(flashCardId);
            
            int wordsAvailable = textFlashCard.getFlashCard().length;
            System.out.println(flashCardId + " words available: " + wordsAvailable);
            if (noWordsPerTest >= wordsAvailable) {
                noWordsPerTest = wordsAvailable;
            }
            SpinnerNumberModel spinWordsModel = new SpinnerNumberModel(noWordsPerTest, 0, wordsAvailable, 1);
            this.spinWordsPerTest.setModel(spinWordsModel);            
        }
    }//GEN-LAST:event_ckImageFlashCardActionPerformed

    private void ckRandomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckRandomActionPerformed
        JCheckBox jckRandom = (JCheckBox) evt.getSource();
        Boolean selected = jckRandom.isSelected();
    }//GEN-LAST:event_ckRandomActionPerformed

    public void showNextCharacter() {
        noWordsPerTest = ((Integer)spinWordsPerTest.getValue()).intValue();
        numberOfWordTested++;
        Boolean randomMode = ckRandom.isSelected();
        if (numberOfWordTested > noWordsPerTest) {
            numberOfWordTested = 0;
            this.btnStartStop.doClick();
        }
        else {
            if (ckImageFlashCard.isSelected()) {
                ImageIcon icon = null;
                if (randomMode) {
                    while (true) {
                        int nextInt = randomGR.nextInt();                
                        List<ImageIcon> imageList = ImageUtil.getImageIcon(gradeId);
                        if ((nextInt >= 1) && (nextInt <= imageList.size())) {
                            icon = imageList.get(nextInt - 1);
                            break;
                        }
                    }
                } else {
                    int nextInt = numberOfWordTested;                
                    List<ImageIcon> imageList = ImageUtil.getImageIcon(gradeId);
                    icon = imageList.get(nextInt - 1);
                }
                lblTestCount.setText(String.valueOf(numberOfWordTested));                
                btnImage.setIcon(icon);                
            }
            else {
                String character = null;
                if (randomMode) {
                    int nextInt = randomGR.nextInt();
                    character = textFlashCard.getFlashCard()[nextInt];
                    if (AppState.isVerbose())
                        System.out.print(" " + character + " ");
                } else {
                    int nextInt = numberOfWordTested;                
                    character = textFlashCard.getFlashCard()[nextInt -1];
                }
                lblTestCount.setText(String.valueOf(numberOfWordTested));                
                lblDisplay.setText(character);
                txtDisplay.setText(character);
                lblDisplay.setVisible(false);
                txtDisplay.setVisible(false);
                if (this.ckContrast.isSelected()) {
                    txtDisplay.setVisible(true);
                } else {
                    lblDisplay.setVisible(true);
                }
            }
        }
    }
    
    @Action
    public void startOrStop() {
        double displayedInSeconds = ((Double)spinDisplayInterval.getValue());
        noWordsPerTest = ((Integer)spinWordsPerTest.getValue()).intValue();

        int wordsAvailable = textFlashCard.getFlashCard().length;
        if (noWordsPerTest >= wordsAvailable) {
            noWordsPerTest = wordsAvailable;
            spinWordsPerTest.setValue(wordsAvailable);
        }
        if (wordsAvailable == 0)
            return;

        if (this.btnStartStop.getText().equals("Start")) { // Start
            setDisplayControl();
            randomGR.setMaxValue(textFlashCard.getTestCount());
            randomGR.setNumberOfWordsPerTest(numberOfWordTested);
            randomGR.resetTestCount();
            lblCountDown.setText(String.valueOf(displayedInSeconds));
            stopWatch = new StopWatch(0.1d, displayedInSeconds); // interval: 1 seconds
            stopWatch.start();
            this.lblDisplay.setVisible(false);
            this.btnImage.setVisible(false);
            this.txtDisplay.setVisible(false);
            if (this.ckImageFlashCard.isSelected()) {
                this.btnImage.setVisible(true);
            } else {
                this.lblDisplay.setVisible(true);
                this.txtDisplay.setVisible(true);
            }            
            showNextCharacter();
            this.btnStartStop.setText("Stop");
        } else { // Stop
            setDisplayControlIdleSize();
            numberOfWordTested = 0;
            stopWatch.stop();
            this.btnStartStop.setText("Start");
            this.btnImage.setVisible(false);
            this.txtDisplay.setVisible(false);
            this.lblDisplay.setVisible(true);
            this.lblDisplay.setText("Ready");
            this.txtDisplay.setText("Ready");
            lblCountDown.setText(String.valueOf(displayedInSeconds));
            lblTestCount.setText("0");
        }
    }

    class StopWatch implements ActionListener {
        double countStartMaster = 10;
        double countStart = 10;
        double dInterval = 0.1d;
        Timer t = null;
      /**
       * 
       * @param interval interval in seconds
       */
      public StopWatch(double dInterval, double countStart) {
            this.countStartMaster = countStart;
            this.countStart = countStart;
            int interval = (int)(dInterval * 1000);
            t = new Timer(interval, this);
      }

      public void start() {
          if (t != null)
              t.start();
      }
      
      public void stop() {
          if (t != null)
              t.stop();          
      }
      
      public String toString(double fVal, int fractionDigits) {
        if (Double.isNaN(fVal))
            return "";
        String sVal = String.valueOf(fVal);
        int idx = sVal.indexOf(".");
        int endIdx = Math.min(idx + fractionDigits + 1, sVal.length());
        if (fractionDigits == 0)
            endIdx--;
        sVal = sVal.substring(0, endIdx);
        return sVal;
      }
      public void actionPerformed(ActionEvent ae) {
            countStart -= dInterval;
            lblCountDown.setText(toString(countStart, 1));
            if (countStart <= 0) {
                countStart = countStartMaster;
                lblCountDown.setText(toString(countStart, 1));
                showNextCharacter();
            }
        }
    }    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnImage;
    private javax.swing.JButton btnStartStop;
    private javax.swing.JComboBox cbFlashCard;
    private javax.swing.JComboBox cbFont;
    private javax.swing.JComboBox cbFontSize;
    private javax.swing.JCheckBox ckContrast;
    private javax.swing.JCheckBox ckImageFlashCard;
    private javax.swing.JCheckBox ckRandom;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenuItem jMenuItemConfig;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JMenuItem jmenuConfigImageFlashCard;
    private javax.swing.JLabel lblCountDown;
    private javax.swing.JLabel lblDisplay;
    private javax.swing.JLabel lblTestCount;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JSpinner spinDisplayInterval;
    private javax.swing.JSpinner spinWordsPerTest;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JMenu toolsMenu;
    private javax.swing.JTextField txtDisplay;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
}
