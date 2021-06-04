/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import model.Conta;
import model.Doacao;
import model.Endereco;
import model.Evento;
import model.Funcionario;
import model.Gasto;
import model.Gestor;
import model.Item;
import model.Trabalho;
import model.Voluntario;
import model.VoluntarioPF;
import model.VoluntarioPJ;

/**
 *
 * @author lucas
 */
public class MoraisVoluntariado extends javax.swing.JFrame {

    /**
     * Creates new form TelaLogin
     */
	boolean connected = false;
	private int accountID;
	private int userID;
	private int idCount = 1;
	private ArrayList<Conta> listaContas = new ArrayList<Conta>();
	private ArrayList<Funcionario> listaFuncionarios = new ArrayList<Funcionario>();
	private ArrayList<Gestor> listaGestores = new ArrayList<Gestor>();
	private ArrayList<Voluntario> listaVoluntarios = new ArrayList<Voluntario>();
	private ArrayList<Gasto> listaGastosTemp = new ArrayList<Gasto>();
	private ArrayList<Item> listaItensTemp = new ArrayList<Item>();
	private ArrayList<Trabalho> listaTrabalhosTemp = new ArrayList<Trabalho>();
	private ArrayList<Evento> listaEventos = new ArrayList<Evento>();
	private ArrayList<Doacao> listaDoacoes = new ArrayList<Doacao>();
	private ArrayList<Doacao> listaDoacoesPendentes = new ArrayList<Doacao>();
	DefaultListModel<String> cleanList = new DefaultListModel<String>();
	FileWriter arquivoEventos = new FileWriter("Eventos.txt");
	FileWriter arquivoDoacoes = new FileWriter("Doações.txt");
	FileWriter arquivoFinanceiro = new FileWriter("Relatório Financeiro.txt");
	PrintWriter escreverArquivoE = new PrintWriter(arquivoEventos);
	PrintWriter escreverArquivoD = new PrintWriter(arquivoDoacoes);
	PrintWriter escreverArquivoF = new PrintWriter(arquivoFinanceiro);
	
	
    public MoraisVoluntariado() throws IOException {
        initComponents();
		resetLayers();
		loginPanel.setVisible(true);
		
		listaContas.add(new Conta(0, "gestor", "123456", "Gestor"));
		listaGestores.add(new Gestor(0, "Alana Marques", "Feminino", "123456789", "83982105547", null));
    }
	
	public void resetLayers(){
		// Paineis do login
		loginPanel.setVisible(false);
		
		// Paineis do funcionário
		pnFuncionario.setVisible(false);
		pnFuncMain.setVisible(false);
		pnCdVolPF.setVisible(false);
		pnCdVolPJ.setVisible(false);
		pnCdEvento.setVisible(false);
		pnCdTrabalho.setVisible(false);
		pnRvVol.setVisible(false);
		pnFuncEventos.setVisible(false);
		pnAceitarDoacao.setVisible(false);
		pnGerenciarEntrega.setVisible(false);
		
		// Paineis do VoluntárioPF
		pnVoluntarioPF.setVisible(false);
		pnVolMain.setVisible(false);
		pnTbEvento.setVisible(false);
		pnDoar.setVisible(false);
		pnVolPFEventos.setVisible(false);
		pnMinhasDoacoes.setVisible(false); 
		
		// Paineis do VoluntárioPJ
		pnVoluntarioPJ.setVisible(false);
		pnEventosPatrocinados.setVisible(false);
		pnPatrocinarEvento.setVisible(false);
		
		// Paineis Gestor
		pnGestor.setVisible(false);
		pnGestorInicio.setVisible(false);
		pnCadastrarFunc.setVisible(false);
		pnAnalisarDespesas.setVisible(false);
		pnAnalisarReceitas.setVisible(false);
		pnRelatorioFinanc.setVisible(false);
	}
	
	public void enableFuncionario(){
		pnFuncMain.setVisible(true); // Paginá principal Funcionário
		pnFuncionario.setVisible(true); // Menu Funcionário
		taInfoFuncionario.setText(listaFuncionarios.get(userID).toString());
	}
	
	public void enableVoluntarioPF(){
		pnVolMain.setVisible(true); // Paginá principal Funcionário
		pnVoluntarioPF.setVisible(true); // Menu Funcionário
		taInfoVoluntarioPF.setText(listaVoluntarios.get(userID).toString());
	}
	
	public void enableVoluntarioPJ(){
		pnVolMain.setVisible(true); // Paginá principal Funcionário
		pnVoluntarioPJ.setVisible(true); // Menu Funcionário
		taInfoVoluntarioPF.setText(listaVoluntarios.get(userID).toString());
	}
	
	public void enableGestor(){
		pnGestorInicio.setVisible(true);
		pnGestor.setVisible(true);
		taInfoGestor.setText(listaGestores.get(userID).toString());
	}
	
	public String gerarMeusEventosPF(){
		String eventos = "";
		for(int i = 0; i < listaEventos.size(); i++){
			for(int j = 0; j < listaEventos.get(i).getTrabalhos().size(); j++){
				if(listaEventos.get(i).getTrabalhos().get(j).getVol() == listaVoluntarios.get(userID)){
					eventos += String.format("%s\nTrabalho:\n%s\n\n", listaEventos.get(i).toString(), listaEventos.get(i).getTrabalhos().get(j));
				}
			}
		}
		return eventos;
	}
	
	public String gerarMeusEventosPJ(){
		String eventos = "";
		for(int i = 0; i < listaEventos.size(); i++){
			if(listaEventos.get(i).verificarPatrocinadorRepetido(listaVoluntarios.get(userID))){
				eventos += String.format("%s\nPatrocinadores:\n%s\n\n", listaEventos.get(i).toString(), listaEventos.get(i).infoPatrocinadores());
			}
		}
		return eventos;
	}
	
	public String gerarMeusEventosFunc(){
		String eventos = "";
		for(int i = 0; i < listaEventos.size(); i++){
			if(listaEventos.get(i).getResponsavel() == listaFuncionarios.get(userID)){
				eventos += String.format("%s\nTrabalhos:\n%s\n\n", listaEventos.get(i).toString(), listaEventos.get(i).infoTrabalhos());
			}
		}
		return eventos;
	}
	
	public String gerarMinhasDoacoesPendentes(){
		String doacoesPendentes = "";
		for(int i = 0; i < listaDoacoesPendentes.size(); i++){
			if(listaDoacoesPendentes.get(i).getVol() == listaVoluntarios.get(userID)){
				doacoesPendentes += String.format("%n%s%n", listaDoacoesPendentes.get(i).toString());
			}
		}
		return doacoesPendentes;
	}
	
	public String gerarMinhasDoacoes(){
		String doacoes = "";
		for(int i = 0; i < listaDoacoes.size(); i++){
			if(listaDoacoes.get(i).getVol() == listaVoluntarios.get(userID)){
				doacoes += String.format("%n%s%n", listaDoacoes.get(i).toString());
			}
		}
		return doacoes;
	}
	
	public String gerarDoacoes(){
		String doacoes = "";
		for(int i = 0; i < listaDoacoes.size(); i++){
			doacoes += String.format("%n%s%n", listaDoacoes.get(i).toString());
		}
		return doacoes;
	}
	
	public String gerarEventos(){
		String eventos = "";
		for(int i = 0; i < listaEventos.size(); i++){
			eventos += String.format("%s\nTrabalhos:\n%s\nPatrocinadores:\n%s", listaEventos.get(i).toString(), listaEventos.get(i).infoTrabalhos(), listaEventos.get(i).infoPatrocinadores());
		}
		return eventos;
	}
	
	
	
	public void clearCdVolPF(){
		tfVolPFNome.setText("");
		tfVolPFBairro.setText("");
		tfVolPFCidade.setText("");
		tfVolPFComplemento.setText("");
		tfVolPFNumero.setText("");
		tfVolPFRua.setText("");
		ftfVolPFCEP.setText("");
		ftfVolPFCPF.setText("");
		ftfVolPFTelefone.setText("");
	}
	
	public void clearCdVolPJ(){
		tfVolPJNome.setText("");
		tfVolPJBairro.setText("");
		tfVolPJCidade.setText("");
		tfVolPJComplemento.setText("");
		tfVolPJNumero.setText("");
		tfVolPJRua.setText("");
		ftfVolPJCEP.setText("");
		ftfVolPJCNPJ.setText("");
		ftfVolPJIE.setText("");
		ftfVolPJTelefone.setText("");
	}
	
	public void clearCdEvento(){
		tfNomeEvento.setText("");
		tfNomeGasto.setText("");
		tfValorGasto.setText("");
		ftfDataEvento.setText("");
		ftfDuracaoFim.setText("");
		ftfDuracaoInicio.setText("");
		tpObjetivoEvento.setText("");
		lbTotalGastos.setText("R$ 0");
		listaGastosTemp.clear();
		jLGastos.setModel(converterGastosLista());
	}
	
	public void clearCdTrabalho(){
		tfNomeTrabalho.setText("");
		tpDescricaoTrabalho.setText("");
		listaTrabalhosTemp.clear();
		jLTrabalhos.setModel(converterTrabalhosLista());
	}
	
	public void clearDoarPF(){
		tfNomeItem.setText("");
		tfValorDoacao.setText("0");
		ftfDataDoacao.setText("");
		tfItemQuantidade.setText("");
		listaItensTemp.clear();
		jLItens.setModel(converterItensLista());
	}
	
	public void clearGestor(){
		tfGestorBairro.setText("");
		tfGestorCidade.setText("");
		tfGestorComplemento.setText("");
		tfGestorNome.setText("");
		tfGestorNumero.setText("");
		tfGestorRua.setText("");
		tfGestorSenha.setText("");
		tfGestorUsuario.setText("");
		ftfGestorCEP.setText("");
		ftfGestorCPF.setText("");
		ftfGestorTelefone.setText("");
			
	}
	
	public String gerarInfoDoacoes(){
		String total = "";
		for(int i = 0; i<listaDoacoes.size(); i++){
			total += listaDoacoes.get(i).toString();
		}
		return total;
	}
	
	public double gerarInfoDinheiro(){
		double total = 0;
		for(int i = 0; i<listaDoacoes.size(); i++){
			total += listaDoacoes.get(i).getDoacaoDinheiro();
		}
		return total;
	}
	
	public String gerarInfoEventos(){
		String total = "";
		for(int i = 0; i<listaEventos.size(); i++){
			total += listaEventos.get(i).toString();
		}
		return total;
	}
	
	public double gerarTotalDespesas(){
		double total = 0;
		for(int i = 0; i<listaEventos.size(); i++){
			total += listaEventos.get(i).gerarTotalGastos();
		}
		return total;
	}
	
	public double calcularTotalGastosTemp(){
		double totalgastos = 0;
		for(int i = 0; i<listaGastosTemp.size(); i++){
			totalgastos += listaGastosTemp.get(i).getValor();
		}
		return totalgastos;
	}
	
	public DefaultListModel<String> converterFuncionariosLista(){
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		for(int i = 0; i<listaFuncionarios.size(); i++){
			listModel.addElement(listaFuncionarios.get(i).getNome());
		}
		return listModel;
	}
	
	public DefaultListModel<String> converterGastosLista(){
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		for(int i = 0; i<listaGastosTemp.size(); i++){
			listModel.addElement(listaGastosTemp.get(i).toString());
		}
		return listModel;
	}
	
	public DefaultListModel<String> converterTrabalhosLista(){
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		for(int i = 0; i<listaTrabalhosTemp.size(); i++){
			listModel.addElement(listaTrabalhosTemp.get(i).toString());
		}
		return listModel;
	}
	
	public DefaultListModel<String> converterItensLista(){
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		for(int i = 0; i<listaItensTemp.size(); i++){
			listModel.addElement(listaItensTemp.get(i).toString());
		}
		return listModel;
	}
	
	public DefaultListModel<String> converterTrabalhosDisponiveisLista(){
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		for(int i = 0; i<listaEventos.get(jLEventosTrabalhos.getAnchorSelectionIndex()).getTrabalhos().size(); i++){
			listModel.addElement(listaEventos.get(jLEventosTrabalhos.getAnchorSelectionIndex()).getTrabalhos().get(i).toString());		
		}
		return listModel;
	}
	
	public DefaultListModel<String> converterEventosLista(){
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		for(int i = 0; i<listaEventos.size(); i++){
			listModel.addElement(listaEventos.get(i).getNome());
		}
		return listModel;
	}
	
	public DefaultListModel<String> converterVoluntariosLista(){
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		for(int i = 0; i<listaVoluntarios.size(); i++){
			listModel.addElement(listaVoluntarios.get(i).getNome());
		}
		return listModel;
	}
	
	public DefaultListModel<String> converterDoacoesPendentes(){
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		for(int i = 0; i<listaDoacoesPendentes.size(); i++){
			listModel.addElement(listaDoacoesPendentes.get(i).toString());
		}
		return listModel;
	}
	
