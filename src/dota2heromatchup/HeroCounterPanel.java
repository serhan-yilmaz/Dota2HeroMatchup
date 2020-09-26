/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dota2heromatchup;

import static dota2heromatchup.HeroCounterComparator.COUNTER_ADVANTAGE;
import static dota2heromatchup.HeroCounterComparator.COUNTER_GENERALWR;
import static dota2heromatchup.HeroCounterComparator.COUNTER_NAME;
import static dota2heromatchup.HeroCounterComparator.COUNTER_PICKRATE;
import static dota2heromatchup.HeroCounterComparator.COUNTER_WINRATE;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Serhan
 */
public class HeroCounterPanel extends JPanel{
    public final ArrayList<HeroFilterPanel> filter_selector_list_all = new ArrayList<>();
    public final ArrayList<HeroFilterPanel> filter_selector_list_combobox = new ArrayList<>();
    public final ArrayList<HeroFilterPanel> filter_selector_list_checkbox = new ArrayList<>();
    
    
    private final ArrayList<HeroFilter> filter_list1 = new ArrayList<>();
    private final HeroFilterPanel filter_panel1;
    
    private final ArrayList<HeroFilter> filter_list2 = new ArrayList<>();
    private final HeroFilterPanel filter_panel2;
    
    private final ArrayList<HeroFilter> filter_list3 = new ArrayList<>();
    private final HeroFilterPanel filter_panel3;
    
    private final ArrayList<HeroFilter> filter_list4 = new ArrayList<>();
    private final HeroFilterPanel filter_panel4;
    
    private final ArrayList<HeroFilter> filter_list5 = new ArrayList<>();
    private final HeroFilterPanel filter_panel5;

    private final ArrayList<HeroFilter> filter_list6 = new ArrayList<>();
    private final HeroFilterPanel filter_panel6;
    
    private final ArrayList<HeroFilter> filter_list7 = new ArrayList<>();
    private final HeroFilterPanel filter_panel7;
    
    private final HeroFilter filter_all = new AllpassFilter();
    
    private final ArrayList<String> support_list = new ArrayList<>();
    private final HeroFilter filter_support;
    private final ArrayList<String> carry_list = new ArrayList<>();
    private final HeroFilter filter_carry;
    private final ArrayList<String> initiator_list = new ArrayList<>();
    private final HeroFilter filter_initiator;
    private final ArrayList<String> pusher_list = new ArrayList<>();
    private final HeroFilter filter_pusher;
    private final ArrayList<String> ganker_list = new ArrayList<>();
    private final HeroFilter filter_ganker;
    private final ArrayList<String> healer_list = new ArrayList<>();
    private final HeroFilter filter_healer;
    
    private final ArrayList<String> lane_middle_list = new ArrayList<>();
    private final HeroFilter filter_middle;
    private final ArrayList<String> lane_offlane_list = new ArrayList<>();
    private final HeroFilter filter_offlane;
    private final ArrayList<String> lane_safe_list = new ArrayList<>();
    private final HeroFilter filter_safe;
    private final ArrayList<String> lane_jungle_list = new ArrayList<>();
    private final HeroFilter filter_jungle;
    private final ArrayList<String> lane_roam_list = new ArrayList<>();
    private final HeroFilter filter_roam;
    
    private final ArrayList<String> ranged_list = new ArrayList<>();
    private final HeroFilter filter_ranged;
    private final ArrayList<String> melee_list = new ArrayList<>();
    private final HeroFilter filter_melee;
    
    private final ArrayList<String> stun_list = new ArrayList<>();
    private final HeroFilter filter_stun;
    private final ArrayList<String> ensare_list = new ArrayList<>();
    private final HeroFilter filter_ensare;
    private final ArrayList<String> slow_list = new ArrayList<>();
    private final HeroFilter filter_slow;
    private final ArrayList<String> silence_list = new ArrayList<>();
    private final HeroFilter filter_silence;
    private final ArrayList<String> disable_misc_list = new ArrayList<>();
    private final HeroFilter filter_disable_misc;
    
    private final ArrayList<String> disable_reliable_list = new ArrayList<>();
    private final HeroFilter filter_disable_reliable;
    private final ArrayList<String> disable_universal_list = new ArrayList<>();
    private final HeroFilter filter_disable_universal;
    private final ArrayList<String> disable_aoe_list = new ArrayList<>();
    private final HeroFilter filter_disable_aoe;
    
    private final ArrayList<String> damage_physical_list = new ArrayList<>();
    private final HeroFilter filter_damage_physical;
    private final ArrayList<String> damage_magical_list = new ArrayList<>();
    private final HeroFilter filter_damage_magical;
    private final ArrayList<String> damage_pure_list = new ArrayList<>();
    private final HeroFilter filter_damage_pure;
    
    private final ArrayList<String> extra_global_list = new ArrayList<>();
    private final HeroFilter filter_extra_global;
    private final ArrayList<String> extra_mobile_list = new ArrayList<>();
    private final HeroFilter filter_extra_mobile;
    private final ArrayList<String> extra_invisible_list = new ArrayList<>();
    private final HeroFilter filter_extra_invisible;
    private final ArrayList<String> extra_vision_list = new ArrayList<>();
    private final HeroFilter filter_extra_vision;
    
    private final HeroFilterPanel filter_panel_role_pair;
    private final HeroFilterPanel filter_panel_lane_pair;
    private final HeroFilterPanel filter_panel_range_pair;
    
    private HeroSelectionPanel select_enemy_hero;
    private HeroCounterSelectPanel[] selected_enemy_heroes;
    
