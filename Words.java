


import com.sun.media.jfxmedia.AudioClip;
import com.sun.media.jfxmedia.MediaPlayer;
import javafx.scene.media.Media;
import oracle.jrockit.jfr.JFR;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.swing.SwingUtilities2;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

/**
 * Created by leixd on 17-5-22.
 */
public class Words {
    static long lastProc;               //上一次的发音时间，临时解决重复发音问题
    public static void showImportWordsDialog()
    {



        ArrayList<String[]> wordList=new ArrayList<String[]>();
        DefaultTableModel defaultTableModel=new DefaultTableModel(0,2);
        JFrame jFrame=new JFrame("添加生词并背诵");
        JPanel jPanelup=new JPanel();
        jPanelup.add(new Label("单词："));
        JTextArea jTextArea=new JTextArea();
        JTextArea jTextArea2=new JTextArea();
        JButton jButton=new JButton("添加");
        JButton jButton1=new JButton("保存单词文件");
        JButton jButton2=new JButton("导入单词文件");
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fileDialog=new FileDialog(jFrame);
                fileDialog.setMode(FileDialog.SAVE);
                fileDialog.setVisible(true);
                File file=new File(fileDialog.getDirectory()+fileDialog.getFile());
                try {
                    ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(file));
                    oos.writeObject(wordList);
                    JOptionPane.showMessageDialog(null,"保存成功");
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null,"保存失败");
                } catch (IOException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null,"保存失败");
                }

            }
        });

        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fileDialog=new FileDialog(jFrame);
                fileDialog.setMode(FileDialog.LOAD);
                fileDialog.setVisible(true);
                File file=new File(fileDialog.getDirectory()+fileDialog.getFile());
                try {
                    ObjectInputStream oos=new ObjectInputStream(new FileInputStream(file));
                    ArrayList <String[]> t=(ArrayList<String[]>)oos.readObject();
               //     JOptionPane.showMessageDialog(null,"读取成功");
                    for (String []t2:t)
                    {
                        defaultTableModel.addRow(t2);
                        wordList.add(t2);
                    }

                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null,"读取失败");
                } catch (IOException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null,"读取失败");
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });
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
        jPanelup.add(jButton1);jPanelup.add(jButton2);
        jFrame.add(jPanelup,BorderLayout.NORTH);


       String [][]te=new String[][]{
               new String[]{"单词１","解释１"},
               new String[]{"单词２","解释２"},
               new String[]{"单词３","解释３"}
       };
      String[] title=new String[]{"单词","意思"};

        JTable jTable=new JTable(te,title);



       jButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {

               String []te=new String[]{jTextArea.getText(),jTextArea2.getText()};
               defaultTableModel.addRow(te);
               //fayin
               wordList.add(te);

               char firstChar=jTextArea.getText().charAt(0);
               try {
                   Runtime.getRuntime().exec("cvlc --play-and-exit "+Main.TTSLocation+File.separator+firstChar+File.separator+jTextArea.getText().replace(" ","").replace("\n","")+".wav");
                       } catch (IOException e1) {
                   e1.printStackTrace();
               }
               jTextArea2.setText("");
               jTextArea.setText("");
               jTextArea.grabFocus();
           }
       });


        jTable.setModel(defaultTableModel);
        jTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (System.currentTimeMillis()-lastProc<200) return;
                lastProc=System.currentTimeMillis();

                int row = jTable.getSelectedRow();
                String word=wordList.get(row)[0];
                try {
                    char firstChar=word.charAt(0);
                    Runtime.getRuntime().exec("cvlc --play-and-exit "+Main.TTSLocation+File.separator+firstChar+File.separator+word.replace(" ","").replace("\n","")+".wav");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        jFrame.add(new JScrollPane(jTable));


        jFrame.pack();

        jFrame.setVisible(true);

    }
}
