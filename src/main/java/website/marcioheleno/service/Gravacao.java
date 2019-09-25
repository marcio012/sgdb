package website.marcioheleno.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import lombok.extern.java.Log;
import website.marcioheleno.model.bloco.container.Container;
import website.marcioheleno.model.bloco.dados.Bloco;
import website.marcioheleno.utils.ConverterUltils;

@Log
public class Gravacao {

	public static void exportaArquivoTxt(List<Container> containers) {
		// Declarações
		byte[] tuplaByte, auxEntrada = new byte[3];
		int controle = 8, controleTupla = 0;
		int tamTupla, tamEntrada = 0;
		int idContainer, idBloco;
		String text = "";
		String dados;

		for (Container container : containers) {
			// Retorna em um HashMap as colunas e seus tipo de dados
			HashMap<Integer, String> metaDados = headerTabela(container.getControle());

			// Ler Blocos, e dentro dos blocos ler as tuplas e começar a salvar esses
			// strings em uma grande string pra salvar no TXT
			for (Bloco bloco : container.getBlocosDados()) {
				idContainer = bloco.getDados()[0];
				idBloco = ConverterUltils.byteToInt(ConverterUltils.getBytes(bloco.getDados(), 1, 3));

				while (controle < ConverterUltils.byteToInt(ConverterUltils.getBytes(bloco.getDados(), 5, 3))) {
					text += String.valueOf(idContainer) + "|" + String.valueOf(idBloco) + "|";

					// Calcula o espaço ocupado pela tupla no bloco
					tamTupla = ConverterUltils.byteToInt(ConverterUltils.getBytes(bloco.getDados(), controle, 4))
							+ 2 * metaDados.size();

					// Pega os dados da tupla
					controle += 4;
					tuplaByte = ConverterUltils.getBytes(bloco.getDados(), controle, tamTupla);

					for (int i = 0, j = 0; i < tamTupla + 1; i++, j++) {

						if (i == tamEntrada + controleTupla) {

							if (i != 0) {
								// Teste
								dados = new String(auxEntrada);
								text += dados + "|";
								log.info(dados);

								if (i == tamTupla - 1 || i == tamTupla)
									break;
								// Atualização do Controle
								controleTupla += tamEntrada;
								tamEntrada = ConverterUltils
										.byte2ToInt(ConverterUltils.getBytes(tuplaByte, controleTupla, 2));
								auxEntrada = new byte[tamEntrada];
								auxEntrada = new byte[3];
								i = controleTupla - 1;
								j = 0;
							}

							// Divide por cada dado na tuplaByte e salva em String
							if (auxEntrada.length == 3) {
								if (i < tamTupla - 1) {
									tamEntrada = ConverterUltils
											.byte2ToInt(ConverterUltils.getBytes(tuplaByte, controleTupla, 2));
									auxEntrada = new byte[tamEntrada];
									i = controleTupla + 1;
									j = -1;
									controleTupla += 2;
								}
							}
						} else {
							auxEntrada[j] = tuplaByte[i];
						}

					}
					text += "\n";
					auxEntrada = new byte[3];
					controleTupla = 0;
					tamEntrada = 0;
					controle = controle + tamTupla;
				}

				controle = 8;
			}
		}

		// grava dados no arquivo
		try {
			PrintWriter out = new PrintWriter("./assets/Blocos.txt");
			out.println(text);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private static HashMap<Integer, String> headerTabela(Bloco controle) {
		HashMap<Integer, String> metaDadosAtrib = new HashMap<Integer, String>();
		byte[] header;
		int index = 0;

		header = ConverterUltils.getBytes(controle.getDados(), 11,
				ConverterUltils.byte2ToInt(ConverterUltils.getBytes(controle.getDados(), 9, 2)));
		String headerString = ConverterUltils.byteToString(header);
//		System.out.println(headerString);

		String[] metaDados = headerString.split("[|]");
		for (String string : metaDados) {
			string = string.replace("[", "#");
			String[] aux = string.split("#");

			String meta = aux[1];
			meta = meta.replace("]", "");
			metaDadosAtrib.put(index++, meta);
		}

		return metaDadosAtrib;
	}

}
