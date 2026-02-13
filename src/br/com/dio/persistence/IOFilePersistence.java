package br.com.dio.persistence;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
        var contentList = toListString();

        if(contentList.stream().noneMatch(c -> c.contains(sentence))) return false;
        clearFile();
        contentList.stream().filter(c -> !c.contains(sentence)).forEach(this::write);

        return false;
    }

    @Override
    public String replace(String oldContent, String newContent) {
        var contentList = toListString();

        if(contentList.stream().noneMatch(c -> c.contains(oldContent))) return "";

        clearFile();
        contentList.stream().map(c -> c.contains(oldContent) ? newContent : c).forEach(this::write);
        return newContent;
    }


    @Override
    public String findAll() {
        var content = new StringBuilder();
        try (var reader = new BufferedReader(new FileReader(currentDir + storedDir + fileName))){

            String line;
            do {
                line = reader.readLine();
                if((line != null)) content.append(line)
                        .append(System.lineSeparator());

            }while(line !=null);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    @Override
    public String findBy(String sentense) {
        var found= "";
        try (var reader = new BufferedReader(new FileReader(currentDir + storedDir + fileName))){
            String line = reader.readLine();
            while (line != null){
                if ((line.contains(sentense))){
                    found = line;
                    break;
                }
                line = reader.readLine();
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return found;
    }

    private void clearFile(){
        try (OutputStream outputStream = new FileOutputStream(currentDir + storedDir + fileName);){

        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private List<String> toListString() {
        var content = findAll();
        return new ArrayList<>(Stream.of(content.split(System.lineSeparator())).toList());

    }

}
