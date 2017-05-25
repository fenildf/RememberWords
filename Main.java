

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Main {
    public static String TTSLocation="/usr/share/WyabdcRealPeopleTTS" ;                     //发音库的位置
    public static String WordsFileLocation="/home/leixd/Rememberwords/";                    //默认单词文件存储位置
    public static void main(String[] args) {
	// write your code here

        File file=new File(WordsFileLocation);
        if (!file.exists()) file.mkdir();

        JFrame jFrameMain=new JFrame("Remember Words");
        JPanel jPanel=new JPanel();
        JButton jButton1=new JButton("添加生词并背诵");
        JButton jButton2=new JButton("使用说明");
        JButton jButton3=new JButton("关于");
        jPanel.add(jButton1);jPanel.add(jButton2);jPanel.add(jButton3);
        jFrameMain.add(jPanel);
        jFrameMain.pack();
        jFrameMain.setVisible(true);
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Words.showImportWordsDialog();
            }
        });
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"敬请期待");
            }
        });
        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"作者：Thunder Se\n制作时间：2017年5月22日\n版本：0.4\n此程序完全开源且免费，符合GNU规范。\n发音库需要去http://reciteword.sourceforge.net/ 网站下载，并按照网站要求放到/usr/share中相应目录");
            }
        });
    }
}
