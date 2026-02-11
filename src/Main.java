import br.com.dio.persistence.FilePercistence;
import br.com.dio.persistence.IOFilePersistence;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FilePercistence percistence = new IOFilePersistence("user.csv");
        System.out.println(percistence.write("eu;eu@eu.com;15/01/2025"));
    }
}
