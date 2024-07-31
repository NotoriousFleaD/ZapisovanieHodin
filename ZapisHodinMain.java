import java.io.FileWriter;
import java.io.IOException;

public class ZapisHodinMain{
  public static void main(String[] arg){
    ZapisHodin zapis = new ZapisHodin();
    zapis.info();
    System.out.println(zapis.vypisHierSCasom());
    
    try {
      FileWriter myWriter = new FileWriter("hodiny.txt");
      myWriter.write(zapis.getDatum() + " - " + zapis.premenaNaStringTime(zapis.getStravenyCas()) + " | " + zapis.vypisHierSCasom());
      myWriter.close();
      System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}