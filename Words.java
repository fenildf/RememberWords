

import oracle.jrockit.jfr.JFR;
import sun.swing.SwingUtilities2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Stack;
import java.util.Vector;

/**
 * Created by leixd on 17-5-22.
 */
public class Words {
    public static void showImportWordsDialog()
    {
        JFrame jFrame=new JFrame("添加生词并背诵");
        JPanel jPanelup=new JPanel();
        jPanelup.add(new Label("单词："));
        JTextArea jTextArea=new JTextArea();
        JTextArea jTextArea2=new JTextArea();
        JButton jButton=new JButton("添加");
        jTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode()==KeyEvent.VK_ENTER)
                    jTextArea2.grabFocus();
            }

        });
        jTextArea2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode()==KeyEvent.VK_ENTER)
                    jButton.doClick();
            }

        });

        jTextArea.setColumns(30);
        jPanelup.add(jTextArea);
        jPanelup.add(new Label("解释："));

        jTextArea2.setColumns(30);
        jPanelup.add(jTextArea2);

        jPanelup.add(jButton);
        jFrame.add(jPanelup,BorderLayout.NORTH);


       String [][]te=new String[][]{
               new String[]{"单词１","解释１"},
               new String[]{"单词２","解释２"},
               new String[]{"单词３","解释３"}
       };
      String[] title=new String[]{"单词","意思"};

        JTable jTable=new JTable(te,title);

        DefaultTableModel defaultTableModel=new DefaultTableModel(0,2);

       jButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {

               String []te=new String[]{jTextArea.getText(),jTextArea2.getText()};
               defaultTableModel.addRow(te);
               //fayin
               char firstChar=jTextArea.getText().charAt(0);
               try {
                   Runtime.getRuntime().exec("aplay "+Main.TTSLocation+File.separator+firstChar+File.separator+jTextArea.getText().substring(0,jTextArea.getText().length()-1)+".wav");

               } catch (IOException e1) {
                   e1.printStackTrace();
               }
               jTextArea2.setText("");
               jTextArea.setText("");
               jTextArea.grabFocus();
           }
       });


        jTable.setModel(defaultTableModel);
        jFrame.add(new JScrollPane(jTable));


        jFrame.pack();

        jFrame.setVisible(true);

    }
}