	public DefaultListModel<String> converterDoacoes(){
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		for(int i = 0; i<listaDoacoes.size(); i++){
			listModel.addElement(String.format("Voluntário: %s, Entregue: %s", listaDoacoes.get(i).getVol().getNome(), listaDoacoes.get(i).infoEntregue()));
		}
		return listModel;
	}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loginPanel = new keeptoo.KGradientPanel();
        tfLogin = new javax.swing.JTextField();
        pfSenha = new javax.swing.JPasswordField();
        lbLogin = new javax.swing.JLabel();
        lbSenha = new javax.swing.JLabel();
        btLogin = new keeptoo.KButton();
        iconMV = new javax.swing.JLabel();
        ldMenus = new javax.swing.JLayeredPane();
        pnFuncionario = new keeptoo.KGradientPanel();
        lbTituloFuncionario = new javax.swing.JLabel();
        btEntregas = new keeptoo.KButton();
        btCadastroVoluntario = new keeptoo.KButton();
        btFuncInicio = new keeptoo.KButton();
        btCadastroEvento = new keeptoo.KButton();
        btCadastroTrabalho = new keeptoo.KButton();
        btAceitarDoacao = new keeptoo.KButton();
        btRemoverVoluntario = new keeptoo.KButton();
        btRelatorios = new keeptoo.KButton();
        btFuncLogout1 = new keeptoo.KButton();
        btMeusEventosFunc = new keeptoo.KButton();
        pnVoluntarioPF = new keeptoo.KGradientPanel();
        lbTituloVolPF = new javax.swing.JLabel();
        btVolPFInicio = new keeptoo.KButton();
        btTrabalharEvento = new keeptoo.KButton();
        btDoarPF = new keeptoo.KButton();
        btVolPFLogout = new keeptoo.KButton();
        btMeusEventosPF = new keeptoo.KButton();
        btMinhasDoacoes = new keeptoo.KButton();
        pnVoluntarioPJ = new keeptoo.KGradientPanel();
        lbTituloVolPF1 = new javax.swing.JLabel();
        btVolPJInicio = new keeptoo.KButton();
        btPatrocinarEvento = new keeptoo.KButton();
        btDoarPJ = new keeptoo.KButton();
        btVolPJLogout = new keeptoo.KButton();
        btMeusEventosPJ = new keeptoo.KButton();
        btMinhasDoacoesPJ = new keeptoo.KButton();
        pnGestor = new keeptoo.KGradientPanel();
        lbTituloVolPF2 = new javax.swing.JLabel();
        btGestorInicio = new keeptoo.KButton();
        btGestorAnalisarReceitas = new keeptoo.KButton();
        btGestorAnalisarDespesas = new keeptoo.KButton();
        btGestorDesconectar = new keeptoo.KButton();
        btGestorCadastrar = new keeptoo.KButton();
        btGestorRelatorio = new keeptoo.KButton();
        ldFuncionario = new javax.swing.JLayeredPane();
        pnFuncMain = new javax.swing.JPanel();
        pnMainTitle = new keeptoo.KGradientPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taInfoFuncionario = new javax.swing.JTextArea();
        pnCdVolPF = new javax.swing.JPanel();
        pnCdVolTitle = new keeptoo.KGradientPanel();
        jLabel1 = new javax.swing.JLabel();
        pnDados = new javax.swing.JPanel();
        ftfVolPFTelefone = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        tfVolPFNome = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        ftfVolPFCPF = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        cbVolPFSexo = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cbVolPFTurno = new javax.swing.JComboBox<>();
        btCadastrarVolPF = new keeptoo.KButton();
        pnEndereco = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        tfVolPFRua = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        tfVolPFNumero = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        tfVolPFComplemento = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        tfVolPFBairro = new javax.swing.JTextField();
        tfVolPFCidade = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cbVolPFUF = new javax.swing.JComboBox<>();
        ftfVolPFCEP = new javax.swing.JFormattedTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        tfVolPFUsuario = new javax.swing.JTextField();
        tfVolPFSenha = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        pnCdVolPJ = new javax.swing.JPanel();
        pnCdVolTitle1 = new keeptoo.KGradientPanel();
        jLabel19 = new javax.swing.JLabel();
        pnDados1 = new javax.swing.JPanel();
        ftfVolPJTelefone = new javax.swing.JFormattedTextField();
        jLabel21 = new javax.swing.JLabel();
        tfVolPJNome = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        ftfVolPJCNPJ = new javax.swing.JFormattedTextField();
        jLabel24 = new javax.swing.JLabel();
        ftfVolPJIE = new javax.swing.JFormattedTextField();
        btCadastrarVolPJ = new keeptoo.KButton();
        pnEndereco1 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        tfVolPJRua = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        tfVolPJNumero = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        tfVolPJComplemento = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        tfVolPJBairro = new javax.swing.JTextField();
        tfVolPJCidade = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        cbVolPJUF = new javax.swing.JComboBox<>();
        ftfVolPJCEP = new javax.swing.JFormattedTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        tfVolPJUsuario = new javax.swing.JTextField();
        tfVolPJSenha = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        pnCdTrabalho = new javax.swing.JPanel();
        pnMainTitle2 = new keeptoo.KGradientPanel();
        jLabel49 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        tfNomeTrabalho = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tpDescricaoTrabalho = new javax.swing.JTextPane();
        jLabel52 = new javax.swing.JLabel();
        btRemoverTrabalho = new keeptoo.KButton();
        btAdicionarTrabalho = new keeptoo.KButton();
        jLabel55 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jLTrabalhos = new javax.swing.JList<>();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jLEventos = new javax.swing.JList<>();
        btInfoEvento = new keeptoo.KButton();
        btCadastrarTrabalho = new keeptoo.KButton();
        pnRvVol = new javax.swing.JPanel();
        pnMainTitle3 = new keeptoo.KGradientPanel();
        jLabel51 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jLVoluntarios = new javax.swing.JList<>();
        jLabel59 = new javax.swing.JLabel();
        btInfoVol = new keeptoo.KButton();
        btRemoverVol = new keeptoo.KButton();
        pnFuncEventos = new javax.swing.JPanel();
        pnMainTitle6 = new keeptoo.KGradientPanel();
        jLabel62 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        taEventosFunc = new javax.swing.JTextArea();
        pnCdEvento = new javax.swing.JPanel();
        pnMainTitle1 = new keeptoo.KGradientPanel();
        jLabel25 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        tfNomeEvento = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        ftfDataEvento = new javax.swing.JFormattedTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tpObjetivoEvento = new javax.swing.JTextPane();
        jLabel91 = new javax.swing.JLabel();
        ftfDuracaoInicio = new javax.swing.JFormattedTextField();
        ftfDuracaoFim = new javax.swing.JFormattedTextField();
        jLabel92 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jLGastos = new javax.swing.JList<>();
        jLabel94 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jScrollPane20 = new javax.swing.JScrollPane();
        jLFuncionarios = new javax.swing.JList<>();
        btInfoFuncionario = new keeptoo.KButton();
        btAddGasto = new keeptoo.KButton();
        tfNomeGasto = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        btRemoveGasto = new keeptoo.KButton();
        tfValorGasto = new javax.swing.JTextField();
        btCadastrarEvento = new keeptoo.KButton();
        lbTotalGastos = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        pnAceitarDoacao = new javax.swing.JPanel();
        pnMainTitle12 = new keeptoo.KGradientPanel();
        jLabel68 = new javax.swing.JLabel();
        jScrollPane17 = new javax.swing.JScrollPane();
        jLDoacoesPendentes = new javax.swing.JList<>();
        jLabel80 = new javax.swing.JLabel();
        btInfoDoacao = new keeptoo.KButton();
        btAceitarDoacaoSelecionada = new keeptoo.KButton();
        btRecusarDoacao = new keeptoo.KButton();
        pnGerenciarEntrega = new javax.swing.JPanel();
        pnMainTitle15 = new keeptoo.KGradientPanel();
        jLabel88 = new javax.swing.JLabel();
        jScrollPane21 = new javax.swing.JScrollPane();
        jLDoacoes = new javax.swing.JList<>();
        jLabel90 = new javax.swing.JLabel();
        btInfoDoacao1 = new keeptoo.KButton();
        btConfirmarEntrega = new keeptoo.KButton();
        ldVoluntarioPF = new javax.swing.JLayeredPane();
        pnVolMain = new javax.swing.JPanel();
        pnMainTitle4 = new keeptoo.KGradientPanel();
        jLabel53 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        taInfoVoluntarioPF = new javax.swing.JTextArea();
        pnTbEvento = new javax.swing.JPanel();
        pnCdVolTitle2 = new keeptoo.KGradientPanel();
        jLabel54 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jLTrabalhosDisponiveis = new javax.swing.JList<>();
        jLabel58 = new javax.swing.JLabel();
        btAceitarTrabalho = new keeptoo.KButton();
        jScrollPane12 = new javax.swing.JScrollPane();
        jLEventosTrabalhos = new javax.swing.JList<>();
        jLabel60 = new javax.swing.JLabel();
        btVerTrabalhos = new keeptoo.KButton();
        pnVolPFEventos = new javax.swing.JPanel();
        pnMainTitle5 = new keeptoo.KGradientPanel();
        jLabel61 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        taEventosVolPF = new javax.swing.JTextArea();
        pnDoar = new javax.swing.JPanel();
        pnMainTitle8 = new keeptoo.KGradientPanel();
        jLabel40 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        ftfDataDoacao = new javax.swing.JFormattedTextField();
        tfValorDoacao = new javax.swing.JTextField();
        jLabel76 = new javax.swing.JLabel();
        cbRepetirDoacao = new javax.swing.JCheckBox();
        cbModoEntrega = new javax.swing.JComboBox<>();
        jLabel42 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jLItens = new javax.swing.JList<>();
        jLabel48 = new javax.swing.JLabel();
        btAddItem = new keeptoo.KButton();
        tfNomeItem = new javax.swing.JTextField();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        btRemoveItem = new keeptoo.KButton();
        tfItemQuantidade = new javax.swing.JTextField();
        btFinalizarDoacaoPF = new keeptoo.KButton();
        pnMinhasDoacoes = new javax.swing.JPanel();
        pnMainTitle7 = new keeptoo.KGradientPanel();
        jLabel63 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        taDoacoesPendentes = new javax.swing.JTextArea();
        jScrollPane15 = new javax.swing.JScrollPane();
        taDoacoesAceitas = new javax.swing.JTextArea();
        ldVoluntarioPJ = new javax.swing.JLayeredPane();
        pnPatrocinarEvento = new javax.swing.JPanel();
        pnPatrocinarTitle = new keeptoo.KGradientPanel();
        jLabel67 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        btPatrocinarEventoSelecionado = new keeptoo.KButton();
        jScrollPane18 = new javax.swing.JScrollPane();
        jLEventosPatrocinar = new javax.swing.JList<>();
        jLabel69 = new javax.swing.JLabel();
        btVerInfoEvento = new keeptoo.KButton();
        pnEventosPatrocinados = new javax.swing.JPanel();
        pnMainTitle10 = new keeptoo.KGradientPanel();
        jLabel70 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane19 = new javax.swing.JScrollPane();
        taEventosPatrocinados = new javax.swing.JTextArea();
        ldGestor = new javax.swing.JLayeredPane();
        pnGestorInicio = new javax.swing.JPanel();
        pnMainTitle9 = new keeptoo.KGradientPanel();
        jLabel66 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        taInfoGestor = new javax.swing.JTextArea();
        pnAnalisarReceitas = new javax.swing.JPanel();
        pnMainTitle11 = new keeptoo.KGradientPanel();
        jLabel73 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane22 = new javax.swing.JScrollPane();
        taReceitas = new javax.swing.JTextArea();
        lbTotalReceitas = new javax.swing.JLabel();
        pnRelatorioFinanc = new javax.swing.JPanel();
        pnMainTitle13 = new keeptoo.KGradientPanel();
        jLabel85 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel86 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jScrollPane24 = new javax.swing.JScrollPane();
        taDoacoesPendentes1 = new javax.swing.JTextArea();
        jScrollPane25 = new javax.swing.JScrollPane();
        taDoacoesAceitas1 = new javax.swing.JTextArea();
        pnAnalisarDespesas = new javax.swing.JPanel();
        pnMainTitle14 = new keeptoo.KGradientPanel();
        jLabel89 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane26 = new javax.swing.JScrollPane();
        taDespesas = new javax.swing.JTextArea();
        lbTotalDespesas = new javax.swing.JLabel();
        pnCadastrarFunc = new javax.swing.JPanel();
        pnCdVolTitle4 = new keeptoo.KGradientPanel();
        jLabel71 = new javax.swing.JLabel();
        pnDados2 = new javax.swing.JPanel();
        ftfGestorTelefone = new javax.swing.JFormattedTextField();
        jLabel72 = new javax.swing.JLabel();
        tfGestorNome = new javax.swing.JTextField();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        ftfGestorCPF = new javax.swing.JFormattedTextField();
        jLabel79 = new javax.swing.JLabel();
        cbGestorSexo = new javax.swing.JComboBox<>();
        btCadastrarFuncionario = new keeptoo.KButton();
        pnEndereco2 = new javax.swing.JPanel();
        jLabel81 = new javax.swing.JLabel();
        tfGestorRua = new javax.swing.JTextField();
        jLabel82 = new javax.swing.JLabel();
        tfGestorNumero = new javax.swing.JTextField();
        jLabel83 = new javax.swing.JLabel();
        tfGestorComplemento = new javax.swing.JTextField();
        jLabel84 = new javax.swing.JLabel();
        tfGestorBairro = new javax.swing.JTextField();
        tfGestorCidade = new javax.swing.JTextField();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        cbGestorUF = new javax.swing.JComboBox<>();
        ftfGestorCEP = new javax.swing.JFormattedTextField();
        jLabel99 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        tfGestorUsuario = new javax.swing.JTextField();
        tfGestorSenha = new javax.swing.JTextField();
        jLabel101 = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Morais Voluntariado");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImages(null);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        loginPanel.setkEndColor(new java.awt.Color(51, 0, 102));
        loginPanel.setkGradientFocus(90);
        loginPanel.setkStartColor(new java.awt.Color(54, 33, 89));
        loginPanel.setMaximumSize(new java.awt.Dimension(800, 500));
        loginPanel.setMinimumSize(new java.awt.Dimension(800, 500));
        loginPanel.setPreferredSize(new java.awt.Dimension(800, 500));
        loginPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tfLogin.setBackground(new Color(0,0,0,0));
        tfLogin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfLogin.setForeground(new java.awt.Color(255, 255, 255));
        tfLogin.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        tfLogin.setCaretColor(new java.awt.Color(255, 255, 255));
        tfLogin.setOpaque(false);
        tfLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfLoginActionPerformed(evt);
            }
        });
        loginPanel.add(tfLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 242, 200, 30));

        pfSenha.setBackground(new Color(0,0,0,0));
        pfSenha.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pfSenha.setForeground(new java.awt.Color(255, 255, 255));
        pfSenha.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pfSenha.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        pfSenha.setCaretColor(new java.awt.Color(255, 255, 255));
        pfSenha.setOpaque(false);
        pfSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pfSenhaActionPerformed(evt);
            }
        });
        loginPanel.add(pfSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 316, 200, 30));

        lbLogin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbLogin.setForeground(new java.awt.Color(255, 255, 255));
        lbLogin.setText("Login");
        loginPanel.add(lbLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 212, -1, -1));

        lbSenha.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbSenha.setForeground(new java.awt.Color(255, 255, 255));
        lbSenha.setText("Senha");
        loginPanel.add(lbSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 290, -1, -1));

        btLogin.setText("Entrar");
        btLogin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btLogin.setkEndColor(new java.awt.Color(233, 193, 253));
        btLogin.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btLogin.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btLogin.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btLogin.setkPressedColor(new java.awt.Color(250, 209, 254));
        btLogin.setkStartColor(new java.awt.Color(199, 96, 230));
        btLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLoginActionPerformed(evt);
            }
        });
        loginPanel.add(btLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(344, 364, 112, 32));

        iconMV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/logotext.png"))); // NOI18N
        loginPanel.add(iconMV, new org.netbeans.lib.awtextra.AbsoluteConstraints(276, 11, -1, -1));

        getContentPane().add(loginPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        ldMenus.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnFuncionario.setkBorderRadius(0);
        pnFuncionario.setkEndColor(new java.awt.Color(102, 0, 102));
        pnFuncionario.setkStartColor(new java.awt.Color(51, 0, 102));
        pnFuncionario.setPreferredSize(new java.awt.Dimension(160, 500));
        pnFuncionario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTituloFuncionario.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        lbTituloFuncionario.setForeground(new java.awt.Color(255, 255, 255));
        lbTituloFuncionario.setText("Funcionário");
        pnFuncionario.add(lbTituloFuncionario, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, -1));

        btEntregas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/delivery.png"))); // NOI18N
        btEntregas.setText("Gerenciar Entregas");
        btEntregas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btEntregas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btEntregas.setIconTextGap(0);
        btEntregas.setkBorderRadius(0);
        btEntregas.setkEndColor(new java.awt.Color(233, 193, 253));
        btEntregas.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btEntregas.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btEntregas.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btEntregas.setkPressedColor(new java.awt.Color(250, 209, 254));
        btEntregas.setkStartColor(new java.awt.Color(199, 96, 230));
        btEntregas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEntregasActionPerformed(evt);
            }
        });
        pnFuncionario.add(btEntregas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 160, 40));

        btCadastroVoluntario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/addfuncionario.png"))); // NOI18N
        btCadastroVoluntario.setText("Cadastrar Voluntário");
        btCadastroVoluntario.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btCadastroVoluntario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btCadastroVoluntario.setIconTextGap(1);
        btCadastroVoluntario.setkBorderRadius(0);
        btCadastroVoluntario.setkEndColor(new java.awt.Color(233, 193, 253));
        btCadastroVoluntario.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btCadastroVoluntario.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btCadastroVoluntario.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btCadastroVoluntario.setkPressedColor(new java.awt.Color(250, 209, 254));
        btCadastroVoluntario.setkStartColor(new java.awt.Color(199, 96, 230));
        btCadastroVoluntario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCadastroVoluntarioActionPerformed(evt);
            }
        });
        pnFuncionario.add(btCadastroVoluntario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 160, 40));

        btFuncInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/home.png"))); // NOI18N
        btFuncInicio.setText("Início");
        btFuncInicio.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btFuncInicio.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btFuncInicio.setIconTextGap(0);
        btFuncInicio.setkBorderRadius(0);
        btFuncInicio.setkEndColor(new java.awt.Color(233, 193, 253));
        btFuncInicio.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btFuncInicio.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btFuncInicio.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btFuncInicio.setkPressedColor(new java.awt.Color(250, 209, 254));
        btFuncInicio.setkStartColor(new java.awt.Color(199, 96, 230));
        btFuncInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFuncInicioActionPerformed(evt);
            }
        });
        pnFuncionario.add(btFuncInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 160, 40));

        btCadastroEvento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/new event.png"))); // NOI18N
        btCadastroEvento.setText("Cadastrar Evento");
        btCadastroEvento.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btCadastroEvento.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btCadastroEvento.setIconTextGap(0);
        btCadastroEvento.setkBorderRadius(0);
        btCadastroEvento.setkEndColor(new java.awt.Color(233, 193, 253));
        btCadastroEvento.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btCadastroEvento.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btCadastroEvento.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btCadastroEvento.setkPressedColor(new java.awt.Color(250, 209, 254));
        btCadastroEvento.setkStartColor(new java.awt.Color(199, 96, 230));
        btCadastroEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCadastroEventoActionPerformed(evt);
            }
        });
        pnFuncionario.add(btCadastroEvento, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 160, 40));

        btCadastroTrabalho.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/new work.png"))); // NOI18N
        btCadastroTrabalho.setText("Cadastrar Trabalho");
        btCadastroTrabalho.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btCadastroTrabalho.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btCadastroTrabalho.setIconTextGap(0);
        btCadastroTrabalho.setkBorderRadius(0);
        btCadastroTrabalho.setkEndColor(new java.awt.Color(233, 193, 253));
        btCadastroTrabalho.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btCadastroTrabalho.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btCadastroTrabalho.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btCadastroTrabalho.setkPressedColor(new java.awt.Color(250, 209, 254));
        btCadastroTrabalho.setkStartColor(new java.awt.Color(199, 96, 230));
        btCadastroTrabalho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCadastroTrabalhoActionPerformed(evt);
            }
        });
        pnFuncionario.add(btCadastroTrabalho, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 160, 40));

        btAceitarDoacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/donate.png"))); // NOI18N
        btAceitarDoacao.setText("Aceitar Doações");
        btAceitarDoacao.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btAceitarDoacao.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btAceitarDoacao.setIconTextGap(0);
        btAceitarDoacao.setkBorderRadius(0);
        btAceitarDoacao.setkEndColor(new java.awt.Color(233, 193, 253));
        btAceitarDoacao.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btAceitarDoacao.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btAceitarDoacao.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btAceitarDoacao.setkPressedColor(new java.awt.Color(250, 209, 254));
        btAceitarDoacao.setkStartColor(new java.awt.Color(199, 96, 230));
        btAceitarDoacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAceitarDoacaoActionPerformed(evt);
            }
        });
        pnFuncionario.add(btAceitarDoacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 160, 40));

        btRemoverVoluntario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/removefuncionario.png"))); // NOI18N
        btRemoverVoluntario.setText("Remover Volutário");
        btRemoverVoluntario.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btRemoverVoluntario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btRemoverVoluntario.setIconTextGap(0);
        btRemoverVoluntario.setkBorderRadius(0);
        btRemoverVoluntario.setkEndColor(new java.awt.Color(233, 193, 253));
        btRemoverVoluntario.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btRemoverVoluntario.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btRemoverVoluntario.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btRemoverVoluntario.setkPressedColor(new java.awt.Color(250, 209, 254));
        btRemoverVoluntario.setkStartColor(new java.awt.Color(199, 96, 230));
        btRemoverVoluntario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoverVoluntarioActionPerformed(evt);
            }
        });
        pnFuncionario.add(btRemoverVoluntario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 160, 40));

        btRelatorios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/archive.png"))); // NOI18N
        btRelatorios.setText("Relatórios");
        btRelatorios.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btRelatorios.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btRelatorios.setIconTextGap(0);
        btRelatorios.setkBorderRadius(0);
        btRelatorios.setkEndColor(new java.awt.Color(233, 193, 253));
        btRelatorios.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btRelatorios.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btRelatorios.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btRelatorios.setkPressedColor(new java.awt.Color(250, 209, 254));
        btRelatorios.setkStartColor(new java.awt.Color(199, 96, 230));
        btRelatorios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRelatoriosActionPerformed(evt);
            }
        });
        pnFuncionario.add(btRelatorios, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 160, 40));

        btFuncLogout1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/exit.png"))); // NOI18N
        btFuncLogout1.setText("Desconectar");
        btFuncLogout1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btFuncLogout1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btFuncLogout1.setIconTextGap(0);
        btFuncLogout1.setkBorderRadius(0);
        btFuncLogout1.setkEndColor(new java.awt.Color(233, 193, 253));
        btFuncLogout1.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btFuncLogout1.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btFuncLogout1.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btFuncLogout1.setkPressedColor(new java.awt.Color(250, 209, 254));
        btFuncLogout1.setkStartColor(new java.awt.Color(199, 96, 230));
        btFuncLogout1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFuncLogout1ActionPerformed(evt);
            }
        });
        pnFuncionario.add(btFuncLogout1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 160, 40));

        btMeusEventosFunc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/eventicon.png"))); // NOI18N
        btMeusEventosFunc.setText("Meus Eventos");
        btMeusEventosFunc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btMeusEventosFunc.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btMeusEventosFunc.setIconTextGap(0);
        btMeusEventosFunc.setkBorderRadius(0);
        btMeusEventosFunc.setkEndColor(new java.awt.Color(233, 193, 253));
        btMeusEventosFunc.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btMeusEventosFunc.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btMeusEventosFunc.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btMeusEventosFunc.setkPressedColor(new java.awt.Color(250, 209, 254));
        btMeusEventosFunc.setkStartColor(new java.awt.Color(199, 96, 230));
        btMeusEventosFunc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btMeusEventosFuncActionPerformed(evt);
            }
        });
        pnFuncionario.add(btMeusEventosFunc, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 160, 40));

        ldMenus.add(pnFuncionario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnVoluntarioPF.setkBorderRadius(0);
        pnVoluntarioPF.setkEndColor(new java.awt.Color(102, 0, 102));
        pnVoluntarioPF.setkStartColor(new java.awt.Color(51, 0, 102));
        pnVoluntarioPF.setPreferredSize(new java.awt.Dimension(160, 500));
        pnVoluntarioPF.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTituloVolPF.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        lbTituloVolPF.setForeground(new java.awt.Color(255, 255, 255));
        lbTituloVolPF.setText("Voluntário PF");
        pnVoluntarioPF.add(lbTituloVolPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 100, -1));

        btVolPFInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/home.png"))); // NOI18N
        btVolPFInicio.setText("Início");
        btVolPFInicio.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btVolPFInicio.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btVolPFInicio.setIconTextGap(0);
        btVolPFInicio.setkBorderRadius(0);
        btVolPFInicio.setkEndColor(new java.awt.Color(233, 193, 253));
        btVolPFInicio.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btVolPFInicio.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btVolPFInicio.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btVolPFInicio.setkPressedColor(new java.awt.Color(250, 209, 254));
        btVolPFInicio.setkStartColor(new java.awt.Color(199, 96, 230));
        btVolPFInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btVolPFInicioActionPerformed(evt);
            }
        });
        pnVoluntarioPF.add(btVolPFInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 160, 40));

        btTrabalharEvento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/new work.png"))); // NOI18N
        btTrabalharEvento.setText("  Trabalhar em Evento");
        btTrabalharEvento.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btTrabalharEvento.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btTrabalharEvento.setIconTextGap(0);
        btTrabalharEvento.setkBorderRadius(0);
        btTrabalharEvento.setkEndColor(new java.awt.Color(233, 193, 253));
        btTrabalharEvento.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btTrabalharEvento.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btTrabalharEvento.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btTrabalharEvento.setkPressedColor(new java.awt.Color(250, 209, 254));
        btTrabalharEvento.setkStartColor(new java.awt.Color(199, 96, 230));
        btTrabalharEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTrabalharEventoActionPerformed(evt);
            }
        });
        pnVoluntarioPF.add(btTrabalharEvento, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 160, 40));

        btDoarPF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/donate.png"))); // NOI18N
        btDoarPF.setText("Fazer doação");
        btDoarPF.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btDoarPF.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btDoarPF.setIconTextGap(0);
        btDoarPF.setkBorderRadius(0);
        btDoarPF.setkEndColor(new java.awt.Color(233, 193, 253));
        btDoarPF.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btDoarPF.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btDoarPF.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btDoarPF.setkPressedColor(new java.awt.Color(250, 209, 254));
        btDoarPF.setkStartColor(new java.awt.Color(199, 96, 230));
        btDoarPF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDoarPFActionPerformed(evt);
            }
        });
        pnVoluntarioPF.add(btDoarPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 160, 40));

        btVolPFLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/exit.png"))); // NOI18N
        btVolPFLogout.setText("Desconectar");
        btVolPFLogout.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btVolPFLogout.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btVolPFLogout.setIconTextGap(0);
        btVolPFLogout.setkBorderRadius(0);
        btVolPFLogout.setkEndColor(new java.awt.Color(233, 193, 253));
        btVolPFLogout.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btVolPFLogout.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btVolPFLogout.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btVolPFLogout.setkPressedColor(new java.awt.Color(250, 209, 254));
        btVolPFLogout.setkStartColor(new java.awt.Color(199, 96, 230));
        btVolPFLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btVolPFLogoutActionPerformed(evt);
            }
        });
        pnVoluntarioPF.add(btVolPFLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 160, 40));

        btMeusEventosPF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/eventicon.png"))); // NOI18N
        btMeusEventosPF.setText("Meus Eventos");
        btMeusEventosPF.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btMeusEventosPF.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btMeusEventosPF.setIconTextGap(0);
        btMeusEventosPF.setkBorderRadius(0);
        btMeusEventosPF.setkEndColor(new java.awt.Color(233, 193, 253));
        btMeusEventosPF.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btMeusEventosPF.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btMeusEventosPF.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btMeusEventosPF.setkPressedColor(new java.awt.Color(250, 209, 254));
        btMeusEventosPF.setkStartColor(new java.awt.Color(199, 96, 230));
        btMeusEventosPF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btMeusEventosPFActionPerformed(evt);
            }
        });
        pnVoluntarioPF.add(btMeusEventosPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 160, 40));

        btMinhasDoacoes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/donates.png"))); // NOI18N
        btMinhasDoacoes.setText("Minhas doações");
        btMinhasDoacoes.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btMinhasDoacoes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btMinhasDoacoes.setIconTextGap(0);
        btMinhasDoacoes.setkBorderRadius(0);
        btMinhasDoacoes.setkEndColor(new java.awt.Color(233, 193, 253));
        btMinhasDoacoes.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btMinhasDoacoes.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btMinhasDoacoes.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btMinhasDoacoes.setkPressedColor(new java.awt.Color(250, 209, 254));
        btMinhasDoacoes.setkStartColor(new java.awt.Color(199, 96, 230));
        btMinhasDoacoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btMinhasDoacoesActionPerformed(evt);
            }
        });
        pnVoluntarioPF.add(btMinhasDoacoes, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 160, 40));

        ldMenus.add(pnVoluntarioPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnVoluntarioPJ.setkBorderRadius(0);
        pnVoluntarioPJ.setkEndColor(new java.awt.Color(102, 0, 102));
        pnVoluntarioPJ.setkStartColor(new java.awt.Color(51, 0, 102));
        pnVoluntarioPJ.setPreferredSize(new java.awt.Dimension(160, 500));
        pnVoluntarioPJ.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTituloVolPF1.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        lbTituloVolPF1.setForeground(new java.awt.Color(255, 255, 255));
        lbTituloVolPF1.setText("Voluntário PJ");
        pnVoluntarioPJ.add(lbTituloVolPF1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 100, -1));

        btVolPJInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/home.png"))); // NOI18N
        btVolPJInicio.setText("Início");
        btVolPJInicio.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btVolPJInicio.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btVolPJInicio.setIconTextGap(0);
        btVolPJInicio.setkBorderRadius(0);
        btVolPJInicio.setkEndColor(new java.awt.Color(233, 193, 253));
        btVolPJInicio.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btVolPJInicio.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btVolPJInicio.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btVolPJInicio.setkPressedColor(new java.awt.Color(250, 209, 254));
        btVolPJInicio.setkStartColor(new java.awt.Color(199, 96, 230));
        btVolPJInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btVolPJInicioActionPerformed(evt);
            }
        });
        pnVoluntarioPJ.add(btVolPJInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 160, 40));

        btPatrocinarEvento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/new work.png"))); // NOI18N
        btPatrocinarEvento.setText("  Patrocinar Evento");
        btPatrocinarEvento.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btPatrocinarEvento.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btPatrocinarEvento.setIconTextGap(0);
        btPatrocinarEvento.setkBorderRadius(0);
        btPatrocinarEvento.setkEndColor(new java.awt.Color(233, 193, 253));
        btPatrocinarEvento.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btPatrocinarEvento.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btPatrocinarEvento.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btPatrocinarEvento.setkPressedColor(new java.awt.Color(250, 209, 254));
        btPatrocinarEvento.setkStartColor(new java.awt.Color(199, 96, 230));
        btPatrocinarEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPatrocinarEventoActionPerformed(evt);
            }
        });
        pnVoluntarioPJ.add(btPatrocinarEvento, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 160, 40));

        btDoarPJ.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/donate.png"))); // NOI18N
        btDoarPJ.setText("Fazer doação");
        btDoarPJ.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btDoarPJ.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btDoarPJ.setIconTextGap(0);
        btDoarPJ.setkBorderRadius(0);
        btDoarPJ.setkEndColor(new java.awt.Color(233, 193, 253));
        btDoarPJ.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btDoarPJ.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btDoarPJ.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btDoarPJ.setkPressedColor(new java.awt.Color(250, 209, 254));
        btDoarPJ.setkStartColor(new java.awt.Color(199, 96, 230));
        btDoarPJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDoarPJActionPerformed(evt);
            }
        });
        pnVoluntarioPJ.add(btDoarPJ, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 160, 40));

        btVolPJLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/exit.png"))); // NOI18N
        btVolPJLogout.setText("Desconectar");
        btVolPJLogout.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btVolPJLogout.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btVolPJLogout.setIconTextGap(0);
        btVolPJLogout.setkBorderRadius(0);
        btVolPJLogout.setkEndColor(new java.awt.Color(233, 193, 253));
        btVolPJLogout.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btVolPJLogout.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btVolPJLogout.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btVolPJLogout.setkPressedColor(new java.awt.Color(250, 209, 254));
        btVolPJLogout.setkStartColor(new java.awt.Color(199, 96, 230));
        btVolPJLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btVolPJLogoutActionPerformed(evt);
            }
        });
        pnVoluntarioPJ.add(btVolPJLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 160, 40));

        btMeusEventosPJ.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/eventicon.png"))); // NOI18N
        btMeusEventosPJ.setText("Meus Eventos");
        btMeusEventosPJ.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btMeusEventosPJ.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btMeusEventosPJ.setIconTextGap(0);
        btMeusEventosPJ.setkBorderRadius(0);
        btMeusEventosPJ.setkEndColor(new java.awt.Color(233, 193, 253));
        btMeusEventosPJ.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btMeusEventosPJ.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btMeusEventosPJ.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btMeusEventosPJ.setkPressedColor(new java.awt.Color(250, 209, 254));
        btMeusEventosPJ.setkStartColor(new java.awt.Color(199, 96, 230));
        btMeusEventosPJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btMeusEventosPJActionPerformed(evt);
            }
        });
        pnVoluntarioPJ.add(btMeusEventosPJ, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 160, 40));

        btMinhasDoacoesPJ.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/donates.png"))); // NOI18N
        btMinhasDoacoesPJ.setText("Minhas doações");
        btMinhasDoacoesPJ.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btMinhasDoacoesPJ.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btMinhasDoacoesPJ.setIconTextGap(0);
        btMinhasDoacoesPJ.setkBorderRadius(0);
        btMinhasDoacoesPJ.setkEndColor(new java.awt.Color(233, 193, 253));
        btMinhasDoacoesPJ.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btMinhasDoacoesPJ.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btMinhasDoacoesPJ.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btMinhasDoacoesPJ.setkPressedColor(new java.awt.Color(250, 209, 254));
        btMinhasDoacoesPJ.setkStartColor(new java.awt.Color(199, 96, 230));
        btMinhasDoacoesPJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btMinhasDoacoesPJActionPerformed(evt);
            }
        });
        pnVoluntarioPJ.add(btMinhasDoacoesPJ, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 160, 40));

        ldMenus.add(pnVoluntarioPJ, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnGestor.setkBorderRadius(0);
        pnGestor.setkEndColor(new java.awt.Color(102, 0, 102));
        pnGestor.setkStartColor(new java.awt.Color(51, 0, 102));
        pnGestor.setPreferredSize(new java.awt.Dimension(160, 500));
        pnGestor.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTituloVolPF2.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        lbTituloVolPF2.setForeground(new java.awt.Color(255, 255, 255));
        lbTituloVolPF2.setText("Gestor");
        pnGestor.add(lbTituloVolPF2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 60, -1));

        btGestorInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/home.png"))); // NOI18N
        btGestorInicio.setText("Início");
        btGestorInicio.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btGestorInicio.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btGestorInicio.setIconTextGap(0);
        btGestorInicio.setkBorderRadius(0);
        btGestorInicio.setkEndColor(new java.awt.Color(233, 193, 253));
        btGestorInicio.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btGestorInicio.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btGestorInicio.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btGestorInicio.setkPressedColor(new java.awt.Color(250, 209, 254));
        btGestorInicio.setkStartColor(new java.awt.Color(199, 96, 230));
        btGestorInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGestorInicioActionPerformed(evt);
            }
        });
        pnGestor.add(btGestorInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 160, 40));

        btGestorAnalisarReceitas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/receitas.png"))); // NOI18N
        btGestorAnalisarReceitas.setText("Analisar Receitas");
        btGestorAnalisarReceitas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btGestorAnalisarReceitas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btGestorAnalisarReceitas.setIconTextGap(0);
        btGestorAnalisarReceitas.setkBorderRadius(0);
        btGestorAnalisarReceitas.setkEndColor(new java.awt.Color(233, 193, 253));
        btGestorAnalisarReceitas.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btGestorAnalisarReceitas.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btGestorAnalisarReceitas.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btGestorAnalisarReceitas.setkPressedColor(new java.awt.Color(250, 209, 254));
        btGestorAnalisarReceitas.setkStartColor(new java.awt.Color(199, 96, 230));
        btGestorAnalisarReceitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGestorAnalisarReceitasActionPerformed(evt);
            }
        });
        pnGestor.add(btGestorAnalisarReceitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 160, 40));

        btGestorAnalisarDespesas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/despesas.png"))); // NOI18N
        btGestorAnalisarDespesas.setText("Analisar Despesas");
        btGestorAnalisarDespesas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btGestorAnalisarDespesas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btGestorAnalisarDespesas.setIconTextGap(0);
        btGestorAnalisarDespesas.setkBorderRadius(0);
        btGestorAnalisarDespesas.setkEndColor(new java.awt.Color(233, 193, 253));
        btGestorAnalisarDespesas.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btGestorAnalisarDespesas.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btGestorAnalisarDespesas.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btGestorAnalisarDespesas.setkPressedColor(new java.awt.Color(250, 209, 254));
        btGestorAnalisarDespesas.setkStartColor(new java.awt.Color(199, 96, 230));
        btGestorAnalisarDespesas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGestorAnalisarDespesasActionPerformed(evt);
            }
        });
        pnGestor.add(btGestorAnalisarDespesas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 160, 40));

        btGestorDesconectar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/exit.png"))); // NOI18N
        btGestorDesconectar.setText("Desconectar");
        btGestorDesconectar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btGestorDesconectar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btGestorDesconectar.setIconTextGap(0);
        btGestorDesconectar.setkBorderRadius(0);
        btGestorDesconectar.setkEndColor(new java.awt.Color(233, 193, 253));
        btGestorDesconectar.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btGestorDesconectar.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btGestorDesconectar.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btGestorDesconectar.setkPressedColor(new java.awt.Color(250, 209, 254));
        btGestorDesconectar.setkStartColor(new java.awt.Color(199, 96, 230));
        btGestorDesconectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGestorDesconectarActionPerformed(evt);
            }
        });
        pnGestor.add(btGestorDesconectar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 160, 40));

        btGestorCadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/cadastrarfunc.png"))); // NOI18N
        btGestorCadastrar.setText("       Cadastrar Funcionário");
        btGestorCadastrar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btGestorCadastrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btGestorCadastrar.setIconTextGap(0);
        btGestorCadastrar.setkBorderRadius(0);
        btGestorCadastrar.setkEndColor(new java.awt.Color(233, 193, 253));
        btGestorCadastrar.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btGestorCadastrar.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btGestorCadastrar.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btGestorCadastrar.setkPressedColor(new java.awt.Color(250, 209, 254));
        btGestorCadastrar.setkStartColor(new java.awt.Color(199, 96, 230));
        btGestorCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGestorCadastrarActionPerformed(evt);
            }
        });
        pnGestor.add(btGestorCadastrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 160, 40));

        btGestorRelatorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/relatoriofinanc.png"))); // NOI18N
        btGestorRelatorio.setText("       Relatório Financeiro");
        btGestorRelatorio.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btGestorRelatorio.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btGestorRelatorio.setIconTextGap(0);
        btGestorRelatorio.setkBorderRadius(0);
        btGestorRelatorio.setkEndColor(new java.awt.Color(233, 193, 253));
        btGestorRelatorio.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btGestorRelatorio.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btGestorRelatorio.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btGestorRelatorio.setkPressedColor(new java.awt.Color(250, 209, 254));
        btGestorRelatorio.setkStartColor(new java.awt.Color(199, 96, 230));
        btGestorRelatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGestorRelatorioActionPerformed(evt);
            }
        });
        pnGestor.add(btGestorRelatorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 160, 40));

        ldMenus.add(pnGestor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(ldMenus, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 500));

        ldFuncionario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnFuncMain.setBackground(new java.awt.Color(204, 204, 204));
        pnFuncMain.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnMainTitle.setkBorderRadius(0);
        pnMainTitle.setkEndColor(new java.awt.Color(204, 0, 204));
        pnMainTitle.setkStartColor(new java.awt.Color(51, 0, 102));
        pnMainTitle.setPreferredSize(new java.awt.Dimension(640, 40));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Início");

        javax.swing.GroupLayout pnMainTitleLayout = new javax.swing.GroupLayout(pnMainTitle);
        pnMainTitle.setLayout(pnMainTitleLayout);
        pnMainTitleLayout.setHorizontalGroup(
            pnMainTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnMainTitleLayout.createSequentialGroup()
                .addGap(289, 289, 289)
                .addComponent(jLabel2)
                .addContainerGap(304, Short.MAX_VALUE))
        );
        pnMainTitleLayout.setVerticalGroup(
            pnMainTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnMainTitleLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap())
        );

        pnFuncMain.add(pnMainTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 640, 40));

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Seus dados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 11))); // NOI18N

        taInfoFuncionario.setEditable(false);
        taInfoFuncionario.setColumns(20);
        taInfoFuncionario.setRows(5);
        jScrollPane1.setViewportView(taInfoFuncionario);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 214, Short.MAX_VALUE))
        );

        pnFuncMain.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 620, 440));

        ldFuncionario.add(pnFuncMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        pnCdVolPF.setBackground(new java.awt.Color(204, 204, 204));
        pnCdVolPF.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnCdVolTitle.setkBorderRadius(0);
        pnCdVolTitle.setkEndColor(new java.awt.Color(204, 0, 204));
        pnCdVolTitle.setkStartColor(new java.awt.Color(51, 0, 102));
        pnCdVolTitle.setPreferredSize(new java.awt.Dimension(640, 40));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cadastrar Voluntário");

        javax.swing.GroupLayout pnCdVolTitleLayout = new javax.swing.GroupLayout(pnCdVolTitle);
        pnCdVolTitle.setLayout(pnCdVolTitleLayout);
        pnCdVolTitleLayout.setHorizontalGroup(
            pnCdVolTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCdVolTitleLayout.createSequentialGroup()
                .addGap(232, 232, 232)
                .addComponent(jLabel1)
                .addContainerGap(232, Short.MAX_VALUE))
        );
        pnCdVolTitleLayout.setVerticalGroup(
            pnCdVolTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCdVolTitleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnCdVolPF.add(pnCdVolTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, -1, -1));

        pnDados.setBackground(new java.awt.Color(204, 204, 204));
        pnDados.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, new java.awt.Color(255, 255, 255), null, new java.awt.Color(102, 0, 102)));

        try {
            ftfVolPFTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel3.setText("Nome");

        jLabel4.setText("Telefone");

        jLabel5.setText("CPF");

        try {
            ftfVolPFCPF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel6.setText("Sexo");

        cbVolPFSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Feminino" }));

        jLabel7.setText("Turno");

        cbVolPFTurno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Manhã", "Tarde", "Noite" }));

        javax.swing.GroupLayout pnDadosLayout = new javax.swing.GroupLayout(pnDados);
        pnDados.setLayout(pnDadosLayout);
        pnDadosLayout.setHorizontalGroup(
            pnDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDadosLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel3)
                .addGap(4, 4, 4)
                .addComponent(tfVolPFNome, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnDadosLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel4)
                .addGap(4, 4, 4)
                .addComponent(ftfVolPFTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnDadosLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel5)
                .addGap(4, 4, 4)
                .addComponent(ftfVolPFCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnDadosLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel6)
                .addGap(4, 4, 4)
                .addComponent(cbVolPFSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnDadosLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel7)
                .addGap(4, 4, 4)
                .addComponent(cbVolPFTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnDadosLayout.setVerticalGroup(
            pnDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDadosLayout.createSequentialGroup()
                .addGroup(pnDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnDadosLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel3))
                    .addComponent(tfVolPFNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(pnDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnDadosLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel4))
                    .addComponent(ftfVolPFTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(pnDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnDadosLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel5))
                    .addComponent(ftfVolPFCPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(pnDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnDadosLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel6))
                    .addComponent(cbVolPFSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnDadosLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel7))
                    .addComponent(cbVolPFTurno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(118, Short.MAX_VALUE))
        );

        pnCdVolPF.add(pnDados, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 60, 168, 250));

        btCadastrarVolPF.setText("Cadastrar");
        btCadastrarVolPF.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btCadastrarVolPF.setkEndColor(new java.awt.Color(233, 193, 253));
        btCadastrarVolPF.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btCadastrarVolPF.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btCadastrarVolPF.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btCadastrarVolPF.setkPressedColor(new java.awt.Color(250, 209, 254));
        btCadastrarVolPF.setkStartColor(new java.awt.Color(199, 96, 230));
        btCadastrarVolPF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCadastrarVolPFActionPerformed(evt);
            }
        });
        pnCdVolPF.add(btCadastrarVolPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 320, 110, 30));

        pnEndereco.setBackground(new java.awt.Color(204, 204, 204));
        pnEndereco.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, null, null, new java.awt.Color(102, 0, 102)));

        jLabel8.setText("Rua");

        jLabel9.setText("Número");

        jLabel10.setText("Complemento");

        jLabel11.setText("Bairro");

        jLabel12.setText("Cidade");

        jLabel13.setText("UF");

        jLabel14.setText("CEP");

        cbVolPFUF.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));

        try {
            ftfVolPFCEP.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout pnEnderecoLayout = new javax.swing.GroupLayout(pnEndereco);
        pnEndereco.setLayout(pnEnderecoLayout);
        pnEnderecoLayout.setHorizontalGroup(
            pnEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnEnderecoLayout.createSequentialGroup()
                .addGroup(pnEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnEnderecoLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfVolPFBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnEnderecoLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfVolPFRua, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnEnderecoLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfVolPFCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnEnderecoLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfVolPFComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnEnderecoLayout.createSequentialGroup()
                        .addGroup(pnEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel9)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ftfVolPFCEP, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(tfVolPFNumero, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cbVolPFUF, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(0, 25, Short.MAX_VALUE))
        );
        pnEnderecoLayout.setVerticalGroup(
            pnEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnEnderecoLayout.createSequentialGroup()
                .addGroup(pnEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel8)
                    .addComponent(tfVolPFRua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel11)
                    .addComponent(tfVolPFBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel10)
                    .addComponent(tfVolPFComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfVolPFCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ftfVolPFCEP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfVolPFNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbVolPFUF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnCdVolPF.add(pnEndereco, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 60, 200, 250));

        jLabel15.setText("Endereço");
        pnCdVolPF.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 46, -1, -1));

        jLabel16.setText("Dados");
        pnCdVolPF.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 44, -1, 20));

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, null, null, new java.awt.Color(102, 0, 102)));

        jLabel17.setText("Usuário");

        jLabel18.setText("Senha");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfVolPFUsuario)
                    .addComponent(tfVolPFSenha, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(tfVolPFUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(tfVolPFSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(187, Short.MAX_VALUE))
        );

        pnCdVolPF.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 60, 190, 250));

        jLabel20.setText("Conta");
        pnCdVolPF.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 44, -1, 20));

        ldFuncionario.add(pnCdVolPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        pnCdVolPJ.setBackground(new java.awt.Color(204, 204, 204));
        pnCdVolPJ.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnCdVolTitle1.setkBorderRadius(0);
        pnCdVolTitle1.setkEndColor(new java.awt.Color(204, 0, 204));
        pnCdVolTitle1.setkStartColor(new java.awt.Color(51, 0, 102));
        pnCdVolTitle1.setPreferredSize(new java.awt.Dimension(640, 40));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Cadastrar Voluntário");

        javax.swing.GroupLayout pnCdVolTitle1Layout = new javax.swing.GroupLayout(pnCdVolTitle1);
        pnCdVolTitle1.setLayout(pnCdVolTitle1Layout);
        pnCdVolTitle1Layout.setHorizontalGroup(
            pnCdVolTitle1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCdVolTitle1Layout.createSequentialGroup()
                .addGap(232, 232, 232)
                .addComponent(jLabel19)
                .addContainerGap(232, Short.MAX_VALUE))
        );
        pnCdVolTitle1Layout.setVerticalGroup(
            pnCdVolTitle1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCdVolTitle1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnCdVolPJ.add(pnCdVolTitle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, -1, -1));

        pnDados1.setBackground(new java.awt.Color(204, 204, 204));
        pnDados1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, new java.awt.Color(255, 255, 255), null, new java.awt.Color(102, 0, 102)));

        try {
            ftfVolPJTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel21.setText("Nome Fantasia");

        jLabel22.setText("Telefone");

        jLabel23.setText("CNPJ");

        try {
            ftfVolPJCNPJ.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###.###/####-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel24.setText("Inscrição Estadual");

        try {
            ftfVolPJIE.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###.####-#")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout pnDados1Layout = new javax.swing.GroupLayout(pnDados1);
        pnDados1.setLayout(pnDados1Layout);
        pnDados1Layout.setHorizontalGroup(
            pnDados1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDados1Layout.createSequentialGroup()
                .addGroup(pnDados1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnDados1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ftfVolPJIE, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnDados1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnDados1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnDados1Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ftfVolPJCNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnDados1Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ftfVolPJTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnDados1Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfVolPJNome, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        pnDados1Layout.setVerticalGroup(
            pnDados1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDados1Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(pnDados1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(tfVolPJNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(pnDados1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ftfVolPJTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addGap(6, 6, 6)
                .addGroup(pnDados1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(ftfVolPJCNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(pnDados1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ftfVolPJIE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addContainerGap(143, Short.MAX_VALUE))
        );

        pnCdVolPJ.add(pnDados1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 60, 220, 250));

        btCadastrarVolPJ.setText("Cadastrar");
        btCadastrarVolPJ.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btCadastrarVolPJ.setkEndColor(new java.awt.Color(233, 193, 253));
        btCadastrarVolPJ.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btCadastrarVolPJ.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btCadastrarVolPJ.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btCadastrarVolPJ.setkPressedColor(new java.awt.Color(250, 209, 254));
        btCadastrarVolPJ.setkStartColor(new java.awt.Color(199, 96, 230));
        btCadastrarVolPJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCadastrarVolPJActionPerformed(evt);
            }
        });
        pnCdVolPJ.add(btCadastrarVolPJ, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 320, 110, 30));

        pnEndereco1.setBackground(new java.awt.Color(204, 204, 204));
        pnEndereco1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, null, null, new java.awt.Color(102, 0, 102)));

        jLabel26.setText("Rua");

        jLabel27.setText("Número");

        jLabel28.setText("Complemento");

        jLabel29.setText("Bairro");

        jLabel30.setText("Cidade");

        jLabel31.setText("UF");

        jLabel32.setText("CEP");

        cbVolPJUF.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));

        try {
            ftfVolPJCEP.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout pnEndereco1Layout = new javax.swing.GroupLayout(pnEndereco1);
        pnEndereco1.setLayout(pnEndereco1Layout);
        pnEndereco1Layout.setHorizontalGroup(
            pnEndereco1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnEndereco1Layout.createSequentialGroup()
                .addGroup(pnEndereco1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnEndereco1Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfVolPJBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnEndereco1Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfVolPJRua, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnEndereco1Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfVolPJCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnEndereco1Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfVolPJComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnEndereco1Layout.createSequentialGroup()
                        .addGroup(pnEndereco1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel32)
                            .addComponent(jLabel27)
                            .addComponent(jLabel31))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnEndereco1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ftfVolPJCEP, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnEndereco1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(tfVolPJNumero, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cbVolPJUF, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(0, 25, Short.MAX_VALUE))
        );
        pnEndereco1Layout.setVerticalGroup(
            pnEndereco1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnEndereco1Layout.createSequentialGroup()
                .addGroup(pnEndereco1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel26)
                    .addComponent(tfVolPJRua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnEndereco1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel29)
                    .addComponent(tfVolPJBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnEndereco1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel28)
                    .addComponent(tfVolPJComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnEndereco1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfVolPJCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnEndereco1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ftfVolPJCEP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnEndereco1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfVolPJNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnEndereco1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbVolPJUF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31))
                .addContainerGap(68, Short.MAX_VALUE))
        );

        pnCdVolPJ.add(pnEndereco1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 60, 200, 250));

        jLabel33.setText("Endereço");
        pnCdVolPJ.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 40, -1, 20));

        jLabel34.setText("Dados");
        pnCdVolPJ.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 40, -1, 20));

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, null, null, new java.awt.Color(102, 0, 102)));

        jLabel35.setText("Usuário");

        jLabel36.setText("Senha");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfVolPJUsuario)
                    .addComponent(tfVolPJSenha, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(tfVolPJUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(tfVolPJSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(187, Short.MAX_VALUE))
        );

        pnCdVolPJ.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 60, 170, 250));

        jLabel37.setText("Conta");
        pnCdVolPJ.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 40, -1, 20));

        ldFuncionario.add(pnCdVolPJ, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        pnCdTrabalho.setBackground(new java.awt.Color(204, 204, 204));
        pnCdTrabalho.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnMainTitle2.setkBorderRadius(0);
        pnMainTitle2.setkEndColor(new java.awt.Color(204, 0, 204));
        pnMainTitle2.setkStartColor(new java.awt.Color(51, 0, 102));
        pnMainTitle2.setPreferredSize(new java.awt.Dimension(640, 40));

        jLabel49.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 255));
        jLabel49.setText("Cadastrar Trabalho");

        javax.swing.GroupLayout pnMainTitle2Layout = new javax.swing.GroupLayout(pnMainTitle2);
        pnMainTitle2.setLayout(pnMainTitle2Layout);
        pnMainTitle2Layout.setHorizontalGroup(
            pnMainTitle2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnMainTitle2Layout.createSequentialGroup()
                .addContainerGap(234, Short.MAX_VALUE)
                .addComponent(jLabel49)
                .addGap(244, 244, 244))
        );
        pnMainTitle2Layout.setVerticalGroup(
            pnMainTitle2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnMainTitle2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel49)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnCdTrabalho.add(pnMainTitle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 640, 40));

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));
        jPanel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, null, null, new java.awt.Color(102, 0, 102)));

        jLabel50.setText("Nome");

        jScrollPane4.setViewportView(tpDescricaoTrabalho);

        jLabel52.setText("Descrição");

        btRemoverTrabalho.setText("Remover");
        btRemoverTrabalho.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btRemoverTrabalho.setkEndColor(new java.awt.Color(233, 193, 253));
        btRemoverTrabalho.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btRemoverTrabalho.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btRemoverTrabalho.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btRemoverTrabalho.setkPressedColor(new java.awt.Color(250, 209, 254));
        btRemoverTrabalho.setkStartColor(new java.awt.Color(199, 96, 230));
        btRemoverTrabalho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoverTrabalhoActionPerformed(evt);
            }
        });

        btAdicionarTrabalho.setText("Adicionar");
        btAdicionarTrabalho.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btAdicionarTrabalho.setkEndColor(new java.awt.Color(233, 193, 253));
        btAdicionarTrabalho.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btAdicionarTrabalho.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btAdicionarTrabalho.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btAdicionarTrabalho.setkPressedColor(new java.awt.Color(250, 209, 254));
        btAdicionarTrabalho.setkStartColor(new java.awt.Color(199, 96, 230));
        btAdicionarTrabalho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdicionarTrabalhoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel52)
                    .addComponent(jLabel50))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfNomeTrabalho, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btRemoverTrabalho, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btAdicionarTrabalho, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btAdicionarTrabalho, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btRemoverTrabalho, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfNomeTrabalho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel50))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel52)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE))))
                .addContainerGap())
        );

        pnCdTrabalho.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 60, 410, 140));

        jLabel55.setText("Dados");
        pnCdTrabalho.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 40, -1, 20));

        jLTrabalhos.setModel(converterFuncionariosLista());
        jScrollPane6.setViewportView(jLTrabalhos);

        pnCdTrabalho.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 230, 410, 90));

        jLabel56.setText("Selecionar Evento");
        pnCdTrabalho.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 320, -1, 20));

        jLabel57.setText("Trabalhos");
        pnCdTrabalho.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 210, -1, 20));

        jLEventos.setModel(converterFuncionariosLista());
        jScrollPane7.setViewportView(jLEventos);

        pnCdTrabalho.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 340, 410, 80));

        btInfoEvento.setText("Ver informações");
        btInfoEvento.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btInfoEvento.setkEndColor(new java.awt.Color(233, 193, 253));
        btInfoEvento.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btInfoEvento.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btInfoEvento.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btInfoEvento.setkPressedColor(new java.awt.Color(250, 209, 254));
        btInfoEvento.setkStartColor(new java.awt.Color(199, 96, 230));
        btInfoEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btInfoEventoActionPerformed(evt);
            }
        });
        pnCdTrabalho.add(btInfoEvento, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 430, 100, 20));

        btCadastrarTrabalho.setText("Cadastrar Trabalho");
        btCadastrarTrabalho.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btCadastrarTrabalho.setkEndColor(new java.awt.Color(233, 193, 253));
        btCadastrarTrabalho.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btCadastrarTrabalho.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btCadastrarTrabalho.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btCadastrarTrabalho.setkPressedColor(new java.awt.Color(250, 209, 254));
        btCadastrarTrabalho.setkStartColor(new java.awt.Color(199, 96, 230));
        btCadastrarTrabalho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCadastrarTrabalhoActionPerformed(evt);
            }
        });
        pnCdTrabalho.add(btCadastrarTrabalho, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 440, 100, 50));

        ldFuncionario.add(pnCdTrabalho, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        pnRvVol.setBackground(new java.awt.Color(204, 204, 204));
        pnRvVol.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnMainTitle3.setkBorderRadius(0);
        pnMainTitle3.setkEndColor(new java.awt.Color(204, 0, 204));
        pnMainTitle3.setkStartColor(new java.awt.Color(51, 0, 102));
        pnMainTitle3.setPreferredSize(new java.awt.Dimension(640, 40));

        jLabel51.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setText("Remover Voluntário");

        javax.swing.GroupLayout pnMainTitle3Layout = new javax.swing.GroupLayout(pnMainTitle3);
        pnMainTitle3.setLayout(pnMainTitle3Layout);
        pnMainTitle3Layout.setHorizontalGroup(
            pnMainTitle3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnMainTitle3Layout.createSequentialGroup()
                .addContainerGap(239, Short.MAX_VALUE)
                .addComponent(jLabel51)
                .addGap(230, 230, 230))
        );
        pnMainTitle3Layout.setVerticalGroup(
            pnMainTitle3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnMainTitle3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel51)
                .addContainerGap())
        );

        pnRvVol.add(pnMainTitle3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 640, 40));

        jLVoluntarios.setModel(converterVoluntariosLista());
        jScrollPane9.setViewportView(jLVoluntarios);

        pnRvVol.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 80, 410, 240));

        jLabel59.setText("Selecione o voluntário que deseja remover");
        pnRvVol.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 60, -1, 20));

        btInfoVol.setText("Ver informações");
        btInfoVol.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btInfoVol.setkEndColor(new java.awt.Color(233, 193, 253));
        btInfoVol.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btInfoVol.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btInfoVol.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btInfoVol.setkPressedColor(new java.awt.Color(250, 209, 254));
        btInfoVol.setkStartColor(new java.awt.Color(199, 96, 230));
        btInfoVol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btInfoVolActionPerformed(evt);
            }
        });
        pnRvVol.add(btInfoVol, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 330, 100, 20));

        btRemoverVol.setText("Remover");
        btRemoverVol.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btRemoverVol.setkEndColor(new java.awt.Color(233, 193, 253));
        btRemoverVol.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btRemoverVol.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btRemoverVol.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btRemoverVol.setkPressedColor(new java.awt.Color(250, 209, 254));
        btRemoverVol.setkStartColor(new java.awt.Color(199, 96, 230));
        btRemoverVol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoverVolActionPerformed(evt);
            }
        });
        pnRvVol.add(btRemoverVol, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 330, 90, 20));

        ldFuncionario.add(pnRvVol, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        pnFuncEventos.setBackground(new java.awt.Color(204, 204, 204));
        pnFuncEventos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnMainTitle6.setkBorderRadius(0);
        pnMainTitle6.setkEndColor(new java.awt.Color(204, 0, 204));
        pnMainTitle6.setkStartColor(new java.awt.Color(51, 0, 102));
        pnMainTitle6.setPreferredSize(new java.awt.Dimension(640, 40));

        jLabel62.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(255, 255, 255));
        jLabel62.setText("Meus Eventos");

        javax.swing.GroupLayout pnMainTitle6Layout = new javax.swing.GroupLayout(pnMainTitle6);
        pnMainTitle6.setLayout(pnMainTitle6Layout);
        pnMainTitle6Layout.setHorizontalGroup(
            pnMainTitle6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnMainTitle6Layout.createSequentialGroup()
                .addContainerGap(263, Short.MAX_VALUE)
                .addComponent(jLabel62)
                .addGap(259, 259, 259))
        );
        pnMainTitle6Layout.setVerticalGroup(
            pnMainTitle6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnMainTitle6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel62)
                .addContainerGap())
        );

        pnFuncEventos.add(pnMainTitle6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 640, 40));

        jPanel10.setBackground(new java.awt.Color(204, 204, 204));
        jPanel10.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, null, null, new java.awt.Color(102, 0, 102)));

        taEventosFunc.setEditable(false);
        taEventosFunc.setColumns(20);
        taEventosFunc.setRows(5);
        jScrollPane13.setViewportView(taEventosFunc);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnFuncEventos.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 620, 430));

        ldFuncionario.add(pnFuncEventos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        pnCdEvento.setBackground(new java.awt.Color(204, 204, 204));
        pnCdEvento.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnMainTitle1.setkBorderRadius(0);
        pnMainTitle1.setkEndColor(new java.awt.Color(204, 0, 204));
        pnMainTitle1.setkStartColor(new java.awt.Color(51, 0, 102));
        pnMainTitle1.setPreferredSize(new java.awt.Dimension(640, 40));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Cadastrar Evento");

        javax.swing.GroupLayout pnMainTitle1Layout = new javax.swing.GroupLayout(pnMainTitle1);
        pnMainTitle1.setLayout(pnMainTitle1Layout);
        pnMainTitle1Layout.setHorizontalGroup(
            pnMainTitle1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnMainTitle1Layout.createSequentialGroup()
                .addContainerGap(251, Short.MAX_VALUE)
                .addComponent(jLabel25)
                .addGap(244, 244, 244))
        );
        pnMainTitle1Layout.setVerticalGroup(
            pnMainTitle1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnMainTitle1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnCdEvento.add(pnMainTitle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 640, 40));

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));
        jPanel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, null, null, new java.awt.Color(102, 0, 102)));

        jLabel38.setText("Nome");

        jLabel39.setText("Data");

        try {
            ftfDataEvento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jScrollPane2.setViewportView(tpObjetivoEvento);

        jLabel91.setText("Objetivo");

        try {
            ftfDuracaoInicio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ftfDuracaoInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ftfDuracaoInicioActionPerformed(evt);
            }
        });

        try {
            ftfDuracaoFim.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel92.setText("Duração");

        jLabel93.setText("    Início    –      Fim");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel92)
                            .addComponent(jLabel39))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel38)
                        .addGap(6, 6, 6)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tfNomeEvento)
                    .addComponent(jLabel93, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(ftfDuracaoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ftfDuracaoFim))
                    .addComponent(ftfDataEvento))
                .addGap(30, 30, 30)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel91))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfNomeEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38))
                .addGap(8, 8, 8)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(ftfDataEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel92)
                    .addComponent(ftfDuracaoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ftfDuracaoFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(jLabel93)
                .addContainerGap(27, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel91)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        pnCdEvento.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 60, 410, 140));

        jLabel43.setText("Dados");
        pnCdEvento.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 44, -1, 20));

        jLGastos.setModel(converterFuncionariosLista());
        jScrollPane3.setViewportView(jLGastos);

        pnCdEvento.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 340, 410, 110));

        jLabel94.setText("Selecionar funcionário responsável");
        pnCdEvento.add(jLabel94, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 204, -1, 20));

        jLabel45.setText("Gastos");
        pnCdEvento.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 320, -1, 20));

        jLFuncionarios.setModel(converterFuncionariosLista());
        jScrollPane20.setViewportView(jLFuncionarios);

        pnCdEvento.add(jScrollPane20, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 220, 410, 80));

        btInfoFuncionario.setText("Ver informações");
        btInfoFuncionario.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btInfoFuncionario.setkEndColor(new java.awt.Color(233, 193, 253));
        btInfoFuncionario.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btInfoFuncionario.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btInfoFuncionario.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btInfoFuncionario.setkPressedColor(new java.awt.Color(250, 209, 254));
        btInfoFuncionario.setkStartColor(new java.awt.Color(199, 96, 230));
        btInfoFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btInfoFuncionarioActionPerformed(evt);
            }
        });
        pnCdEvento.add(btInfoFuncionario, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 310, 100, 20));

        btAddGasto.setText("Adicionar");
        btAddGasto.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btAddGasto.setkEndColor(new java.awt.Color(233, 193, 253));
        btAddGasto.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btAddGasto.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btAddGasto.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btAddGasto.setkPressedColor(new java.awt.Color(250, 209, 254));
        btAddGasto.setkStartColor(new java.awt.Color(199, 96, 230));
        btAddGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddGastoActionPerformed(evt);
            }
        });
        pnCdEvento.add(btAddGasto, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 460, 60, 20));
        pnCdEvento.add(tfNomeGasto, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 460, 130, -1));

        jLabel46.setText("Descrição");
        pnCdEvento.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 460, 60, 20));

        jLabel47.setText("Valor");
        pnCdEvento.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 460, -1, 20));

        btRemoveGasto.setText("Remover item");
        btRemoveGasto.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btRemoveGasto.setkEndColor(new java.awt.Color(233, 193, 253));
        btRemoveGasto.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btRemoveGasto.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btRemoveGasto.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btRemoveGasto.setkPressedColor(new java.awt.Color(250, 209, 254));
        btRemoveGasto.setkStartColor(new java.awt.Color(199, 96, 230));
        btRemoveGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoveGastoActionPerformed(evt);
            }
        });
        pnCdEvento.add(btRemoveGasto, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 400, 80, 20));
        pnCdEvento.add(tfValorGasto, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 460, 110, -1));

        btCadastrarEvento.setText("Cadastrar Evento");
        btCadastrarEvento.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btCadastrarEvento.setkEndColor(new java.awt.Color(233, 193, 253));
        btCadastrarEvento.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btCadastrarEvento.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btCadastrarEvento.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btCadastrarEvento.setkPressedColor(new java.awt.Color(250, 209, 254));
        btCadastrarEvento.setkStartColor(new java.awt.Color(199, 96, 230));
        btCadastrarEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCadastrarEventoActionPerformed(evt);
            }
        });
        pnCdEvento.add(btCadastrarEvento, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 430, 100, 50));

        lbTotalGastos.setText("R$ "+calcularTotalGastosTemp());
        pnCdEvento.add(lbTotalGastos, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 360, 110, 20));

        jLabel95.setText("Total");
        pnCdEvento.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 340, -1, -1));

        ldFuncionario.add(pnCdEvento, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        pnAceitarDoacao.setBackground(new java.awt.Color(204, 204, 204));
        pnAceitarDoacao.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnMainTitle12.setkBorderRadius(0);
        pnMainTitle12.setkEndColor(new java.awt.Color(204, 0, 204));
        pnMainTitle12.setkStartColor(new java.awt.Color(51, 0, 102));
        pnMainTitle12.setPreferredSize(new java.awt.Dimension(640, 40));

        jLabel68.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(255, 255, 255));
        jLabel68.setText("Aceitar Doação");

        javax.swing.GroupLayout pnMainTitle12Layout = new javax.swing.GroupLayout(pnMainTitle12);
        pnMainTitle12.setLayout(pnMainTitle12Layout);
        pnMainTitle12Layout.setHorizontalGroup(
            pnMainTitle12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnMainTitle12Layout.createSequentialGroup()
                .addGap(248, 248, 248)
                .addComponent(jLabel68)
                .addContainerGap(262, Short.MAX_VALUE))
        );
        pnMainTitle12Layout.setVerticalGroup(
            pnMainTitle12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnMainTitle12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel68)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnAceitarDoacao.add(pnMainTitle12, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 640, 40));

        jLDoacoesPendentes.setModel(converterVoluntariosLista());
        jScrollPane17.setViewportView(jLDoacoesPendentes);

        pnAceitarDoacao.add(jScrollPane17, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 80, 410, 240));

        jLabel80.setText("Selecione a doação");
        pnAceitarDoacao.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 60, -1, 20));

        btInfoDoacao.setText("Ver informações");
        btInfoDoacao.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btInfoDoacao.setkEndColor(new java.awt.Color(233, 193, 253));
        btInfoDoacao.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btInfoDoacao.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btInfoDoacao.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btInfoDoacao.setkPressedColor(new java.awt.Color(250, 209, 254));
        btInfoDoacao.setkStartColor(new java.awt.Color(199, 96, 230));
        btInfoDoacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btInfoDoacaoActionPerformed(evt);
            }
        });
        pnAceitarDoacao.add(btInfoDoacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 330, 100, 20));

        btAceitarDoacaoSelecionada.setText("Aceitar");
        btAceitarDoacaoSelecionada.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btAceitarDoacaoSelecionada.setkEndColor(new java.awt.Color(233, 193, 253));
        btAceitarDoacaoSelecionada.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btAceitarDoacaoSelecionada.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btAceitarDoacaoSelecionada.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btAceitarDoacaoSelecionada.setkPressedColor(new java.awt.Color(250, 209, 254));
        btAceitarDoacaoSelecionada.setkStartColor(new java.awt.Color(199, 96, 230));
        btAceitarDoacaoSelecionada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAceitarDoacaoSelecionadaActionPerformed(evt);
            }
        });
        pnAceitarDoacao.add(btAceitarDoacaoSelecionada, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 330, 90, 20));

        btRecusarDoacao.setText("Recusar");
        btRecusarDoacao.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btRecusarDoacao.setkEndColor(new java.awt.Color(233, 193, 253));
        btRecusarDoacao.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btRecusarDoacao.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btRecusarDoacao.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btRecusarDoacao.setkPressedColor(new java.awt.Color(250, 209, 254));
        btRecusarDoacao.setkStartColor(new java.awt.Color(199, 96, 230));
        btRecusarDoacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRecusarDoacaoActionPerformed(evt);
            }
        });
        pnAceitarDoacao.add(btRecusarDoacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 330, 90, 20));

        ldFuncionario.add(pnAceitarDoacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        pnGerenciarEntrega.setBackground(new java.awt.Color(204, 204, 204));
        pnGerenciarEntrega.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnMainTitle15.setkBorderRadius(0);
        pnMainTitle15.setkEndColor(new java.awt.Color(204, 0, 204));
        pnMainTitle15.setkStartColor(new java.awt.Color(51, 0, 102));
        pnMainTitle15.setPreferredSize(new java.awt.Dimension(640, 40));

        jLabel88.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel88.setForeground(new java.awt.Color(255, 255, 255));
        jLabel88.setText("Gerenciar Entrega");

        javax.swing.GroupLayout pnMainTitle15Layout = new javax.swing.GroupLayout(pnMainTitle15);
        pnMainTitle15.setLayout(pnMainTitle15Layout);
        pnMainTitle15Layout.setHorizontalGroup(
            pnMainTitle15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnMainTitle15Layout.createSequentialGroup()
                .addGap(248, 248, 248)
                .addComponent(jLabel88)
                .addContainerGap(239, Short.MAX_VALUE))
        );
        pnMainTitle15Layout.setVerticalGroup(
            pnMainTitle15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnMainTitle15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel88)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnGerenciarEntrega.add(pnMainTitle15, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 640, 40));

        jLDoacoes.setModel(converterVoluntariosLista());
        jScrollPane21.setViewportView(jLDoacoes);

        pnGerenciarEntrega.add(jScrollPane21, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 70, 410, 330));

        jLabel90.setText("Selecione a doação");
        pnGerenciarEntrega.add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 50, -1, 20));

        btInfoDoacao1.setText("Ver informações");
        btInfoDoacao1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btInfoDoacao1.setkEndColor(new java.awt.Color(233, 193, 253));
        btInfoDoacao1.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btInfoDoacao1.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btInfoDoacao1.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btInfoDoacao1.setkPressedColor(new java.awt.Color(250, 209, 254));
        btInfoDoacao1.setkStartColor(new java.awt.Color(199, 96, 230));
        btInfoDoacao1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btInfoDoacao1ActionPerformed(evt);
            }
        });
        pnGerenciarEntrega.add(btInfoDoacao1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 410, 100, 20));

        btConfirmarEntrega.setText("Confirmar Entrega");
        btConfirmarEntrega.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btConfirmarEntrega.setkEndColor(new java.awt.Color(233, 193, 253));
        btConfirmarEntrega.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btConfirmarEntrega.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btConfirmarEntrega.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btConfirmarEntrega.setkPressedColor(new java.awt.Color(250, 209, 254));
        btConfirmarEntrega.setkStartColor(new java.awt.Color(199, 96, 230));
        btConfirmarEntrega.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btConfirmarEntregaActionPerformed(evt);
            }
        });
        pnGerenciarEntrega.add(btConfirmarEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 410, 120, 20));

        ldFuncionario.add(pnGerenciarEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        getContentPane().add(ldFuncionario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        ldVoluntarioPF.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnVolMain.setBackground(new java.awt.Color(204, 204, 204));
        pnVolMain.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnMainTitle4.setkBorderRadius(0);
        pnMainTitle4.setkEndColor(new java.awt.Color(204, 0, 204));
        pnMainTitle4.setkStartColor(new java.awt.Color(51, 0, 102));
        pnMainTitle4.setPreferredSize(new java.awt.Dimension(640, 40));

        jLabel53.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setText("Início");

        javax.swing.GroupLayout pnMainTitle4Layout = new javax.swing.GroupLayout(pnMainTitle4);
        pnMainTitle4.setLayout(pnMainTitle4Layout);
        pnMainTitle4Layout.setHorizontalGroup(
            pnMainTitle4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnMainTitle4Layout.createSequentialGroup()
                .addGap(289, 289, 289)
                .addComponent(jLabel53)
                .addContainerGap(304, Short.MAX_VALUE))
        );
        pnMainTitle4Layout.setVerticalGroup(
            pnMainTitle4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnMainTitle4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel53)
                .addContainerGap())
        );

        pnVolMain.add(pnMainTitle4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 640, 40));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Seus dados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 11))); // NOI18N

        taInfoVoluntarioPF.setEditable(false);
        taInfoVoluntarioPF.setColumns(20);
        taInfoVoluntarioPF.setRows(5);
        jScrollPane8.setViewportView(taInfoVoluntarioPF);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 214, Short.MAX_VALUE))
        );

        pnVolMain.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 620, 440));

        ldVoluntarioPF.add(pnVolMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        pnTbEvento.setBackground(new java.awt.Color(204, 204, 204));
        pnTbEvento.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnCdVolTitle2.setkBorderRadius(0);
        pnCdVolTitle2.setkEndColor(new java.awt.Color(204, 0, 204));
        pnCdVolTitle2.setkStartColor(new java.awt.Color(51, 0, 102));
        pnCdVolTitle2.setPreferredSize(new java.awt.Dimension(640, 40));

        jLabel54.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setText("Trabalhos disponíveis");

        javax.swing.GroupLayout pnCdVolTitle2Layout = new javax.swing.GroupLayout(pnCdVolTitle2);
        pnCdVolTitle2.setLayout(pnCdVolTitle2Layout);
        pnCdVolTitle2Layout.setHorizontalGroup(
            pnCdVolTitle2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCdVolTitle2Layout.createSequentialGroup()
                .addGap(232, 232, 232)
                .addComponent(jLabel54)
                .addContainerGap(224, Short.MAX_VALUE))
        );
        pnCdVolTitle2Layout.setVerticalGroup(
            pnCdVolTitle2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCdVolTitle2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel54)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnTbEvento.add(pnCdVolTitle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, -1, -1));

        jPanel9.setBackground(new java.awt.Color(204, 204, 204));
        jPanel9.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, null, null, new java.awt.Color(102, 0, 102)));

        jScrollPane10.setViewportView(jLTrabalhosDisponiveis);

        jLabel58.setText("Selecionar Trabalho");

        btAceitarTrabalho.setText("Aceitar Trabalho");
        btAceitarTrabalho.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btAceitarTrabalho.setkEndColor(new java.awt.Color(233, 193, 253));
        btAceitarTrabalho.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btAceitarTrabalho.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btAceitarTrabalho.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btAceitarTrabalho.setkPressedColor(new java.awt.Color(250, 209, 254));
        btAceitarTrabalho.setkStartColor(new java.awt.Color(199, 96, 230));
        btAceitarTrabalho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAceitarTrabalhoActionPerformed(evt);
            }
        });

        jLEventosTrabalhos.setModel(converterEventosLista());
        jScrollPane12.setViewportView(jLEventosTrabalhos);

        jLabel60.setText("Selecionar Evento");

        btVerTrabalhos.setText("Ver trabalhos");
        btVerTrabalhos.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btVerTrabalhos.setkEndColor(new java.awt.Color(233, 193, 253));
        btVerTrabalhos.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btVerTrabalhos.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btVerTrabalhos.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btVerTrabalhos.setkPressedColor(new java.awt.Color(250, 209, 254));
        btVerTrabalhos.setkStartColor(new java.awt.Color(199, 96, 230));
        btVerTrabalhos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btVerTrabalhosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel58)
                        .addGap(246, 246, 246))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane10)
                            .addComponent(jScrollPane12)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel60)
                                .addGap(244, 244, 244))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btVerTrabalhos, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btAceitarTrabalho, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel60)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btVerTrabalhos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel58)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btAceitarTrabalho, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pnTbEvento.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 620, 430));

        ldVoluntarioPF.add(pnTbEvento, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        pnVolPFEventos.setBackground(new java.awt.Color(204, 204, 204));
        pnVolPFEventos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnMainTitle5.setkBorderRadius(0);
        pnMainTitle5.setkEndColor(new java.awt.Color(204, 0, 204));
        pnMainTitle5.setkStartColor(new java.awt.Color(51, 0, 102));
        pnMainTitle5.setPreferredSize(new java.awt.Dimension(640, 40));

        jLabel61.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(255, 255, 255));
        jLabel61.setText("Meus Eventos");

        javax.swing.GroupLayout pnMainTitle5Layout = new javax.swing.GroupLayout(pnMainTitle5);
        pnMainTitle5.setLayout(pnMainTitle5Layout);
        pnMainTitle5Layout.setHorizontalGroup(
            pnMainTitle5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnMainTitle5Layout.createSequentialGroup()
                .addContainerGap(263, Short.MAX_VALUE)
                .addComponent(jLabel61)
                .addGap(259, 259, 259))
        );
        pnMainTitle5Layout.setVerticalGroup(
            pnMainTitle5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnMainTitle5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel61)
                .addContainerGap())
        );

        pnVolPFEventos.add(pnMainTitle5, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 640, 40));

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));
        jPanel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, null, null, new java.awt.Color(102, 0, 102)));

        taEventosVolPF.setEditable(false);
        taEventosVolPF.setColumns(20);
        taEventosVolPF.setRows(5);
        jScrollPane11.setViewportView(taEventosVolPF);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnVolPFEventos.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 620, 430));

        ldVoluntarioPF.add(pnVolPFEventos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        pnDoar.setBackground(new java.awt.Color(204, 204, 204));
        pnDoar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnMainTitle8.setkBorderRadius(0);
        pnMainTitle8.setkEndColor(new java.awt.Color(204, 0, 204));
        pnMainTitle8.setkStartColor(new java.awt.Color(51, 0, 102));
        pnMainTitle8.setPreferredSize(new java.awt.Dimension(640, 40));

        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("Cadastrar Evento");

        javax.swing.GroupLayout pnMainTitle8Layout = new javax.swing.GroupLayout(pnMainTitle8);
        pnMainTitle8.setLayout(pnMainTitle8Layout);
        pnMainTitle8Layout.setHorizontalGroup(
            pnMainTitle8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnMainTitle8Layout.createSequentialGroup()
                .addContainerGap(251, Short.MAX_VALUE)
                .addComponent(jLabel40)
                .addGap(244, 244, 244))
        );
        pnMainTitle8Layout.setVerticalGroup(
            pnMainTitle8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnMainTitle8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel40)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnDoar.add(pnMainTitle8, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 640, 40));

        jPanel8.setBackground(new java.awt.Color(204, 204, 204));
        jPanel8.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, null, null, new java.awt.Color(102, 0, 102)));

        jLabel41.setText("Data");

        try {
            ftfDataDoacao.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        tfValorDoacao.setText("0");

        jLabel76.setText("Valor");

        cbRepetirDoacao.setBackground(new java.awt.Color(204, 204, 204));
        cbRepetirDoacao.setText("Repetir Doação");

        cbModoEntrega.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Retirada", "Entrega" }));

        jLabel42.setText("Modo de Entrega");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel76, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel42))
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfValorDoacao)
                    .addComponent(ftfDataDoacao)
                    .addComponent(cbModoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(cbRepetirDoacao)
                .addContainerGap(65, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfValorDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel76)
                    .addComponent(cbRepetirDoacao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(ftfDataDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbModoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        pnDoar.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 60, 410, 140));

        jLabel44.setText("Dados");
        pnDoar.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 44, -1, 20));

        jLItens.setModel(converterFuncionariosLista());
        jScrollPane5.setViewportView(jLItens);

        pnDoar.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 230, 410, 110));

        jLabel48.setText("Itens");
        pnDoar.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 210, -1, 20));

        btAddItem.setText("Adicionar");
        btAddItem.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btAddItem.setkEndColor(new java.awt.Color(233, 193, 253));
        btAddItem.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btAddItem.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btAddItem.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btAddItem.setkPressedColor(new java.awt.Color(250, 209, 254));
        btAddItem.setkStartColor(new java.awt.Color(199, 96, 230));
        btAddItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddItemActionPerformed(evt);
            }
        });
        pnDoar.add(btAddItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 350, 60, 20));
        pnDoar.add(tfNomeItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 350, 130, -1));

        jLabel77.setText("Nome");
        pnDoar.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 350, 40, 20));

        jLabel78.setText("Quantidade");
        pnDoar.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 350, 70, 20));

        btRemoveItem.setText("Remover item");
        btRemoveItem.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btRemoveItem.setkEndColor(new java.awt.Color(233, 193, 253));
        btRemoveItem.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btRemoveItem.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btRemoveItem.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btRemoveItem.setkPressedColor(new java.awt.Color(250, 209, 254));
        btRemoveItem.setkStartColor(new java.awt.Color(199, 96, 230));
        btRemoveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoveItemActionPerformed(evt);
            }
        });
        pnDoar.add(btRemoveItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 230, 80, 20));
        pnDoar.add(tfItemQuantidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 350, 90, -1));

        btFinalizarDoacaoPF.setText("Finalizar Doação");
        btFinalizarDoacaoPF.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btFinalizarDoacaoPF.setkEndColor(new java.awt.Color(233, 193, 253));
        btFinalizarDoacaoPF.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btFinalizarDoacaoPF.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btFinalizarDoacaoPF.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btFinalizarDoacaoPF.setkPressedColor(new java.awt.Color(250, 209, 254));
        btFinalizarDoacaoPF.setkStartColor(new java.awt.Color(199, 96, 230));
        btFinalizarDoacaoPF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFinalizarDoacaoPFActionPerformed(evt);
            }
        });
        pnDoar.add(btFinalizarDoacaoPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 380, 100, 50));

        ldVoluntarioPF.add(pnDoar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        pnMinhasDoacoes.setBackground(new java.awt.Color(204, 204, 204));
        pnMinhasDoacoes.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnMainTitle7.setkBorderRadius(0);
        pnMainTitle7.setkEndColor(new java.awt.Color(204, 0, 204));
        pnMainTitle7.setkStartColor(new java.awt.Color(51, 0, 102));
        pnMainTitle7.setPreferredSize(new java.awt.Dimension(640, 40));

        jLabel63.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(255, 255, 255));
        jLabel63.setText("Minhas Doações");

        javax.swing.GroupLayout pnMainTitle7Layout = new javax.swing.GroupLayout(pnMainTitle7);
        pnMainTitle7.setLayout(pnMainTitle7Layout);
        pnMainTitle7Layout.setHorizontalGroup(
            pnMainTitle7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnMainTitle7Layout.createSequentialGroup()
                .addGap(248, 248, 248)
                .addComponent(jLabel63)
                .addContainerGap(253, Short.MAX_VALUE))
        );
        pnMainTitle7Layout.setVerticalGroup(
            pnMainTitle7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnMainTitle7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel63)
                .addContainerGap())
        );

        pnMinhasDoacoes.add(pnMainTitle7, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 640, 40));

        jPanel11.setBackground(new java.awt.Color(204, 204, 204));
        jPanel11.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, null, null, new java.awt.Color(102, 0, 102)));

        jLabel64.setText("Doações pendentes");

        jLabel65.setText("Doações aceitas");

        taDoacoesPendentes.setEditable(false);
        taDoacoesPendentes.setColumns(20);
        taDoacoesPendentes.setRows(5);
        jScrollPane14.setViewportView(taDoacoesPendentes);

        taDoacoesAceitas.setEditable(false);
        taDoacoesAceitas.setColumns(20);
        taDoacoesAceitas.setRows(5);
        jScrollPane15.setViewportView(taDoacoesAceitas);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel64)
                .addGap(257, 257, 257))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane15)
                    .addComponent(jScrollPane14))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(269, Short.MAX_VALUE)
                .addComponent(jLabel65)
                .addGap(267, 267, 267))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel64)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel65)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnMinhasDoacoes.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 620, 430));

        ldVoluntarioPF.add(pnMinhasDoacoes, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        getContentPane().add(ldVoluntarioPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        ldVoluntarioPJ.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnPatrocinarEvento.setBackground(new java.awt.Color(204, 204, 204));
        pnPatrocinarEvento.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnPatrocinarTitle.setkBorderRadius(0);
        pnPatrocinarTitle.setkEndColor(new java.awt.Color(204, 0, 204));
        pnPatrocinarTitle.setkStartColor(new java.awt.Color(51, 0, 102));
        pnPatrocinarTitle.setPreferredSize(new java.awt.Dimension(640, 40));

        jLabel67.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(255, 255, 255));
        jLabel67.setText("Patrocinar Evento");

        javax.swing.GroupLayout pnPatrocinarTitleLayout = new javax.swing.GroupLayout(pnPatrocinarTitle);
        pnPatrocinarTitle.setLayout(pnPatrocinarTitleLayout);
        pnPatrocinarTitleLayout.setHorizontalGroup(
            pnPatrocinarTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnPatrocinarTitleLayout.createSequentialGroup()
                .addContainerGap(251, Short.MAX_VALUE)
                .addComponent(jLabel67)
                .addGap(237, 237, 237))
        );
        pnPatrocinarTitleLayout.setVerticalGroup(
            pnPatrocinarTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnPatrocinarTitleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel67)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnPatrocinarEvento.add(pnPatrocinarTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, -1, -1));

        jPanel13.setBackground(new java.awt.Color(204, 204, 204));
        jPanel13.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, null, null, new java.awt.Color(102, 0, 102)));

        btPatrocinarEventoSelecionado.setText("Patrocinar evento");
        btPatrocinarEventoSelecionado.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btPatrocinarEventoSelecionado.setkEndColor(new java.awt.Color(233, 193, 253));
        btPatrocinarEventoSelecionado.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btPatrocinarEventoSelecionado.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btPatrocinarEventoSelecionado.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btPatrocinarEventoSelecionado.setkPressedColor(new java.awt.Color(250, 209, 254));
        btPatrocinarEventoSelecionado.setkStartColor(new java.awt.Color(199, 96, 230));
        btPatrocinarEventoSelecionado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPatrocinarEventoSelecionadoActionPerformed(evt);
            }
        });

        jLEventosPatrocinar.setModel(converterEventosLista());
        jScrollPane18.setViewportView(jLEventosPatrocinar);

        jLabel69.setText("Selecionar Evento");

        btVerInfoEvento.setText("Ver informações");
        btVerInfoEvento.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btVerInfoEvento.setkEndColor(new java.awt.Color(233, 193, 253));
        btVerInfoEvento.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btVerInfoEvento.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btVerInfoEvento.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btVerInfoEvento.setkPressedColor(new java.awt.Color(250, 209, 254));
        btVerInfoEvento.setkStartColor(new java.awt.Color(199, 96, 230));
        btVerInfoEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btVerInfoEventoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel69)
                        .addGap(244, 244, 244))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btVerInfoEvento, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btPatrocinarEventoSelecionado, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel69)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btPatrocinarEventoSelecionado, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btVerInfoEvento, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        pnPatrocinarEvento.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 620, 430));

        ldVoluntarioPJ.add(pnPatrocinarEvento, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        pnEventosPatrocinados.setBackground(new java.awt.Color(204, 204, 204));
        pnEventosPatrocinados.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnMainTitle10.setkBorderRadius(0);
        pnMainTitle10.setkEndColor(new java.awt.Color(204, 0, 204));
        pnMainTitle10.setkStartColor(new java.awt.Color(51, 0, 102));
        pnMainTitle10.setPreferredSize(new java.awt.Dimension(640, 40));

        jLabel70.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(255, 255, 255));
        jLabel70.setText("Meus Eventos");

        javax.swing.GroupLayout pnMainTitle10Layout = new javax.swing.GroupLayout(pnMainTitle10);
        pnMainTitle10.setLayout(pnMainTitle10Layout);
        pnMainTitle10Layout.setHorizontalGroup(
            pnMainTitle10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnMainTitle10Layout.createSequentialGroup()
                .addContainerGap(263, Short.MAX_VALUE)
                .addComponent(jLabel70)
                .addGap(259, 259, 259))
        );
        pnMainTitle10Layout.setVerticalGroup(
            pnMainTitle10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnMainTitle10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel70)
                .addContainerGap())
        );

        pnEventosPatrocinados.add(pnMainTitle10, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 640, 40));

        jPanel14.setBackground(new java.awt.Color(204, 204, 204));
        jPanel14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, null, null, new java.awt.Color(102, 0, 102)));

        taEventosPatrocinados.setEditable(false);
        taEventosPatrocinados.setColumns(20);
        taEventosPatrocinados.setRows(5);
        jScrollPane19.setViewportView(taEventosPatrocinados);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane19, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane19, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnEventosPatrocinados.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 620, 430));

        ldVoluntarioPJ.add(pnEventosPatrocinados, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        getContentPane().add(ldVoluntarioPJ, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        ldGestor.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnGestorInicio.setBackground(new java.awt.Color(204, 204, 204));
        pnGestorInicio.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnMainTitle9.setkBorderRadius(0);
        pnMainTitle9.setkEndColor(new java.awt.Color(204, 0, 204));
        pnMainTitle9.setkStartColor(new java.awt.Color(51, 0, 102));
        pnMainTitle9.setPreferredSize(new java.awt.Dimension(640, 40));

        jLabel66.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(255, 255, 255));
        jLabel66.setText("Início");

        javax.swing.GroupLayout pnMainTitle9Layout = new javax.swing.GroupLayout(pnMainTitle9);
        pnMainTitle9.setLayout(pnMainTitle9Layout);
        pnMainTitle9Layout.setHorizontalGroup(
            pnMainTitle9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnMainTitle9Layout.createSequentialGroup()
                .addGap(289, 289, 289)
                .addComponent(jLabel66)
                .addContainerGap(304, Short.MAX_VALUE))
        );
        pnMainTitle9Layout.setVerticalGroup(
            pnMainTitle9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnMainTitle9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel66)
                .addContainerGap())
        );

        pnGestorInicio.add(pnMainTitle9, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 640, 40));

        jPanel12.setBackground(new java.awt.Color(204, 204, 204));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Seus dados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 11))); // NOI18N

        taInfoGestor.setEditable(false);
        taInfoGestor.setColumns(20);
        taInfoGestor.setRows(5);
        jScrollPane16.setViewportView(taInfoGestor);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 214, Short.MAX_VALUE))
        );

        pnGestorInicio.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 620, 440));

        ldGestor.add(pnGestorInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        pnAnalisarReceitas.setBackground(new java.awt.Color(204, 204, 204));
        pnAnalisarReceitas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnMainTitle11.setkBorderRadius(0);
        pnMainTitle11.setkEndColor(new java.awt.Color(204, 0, 204));
        pnMainTitle11.setkStartColor(new java.awt.Color(51, 0, 102));
        pnMainTitle11.setPreferredSize(new java.awt.Dimension(640, 40));

        jLabel73.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(255, 255, 255));
        jLabel73.setText("Analisar Receitas");

        javax.swing.GroupLayout pnMainTitle11Layout = new javax.swing.GroupLayout(pnMainTitle11);
        pnMainTitle11.setLayout(pnMainTitle11Layout);
        pnMainTitle11Layout.setHorizontalGroup(
            pnMainTitle11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnMainTitle11Layout.createSequentialGroup()
                .addContainerGap(252, Short.MAX_VALUE)
                .addComponent(jLabel73)
                .addGap(243, 243, 243))
        );
        pnMainTitle11Layout.setVerticalGroup(
            pnMainTitle11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnMainTitle11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel73)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnAnalisarReceitas.add(pnMainTitle11, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 640, 40));

        jPanel16.setBackground(new java.awt.Color(204, 204, 204));
        jPanel16.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, null, null, new java.awt.Color(102, 0, 102)));

        taReceitas.setEditable(false);
        taReceitas.setColumns(20);
        taReceitas.setRows(5);
        jScrollPane22.setViewportView(taReceitas);

        lbTotalReceitas.setText("Total de Receitas: R$ 0");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane22, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                    .addComponent(lbTotalReceitas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbTotalReceitas)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pnAnalisarReceitas.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 620, 430));

        ldGestor.add(pnAnalisarReceitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        pnRelatorioFinanc.setBackground(new java.awt.Color(204, 204, 204));
        pnRelatorioFinanc.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnMainTitle13.setkBorderRadius(0);
        pnMainTitle13.setkEndColor(new java.awt.Color(204, 0, 204));
        pnMainTitle13.setkStartColor(new java.awt.Color(51, 0, 102));
        pnMainTitle13.setPreferredSize(new java.awt.Dimension(640, 40));

        jLabel85.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(255, 255, 255));
        jLabel85.setText("Minhas Doações");

        javax.swing.GroupLayout pnMainTitle13Layout = new javax.swing.GroupLayout(pnMainTitle13);
        pnMainTitle13.setLayout(pnMainTitle13Layout);
        pnMainTitle13Layout.setHorizontalGroup(
            pnMainTitle13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnMainTitle13Layout.createSequentialGroup()
                .addGap(248, 248, 248)
                .addComponent(jLabel85)
                .addContainerGap(253, Short.MAX_VALUE))
        );
        pnMainTitle13Layout.setVerticalGroup(
            pnMainTitle13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnMainTitle13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel85)
                .addContainerGap())
        );

        pnRelatorioFinanc.add(pnMainTitle13, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 640, 40));

        jPanel18.setBackground(new java.awt.Color(204, 204, 204));
        jPanel18.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, null, null, new java.awt.Color(102, 0, 102)));

        jLabel86.setText("Doações pendentes");

        jLabel87.setText("Doações aceitas");

        taDoacoesPendentes1.setEditable(false);
        taDoacoesPendentes1.setColumns(20);
        taDoacoesPendentes1.setRows(5);
        jScrollPane24.setViewportView(taDoacoesPendentes1);

        taDoacoesAceitas1.setEditable(false);
        taDoacoesAceitas1.setColumns(20);
        taDoacoesAceitas1.setRows(5);
        jScrollPane25.setViewportView(taDoacoesAceitas1);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel86)
                .addGap(257, 257, 257))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane25)
                    .addComponent(jScrollPane24))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(269, Short.MAX_VALUE)
                .addComponent(jLabel87)
                .addGap(267, 267, 267))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel86)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane24, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel87)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane25, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnRelatorioFinanc.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 620, 430));

        ldGestor.add(pnRelatorioFinanc, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        pnAnalisarDespesas.setBackground(new java.awt.Color(204, 204, 204));
        pnAnalisarDespesas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnMainTitle14.setkBorderRadius(0);
        pnMainTitle14.setkEndColor(new java.awt.Color(204, 0, 204));
        pnMainTitle14.setkStartColor(new java.awt.Color(51, 0, 102));
        pnMainTitle14.setPreferredSize(new java.awt.Dimension(640, 40));

        jLabel89.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel89.setForeground(new java.awt.Color(255, 255, 255));
        jLabel89.setText("Analisar Despesas");

        javax.swing.GroupLayout pnMainTitle14Layout = new javax.swing.GroupLayout(pnMainTitle14);
        pnMainTitle14.setLayout(pnMainTitle14Layout);
        pnMainTitle14Layout.setHorizontalGroup(
            pnMainTitle14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnMainTitle14Layout.createSequentialGroup()
                .addContainerGap(254, Short.MAX_VALUE)
                .addComponent(jLabel89)
                .addGap(234, 234, 234))
        );
        pnMainTitle14Layout.setVerticalGroup(
            pnMainTitle14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnMainTitle14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel89)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnAnalisarDespesas.add(pnMainTitle14, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 640, 40));

        jPanel19.setBackground(new java.awt.Color(204, 204, 204));
        jPanel19.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, null, null, new java.awt.Color(102, 0, 102)));

        taDespesas.setEditable(false);
        taDespesas.setColumns(20);
        taDespesas.setRows(5);
        jScrollPane26.setViewportView(taDespesas);

        lbTotalDespesas.setText("Total de Despesas: R$ 0");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane26, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                    .addComponent(lbTotalDespesas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane26, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbTotalDespesas)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pnAnalisarDespesas.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 620, 430));

        ldGestor.add(pnAnalisarDespesas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        pnCadastrarFunc.setBackground(new java.awt.Color(204, 204, 204));
        pnCadastrarFunc.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnCdVolTitle4.setkBorderRadius(0);
        pnCdVolTitle4.setkEndColor(new java.awt.Color(204, 0, 204));
        pnCdVolTitle4.setkStartColor(new java.awt.Color(51, 0, 102));
        pnCdVolTitle4.setPreferredSize(new java.awt.Dimension(640, 40));

        jLabel71.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(255, 255, 255));
        jLabel71.setText("Cadastrar Funcionário");

        javax.swing.GroupLayout pnCdVolTitle4Layout = new javax.swing.GroupLayout(pnCdVolTitle4);
        pnCdVolTitle4.setLayout(pnCdVolTitle4Layout);
        pnCdVolTitle4Layout.setHorizontalGroup(
            pnCdVolTitle4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCdVolTitle4Layout.createSequentialGroup()
                .addGap(232, 232, 232)
                .addComponent(jLabel71)
                .addContainerGap(222, Short.MAX_VALUE))
        );
        pnCdVolTitle4Layout.setVerticalGroup(
            pnCdVolTitle4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCdVolTitle4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel71)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnCadastrarFunc.add(pnCdVolTitle4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, -1, -1));

        pnDados2.setBackground(new java.awt.Color(204, 204, 204));
        pnDados2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, new java.awt.Color(255, 255, 255), null, new java.awt.Color(102, 0, 102)));

        try {
            ftfGestorTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel72.setText("Nome");

        jLabel74.setText("Telefone");

        jLabel75.setText("CPF");

        try {
            ftfGestorCPF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel79.setText("Sexo");

        cbGestorSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Feminino" }));

        javax.swing.GroupLayout pnDados2Layout = new javax.swing.GroupLayout(pnDados2);
        pnDados2.setLayout(pnDados2Layout);
        pnDados2Layout.setHorizontalGroup(
            pnDados2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDados2Layout.createSequentialGroup()
                .addGroup(pnDados2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnDados2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel72)
                        .addGap(4, 4, 4)
                        .addComponent(tfGestorNome, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnDados2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel74)
                        .addGap(4, 4, 4)
                        .addComponent(ftfGestorTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnDados2Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel75)
                        .addGap(4, 4, 4)
                        .addComponent(ftfGestorCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnDados2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel79)
                        .addGap(4, 4, 4)
                        .addComponent(cbGestorSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );
        pnDados2Layout.setVerticalGroup(
            pnDados2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDados2Layout.createSequentialGroup()
                .addGroup(pnDados2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnDados2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel72))
                    .addComponent(tfGestorNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(pnDados2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnDados2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel74))
                    .addComponent(ftfGestorTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(pnDados2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnDados2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel75))
                    .addComponent(ftfGestorCPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(pnDados2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnDados2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel79))
                    .addComponent(cbGestorSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(146, Short.MAX_VALUE))
        );

        pnCadastrarFunc.add(pnDados2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 60, 168, 250));

        btCadastrarFuncionario.setText("Cadastrar");
        btCadastrarFuncionario.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btCadastrarFuncionario.setkEndColor(new java.awt.Color(233, 193, 253));
        btCadastrarFuncionario.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btCadastrarFuncionario.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btCadastrarFuncionario.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btCadastrarFuncionario.setkPressedColor(new java.awt.Color(250, 209, 254));
        btCadastrarFuncionario.setkStartColor(new java.awt.Color(199, 96, 230));
        btCadastrarFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCadastrarFuncionarioActionPerformed(evt);
            }
        });
        pnCadastrarFunc.add(btCadastrarFuncionario, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 320, 110, 30));

        pnEndereco2.setBackground(new java.awt.Color(204, 204, 204));
        pnEndereco2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, null, null, new java.awt.Color(102, 0, 102)));

        jLabel81.setText("Rua");

        jLabel82.setText("Número");

        jLabel83.setText("Complemento");

        jLabel84.setText("Bairro");

        jLabel96.setText("Cidade");

        jLabel97.setText("UF");

        jLabel98.setText("CEP");

        cbGestorUF.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));

        try {
            ftfGestorCEP.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout pnEndereco2Layout = new javax.swing.GroupLayout(pnEndereco2);
        pnEndereco2.setLayout(pnEndereco2Layout);
        pnEndereco2Layout.setHorizontalGroup(
            pnEndereco2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnEndereco2Layout.createSequentialGroup()
                .addGroup(pnEndereco2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnEndereco2Layout.createSequentialGroup()
                        .addComponent(jLabel84)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfGestorBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnEndereco2Layout.createSequentialGroup()
                        .addComponent(jLabel81)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfGestorRua, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnEndereco2Layout.createSequentialGroup()
                        .addComponent(jLabel96)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfGestorCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnEndereco2Layout.createSequentialGroup()
                        .addComponent(jLabel83)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfGestorComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnEndereco2Layout.createSequentialGroup()
                        .addGroup(pnEndereco2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel98)
                            .addComponent(jLabel82)
                            .addComponent(jLabel97))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnEndereco2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ftfGestorCEP, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnEndereco2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(tfGestorNumero, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cbGestorUF, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(0, 25, Short.MAX_VALUE))
        );
        pnEndereco2Layout.setVerticalGroup(
            pnEndereco2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnEndereco2Layout.createSequentialGroup()
                .addGroup(pnEndereco2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel81)
                    .addComponent(tfGestorRua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnEndereco2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel84)
                    .addComponent(tfGestorBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnEndereco2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel83)
                    .addComponent(tfGestorComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnEndereco2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfGestorCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel96))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnEndereco2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ftfGestorCEP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel98))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnEndereco2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfGestorNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel82))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnEndereco2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbGestorUF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel97))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnCadastrarFunc.add(pnEndereco2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 60, 200, 250));

        jLabel99.setText("Endereço");
        pnCadastrarFunc.add(jLabel99, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 46, -1, -1));

        jLabel100.setText("Dados");
        pnCadastrarFunc.add(jLabel100, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 44, -1, 20));

        jPanel17.setBackground(new java.awt.Color(204, 204, 204));
        jPanel17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, null, null, new java.awt.Color(102, 0, 102)));

        jLabel101.setText("Usuário");

        jLabel102.setText("Senha");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel101, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel102, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfGestorUsuario)
                    .addComponent(tfGestorSenha, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel101)
                    .addComponent(tfGestorUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel102)
                    .addComponent(tfGestorSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(187, Short.MAX_VALUE))
        );

        pnCadastrarFunc.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 60, 190, 250));

        jLabel103.setText("Conta");
        pnCadastrarFunc.add(jLabel103, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 44, -1, 20));

        ldGestor.add(pnCadastrarFunc, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        getContentPane().add(ldGestor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tfLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfLoginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfLoginActionPerformed

    private void btEntregasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEntregasActionPerformed
        resetLayers();
		pnFuncionario.setVisible(true);
		pnGerenciarEntrega.setVisible(true);
		jLDoacoes.setModel(converterDoacoes());
    }//GEN-LAST:event_btEntregasActionPerformed

    private void btCadastroVoluntarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCadastroVoluntarioActionPerformed
       Object[] tipoVol = {"Pessoa Física", "Pessoa Jurídica"};
	   Object choice = JOptionPane.showOptionDialog(rootPane, "Qual tipo de voluntário deseja cadastrar?", "Cadastrar Voluntário", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, tipoVol, tipoVol);
	   if(choice.toString().equals("0")){
		   resetLayers();
		   pnFuncionario.setVisible(true);
		   pnCdVolPF.setVisible(true);
	   }
	   else if(choice.toString().equals("1")){
		   resetLayers();
		   pnFuncionario.setVisible(true);
		   pnCdVolPJ.setVisible(true);
	   }
    }//GEN-LAST:event_btCadastroVoluntarioActionPerformed

    private void btFuncInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFuncInicioActionPerformed
        resetLayers();
		enableFuncionario();
    }//GEN-LAST:event_btFuncInicioActionPerformed

    private void btCadastroEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCadastroEventoActionPerformed
        resetLayers();
		pnFuncionario.setVisible(true);
		pnCdEvento.setVisible(true);
		jLFuncionarios.setModel(converterFuncionariosLista());
    }//GEN-LAST:event_btCadastroEventoActionPerformed

    private void btCadastroTrabalhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCadastroTrabalhoActionPerformed
        resetLayers();
		pnFuncionario.setVisible(true);
		pnCdTrabalho.setVisible(true);
		jLTrabalhos.setModel(converterTrabalhosLista());
		jLEventos.setModel(converterEventosLista());
    }//GEN-LAST:event_btCadastroTrabalhoActionPerformed

    private void btAceitarDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAceitarDoacaoActionPerformed
        resetLayers();
		pnAceitarDoacao.setVisible(true);
		pnFuncionario.setVisible(true);
		jLDoacoesPendentes.setModel(converterDoacoesPendentes());
    }//GEN-LAST:event_btAceitarDoacaoActionPerformed

    private void btRemoverVoluntarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoverVoluntarioActionPerformed
        resetLayers();
		pnFuncionario.setVisible(true);
		pnRvVol.setVisible(true);
		jLVoluntarios.setModel(converterVoluntariosLista());
    }//GEN-LAST:event_btRemoverVoluntarioActionPerformed

    private void btRelatoriosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRelatoriosActionPerformed
		escreverArquivoE.println(gerarEventos());
		escreverArquivoD.printf("%s", gerarDoacoes());
		JOptionPane.showMessageDialog(rootPane, "Arquivo Eventos.txt e Doações.txt gerados com sucesso!", "Relatórios", JOptionPane.INFORMATION_MESSAGE);
		try {
			arquivoDoacoes.close();
		} catch (IOException ex) {
			Logger.getLogger(MoraisVoluntariado.class.getName()).log(Level.SEVERE, null, ex);
		}
		try {
			arquivoEventos.close();
		} catch (IOException ex) {
			Logger.getLogger(MoraisVoluntariado.class.getName()).log(Level.SEVERE, null, ex);
		}
    }//GEN-LAST:event_btRelatoriosActionPerformed

    private void pfSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pfSenhaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pfSenhaActionPerformed

    private void btFuncLogout1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFuncLogout1ActionPerformed
        Object[] botoesDesconectar = {"Desconectar", "Voltar"};
		Object choice = JOptionPane.showOptionDialog(rootPane, "Tem certeza que deseja sair?", "Desconectar", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoesDesconectar, botoesDesconectar);
		if(choice.toString().equals("0")){
			connected = false;
			userID = 0;
			resetLayers();
			loginPanel.setVisible(true);
		}
    }//GEN-LAST:event_btFuncLogout1ActionPerformed

    private void btCadastrarVolPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCadastrarVolPFActionPerformed
        listaContas.add(new Conta(idCount, tfVolPFUsuario.getText(), tfVolPFSenha.getText(), "VoluntárioPF"));
		
		listaVoluntarios.add(new VoluntarioPF(idCount, tfVolPFNome.getText(), ftfVolPFTelefone.getText(), 
				new Endereco(tfVolPFRua.getText(), tfVolPFNumero.getText(), tfVolPFComplemento.getText(), 
				tfVolPFBairro.getText(), tfVolPFCidade.getText(), cbVolPFUF.getSelectedItem().toString(), 
				ftfVolPFCEP.getText()), ftfVolPFCPF.getText(), cbVolPFSexo.getSelectedItem().toString(), 
				cbVolPFTurno.getSelectedItem().toString()));
		JOptionPane.showMessageDialog(rootPane, "Voluntário cadastrado com sucesso!");
		idCount++;
		clearCdVolPF();
		resetLayers();
		enableFuncionario();
    }//GEN-LAST:event_btCadastrarVolPFActionPerformed

    private void btCadastrarVolPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCadastrarVolPJActionPerformed
        listaContas.add(new Conta(idCount, tfVolPJUsuario.getText(), tfVolPJSenha.getText(), "VoluntárioPJ"));
		
		listaVoluntarios.add(new VoluntarioPJ(idCount, tfVolPJNome.getText(), ftfVolPJTelefone.getText(), 
				new Endereco(tfVolPJRua.getText(), tfVolPJNumero.getText(), tfVolPJComplemento.getText(), 
				tfVolPJBairro.getText(), tfVolPJCidade.getText(), cbVolPJUF.getSelectedItem().toString(), 
				ftfVolPJCEP.getText()), ftfVolPJCNPJ.getText(), ftfVolPJIE.getText()));
		JOptionPane.showMessageDialog(rootPane, "Voluntário cadastrado com sucesso!");
		idCount++;
		clearCdVolPJ();
		resetLayers();
		enableFuncionario();
    }//GEN-LAST:event_btCadastrarVolPJActionPerformed

    private void btLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLoginActionPerformed
        String senha = new String(pfSenha.getPassword());
		for(int i = 0; i < listaContas.size(); i++){
			if(tfLogin.getText().equals(listaContas.get(i).getUsuario()) && senha.equals(listaContas.get(i).getSenha())){
				connected = true;
				accountID = i;
				i = listaContas.size();
			}
		}
		if(connected){
			JOptionPane.showMessageDialog(rootPane, "Conectado com sucesso!", "Login", JOptionPane.INFORMATION_MESSAGE);
			
			if(this.listaContas.get(this.accountID).getTipo().equals("Funcionário")){
				for(int i = 0; i < this.listaFuncionarios.size(); i++){
					if(this.listaFuncionarios.get(i).getIdConta() == this.accountID){
						this.userID = i;
						i = this.listaFuncionarios.size();
						resetLayers();
						enableFuncionario();
					}
				}
			}
			else if(this.listaContas.get(this.accountID).getTipo().equals("Gestor")){
				for(int i = 0; i < listaGestores.size(); i++){
					if(this.listaGestores.get(i).getIdConta() == this.accountID){
						this.userID = i;
						i = this.listaGestores.size();
						resetLayers();
						enableGestor();
					}
				}
			}
			else if(this.listaContas.get(this.accountID).getTipo().equals("VoluntárioPF")){
				for(int i = 0; i < listaVoluntarios.size(); i++){
					if(this.listaVoluntarios.get(i).getIdConta() == this.accountID){
						this.userID = i;
						i = this.listaVoluntarios.size();
						resetLayers();
						enableVoluntarioPF();
					}
				}
			}
			else if(this.listaContas.get(this.accountID).getTipo().equals("VoluntárioPJ")){
				for(int i = 0; i < listaVoluntarios.size(); i++){
					if(this.listaVoluntarios.get(i).getIdConta() == this.accountID){
						this.userID = i;
						i = this.listaVoluntarios.size();
						resetLayers();
						enableVoluntarioPJ();
					}
				}
			}
		}
		else{
			JOptionPane.showMessageDialog(rootPane, "Usuário e/ou senha incorreto(s)!", "Login", JOptionPane.ERROR_MESSAGE);
		}
    }//GEN-LAST:event_btLoginActionPerformed

    private void btInfoEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btInfoEventoActionPerformed
        JOptionPane.showMessageDialog(rootPane, listaEventos.get(jLEventos.getAnchorSelectionIndex()));
    }//GEN-LAST:event_btInfoEventoActionPerformed

    private void btCadastrarTrabalhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCadastrarTrabalhoActionPerformed
        listaEventos.get(jLEventos.getAnchorSelectionIndex()).setTrabalhos(listaTrabalhosTemp);
		JOptionPane.showMessageDialog(rootPane, "Trabalho cadastrado com sucesso!", "Cadastrar Trabalho", JOptionPane.INFORMATION_MESSAGE);
		clearCdTrabalho();
		resetLayers();
		enableFuncionario();	
    }//GEN-LAST:event_btCadastrarTrabalhoActionPerformed

    private void btRemoverTrabalhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoverTrabalhoActionPerformed
        listaTrabalhosTemp.remove(jLTrabalhos.getAnchorSelectionIndex());
		jLTrabalhos.setModel(converterTrabalhosLista());
    }//GEN-LAST:event_btRemoverTrabalhoActionPerformed

    private void btAdicionarTrabalhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdicionarTrabalhoActionPerformed
        listaTrabalhosTemp.add(new Trabalho(tfNomeTrabalho.getText(), tpDescricaoTrabalho.getText()));
		jLTrabalhos.setModel(converterTrabalhosLista());
    }//GEN-LAST:event_btAdicionarTrabalhoActionPerformed

    private void btRemoverVolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoverVolActionPerformed
        Object[] botoesRVol = {"Remover", "Voltar"};
		Object choice = JOptionPane.showOptionDialog(rootPane, "Tem certeza que deseja remover o Voluntário selecionado?", "Remover Voluntário", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoesRVol, botoesRVol);
		if(choice.toString().equals("0")){
			listaContas.remove(listaVoluntarios.get(jLVoluntarios.getAnchorSelectionIndex()).getIdConta());
			listaVoluntarios.remove(jLVoluntarios.getAnchorSelectionIndex());
			jLVoluntarios.setModel(converterVoluntariosLista());
		}
    }//GEN-LAST:event_btRemoverVolActionPerformed

    private void btInfoVolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btInfoVolActionPerformed
        JOptionPane.showMessageDialog(rootPane, listaVoluntarios.get(jLVoluntarios.getAnchorSelectionIndex()));
    }//GEN-LAST:event_btInfoVolActionPerformed

    private void btVolPFInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btVolPFInicioActionPerformed
        resetLayers();
		enableVoluntarioPF();
    }//GEN-LAST:event_btVolPFInicioActionPerformed

    private void btTrabalharEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTrabalharEventoActionPerformed
        resetLayers();
		pnVoluntarioPF.setVisible(true);
		pnTbEvento.setVisible(true);
		jLEventosTrabalhos.setModel(converterEventosLista());
    }//GEN-LAST:event_btTrabalharEventoActionPerformed

    private void btDoarPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDoarPFActionPerformed
        resetLayers();
		pnVoluntarioPF.setVisible(true);
		pnDoar.setVisible(true);
    }//GEN-LAST:event_btDoarPFActionPerformed

    private void btVolPFLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btVolPFLogoutActionPerformed
        Object[] botoesDesconectar = {"Desconectar", "Voltar"};
		Object choice = JOptionPane.showOptionDialog(rootPane, "Tem certeza que deseja sair?", "Desconectar", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoesDesconectar, botoesDesconectar);
		if(choice.toString().equals("0")){
			connected = false;
			userID = 0;
			resetLayers();
			loginPanel.setVisible(true);
		}
    }//GEN-LAST:event_btVolPFLogoutActionPerformed

    private void btAceitarTrabalhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAceitarTrabalhoActionPerformed
        if(listaEventos.get(jLEventosTrabalhos.getAnchorSelectionIndex()).getTrabalhos().get(jLTrabalhosDisponiveis.getAnchorSelectionIndex()).getVol() == null){
			listaEventos.get(jLEventosTrabalhos.getAnchorSelectionIndex()).setVoluntarioTrabalho(jLTrabalhosDisponiveis.getAnchorSelectionIndex(), listaVoluntarios.get(userID));
			jLTrabalhosDisponiveis.setModel(cleanList);
			JOptionPane.showMessageDialog(rootPane, "Trabalho aceito com sucesso!", "Aceitar trabalho", JOptionPane.INFORMATION_MESSAGE);
			resetLayers();
			enableVoluntarioPF();
		}
		else{
			JOptionPane.showMessageDialog(rootPane, "Este trabalho já possui um voluntário!", "Aceitar trabalho", JOptionPane.ERROR_MESSAGE);
		}
    }//GEN-LAST:event_btAceitarTrabalhoActionPerformed

    private void btVerTrabalhosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btVerTrabalhosActionPerformed
        jLTrabalhosDisponiveis.setModel(converterTrabalhosDisponiveisLista());
    }//GEN-LAST:event_btVerTrabalhosActionPerformed

    private void btMeusEventosPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btMeusEventosPFActionPerformed
        resetLayers();
		pnVolPFEventos.setVisible(true); 
		pnVoluntarioPF.setVisible(true);
		taEventosVolPF.setText(gerarMeusEventosPF());
    }//GEN-LAST:event_btMeusEventosPFActionPerformed

    private void btMinhasDoacoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btMinhasDoacoesActionPerformed
        resetLayers();
		pnMinhasDoacoes.setVisible(true); 
		pnVoluntarioPF.setVisible(true);
		taDoacoesPendentes.setText(gerarMinhasDoacoesPendentes());
		taDoacoesAceitas.setText(gerarMinhasDoacoes());
    }//GEN-LAST:event_btMinhasDoacoesActionPerformed

    private void btMeusEventosFuncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btMeusEventosFuncActionPerformed
        resetLayers();
		pnFuncEventos.setVisible(true); 
		pnFuncionario.setVisible(true);
		taEventosFunc.setText(gerarMeusEventosFunc());
    }//GEN-LAST:event_btMeusEventosFuncActionPerformed

    private void btAddItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddItemActionPerformed
        listaItensTemp.add(new Item(tfNomeItem.getText(), Integer.parseInt(tfItemQuantidade.getText())));
		jLItens.setModel(converterItensLista());
    }//GEN-LAST:event_btAddItemActionPerformed

    private void btRemoveItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoveItemActionPerformed
        listaItensTemp.remove(jLItens.getSelectedIndex());
		jLItens.setModel(converterItensLista());
    }//GEN-LAST:event_btRemoveItemActionPerformed

    private void btFinalizarDoacaoPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFinalizarDoacaoPFActionPerformed
        listaDoacoesPendentes.add(new Doacao(listaVoluntarios.get(userID), Double.parseDouble(tfValorDoacao.getText()), listaItensTemp, cbRepetirDoacao.isSelected(), cbModoEntrega.getSelectedItem().toString(), false, ftfDataDoacao.getText()));
		resetLayers();
		enableVoluntarioPF();
		clearDoarPF();
		JOptionPane.showMessageDialog(rootPane, "Doação realizada com sucesso!", "Doar", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btFinalizarDoacaoPFActionPerformed

    private void ftfDuracaoInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ftfDuracaoInicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ftfDuracaoInicioActionPerformed

    private void btInfoFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btInfoFuncionarioActionPerformed
        JOptionPane.showMessageDialog(rootPane, listaFuncionarios.get(jLFuncionarios.getAnchorSelectionIndex()));
    }//GEN-LAST:event_btInfoFuncionarioActionPerformed

    private void btAddGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddGastoActionPerformed
        listaGastosTemp.add(new Gasto(tfNomeGasto.getText(), Double.parseDouble(tfValorGasto.getText())));
        jLGastos.setModel(converterGastosLista());
        lbTotalGastos.setText("R$ "+calcularTotalGastosTemp());
    }//GEN-LAST:event_btAddGastoActionPerformed

    private void btRemoveGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoveGastoActionPerformed
        listaGastosTemp.remove(jLGastos.getAnchorSelectionIndex());
        jLGastos.setModel(converterGastosLista());
        lbTotalGastos.setText("R$ "+calcularTotalGastosTemp());
    }//GEN-LAST:event_btRemoveGastoActionPerformed

    private void btCadastrarEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCadastrarEventoActionPerformed
        listaEventos.add(new Evento(tfNomeEvento.getText(), ftfDataEvento.getText(),
            String.format("%s – %s", ftfDuracaoInicio.getText(), ftfDuracaoFim.getText()),
            tpObjetivoEvento.getText(), listaGastosTemp, listaFuncionarios.get(jLFuncionarios.getAnchorSelectionIndex())));
		clearCdEvento();
		resetLayers();
		enableFuncionario();
		JOptionPane.showMessageDialog(rootPane, "Evento cadastrado com sucesso!", "Cadastrar Evento", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btCadastrarEventoActionPerformed

    private void btPatrocinarEventoSelecionadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPatrocinarEventoSelecionadoActionPerformed
        if(!listaEventos.get(jLEventosPatrocinar.getAnchorSelectionIndex()).verificarPatrocinadorRepetido(listaVoluntarios.get(userID))){
			listaEventos.get(jLEventosPatrocinar.getAnchorSelectionIndex()).addPatrocinador(listaVoluntarios.get(userID));
			JOptionPane.showMessageDialog(rootPane, "Evento patrocinado com sucesso!", "Patrocinar Evento", JOptionPane.INFORMATION_MESSAGE);
		}
		else{
			JOptionPane.showMessageDialog(rootPane, "Você já está patrocinando este evento!", "Patrocinar Evento", JOptionPane.ERROR_MESSAGE);
		}
    }//GEN-LAST:event_btPatrocinarEventoSelecionadoActionPerformed

    private void btVolPJInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btVolPJInicioActionPerformed
        resetLayers();
		enableVoluntarioPJ();
    }//GEN-LAST:event_btVolPJInicioActionPerformed

    private void btPatrocinarEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPatrocinarEventoActionPerformed
        resetLayers();
		pnVoluntarioPJ.setVisible(true);
		pnPatrocinarEvento.setVisible(true);
		jLEventosPatrocinar.setModel(converterEventosLista());
    }//GEN-LAST:event_btPatrocinarEventoActionPerformed

    private void btDoarPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDoarPJActionPerformed
        resetLayers();
		pnVoluntarioPJ.setVisible(true);
		pnDoar.setVisible(true);
    }//GEN-LAST:event_btDoarPJActionPerformed

    private void btVolPJLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btVolPJLogoutActionPerformed
        Object[] botoesDesconectar = {"Desconectar", "Voltar"};
		Object choice = JOptionPane.showOptionDialog(rootPane, "Tem certeza que deseja sair?", "Desconectar", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoesDesconectar, botoesDesconectar);
		if(choice.toString().equals("0")){
			connected = false;
			userID = 0;
			resetLayers();
			loginPanel.setVisible(true);
		}
    }//GEN-LAST:event_btVolPJLogoutActionPerformed

    private void btMeusEventosPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btMeusEventosPJActionPerformed
        resetLayers();
		pnEventosPatrocinados.setVisible(true); 
		pnVoluntarioPJ.setVisible(true);
		taEventosPatrocinados.setText(gerarMeusEventosPJ());
    }//GEN-LAST:event_btMeusEventosPJActionPerformed

    private void btMinhasDoacoesPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btMinhasDoacoesPJActionPerformed
        resetLayers();
		pnMinhasDoacoes.setVisible(true); 
		pnVoluntarioPJ.setVisible(true);
		taDoacoesPendentes.setText(gerarMinhasDoacoesPendentes());
		taDoacoesAceitas.setText(gerarMinhasDoacoes());
    }//GEN-LAST:event_btMinhasDoacoesPJActionPerformed

    private void btVerInfoEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btVerInfoEventoActionPerformed
        JOptionPane.showMessageDialog(rootPane, listaEventos.get(jLEventosPatrocinar.getAnchorSelectionIndex()));
    }//GEN-LAST:event_btVerInfoEventoActionPerformed

    private void btGestorInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGestorInicioActionPerformed
        resetLayers();
		enableGestor();
    }//GEN-LAST:event_btGestorInicioActionPerformed

    private void btGestorAnalisarReceitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGestorAnalisarReceitasActionPerformed
        resetLayers();
		pnGestor.setVisible(true);
		pnAnalisarReceitas.setVisible(true);
		taReceitas.setText(gerarInfoDoacoes());
		lbTotalReceitas.setText(String.format("Total receitas R$ %f", gerarInfoDinheiro()));
    }//GEN-LAST:event_btGestorAnalisarReceitasActionPerformed

    private void btGestorAnalisarDespesasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGestorAnalisarDespesasActionPerformed
        resetLayers();
		pnGestor.setVisible(true);
		pnAnalisarDespesas.setVisible(true);
		taDespesas.setText(gerarInfoEventos());
		lbTotalDespesas.setText(String.format("Total despesas R$ %f", gerarTotalDespesas()));
    }//GEN-LAST:event_btGestorAnalisarDespesasActionPerformed

    private void btGestorDesconectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGestorDesconectarActionPerformed
        Object[] botoesDesconectar = {"Desconectar", "Voltar"};
		Object choice = JOptionPane.showOptionDialog(rootPane, "Tem certeza que deseja sair?", "Desconectar", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoesDesconectar, botoesDesconectar);
		if(choice.toString().equals("0")){
			connected = false;
			userID = 0;
			resetLayers();
			loginPanel.setVisible(true);
		}
    }//GEN-LAST:event_btGestorDesconectarActionPerformed

    private void btGestorCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGestorCadastrarActionPerformed
        resetLayers();
		pnGestor.setVisible(true);
		pnCadastrarFunc.setVisible(true);
    }//GEN-LAST:event_btGestorCadastrarActionPerformed

    private void btGestorRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGestorRelatorioActionPerformed
        String total = String.format("Total Receitas: R$ %f\nTotal Despesas: R$ %f", gerarInfoDinheiro(), gerarTotalDespesas());
		escreverArquivoF.println(total);
		escreverArquivoF.close();
		JOptionPane.showMessageDialog(rootPane, "Arquivo Relatório Financeiro.txt gerado com sucesso!", "Relatório Financeiro", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btGestorRelatorioActionPerformed

    private void btCadastrarFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCadastrarFuncionarioActionPerformed
        listaContas.add(new Conta(idCount, tfGestorUsuario.getText(), tfGestorSenha.getText(), "Funcionário"));
		listaFuncionarios.add(new Funcionario(idCount, tfGestorNome.getText(), cbGestorSexo.getSelectedItem().toString(), ftfGestorCPF.getText(), ftfGestorTelefone.getText(), new Endereco(tfGestorRua.getText(), tfGestorNumero.getText(), tfGestorComplemento.getText(), tfGestorBairro.getText(), tfGestorCidade.getText(), cbGestorUF.getSelectedItem().toString(), ftfGestorCEP.getText())));
		JOptionPane.showMessageDialog(rootPane, "Funcionário cadastrado com sucesso!");
		idCount++;
		clearGestor();
		resetLayers();
		enableGestor();
    }//GEN-LAST:event_btCadastrarFuncionarioActionPerformed

    private void btInfoDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btInfoDoacaoActionPerformed
        JOptionPane.showMessageDialog(rootPane, listaDoacoesPendentes.get(jLDoacoesPendentes.getAnchorSelectionIndex()), "Doação", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btInfoDoacaoActionPerformed

    private void btAceitarDoacaoSelecionadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAceitarDoacaoSelecionadaActionPerformed
		listaDoacoes.add(listaDoacoesPendentes.get(jLDoacoesPendentes.getAnchorSelectionIndex()));
		listaDoacoesPendentes.remove(jLDoacoesPendentes.getAnchorSelectionIndex());
		JOptionPane.showMessageDialog(rootPane, "Doação aceita com sucesso!", "Aceitar doação", JOptionPane.INFORMATION_MESSAGE);
		jLDoacoesPendentes.setModel(converterDoacoesPendentes());
    }//GEN-LAST:event_btAceitarDoacaoSelecionadaActionPerformed

    private void btRecusarDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRecusarDoacaoActionPerformed
        listaDoacoesPendentes.remove(jLDoacoesPendentes.getAnchorSelectionIndex());
		JOptionPane.showMessageDialog(rootPane, "Doação recusada com sucesso!", "Recusar doação", JOptionPane.INFORMATION_MESSAGE);
		jLDoacoesPendentes.setModel(converterDoacoesPendentes());
    }//GEN-LAST:event_btRecusarDoacaoActionPerformed

    private void btInfoDoacao1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btInfoDoacao1ActionPerformed
        JOptionPane.showMessageDialog(rootPane, listaDoacoes.get(jLDoacoes.getAnchorSelectionIndex()), "Doação", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btInfoDoacao1ActionPerformed

    private void btConfirmarEntregaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btConfirmarEntregaActionPerformed
        listaDoacoes.get(jLDoacoes.getAnchorSelectionIndex()).setEntregue(true);
		JOptionPane.showMessageDialog(rootPane, "Status alterado para entregue!", "Gerenciar Entrega", JOptionPane.INFORMATION_MESSAGE);
		jLDoacoes.setModel(converterDoacoes());
    }//GEN-LAST:event_btConfirmarEntregaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MoraisVoluntariado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MoraisVoluntariado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MoraisVoluntariado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MoraisVoluntariado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
				try {
					new MoraisVoluntariado().setVisible(true);
				} catch (IOException ex) {
					Logger.getLogger(MoraisVoluntariado.class.getName()).log(Level.SEVERE, null, ex);
				}
            }
        });
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private keeptoo.KButton btAceitarDoacao;
    private keeptoo.KButton btAceitarDoacaoSelecionada;
    private keeptoo.KButton btAceitarTrabalho;
    private keeptoo.KButton btAddGasto;
    private keeptoo.KButton btAddItem;
    private keeptoo.KButton btAdicionarTrabalho;
    private keeptoo.KButton btCadastrarEvento;
    private keeptoo.KButton btCadastrarFuncionario;
    private keeptoo.KButton btCadastrarTrabalho;
    private keeptoo.KButton btCadastrarVolPF;
    private keeptoo.KButton btCadastrarVolPJ;
    private keeptoo.KButton btCadastroEvento;
    private keeptoo.KButton btCadastroTrabalho;
    private keeptoo.KButton btCadastroVoluntario;
    private keeptoo.KButton btConfirmarEntrega;
    private keeptoo.KButton btDoarPF;
    private keeptoo.KButton btDoarPJ;
    private keeptoo.KButton btEntregas;
    private keeptoo.KButton btFinalizarDoacaoPF;
    private keeptoo.KButton btFuncInicio;
    private keeptoo.KButton btFuncLogout1;
    private keeptoo.KButton btGestorAnalisarDespesas;
    private keeptoo.KButton btGestorAnalisarReceitas;
    private keeptoo.KButton btGestorCadastrar;
    private keeptoo.KButton btGestorDesconectar;
    private keeptoo.KButton btGestorInicio;
    private keeptoo.KButton btGestorRelatorio;
    private keeptoo.KButton btInfoDoacao;
    private keeptoo.KButton btInfoDoacao1;
    private keeptoo.KButton btInfoEvento;
    private keeptoo.KButton btInfoFuncionario;
    private keeptoo.KButton btInfoVol;
    private keeptoo.KButton btLogin;
    private keeptoo.KButton btMeusEventosFunc;
    private keeptoo.KButton btMeusEventosPF;
    private keeptoo.KButton btMeusEventosPJ;
    private keeptoo.KButton btMinhasDoacoes;
    private keeptoo.KButton btMinhasDoacoesPJ;
    private keeptoo.KButton btPatrocinarEvento;
    private keeptoo.KButton btPatrocinarEventoSelecionado;
    private keeptoo.KButton btRecusarDoacao;
    private keeptoo.KButton btRelatorios;
    private keeptoo.KButton btRemoveGasto;
    private keeptoo.KButton btRemoveItem;
    private keeptoo.KButton btRemoverTrabalho;
    private keeptoo.KButton btRemoverVol;
    private keeptoo.KButton btRemoverVoluntario;
    private keeptoo.KButton btTrabalharEvento;
    private keeptoo.KButton btVerInfoEvento;
    private keeptoo.KButton btVerTrabalhos;
    private keeptoo.KButton btVolPFInicio;
    private keeptoo.KButton btVolPFLogout;
    private keeptoo.KButton btVolPJInicio;
    private keeptoo.KButton btVolPJLogout;
    private javax.swing.JComboBox<String> cbGestorSexo;
    private javax.swing.JComboBox<String> cbGestorUF;
    private javax.swing.JComboBox<String> cbModoEntrega;
    private javax.swing.JCheckBox cbRepetirDoacao;
    private javax.swing.JComboBox<String> cbVolPFSexo;
    private javax.swing.JComboBox<String> cbVolPFTurno;
    private javax.swing.JComboBox<String> cbVolPFUF;
    private javax.swing.JComboBox<String> cbVolPJUF;
    private javax.swing.JFormattedTextField ftfDataDoacao;
    private javax.swing.JFormattedTextField ftfDataEvento;
    private javax.swing.JFormattedTextField ftfDuracaoFim;
    private javax.swing.JFormattedTextField ftfDuracaoInicio;
    private javax.swing.JFormattedTextField ftfGestorCEP;
    private javax.swing.JFormattedTextField ftfGestorCPF;
    private javax.swing.JFormattedTextField ftfGestorTelefone;
    private javax.swing.JFormattedTextField ftfVolPFCEP;
    private javax.swing.JFormattedTextField ftfVolPFCPF;
    private javax.swing.JFormattedTextField ftfVolPFTelefone;
    private javax.swing.JFormattedTextField ftfVolPJCEP;
    private javax.swing.JFormattedTextField ftfVolPJCNPJ;
    private javax.swing.JFormattedTextField ftfVolPJIE;
    private javax.swing.JFormattedTextField ftfVolPJTelefone;
    private javax.swing.JLabel iconMV;
    private javax.swing.JList<String> jLDoacoes;
    private javax.swing.JList<String> jLDoacoesPendentes;
    private javax.swing.JList<String> jLEventos;
    private javax.swing.JList<String> jLEventosPatrocinar;
    private javax.swing.JList<String> jLEventosTrabalhos;
    private javax.swing.JList<String> jLFuncionarios;
    private javax.swing.JList<String> jLGastos;
    private javax.swing.JList<String> jLItens;
    private javax.swing.JList<String> jLTrabalhos;
    private javax.swing.JList<String> jLTrabalhosDisponiveis;
    private javax.swing.JList<String> jLVoluntarios;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel lbLogin;
    private javax.swing.JLabel lbSenha;
    private javax.swing.JLabel lbTituloFuncionario;
    private javax.swing.JLabel lbTituloVolPF;
    private javax.swing.JLabel lbTituloVolPF1;
    private javax.swing.JLabel lbTituloVolPF2;
    private javax.swing.JLabel lbTotalDespesas;
    private javax.swing.JLabel lbTotalGastos;
    private javax.swing.JLabel lbTotalReceitas;
    private javax.swing.JLayeredPane ldFuncionario;
    private javax.swing.JLayeredPane ldGestor;
    private javax.swing.JLayeredPane ldMenus;
    private javax.swing.JLayeredPane ldVoluntarioPF;
    private javax.swing.JLayeredPane ldVoluntarioPJ;
    private keeptoo.KGradientPanel loginPanel;
    private javax.swing.JPasswordField pfSenha;
    private javax.swing.JPanel pnAceitarDoacao;
    private javax.swing.JPanel pnAnalisarDespesas;
    private javax.swing.JPanel pnAnalisarReceitas;
    private javax.swing.JPanel pnCadastrarFunc;
    private javax.swing.JPanel pnCdEvento;
    private javax.swing.JPanel pnCdTrabalho;
    private javax.swing.JPanel pnCdVolPF;
    private javax.swing.JPanel pnCdVolPJ;
    private keeptoo.KGradientPanel pnCdVolTitle;
    private keeptoo.KGradientPanel pnCdVolTitle1;
    private keeptoo.KGradientPanel pnCdVolTitle2;
    private keeptoo.KGradientPanel pnCdVolTitle4;
    private javax.swing.JPanel pnDados;
    private javax.swing.JPanel pnDados1;
    private javax.swing.JPanel pnDados2;
    private javax.swing.JPanel pnDoar;
    private javax.swing.JPanel pnEndereco;
    private javax.swing.JPanel pnEndereco1;
    private javax.swing.JPanel pnEndereco2;
    private javax.swing.JPanel pnEventosPatrocinados;
    private javax.swing.JPanel pnFuncEventos;
    private javax.swing.JPanel pnFuncMain;
    private keeptoo.KGradientPanel pnFuncionario;
    private javax.swing.JPanel pnGerenciarEntrega;
    private keeptoo.KGradientPanel pnGestor;
    private javax.swing.JPanel pnGestorInicio;
    private keeptoo.KGradientPanel pnMainTitle;
    private keeptoo.KGradientPanel pnMainTitle1;
    private keeptoo.KGradientPanel pnMainTitle10;
    private keeptoo.KGradientPanel pnMainTitle11;
    private keeptoo.KGradientPanel pnMainTitle12;
    private keeptoo.KGradientPanel pnMainTitle13;
    private keeptoo.KGradientPanel pnMainTitle14;
    private keeptoo.KGradientPanel pnMainTitle15;
    private keeptoo.KGradientPanel pnMainTitle2;
    private keeptoo.KGradientPanel pnMainTitle3;
    private keeptoo.KGradientPanel pnMainTitle4;
    private keeptoo.KGradientPanel pnMainTitle5;
    private keeptoo.KGradientPanel pnMainTitle6;
    private keeptoo.KGradientPanel pnMainTitle7;
    private keeptoo.KGradientPanel pnMainTitle8;
    private keeptoo.KGradientPanel pnMainTitle9;
    private javax.swing.JPanel pnMinhasDoacoes;
    private javax.swing.JPanel pnPatrocinarEvento;
    private keeptoo.KGradientPanel pnPatrocinarTitle;
    private javax.swing.JPanel pnRelatorioFinanc;
    private javax.swing.JPanel pnRvVol;
    private javax.swing.JPanel pnTbEvento;
    private javax.swing.JPanel pnVolMain;
    private javax.swing.JPanel pnVolPFEventos;
    private keeptoo.KGradientPanel pnVoluntarioPF;
    private keeptoo.KGradientPanel pnVoluntarioPJ;
    private javax.swing.JTextArea taDespesas;
    private javax.swing.JTextArea taDoacoesAceitas;
    private javax.swing.JTextArea taDoacoesAceitas1;
    private javax.swing.JTextArea taDoacoesPendentes;
    private javax.swing.JTextArea taDoacoesPendentes1;
    private javax.swing.JTextArea taEventosFunc;
    private javax.swing.JTextArea taEventosPatrocinados;
    private javax.swing.JTextArea taEventosVolPF;
    private javax.swing.JTextArea taInfoFuncionario;
    private javax.swing.JTextArea taInfoGestor;
    private javax.swing.JTextArea taInfoVoluntarioPF;
    private javax.swing.JTextArea taReceitas;
    private javax.swing.JTextField tfGestorBairro;
    private javax.swing.JTextField tfGestorCidade;
    private javax.swing.JTextField tfGestorComplemento;
    private javax.swing.JTextField tfGestorNome;
    private javax.swing.JTextField tfGestorNumero;
    private javax.swing.JTextField tfGestorRua;
    private javax.swing.JTextField tfGestorSenha;
    private javax.swing.JTextField tfGestorUsuario;
    private javax.swing.JTextField tfItemQuantidade;
    private javax.swing.JTextField tfLogin;
    private javax.swing.JTextField tfNomeEvento;
    private javax.swing.JTextField tfNomeGasto;
    private javax.swing.JTextField tfNomeItem;
    private javax.swing.JTextField tfNomeTrabalho;
    private javax.swing.JTextField tfValorDoacao;
    private javax.swing.JTextField tfValorGasto;
    private javax.swing.JTextField tfVolPFBairro;
    private javax.swing.JTextField tfVolPFCidade;
    private javax.swing.JTextField tfVolPFComplemento;
    private javax.swing.JTextField tfVolPFNome;
    private javax.swing.JTextField tfVolPFNumero;
    private javax.swing.JTextField tfVolPFRua;
    private javax.swing.JTextField tfVolPFSenha;
    private javax.swing.JTextField tfVolPFUsuario;
    private javax.swing.JTextField tfVolPJBairro;
    private javax.swing.JTextField tfVolPJCidade;
    private javax.swing.JTextField tfVolPJComplemento;
    private javax.swing.JTextField tfVolPJNome;
    private javax.swing.JTextField tfVolPJNumero;
    private javax.swing.JTextField tfVolPJRua;
    private javax.swing.JTextField tfVolPJSenha;
    private javax.swing.JTextField tfVolPJUsuario;
    private javax.swing.JTextPane tpDescricaoTrabalho;
    private javax.swing.JTextPane tpObjetivoEvento;
    // End of variables declaration//GEN-END:variables

}
