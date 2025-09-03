package dados;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import negocio.LimiteManager;
import negocio.MensalidadeManager;
import negocio.entidades.Lembrete;
import negocio.entidades.LembreteLimite;
import negocio.entidades.Limite;
import negocio.entidades.Mensalidade;
import negocio.entidades.MensalidadeLembrete;

/**
 * Classe de repositório responsável pela persistência dos objetos Lembrete.
 * 
 * Essa classe abstrai o acesso ao sistema de arquivos, garantindo que a leitura
 * e escrita dos dados sejam feitas de forma consistente e segura.
 * 
 * Utiliza o padrão de projeto Repository para centralizar a lógica de
 * persistência, facilitando manutenção e reutilização.
 * 
 * @author Halina Mochel
 */
public class LembreteRepository {
	private List<Lembrete> lembretes;
	// Caminho do arquivo onde os lembretes são armazenados
	private static final String CAMINHO_ARQUIVO = "files/lembretes.txt";
	// Formato padrão para leitura e escrita das datas
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	public LembreteRepository(MensalidadeManager mm, LimiteManager lm) {
		lembretes = new ArrayList<Lembrete>();
		lembretes = carregar(mm, lm);
	}

	
	
	public List<Lembrete> getLembretes(){
		return lembretes;
	}
	
	public void criar(Lembrete l) {
		lembretes.add(l);
		salvar();
	}
	
	public void remover(Lembrete l) {
		int indice = lembretes.indexOf(l);
		if(indice != -1) {
			lembretes.remove(l);
			salvar();
		}
	}
	
	public void atualizar(Lembrete l, String nT, String nD, LocalDate nDa, boolean ativo) {
		int indice = lembretes.indexOf(l);
		if(indice != -1) {
			lembretes.get(indice).setTitulo(nT);
			lembretes.get(indice).setDescricao(nD);
			lembretes.get(indice).setDataAlerta(nDa);
			lembretes.get(indice).setAtivo(ativo);
			salvar();
		}
	}
	
	public Lembrete consultar(int id) {
		return lembretes.stream().filter(l ->l.getId() == id).findFirst().orElse(null);
	}
	
	/**
	 * Garante que o arquivo e seu diretório existam antes de qualquer operação de
	 * leitura ou escrita. Isso evita erros de arquivo não encontrado e facilita a
	 * criação automática do ambiente.
	 */
	private void garantirArquivo() {
		try {
			Path path = Paths.get(CAMINHO_ARQUIVO);
			Path dir = path.getParent();
			if (dir != null && !Files.exists(dir)) {
				Files.createDirectories(dir); // Cria diretórios se não existirem
			}
			if (!Files.exists(path)) {
				Files.createFile(path); // Cria o arquivo se não existir
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Salva a lista de lembretes no arquivo, sobrescrevendo o conteúdo anterior.
	 * Utiliza BufferedWriter para escrita eficiente e segura.
	 */
	private void salvar(){
		garantirArquivo();
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(CAMINHO_ARQUIVO))) {
			for (Lembrete lembrete : lembretes) {
				writer.write(lembrete.toFileString()); // Converte o lembrete para formato texto
				writer.newLine();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Carrega todos os lembretes do arquivo, reconstruindo os objetos Lembrete e
	 * suas subclasses a partir das linhas lidas, associando-os às mensalidades e
	 * limites correspondentes.
	 * 
	 * Recebe listas de mensalidades e limites para garantir a associação correta,
	 * respeitando a integridade referencial.
	 * 
	 * Trata erros de parsing e associações inválidas, reportando avisos sem
	 * interromper a carga.
	 * 
	 * @param mm 	Manager de mensalidades
	 * @param lm	Manager de limites
	 * @return Lista de objetos Lembrete carregados do arquivo
	 */
	private List<Lembrete> carregar(MensalidadeManager mm, LimiteManager lm){
		garantirArquivo();
		List<Limite> limites = lm.getLimites();
		List<Mensalidade> mensalidades = mm.listarMensalidades();

		try (BufferedReader reader = Files.newBufferedReader(Paths.get(CAMINHO_ARQUIVO))) {
			String linha;
			while ((linha = reader.readLine()) != null) {
				if (linha.trim().isEmpty())
					continue; // Ignora linhas vazias
				try {
					// Divide a linha em até 8 partes para capturar todos os campos esperados
					String[] partes = linha.split(";", 8);
					String tipo = partes[0].trim(); // Tipo do lembrete (ex: LIMITE, MENSALIDADE, LEMBRETE)
					int id = Integer.parseInt(partes[1].trim());
					String titulo = partes[2].trim();
					String descricao = partes[3].trim();
					LocalDate dataCriacao = LocalDate.parse(partes[4].trim(), DATE_FORMATTER);
					LocalDate dataAlerta = LocalDate.parse(partes[5].trim(), DATE_FORMATTER);
					boolean ativo = Boolean.parseBoolean(partes[6].trim());

					switch (tipo) {
					case "LIMITE":
						// Para lembretes do tipo limite, associa o limite correspondente pelo ID
						int limiteId = Integer.parseInt(partes[7].trim());
						Optional<Limite> limite = limites.stream().filter(l -> l.getId() == limiteId).findFirst();
						if (limite.isPresent()) {
							lembretes.add(new LembreteLimite(id, titulo, descricao, dataCriacao, dataAlerta, ativo,
									limite.get()));
						} else {
							System.err.println(
									"Erro ao carregar LembreteLimite: Limite ID " + limiteId + " não encontrado.");
						}
						break;
					case "MENSALIDADE":
						// Para lembretes do tipo mensalidade, associa a mensalidade correspondente pelo
						// ID
						int mensalidadeId = Integer.parseInt(partes[7].trim());
						Optional<Mensalidade> mensalidade = mensalidades.stream()
								.filter(m -> m.getId() == mensalidadeId).findFirst();
						if (mensalidade.isPresent()) {
							// Note que aqui é passado o ID da mensalidade, pode ser que o construtor use
							// isso para buscar a mensalidade
							lembretes.add(new MensalidadeLembrete(id, titulo, descricao, dataCriacao, dataAlerta, ativo,
									mensalidadeId));
						} else {
							System.err.println("Erro ao carregar MensalidadeLembrete: Mensalidade ID " + mensalidadeId
									+ " não encontrada.");
						}
						break;
					case "LEMBRETE":
						// Lembrete genérico sem associação extra
						lembretes.add(new Lembrete(id, titulo, descricao, dataCriacao, dataAlerta, ativo));
						break;
					// Caso surjam outros tipos de lembretes, podem ser adicionados aqui
					default:
						System.err.println("Tipo de lembrete desconhecido: " + tipo + " na linha: " + linha);
						break;
					}
				} catch (Exception e) {
					// Captura erros de parsing e exibe mensagem para facilitar depuração
					System.err.println("Erro ao carregar lembrete: " + e.getMessage() + " | Linha: " + linha);
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		return lembretes;
	}
}