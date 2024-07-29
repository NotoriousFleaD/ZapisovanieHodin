import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.util.ArrayList;

public class ZapisHodin{
  private String datum;
  private ArrayList hra, casStravenyHry; 
  private LocalTime zaciatokCasu2, koniecCasu2, stravenyCas;
  private String[][] hry_a_cas;
  
  public ZapisHodin(){
    Scanner sc = new Scanner(System.in);
    hra = new ArrayList<String>();
    
    datum = zistenieDatumu();
    
    String zaciatokCasu = "";
    boolean cyklus = true;
    do {
      System.out.print("V aky cas si prisiel: ");
      zaciatokCasu = sc.nextLine();
      
      if(zaciatokCasu.length() != 5){
        zaciatokCasu = opravaCasu(zaciatokCasu);
        
        cyklus = kontrolaCasu(zaciatokCasu);
      }else{
        cyklus = kontrolaCasu(zaciatokCasu);
      }
    } while (cyklus);
    zaciatokCasu2 = LocalTime.parse(zaciatokCasu);
    
    String koniecCasu = "";
    cyklus = true;
    do {
      System.out.print("V aky cas si odisiel: ");
      koniecCasu = sc.nextLine();
      
      if(koniecCasu.length() != 5){
        koniecCasu = opravaCasu(koniecCasu);
        
        cyklus = kontrolaCasu(koniecCasu);
      }else{
        cyklus = kontrolaCasu(koniecCasu);
      }
    } while (cyklus);
    koniecCasu2 = LocalTime.parse(koniecCasu);
    
    stravenyCas = vypocetStravenehoCasu(zaciatokCasu2, koniecCasu2);
    
    LocalTime zostavajuciCas = stravenyCas;
    
    System.out.print("Zadaj nazov hry: ");
    hra.add(sc.nextLine());
    
    System.out.print("Zadaj kolko casu si odohral v " + hra.get(0) + ": ");
    String casHra = sc.nextLine();
    
    LocalTime premennaCas = premenaNaLocalTime(zostavajuciCas, casHra);
    
    hry_a_cas = new String[1][2];
    //tu som skoncil, debata nema reci 
    hry_a_cas[0][0] = "" + hra.get(0);
    hry_a_cas[0][1] = "" + String.valueOf(premennaCas);
    
    //    zostavajuciCas = zostavajuciCas.of(zostavajuciCas.getHour() - premennaCas.getHour(), zostavajuciCas.getMinute() - premennaCas.getMinute());
    zostavajuciCas = zostavajuciCas.minusHours(premennaCas.getHour());
    zostavajuciCas = zostavajuciCas.minusMinutes(premennaCas.getMinute());
    
    //    switch(hra.size()){
    //      case 1:
    //        hry_a_cas = new String[1][2];
    //        
    //        hry_a_cas[0][0] = "" + hra.get(0);
    //        hry_a_cas[0][0] = String.valueOf(stravenyCas);
    //        break;
    //        
    //      default:
    //        do{
    //          hry_a_cas = new String[hra.size()][2];
    //          
    //          // Inicializacia hier do pola
    //          for(int i = 0; i < hra.size(); i++){
    //            hry_a_cas[i][0] = "" + hra.get(i);  //<<< nechaapem
    //          }
    //          
    //        
    //        }while(zostavajuciCas.getHour() != 0 && zostavajuciCas.getMinute() != 0);
    //    }
    
    
    
    
    
  }
  
  public ZapisHodin(String datum, LocalTime zaciatokCasu, LocalTime koniecCasu, ArrayList hra, ArrayList casStravenyHry){
    this.datum = datum;
    this.zaciatokCasu2 = zaciatokCasu;
    this.koniecCasu2 = koniecCasu;
    this.hra = new ArrayList<String>(hra);
    this.casStravenyHry = new ArrayList<LocalTime>(casStravenyHry);
    
    stravenyCas = vypocetStravenehoCasu(zaciatokCasu2, koniecCasu2);
  }
  
//  public ZapisHodin(String datum, LocalTime zaciatokCasu, LocalTime koniecCasu, String hra){
//    this.datum = datum;
//    this.zaciatokCasu2 = zaciatokCasu;
//    this.koniecCasu2 = koniecCasu;
//    this.hry = hra;
//    
//    stravenyCas = vypocetStravenehoCasu(zaciatokCasu2, koniecCasu2);
//  }
  
  public String opravaCasu(String cas){
    return "0" + cas;
  }
  
  public boolean kontrolaCasu(String cas){
    char[] pole = cas.toCharArray();
    char[] charCas = new char[2];
    int intCas = 0;
    
    //Hodiny
    for(int i = 0; i < 2; i++){
      charCas[i] = pole[i];
    }
    
    intCas = Integer.valueOf(String.valueOf(charCas));
    
    if(intCas < 0 || intCas > 23){
      System.out.println("Tak si kokot?");
      return true;
    }else {
      
      //Minuty
      for(int i = 3; i < 5; i++){
        charCas[i - 3] = pole[i];
      }
      
      intCas = Integer.valueOf(String.valueOf(charCas));
      
      if(intCas < 0 || intCas > 59){
        System.out.println("Tak si kokot?");
        return true;
      }else {
        return false;
      }
    }
  }

