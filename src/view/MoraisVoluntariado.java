/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
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
	
	
    public MoraisVoluntariado() {
        initComponents();
		resetLayers();
		loginPanel.setVisible(true);
		
		listaContas.add(new Conta(0, "lucaslins", "88219442", "Funcionário"));
		listaContas.add(new Conta(1, "alana", "d8cs9tua", "VoluntárioPF"));
		listaFuncionarios.add(new Funcionario(0, "Lucas Lins", "Masculino", "123456789", "88219442", null));
		listaVoluntarios.add(new VoluntarioPF(1, "Linsdo", "83982105547", null, "12345", "Masculino", "Noite"));
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
		
		// Paineis do VoluntárioPF
		pnVoluntarioPF.setVisible(false);
		pnVolPFMain.setVisible(false);
		pnTbEvento.setVisible(false);
		pnDoarPF.setVisible(false);
		pnVolPFEventos.setVisible(false);
	}
	
	public void enableFuncionario(){
		pnFuncMain.setVisible(true); // Paginá principal Funcionário
		pnFuncionario.setVisible(true); // Menu Funcionário
		taInfoFuncionario.setText(listaFuncionarios.get(userID).toString());
	}
	
	public void enableVoluntarioPF(){
		pnVolPFMain.setVisible(true); // Paginá principal Funcionário
		pnVoluntarioPF.setVisible(true); // Menu Funcionário
		taInfoVoluntarioPF.setText(listaVoluntarios.get(userID).toString());
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
	
	public String gerarMeusEventosFunc(){
		String eventos = "";
		for(int i = 0; i < listaEventos.size(); i++){
			if(listaEventos.get(i).getResponsavel() == listaFuncionarios.get(userID)){
				eventos += String.format("%s\nTrabalhos:\n%s\n\n", listaEventos.get(i).toString(), listaEventos.get(i).infoTrabalhos());
			}
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
        btImportarDados = new keeptoo.KButton();
        btFuncLogout1 = new keeptoo.KButton();
        btMeusEventosFunc = new keeptoo.KButton();
        pnVoluntarioPF = new keeptoo.KGradientPanel();
        lbTituloVolPF = new javax.swing.JLabel();
        btVolPFInicio = new keeptoo.KButton();
        btTrabalharEvento = new keeptoo.KButton();
        btDoarPF = new keeptoo.KButton();
        btVolPFLogout = new keeptoo.KButton();
        btMeusEventosPF = new keeptoo.KButton();
        btDoarPF1 = new keeptoo.KButton();
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
        ldVoluntarioPF = new javax.swing.JLayeredPane();
        pnVolPFMain = new javax.swing.JPanel();
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
        pnDoarPF = new javax.swing.JPanel();
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

        btImportarDados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/import.png"))); // NOI18N
        btImportarDados.setText("Importar Dados");
        btImportarDados.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btImportarDados.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btImportarDados.setIconTextGap(0);
        btImportarDados.setkBorderRadius(0);
        btImportarDados.setkEndColor(new java.awt.Color(233, 193, 253));
        btImportarDados.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btImportarDados.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btImportarDados.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btImportarDados.setkPressedColor(new java.awt.Color(250, 209, 254));
        btImportarDados.setkStartColor(new java.awt.Color(199, 96, 230));
        btImportarDados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btImportarDadosActionPerformed(evt);
            }
        });
        pnFuncionario.add(btImportarDados, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 160, 40));

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
        pnFuncionario.add(btFuncLogout1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 440, 160, 40));

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

        btDoarPF1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/donates.png"))); // NOI18N
        btDoarPF1.setText("Minhas doações");
        btDoarPF1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btDoarPF1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btDoarPF1.setIconTextGap(0);
        btDoarPF1.setkBorderRadius(0);
        btDoarPF1.setkEndColor(new java.awt.Color(233, 193, 253));
        btDoarPF1.setkHoverEndColor(new java.awt.Color(236, 174, 243));
        btDoarPF1.setkHoverForeGround(new java.awt.Color(153, 0, 255));
        btDoarPF1.setkHoverStartColor(new java.awt.Color(221, 143, 253));
        btDoarPF1.setkPressedColor(new java.awt.Color(250, 209, 254));
        btDoarPF1.setkStartColor(new java.awt.Color(199, 96, 230));
        btDoarPF1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDoarPF1ActionPerformed(evt);
            }
        });
        pnVoluntarioPF.add(btDoarPF1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 160, 40));

        ldMenus.add(pnVoluntarioPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

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

        getContentPane().add(ldFuncionario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        ldVoluntarioPF.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnVolPFMain.setBackground(new java.awt.Color(204, 204, 204));
        pnVolPFMain.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        pnVolPFMain.add(pnMainTitle4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 640, 40));

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

        pnVolPFMain.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 620, 440));

        ldVoluntarioPF.add(pnVolPFMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

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

        pnDoarPF.setBackground(new java.awt.Color(204, 204, 204));
        pnDoarPF.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        pnDoarPF.add(pnMainTitle8, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 640, 40));

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

        pnDoarPF.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 60, 410, 140));

        jLabel44.setText("Dados");
        pnDoarPF.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 44, -1, 20));

        jLItens.setModel(converterFuncionariosLista());
        jScrollPane5.setViewportView(jLItens);

        pnDoarPF.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 230, 410, 110));

        jLabel48.setText("Itens");
        pnDoarPF.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 210, -1, 20));

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
        pnDoarPF.add(btAddItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 350, 60, 20));
        pnDoarPF.add(tfNomeItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 350, 130, -1));

        jLabel77.setText("Nome");
        pnDoarPF.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 350, 40, 20));

        jLabel78.setText("Quantidade");
        pnDoarPF.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 350, 70, 20));

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
        pnDoarPF.add(btRemoveItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 230, 80, 20));
        pnDoarPF.add(tfItemQuantidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 350, 90, -1));

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
        pnDoarPF.add(btFinalizarDoacaoPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 380, 100, 50));

        ldVoluntarioPF.add(pnDoarPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        getContentPane().add(ldVoluntarioPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tfLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfLoginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfLoginActionPerformed

    private void btEntregasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEntregasActionPerformed
        // TODO add your handling code here:
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
        // TODO add your handling code here:
    }//GEN-LAST:event_btAceitarDoacaoActionPerformed

    private void btRemoverVoluntarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoverVoluntarioActionPerformed
        resetLayers();
		pnFuncionario.setVisible(true);
		pnRvVol.setVisible(true);
		jLVoluntarios.setModel(converterVoluntariosLista());
    }//GEN-LAST:event_btRemoverVoluntarioActionPerformed

    private void btRelatoriosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRelatoriosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btRelatoriosActionPerformed

    private void btImportarDadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btImportarDadosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btImportarDadosActionPerformed

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
		pnDoarPF.setVisible(true);
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

    private void btDoarPF1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDoarPF1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btDoarPF1ActionPerformed

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
		JOptionPane.showMessageDialog(rootPane, listaDoacoesPendentes.get(0));
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
                new MoraisVoluntariado().setVisible(true);
            }
        });
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private keeptoo.KButton btAceitarDoacao;
    private keeptoo.KButton btAceitarTrabalho;
    private keeptoo.KButton btAddGasto;
    private keeptoo.KButton btAddItem;
    private keeptoo.KButton btAdicionarTrabalho;
    private keeptoo.KButton btCadastrarEvento;
    private keeptoo.KButton btCadastrarTrabalho;
    private keeptoo.KButton btCadastrarVolPF;
    private keeptoo.KButton btCadastrarVolPJ;
    private keeptoo.KButton btCadastroEvento;
    private keeptoo.KButton btCadastroTrabalho;
    private keeptoo.KButton btCadastroVoluntario;
    private keeptoo.KButton btDoarPF;
    private keeptoo.KButton btDoarPF1;
    private keeptoo.KButton btEntregas;
    private keeptoo.KButton btFinalizarDoacaoPF;
    private keeptoo.KButton btFuncInicio;
    private keeptoo.KButton btFuncLogout1;
    private keeptoo.KButton btImportarDados;
    private keeptoo.KButton btInfoEvento;
    private keeptoo.KButton btInfoFuncionario;
    private keeptoo.KButton btInfoVol;
    private keeptoo.KButton btLogin;
    private keeptoo.KButton btMeusEventosFunc;
    private keeptoo.KButton btMeusEventosPF;
    private keeptoo.KButton btRelatorios;
    private keeptoo.KButton btRemoveGasto;
    private keeptoo.KButton btRemoveItem;
    private keeptoo.KButton btRemoverTrabalho;
    private keeptoo.KButton btRemoverVol;
    private keeptoo.KButton btRemoverVoluntario;
    private keeptoo.KButton btTrabalharEvento;
    private keeptoo.KButton btVerTrabalhos;
    private keeptoo.KButton btVolPFInicio;
    private keeptoo.KButton btVolPFLogout;
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
    private javax.swing.JFormattedTextField ftfVolPFCEP;
    private javax.swing.JFormattedTextField ftfVolPFCPF;
    private javax.swing.JFormattedTextField ftfVolPFTelefone;
    private javax.swing.JFormattedTextField ftfVolPJCEP;
    private javax.swing.JFormattedTextField ftfVolPJCNPJ;
    private javax.swing.JFormattedTextField ftfVolPJIE;
    private javax.swing.JFormattedTextField ftfVolPJTelefone;
    private javax.swing.JLabel iconMV;
    private javax.swing.JList<String> jLEventos;
    private javax.swing.JList<String> jLEventosTrabalhos;
    private javax.swing.JList<String> jLFuncionarios;
    private javax.swing.JList<String> jLGastos;
    private javax.swing.JList<String> jLItens;
    private javax.swing.JList<String> jLTrabalhos;
    private javax.swing.JList<String> jLTrabalhosDisponiveis;
    private javax.swing.JList<String> jLVoluntarios;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
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
    private javax.swing.JLabel lbTotalGastos;
    private javax.swing.JLayeredPane ldFuncionario;
    private javax.swing.JLayeredPane ldMenus;
    private javax.swing.JLayeredPane ldVoluntarioPF;
    private keeptoo.KGradientPanel loginPanel;
    private javax.swing.JPasswordField pfSenha;
    private javax.swing.JPanel pnCdEvento;
    private javax.swing.JPanel pnCdTrabalho;
    private javax.swing.JPanel pnCdVolPF;
    private javax.swing.JPanel pnCdVolPJ;
    private keeptoo.KGradientPanel pnCdVolTitle;
    private keeptoo.KGradientPanel pnCdVolTitle1;
    private keeptoo.KGradientPanel pnCdVolTitle2;
    private javax.swing.JPanel pnDados;
    private javax.swing.JPanel pnDados1;
    private javax.swing.JPanel pnDoarPF;
    private javax.swing.JPanel pnEndereco;
    private javax.swing.JPanel pnEndereco1;
    private javax.swing.JPanel pnFuncEventos;
    private javax.swing.JPanel pnFuncMain;
    private keeptoo.KGradientPanel pnFuncionario;
    private keeptoo.KGradientPanel pnMainTitle;
    private keeptoo.KGradientPanel pnMainTitle1;
    private keeptoo.KGradientPanel pnMainTitle2;
    private keeptoo.KGradientPanel pnMainTitle3;
    private keeptoo.KGradientPanel pnMainTitle4;
    private keeptoo.KGradientPanel pnMainTitle5;
    private keeptoo.KGradientPanel pnMainTitle6;
    private keeptoo.KGradientPanel pnMainTitle8;
    private javax.swing.JPanel pnRvVol;
    private javax.swing.JPanel pnTbEvento;
    private javax.swing.JPanel pnVolPFEventos;
    private javax.swing.JPanel pnVolPFMain;
    private keeptoo.KGradientPanel pnVoluntarioPF;
    private javax.swing.JTextArea taEventosFunc;
    private javax.swing.JTextArea taEventosVolPF;
    private javax.swing.JTextArea taInfoFuncionario;
    private javax.swing.JTextArea taInfoVoluntarioPF;
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
