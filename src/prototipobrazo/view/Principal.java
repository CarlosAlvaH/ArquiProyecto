package prototipobrazo.view;

import DAO.CajaDAO;
import DAO.CajaDAOInt;
import DAO.EstanteDAO;
import DAO.EstanteDaoInt;
import DAO.UsuarioDAO;
import clases.Usuario;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import prototipobrazo.clase.Caja;
import prototipobrazo.clase.Conexion;
import prototipobrazo.clase.Estante;

public class Principal extends javax.swing.JFrame {

    private static final Conexion conexion = new Conexion();
    LecturaQr lecQr = new LecturaQr();
    UsuarioDAO consult_usu = new UsuarioDAO();
    Usuario us = new Usuario();

    public Principal(String u) {
        initComponents();
        us.setUser(u);
        llenarCombo();
        dato();
        rellenarDatos();
        combEst();
        llenarPorcentaje();
        cambiarGraficos();
        //cargarImagen();
        cargarUser();
        //cargarUser();
    }

    public void cargarUser() {
        Usuario use = consult_usu.CargarUsuario(us.getUser());
        if (use != null) {
        ImageIcon image = (ImageIcon) use.getFoto();
        int tamano = Math.min(fotoLbl.getWidth(), fotoLbl.getHeight());
        Image escalaImage = image.getImage().getScaledInstance(tamano, tamano, Image.SCALE_SMOOTH);
        ImageIcon nuevaImg = new ImageIcon(escalaImage);
        fotoLbl.setIcon(nuevaImg);
        
        lblNombre.setText(use.getNombre());
        }
    }

    public void llenarCombo() {
        String[] puertos = conexion.obtenerLista().split(",");
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (int i = 0; i < puertos.length; i++) {
            model.addElement(puertos[i]);
        }
        cmbPuer.setModel(model);
    }

