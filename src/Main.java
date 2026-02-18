import br.com.dio.persistence.FilePercistence;
import br.com.dio.persistence.IOFilePersistence;
import br.com.dio.persistence.NIOFilePersistence;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FilePercistence percistence = new NIOFilePersistence("user.csv");

        System.out.println(percistence.write("eu;eu@eu;22/09/1997"));
        System.out.println("=========================");
        System.out.println(percistence.write("ele;ele@ele; 28/11/1999"));
        System.out.println("=========================");
        System.out.println(percistence.write("tu;tu@tu;12/19/2000"));

        System.out.println("=========================");
        System.out.println(percistence.findAll());
        System.out.println("=========================");

        System.out.println(percistence.remove("ele@"));
        System.out.println("=========================");

        System.out.println(percistence.findBy("tu"));
        System.out.println("=========================");
        System.out.println(percistence.findBy("ele@"));
        System.out.println("=========================");
        System.out.println(percistence.replace("tu" , "nos;nos@nos.com;17/10/1988"));
        System.out.println("=========================");
        System.out.println(percistence.replace("eu" , "vos;vos@vos.com;17;01;2000"));
        System.out.println("=========================");

    }
}
