package br.com.dio.persistence;

import java.io.*;

public class IOFilePersistence implements FilePercistence {

    private final String currentDir = System.getProperty("user.dir");

    private final String storedDir = "/managedFiles/IO/";

    private final String fileName;

    public IOFilePersistence(String fileName) throws IOException {
        this.fileName = fileName;
        var file = new File(currentDir + storedDir);
        if(!file.exists() && !file.mkdirs())throw new IOException("erro ao cirar arquivo");
        clearFile();
    }

    @Override
    public String write(String data) {
        try (
                var fileWriter = new FileWriter(currentDir + storedDir + fileName , true);
                var bufferdwrite = new BufferedWriter(fileWriter);
                var printWriter = new PrintWriter(bufferdwrite)
        ){
            printWriter.println(data);
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return data;
    }

    @Override
    public boolean remove(String sentence) {
        return false;
    }

    @Override
    public String replace(String oldContent, String newContent) {
        return "";
    }

    @Override
    public String findAll() {
        return "";
    }

    @Override
    public String findBy(String sentense) {
        return "";
    }

    private void clearFile(){
        try (OutputStream outputStream = new FileOutputStream(currentDir + storedDir + fileName);){
            System.out.printf("inicializando recursos (%s)", currentDir + storedDir + fileName);
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

}