    private HeroSelectionPanel select_ally_hero;
    private HeroCounterSelectPanel[] selected_ally_heroes;
    private JLabel ally_team_advantage = new JLabel("Ally Team Advantage: 0.0%");
    private JLabel ally_team_winrate = new JLabel(", Ally Team Winrate: 0.0%");
    private JLabel enemy_team_advantage = new JLabel("Enemy Team Advantage: 0.0%");
    private JLabel enemy_team_winrate = new JLabel(", Enemy Team Winrate: 0.0%");
    
    
//    private final JTextArea bottom_textarea = new JTextArea();
    private final JTextPane bottom_textarea = new JTextPane();
    private final HeroCounterTable bottom_table = new HeroCounterTable();
    private final JScrollPane bottom_scroll 
            = new JScrollPane(bottom_table,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    private final HeroCounterDataModel dtm 
            = new HeroCounterDataModel(0, 0);
//    private final DefaultTableModel dtm 
//            = new DefaultTableModel(0, 0);
    private final JTabbedPane tabbed_pane = new JTabbedPane();
    
    JLabel filter_label = new JLabel("Filters : ");
    JButton filter_button;
    JLabel counter_label = new JLabel("Counter Picks : ");
    JLabel counter_sort_label = new JLabel("Sorting : ");
    JLabel counter_filter_label = new JLabel("Filters : ");
    JComboBox counter_sort_box;
    
    JLabel counter_color_label = new JLabel("Coloring : ");
    JComboBox counter_color_box;
    
    private MatchupData data;
    
    private int selected_hero_amount = 0;
    private int last_sorting = -1;
    private int last_coloring = -1;
    
    public HeroCounterPanel(){
        super();
        createHeroFilters();
        String[] sort_options = 
            {"Name", "Advantage", "Win Rate", "General WR", "Pick Rate"};
        counter_sort_box = new JComboBox(sort_options);
        counter_sort_box.setSelectedIndex(2);
        
        String[] color_options = {"None", "Advantage", "Win Rate",  
                "General WR", "Pick Rate"};
        counter_color_box = new JComboBox(color_options);
        counter_color_box.setSelectedIndex(1);
        
        filter_support = new BasicFilter("Support", support_list);
        filter_carry = new BasicFilter("Carry", carry_list);
        filter_initiator = new BasicFilter("Initiator", initiator_list);
        filter_pusher = new BasicFilter("Pusher", pusher_list);
        filter_ganker = new BasicFilter("Ganker", ganker_list);
        filter_healer = new BasicFilter("Healer", healer_list);
        
        filter_middle = new BasicFilter("Middle", lane_middle_list);
        filter_offlane = new BasicFilter("Offlane", lane_offlane_list);
        filter_safe = new BasicFilter("Safe", lane_safe_list);
        filter_jungle = new BasicFilter("Jungle", lane_jungle_list);
        filter_roam = new BasicFilter("Roam", lane_roam_list);
        
        filter_ranged = new BasicFilter("Ranged", ranged_list);
        filter_melee = new BasicFilter("Melee", melee_list);
        
        filter_stun = new BasicFilter("Stun", stun_list);
        filter_ensare = new BasicFilter("Root", ensare_list);
        filter_slow = new BasicFilter("Slow", slow_list);
        filter_silence = new BasicFilter("Silence", silence_list);
        filter_disable_misc = new BasicFilter("Misc", disable_misc_list);
        
        filter_disable_reliable = new BasicFilter("Reliable", disable_reliable_list);
        filter_disable_universal = new BasicFilter("Universal", disable_universal_list);
        filter_disable_aoe = new BasicFilter("AOE", disable_aoe_list);
        
        filter_damage_physical = new BasicFilter("Physical", damage_physical_list);
        filter_damage_magical = new BasicFilter("Magical", damage_magical_list);
        filter_damage_pure = new BasicFilter("Pure", damage_pure_list);
        
        filter_extra_global = new BasicFilter("Global", extra_global_list);
        filter_extra_mobile = new BasicFilter("Mobile", extra_mobile_list);
        filter_extra_invisible = new BasicFilter("Invisible", extra_invisible_list);
        filter_extra_vision = new BasicFilter("Vision", extra_vision_list);
        
        createHeroFilterTooltip();
        createHeroFilterSelectors();
        
        filter_panel1 = new ComboboxHeroFilterPanel("Role", filter_list1);
        filter_panel7 = new ComboboxHeroFilterPanel("Lane", filter_list7);
        filter_panel2 = new ComboboxHeroFilterPanel("Range", filter_list2);
//        filter_panel2 = new CheckboxHeroFilterPanel("Range", filter_list2, true);
        filter_panel3 = new CheckboxHeroFilterPanel("Disable", filter_list3, true);
        filter_panel4 = new CheckboxHeroFilterPanel("DisableProperty", filter_list4, true);
        filter_panel5 = new CheckboxHeroFilterPanel("Damage", filter_list5, true);
        filter_panel6 = new CheckboxHeroFilterPanel("Extra", filter_list6, true);
        
        filter_panel_role_pair = filter_panel1.createSynchronizedPair();
        filter_panel_lane_pair = filter_panel7.createSynchronizedPair();
        filter_panel_range_pair = filter_panel2.createSynchronizedPair();
        
        filter_selector_list_combobox.add(filter_panel1);
        filter_selector_list_combobox.add(filter_panel7);
        filter_selector_list_combobox.add(filter_panel2);
        
        filter_selector_list_checkbox.add(filter_panel3);
        filter_selector_list_checkbox.add(filter_panel4);
        filter_selector_list_checkbox.add(filter_panel5);
        filter_selector_list_checkbox.add(filter_panel6);
        
        filter_selector_list_all.addAll(filter_selector_list_combobox);
        filter_selector_list_all.addAll(filter_selector_list_checkbox);
        
        initialize();
    }
    
    private void createOutputFileFilters() throws FileNotFoundException, IOException{
        OutputStreamWriter writer = new OutputStreamWriter(
                            new FileOutputStream("Hero_Attributes.txt"));
        BufferedWriter bufWriter = new BufferedWriter(writer);
        for(Hero h: data.hero_list){
            bufWriter.write(h.name + ";");
            boolean initial = true;
            for(HeroFilterPanel hfp: filter_selector_list_all){
                for(HeroFilter hf: hfp.getFilters()){
                    if(hf.getName().compareTo("All") == 0)
                        continue;
                    if(hf.filter(h)){
                        if(!initial){
                            bufWriter.write(", ");
                        } else {
                            initial = false;
                        }
                        bufWriter.write(hfp.name + "_" + hf.getName() + "");
                    }
                }
            }
            bufWriter.newLine();
        }
        bufWriter.close();
    }
    
    private void createHeroFilterTooltip(){
       filter_disable_misc.setTooltip("Unclassified disables.");
       filter_disable_misc.setTooltip("Other forms of disable e.g. taunt, disarm, trapping etc.");
        
       filter_disable_reliable.setTooltip("Disables that last longer time or that are easier to cast.");
       filter_disable_universal.setTooltip("Disables which bypass spell-immunity.");
       filter_disable_aoe.setTooltip("Disables that affect an area.");
       
       filter_extra_global.setTooltip("Heroes that have a spell with global range.");
       filter_extra_mobile.setTooltip("Heroes that have a spell which provides mobility e.g. blink, sprint etc.");
       filter_extra_invisible.setTooltip("Heroes that have a spell which provides invisibility.");
//     filter_extra_invisible.setTooltip("Heroes that have a spell which requires the enemy team to buy invisible detection.");
       filter_extra_vision.setTooltip("Heroes that have a spell which provides vision.");
       
    }
    
    private void createHeroFilterSelectors(){
        filter_list1.add(filter_all);
        filter_list1.add(filter_carry);
        filter_list1.add(filter_support);
        filter_list1.add(filter_initiator);
        filter_list1.add(filter_pusher);
        filter_list1.add(filter_ganker);
        filter_list1.add(filter_healer);
        
        filter_list2.add(filter_all);
        filter_list2.add(filter_melee);
        filter_list2.add(filter_ranged);
        
        filter_list3.add(filter_stun);
        filter_list3.add(filter_ensare);
        filter_list3.add(filter_slow);
        filter_list3.add(filter_silence);
        filter_list3.add(filter_disable_misc);
        
        filter_list4.add(filter_disable_reliable);
        filter_list4.add(filter_disable_universal);
        filter_list4.add(filter_disable_aoe);
        
        filter_list5.add(filter_damage_physical);
        filter_list5.add(filter_damage_magical);
        filter_list5.add(filter_damage_pure);
        
        filter_list6.add(filter_extra_global);
        filter_list6.add(filter_extra_mobile);
        filter_list6.add(filter_extra_invisible);
        filter_list6.add(filter_extra_vision);
        
        filter_list7.add(filter_all);
        filter_list7.add(filter_middle);
        filter_list7.add(filter_offlane);
        filter_list7.add(filter_safe);
        filter_list7.add(filter_jungle);
        filter_list7.add(filter_roam);
        
    }
    private void initialize(){
        
        this.setLayout(new BorderLayout());
        
        select_enemy_hero = new HeroSelectionPanel();
        JPanel enemy_selection = new JPanel();
        enemy_selection.setLayout(new GridLayout(0,1));
        
        selected_enemy_heroes = new HeroCounterSelectPanel[5];
        for(int i = 0; i < 5; i++){
            selected_enemy_heroes[i] = new HeroCounterSelectPanel(i + 1);
            selected_enemy_heroes[i].setBorder(new LineBorder(Color.lightGray));
            enemy_selection.add(selected_enemy_heroes[i]);
        }
        JPanel enemy_team_stats_panel = new JPanel();
        enemy_team_stats_panel.add(enemy_team_advantage);
        enemy_team_stats_panel.add(enemy_team_winrate);
        
        JPanel enemy_hero_selection_panel = new JPanel();
        enemy_hero_selection_panel.setLayout(new BorderLayout());
        enemy_hero_selection_panel.add(select_enemy_hero,BorderLayout.NORTH);
        enemy_hero_selection_panel.add(enemy_selection,BorderLayout.CENTER);
        enemy_hero_selection_panel.add(enemy_team_stats_panel, BorderLayout.SOUTH);
        
        select_ally_hero = new HeroSelectionPanel();
        JPanel ally_selection = new JPanel();
        ally_selection.setLayout(new GridLayout(0,1));
        
        selected_ally_heroes = new HeroCounterSelectPanel[5];
        for(int i = 0; i < 5; i++){
            selected_ally_heroes[i] = new HeroCounterSelectPanel(i + 1);
            selected_ally_heroes[i].setBorder(new LineBorder(Color.lightGray));
            ally_selection.add(selected_ally_heroes[i]);
        }
        
        JPanel ally_team_stats_panel = new JPanel();
        ally_team_stats_panel.add(ally_team_advantage);
        ally_team_stats_panel.add(ally_team_winrate);
        
        JPanel ally_hero_selection_panel = new JPanel();
        ally_hero_selection_panel.setLayout(new BorderLayout());
        ally_hero_selection_panel.add(select_ally_hero,BorderLayout.NORTH);
        ally_hero_selection_panel.add(ally_selection,BorderLayout.CENTER);
        ally_hero_selection_panel.add(ally_team_stats_panel, BorderLayout.SOUTH);
        
//        bottom_textarea.setRows(18);
        bottom_textarea.setEditable(false);
        bottom_textarea.setFont(new Font(Font.MONOSPACED,Font.PLAIN,11));
        bottom_table.setFont(new Font(Font.MONOSPACED,Font.PLAIN,11));
        
        JPanel bottom_panel = new JPanel();
        bottom_panel.setLayout(new BorderLayout());
        
        JPanel filter_main_panel = new JPanel();
        filter_main_panel.setLayout(new BoxLayout(filter_main_panel,BoxLayout.PAGE_AXIS));
        filter_main_panel.setBorder(new LineBorder(Color.gray));
        
        JPanel filter_combobox_panel = new JPanel();
        filter_combobox_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        for(HeroFilterPanel fp : filter_selector_list_combobox){
            filter_combobox_panel.add(fp);
        }
        
        filter_main_panel.add(filter_combobox_panel);
        filter_main_panel.add(Box.createHorizontalGlue());
        for(HeroFilterPanel fp : filter_selector_list_checkbox){
            filter_main_panel.add(fp);
            filter_main_panel.add(Box.createHorizontalGlue());
        }
        
        JPanel filter_complete_panel = new JPanel();
        filter_complete_panel.setLayout(new BorderLayout());
        
        Font font1 = new Font(Font.SERIF, Font.BOLD, 16);
        
        filter_button = new JButton("Clear All");
        
        filter_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearAllFilters();
            }
        });
        
        filter_label.setFont(font1);
        JPanel filter_label_panel = new JPanel();
        filter_label_panel.setLayout(new BorderLayout());
        
        filter_label_panel.add(filter_label, BorderLayout.WEST);
        filter_label_panel.add(filter_button, BorderLayout.EAST);
        
        JPanel filter_center_panel = new JPanel();
        filter_center_panel.add(filter_main_panel);
        
        filter_complete_panel.add(filter_label_panel, BorderLayout.NORTH);
        filter_complete_panel.add(filter_center_panel,BorderLayout.CENTER);
        
        JPanel bottom_south_panel = new JPanel();
        bottom_south_panel.setLayout(new BorderLayout());
        
        JPanel counter_top_panel = new JPanel();
        counter_top_panel.setLayout(new BorderLayout());
        
        JPanel counter_sort_panel = new JPanel();
        counter_sort_panel.add(counter_sort_label);
        counter_sort_panel.add(counter_sort_box);
        
        JPanel counter_color_panel = new JPanel();
        counter_color_panel.add(counter_color_label);
        counter_color_panel.add(counter_color_box);
        
        JPanel counter_option_panel = new JPanel();
        counter_option_panel.add(counter_color_panel);
        counter_option_panel.add(counter_sort_panel);
        
        counter_label.setFont(font1);
        
        counter_top_panel.add(counter_label, BorderLayout.WEST);
        counter_top_panel.add(counter_option_panel, BorderLayout.EAST);
        
        JPanel counter_bottom_panel = new JPanel();
        counter_bottom_panel.setLayout(new BorderLayout());
        JPanel counter_filters_panel = new JPanel();
        counter_filters_panel.add(filter_panel_role_pair);
        counter_filters_panel.add(filter_panel_lane_pair);
        counter_filters_panel.add(filter_panel_range_pair);
        
        counter_filter_label.setFont(font1);
        
        counter_bottom_panel.add(counter_filter_label, BorderLayout.WEST);
        counter_bottom_panel.add(counter_filters_panel, BorderLayout.CENTER);
        
        bottom_south_panel.add(counter_top_panel,BorderLayout.NORTH);
        bottom_south_panel.add(bottom_scroll, BorderLayout.CENTER);
        bottom_south_panel.add(counter_bottom_panel,BorderLayout.SOUTH);
        
