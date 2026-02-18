package br.com.dio.persistence;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class NIOFilePersistence implements FilePercistence{


    private final String currentDir = System.getProperty("user.dir");

    private final String storedDir = "/managedFiles/NIO/";

    private final String fileName;

    public NIOFilePersistence(String fileName) throws IOException {
        this.fileName = fileName;
        var file = new File(currentDir + storedDir);
        if(!file.exists() && !file.mkdirs())throw new IOException("erro ao cirar arquivo");
        clearFile();
    }

    @Override
    public String write(String data) {
        try (
                var file = new RandomAccessFile(new File(currentDir+ storedDir + fileName),"rw")
        ){
            file.seek(file.length());
            file.writeBytes(data);
            file.writeBytes(System.lineSeparator());

        }catch (IOException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean remove(String sentence) {
        var contntList = toListString();

        if (contntList.stream().noneMatch(c->c.contains(sentence))) return false;

        clearFile();
        contntList.stream().filter(c->!c.contains(sentence)).forEach(this:: write);
        return true;
    }

    @Override
    public String replace(String oldContent, String newContent) {
        var contntList = toListString();

        if (contntList.stream().noneMatch(c->c.contains(oldContent))) return "";

        clearFile();
        contntList.stream().map(c->c.contains(oldContent)? newContent : c).forEach(this:: write);
        return newContent;
    }

    @Override
    public String findAll() {
        var content = new StringBuilder();
        try (
                var file = new RandomAccessFile(new File(currentDir+ storedDir + fileName), "r");
                var channel = file.getChannel();
        ){
            var buffer = ByteBuffer.allocate(256);
            var bytesReader = channel.read(buffer);
            while (bytesReader != -1){
                buffer.flip();
                while (buffer.hasRemaining()){
                    content.append((char) buffer.get());
                }
                buffer.clear();
                bytesReader = channel.read(buffer);
            }

        }catch (IOException ex){
            ex.printStackTrace();
        }
        return content.toString();
    }

    @Override
    public String findBy(String sentense) {
        var content = new StringBuilder();
        try (
                var file = new RandomAccessFile(new File(currentDir+ storedDir + fileName), "r");
                var channel = file.getChannel();
        ){
            var buffer = ByteBuffer.allocate(256);
            var bytesReader = channel.read(buffer);
            while (bytesReader != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    while (!content.toString().endsWith(System.lineSeparator())) {
                        content.append((char) buffer.get());
                    }
                    if (content.toString().contains(sentense)) {
                        break;
                    } else {
                        content.setLength(0);
                    }
                    if (!content.isEmpty()) break;
                }
                buffer.clear();
                bytesReader = channel.read(buffer);

            }

        }catch (IOException ex){
            ex.printStackTrace();
        }
        return content.toString();
    }

    private List<String> toListString() {
        var content = findAll();
        return new ArrayList<>(Stream.of(content.split(System.lineSeparator())).toList());

    }

    private void clearFile(){
        try (OutputStream outputStream = new FileOutputStream(currentDir + storedDir + fileName);){

        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

}
