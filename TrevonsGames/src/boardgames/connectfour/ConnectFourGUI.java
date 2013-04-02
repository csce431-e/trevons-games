/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.connectfour;

import java.util.ArrayList;
import javax.swing.JButton;
import java.awt.Color;

/**
 *
 * @author Jonathan
 */
public class ConnectFourGUI extends javax.swing.JFrame {
    /**
     * Creates new customizer ConnectFourGUI
     */
    
//    ConnectFourGame cfg;
//    int state;
//    int column;
//    int rowHeight;
//    boolean gameIsOver;
//    boolean disableButtons;
//    ArrayList<JButton> columns;
    
    public ConnectFourGUI() {
        initComponents();
//        columns = new ArrayList<>();
//        
//        cfg = new ConnectFourGame();
//        
//        columns.add(col1);
//        columns.add(col2);
//        columns.add(col3);
//        columns.add(col4);
//        columns.add(col5);
//        columns.add(col6);
//        columns.add(col7);
//        
//        state = 0;
//        gameIsOver = false;
//        disableButtons = false;
    }
    
    
    private void updateBoard(int column) {
//        rowHeight = cfg.b.rowHeight(column);
//        int color = cfg.b.getColor(rowHeight, column);
//        
//        switch(column) {
//            case 1:
//                if(rowHeight == 1) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r1c1.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r1c1.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 2) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r2c1.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r2c1.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 3) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r3c1.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r3c1.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 4) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r4c1.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r4c1.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 5) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r5c1.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r5c1.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 6) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r6c1.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r6c1.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                break;
//            case 2:
//                if(rowHeight == 1) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r1c2.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r1c2.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 2) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r2c2.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r2c2.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 3) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r3c2.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r3c2.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 4) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r4c2.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r4c2.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 5) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r5c2.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r5c2.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 6) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r6c2.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r6c2.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                break;
//            case 3:
//                if(rowHeight == 1) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r1c3.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r1c3.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 2) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r2c3.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r2c3.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 3) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r3c3.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r3c3.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 4) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r4c3.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r4c3.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 5) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r5c3.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r5c3.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 6) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r6c3.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r6c3.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                break;
//            case 4:
//                if(rowHeight == 1) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r1c4.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r1c4.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 2) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r2c4.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r2c4.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 3) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r3c4.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r3c4.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 4) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r4c4.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r4c4.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 5) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r5c4.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r5c4.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 6) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r6c4.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r6c4.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                break;
//            case 5:
//                if(rowHeight == 1) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r1c5.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r1c5.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 2) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r2c5.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r2c5.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 3) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r3c5.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r3c5.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 4) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r4c5.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r4c5.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 5) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r5c5.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r5c5.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 6) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r6c5.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r6c5.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                break;
//            case 6:
//                if(rowHeight == 1) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r1c6.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r1c6.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 2) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r2c6.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r2c6.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 3) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r3c6.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r3c6.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 4) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r4c6.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r4c6.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 5) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r5c6.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r5c6.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 6) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r6c6.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r6c6.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                break;
//            case 7:
//            if(rowHeight == 1) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r1c7.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r1c7.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 2) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r2c7.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r2c7.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 3) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r3c7.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r3c7.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 4) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r4c7.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r4c7.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 5) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r5c7.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r5c7.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                if(rowHeight == 6) {
//                    switch(color) {
//                        case 1:
//                            break;
//                        case 2:
//                            r6c7.setBackground(Color.BLACK);
//                            break;
//                        case 3:
//                            r6c7.setBackground(Color.RED);
//                            break;
//                    }
//                }
//                break;
//        }
        
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the FormEditor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        col1 = new javax.swing.JButton();
        col2 = new javax.swing.JButton();
        col3 = new javax.swing.JButton();
        col4 = new javax.swing.JButton();
        col5 = new javax.swing.JButton();
        col6 = new javax.swing.JButton();
        col7 = new javax.swing.JButton();
        r1c1 = new javax.swing.JButton();
        r2c1 = new javax.swing.JButton();
        r1c2 = new javax.swing.JButton();
        r1c3 = new javax.swing.JButton();
        r1c4 = new javax.swing.JButton();
        r1c5 = new javax.swing.JButton();
        r1c6 = new javax.swing.JButton();
        r1c7 = new javax.swing.JButton();
        r3c1 = new javax.swing.JButton();
        r4c1 = new javax.swing.JButton();
        r5c1 = new javax.swing.JButton();
        r6c1 = new javax.swing.JButton();
        r6c2 = new javax.swing.JButton();
        r5c2 = new javax.swing.JButton();
        r4c2 = new javax.swing.JButton();
        r3c2 = new javax.swing.JButton();
        r2c2 = new javax.swing.JButton();
        r6c3 = new javax.swing.JButton();
        r6c4 = new javax.swing.JButton();
        r6c5 = new javax.swing.JButton();
        r6c6 = new javax.swing.JButton();
        r6c7 = new javax.swing.JButton();
        r5c3 = new javax.swing.JButton();
        r5c4 = new javax.swing.JButton();
        r5c5 = new javax.swing.JButton();
        r5c6 = new javax.swing.JButton();
        r5c7 = new javax.swing.JButton();
        r4c3 = new javax.swing.JButton();
        r4c4 = new javax.swing.JButton();
        r4c5 = new javax.swing.JButton();
        r4c6 = new javax.swing.JButton();
        r4c7 = new javax.swing.JButton();
        r3c3 = new javax.swing.JButton();
        r3c4 = new javax.swing.JButton();
        r3c5 = new javax.swing.JButton();
        r3c6 = new javax.swing.JButton();
        r3c7 = new javax.swing.JButton();
        r2c3 = new javax.swing.JButton();
        r2c4 = new javax.swing.JButton();
        r2c5 = new javax.swing.JButton();
        r2c6 = new javax.swing.JButton();
        r2c7 = new javax.swing.JButton();

        col1.setText("1");
        col1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                col1ActionPerformed(evt);
            }
        });

        col2.setText("2");
        col2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                col2ActionPerformed(evt);
            }
        });

        col3.setText("3");
        col3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                col3ActionPerformed(evt);
            }
        });

        col4.setText("4");
        col4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                col4ActionPerformed(evt);
            }
        });

        col5.setText("5");
        col5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                col5ActionPerformed(evt);
            }
        });

        col6.setText("6");
        col6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                col6ActionPerformed(evt);
            }
        });

        col7.setText("7");
        col7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                col7ActionPerformed(evt);
            }
        });

        r1c1.setText("row1column1");
        r1c1.setEnabled(false);

        r2c1.setText("row2column1");
        r2c1.setEnabled(false);

        r1c2.setText("row1column2");
        r1c2.setEnabled(false);

        r1c3.setText("row1column3");
        r1c3.setEnabled(false);

        r1c4.setText("row1column4");
        r1c4.setEnabled(false);

        r1c5.setText("row1column5");
        r1c5.setEnabled(false);

        r1c6.setText("row1column6");
        r1c6.setEnabled(false);

        r1c7.setText("row1column7");
        r1c7.setToolTipText("");
        r1c7.setEnabled(false);

        r3c1.setText("row3column1");
        r3c1.setEnabled(false);

        r4c1.setText("row4column1");
        r4c1.setEnabled(false);

        r5c1.setText("row5column1");
        r5c1.setEnabled(false);

        r6c1.setText("row6column1");
        r6c1.setToolTipText("");
        r6c1.setEnabled(false);

        r6c2.setText("row6column2");
        r6c2.setEnabled(false);

        r5c2.setText("row5column2");
        r5c2.setEnabled(false);

        r4c2.setText("row4column2");
        r4c2.setEnabled(false);

        r3c2.setText("row3column2");
        r3c2.setEnabled(false);

        r2c2.setText("row2column2");
        r2c2.setEnabled(false);

        r6c3.setText("row6column3");
        r6c3.setEnabled(false);

        r6c4.setText("row6column4");
        r6c4.setEnabled(false);

        r6c5.setText("row6column5");
        r6c5.setEnabled(false);

        r6c6.setText("row6column6");
        r6c6.setEnabled(false);

        r6c7.setText("row6column7");
        r6c7.setEnabled(false);

        r5c3.setText("row5column3");
        r5c3.setEnabled(false);

        r5c4.setText("row5column4");
        r5c4.setEnabled(false);

        r5c5.setText("row5column5");
        r5c5.setEnabled(false);

        r5c6.setText("row5column6");
        r5c6.setToolTipText("");
        r5c6.setEnabled(false);

        r5c7.setText("row5column7");
        r5c7.setEnabled(false);

        r4c3.setText("row4column3");
        r4c3.setEnabled(false);

        r4c4.setText("row4column4");
        r4c4.setEnabled(false);

        r4c5.setText("row4column5");
        r4c5.setEnabled(false);

        r4c6.setText("row4column6");
        r4c6.setEnabled(false);

        r4c7.setText("row4column7");
        r4c7.setEnabled(false);

        r3c3.setText("row3column3");
        r3c3.setEnabled(false);

        r3c4.setText("row3column4");
        r3c4.setEnabled(false);

        r3c5.setText("row3column5");
        r3c5.setEnabled(false);

        r3c6.setText("row3column6");
        r3c6.setEnabled(false);

        r3c7.setText("row3column7");
        r3c7.setEnabled(false);

        r2c3.setText("row2column3");
        r2c3.setEnabled(false);

        r2c4.setText("row2column4");
        r2c4.setEnabled(false);

        r2c5.setText("row2column5");
        r2c5.setEnabled(false);

        r2c6.setText("row2column6");
        r2c6.setEnabled(false);

        r2c7.setText("row2column7");
        r2c7.setToolTipText("");
        r2c7.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(r6c1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r5c1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r4c1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r3c1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r2c1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r1c1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(col1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(col2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(r1c2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r6c2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r5c2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r4c2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r3c2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r2c2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(col3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(r1c3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r6c3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r5c3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r4c3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r3c3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r2c3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(col4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(r1c4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r6c4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r5c4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r4c4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r3c4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r2c4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(col5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(r1c5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r6c5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r5c5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r4c5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r3c5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r2c5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(col6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(r1c6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r6c6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r5c6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r4c6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r3c6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r2c6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(col7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(r1c7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r6c7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r5c7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r4c7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r3c7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(r2c7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(r6c7, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(r6c6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r6c5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r6c4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r6c3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r6c2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r6c1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(r5c7, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(r5c6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r5c5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r5c4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r5c3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r5c2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r5c1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(r4c7, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(r4c6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r4c5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r4c4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r4c3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r4c2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r4c1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(r3c7, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(r3c6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r3c5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r3c4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r3c3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r3c2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r3c1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(r2c1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(r2c2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r2c3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r2c4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r2c5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r2c6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r2c7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(r1c1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(r1c2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r1c3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r1c4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r1c5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r1c6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r1c7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(col1)
                    .addComponent(col2)
                    .addComponent(col3)
                    .addComponent(col4)
                    .addComponent(col5)
                    .addComponent(col6)
                    .addComponent(col7))
                .addGap(25, 25, 25))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void col1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_col1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_col1ActionPerformed

    private void col2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_col2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_col2ActionPerformed

    private void col3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_col3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_col3ActionPerformed

    private void col4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_col4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_col4ActionPerformed

    private void col5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_col5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_col5ActionPerformed

    private void col6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_col6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_col6ActionPerformed

    private void col7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_col7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_col7ActionPerformed

    private void jButtonActionPerformed(java.awt.event.ActionEvent evt) {
//        if(!gameIsOver && !disableButtons) {
//            JButton clicked = (JButton)evt.getSource();
//            
//            if(state == 0) {
//                // Do stuff
//                if (clicked == col1) {
//                    // Make move in column 1
//                    column = 1;
//                    int turn = cfg.b.getTurn();
//                    cfg.b.move(turn, column);
//                    updateBoard(column);
//                }
//                if (clicked == col2) {
//                    // Make move in column 2
//                    column = 2;
//                    int turn = cfg.b.getTurn();
//                    cfg.b.move(turn, column);
//                    updateBoard(column);
//                }
//                if (clicked == col3) {
//                    // Make move in column 3
//                    column = 3;
//                    int turn = cfg.b.getTurn();
//                    cfg.b.move(turn, column);
//                    updateBoard(column);
//                }
//                if (clicked == col4) {
//                    // Make move in column 4
//                    column = 4;
//                    int turn = cfg.b.getTurn();
//                    cfg.b.move(turn, column);
//                    updateBoard(column);
//                }
//                if (clicked == col5) {
//                    // Make move in column 5
//                    column = 5;
//                    int turn = cfg.b.getTurn();
//                    cfg.b.move(turn, column);
//                    updateBoard(column);
//                }
//                if (clicked == col6) {
//                    // Make move in column 6
//                    column = 6;
//                    int turn = cfg.b.getTurn();
//                    cfg.b.move(turn, column);
//                    updateBoard(column);
//                }
//                if (clicked == col7) {
//                    // Make move in column 7
//                    column = 7;
//                    int turn = cfg.b.getTurn();
//                    cfg.b.move(turn, column);
//                    updateBoard(column);
//                }
//            }
//        }
//        
    }

    public void playConnect() {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() 
            {
                new ConnectFourGUI().setVisible(true);               
            }
        }); 
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton col1;
    private javax.swing.JButton col2;
    private javax.swing.JButton col3;
    private javax.swing.JButton col4;
    private javax.swing.JButton col5;
    private javax.swing.JButton col6;
    private javax.swing.JButton col7;
    private javax.swing.JButton r1c1;
    private javax.swing.JButton r1c2;
    private javax.swing.JButton r1c3;
    private javax.swing.JButton r1c4;
    private javax.swing.JButton r1c5;
    private javax.swing.JButton r1c6;
    private javax.swing.JButton r1c7;
    private javax.swing.JButton r2c1;
    private javax.swing.JButton r2c2;
    private javax.swing.JButton r2c3;
    private javax.swing.JButton r2c4;
    private javax.swing.JButton r2c5;
    private javax.swing.JButton r2c6;
    private javax.swing.JButton r2c7;
    private javax.swing.JButton r3c1;
    private javax.swing.JButton r3c2;
    private javax.swing.JButton r3c3;
    private javax.swing.JButton r3c4;
    private javax.swing.JButton r3c5;
    private javax.swing.JButton r3c6;
    private javax.swing.JButton r3c7;
    private javax.swing.JButton r4c1;
    private javax.swing.JButton r4c2;
    private javax.swing.JButton r4c3;
    private javax.swing.JButton r4c4;
    private javax.swing.JButton r4c5;
    private javax.swing.JButton r4c6;
    private javax.swing.JButton r4c7;
    private javax.swing.JButton r5c1;
    private javax.swing.JButton r5c2;
    private javax.swing.JButton r5c3;
    private javax.swing.JButton r5c4;
    private javax.swing.JButton r5c5;
    private javax.swing.JButton r5c6;
    private javax.swing.JButton r5c7;
    private javax.swing.JButton r6c1;
    private javax.swing.JButton r6c2;
    private javax.swing.JButton r6c3;
    private javax.swing.JButton r6c4;
    private javax.swing.JButton r6c5;
    private javax.swing.JButton r6c6;
    private javax.swing.JButton r6c7;
    // End of variables declaration//GEN-END:variables
}