  public LocalTime vypocetStravenehoCasu(LocalTime cas1, LocalTime cas2){
    char[] pole = String.valueOf(cas1).toCharArray();
    char[] pole2 = String.valueOf(cas2).toCharArray();
    char[] charCas1 = new char[2];
    char[] charCas2 = new char[2];
    LocalTime cas = LocalTime.now(); // Bola potrebna inicializacia, kazdopadne je to totalna blbost
    cas = cas.of(00, 00, 00); // Vynulovane hodnoty
    
    //Porovnanie s hodinami
    for(int i = 0; i < 2; i++){
      charCas1[i] = pole[i];
    }
    
    for(int i = 0; i < 2; i++){
      charCas2[i] = pole2[i];
    }
    
    int hodiny = Integer.valueOf(String.valueOf(charCas2)) - Integer.valueOf(String.valueOf(charCas1));
    
    if(hodiny < 0){
      System.out.println("Prespavame v praci?");
      hodiny = 24 + hodiny;
    }
    
    //Porovnanie s minutami
    for(int i = 3; i < 5; i++){
      charCas1[i - 3] = pole[i];
    }
    
    for(int i = 3; i < 5; i++){
      charCas2[i - 3] = pole2[i];
    }
    
    int minuty = Integer.valueOf(String.valueOf(charCas2)) - Integer.valueOf(String.valueOf(charCas1));
    
    if(minuty < 0){
      minuty = 60 + minuty;
      hodiny--;
    }
    
    return cas.of(hodiny, minuty);
  }
  
  public String zistenieDatumu(){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM");
    LocalDate curDate = LocalDate.now();
    return formatter.format(curDate);
  }
  
//  public void vypisHierSCasom(){
//    
//  }

  public LocalTime premenaNaLocalTime(LocalTime cas, String hodnotaCasu){
    char[] pole1 = new char[2]; 
    char[] pole2 = hodnotaCasu.toCharArray();
    int i = 0;
    
    pole1 = vynulovaniePola(pole1, 2);
    for (i = 0; pole2[i] != 'h'; i++) {
      char buffer = pole1[0];
      pole1[0] = pole1[1];
      pole1[1] = pole1[0];
 
      pole1[1] = pole2[i];
    }

    int c = Integer.parseInt(String.valueOf(pole1));
    cas = cas.minusHours(c);
    
    
    System.out.println("pred>" + cas.getMinute());
    
    
    i+=2; // aby sa prekocila medzera
    pole1 = vynulovaniePola(pole1, 2);
    for (int j = i; pole2[j] != 'm'; j++ ) {
      char buffer = pole1[0];
      pole1[0] = pole1[1];
      pole1[1] = pole1[0];
 
      pole1[1] = pole2[j];
      System.out.println(pole1);
    }
    cas = cas.minusMinutes(Integer.valueOf(String.valueOf(pole1)));
    System.out.println("po>" + cas.getMinute());

    System.out.println("KONIEC");
    return cas;
  }

  public char[] vynulovaniePola(char[] pole, int velkost){
    for(int i = 0; i < velkost; i++){
      pole[i] = '0';
    }
    
    return pole;
  }

  
  
  public String toString(){
    return "Datum: " + datum + "\nZaciatok prace: " + zaciatokCasu2 + "\nKoniec prace: " + koniecCasu2 + "\nHry: " + getHry() + "\nCelkovy straveny cas v praci: " + stravenyCas + " --> " + stravenyCas.getHour() + "h " + stravenyCas.getMinute() + "min" + "\nID: " + super.toString();
  }
  
  public void info(){
    System.out.println(toString());
  }
  
  

  public void setDatum(String datum){ //[DD.MM] » [03.10]
    this.datum = datum;
  }
  
  public String getDatum(){
    return datum;
  }
  
  //---
  public void setHra(String hra, int index){
    this.hra.set(index, hra);
  }
  
  public void addHra(String hra){
    this.hra.add(hra);
  }
  
  public String getHry(){
    String hry = "";
    for(int i = 0; i < hra.size(); i++){
      switch(i){
        case 0:
          hry = "" + hra.get(i); //Nechaapem
          break;
        default:
          hry = hry + ", " + hra.get(i);
      }
    }
    return hry; //Bolo potrebne vratit String hodnotu, kvoli toString() metode
  }
  //---

  public void setZaciatokCasu(String cas){ //[MM:SS] » [12:55]
    this.zaciatokCasu2 = LocalTime.parse(cas);
  }

  public String getZaciatokCasu(){
    return String.valueOf(zaciatokCasu2);
  }
  
  
  public void setKoniecCasu(String cas){ //[MM:SS] » [12:55]
    this.koniecCasu2 = LocalTime.parse(cas);
  }

  public String getKoniecCasu(){
    return String.valueOf(koniecCasu2);
  }
    
}
