package website.marcioheleno;

import lombok.extern.java.Log;
import website.marcioheleno.service.GerenciadorBuffer;
import website.marcioheleno.service.Leitura;

import java.io.IOException;

@Log
public class App {

    public static void main(String[] args) {
        Leitura leitura = new Leitura();
        leitura.criaContainers();

//        GerenciadorBuffer.geraRequisicoes(leitura.criaContainers().);

    }
}
