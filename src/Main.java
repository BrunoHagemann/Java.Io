import br.com.dio.persistence.FilePercistence;
import br.com.dio.persistence.IOFilePersistence;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FilePercistence percistence = new IOFilePersistence("user.csv");
        System.out.println("======================================");
        System.out.println(percistence.write("eu;eu@eu.com;15/01/2025"));
        System.out.println("======================================");
        System.out.println(percistence.write("ele;ele@ele.com;25/12/2020"));
        System.out.println("======================================");
        System.out.println(percistence.write("tu;tu@tu.com;17/06/2024"));
        System.out.println("======================================");

        System.out.println(percistence.findAll());

        System.out.println("======================================");

        System.out.println(percistence.remove("tu"));

        System.out.println("======================================");
        System.out.println(percistence.findBy("ele"));
        System.out.println("======================================");
        System.out.println(percistence.findBy("tu@"));
        System.out.println("======================================");
        System.out.println(percistence.findBy("15"));
        System.out.println("======================================");
        System.out.println(percistence.replace(".com;15/01/", "Carlos;Carlos@carlo.com;23/04/1991"));
        System.out.println("======================================");
        System.out.println(percistence.findAll());


    }
}
