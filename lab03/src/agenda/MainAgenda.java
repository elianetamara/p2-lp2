package agenda;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Interface com menus texto para manipular uma agenda de contatos.
 * 
 * @author nazarenoandrade
 *
 */
public class MainAgenda {

	public static void main(String[] args) {
		Agenda agenda = new Agenda();

		System.out.println("Carregando agenda inicial");
		try {
			/*
			 * Essa é a maneira de lidar com possíveis erros por falta do arquivo. 
			 */
			carregaAgenda("agenda_inicial.csv", agenda);
		} catch (FileNotFoundException e) {
			System.err.println("Arquivo não encontrado: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Erro lendo arquivo: " + e.getMessage());
		}

		Scanner scanner = new Scanner(System.in);
		String escolha;
		while (true) {
			escolha = menu(scanner);
			comando(escolha, agenda, scanner);
		}

	}

	/**
	 * Exibe o menu e captura a escolha do/a usuário/a.
	 * 
	 * @param scanner Para captura da opção do usuário.
	 * @return O comando escolhido.
	 */
	private static String menu(Scanner scanner) {
		System.out.println(
				"\n---\nMENU\n" + 
						"(C)adastrar Contato\n" + 
						"(L)istar Contatos\n" + 
						"(E)xibir Contato\n" +
						"(F)avoritos\n" +
						"(A)dicionar Favorito\n" +
						"(R)emover Favorito\n" +
						"(S)air\n" + 
						"\n" + 
						"Opção> ");
		return scanner.next().toUpperCase();
	}

	/**
	 * Interpreta a opção escolhida por quem está usando o sistema.
	 * 
	 * @param opcao   Opção digitada.
	 * @param agenda  A agenda que estamos manipulando.
	 * @param scanner Objeto scanner para o caso do comando precisar de mais input.
	 */
	private static void comando(String opcao, Agenda agenda, Scanner scanner) {
		switch (opcao) {
		case "C":
			cadastraContato(agenda, scanner);
			break;
		case "L":
			listaContatos(agenda);
			break;
		case "E":
			exibeContato(agenda, scanner);
			break;
		case "S":
			sai();
			break;
		case "F":
			exibeFavoritos(agenda);
			break;
		case "A":
			adicionarFavorito(agenda, scanner);
			break;
		case "R":
			removerFavorito(agenda, scanner);
			break;
		default:
			System.out.println("Opção inválida!");
		}
	}

	private static void removerFavorito(Agenda agenda, Scanner scanner) {
		System.out.print("\nPosição> ");
		int posicao = scanner.nextInt();
		agenda.removeFavorito(posicao);
	}

	private static void adicionarFavorito(Agenda agenda, Scanner scanner) {
		System.out.print("\nContato> ");
		int indiceContato = scanner.nextInt();
		System.out.print("\nPosição> ");
		int posicao = scanner.nextInt();
		agenda.cadastraFavorito(agenda.getContato(indiceContato), posicao);
	}

	private static void exibeFavoritos(Agenda agenda) {
		String[] listaFavoritos = agenda.listaFavoritos();
		for (String s : listaFavoritos) {
			if(!s.equals(null)) {
				System.out.println(s);
			}
		}
	}

	/**
	 * Imprime lista de contatos da agenda.
	 * 
	 * @param agenda A agenda sendo manipulada.
	 */
	private static void listaContatos(Agenda agenda) {
		System.out.println("\nLista de contatos: ");
		String[] listaContatos = agenda.listaContatos();
		for (String s: listaContatos) {
			System.out.println(s);
		}
	}

	/**
	 * Imprime os detalhes de um dos contatos da agenda.
	 *
	 * @param agenda A agenda.
	 * @param scanner Scanner para capturar qual contato.
	 * @throws IllegalArgumentException Se a posição for inválida
	 */
	private static void exibeContato(Agenda agenda, Scanner scanner) throws IllegalArgumentException{
		System.out.print("\nContato> ");
		int posicao = scanner.nextInt();
		try {
			System.out.println(agenda.exibeContato(posicao));
		}catch (IllegalArgumentException e){
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Cadastra um contato na agenda.
	 *
	 * @param agenda A agenda.
	 * @param scanner Scanner para pedir informações do contato.
	 * @throws IllegalArgumentException Se os argumentos forem inválidos
	 */
	private static void cadastraContato(Agenda agenda, Scanner scanner) throws IllegalArgumentException{
		System.out.print("\nPosição na agenda> ");
		int posicao = scanner.nextInt();
		System.out.print("\nNome> ");
		String nome = scanner.next();
		System.out.print("\nSobrenome> ");
		String sobrenome = scanner.next();
		System.out.print("\nTelefone> ");
		String telefone = scanner.next();
		try {
			agenda.cadastraContato(posicao, nome, sobrenome, telefone);
		}catch (IllegalArgumentException e){
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Sai da aplicação.
	 */
	private static void sai() {
		System.out.println("\nVlw flw o/");
		System.exit(0);
	}

	/**
	 * Lê uma agenda de um arquivo csv. 
	 * 
	 * @param arquivoContatos O caminho para o arquivo.
	 * @param agenda A agenda que deve ser populada com os dados. 
	 * @throws IOException Caso o arquivo não exista ou não possa ser lido.
	 */
	private static void carregaAgenda(String arquivoContatos, Agenda agenda) throws FileNotFoundException, IOException {
		LeitorDeAgenda leitor = new LeitorDeAgenda();
		
		int carregados =  leitor.carregaContatos(arquivoContatos, agenda);
		System.out.println("Carregamos " + carregados + " registros.");
	}
}