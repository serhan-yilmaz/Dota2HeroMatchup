/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dota2heromatchup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 *
 * @author Serhan
 */
public class DotabuffParser {
//    private final static String QUERY_DATE = "patch_7.20";
    private final static String QUERY_DATE = "week";
    
    private final static String DELIM_0 = "data-link-to=";
    private final static String DELIM_1 = "data-value=\"";
    private final static String DELIM_2 = "\"";
    private final static String DELIM_3 = "href=\"/heroes/";
    private final static String DELIM_4 = "td data-value=\"";
    
    private ArrayList<Hero> hero_list = new ArrayList<>();
    private MatchupData matchup_data;
    protected volatile double progress;
    private long initial_time;
    private long checkpoint_time;
    private double checkpoint_estimate;
    
    public DotabuffParser(){
        try {
            /* Start of Fix */
            TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
                public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                public void checkServerTrusted(X509Certificate[] certs, String authType) { }
                
            } };
            
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            
            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) { return true; }
            };
            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
            /* End of the fix*/
        } catch (KeyManagementException ex) {
            Logger.getLogger(DotabuffParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DotabuffParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void parse() throws IOException, ParsingException{
        initial_time = System.nanoTime();
        calculateEstimatedTime();
//        BufferedReader in = establishConnection("http://www.google.com");
        BufferedReader in = establishConnection("https://www.dotabuff.com/heroes/winning?date=" + QUERY_DATE);
//        System.out.println(in.ready());
        progress = 0;
        String line = readLongestLine(in);
        if(line == null){
            throw new ParsingException("ParseHero line is null.");
        }
//        System.out.println(line);
        acquireHeroList(line);
        matchup_data = new MatchupData(hero_list);
        calculateEstimatedTime();

        for(int i = 0; i < hero_list.size(); i++){
            parseHero(i);
            progress = 100 * (i + 2.0) / ( hero_list.size() + 1);
            System.err.println("Progress : %" + progress);
            calculateEstimatedTime();
        }
    }
    public MatchupData getMatchupData(){
        return matchup_data;
    }
    
    private void parseHero(int index) throws IOException, ParsingException{
        Hero hero = hero_list.get(index);
        String url = "https://www.dotabuff.com/heroes/" + hero.link + "/counters?date=" + QUERY_DATE;
        BufferedReader in = establishConnection(url);
        String line = readLongestLine(in);
        if(line == null){
            throw new ParsingException("ParseHero line is null.");
        }
//        System.out.println(line);
//        int iterator = 0;
//        int state = 0;
//        int hero_id = -1;
        StringParser sp = new StringParser(line);
        sp.parseUntil(">Matchups<");
//        System.out.println(sp.getIterator());
        int startIndex = sp.getIterator();
        int endIndex = startIndex + 100;
//        System.out.println(line.substring(sp.getIterator()));
//        System.out.println(line.substring(startIndex, endIndex));
        while(!sp.isCompleted()){
            sp.parseUntil(DELIM_1);
            String hero_link = sp.parseUntil(DELIM_2);
//            System.out.println(hero_link);
            int hero_id = getHeroID(hero_link);
            sp.parseUntil(DELIM_3);
            sp.parseUntil(DELIM_2);
            sp.parseUntil(DELIM_4);
            sp.parseUntil(DELIM_4);
            String hero_wr = sp.parseUntil(DELIM_2);
//            System.out.println(hero_wr);
            matchup_data.win_rate[index][hero_id] = string2double(hero_wr);
            sp.parseUntil(DELIM_4);
            String hero_nm = sp.parseUntil(DELIM_2);
//            System.out.println(hero_nm);
            matchup_data.match_amount[index][hero_id] = string2double(hero_nm);
            sp.parseUntil(DELIM_0);
        }
        
//        CharParser lp = new CharParser();
//        lp.setTarget(DELIM_1);
//       while(iterator < line.length()){
//            char c = line.charAt(iterator);
//            lp.parse(c);
//            if(lp.isTargetReached()){
//                switch(state){
//                    case 0 :
//                        lp.record();
//                        lp.setTarget(DELIM_2);
//                        state++;
//                        break;
//                    case 1 :
//                        String s1 = lp.stopRecording();
//                        hero_id = getHeroID(s1);
//                        state++;
//                        lp.setTarget(DELIM_3);
//                        break;
//                    case 2 :
//                        lp.record();
//                        lp.setTarget(DELIM_2);
//                        state++;
//                        break;
//                    case 3 :
//                        String s3 = lp.stopRecording();
//                        lp.setTarget(DELIM_4);
//                        state++;
//                        break;
//                    case 4 :
//                        lp.record();
//                        lp.setTarget(DELIM_2);
//                        state++;
//                        break;
//                    case 5 :
//                        String s5 = lp.stopRecording();
//                        double d5 = string2double(s5);
//                        matchup_data.advantage[index][hero_id] = d5;
//                        state++;
//                        lp.setTarget(DELIM_4);
//                        break;
//                    case 6 :
//                        lp.record();
//                        lp.setTarget(DELIM_2);
//                        state++;
//                        break;
//                    case 7 :
//                        String s7 = lp.stopRecording();
//                        double d7 = string2double(s7);
//                        matchup_data.win_rate[index][hero_id] = d7;
//                        lp.setTarget(DELIM_4);
//                        state++;
//                        break;
//                    case 8 : 
//                        lp.record();
//                        lp.setTarget(DELIM_2);
//                        state++;
//                        break;
//                    case 9 :
//                        String s9 = lp.stopRecording();
//                        double d9 = string2double(s9);
//                        matchup_data.match_amount[index][hero_id] = d9;
//                        lp.setTarget(DELIM_1);
//                        state = 0;
//                        break;
//                }
//            }
//            iterator++;
//        }
    }
    private void acquireHeroList(String line){
        int iterator = 0;
        int state = 0;
        CharParser lp = new CharParser();
        lp.setTarget(DELIM_1);
        Hero hero = null;
        while(iterator < line.length()){
            char c = line.charAt(iterator);
            //System.out.print(c);
            lp.parse(c);
            if(lp.isTargetReached()){
                switch(state){
                    case 0 :
                        lp.record();
                        lp.setTarget(DELIM_2);
                        state++;
                        break;
                    case 1 :
                        String s1 = lp.stopRecording();
                        hero = new Hero(s1);
                        hero_list.add(hero);
                        //System.out.println(hero.name);
                        state++;
                        lp.setTarget(DELIM_3);
                        break;
                    case 2 :
                        lp.record();
                        lp.setTarget(DELIM_2);
                        state++;
                        break;
                    case 3 :
                        String s3 = lp.stopRecording();
                        hero.link = s3;
                        lp.setTarget(DELIM_4);
                        state++;
                        break;
                    case 4 :
                        lp.record();
                        lp.setTarget(DELIM_2);
                        state++;
                        break;
                    case 5 :
                        String s5 = lp.stopRecording();
                        double d5 = string2double(s5);
                        hero.win_rate = d5;
                        state++;
                        lp.setTarget(DELIM_4);
                        break;
                    case 6 :
                        lp.record();
                        lp.setTarget(DELIM_2);
                        state++;
                        break;
                    case 7 :
                        String s7 = lp.stopRecording();
                        double d7 = string2double(s7);
                        hero.pick_rate = d7;
                        lp.setTarget(DELIM_4);
                        state++;
                        break;
                    case 8 : 
                        lp.record();
                        lp.setTarget(DELIM_2);
                        state++;
                        break;
                    case 9 :
                        String s9 = lp.stopRecording();
                        double d9 = string2double(s9);
                        hero.kda = d9;
                        lp.setTarget(DELIM_1);
                        state = 0;
                        break;
                }
            }
            iterator++;
        }
        orderHeroList();
        for(Hero h : hero_list){
            h.print();
        }
        System.out.println("Hero amount : " + hero_list.size());
    }
    
    private BufferedReader establishConnection(String url) throws MalformedURLException, IOException{
        
        URL oracle = new URL(url);
        URLConnection openConnection = oracle.openConnection();
        openConnection.addRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0");
//        openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");        
//        System.out.println(openConnection.getInputStream().available() + " a");
        return new BufferedReader(new InputStreamReader(openConnection.getInputStream()));
    }
    
    private String readLongestLine(BufferedReader in) throws IOException{
        in.mark(0);
        in.reset();
        String inputLine;
        String maxLine = null;
        while ((inputLine = in.readLine()) != null)
        {
            if(maxLine == null || maxLine.length() < inputLine.length())
                maxLine = inputLine;
        }
        return maxLine;
    }
    
    private double string2double(String number){
        double value = Double.parseDouble( number.replace(",",".") );
        return value;
    }
    private void orderHeroList(){
        Collections.sort(hero_list, new HeroComparator());
        for(int i = 0; i < hero_list.size(); i++){
            hero_list.get(i).id = i;
        }
    }
    private int getHeroID(String name) throws ParsingException{
        for(int i = 0; i < hero_list.size(); i++){
            if(hero_list.get(i).name.equals(name))
                return i;
        }
        throw new ParsingException("Hero not found : " + name);
    }
    
    private double calculateEstimatedTime(){
        checkpoint_time = System.nanoTime();
        long current = System.nanoTime();
        double value = (current - initial_time) * 1e-9 * (100 - progress) / progress;
        checkpoint_estimate = value;
        return value;
    }
    protected double getEstimatedTime(){
        long current = System.nanoTime();
        double value = checkpoint_estimate - (current - checkpoint_time) * 1e-9;
        if(value < 0)
            value = 0;
        return value;
    }
}
