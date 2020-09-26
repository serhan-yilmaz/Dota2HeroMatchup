/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dota2heromatchup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Serhan
 */
public class GUI {
    public static final String VERSION = "v1.17c";
    
    protected JFrame f;
    
    private boolean ready = false;
    
    private JProgressBar progress_bar;
    private DotabuffParser parser;
    private JLabel status_label;
    private JTabbedPane middle_pane;
    private HeroSelectionPanel tab1_top_pane;
    private HeroSelectionPanel tab1_middle_vs_pane;
    private HeroCounterPanel tab2_middle_pane;
    
    private final JPanel top_east_pane = new JPanel();
    private final JPanel progress_bar_wrapper = new JPanel();
    
    private final JLabel progress_bar_label2 = new JLabel(" ");
    private final JLabel top_east_label2 = new JLabel(" ");
    
    JLabel tab1_middle_spane1_label = new JLabel("Name : ");
    JLabel tab1_middle_spane2_label = new JLabel("Win Rate : ");
    JLabel tab1_middle_spane3_label = new JLabel("Pick Rate : ");
    JLabel tab1_middle_spane4_label = new JLabel("KDA : ");
    
    JLabel tab3_middle_spane1_label = new JLabel("Font Size : ");
    
    private final JLabel tab1_middle_spane1_label2 = new JLabel(" ");
    private final JLabel tab1_middle_spane2_label2 = new JLabel(" ");
    private final JLabel tab1_middle_spane3_label2 = new JLabel(" ");
    private final JLabel tab1_middle_spane4_label2 = new JLabel(" ");
    
    JLabel tab1_middle2_spane1_label = new JLabel("Matchup : ");
    JLabel tab1_middle2_spane2_label = new JLabel("Advantage : ");
    JLabel tab1_middle2_spane3_label = new JLabel("Matchup Win Rate : ");
    JLabel tab1_middle2_spane4_label = new JLabel("Expected Win Rate : ");
    JLabel tab1_middle2_spane5_label = new JLabel("Matches Played : ");
    
    private final JLabel tab1_middle2_spane1_label2 = new JLabel(" ");
    private final JLabel tab1_middle2_spane2_label2 = new JLabel(" ");
    private final JLabel tab1_middle2_spane3_label2 = new JLabel(" ");
    private final JLabel tab1_middle2_spane4_label2 = new JLabel(" ");
    private final JLabel tab1_middle2_spane5_label2 = new JLabel(" ");
    
    String[] font_size_strings = { "Very Small (13)", "Small (16)", "Medium (19)", "Large (22)", "Very Large (25)" };
    Integer[] font_size_list = {13, 16, 19, 22, 25};
    final JComboBox fontSizeList = new JComboBox(font_size_strings);
    
    private final JButton button1 = new JButton("Web Parse");
    private final JButton button2 = new JButton("File Parse");  
    
    JLabel bottom_label = new JLabel("Status : ");
    JLabel tab1_middle_label = new JLabel("Versus");
    JLabel progress_bar_label = new JLabel("Remaining Time : ");
    JLabel top_east_label = new JLabel("Data acquired at : ");
    
    protected MatchupData data;
    private boolean ready2start = false;
    private boolean dataIsReady = false;
    
    private final ArrayList<JLabel> filter_label1 = new ArrayList<>();
    private final ArrayList<JLabel> filter_label2 = new ArrayList<>();
    
