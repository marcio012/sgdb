package website.marcioheleno;

import lombok.extern.java.Log;
import website.marcioheleno.service.Leitura;

import java.io.IOException;

@Log
public class App {

    public static void main(String[] args) {
        Leitura leitura = new Leitura();
        try {
            leitura.criaContainers();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