//        JPanel bottom_south_info_panel = new JPanel();
//        bottom_south_info_panel.setLayout(new GridLayout(1,1));
//        bottom_south_info_panel.add(bottom_scroll);
        
        
        bottom_panel.add(filter_complete_panel,BorderLayout.NORTH);
        bottom_panel.add(bottom_south_panel,BorderLayout.SOUTH);
        
        tabbed_pane.addTab("Ally Heroes", ally_hero_selection_panel);
        tabbed_pane.addTab("Enemy Heroes", enemy_hero_selection_panel);
        tabbed_pane.addTab("Filters", filter_complete_panel);
        tabbed_pane.addTab("Counter Picks", bottom_south_panel);
        
        this.add(tabbed_pane);
    }
    
    public void activate(MatchupData data){
        select_ally_hero.activate(data);
        select_enemy_hero.activate(data);
        this.data = data;
        
//        try{
//            createOutputFileFilters();
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(HeroCounterPanel.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(HeroCounterPanel.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
    }
    
    public void cycle(){
        select_ally_hero.cycle();
        select_enemy_hero.cycle();
        Hero ally_hero = select_ally_hero.getSelectedHero();
        Hero enemy_hero = select_enemy_hero.getSelectedHero();
        boolean select_enemy_action_request = select_enemy_hero.isActionRequested();
        boolean select_ally_action_request = select_ally_hero.isActionRequested();
        boolean changed = false;
        if(ally_hero == null){
            for(int i = 0; i < selected_ally_heroes.length; i++){
                selected_ally_heroes[i].setSelectionEnabled(false);
            }
        } else {
            boolean f = false;
            boolean f2 = false;
            for(int i = 0; i < selected_enemy_heroes.length; i++){
                Hero h2 = selected_enemy_heroes[i].getSelectedHero();
                if(h2 != null && h2.equals(ally_hero))
                    f = true;
            }
            for(int i = 0; i < selected_ally_heroes.length; i++){
                if(selected_ally_heroes[i].isSelectionRequested()){
                    if(!f){
                        selected_ally_heroes[i].selectHero(ally_hero);
                        select_ally_hero.clearSelection();
                        changed = true;
                    }
                }
                if(select_ally_action_request){
                    if(selected_ally_heroes[i].getSelectedHero() == null && !f && !f2){
                        select_ally_action_request = false;
                        selected_ally_heroes[i].selectHero(ally_hero);
                        select_ally_hero.clearSelection();
                        changed = true;
                    }
                }
                Hero h2 = selected_ally_heroes[i].getSelectedHero();
                if(h2 != null && h2.equals(ally_hero))
                    f = true;
                boolean d1 = h2 == null;
                if(d1 && !f && !f2){
                    selected_ally_heroes[i].setSelectionEnabled(true);
                } else {
                    selected_ally_heroes[i].setSelectionEnabled(false);
                }
                if(h2 == null)
                    f2 = true;
            }
        }
        if(enemy_hero == null){
            for(int i = 0; i < selected_enemy_heroes.length; i++){
                selected_enemy_heroes[i].setSelectionEnabled(false);
            }
        } else {
            boolean f = false;
            boolean f2 = false;
            for(int i = 0; i < selected_ally_heroes.length; i++){
                Hero h2 = selected_ally_heroes[i].getSelectedHero();
                if(h2 != null && h2.equals(enemy_hero))
                    f = true;
            }
            for(int i = 0; i < selected_enemy_heroes.length; i++){
                if(selected_enemy_heroes[i].isSelectionRequested()){
                    if(!f){
                        selected_enemy_heroes[i].selectHero(enemy_hero);
                        select_enemy_hero.clearSelection();
                        changed = true;
                    }
                }
                if(select_enemy_action_request){
                    if(selected_enemy_heroes[i].getSelectedHero() == null && !f && !f2){
                        select_enemy_action_request = false;
                        selected_enemy_heroes[i].selectHero(enemy_hero);
                        select_enemy_hero.clearSelection();
                        changed = true;
                    }
                }
                Hero h2 = selected_enemy_heroes[i].getSelectedHero();
                if(h2 != null && h2.equals(enemy_hero))
                    f = true;
                boolean d1 = h2 == null;
                if(d1 && !f && !f2){
                    selected_enemy_heroes[i].setSelectionEnabled(true);
                } else {
                    selected_enemy_heroes[i].setSelectionEnabled(false);
                }
                if(h2 == null)
                    f2 = true;
            }
        }
        boolean f = false;
        for(int i = 0; i < selected_ally_heroes.length; i++){
            Hero h2 = selected_ally_heroes[i].getSelectedHero();
            if(!f){
                if(h2 != null && selected_ally_heroes[i].isDeletionRequested()){
                   selected_ally_heroes[i].deactivate();
                   f = true;
                   changed = true;
                }
            }
            if(f){
                if(i != selected_ally_heroes.length - 1){
                    Hero h3 = selected_ally_heroes[i + 1].getSelectedHero();
                    if(h3 != null)
                        selected_ally_heroes[i].selectHero(h3);
                    else 
                        selected_ally_heroes[i].deactivate();
                } else {
                    selected_ally_heroes[i].deactivate();
                }
            }
        }
        f = false;
        for(int i = 0; i < selected_enemy_heroes.length; i++){
            Hero h2 = selected_enemy_heroes[i].getSelectedHero();
            if(!f){
                if(h2 != null && selected_enemy_heroes[i].isDeletionRequested()){
                   selected_enemy_heroes[i].deactivate();
                   f = true;
                   changed = true;
                }
            }
            if(f){
                if(i != selected_enemy_heroes.length - 1){
                    Hero h3 = selected_enemy_heroes[i + 1].getSelectedHero();
                    if(h3 != null)
                        selected_enemy_heroes[i].selectHero(h3);
                    else 
                        selected_enemy_heroes[i].deactivate();
                } else {
                    selected_enemy_heroes[i].deactivate();
                }
            }
        }
        for(HeroFilterPanel fp : filter_selector_list_all){
            if(fp.isModified()){
                changed = true;
            }
        }
        
        if(last_sorting != counter_sort_box.getSelectedIndex()){
            changed = true;
        }
        
        if(last_coloring != counter_color_box.getSelectedIndex()){
            changed = true;
        }
        
        if(changed){
            calculateCounters();
        }
        int selected_hero_amount = 0;
        for(int i = 0; i < selected_enemy_heroes.length; i++){
            Hero h2 = selected_enemy_heroes[i].getSelectedHero();
            if(h2 == null){
                break;
            } else {
                selected_hero_amount++;
            }
        }
        int selected_ally_hero_amount = 0;
        for(int i = 0; i < selected_ally_heroes.length; i++){
            Hero h2 = selected_ally_heroes[i].getSelectedHero();
            if(h2 == null){
                break;
            } else {
                selected_ally_hero_amount++;
            }
        }
//        tabbed_pane.setEnabledAt(1, selected_hero_amount != 0);
//        tabbed_pane.setEnabledAt(2, selected_hero_amount != 0);
        tabbed_pane.setEnabledAt(1, true);
        tabbed_pane.setEnabledAt(2, true);
        tabbed_pane.setEnabledAt(3, selected_ally_hero_amount != 5);
    }
    
    private void calculateCounters(){
        if(data == null)
            return;
//        if(selected_heroes[0].getSelectedHero() == null)
//            return;
        ArrayList<Hero> enemy_heroes = new ArrayList<>();
        for(int i = 0; i < selected_enemy_heroes.length; i++){
            Hero h = selected_enemy_heroes[i].getSelectedHero();
            if(h != null){
                enemy_heroes.add(h);
            } else {
                break;
            }
        }
        
        ArrayList<Hero> ally_heroes = new ArrayList<>();
        for(int i = 0; i < selected_ally_heroes.length; i++){
            Hero h = selected_ally_heroes[i].getSelectedHero();
            if(h != null){
                ally_heroes.add(h);
            } else {
                break;
            }
        }
        
        double ally_WR_Odds = 1;
        double enemy_WR_Odds = 1;
        ArrayList<Hero> no_heroes = new ArrayList<>();
        double ally_OR_total = 1;
        for(int i = 0; i < selected_ally_heroes.length; i++){
            Hero h = selected_ally_heroes[i].getSelectedHero();
            if(h != null){
                HeroCounter hc = new HeroCounter(h, no_heroes, enemy_heroes,data);
                selected_ally_heroes[i].setAdvantage(hc.getPoint());
                double OR =  2 / (1 - hc.getPoint() * 1e-2) - 1;
                ally_OR_total = ally_OR_total * OR;
                double WR_odds = (h.win_rate * 1e-2) / (1 - (h.win_rate * 1e-2));
                ally_WR_Odds = ally_WR_Odds * WR_odds;
                enemy_WR_Odds = enemy_WR_Odds / WR_odds;
            }
        }
        
        double enemy_OR_total = 1;
        for(int i = 0; i < selected_enemy_heroes.length; i++){
            Hero h = selected_enemy_heroes[i].getSelectedHero();
            if(h != null){
                HeroCounter hc = new HeroCounter(h, no_heroes, ally_heroes, data);
                selected_enemy_heroes[i].setAdvantage(hc.getPoint());
                double OR =  2 / (1 - hc.getPoint() * 1e-2) - 1;
                enemy_OR_total = enemy_OR_total * OR;
                double WR_odds = (h.win_rate * 1e-2) / (1 - (h.win_rate * 1e-2));
                ally_WR_Odds = ally_WR_Odds / WR_odds;
                enemy_WR_Odds = enemy_WR_Odds * WR_odds;
            }
        }
        double ally_adv = 100 * (1 - 2/(ally_OR_total + 1));
        double ally_wr = 100 * (1 - 1/(ally_OR_total*ally_WR_Odds + 1));
        
        double enemy_adv = (100) * (1 - 2/(enemy_OR_total + 1));
        double enemy_wr = 100 * (1 - 1/(enemy_OR_total*enemy_WR_Odds + 1));
        
        NumberFormat formatter1 = new DecimalFormat("#0.00",DecimalFormatSymbols.getInstance(Locale.US));
        ally_team_advantage.setText("Ally Team Advantage: " + 
                formatter1.format(ally_adv) + "%");
        ally_team_winrate.setText(", Ally Team Winrate: " + 
                formatter1.format(ally_wr) + "%");
        enemy_team_advantage.setText("Enemy Team Advantage: " + 
                formatter1.format(enemy_adv) + "%");
        enemy_team_winrate.setText(", Enemy Team Winrate: " + 
                formatter1.format(enemy_wr) + "%");
        
        ArrayList<HeroCounter> hero_counters = new ArrayList<>();
        ArrayList<HeroCounter> all_hero_counters = new ArrayList<>();
        for(Hero h2 : data.hero_list){
            boolean f = true;
            all_hero_counters.add(new HeroCounter(h2, ally_heroes, enemy_heroes,data, ally_OR_total));
            for(HeroFilterPanel fp : filter_selector_list_all){
                if(!fp.filter(h2)){
                    f = false;
                    break;
                }
            }
            if(f){
                for (HeroCounterSelectPanel selected_hero : selected_enemy_heroes) {
                    if (h2.equals(selected_hero.getSelectedHero())) {
                        f = false;
                        break;
                    }
                }
                for (HeroCounterSelectPanel selected_hero : selected_ally_heroes) {
                    if (h2.equals(selected_hero.getSelectedHero())) {
                        f = false;
                        break;
                    }
                }
                if(f){
                    hero_counters.add(new HeroCounter(h2, ally_heroes, enemy_heroes,data, ally_OR_total));
                }
            }
        }
        
        last_sorting = counter_sort_box.getSelectedIndex();
        last_coloring = counter_color_box.getSelectedIndex();
        HeroCounterComparator comp;
        switch(counter_sort_box.getSelectedIndex()){
            case 0:
                comp = new HeroCounterComparator(COUNTER_NAME);
                break;
            case 1:
                comp = new HeroCounterComparator(COUNTER_ADVANTAGE);
                break;
            case 2:
                comp = new HeroCounterComparator(COUNTER_WINRATE);
                break;
            case 3:
                comp = new HeroCounterComparator(COUNTER_GENERALWR);
                break;
            case 4:
                comp = new HeroCounterComparator(COUNTER_PICKRATE);
                break;
            default:
                throw new RuntimeException("Invalid counter sort option.");
           
        }
        last_sorting = counter_sort_box.getSelectedIndex();
        Collections.sort(hero_counters, comp);
        
        NumberFormat formatter = new DecimalFormat("#0.000",DecimalFormatSymbols.getInstance(Locale.US));
        NumberFormat formatterSynergy = new DecimalFormat("+#0.00;-#0.00",DecimalFormatSymbols.getInstance(Locale.US));
        
        String eol = System.getProperty("line.separator");
        String header[] = new String[] {""};
        Color colorHeader = new Color(238, 238, 224);
        dtm.setColumnIdentifiers(header);
        dtm.setRowCount(0);
        
        String r = "", r1 = "";
        String totalHeroesText = "Total : " + hero_counters.size() + " heroes";
        r1 = r1 + getCenterAlignedString("Name",20,'-','*');
        r1 = r1+ getCenterAlignedString("Advantage",10,'-','*');
        r1 = r1 + getCenterAlignedString("Win Rate",14,'-','*');
        r1 = r1 + "-" + getCenterAlignedString("General WR",14,'-','*');
        String[] row = {totalHeroesText};
        dtm.addRow(row, colorHeader);
        row[0] = r1;
        dtm.addRow(row, colorHeader);
        r = r + "Statistics : " + eol;
        r = r + totalHeroesText + eol;
        r = r + "Counters : " + eol;
        r = r + r1 + eol;
        
        boolean initial = true;
        double advantageMin = -1;
        double advantageMax = 1;
        for(int i = 0; i < 150 && i < all_hero_counters.size(); i++){
            HeroCounter h = all_hero_counters.get(i);
            double value = getColorVariableValue(h, last_coloring);
            if(value < advantageMin || initial){
                advantageMin = value;
            }
            if(value > advantageMax || initial){
                advantageMax = value;
            }
            if(initial)
                initial = false;
        }
        double advantageRange = advantageMax - advantageMin;
        Color colorMin = new Color(250, 145, 145);
        Color colorMax = new Color(145, 250, 145);
        if(last_coloring == 4)
            colorMin = Color.WHITE;
        
        for(int i = 0; i < 150 && i < hero_counters.size(); i++){
            HeroCounter h = hero_counters.get(i);
            double advantage = h.getPoint();
            double value = getColorVariableValue(h, last_coloring);
            String heroName = String.format("%-20s", h.getHero().name);
            String synergyText = formatterSynergy.format(advantage)  + "%";
            String matchupWRText = formatter.format(h.getMatchupWR()) + "%";
//            String generalWRText = formatter.format(h.getExpectedWR()) + "%";
            String generalWRText = formatter.format(h.getHero().win_rate) + "%";
            String r2 = heroName;
            r2 = r2 + ":" + getCenterAlignedString(synergyText, 8,' ',' ');
            r2 = r2 + "  " + getCenterAlignedString(matchupWRText, 13,' ',' ');
            r2 = r2 + "  " + getCenterAlignedString(generalWRText,13,' ',' ');
            r = r + r2 + eol;
//            String[] row ={heroName, synergyText, matchupWRText, generalWRText};
            row[0] = r2;
            Color color = Color.WHITE;
            if(advantageRange != 0){
                double rate = (value - advantageMin) / advantageRange;
                color = mixColors(colorMin, colorMax, rate);
            }
            dtm.addRow(row, color);
        }
        
//        bottom_textarea.setLineWrap(false);
//        bottom_textarea.setRows(12);
//        bottom_scroll.
//        bottom_textarea.
//        
//        bottom_scroll.setBounds(0, 0, 200, 100);
//        dtm.
//        bottom_table.setro
        bottom_table.setModel(dtm);
//        bottom_table.setShowGrid(false);
//        bottom_table.setGridColor(new Color(0, 0 , 0, 0));
//        bottom_table.set
        bottom_textarea.setText(r);
        bottom_textarea.setEditable(false);
    }
    
    private double getColorVariableValue(HeroCounter hc, int colorVar){
        switch(colorVar){
            case 0:
                return 0;
            case 1:
                return hc.getPoint();
            case 2:
                return hc.getMatchupWR();
//            case 3:
//                return hc.getExpectedWR();
            case 3:
                return hc.getHero().win_rate;
            case 4:
                return hc.getHero().pick_rate;
            default:
                throw new RuntimeException("Invalid counter color option.");
        }
    }
    
    private Color mixColors(Color c1, Color c2, double r){
        int red = (int) Math.round(c1.getRed() * (1-r) + c2.getRed() * r);
        int green = (int) Math.round(c1.getGreen() * (1-r) + c2.getGreen() * r);
        int blue = (int) Math.round(c1.getBlue() * (1-r) + c2.getBlue() * r);
        return new Color(red, green, blue);
    }
    
    private String getCenterAlignedString(String s, int n, char delim, char delim2){
        String r = "";
        int a = (int) ((n - s.length()) * 0.5);
        int b = n - s.length() - a - 1;
        for(int i = 0; i < a ;i++){
            r = r + delim;
        }
        r = r + s;
        for(int i = 0; i < b ;i++){
            r = r + delim;
        }
        r = r + delim2;
        return r;
    }
    
    protected void setFontSize(float fontsize){
        int nRows = (int) fontsize;
//        bottom_textarea.setRows((int) fontsize);
        if(fontsize >= 15){
//            int nRows = 18;
            nRows = (int)(15 + (fontsize - 15) * 0.2);
//            bottom_textarea.setRows(nRows);
        }

        Font font = tabbed_pane.getFont();
        Font newFont = font.deriveFont(fontsize);
        Font font2 = filter_label.getFont();
        Font newFont2 = font2.deriveFont((float) (fontsize * 1.4));
        Font font3 = bottom_table.getFont();
        Font newFont3 = font3.deriveFont((float) (fontsize * 0.915));
        
//        bottom_table.set
        bottom_table.setPreferredScrollableViewportSize(new Dimension(0, 100));
        bottom_table.setRowHeight((int) (fontsize * 1.445));
        bottom_table.setPreferredScrollableViewportSize(new Dimension( 
                bottom_table.getPreferredScrollableViewportSize().width, 
                nRows*bottom_table.getRowHeight() 
        )); 
        
        tabbed_pane.setFont(newFont);
        select_ally_hero.setFontSize(fontsize);
        select_enemy_hero.setFontSize(fontsize);
        counter_sort_box.setFont(newFont);
        counter_color_box.setFont(newFont);
        
        for(int i = 0; i < selected_enemy_heroes.length; i++){
            selected_enemy_heroes[i].setFontSize(fontsize);
        }
        
        enemy_team_advantage.setFont(newFont);
        enemy_team_winrate.setFont(newFont);
        ally_team_advantage.setFont(newFont);
        ally_team_winrate.setFont(newFont);
        
        for(int i = 0; i < selected_ally_heroes.length; i++){
            selected_ally_heroes[i].setFontSize(fontsize);
        }
        
        filter_label.setFont(newFont2);
        counter_label.setFont(newFont2);
        counter_filter_label.setFont(newFont2);
        filter_button.setFont(newFont);
        counter_sort_label.setFont(newFont);
        counter_color_label.setFont(newFont);
        
        filter_panel1.setFontSize(fontsize);
        filter_panel2.setFontSize(fontsize);
        filter_panel3.setFontSize(fontsize);
        filter_panel4.setFontSize(fontsize);
        filter_panel5.setFontSize(fontsize);
        filter_panel6.setFontSize(fontsize);
        filter_panel7.setFontSize(fontsize);
        filter_panel_role_pair.setFontSize(fontsize);
        filter_panel_lane_pair.setFontSize(fontsize);  
        filter_panel_range_pair.setFontSize(fontsize);
        
        bottom_table.setFont(newFont3);
    }
    
    private void clearAllFilters(){
        filter_panel1.clearAll();
        filter_panel2.clearAll();
        filter_panel3.clearAll();
        filter_panel4.clearAll();
        filter_panel5.clearAll();
        filter_panel6.clearAll();
        filter_panel7.clearAll();
    }
    
    private void createHeroFilters(){
        support_list.add("Abaddon");
        support_list.add("Alchemist");
        support_list.add("Ancient Apparition");
        support_list.add("Bane");
        support_list.add("Beastmaster");
        support_list.add("Chen");
        support_list.add("Crystal Maiden");
        support_list.add("Dark Willow");
        support_list.add("Dazzle");
        support_list.add("Disruptor");
        support_list.add("Earth Spirit");
        support_list.add("Elder Titan");
        support_list.add("Enchantress");
        support_list.add("Faceless Void");
        support_list.add("Grimstroke");
        support_list.add("Io");
        support_list.add("Jakiro");
        support_list.add("Keeper of the Light");
        support_list.add("Leshrac");
        support_list.add("Lich");
        support_list.add("Lina");
        support_list.add("Lion");
        support_list.add("Mirana");
        support_list.add("Naga Siren");
        support_list.add("Necrophos");
        support_list.add("Nyx Assassin");
        support_list.add("Ogre Magi");
        support_list.add("Omniknight");
        support_list.add("Oracle");
        support_list.add("Phoenix");
        support_list.add("Pudge");
        support_list.add("Pugna");
        support_list.add("Rubick");
        support_list.add("Shadow Demon");
        support_list.add("Shadow Shaman");
        support_list.add("Silencer");
        support_list.add("Skywrath Mage");
        support_list.add("Treant Protector");
        support_list.add("Tusk");
        support_list.add("Underlord");
        support_list.add("Vengeful Spirit");
        support_list.add("Venomancer");
        support_list.add("Visage");
        support_list.add("Warlock");
        support_list.add("Windranger");
        support_list.add("Winter Wyvern");
        support_list.add("Witch Doctor");
        support_list.add("Wraith King");
        
        carry_list.add("Abaddon");
        carry_list.add("Alchemist");
        carry_list.add("Anti-Mage");
        carry_list.add("Arc Warden");
        carry_list.add("Bloodseeker");
        carry_list.add("Brewmaster");
        carry_list.add("Bristleback");
        carry_list.add("Broodmother");
        carry_list.add("Chaos Knight");
        carry_list.add("Clinkz");
        carry_list.add("Death Prophet");
        carry_list.add("Doom");
        carry_list.add("Dragon Knight");
        carry_list.add("Drow Ranger");
        carry_list.add("Ember Spirit");
        carry_list.add("Enchantress");
        carry_list.add("Faceless Void");
        carry_list.add("Gyrocopter");
        carry_list.add("Huskar");
        carry_list.add("Invoker");
        carry_list.add("Juggernaut");
        carry_list.add("Kunkka");
        carry_list.add("Legion Commander");
        carry_list.add("Leshrac");
        carry_list.add("Lifestealer");
        carry_list.add("Lina");
        carry_list.add("Lone Druid");
        carry_list.add("Luna");
        carry_list.add("Lycan");
        carry_list.add("Medusa");
        carry_list.add("Meepo");
        carry_list.add("Mirana");
        carry_list.add("Monkey King");
        carry_list.add("Morphling");
        carry_list.add("Naga Siren");
        carry_list.add("Nature's Prophet");
        carry_list.add("Necrophos");
        carry_list.add("Night Stalker");
        carry_list.add("Outworld Devourer");
        carry_list.add("Pangolier");
        carry_list.add("Phantom Assassin");
        carry_list.add("Phantom Lancer");
        carry_list.add("Puck");
        carry_list.add("Pudge");
        carry_list.add("Pugna");
        carry_list.add("Queen of Pain");
        carry_list.add("Razor");
        carry_list.add("Riki");
        carry_list.add("Shadow Fiend");
        carry_list.add("Silencer");
        carry_list.add("Skywrath Mage");
        carry_list.add("Slardar");
        carry_list.add("Slark");
        carry_list.add("Sniper");
        carry_list.add("Spectre");
        carry_list.add("Storm Spirit");
        carry_list.add("Sven");
        carry_list.add("Templar Assassin");
        carry_list.add("Terrorblade");
        carry_list.add("Timbersaw");
        carry_list.add("Tinker");
        carry_list.add("Tiny");
        carry_list.add("Troll Warlord");
        carry_list.add("Tusk");
        carry_list.add("Ursa");
        carry_list.add("Vengeful Spirit");
        carry_list.add("Venomancer");
        carry_list.add("Viper");
        carry_list.add("Visage");
        carry_list.add("Warlock");
        carry_list.add("Weaver");
        carry_list.add("Windranger");
        carry_list.add("Wraith King");
        carry_list.add("Zeus");
        
        melee_list.add("Abaddon");
        melee_list.add("Alchemist");
        melee_list.add("Anti-Mage");
        melee_list.add("Axe");
        melee_list.add("Beastmaster");
        melee_list.add("Bloodseeker");
        melee_list.add("Bounty Hunter");
        melee_list.add("Brewmaster");
        melee_list.add("Bristleback");
        melee_list.add("Broodmother");
        melee_list.add("Centaur Warrunner");
        melee_list.add("Chaos Knight");
        melee_list.add("Clockwerk");
        melee_list.add("Dark Seer");
        melee_list.add("Doom");
        melee_list.add("Dragon Knight");
        melee_list.add("Earth Spirit");
        melee_list.add("Earthshaker");
        melee_list.add("Elder Titan");
        melee_list.add("Ember Spirit");
        melee_list.add("Faceless Void");
        melee_list.add("Juggernaut");
        melee_list.add("Kunkka");
        melee_list.add("Legion Commander");
        melee_list.add("Lifestealer");
        melee_list.add("Lone Druid");
        melee_list.add("Lycan");
        melee_list.add("Magnus");
        melee_list.add("Meepo");
        melee_list.add("Monkey King");
        melee_list.add("Naga Siren");
        melee_list.add("Night Stalker");
        melee_list.add("Nyx Assassin");
        melee_list.add("Ogre Magi");
        melee_list.add("Omniknight");
        melee_list.add("Pangolier");
        melee_list.add("Phantom Assassin");
        melee_list.add("Phantom Lancer");
        melee_list.add("Pudge");
        melee_list.add("Riki");
        melee_list.add("Sand King");
        melee_list.add("Slardar");
        melee_list.add("Slark");
        melee_list.add("Spectre");
        melee_list.add("Spirit Breaker");
        melee_list.add("Sven");
        melee_list.add("Terrorblade");
        melee_list.add("Tidehunter");
        melee_list.add("Timbersaw");
        melee_list.add("Tiny");
        melee_list.add("Treant Protector");
        melee_list.add("Troll Warlord");
        melee_list.add("Tusk");
        melee_list.add("Undying");
        melee_list.add("Underlord");
        melee_list.add("Ursa");
        melee_list.add("Wraith King");
        
        ranged_list.add("Ancient Apparition");
        ranged_list.add("Arc Warden");
        ranged_list.add("Bane");
        ranged_list.add("Batrider");
        ranged_list.add("Bane");
        ranged_list.add("Chen");
        ranged_list.add("Clinkz");
        ranged_list.add("Crystal Maiden");
        ranged_list.add("Dark Willow");
        ranged_list.add("Dazzle");
        ranged_list.add("Death Prophet");
        ranged_list.add("Disruptor");
        ranged_list.add("Dragon Knight");
        ranged_list.add("Drow Ranger");
        ranged_list.add("Enchantress");
        ranged_list.add("Enigma");
        ranged_list.add("Grimstroke");
        ranged_list.add("Gyrocopter");
        ranged_list.add("Huskar");
        ranged_list.add("Invoker");
        ranged_list.add("Io");
        ranged_list.add("Jakiro");
        ranged_list.add("Keeper of the Light");
        ranged_list.add("Leshrac");
        ranged_list.add("Lich");
        ranged_list.add("Lina");
        ranged_list.add("Lion");
        ranged_list.add("Lone Druid");
        ranged_list.add("Luna");
        ranged_list.add("Medusa");
        ranged_list.add("Mirana");
        ranged_list.add("Morphling");
        ranged_list.add("Nature's Prophet");
        ranged_list.add("Necrophos");
        ranged_list.add("Oracle");
        ranged_list.add("Outworld Devourer");
        ranged_list.add("Phoenix");
        ranged_list.add("Puck");
        ranged_list.add("Pugna");
        ranged_list.add("Queen of Pain");
        ranged_list.add("Razor");
        ranged_list.add("Rubick");
        ranged_list.add("Shadow Demon");
        ranged_list.add("Shadow Fiend");
        ranged_list.add("Shadow Shaman");
        ranged_list.add("Silencer");
        ranged_list.add("Skywrath Mage");
        ranged_list.add("Sniper");
        ranged_list.add("Storm Spirit");
        ranged_list.add("Techies");
        ranged_list.add("Templar Assassin");
        ranged_list.add("Terrorblade");
        ranged_list.add("Tinker");
        ranged_list.add("Troll Warlord");
        ranged_list.add("Vengeful Spirit");
        ranged_list.add("Venomancer");
        ranged_list.add("Viper");
        ranged_list.add("Visage");
        ranged_list.add("Warlock");
        ranged_list.add("Weaver");
        ranged_list.add("Windranger");
        ranged_list.add("Winter Wyvern");
        ranged_list.add("Witch Doctor");
        ranged_list.add("Zeus");
        
        stun_list.add("Alchemist");
        stun_list.add("Ancient Apparition");
        stun_list.add("Anti-Mage");
        stun_list.add("Bane");
        stun_list.add("Batrider");
        stun_list.add("Beastmaster");
        stun_list.add("Bounty Hunter");
        stun_list.add("Brewmaster");
        stun_list.add("Centaur Warrunner");
        stun_list.add("Chaos Knight");
        stun_list.add("Chen");
        stun_list.add("Clockwerk");
        stun_list.add("Dark Willow");
        stun_list.add("Dazzle");
        stun_list.add("Doom");
        stun_list.add("Dragon Knight");
        stun_list.add("Earth Spirit");
        stun_list.add("Earthshaker");
        stun_list.add("Enigma");
        stun_list.add("Faceless Void");
        stun_list.add("Gyrocopter");
        stun_list.add("Invoker");
        stun_list.add("Jakiro");
        stun_list.add("Grimstroke");
        stun_list.add("Keeper of the Light");
        stun_list.add("Kunkka");
        stun_list.add("Leshrac");
        stun_list.add("Lina");
        stun_list.add("Lion");
        stun_list.add("Luna");
        stun_list.add("Magnus");
        stun_list.add("Mirana");
        stun_list.add("Morphling");
        stun_list.add("Monkey King");
        stun_list.add("Naga Siren");
        stun_list.add("Necrophos");
        stun_list.add("Nyx Assassin");
        stun_list.add("Ogre Magi");
        stun_list.add("Pangolier");
        stun_list.add("Phoenix");
        stun_list.add("Puck");
        stun_list.add("Pudge");
        stun_list.add("Rubick");
        stun_list.add("Sand King");
        stun_list.add("Shadow Shaman");
        stun_list.add("Slardar");
        stun_list.add("Spirit Breaker");
        stun_list.add("Storm Spirit");
        stun_list.add("Sven");
        stun_list.add("Techies");
        stun_list.add("Tidehunter");
        stun_list.add("Tiny");
        stun_list.add("Troll Warlord");
        stun_list.add("Tusk");
        stun_list.add("Vengeful Spirit");
        stun_list.add("Visage");
        stun_list.add("Warlock");
        stun_list.add("Windranger");
        stun_list.add("Witch Doctor");
        stun_list.add("Windranger");
        stun_list.add("Wraith King");
        
        ensare_list.add("Chen");
        ensare_list.add("Crystal Maiden");
        ensare_list.add("Dark Willow");
        ensare_list.add("Disruptor");
        ensare_list.add("Ember Spirit");
        ensare_list.add("Lone Druid");
        ensare_list.add("Meepo");
        ensare_list.add("Naga Siren");
        ensare_list.add("Oracle");
        ensare_list.add("Treant Protector");
        ensare_list.add("Underlord");
        
        slow_list.add("Abaddon");
        slow_list.add("Ancient Apparition");
        slow_list.add("Arc Warden");
        slow_list.add("Axe");
        slow_list.add("Batrider");
        slow_list.add("Beastmaster");
        slow_list.add("Bounty Hunter");
        slow_list.add("Broodmother");
        slow_list.add("Centaur Warrunner");
        slow_list.add("Chen");
        slow_list.add("Crystal Maiden");
        slow_list.add("Dazzle");
        slow_list.add("Death Prophet");
        slow_list.add("Dragon Knight");
        slow_list.add("Drow Ranger");
        slow_list.add("Enchantress");
        slow_list.add("Gyrocopter");
        slow_list.add("Huskar");
        slow_list.add("Invoker");
        slow_list.add("Io");
        slow_list.add("Jakiro");
        slow_list.add("Grimstroke");
        slow_list.add("Kunkka");
        slow_list.add("Leshrac");
        slow_list.add("Lich");
        slow_list.add("Lifestealer");
        slow_list.add("Meepo");
        slow_list.add("Night Stalker");
        slow_list.add("Ogre Magi");
        slow_list.add("Omniknight");
        slow_list.add("Pangolier");
        slow_list.add("Phantom Assassin");
        slow_list.add("Phantom Lancer");
        slow_list.add("Phoenix");
        slow_list.add("Pudge");
        slow_list.add("Queen of Pain");
        slow_list.add("Riki");
        slow_list.add("Shadow Demon");
        slow_list.add("Shadow Fiend");
        slow_list.add("Silencer");
        slow_list.add("Skywrath Mage");
        slow_list.add("Sniper");
        slow_list.add("Spectre");
        slow_list.add("Storm Spirit");
        slow_list.add("Templar Assassin");
        slow_list.add("Terrorblade");
        slow_list.add("Tidehunter");
        slow_list.add("Timbersaw");
        slow_list.add("Treant Protector");
        slow_list.add("Troll Warlord");
        slow_list.add("Tusk");
        slow_list.add("Undying");
        slow_list.add("Ursa");
        slow_list.add("Venomancer");
        slow_list.add("Viper");
        slow_list.add("Warlock");
        slow_list.add("Winter Wyvern");
        slow_list.add("Wraith King");
        
        silence_list.add("Bloodseeker");
        silence_list.add("Death Prophet");
        silence_list.add("Disruptor");
        silence_list.add("Doom");
        silence_list.add("Drow Ranger");
        silence_list.add("Earth Spirit");
        silence_list.add("Grimstroke");
        silence_list.add("Night Stalker");
        silence_list.add("Puck");
        silence_list.add("Riki");
        silence_list.add("Silencer");
        
        disable_misc_list.add("Ancient Apparition");
        disable_misc_list.add("Anti-Mage");
        disable_misc_list.add("Arc Warden");
        disable_misc_list.add("Axe");
        disable_misc_list.add("Bane");
        disable_misc_list.add("Batrider");
        disable_misc_list.add("Bloodseeker");
        disable_misc_list.add("Brewmaster");
        disable_misc_list.add("Chaos Knight");
        disable_misc_list.add("Clockwerk");
        disable_misc_list.add("Dark Seer");
        disable_misc_list.add("Dark Willow");
        disable_misc_list.add("Dazzle");
        disable_misc_list.add("Disruptor");
        disable_misc_list.add("Earth Spirit");
        disable_misc_list.add("Earthshaker");
        disable_misc_list.add("Elder Titan");
        disable_misc_list.add("Faceless Void");
        disable_misc_list.add("Invoker");
        disable_misc_list.add("Grimstroke");
        disable_misc_list.add("Keeper of the Light");
        disable_misc_list.add("Legion Commander");
        disable_misc_list.add("Magnus");
        disable_misc_list.add("Medusa");
        disable_misc_list.add("Naga Siren");
        disable_misc_list.add("Nature's Prophet");
        disable_misc_list.add("Night Stalker");
        disable_misc_list.add("Outworld Devourer");
        disable_misc_list.add("Phoenix");
        disable_misc_list.add("Pudge");
        disable_misc_list.add("Pugna");
        disable_misc_list.add("Razor");
        disable_misc_list.add("Rubick");
        disable_misc_list.add("Shadow Demon");
        disable_misc_list.add("Slark");
        disable_misc_list.add("Tidehunter");
        disable_misc_list.add("Timbersaw");
        disable_misc_list.add("Tiny");
        disable_misc_list.add("Tusk");
        disable_misc_list.add("Vengeful Spirit");
        disable_misc_list.add("Winter Wyvern");
        
        disable_reliable_list.add("Alchemist");
        disable_reliable_list.add("Axe");
        disable_reliable_list.add("Bane");
        disable_reliable_list.add("Batrider");
        disable_reliable_list.add("Beastmaster");
        disable_reliable_list.add("Bloodseeker");
        disable_reliable_list.add("Centaur Warrunner");
        disable_reliable_list.add("Chaos Knight");
        disable_reliable_list.add("Chen");
        disable_reliable_list.add("Clockwerk");
        disable_reliable_list.add("Crystal Maiden");
        disable_reliable_list.add("Dark Willow");
        disable_reliable_list.add("Death Prophet");
        disable_reliable_list.add("Disruptor");
        disable_reliable_list.add("Doom");
        disable_reliable_list.add("Dragon Knight");
        disable_reliable_list.add("Drow Ranger");
        disable_reliable_list.add("Earth Spirit");
        disable_reliable_list.add("Earthshaker");
        disable_reliable_list.add("Enigma");
        disable_reliable_list.add("Faceless Void");
        disable_reliable_list.add("Gyrocopter");
        disable_reliable_list.add("Invoker");
        disable_reliable_list.add("Jakiro");
        disable_reliable_list.add("Kunkka");
        disable_reliable_list.add("Legion Commander");
        disable_reliable_list.add("Leshrac");
        disable_reliable_list.add("Lich");
        disable_reliable_list.add("Lifestealer");
        disable_reliable_list.add("Lina");
        disable_reliable_list.add("Lion");
        disable_reliable_list.add("Magnus");
        disable_reliable_list.add("Meepo");
        disable_reliable_list.add("Monkey King");
        disable_reliable_list.add("Naga Siren");
        disable_reliable_list.add("Night Stalker");
        disable_reliable_list.add("Nyx Assassin");
        disable_reliable_list.add("Ogre Magi");
        disable_reliable_list.add("Oracle");
        disable_reliable_list.add("Outworld Devourer");
        disable_reliable_list.add("Phantom Assassin");
        disable_reliable_list.add("Phantom Lancer");
        disable_reliable_list.add("Phoenix");
        disable_reliable_list.add("Puck");
        disable_reliable_list.add("Pudge");
        disable_reliable_list.add("Pugna");
        disable_reliable_list.add("Queen of Pain");
        disable_reliable_list.add("Razor");
        disable_reliable_list.add("Riki");
        disable_reliable_list.add("Rubick");
        disable_reliable_list.add("Sand King");
        disable_reliable_list.add("Shadow Demon");
        disable_reliable_list.add("Shadow Shaman");
        disable_reliable_list.add("Silencer");
        disable_reliable_list.add("Slardar");
        disable_reliable_list.add("Spirit Breaker");
        disable_reliable_list.add("Sven");
        disable_reliable_list.add("Templar Assassin");
        disable_reliable_list.add("Tidehunter");
        disable_reliable_list.add("Timbersaw");
        disable_reliable_list.add("Tiny");
        disable_reliable_list.add("Treant Protector");
        disable_reliable_list.add("Tusk");
        disable_reliable_list.add("Underlord");
        disable_reliable_list.add("Vengeful Spirit");
        disable_reliable_list.add("Venomancer");
        disable_reliable_list.add("Viper");
        disable_reliable_list.add("Visaget");
        disable_reliable_list.add("Windranger");
        disable_reliable_list.add("Winter Wyvern");
        disable_reliable_list.add("Witch Doctor");
        disable_reliable_list.add("Wraith King");
        
        disable_universal_list.add("Abaddon");
        disable_universal_list.add("Anti-Mage");
        disable_universal_list.add("Axe");
        disable_universal_list.add("Bane");
        disable_universal_list.add("Batrider");
        disable_universal_list.add("Beastmaster");
        disable_universal_list.add("Bloodseeker");
        disable_universal_list.add("Broodmother");
        disable_universal_list.add("Clockwerk");
        disable_universal_list.add("Disruptor");
        disable_universal_list.add("Doom");
        disable_universal_list.add("Earthshaker");
        disable_universal_list.add("Enigma");
        disable_universal_list.add("Faceless Void");
        disable_universal_list.add("Legion Commander");
        disable_universal_list.add("Lone Druid");
        disable_universal_list.add("Magnus");
        disable_universal_list.add("Medusa");
        disable_universal_list.add("Nature's Prophet");
        disable_universal_list.add("Necrophos");
        disable_universal_list.add("Phoenix");
        disable_universal_list.add("Puck");
        disable_universal_list.add("Pudge");
        disable_universal_list.add("Razor");
        disable_universal_list.add("Shadow Demon");
        disable_universal_list.add("Silencer");
        disable_universal_list.add("Spirit Breaker");
        disable_universal_list.add("Treant Protector");
        disable_universal_list.add("Troll Warlord");
        disable_universal_list.add("Tusk");
        disable_universal_list.add("Vengeful Spirit");
        disable_universal_list.add("Viper");
        disable_universal_list.add("Warlock");
        disable_universal_list.add("Winter Wyvern");
        
        disable_aoe_list.add("Arc Warden");
        disable_aoe_list.add("Axe");
        disable_aoe_list.add("Bloodseeker");
        disable_aoe_list.add("Centaur Warrunner");
        disable_aoe_list.add("Chen");
        disable_aoe_list.add("Clockwerk");
        disable_aoe_list.add("Crystal Maiden");
        disable_aoe_list.add("Dark Seer");
        disable_aoe_list.add("Dark Willow");
        disable_aoe_list.add("Death Prophet");
        disable_aoe_list.add("Disruptor");
        disable_aoe_list.add("Drow Ranger");
        disable_aoe_list.add("Earth Spirit");
        disable_aoe_list.add("Earthshaker");
        disable_aoe_list.add("Elder Titan");
        disable_aoe_list.add("Enigma");
        disable_aoe_list.add("Faceless Void");
        disable_aoe_list.add("Grimstroke");
        disable_aoe_list.add("Gyrocopter");
        disable_aoe_list.add("Invoker");
        disable_aoe_list.add("Jakiro");
        disable_aoe_list.add("Kunkka");
        disable_aoe_list.add("Leshrac");
        disable_aoe_list.add("Lich");
        disable_aoe_list.add("Lina");
        disable_aoe_list.add("Lion");
        disable_aoe_list.add("Magnus");
        disable_aoe_list.add("Medusa");
        disable_aoe_list.add("Meepo");
        disable_aoe_list.add("Monkey King");
        disable_aoe_list.add("Naga Siren");
        disable_aoe_list.add("Phoenix");
        disable_aoe_list.add("Puck");
        disable_aoe_list.add("Riki");
        disable_aoe_list.add("Rubick");
        disable_aoe_list.add("Sand King");
        disable_aoe_list.add("Shadow Fiend");
        disable_aoe_list.add("Silencer");
        disable_aoe_list.add("Slardar");
        disable_aoe_list.add("Sniper");
        disable_aoe_list.add("Spirit Breaker");
        disable_aoe_list.add("Sven");
        disable_aoe_list.add("Techies");
        disable_aoe_list.add("Tidehunter");
        disable_aoe_list.add("Timbersaw");
        disable_aoe_list.add("Tiny");
        disable_aoe_list.add("Treant Protector");
        disable_aoe_list.add("Tusk");
        disable_aoe_list.add("Underlord");
        disable_aoe_list.add("Ursa");
        disable_aoe_list.add("Venomancer");
        disable_aoe_list.add("Visage");
        disable_aoe_list.add("Warlock");
        disable_aoe_list.add("Winter Wyvern");
        
        damage_physical_list.add("Abaddon");
        damage_physical_list.add("Alchemist");
        damage_physical_list.add("Anti-Mage");
        damage_physical_list.add("Arc Warden");
        damage_physical_list.add("Axe");
        damage_physical_list.add("Beastmaster");
        damage_physical_list.add("Bloodseeker");
        damage_physical_list.add("Bounty Hunter");
        damage_physical_list.add("Brewmaster");
        damage_physical_list.add("Bristleback");
        damage_physical_list.add("Broodmother");
        damage_physical_list.add("Chaos Knight");
        damage_physical_list.add("Chen");
        damage_physical_list.add("Clinkz");
        damage_physical_list.add("Dazzle");
        damage_physical_list.add("Death Prophet");
        damage_physical_list.add("Doom");
        damage_physical_list.add("Dragon Knight");
        damage_physical_list.add("Drow Ranger");
        damage_physical_list.add("Elder Titan");
        damage_physical_list.add("Ember Spirit");
        damage_physical_list.add("Enigma");
        damage_physical_list.add("Faceless Void");
        damage_physical_list.add("Gyrocopter");
        damage_physical_list.add("Huskar");
        damage_physical_list.add("Invoker");
        damage_physical_list.add("Juggernaut");
        damage_physical_list.add("Kunkka");
        damage_physical_list.add("Legion Commander");
        damage_physical_list.add("Lifestealer");
        damage_physical_list.add("Lina");
        damage_physical_list.add("Lone Druid");
        damage_physical_list.add("Luna");
        damage_physical_list.add("Lycan");
        damage_physical_list.add("Magnus");
        damage_physical_list.add("Medusa");
        damage_physical_list.add("Meepo");
        damage_physical_list.add("Mirana");
        damage_physical_list.add("Monkey King");
        damage_physical_list.add("Morphling");
        damage_physical_list.add("Naga Siren");
        damage_physical_list.add("Nature's Prophet");
        damage_physical_list.add("Night Stalker");
        damage_physical_list.add("Pangolier");
        damage_physical_list.add("Phantom Assassin");
        damage_physical_list.add("Phantom Lancer");
        damage_physical_list.add("Razor");
        damage_physical_list.add("Riki");
        damage_physical_list.add("Shadow Fiend");
        damage_physical_list.add("Shadow Shaman");
        damage_physical_list.add("Slardar");
        damage_physical_list.add("Slark");
        damage_physical_list.add("Sniper");
        damage_physical_list.add("Spectre");
        damage_physical_list.add("Spirit Breaker");
        damage_physical_list.add("Sven");
        damage_physical_list.add("Techies");
        damage_physical_list.add("Templar Assassin");
        damage_physical_list.add("Terrorblade");
        damage_physical_list.add("Tidehunter");
        damage_physical_list.add("Tiny");
        damage_physical_list.add("Treant Protector");
        damage_physical_list.add("Troll Warlord");
        damage_physical_list.add("Tusk");
        damage_physical_list.add("Underlord");
        damage_physical_list.add("Ursa");
        damage_physical_list.add("Vengeful Spirit");
        damage_physical_list.add("Viper");
        damage_physical_list.add("Visage");
        damage_physical_list.add("Weaver");
        damage_physical_list.add("Windranger");
        damage_physical_list.add("Witch Doctor");
        damage_physical_list.add("Wraith King");
        
        damage_magical_list.add("Abaddon");
        damage_magical_list.add("Ancient Apparition");
        damage_magical_list.add("Arc Warden");
        damage_magical_list.add("Bane");
        damage_magical_list.add("Batrider");
        damage_magical_list.add("Bounty Hunter");
        damage_magical_list.add("Brewmaster");
        damage_magical_list.add("Centaur Warrunner");
        damage_magical_list.add("Clockwerk");
        damage_magical_list.add("Crystal Maiden");
        damage_magical_list.add("Dark Seer");
        damage_magical_list.add("Dark Willow");
        damage_magical_list.add("Death Prophet");
        damage_magical_list.add("Disruptor");
        damage_magical_list.add("Doom");
        damage_magical_list.add("Earth Spirit");
        damage_magical_list.add("Earthshaker");
        damage_magical_list.add("Elder Titan");
        damage_magical_list.add("Grimstroke");
        damage_magical_list.add("Gyrocopter");
        damage_magical_list.add("Huskar");
        damage_magical_list.add("Invoker");
        damage_magical_list.add("Jakiro");
        damage_magical_list.add("Keeper of the Light");
        damage_magical_list.add("Leshrac");
        damage_magical_list.add("Lich");
        damage_magical_list.add("Lina");
        damage_magical_list.add("Lion");
        damage_magical_list.add("Luna");
        damage_magical_list.add("Magnus");
        damage_magical_list.add("Meepo");
        damage_magical_list.add("Mirana");
        damage_magical_list.add("Morphling");
        damage_magical_list.add("Nature's Prophet");
        damage_magical_list.add("Necrophos");
        damage_magical_list.add("Nyx Assassin");
        damage_magical_list.add("Ogre Magi");
        damage_magical_list.add("Oracle");
        damage_magical_list.add("Phoenix");
        damage_magical_list.add("Puck");
        damage_magical_list.add("Pudge");
        damage_magical_list.add("Pugna");
        damage_magical_list.add("Queen of Pain");
        damage_magical_list.add("Rubick");
        damage_magical_list.add("Sand King");
        damage_magical_list.add("Shadow Demon");
        damage_magical_list.add("Shadow Fiend");
        damage_magical_list.add("Shadow Shaman");
        damage_magical_list.add("Silencer");
        damage_magical_list.add("Skywrath Mage");
        damage_magical_list.add("Storm Spirit");
        damage_magical_list.add("Techies");
        damage_magical_list.add("Tidehunter");
        damage_magical_list.add("Timbersaw");
        damage_magical_list.add("Tinker");
        damage_magical_list.add("Tiny");
        damage_magical_list.add("Tusk");
        damage_magical_list.add("Underlord");
        damage_magical_list.add("Vengeful Spirit");
        damage_magical_list.add("Venomancer");
        damage_magical_list.add("Warlock");
        damage_magical_list.add("Winter Wyvern");
        damage_magical_list.add("Witch Doctor");
        damage_magical_list.add("Zeus");
        
        damage_pure_list.add("Axe");
        damage_pure_list.add("Bane");
        damage_pure_list.add("Bloodseeker");
        damage_pure_list.add("Enchantress");
        damage_pure_list.add("Enigma");
        damage_pure_list.add("Invoker");
        damage_pure_list.add("Outworld Devourer");
        damage_pure_list.add("Pudge");
        damage_pure_list.add("Queen of Pain");
        damage_pure_list.add("Silencer");
        damage_pure_list.add("Spectre");
        damage_pure_list.add("Timbersaw");
        damage_pure_list.add("Tinker");
        
        initiator_list.add("Alchemist");
        initiator_list.add("Axe");
        initiator_list.add("Batrider");
        initiator_list.add("Beastmaster");
        initiator_list.add("Bloodseeker");
        initiator_list.add("Brewmaster");
        initiator_list.add("Centaur Warrunner");
        initiator_list.add("Chaos Knight");
        initiator_list.add("Clockwerk");
        initiator_list.add("Dark Seer");
        initiator_list.add("Dark Willow");
        initiator_list.add("Disruptor");
        initiator_list.add("Doom");
        initiator_list.add("Earth Spirit");
        initiator_list.add("Earthshaker");
        initiator_list.add("Elder Titan");
        initiator_list.add("Enigma");
        initiator_list.add("Faceless Void");
        initiator_list.add("Gyrocopter");
        initiator_list.add("Huskar");
        initiator_list.add("Invoker");
        initiator_list.add("Kunkka");
        initiator_list.add("Legion Commander");
        initiator_list.add("Magnus");
        initiator_list.add("Meepo");
        initiator_list.add("Naga Siren");
        initiator_list.add("Night Stalker");
        initiator_list.add("Nyx Assassin");
        initiator_list.add("Phoenix");
        initiator_list.add("Puck");
        initiator_list.add("Pudge");
        initiator_list.add("Sand King");
        initiator_list.add("Slardar");
        initiator_list.add("Spirit Breaker");
        initiator_list.add("Storm Spirit");
        initiator_list.add("Tidehunter");
        initiator_list.add("Tiny");
        initiator_list.add("Treant Protector");
        initiator_list.add("Tusk");
        initiator_list.add("Vengeful Spirit");
        initiator_list.add("Venomancer");
        initiator_list.add("Warlock");
        initiator_list.add("Wraith King");
        
        pusher_list.add("Beastmaster");
        pusher_list.add("Broodmother");
        pusher_list.add("Chen");
        pusher_list.add("Clinkz");
        pusher_list.add("Death Prophet");
        pusher_list.add("Dragon Knight");
        pusher_list.add("Drow Ranger");
        pusher_list.add("Enchantress");
        pusher_list.add("Enigma");
        pusher_list.add("Jakiro");
        pusher_list.add("Juggernaut");
        pusher_list.add("Leshrac");
        pusher_list.add("Lone Druid");
        pusher_list.add("Lycan");
        pusher_list.add("Meepo");
        pusher_list.add("Naga Siren");
        pusher_list.add("Nature's Prophet");
        pusher_list.add("Pugna");
        pusher_list.add("Razor");
        pusher_list.add("Shadow Shaman");
        pusher_list.add("Terrorblade");
        pusher_list.add("Tiny");
        pusher_list.add("Troll Warlord");
        pusher_list.add("Underlord");
        pusher_list.add("Venomancer");
        
        ganker_list.add("Alchemist");
        ganker_list.add("Axe");
        ganker_list.add("Bane");
        ganker_list.add("Batrider");
        ganker_list.add("Beastmaster");
        ganker_list.add("Bloodseeker");
        ganker_list.add("Bounty Hunter");
        ganker_list.add("Clinkz");
        ganker_list.add("Clockwerk");
        ganker_list.add("Dark Willow");
        ganker_list.add("Earth Spirit");
        ganker_list.add("Earthshaker");
        ganker_list.add("Ember Spirit");
        ganker_list.add("Faceless Void");
        ganker_list.add("Grimstroke");
        ganker_list.add("Invoker");
        ganker_list.add("Lina");
        ganker_list.add("Lion");
        ganker_list.add("Magnus");
        ganker_list.add("Mirana");
        ganker_list.add("Monkey King");
        ganker_list.add("Naga Siren");
        ganker_list.add("Nature's Prophet");
        ganker_list.add("Night Stalker");
        ganker_list.add("Nyx Assassin");
        ganker_list.add("Ogre Magi");
        ganker_list.add("Puck");
        ganker_list.add("Pudge");
        ganker_list.add("Queen of Pain");
        ganker_list.add("Riki");
        ganker_list.add("Rubick");
        ganker_list.add("Sand King");
        ganker_list.add("Shadow Demon");
        ganker_list.add("Shadow Shaman");
        ganker_list.add("Slardar");
        ganker_list.add("Spirit Breaker");
        ganker_list.add("Storm Spirit");
        ganker_list.add("Templar Assassin");
        ganker_list.add("Tinker");
        ganker_list.add("Tusk");
        ganker_list.add("Underlord");
        ganker_list.add("Vengeful Spirit");
        ganker_list.add("Windranger");
        ganker_list.add("Witch Doctor");
        ganker_list.add("Wraith King");
        
        healer_list.add("Abaddon");
        healer_list.add("Bloodseeker");
        healer_list.add("Chen");
        healer_list.add("Dazzle");
        healer_list.add("Enchantress");
        healer_list.add("Huskar");
        healer_list.add("Io");
        healer_list.add("Juggernaut");
        healer_list.add("Keeper of the Light");
        healer_list.add("Necrophos");
        healer_list.add("Omniknight");
        healer_list.add("Oracle");
        healer_list.add("Phoenix");
        healer_list.add("Treant Protector");
        healer_list.add("Warlock");
        healer_list.add("Winter Wyvern");
        healer_list.add("Witch Doctor");
        healer_list.add("Wraith King");
        
        extra_global_list.add("Ancient Apparition");
        extra_global_list.add("Bloodseeker");
        extra_global_list.add("Centaur Warrunner");
        extra_global_list.add("Chen");
        extra_global_list.add("Clockwerk");
        extra_global_list.add("Gyrocopter");
        extra_global_list.add("Invoker");
        extra_global_list.add("Io");
        extra_global_list.add("Keeper of the Light");
        extra_global_list.add("Nature's Prophet");
        extra_global_list.add("Mirana");
        extra_global_list.add("Silencer");
        extra_global_list.add("Spectre");
        extra_global_list.add("Spirit Breaker");
        extra_global_list.add("Treant Protector");
        extra_global_list.add("Troll Warlord");
        extra_global_list.add("Underlord");
        extra_global_list.add("Zeus");
        
        extra_mobile_list.add("Anti-Mage");
        extra_mobile_list.add("Batrider");
        extra_mobile_list.add("Bloodseeker");
        extra_mobile_list.add("Bounty Hunter");
        extra_mobile_list.add("Broodmother");
        extra_mobile_list.add("Centaur Warrunner");
        extra_mobile_list.add("Clinkz");
        extra_mobile_list.add("Clockwerk");
        extra_mobile_list.add("Dark Seer");
        extra_mobile_list.add("Earth Spirit");
        extra_mobile_list.add("Ember Spirit");
        extra_mobile_list.add("Faceless Void");
        extra_mobile_list.add("Invoker");
        extra_mobile_list.add("Io");
        extra_mobile_list.add("Magnus");
        extra_mobile_list.add("Mirana");
        extra_mobile_list.add("Monkey King");
        extra_mobile_list.add("Nature's Prophet");
        extra_mobile_list.add("Night Stalker");
        extra_mobile_list.add("Pangolier");
        extra_mobile_list.add("Phoenix");
        extra_mobile_list.add("Puck");
        extra_mobile_list.add("Queen of Pain");
        extra_mobile_list.add("Riki");
        extra_mobile_list.add("Sand King");
        extra_mobile_list.add("Slardar");
        extra_mobile_list.add("Slark");
        extra_mobile_list.add("Spectre");
        extra_mobile_list.add("Spirit Breaker");
        extra_mobile_list.add("Storm Spirit");
        extra_mobile_list.add("Timbersaw");
        extra_mobile_list.add("Tusk");
        extra_mobile_list.add("Underlord");
        extra_mobile_list.add("Weaver");
        extra_mobile_list.add("Windranger");
        
        extra_invisible_list.add("Bounty Hunter");
        extra_invisible_list.add("Broodmother");
        extra_invisible_list.add("Clinkz");
        extra_invisible_list.add("Invoker");
        extra_invisible_list.add("Mirana");
        extra_invisible_list.add("Nyx Assassin");
        extra_invisible_list.add("Riki");
        extra_invisible_list.add("Sand King");
        extra_invisible_list.add("Slark");
        extra_invisible_list.add("Techies");
        extra_invisible_list.add("Templar Assassin");
        extra_invisible_list.add("Treant Protector");
        extra_invisible_list.add("Weaver");
        
        extra_vision_list.add("Beastmaster");
        extra_vision_list.add("Bloodseeker");
        extra_vision_list.add("Bounty Hunter");
        extra_vision_list.add("Clockwerk");
        extra_vision_list.add("Invoker");
        extra_vision_list.add("Keeper of the Light");
        extra_vision_list.add("Mirana");
        extra_vision_list.add("Night Stalker");
        extra_vision_list.add("Puck");
        extra_vision_list.add("Slardar");
        extra_vision_list.add("Spirit Breaker");
        extra_vision_list.add("Techies");
        extra_vision_list.add("Treant Protector");
        extra_vision_list.add("Vengeful Spirit");
        extra_vision_list.add("Windranger");
        extra_vision_list.add("Zeus");
        
        lane_middle_list.add("Alchemist");
        lane_middle_list.add("Arc Warden");
        lane_middle_list.add("Batrider");
        lane_middle_list.add("Brewmaster");
        lane_middle_list.add("Clinkz");
        lane_middle_list.add("Dark Willow");
        lane_middle_list.add("Death Prophet");
        lane_middle_list.add("Doom");
        lane_middle_list.add("Dragon Knight");
        lane_middle_list.add("Ember Spirit");
        lane_middle_list.add("Grimstroke");
        lane_middle_list.add("Gyrocopter");
        lane_middle_list.add("Huskar");
        lane_middle_list.add("Invoker");
        lane_middle_list.add("Kunkka");
        lane_middle_list.add("Legion Commander");
        lane_middle_list.add("Leshrac");
        lane_middle_list.add("Lina");
        lane_middle_list.add("Magnus");
        lane_middle_list.add("Medusa");
        lane_middle_list.add("Meepo");
        lane_middle_list.add("Mirana");
        lane_middle_list.add("Morphling");
        lane_middle_list.add("Necrophos");
        lane_middle_list.add("Night Stalker");
        lane_middle_list.add("Outworld Devourer");
        lane_middle_list.add("Puck");
        lane_middle_list.add("Pudge");
        lane_middle_list.add("Pugna");
        lane_middle_list.add("Queen of Pain");
        lane_middle_list.add("Razor");
        lane_middle_list.add("Shadow Fiend");
        lane_middle_list.add("Silencer");
        lane_middle_list.add("Skywrath Mage");
        lane_middle_list.add("Slark");
        lane_middle_list.add("Storm Spirit");
        lane_middle_list.add("Templar Assassin");
        lane_middle_list.add("Timbersaw");
        lane_middle_list.add("Tinker");
        lane_middle_list.add("Tiny");
        lane_middle_list.add("Troll Warlord");
        lane_middle_list.add("Tusk");
        lane_middle_list.add("Venomancer");
        lane_middle_list.add("Viper");
        lane_middle_list.add("Warlock");
        lane_middle_list.add("Weaver");
        lane_middle_list.add("Windranger");
        lane_middle_list.add("Zeus");
        
        lane_offlane_list.add("Batrider");
        lane_offlane_list.add("Beastmaster");
        lane_offlane_list.add("Bounty Hunter");
        lane_offlane_list.add("Bristleback");
        lane_offlane_list.add("Broodmother");
        lane_offlane_list.add("Centaur Warrunner");
        lane_offlane_list.add("Clinkz");
        lane_offlane_list.add("Clockwerk");
        lane_offlane_list.add("Dark Seer");
        lane_offlane_list.add("Death Prophet");
        lane_offlane_list.add("Doom");
        lane_offlane_list.add("Earth Spirit");
        lane_offlane_list.add("Elder Titan");
        lane_offlane_list.add("Faceless Void");
        lane_offlane_list.add("Grimstroke");
        lane_offlane_list.add("Invoker");
        lane_offlane_list.add("Keeper of the Light");
        lane_offlane_list.add("Lich");
        lane_offlane_list.add("Lone Druid");
        lane_offlane_list.add("Magnus");
        lane_offlane_list.add("Mirana");
        lane_offlane_list.add("Nature's Prophet");
        lane_offlane_list.add("Night Stalker");
        lane_offlane_list.add("Nyx Assassin");
        lane_offlane_list.add("Pangolier");
        lane_offlane_list.add("Phantom Lancer");
        lane_offlane_list.add("Phoenix");
        lane_offlane_list.add("Puck");
        lane_offlane_list.add("Pudge");
        lane_offlane_list.add("Queen of Pain");
        lane_offlane_list.add("Razor");
        lane_offlane_list.add("Slardar");
        lane_offlane_list.add("Spirit Breaker");
        lane_offlane_list.add("Techies");
        lane_offlane_list.add("Tidehunter");
        lane_offlane_list.add("Timbersaw");
        lane_offlane_list.add("Treant Protector");
        lane_offlane_list.add("Tusk");
        lane_offlane_list.add("Underlord");
        lane_offlane_list.add("Undying");
        lane_offlane_list.add("Venomancer");
        lane_offlane_list.add("Viper");
        lane_offlane_list.add("Weaver");
        lane_offlane_list.add("Windranger");
        
        lane_safe_list.add("Abaddon");
        lane_safe_list.add("Alchemist");
        lane_safe_list.add("Ancient Apparition");
        lane_safe_list.add("Anti-Mage");
        lane_safe_list.add("Arc Warden");
        lane_safe_list.add("Bane");
        lane_safe_list.add("Beastmaster");
        lane_safe_list.add("Bloodseeker");
        lane_safe_list.add("Chaos Knight");
        lane_safe_list.add("Clinkz");
        lane_safe_list.add("Crystal Maiden");
        lane_safe_list.add("Dark Willow");
        lane_safe_list.add("Dazzle");
        lane_safe_list.add("Death Prophet");
        lane_safe_list.add("Disruptor");
        lane_safe_list.add("Doom");
        lane_safe_list.add("Dragon Knight");
        lane_safe_list.add("Drow Ranger");
        lane_safe_list.add("Earth Spirit");
        lane_safe_list.add("Earthshaker");
        lane_safe_list.add("Elder Titan");
        lane_safe_list.add("Ember Spirit");
        lane_safe_list.add("Faceless Void");
        lane_safe_list.add("Grimstroke");
        lane_safe_list.add("Gyrocopter");
        lane_safe_list.add("Huskar");
        lane_safe_list.add("Invoker");
        lane_safe_list.add("Io");
        lane_safe_list.add("Jakiro");
        lane_safe_list.add("Juggernaut");
        lane_safe_list.add("Keeper of the Light");
        lane_safe_list.add("Kunkka");
        lane_safe_list.add("Legion Commander");
        lane_safe_list.add("Leshrac");
        lane_safe_list.add("Lich");
        lane_safe_list.add("Lifestealer");
        lane_safe_list.add("Lina");
        lane_safe_list.add("Lion");
        lane_safe_list.add("Lone Druid");
        lane_safe_list.add("Luna");
        lane_safe_list.add("Lycan");
        lane_safe_list.add("Medusa");
        lane_safe_list.add("Meepo");
        lane_safe_list.add("Mirana");
        lane_safe_list.add("Monkey King");
        lane_safe_list.add("Morphling");
        lane_safe_list.add("Naga Siren");
        lane_safe_list.add("Necrophos");
        lane_safe_list.add("Night Stalker");
        lane_safe_list.add("Nyx Assassin");
        lane_safe_list.add("Ogre Magi");
        lane_safe_list.add("Omniknight");
        lane_safe_list.add("Oracle");
        lane_safe_list.add("Outworld Devourer");
        lane_safe_list.add("Pangolier");
        lane_safe_list.add("Phantom Assassin");
        lane_safe_list.add("Phantom Lancer");
        lane_safe_list.add("Pudge");
        lane_safe_list.add("Pugna");
        lane_safe_list.add("Razor");
        lane_safe_list.add("Riki");
        lane_safe_list.add("Sand King");
        lane_safe_list.add("Shadow Demon");
        lane_safe_list.add("Shadow Fiend");
        lane_safe_list.add("Shadow Shaman");
        lane_safe_list.add("Silencer");
        lane_safe_list.add("Skywrath Mage");
        lane_safe_list.add("Slardar");
        lane_safe_list.add("Slark");
        lane_safe_list.add("Sniper");
        lane_safe_list.add("Spectre");
        lane_safe_list.add("Storm Spirit");
        lane_safe_list.add("Sven");
        lane_safe_list.add("Techies");
        lane_safe_list.add("Templar Assassin");
        lane_safe_list.add("Terrorblade");
        lane_safe_list.add("Tiny");
        lane_safe_list.add("Treant Protector");
        lane_safe_list.add("Troll Warlord");
        lane_safe_list.add("Tusk");
        lane_safe_list.add("Underlord");
        lane_safe_list.add("Ursa");
        lane_safe_list.add("Vengeful Spirit");
        lane_safe_list.add("Venomancer");
        lane_safe_list.add("Viper");
        lane_safe_list.add("Visage");
        lane_safe_list.add("Warlock");
        lane_safe_list.add("Weaver");
        lane_safe_list.add("Windranger");
        lane_safe_list.add("Winter Wyvern");
        lane_safe_list.add("Witch Doctor");
        lane_safe_list.add("Wraith King");
        
        lane_jungle_list.add("Axe");
        lane_jungle_list.add("Bloodseeker");
        lane_jungle_list.add("Chen");
        lane_jungle_list.add("Crystal Maiden");
        lane_jungle_list.add("Doom");
        lane_jungle_list.add("Enchantress");
        lane_jungle_list.add("Enigma");
        lane_jungle_list.add("Legion Commander");
        lane_jungle_list.add("Lifestealer");
        lane_jungle_list.add("Lone Druid");
        lane_jungle_list.add("Lycan");
        lane_jungle_list.add("Nature's Prophet");
        lane_jungle_list.add("Sand King");
        lane_jungle_list.add("Wraith King");
        
        lane_roam_list.add("Bounty Hunter");
        lane_roam_list.add("Clinkz");
        lane_roam_list.add("Earth Spirit");
        lane_roam_list.add("Enchantress");
        lane_roam_list.add("Mirana");
        lane_roam_list.add("Night Stalker");
        lane_roam_list.add("Nyx Assassin");
        lane_roam_list.add("Pudge");
        lane_roam_list.add("Riki");
        lane_roam_list.add("Spirit Breaker");
        lane_roam_list.add("Techies");
        lane_roam_list.add("Timbersaw");
        lane_roam_list.add("Tusk");
        lane_roam_list.add("Weaver");
        
    }
    
}