    protected void createAndShowGUI(){
        final GUI gui = this;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                gui.cycle();
            }
        };
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 0, 20);
        
        f = new JFrame("Dota 2 Hero Matchup " + VERSION);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ImageIcon icon = new ImageIcon(this.getClass().getResource("/images/logo3.jpg"));
        f.setIconImage(icon.getImage());
        
        JPanel main_pane = new JPanel();
        main_pane.setLayout(new BorderLayout());
        
        JPanel top_pane = new JPanel();
        top_pane.setLayout(new BorderLayout());
        JPanel top_west_pane = new JPanel();
        top_east_pane.setVisible(false);
        JPanel bottom_pane = new JPanel();
        bottom_pane.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel tab1 = new JPanel();
        tab1.setLayout(new BorderLayout());
        JPanel tab2 = new JPanel();
        
        tab1_top_pane = new HeroSelectionPanel();
        tab1_middle_vs_pane = new HeroSelectionPanel();
        tab2_middle_pane = new HeroCounterPanel();
        
        JPanel tab1_middle_pane_wrapper = new JPanel();
        tab1_middle_pane_wrapper.setLayout(new BoxLayout(tab1_middle_pane_wrapper, BoxLayout.PAGE_AXIS));
        tab1.setBorder(new LineBorder(Color.black));
        
        JPanel tab1_middle_pane = new JPanel();
        tab1_middle_pane.setLayout(new BoxLayout(tab1_middle_pane, BoxLayout.PAGE_AXIS));
        tab1_middle_pane.setBorder(new LineBorder(Color.lightGray));
        
        JPanel tab1_middle_spane1 = new JPanel();
        JPanel tab1_middle_spane2 = new JPanel();
        JPanel tab1_middle_spane3 = new JPanel();
        JPanel tab1_middle_spane4 = new JPanel();
        
        tab1_middle_spane1.setLayout(new GridLayout(1,0));
        tab1_middle_spane2.setLayout(new GridLayout(1,0));
        tab1_middle_spane3.setLayout(new GridLayout(1,0));
        tab1_middle_spane4.setLayout(new GridLayout(1,0));
        
        tab1_middle_spane1.add(tab1_middle_spane1_label);
        tab1_middle_spane1.add(tab1_middle_spane1_label2);
        tab1_middle_spane2.add(tab1_middle_spane2_label);
        tab1_middle_spane2.add(tab1_middle_spane2_label2);
        tab1_middle_spane3.add(tab1_middle_spane3_label);
        tab1_middle_spane3.add(tab1_middle_spane3_label2);
        tab1_middle_spane4.add(tab1_middle_spane4_label);
        tab1_middle_spane4.add(tab1_middle_spane4_label2);
        
        JPanel tab1_middle_label_wrapper = new JPanel();
        tab1_middle_label_wrapper.add(tab1_middle_label);
        
        tab1_middle_pane.add(tab1_top_pane);
        tab1_middle_pane.add(tab1_middle_spane1);
        tab1_middle_pane.add(tab1_middle_spane2);
        tab1_middle_pane.add(tab1_middle_spane3);
        tab1_middle_pane.add(tab1_middle_spane4);
        
        for(HeroFilterPanel fp : tab2_middle_pane.filter_selector_list_all){
            JPanel sub_pane = new JPanel();
            sub_pane.setLayout(new GridLayout(1,0));
            JLabel sub_label1 = new JLabel(fp.name + " : ");
            JLabel sub_label2 = new JLabel(" ");
            filter_label1.add(sub_label1);
            filter_label2.add(sub_label2);
            sub_pane.add(sub_label1);
            sub_pane.add(sub_label2);
            tab1_middle_pane.add(sub_pane);
        }
        
        JPanel tab1_middle2_pane = new JPanel();
        tab1_middle2_pane.setLayout(new GridLayout(0,1));
        tab1_middle2_pane.setBorder(new LineBorder(Color.lightGray));
        
        JPanel tab1_middle2_spane1 = new JPanel();
        JPanel tab1_middle2_spane2 = new JPanel();
        JPanel tab1_middle2_spane3 = new JPanel();
        JPanel tab1_middle2_spane4 = new JPanel();
        JPanel tab1_middle2_spane5 = new JPanel();
        
        tab1_middle2_spane1.setLayout(new GridLayout(1,0));
        tab1_middle2_spane2.setLayout(new GridLayout(1,0));
        tab1_middle2_spane3.setLayout(new GridLayout(1,0));
        tab1_middle2_spane4.setLayout(new GridLayout(1,0));
        tab1_middle2_spane5.setLayout(new GridLayout(1,0));
        
        tab1_middle2_spane1.add(tab1_middle2_spane1_label);
        tab1_middle2_spane1.add(tab1_middle2_spane1_label2);
        tab1_middle2_spane2.add(tab1_middle2_spane2_label);
        tab1_middle2_spane2.add(tab1_middle2_spane2_label2);
        tab1_middle2_spane3.add(tab1_middle2_spane3_label);
        tab1_middle2_spane3.add(tab1_middle2_spane3_label2);
        tab1_middle2_spane4.add(tab1_middle2_spane4_label);
        tab1_middle2_spane4.add(tab1_middle2_spane4_label2);
        tab1_middle2_spane5.add(tab1_middle2_spane5_label);
        tab1_middle2_spane5.add(tab1_middle2_spane5_label2);
        
        tab1_middle2_pane.add(tab1_middle2_spane1);
        tab1_middle2_pane.add(tab1_middle2_spane2);
        tab1_middle2_pane.add(tab1_middle2_spane3);
        tab1_middle2_pane.add(tab1_middle2_spane4);
        tab1_middle2_pane.add(tab1_middle2_spane5);
        
        tab1_middle_pane_wrapper.add(tab1_middle_pane);
        tab1_middle_pane_wrapper.add(tab1_middle_label_wrapper);
        tab1_middle_pane_wrapper.add(tab1_middle_vs_pane);
        tab1_middle_pane_wrapper.add(tab1_middle2_pane);
        
        JPanel tab3 = new JPanel();        
        tab3.setLayout(new BorderLayout());
        JPanel tab3_middle_pane = new JPanel();
        tab3_middle_pane.setLayout(new BoxLayout(tab3_middle_pane, BoxLayout.PAGE_AXIS));
        tab3_middle_pane.setBorder(new LineBorder(Color.lightGray));
        
        JPanel tab3_middle_spane1 = new JPanel();
        tab3_middle_spane1.setLayout(new GridLayout(1, 0));
        tab3_middle_spane1.add(tab3_middle_spane1_label);
