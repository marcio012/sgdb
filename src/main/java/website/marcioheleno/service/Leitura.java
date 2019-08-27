package website.marcioheleno.service;

import lombok.extern.java.Log;
import website.marcioheleno.model.bloco.container.Container;
import website.marcioheleno.model.bloco.dados.Bloco;
import website.marcioheleno.model.bloco.tupla.Tupla;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

@Log
public class Leitura {

    public static List<Container> containers = new ArrayList<Container>();

    public void criaContainers() {
        String arquivo1 = "forn-tpch.txt";
//        String arquivo2 = "../resources/data/meu-tpch.txt";

        log.info("local arquivo: " + arquivo1);
//        log.info("local arquivo: " + arquivo2);


        iniciarLeitura(arquivo1);
//


        System.out.println("Containers criados");

        System.out.println("/n/n/n");
        System.out.println("TESTE BUFFER/n/n/n");
        GerenciadorBuffer.geraRequisicoes();
//        Gravador.exportaArquivoTxt(containers);

    }

    void iniciarLeitura(String arquivoCaminho) {
        System.out.println("Iniciando leitura do arquivo...");

        try {
            RandomAccessFile arquivo = new RandomAccessFile(arquivoCaminho, "rw");

            String linha = arquivo.readLine();

            Container container = new Container(linha);
            System.out.println("Gerado Bloco de Controle");

            while ((linha = arquivo.readLine()) != null) {
                adicionarTupla(Tupla.montaTuplaByte(separador(linha)), container);
            }

            arquivo.close();
            containers.add(container);

        } catch (IOException | NullPointerException exp) {
            exp.printStackTrace();
        }
    }


    void leituraEGravacao(String arquivoCaminho, String arquivoDestino) {
        System.out.println("Iniciando leitura e gravação do arquivo...");

        try {
            RandomAccessFile arquivoLeitura = new RandomAccessFile(arquivoCaminho, "rw");
            RandomAccessFile arquivoEscrita = new RandomAccessFile(arquivoCaminho, "rw");

            String linhaLida = arquivoLeitura.readLine();


            Container container = new Container(linhaLida);
            System.out.println("Gerado Bloco de Controle");

            while ((linhaLida = arquivoLeitura.readLine()) != null) {
                adicionarTupla(Tupla.montaTuplaByte(separador(linhaLida)), container);
                arquivoEscrita.write(linhaLida.getBytes());

            }

            arquivoLeitura.close();
            containers.add(container);

        } catch (IOException | NullPointerException exp) {
            exp.printStackTrace();
        }
    }

    void adicionarTupla(byte[] tupla, Container container) {
        int idBlocoLivre = Bloco.byteToInt(Bloco.getBytes(container.getControle().getDados(), 5, 3));
        //se nao exitir bloco, deve ser criado
        if (idBlocoLivre == 0) {
            Bloco novo = new Bloco(1, container.getContainerId());
            System.out.println("Gerado bloco de ID: " + 1);
            novo.adicionarTuplaNoBloco(tupla);
            container.getBlocos().add(novo);
            container.atualizaIdLivreControle(1);
        } else { //bloco maior que tamanho da tupla
            if (container.tamanhoDoBloco() - container.getBlocoId(idBlocoLivre).getTamanhoBloco() > tupla.length) {
                System.out.println("Salvou tupla no bloco: " + idBlocoLivre);
                System.out.println("idmaiorq" + idBlocoLivre + "tamanho" + container.getBlocoId(idBlocoLivre).getTamanhoBloco());
                container.getBlocoId(idBlocoLivre).adicionarTuplaNoBloco(tupla);
            } else { //bloco menor que tamanho da tupla
                System.out.println("idmenorq" + idBlocoLivre + "tamanho" + container.getBlocoId(idBlocoLivre).getTamanhoBloco());
                Bloco novo = new Bloco(idBlocoLivre + 1, container.getContainerId());
                System.out.println("Gerado bloco de ID: " + (idBlocoLivre + 1));
                novo.adicionarTuplaNoBloco(tupla);
                container.getBlocos().add(novo);
                container.atualizaIdLivreControle(idBlocoLivre + 1);
            }
        }
    }

    String[] separador(String linha) {
        return linha.split("\\|");
    }


}
