package website.marcioheleno.service;

import lombok.extern.java.Log;
import website.marcioheleno.model.bloco.container.Container;
import website.marcioheleno.model.bloco.dados.Bloco;
import website.marcioheleno.model.bloco.tupla.Tupla;
import website.marcioheleno.utils.ConverterUltils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

@Log
public class Leitura {

    public static List<Container> containers = new ArrayList<Container>();

    public void criaContainers() {
        String arquivo1 = "forn-tpch.txt";

        log.info("local arquivo: " + arquivo1);

        iniciarLeitura(arquivo1);

        log.info("Containers criados");
        log.info("/n/n/n");
        log.info("TESTE BUFFER/n/n/n");

        GerenciadorBuffer.geraRequisicoes();
        // TODO: Chamada de exportação do arquivo para outro txt
        Gravacao.exportaArquivoTxt(containers);

    }

    void iniciarLeitura(String arquivoCaminho) {
        log.info("Iniciando leitura do arquivo...");

        try {
            RandomAccessFile arquivo = new RandomAccessFile(arquivoCaminho, "rw");

            String linha = arquivo.readLine();
            Container container = new Container(linha);
//            log.info("Gerado Bloco de Controle");

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
        log.info("Iniciando leitura e gravação do arquivo...");

        try {
            RandomAccessFile arquivoLeitura = new RandomAccessFile(arquivoCaminho, "rw");
            RandomAccessFile arquivoEscrita = new RandomAccessFile(arquivoCaminho, "rw");

            String linhaLida = arquivoLeitura.readLine();


            Container container = new Container(linhaLida);
//            log.info("Gerado Bloco de Controle");

            while ((linhaLida = arquivoLeitura.readLine()) != null) {
                adicionarTupla(Tupla.montaTuplaByte(separador(linhaLida)), container);
                // TODO: chamar a escrita.
//                arquivoEscrita.write(linhaLida.getBytes());

            }

            arquivoLeitura.close();
            containers.add(container);

        } catch (IOException | NullPointerException exp) {
            exp.printStackTrace();
        }
    }

    void adicionarTupla(byte[] tupla, Container container) {

        int idBlocoLivre = ConverterUltils.byteToInt(ConverterUltils.getBytes(container.getControle().getDados(), 5, 3));
        //se nao exitir bloco, deve ser criado
        if (idBlocoLivre == 0) {
            Bloco novo = new Bloco(1, container.getContainerId());
//            log.info("Gerado bloco de ID: " + 1);
            novo.adicionarTuplaNoBloco(tupla);
            container.getBlocos().add(novo);
            container.atualizaIdLivreControle(1);
        } else { //bloco maior que tamanho da tupla
            if (container.tamanhoDoBloco() - container.getBlocoId(idBlocoLivre).getTamanhoBloco() > tupla.length) {
//                log.info("Salvou tupla no bloco: " + idBlocoLivre);
//                log.info("idmaiorq" + idBlocoLivre + "tamanho" + container.getBlocoId(idBlocoLivre).getTamanhoBloco());
                container.getBlocoId(idBlocoLivre).adicionarTuplaNoBloco(tupla);
                Gravacao.salvaArquivo(container);
            } else { //bloco menor que tamanho da tupla
//                log.info("idmenorq" + idBlocoLivre + "tamanho" + container.getBlocoId(idBlocoLivre).getTamanhoBloco());
                Bloco novo = new Bloco(idBlocoLivre + 1, container.getContainerId());
//                log.info("Gerado bloco de ID: " + (idBlocoLivre + 1));
                novo.adicionarTuplaNoBloco(tupla);
                container.getBlocos().add(novo);
                container.atualizaIdLivreControle(idBlocoLivre + 1);
                Gravacao.salvaArquivo(container);
            }
        }
    }

    String[] separador(String linha) {
        return linha.split("\\|");
    }


}
