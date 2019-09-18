package me.largetimmo.comp4004.a1.service.bo;

import lombok.Data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;


@Data
public class Connection {

    private Socket socket;

    private BufferedWriter writer;

    private BufferedReader reader;

    public void send(String data) throws IOException {
        writer.write(data);
        writer.newLine();
        writer.flush();
    }

    public boolean hasData() throws IOException {
        return reader.ready();
    }


    public String receive() throws IOException {
        return reader.readLine();
    }
}