//        tab3_middle_spane1.add(new JLabel("xyz"));
        fontSizeList.setSelectedIndex(3);
        fontSizeList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = fontSizeList.getSelectedIndex();
                gui.setFontSize(font_size_list[index]);
            }
        });
        
        
        tab3_middle_spane1.add(fontSizeList);
        
        tab3_middle_pane.add(tab3_middle_spane1);        
        tab3.add(tab3_middle_pane, BorderLayout.NORTH);
        
        middle_pane = new JTabbedPane();
        middle_pane.addTab("Hero Details", tab1);
        middle_pane.addTab("Hero Matchups", tab2);
        middle_pane.addTab("Options", tab3);
        
        status_label = new JLabel("");
        
        progress_bar = new JProgressBar(0, 100);
        progress_bar.setValue(0);
        progress_bar.setStringPainted(true);
        
        progress_bar_wrapper.add(progress_bar);
        progress_bar_wrapper.add(progress_bar_label);
        progress_bar_wrapper.add(progress_bar_label2);
        
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parseWeb();
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readFileUser();
            }
        });
        
        tab2.add(tab2_middle_pane);
        
        tab1.add(tab1_middle_pane_wrapper,BorderLayout.NORTH);
        
        top_east_pane.add(top_east_label);
        top_east_pane.add(top_east_label2);
        
        top_west_pane.add(button1);
        top_west_pane.add(button2);
        
        top_pane.add(top_west_pane,BorderLayout.WEST);
        top_pane.add(top_east_pane,BorderLayout.EAST);
        
        bottom_pane.add(bottom_label);
        bottom_pane.add(status_label);
        bottom_pane.add(progress_bar_wrapper);
        
        main_pane.add(top_pane,BorderLayout.NORTH);
        main_pane.add(bottom_pane,BorderLayout.SOUTH);
        main_pane.add(middle_pane,BorderLayout.CENTER);
        
        setFontSize(22);
        
        f.add(main_pane);
        
        f.setSize(800,800);
        f.setResizable(false);
        f.setUndecorated(false);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.pack();
        f.setLocationRelativeTo(null);
        setReady(false);
    }
    
    protected void setVisible(){
        f.setVisible(true);
        setReady(true);
        ready2start = true;
        readFileAuto();
    }
    
    private void setReady(boolean bo){
        progress_bar_wrapper.setVisible(!bo);
        if(bo){
            ready = true;
            if(dataIsReady){
                status_label.setText("Ready");
                status_label.setForeground(new Color(35,180,35));
            } else {
                status_label.setText("No Data");
                status_label.setForeground(Color.red);
            }
            button1.setEnabled(true);
            button2.setEnabled(true);
        } else {
            ready = false;
            status_label.setText("Parsing Data...");
            status_label.setForeground(Color.red);
            button1.setEnabled(false);
            button2.setEnabled(false);
        }
    }
    
    protected void parseWeb(){
        if(!ready)
            return;
        setReady(false);
        Runnable a = new Runnable() {
            @Override
            public void run() {
                try {
                    parser = new DotabuffParser();
                    parser.parse();
                    activate(parser.getMatchupData());
                    write2File();
                } catch (IOException ex) {
                    System.err.println("An error has occurred : ");
                    System.err.println(ex.getLocalizedMessage());
                } catch (ParsingException ex) {
                    System.err.println("An error has occurred : ");
                    System.err.println(ex.getLocalizedMessage());
                }
                finally{
                    setReady(true);
                }
            }
        };
        
        new Thread(a).start();
    }
    
    private void readFileAuto(){
        if(dataIsReady)
            return;
        setReady(false);
        
        try {
            FileInputStream fileIn = new FileInputStream("matchup_data.dmd");
            readFile(fileIn);
        } catch (FileNotFoundException ex) {
            System.err.println("File not found : matchup_data.dmd");
        }
        
        setReady(true);
    }
    
    private void readFileUser(){
        if(!ready)
            return;
        setReady(false);
        final JFileChooser fc = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        fc.setCurrentDirectory(workingDirectory);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Dota 2 Matchup Data (.dmd)", "dmd", "Dota2MatchupData");
        fc.setFileFilter(filter);
        int returnVal = fc.showOpenDialog(f);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            if(file != null){
                try {
                    FileInputStream fileIn = new FileInputStream(file);
                    readFile(fileIn);
                } catch (FileNotFoundException ex) {
                    System.err.println("File not found : " + file.getName());
                }
            }
        }
        setReady(true);
    }
    private void readFile(FileInputStream fileIn){
        MatchupData data = null;
            try
            {
               ObjectInputStream in = new ObjectInputStream(fileIn);
               data = (MatchupData) in.readObject();
               in.close();
               fileIn.close();
            }catch(IOException i){
               System.err.println("Specified File is Invalid.");
            }catch(ClassNotFoundException c){
               c.printStackTrace();
            }
            if(data != null){
                activate(data);
                this.data.printHeroes();
            }
    }
    private void write2File(){
      try
      {
         FileOutputStream fileOut = new FileOutputStream("matchup_data.dmd");
         ObjectOutputStream out = new ObjectOutputStream(fileOut);
         out.writeObject(data);
         out.close();
         fileOut.close();
         System.out.printf("Serialized data is saved in matchup_data.dmd\n");
      }catch(IOException i){
          i.printStackTrace();
      }
    }
    
    public void cycle(){
        if(!ready2start)
            return;
        if(parser != null && !ready){
            NumberFormat formatter = new DecimalFormat("#0.0",DecimalFormatSymbols.getInstance(Locale.US));
            progress_bar.setValue((int) parser.progress);
            progress_bar_label2.setText(formatter.format(parser.getEstimatedTime()) + " s");
        }
        middle_pane.setEnabled(dataIsReady);
        tab1_top_pane.cycle();
        tab1_middle_vs_pane.cycle();
        tab2_middle_pane.cycle();
        updateHeroDetails();
        f.pack();
    }
    
    protected void updateHeroDetails(){
        NumberFormat formatter1 = new DecimalFormat("#0.00;-#0.00", 
                DecimalFormatSymbols.getInstance(Locale.US));
        NumberFormat formatter2 = new DecimalFormat("+#0.00;-#0.00", 
                DecimalFormatSymbols.getInstance(Locale.US));
        Hero h = tab1_top_pane.getSelectedHero();
        Hero h2 = tab1_middle_vs_pane.getSelectedHero();
        if(h != null){
            String winrateText = formatter1.format(h.win_rate);
            String pickrateText = formatter1.format(h.pick_rate);
            String kdaText = formatter1.format(h.kda);
            tab1_middle_spane1_label2.setText(h.name);
            tab1_middle_spane2_label2.setText(winrateText + "%");
            tab1_middle_spane3_label2.setText(pickrateText + "%");
            tab1_middle_spane4_label2.setText(kdaText + " ");
            for(int i = 0; i < tab2_middle_pane.filter_selector_list_all.size(); i++){
                HeroFilterPanel fp = tab2_middle_pane.filter_selector_list_all.get(i);
                String s = "";
                ArrayList<String> fn = fp.getValidFilterNames(h);
                if(fn.isEmpty()){
                    s = "None";
                } else {
                    for(int i2 = 0; i2 < fn.size(); i2++){
                        s = s + fn.get(i2);
                        if(i2 != fn.size() - 1 )
                            s = s + ", ";
                    }
                }
                filter_label2.get(i).setText(s);
            }
            if(h2 != null){
                tab1_middle2_spane1_label2.setText(h.name + " vs " + h2.name);
                double actualWR = data.win_rate[h.id][h2.id];
//                double expectedWR = h.win_rate * 
//                        (1 - (h2.win_rate * 1e-2)) / 0.5;
                double case1 = h.win_rate * (100 - h2.win_rate);
                double case2 = (100 - h.win_rate) * h2.win_rate;
//                double case1N = h.win_rate * (100 - h2.win_rate);
                double expectedWR = 100 * case1 / (case1 + case2);
                if(h.id == h2.id){
                    actualWR = 50;
                    expectedWR = 50;
                }
                double advantage = HeroCounter.computeAdvantage(actualWR, expectedWR);
//                double advantage = (actualWR - expectedWR) / 0.5;
//                double advantage = 100 * (actualWR / expectedWR -1);
                long nMatch = (long)data.match_amount[h.id][h2.id];
                String advantageText = formatter2.format(advantage);
                String actualWRText = formatter1.format(actualWR);
                String expectedWRText = formatter1.format(expectedWR);
                tab1_middle2_spane2_label2.setText(advantageText + "%");
                tab1_middle2_spane3_label2.setText(actualWRText + "%");
                tab1_middle2_spane4_label2.setText(expectedWRText + "%");
                tab1_middle2_spane5_label2.setText(nMatch + " ");
            }
        }
    }
    
    protected void activate(MatchupData data){
        tab1_top_pane.activate(data);
        tab1_middle_vs_pane.activate(data);
        tab2_middle_pane.activate(data);
        this.data = data;
        this.dataIsReady = true;
        this.top_east_label2.setText(data.getDate());
        this.top_east_pane.setVisible(true);
    }
    
    protected void setFontSize(float fontsize){
        Font font = tab1_middle_spane1_label2.getFont();
        Font newFont = font.deriveFont(fontsize);
        Font font2 = tab1_middle_label.getFont();
        Font newFont2 = font2.deriveFont((float) (fontsize * 1.5));
        
        tab1_middle_spane1_label.setFont(newFont);
        tab1_middle_spane2_label.setFont(newFont);
        tab1_middle_spane3_label.setFont(newFont);
        tab1_middle_spane4_label.setFont(newFont);
        
        tab3_middle_spane1_label.setFont(newFont);
        fontSizeList.setFont(newFont);
        
        tab1_middle_spane1_label2.setFont(newFont);
        tab1_middle_spane2_label2.setFont(newFont);
        tab1_middle_spane3_label2.setFont(newFont);
        tab1_middle_spane4_label2.setFont(newFont);
        
        tab1_middle2_spane1_label.setFont(newFont);
        tab1_middle2_spane2_label.setFont(newFont);
        tab1_middle2_spane3_label.setFont(newFont);
        tab1_middle2_spane4_label.setFont(newFont);
        tab1_middle2_spane5_label.setFont(newFont);
        
        tab1_middle2_spane1_label2.setFont(newFont);
        tab1_middle2_spane2_label2.setFont(newFont);
        tab1_middle2_spane3_label2.setFont(newFont);
        tab1_middle2_spane4_label2.setFont(newFont);
        tab1_middle2_spane5_label2.setFont(newFont);
        
        for(JLabel label: filter_label1){
            label.setFont(newFont);
        }
        
        for(JLabel label: filter_label2){
            label.setFont(newFont);
        }
        
        tab1_middle_label.setFont(newFont2);
        
        bottom_label.setFont(newFont);
        status_label.setFont(newFont);
        
        progress_bar_label.setFont(newFont);
        top_east_label.setFont(newFont);
        
        progress_bar_label2.setFont(newFont);
        top_east_label2.setFont(newFont);
        
        tab1_top_pane.setFontSize(fontsize);
        tab1_middle_vs_pane.setFontSize(fontsize);
        tab2_middle_pane.setFontSize(fontsize);
        
        middle_pane.setFont(newFont);
        
        button1.setFont(newFont);
        button2.setFont(newFont);
    }
    
    
}
