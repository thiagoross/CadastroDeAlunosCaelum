package br.com.caelum.cadastro.support;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by thiago on 2/20/16.
 */
public class WebClient {

    private static String SERVER_URL = "https://www.caelum.com.br/mobile";

    public String post(String json) throws IOException {
        URL objUrl = new URL(SERVER_URL);
        HttpURLConnection conexao = (HttpURLConnection)objUrl.openConnection();

        conexao.setRequestProperty("Content-type", "application/json");
        conexao.setRequestProperty("Accept", "application/json");

        conexao.setDoOutput(true);

        OutputStream os = conexao.getOutputStream();

        PrintStream output = new PrintStream(os);
        output.println(json);
        conexao.connect();

        InputStream is = conexao.getInputStream();
        Scanner input = new Scanner(is);
        String resposta = input.next();

        return resposta;
    }



}