    public void combEst() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        EstanteDaoInt estDa = new EstanteDAO();
        List<Estante> listEst = new ArrayList<>();
        listEst = estDa.BuscarEstado("VACIO");
        for (Estante est : listEst) {
            model.addElement(est.getIdEstante());
        }
        cmbEst.setModel(model);
    }

    public static Conexion getConexion() {
        return conexion;
    }

    public void dato() {

        txtCodigo.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                transferirSiNoVacio();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                transferirSiNoVacio();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // No se utiliza para JTextField
            }
        });
    }

    private void transferirSiNoVacio() {
        // Obtener el texto del textField1
        String texto = txtCodigo.getText().trim();

        // Transferir datos solo si el textField1 no está vacíoS
        if (!texto.isEmpty()) {
            // Establecer el texto en textField2
            CajaDAOInt cajDa = new CajaDAO();
            Caja caj = new Caja();
            caj = cajDa.Buscar(Integer.parseInt(txtCodigo.getText().trim()));
            txtNom.setText(caj.getNombre());
            txtCant.setText(String.valueOf(caj.getCantidad()));
            txtTipo.setText(caj.getTipo());
            txtFechIn.setText(caj.getFec_ing());
            txtFecSal.setText(caj.getFec_sal());
            txtPeso.setText(caj.getPeso());

            // Marcar que la transferencia se ha realizado
        }
    }

    private void rellenarDatos() {

        try {
            List<Estante> listaEst;
            EstanteDaoInt esDao = new EstanteDAO();
            CajaDAOInt caDao = new CajaDAO();
            DefaultTableModel defaultTableModel = (DefaultTableModel) tableEstante.getModel();
            listaEst = esDao.Listar();
            defaultTableModel.setNumRows(0);
            defaultTableModel.setColumnCount(3);
            for (Estante est : listaEst) {

                Caja ca = caDao.Buscar(est.getIdCaja());
                if (ca != null) {
                    Object[] fila = {ca.getIdCaja(), ca.getNombre(), est.getIdEstante()};
                    defaultTableModel.addRow(fila);
                }
            }
            String[] titulo = new String[]{"Id_Caja", "Nombre", "Id_Estante"};
            defaultTableModel.setColumnIdentifiers(titulo);
            JTableHeader TblHeader = tableEstante.getTableHeader();
            tableEstante.getTableHeader().setBackground(Color.BLUE);
            tableEstante.getTableHeader().setForeground(Color.WHITE);
            Font fuente = new Font("Microsoft Sans Serif", Font.PLAIN, 16);
            tableEstante.getTableHeader().setFont(fuente);

        } catch (Exception e) {
            System.out.println("Error tabla :" + e.getMessage());
        }

    }

    private void limpiar() {
        txtNom.setText("");
        txtCant.setText("");
        txtTipo.setText("");
        txtFechIn.setText("");
        txtFecSal.setText("");
        txtPeso.setText("");
    }

    private void RegistrarIngreso() {
        CajaDAOInt cajDa = new CajaDAO();
        EstanteDaoInt esDa = new EstanteDAO();
        Estante est = new Estante();
        String cod = cmbEst.getSelectedItem().toString();
        est.setIdEstante(cod);
        est.setEstado("LLENO");
        est.setIdCaja(Integer.parseInt(txtCodigo.getText()));
        esDa.actualizar(est);
    }

    private void RegistrarSalida() {
        EstanteDaoInt esDa = new EstanteDAO();
        Estante est = new Estante();
        String dato = "";
        dato = (String) tableEstante.getValueAt(tableEstante.getSelectedRow(), 2);
        est.setIdEstante(dato);
        est.setEstado("VACIO");
        esDa.vaciar(est);
    }

    private void llenarPorcentaje() {
        EstanteDaoInt estDa = new EstanteDAO();
        List<Estante> estList = estDa.Listar();
        boolean aLLeno = false;
        boolean bLLeno = false;
        boolean cLLeno = false;
        boolean dLLeno = false;
        boolean eLLeno = false;
        boolean fLLeno = false;
        boolean a1LLeno = false;
        boolean b1LLeno = false;
        boolean c1LLeno = false;
        boolean d1LLeno = false;
        boolean e1LLeno = false;
        boolean f1LLeno = false;

        for (Estante estante : estList) {
            switch (estante.getIdEstante()) {
                case "A01":
                    if ("LLENO".equals(estante.getEstado())) {
                        aLLeno = true;
                    }
                    break;
                case "A02":
                    if ("LLENO".equals(estante.getEstado())) {
                        a1LLeno = true;
                    }
                    break;
                case "B01":
                    if ("LLENO".equals(estante.getEstado())) {
                        bLLeno = true;
                    }
                    break;
                case "B02":
                    if ("LLENO".equals(estante.getEstado())) {
                        b1LLeno = true;
                    }
                    break;
                case "C01":
                    if ("LLENO".equals(estante.getEstado())) {
                        cLLeno = true;
                    }
                    break;
                case "C02":
                    if ("LLENO".equals(estante.getEstado())) {
                        c1LLeno = true;
                    }
                    break;
                case "D01":
                    if ("LLENO".equals(estante.getEstado())) {
                        dLLeno = true;
                    }
                    break;
                case "D02":
                    if ("LLENO".equals(estante.getEstado())) {
                        d1LLeno = true;
                    }
                    break;
                case "E01":
                    if ("LLENO".equals(estante.getEstado())) {
                        eLLeno = true;
                    }
                    break;
                case "E02":
                    if ("LLENO".equals(estante.getEstado())) {
                        e1LLeno = true;
                    }
                    break;
                case "F01":
                    if ("LLENO".equals(estante.getEstado())) {
                        fLLeno = true;
                    }
                    break;
                case "F02":
                    if ("LLENO".equals(estante.getEstado())) {
                        f1LLeno = true;
                    }
                    break;
            }

        }
        int porcentajeA = calcularPorcentaje(aLLeno, a1LLeno);
        int porcentajeB = calcularPorcentaje(bLLeno, b1LLeno);
        int porcentajeC = calcularPorcentaje(cLLeno, c1LLeno);
        int porcentajeD = calcularPorcentaje(dLLeno, d1LLeno);
        int porcentajeE = calcularPorcentaje(eLLeno, e1LLeno);
        int porcentajeF = calcularPorcentaje(fLLeno, f1LLeno);

        lbA.setText(porcentajeA + "%");
        lbB.setText(porcentajeB + "%");
        lbC.setText(porcentajeC + "%");
        lbD.setText(porcentajeD + "%");
        lbE.setText(porcentajeE + "%");
        lbF.setText(porcentajeF + "%");

    }

    private int calcularPorcentaje(boolean estado, boolean soloUnoLLeno) {
        if (estado && soloUnoLLeno == true) {
            return 100;
        } else if (estado == false && soloUnoLLeno == false) {
            return 0;
        } else {
            return 50;
        }
    }

    private void cambiarGraficos() {
        ImageIcon vacio = new ImageIcon("src/imagen/Vacio.png"); // Reemplaza con la ruta de tu imagen
        ImageIcon lleno = new ImageIcon("src/imagen/Lleno.png");
        switch (lbA.getText()) {
            case "100%":
                lbA1.setIcon(lleno);
                lbA2.setIcon(lleno);
                break;
            case "50%":
                lbA1.setIcon(lleno);
                lbA2.setIcon(vacio);
                break;
            case "0%":
                lbA1.setIcon(vacio);
                lbA2.setIcon(vacio);
                break;
            default:
        }
        switch (lbB.getText()) {
            case "100%":
                lbB1.setIcon(lleno);
                lbB2.setIcon(lleno);
                break;
            case "50%":
                lbB1.setIcon(lleno);
                lbB2.setIcon(vacio);
                break;
            case "0%":
                lbB1.setIcon(vacio);
                lbB2.setIcon(vacio);
                break;
            default:
        }
        switch (lbC.getText()) {
            case "100%":
                lbC1.setIcon(lleno);
                lbC2.setIcon(lleno);
                break;
            case "50%":
                lbC1.setIcon(lleno);
                lbC2.setIcon(vacio);
                break;
            case "0%":
                lbC1.setIcon(vacio);
                lbC2.setIcon(vacio);
                break;
            default:
        }
        switch (lbD.getText()) {
            case "100%":
                lbD1.setIcon(lleno);
                lbD2.setIcon(lleno);
                break;
            case "50%":
                lbD1.setIcon(lleno);
                lbD2.setIcon(vacio);
                break;
            case "0%":
                lbD1.setIcon(vacio);
                lbD2.setIcon(vacio);
                break;
            default:
        }
        switch (lbE.getText()) {
            case "100%":
                lbE1.setIcon(lleno);
                lbE2.setIcon(lleno);
                break;
            case "50%":
                lbE1.setIcon(lleno);
                lbE2.setIcon(vacio);
                break;
            case "0%":
                lbE1.setIcon(vacio);
                lbE2.setIcon(vacio);
                break;
            default:
        }
        switch (lbF.getText()) {
            case "100%":
                lbF1.setIcon(lleno);
                lbF2.setIcon(lleno);
                break;
            case "50%":
                lbF1.setIcon(lleno);
                lbF2.setIcon(vacio);
                break;
            case "0%":
                lbF1.setIcon(vacio);
                lbF2.setIcon(vacio);
                break;
            default:
        }

    }

    private void cargarImagen() {
        ImageIcon brazo = new ImageIcon("src/imagen/Brazo Test.png"); // Reemplaza con la ruta de tu imagen
        ImageIcon logo = new ImageIcon("src/imagen/Logo_Principal.png");
        lbLogo.setIcon(logo);
        lbTest.setIcon(brazo);
    }

    private void extraerCaja() {
        String estante = "";
        estante = (String) tableEstante.getValueAt(tableEstante.getSelectedRow(), 2);
        switch (estante) {
            case "A01":
                conexion.escribir(301);
                break;
            case "A02":
                conexion.escribir(302);
                break;
            case "B01":
                conexion.escribir(303);
                break;
            case "B02":
                conexion.escribir(304);
                break;
            case "C01":
                conexion.escribir(305);
                break;
            case "C02":
                conexion.escribir(306);
                break;
            case "D01":
                conexion.escribir(307);
                break;
            case "D02":
                conexion.escribir(308);
                break;
            case "E01":
                conexion.escribir(309);
                break;
            case "E02":
                conexion.escribir(310);
                break;
            case "F01":
                conexion.escribir(311);
                break;
            case "F02":
                conexion.escribir(312);
                break;
        }
    }
    
    private void ingresarCaja(){
        String estante = "";
        estante = cmbEst.getSelectedItem().toString().trim();
        
        switch (estante) {
            case "A01":
                conexion.escribir(401);
                break;
            case "A02":
                conexion.escribir(402);
                break;
            case "B01":
                conexion.escribir(403);
                break;
            case "B02":
                conexion.escribir(404);
                break;
            case "C01":
                conexion.escribir(405);
                break;
            case "C02":
                conexion.escribir(406);
                break;
            case "D01":
                conexion.escribir(407);
                break;
            case "D02":
                conexion.escribir(408);
                break;
            case "E01":
                conexion.escribir(409);
                break;
            case "E02":
                conexion.escribir(410);
                break;
            case "F01":
                conexion.escribir(411);
                break;
            case "F02":
                conexion.escribir(412);
                break;
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel10 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        btnConectar = new javax.swing.JButton();
        lblEst = new javax.swing.JLabel();
        cmbPuer = new javax.swing.JComboBox<>();
        fotoLbl = new javax.swing.JLabel();
        lbLogo = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        btnTestA = new javax.swing.JButton();
        btnTestB = new javax.swing.JButton();
        btnTestC = new javax.swing.JButton();
        btnTestD = new javax.swing.JButton();
        btnTestE = new javax.swing.JButton();
        lbTest = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        btnIngresar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        imagen = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNom = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtPeso = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtCant = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTipo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtFechIn = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtHorIn = new javax.swing.JTextField();
        txtFecSal = new javax.swing.JTextField();
        FecHorSa = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableEstante = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        lbA1 = new javax.swing.JLabel();
        lbA2 = new javax.swing.JLabel();
        lbB1 = new javax.swing.JLabel();
        lbC1 = new javax.swing.JLabel();
        lbB2 = new javax.swing.JLabel();
        lbC2 = new javax.swing.JLabel();
        lbD1 = new javax.swing.JLabel();
        lbD2 = new javax.swing.JLabel();
        lbE1 = new javax.swing.JLabel();
        lbE2 = new javax.swing.JLabel();
        lbF1 = new javax.swing.JLabel();
        lbF2 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        cmbEst = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        lbF = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lbA = new javax.swing.JLabel();
        lbB = new javax.swing.JLabel();
        lbC = new javax.swing.JLabel();
        lbD = new javax.swing.JLabel();
        lbE = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HandBot");
        setBackground(new java.awt.Color(0, 0, 0));
        setForeground(java.awt.Color.black);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel10.setBackground(new java.awt.Color(13, 18, 22));

        jPanel1.setBackground(new java.awt.Color(13, 18, 22));

        jPanel6.setBackground(new java.awt.Color(13, 18, 22));

        jLabel1.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("HandBot");

        lblNombre.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(255, 255, 255));
        lblNombre.setText("Nombre / Apelldo");

        btnConectar.setBackground(new java.awt.Color(0, 0, 32));
        btnConectar.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 12)); // NOI18N
        btnConectar.setForeground(new java.awt.Color(255, 255, 255));
        btnConectar.setText("Coneccion");
        btnConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConectarActionPerformed(evt);
            }
        });

        lblEst.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEst.setForeground(new java.awt.Color(255, 255, 255));
        lblEst.setText("Estado");

        cmbPuer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbPuer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPuerActionPerformed(evt);
            }
        });

        fotoLbl.setText("jLabel12");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(btnConectar)
                .addGap(18, 18, 18)
                .addComponent(lblEst)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cmbPuer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNombre)
                    .addComponent(fotoLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(lbLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(31, 31, 31))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addComponent(lbLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fotoLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNombre)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConectar)
                    .addComponent(lblEst)
                    .addComponent(cmbPuer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(20, 31, 37));
        jPanel7.setForeground(new java.awt.Color(20, 31, 37));

        jButton1.setBackground(new java.awt.Color(20, 31, 37));
        jButton1.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Escanear Qr");
        jButton1.setBorder(null);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel9.setBackground(new java.awt.Color(20, 31, 37));

        btnTestA.setBackground(new java.awt.Color(204, 0, 51));
        btnTestA.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        btnTestA.setForeground(new java.awt.Color(255, 255, 255));
        btnTestA.setText("Test A");
        btnTestA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTestAActionPerformed(evt);
            }
        });

        btnTestB.setBackground(new java.awt.Color(0, 51, 255));
        btnTestB.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        btnTestB.setForeground(new java.awt.Color(255, 255, 255));
        btnTestB.setText("Test B");
        btnTestB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTestBActionPerformed(evt);
            }
        });

        btnTestC.setBackground(new java.awt.Color(255, 153, 51));
        btnTestC.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        btnTestC.setForeground(new java.awt.Color(255, 255, 255));
        btnTestC.setText("Test C");
        btnTestC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTestCActionPerformed(evt);
            }
        });

        btnTestD.setBackground(new java.awt.Color(0, 204, 51));
        btnTestD.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        btnTestD.setForeground(new java.awt.Color(255, 255, 255));
        btnTestD.setText("Test D");
        btnTestD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTestDActionPerformed(evt);
            }
        });

        btnTestE.setBackground(new java.awt.Color(51, 204, 255));
        btnTestE.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        btnTestE.setForeground(new java.awt.Color(255, 255, 255));
        btnTestE.setText("Test E");
        btnTestE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTestEActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTestA)
                    .addComponent(btnTestB)
                    .addComponent(btnTestC)
                    .addComponent(btnTestE)
                    .addComponent(btnTestD))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addComponent(lbTest, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbTest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(btnTestA)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTestB)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTestC)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTestD)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTestE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel8.setBackground(new java.awt.Color(20, 31, 37));

        btnIngresar.setBackground(new java.awt.Color(120, 147, 166));
        btnIngresar.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        btnIngresar.setForeground(new java.awt.Color(255, 255, 255));
        btnIngresar.setText("Ingresar Material");
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(120, 147, 166));
        btnSalir.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setText("Salida Material");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnIngresar, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(btnIngresar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSalir)
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 9, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(16, 16, 16))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel11.setBackground(new java.awt.Color(36, 50, 57));
        jPanel11.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel3.setBackground(new java.awt.Color(20, 31, 37));
        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black));
        jPanel3.setForeground(java.awt.Color.white);

        jLabel2.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        jLabel2.setForeground(java.awt.Color.white);
        jLabel2.setText("Especificaciones de la caja");

        imagen.setText("Image");

        jLabel3.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        jLabel3.setForeground(java.awt.Color.white);
        jLabel3.setText("Codigo");

        jLabel4.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        jLabel4.setForeground(java.awt.Color.white);
        jLabel4.setText("Nombre");

        txtNom.setEditable(false);

        jLabel5.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        jLabel5.setForeground(java.awt.Color.white);
        jLabel5.setText("Peso");

        txtPeso.setEditable(false);

        jLabel6.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        jLabel6.setForeground(java.awt.Color.white);
        jLabel6.setText("Cantidad");

        txtCant.setEditable(false);

        jLabel7.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        jLabel7.setForeground(java.awt.Color.white);
        jLabel7.setText("Tipo");

        txtTipo.setEditable(false);

        jLabel8.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        jLabel8.setForeground(java.awt.Color.white);
        jLabel8.setText("Fecha y Hora de Ingreso");

        txtFechIn.setEditable(false);

        jLabel9.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        jLabel9.setForeground(java.awt.Color.white);
        jLabel9.setText("Fecha y Hora de Salida");

        txtHorIn.setEditable(false);

        txtFecSal.setEditable(false);

        FecHorSa.setEditable(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(imagen, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtPeso, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtCant, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel8)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtFechIn, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtHorIn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txtCodigo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNom)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(74, 74, 74)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(txtFecSal, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(FecHorSa, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(214, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(imagen, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNom, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPeso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(FecHorSa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtFechIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtHorIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtFecSal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(20, 31, 37));

        jPanel4.setBackground(new java.awt.Color(20, 31, 37));
        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel10.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Ingresos");

        jScrollPane1.setToolTipText("");

        tableEstante.setBackground(new java.awt.Color(120, 147, 166));
        tableEstante.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        tableEstante.setForeground(new java.awt.Color(255, 255, 255));
        tableEstante.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id_Caja", "Nombre", "Tipo", "Id_Estante"
            }
        ));
        tableEstante.setGridColor(new java.awt.Color(120, 147, 166));
        tableEstante.setSelectionBackground(new java.awt.Color(120, 147, 166));
        tableEstante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableEstanteMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableEstante);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(20, 31, 37));
        jPanel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbA1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel5.add(lbA1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, 60, 100));

        lbA2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel5.add(lbA2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 60, 96));

        lbB1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel5.add(lbB1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 60, 100));

        lbC1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel5.add(lbC1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 170, 60, 100));

        lbB2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel5.add(lbB2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 60, 100));

        lbC2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel5.add(lbC2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 80, 60, 100));

        lbD1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel5.add(lbD1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 170, 60, 100));

        lbD2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel5.add(lbD2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, 60, 98));

        lbE1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel5.add(lbE1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 170, 60, 100));

        lbE2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel5.add(lbE2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 80, 60, 100));

        lbF1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel5.add(lbF1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 170, 60, 100));

        lbF2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel5.add(lbF2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 80, 60, 98));

        jLabel23.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Estantes");
        jPanel5.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 110, -1));

        cmbEst.setBackground(new java.awt.Color(120, 147, 166));
        cmbEst.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        cmbEst.setForeground(new java.awt.Color(255, 255, 255));
        cmbEst.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A01", "A02", "B01", "B02", "C01", "C02", "D01", "D02", "E01", "E02", "F01", "F02", " " }));
        jPanel5.add(cmbEst, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 95, -1));

        jLabel11.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        jLabel11.setForeground(java.awt.Color.white);
        jLabel11.setText("F");
        jPanel5.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 50, -1, -1));

        lbF.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        lbF.setForeground(java.awt.Color.white);
        lbF.setText("%");
        jPanel5.add(lbF, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 290, -1, -1));

        jLabel13.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        jLabel13.setForeground(java.awt.Color.white);
        jLabel13.setText("B");
        jPanel5.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, -1, -1));

        jLabel14.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        jLabel14.setForeground(java.awt.Color.white);
        jLabel14.setText("C");
        jPanel5.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, -1, -1));

        jLabel15.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        jLabel15.setForeground(java.awt.Color.white);
        jLabel15.setText("D");
        jPanel5.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 50, -1, -1));

        jLabel16.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        jLabel16.setForeground(java.awt.Color.white);
        jLabel16.setText("E");
        jPanel5.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 50, -1, -1));

        jLabel17.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        jLabel17.setForeground(java.awt.Color.white);
        jLabel17.setText("A");
        jPanel5.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, -1, -1));

        lbA.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        lbA.setForeground(java.awt.Color.white);
        lbA.setText("0%");
        jPanel5.add(lbA, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, -1, -1));

        lbB.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        lbB.setForeground(java.awt.Color.white);
        lbB.setText("%");
        jPanel5.add(lbB, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 290, -1, -1));

        lbC.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        lbC.setForeground(java.awt.Color.white);
        lbC.setText("%");
        jPanel5.add(lbC, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 290, -1, -1));

        lbD.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        lbD.setForeground(java.awt.Color.white);
        lbD.setText("%");
        jPanel5.add(lbD, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 290, -1, -1));

        lbE.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        lbE.setForeground(java.awt.Color.white);
        lbE.setText("%");
        jPanel5.add(lbE, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 290, -1, -1));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel10, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConectarActionPerformed
        if (btnConectar.getText().equals("Conectar")) {
            conexion.conectar(cmbPuer.getSelectedItem().toString().trim());
            conexion.iniciarIo();
            conexion.initListener();
            if (conexion.isConectado()) {
                lblEst.setText("Conectado");
                btnConectar.setText("Desconectar");
            } else {
                lblEst.setText("Desconectado");
            }
        } else {
            btnConectar.setText("Conectar");
            lblEst.setText("Desconectado");
            conexion.desconectar();
        }

    }//GEN-LAST:event_btnConectarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        conexion.desconectar();
    }//GEN-LAST:event_formWindowClosing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        lecQr.setVisible(true);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
        RegistrarIngreso();
        ingresarCaja();
        limpiar();
        rellenarDatos();
        llenarPorcentaje();
        cambiarGraficos();
        
    }//GEN-LAST:event_btnIngresarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        extraerCaja();
        RegistrarSalida();
        
        rellenarDatos();
        combEst();
        llenarPorcentaje();
        cambiarGraficos();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void cmbPuerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPuerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbPuerActionPerformed

    private void btnTestAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTestAActionPerformed
        conexion.escribir(101);

    }//GEN-LAST:event_btnTestAActionPerformed

    private void btnTestBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTestBActionPerformed
        conexion.escribir(102);
    }//GEN-LAST:event_btnTestBActionPerformed

    private void btnTestCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTestCActionPerformed
        conexion.escribir(103);
    }//GEN-LAST:event_btnTestCActionPerformed

    private void btnTestDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTestDActionPerformed
        conexion.escribir(104);
    }//GEN-LAST:event_btnTestDActionPerformed

    private void btnTestEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTestEActionPerformed
        conexion.escribir(105);
    }//GEN-LAST:event_btnTestEActionPerformed

    private void tableEstanteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableEstanteMouseClicked
        String dato = "";
        dato = (String) tableEstante.getValueAt(tableEstante.getSelectedRow(), 0);
        txtCodigo.setText(dato);
    }//GEN-LAST:event_tableEstanteMouseClicked

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
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new Principal().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField FecHorSa;
    private javax.swing.JButton btnConectar;
    private javax.swing.JButton btnIngresar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnTestA;
    private javax.swing.JButton btnTestB;
    private javax.swing.JButton btnTestC;
    private javax.swing.JButton btnTestD;
    private javax.swing.JButton btnTestE;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cmbEst;
    private javax.swing.JComboBox<String> cmbPuer;
    private javax.swing.JLabel fotoLbl;
    private javax.swing.JButton imagen;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbA;
    private javax.swing.JLabel lbA1;
    private javax.swing.JLabel lbA2;
    private javax.swing.JLabel lbB;
    private javax.swing.JLabel lbB1;
    private javax.swing.JLabel lbB2;
    private javax.swing.JLabel lbC;
    private javax.swing.JLabel lbC1;
    private javax.swing.JLabel lbC2;
    private javax.swing.JLabel lbD;
    private javax.swing.JLabel lbD1;
    private javax.swing.JLabel lbD2;
    private javax.swing.JLabel lbE;
    private javax.swing.JLabel lbE1;
    private javax.swing.JLabel lbE2;
    private javax.swing.JLabel lbF;
    private javax.swing.JLabel lbF1;
    private javax.swing.JLabel lbF2;
    private javax.swing.JLabel lbLogo;
    private javax.swing.JLabel lbTest;
    private javax.swing.JLabel lblEst;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JTable tableEstante;
    private javax.swing.JTextField txtCant;
    public static javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtFecSal;
    private javax.swing.JTextField txtFechIn;
    private javax.swing.JTextField txtHorIn;
    private javax.swing.JTextField txtNom;
    private javax.swing.JTextField txtPeso;
    private javax.swing.JTextField txtTipo;
    // End of variables declaration//GEN-END:variables
}
