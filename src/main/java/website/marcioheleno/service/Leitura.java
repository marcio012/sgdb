package website.marcioheleno.service;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.java.Log;
import website.marcioheleno.model.bloco.container.Container;
import website.marcioheleno.model.bloco.dados.Bloco;
import website.marcioheleno.model.bloco.tupla.Tupla;
import website.marcioheleno.utils.ConverterUltils;

@Log
public class Leitura {

	public static List<Container> containers = new ArrayList<Container>();

	public void criaContainers() {
//		TODO: Uso em teste
//		String arquivo1 = "assets/teste-menor.txt";
//		String arquivo2 = "assets/teste-menor2.txt";

		String arquivo1 = "assets/forn-tpch.txt";
		String arquivo2 = "assets/cli-tpch.txt";

		log.info("local arquivo: " + arquivo1);
		iniciarLeitura(arquivo1);

		log.info("local arquivo: " + arquivo2);
		iniciarLeitura(arquivo2);

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
            log.info("Bloco de Controle Gerado");

			while ((linha = arquivo.readLine()) != null) {
				adicionarTupla(Tupla.montaTuplaByte(separador(linha)), container);
			}

			arquivo.close();
			containers.add(container);

		} catch (IOException | NullPointerException exp) {
			exp.printStackTrace();
		}
	}

	void adicionarTupla(byte[] tupla, Container container) {

		int idBlocoLivre = ConverterUltils
				.byteToInt(ConverterUltils.getBytes(container.getControle().getDados(), 5, 3));
		// se nao exitir bloco, deve ser criado
		if (idBlocoLivre == 0) {
			Bloco novo = new Bloco(1, container.getContainerId());
            log.info("Gerado bloco de ID: " + 1);
			novo.adicionarTuplaNoBloco(tupla);
			container.getBlocos().add(novo);
			container.atualizaIdLivreControle(1);
		} else { // bloco maior que tamanho da tupla
			if (container.tamanhoDoBloco() - container.getBlocoId(idBlocoLivre).getTamanhoBloco() > tupla.length) {
                log.fine("Salvou tupla no bloco: " + idBlocoLivre);
                log.info("idmaiorq" + idBlocoLivre + "tamanho" + container.getBlocoId(idBlocoLivre).getTamanhoBloco());
				container.getBlocoId(idBlocoLivre).adicionarTuplaNoBloco(tupla);
//				Gravacao.salvaArquivo(container);
			} else { // bloco menor que tamanho da tupla
                log.fine("idmenorq" + idBlocoLivre + "tamanho" + container.getBlocoId(idBlocoLivre).getTamanhoBloco());
				Bloco novo = new Bloco(idBlocoLivre + 1, container.getContainerId());
                log.info("Gerado bloco de ID: " + (idBlocoLivre + 1));
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
