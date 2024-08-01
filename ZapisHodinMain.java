import java.io.FileWriter;
import java.io.IOException;

public class ZapisHodinMain{
  public static void main(String[] arg){
    ZapisHodin zapis = new ZapisHodin();
    zapis.info();
    
    try {
      FileWriter myWriter = new FileWriter("hodiny.txt", true);
      myWriter.write(zapis.getDatum() + " - " + zapis.premenaNaStringTime(zapis.getStravenyCas()) + " | " + zapis.vypisHierSCasom() + "\n");
      myWriter.close();
      System.out.println("Uspesne zapisane do suboru.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}