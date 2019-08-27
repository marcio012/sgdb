package website.marcioheleno;

import lombok.extern.java.Log;
import website.marcioheleno.service.Leitura;

@Log
public class App {

    public static void main(String[] args) {
        log.info("Teste");
        Leitura leitura = new Leitura();
        leitura.criaContainers();

        //GerenciadorBuffer.geraRequisicoes(leitor.container);
    }
}
