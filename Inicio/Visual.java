/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inicio;

import Otros.Limpiar_txt;
import Otros.imgTabla;
import java.awt.Desktop;

//import java.awt.Graphics2D;
import java.awt.HeadlessException;
//import java.awt.Image;
import java.awt.image.BufferedImage;
//import java.io.BufferedReader;
//import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.FileReader;
//import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import java.io.PrintWriter;
import java.text.SimpleDateFormat;
//import java.util.StringTokenizer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import java.io.IOException;

/**
 *
 * @author Jose Luis
 */
public class Visual extends javax.swing.JFrame {

    /**
     * Creates new form Visual
     */
    
   Exportar obj;
    
    
    Limpiar_txt lt = new Limpiar_txt();
    
    private String ruta_txt = "mi.txt"; 
    
    Producto p;
    Proceso rp;
    
    int clic_tabla;
    Producto producto=new Producto();        
    public  Visual() {
        initComponents();
        rp = new Proceso();
      

        try{
            cargar_txt();
            listarRegistro();
            fecha.setText(fechaActual());
        }catch(ClassNotFoundException ex){
            mensaje("No existe el archivo txt");
        }
        
     
    }
    
  

 
    
    public static String fechaActual(){
        
        Date fecha=new Date();
        
        SimpleDateFormat formatoFecha= new SimpleDateFormat("dd/MM/YYYY");
        
        
        return formatoFecha.format(fecha);
        
        
        
    }
    
    
    
    @SuppressWarnings("null")
    public void cargar_txt() throws ClassNotFoundException{
        //File ruta = new File(ruta_txt);
        try{
            
            FileInputStream fis = new FileInputStream(ruta_txt);
            ObjectInputStream in = new ObjectInputStream(fis);
            if(in != null){
                
                rp =(Proceso)in.readObject();
                in.close();
            }
            
            
            
            /*
            FileReader fi = new FileReader(ruta);
            BufferedReader bu = new BufferedReader(fi);
            
            
            String linea = null;
            while((linea = bu.readLine())!=null){
                StringTokenizer st = new StringTokenizer(linea, ",");
                p = new Producto();
                p.setCodigo(Integer.parseInt(st.nextToken()));
                p.setNombre(st.nextToken());
                p.setPrecio((int) Double.parseDouble(st.nextToken()));
                p.setFechas(st.nextToken());
                p.setDescripcion(st.nextToken());
                
                
                        
                rp.agregarRegistro(p);
            }
            
            bu.close();
            */
        }catch(IOException | NumberFormatException ex){
            mensaje("Error al cargar archivo: "+ex.getMessage());
            System.out.println(ex.getMessage());
        }
            
    }
    
    @SuppressWarnings("null")
    public void grabar_txt(){
       // FileWriter fw;
       // PrintWriter pw;
       FileOutputStream fos;
       ObjectOutputStream out;
       
       
       
        try{
          //  fw = new FileWriter(ruta_txt);
          //  pw = new PrintWriter(fw);
          
          fos = new FileOutputStream(ruta_txt);
          out = new ObjectOutputStream(fos);
          if(out!=null){
           out.writeObject(rp);
           out.close();
              
          }
            /*
            for(int i = 0; i < rp.cantidadRegistro(); i++){
                p = rp.obtenerRegistro(i);
                pw.println(String.valueOf(p.getCodigo()+", "+p.getNombre()+", "+p.getCantidad()+","+p.getFechas()+","+p.getDescripcion()));
            }
             pw.close();
            */
        }catch(IOException ex){
            mensaje("Error al grabar archivo: "+ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }
    
    public void ingresarRegistro(File ruta){
        try{
            if(leerCodigo() == -666)mensaje("Ingresar codigo entero");
            else if(leerNombre() == null)mensaje("Ingresar Nombre");
            else if(leerCantidad() == -666) mensaje("Ingresar Cantidad");
            else if(leerFechas() == null)mensaje("Ingresar fecha");
            else if(leerDescripcion() == null)mensaje("Ingresar Descripcion");
            else{
                p = new Producto(leerCodigo(), leerNombre(), (int) leerCantidad(), leerFechas(), (String) leerDescripcion(),leerFoto(ruta));
                if(rp.buscaCodigo(p.getCodigo())!= -1)mensaje("Este codigo ya existe");
                else rp.agregarRegistro(p);
                
                grabar_txt();
                listarRegistro();
                lt.limpiar_texto(panel);
                fecha.setText(fechaActual());
            }
        }catch(Exception ex){
            mensaje(ex.getMessage());
        }
    }
    
    public void modificarRegistro(File ruta){
        try{
            if(leerCodigo() == -666)mensaje("Ingresar codigo entero");
            else if(leerNombre() == null)mensaje("Ingresar Nombre");
            else if(leerCantidad() == -666) mensaje("Ingresar Cantidad");
            else if(leerFechas() == null)mensaje("Ingresar Fecha");
            else if(leerDescripcion() == null)mensaje("Ingresar Descripcion");
            else{
                int codigo = rp.buscaCodigo(leerCodigo());
                if(txtRuta.getText().equalsIgnoreCase(""))
                p = new Producto(leerCodigo(), leerNombre(), (int) leerCantidad(), leerFechas(), (String) leerDescripcion(), leerFoto2(codigo));
                else  p = new Producto(leerCodigo(), leerNombre(), (int) leerCantidad(), leerFechas(), (String) leerDescripcion(),leerFoto(ruta));
                
                if(codigo == -1)rp.agregarRegistro(p);
                else rp.modificarRegistro(codigo, p);
                
                grabar_txt();
                listarRegistro();
                lt.limpiar_texto(panel);
            }
        }catch(Exception ex){
            mensaje(ex.getMessage());
        }
    }
    
    public void eliminarRegistro(){
        try{
            if(leerCodigo() == -666) mensaje("Ingrese codigo entero");
            
            else{
                int codigo = rp.buscaCodigo(leerCodigo());
                if(codigo == -1) mensaje("codigo no existe");
                
                else{
                    int s = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar este producto","¿Quieres Eliminar el Producto?",0);
                    if(s == 0){
                        rp.eliminarRegistro(codigo);
                        
                        grabar_txt();
                        listarRegistro();
                        lt.limpiar_texto(panel);
                    }
                }
                fechaActual();
                
            }
        }catch(HeadlessException ex){
            mensaje(ex.getMessage());
        }
    }
    
    public void listarRegistro(){
        DefaultTableModel dt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        dt.addColumn("Codigo");
        dt.addColumn("Nombre");
        dt.addColumn("Cantidad");
        dt.addColumn("Fecha");
        dt.addColumn("Descripcion");
        dt.addColumn("Foto");
        
        
        tabla.setDefaultRenderer(Object.class, new imgTabla());
        
        Object fila[] = new Object[dt.getColumnCount()];
        for(int i = 0; i < rp.cantidadRegistro(); i++){
            p = rp.obtenerRegistro(i);
            fila[0] = p.getCodigo();
            fila[1] = p.getNombre();
            fila[2] = p.getCantidad();
            fila[4] = p.getFechas();
            fila[3] = p.getDescripcion();
            try {
                byte[] bi =p .getFoto();
                @SuppressWarnings("UnusedAssignment")
                BufferedImage image =null;
                InputStream in = new ByteArrayInputStream(bi);
                image =ImageIO.read(in);
                ImageIcon img =new ImageIcon(image.getScaledInstance(60,60,0));
                fila[5] =new JLabel(img);
            }catch(IOException ex){
                fila[5] ="No imagen";
            }
            dt.addRow(fila);
        }
        tabla.setModel(dt);
        tabla.setRowHeight(60);
    }

    
 
    
    
    
    
    
    
    
    
    
    
    
    public int leerCodigo(){
        try{
            int codigo = Integer.parseInt(txtCodigo.getText().trim());
            return codigo;
        }catch(NumberFormatException ex){
            return -666;
        }
    }
    
    public String leerNombre(){
        try{
            String nombre = txtNombre.getText().trim().replace(" ", "_");
            return nombre;
        }catch(Exception ex){
            return null;
        }
    }
    
    public double leerCantidad(){
        try{
            double precio = Double.parseDouble(txtCantidad.getText().trim());
            return precio;
        }catch(NumberFormatException ex){
            return -666;
        }
    }
    
     public String leerFechas(){
        try{
            String Fechas = fecha.getText().trim();
            return Fechas;
        }catch(Exception ex){
            return null;
        }
     }
    public Object leerDescripcion(){
        try{
            Object descripcion = txtDescripcion.getText().trim();
            return descripcion;
        }catch(Exception ex){
            return null;
        }
    }
    
    public byte[] leerFoto(File ruta){
        try{
            byte[] icono = new byte[(int) ruta.length()];
            InputStream input = new FileInputStream(ruta);
            input.read(icono);
            return icono;
        }catch(IOException ex){
            return null;
        }
    }
    
    
    // generar pdf 
    
    
    
    

    
    
    
    
    // permite obtener la foto que ya emos cargado en memoria 
    public byte[] leerFoto2(int codigo){
            p = rp.obtenerRegistro(codigo);
            try{
               return p.getFoto();
            }catch(Exception ex){
               return null;
            }
        }

    public void mensaje(String texto){
        JOptionPane.showMessageDialog(null, texto);
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        btnguardar = new javax.swing.JButton();
        btnmodificar = new javax.swing.JButton();
        btneliminar = new javax.swing.JButton();
        txtDescripcion = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        txtRuta = new javax.swing.JTextField();
        lblFoto = new javax.swing.JLabel();
        btnlimpiar = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        fecha = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Registro de Material de Scrap Software 1.2");
        setLocationByPlatform(true);
        setResizable(false);

        panel.setBackground(new java.awt.Color(0, 102, 102));
        panel.setAutoscrolls(true);
        panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnguardar.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        btnguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/si.png"))); // NOI18N
        btnguardar.setText("Guardar");
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });
        panel.add(btnguardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 307, -1, -1));

        btnmodificar.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        btnmodificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Inicio/editar.png"))); // NOI18N
        btnmodificar.setText("Modificar");
        btnmodificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmodificarActionPerformed(evt);
            }
        });
        panel.add(btnmodificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(254, 307, -1, -1));

        btneliminar.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        btneliminar.setIcon(new javax.swing.ImageIcon("C:\\Users\\rodri\\Desktop\\icon\\borrar.png")); // NOI18N
        btneliminar.setText("Eliminar");
        btneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarActionPerformed(evt);
            }
        });
        panel.add(btneliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(418, 307, -1, -1));

        txtDescripcion.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        panel.add(txtDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 240, 273, -1));

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("* Descripcion:");
        panel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, -1));

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("* Cantidad:");
        panel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        txtCantidad.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        panel.add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 160, 92, -1));

        txtNombre.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        panel.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, 273, -1));

        txtCodigo.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });
        panel.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 80, 92, -1));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("* Codigo:");
        panel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("* Nombre:");
        panel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, -1, -1));

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Foto:");
        panel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 20, -1, -1));

        jButton4.setText("Buscar foto...");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        panel.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 20, -1, -1));

        txtRuta.setEditable(false);
        txtRuta.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        panel.add(txtRuta, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 260, 210, 25));

        lblFoto.setBackground(new java.awt.Color(204, 255, 204));
        lblFoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFoto.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panel.add(lblFoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 60, 210, 190));

        btnlimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/limpiar.png"))); // NOI18N
        btnlimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlimpiarActionPerformed(evt);
            }
        });
        panel.add(btnlimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 320, -1, -1));

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla);

        jTabbedPane1.addTab("Tabla de Registros", jScrollPane1);

        panel.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 980, 270));

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("* Fecha Registro: ");
        panel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, -1, -1));

        fecha.setEnabled(false);
        fecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fechaActionPerformed(evt);
            }
        });
        panel.add(fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 200, 270, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Inicio/registro64.png"))); // NOI18N
        jLabel8.setText("Registro de Material de Scrap");
        panel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 320, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pdf.png"))); // NOI18N
        jButton1.setText("PDF");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        panel.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 120, 90, -1));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Inicio/excel.png"))); // NOI18N
        jButton2.setText("Excel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        panel.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 170, 90, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Gdl software Development Engineering Team");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(303, 303, 303)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleName("Registro de Material de Scrap ");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
       
        File ruta = new File(txtRuta.getText());
        this.ingresarRegistro(ruta);
        
       
     
        
        
        
        
        
    }//GEN-LAST:event_btnguardarActionPerformed

    private void btnmodificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmodificarActionPerformed
        File ruta = new File(txtRuta.getText());
        this.modificarRegistro(ruta);
        
        
    }//GEN-LAST:event_btnmodificarActionPerformed

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed
        this.eliminarRegistro();
        
        
    }//GEN-LAST:event_btneliminarActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        JFileChooser jf = new JFileChooser();
        FileNameExtensionFilter fil = new FileNameExtensionFilter("JPG, PNG & GIF","jpg","png","gif");
        jf.setFileFilter(fil);
        jf.setCurrentDirectory(new File("Fotos"));
        int el = jf.showOpenDialog(this);
        if(el == JFileChooser.APPROVE_OPTION){
            txtRuta.setText(jf.getSelectedFile().getAbsolutePath());
            lblFoto.setIcon(new ImageIcon(txtRuta.getText()));
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMouseClicked
        
        clic_tabla = tabla.rowAtPoint(evt.getPoint());
        
        int codigo = (int)tabla.getValueAt(clic_tabla, 0);
        String nombre = ""+tabla.getValueAt(clic_tabla, 1);
        double precio = (double)tabla.getValueAt(clic_tabla, 2);
        Object descripcion = ""+tabla.getValueAt(clic_tabla, 3);
      

        txtCodigo.setText(String.valueOf(codigo));
        txtNombre.setText(nombre);
        txtCantidad.setText(String.valueOf(precio));
        fecha.setText(String.valueOf(fechaActual()));
        txtDescripcion.setText(String.valueOf(descripcion));
        
        try{
            JLabel lbl = (JLabel)tabla.getValueAt(clic_tabla, 4);
            lblFoto.setIcon(lbl.getIcon());
        }catch(Exception ex){
        }
    }//GEN-LAST:event_tablaMouseClicked

    private void btnlimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlimpiarActionPerformed
        Limpiar_txt lp = new Limpiar_txt();
        lp.limpiar_texto(panel);
        fecha.setText(fechaActual());
    }//GEN-LAST:event_btnlimpiarActionPerformed

    private void fechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fechaActionPerformed
      
        fechaActual();
    }//GEN-LAST:event_fechaActionPerformed

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
     
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
 File ruta = new File(txtRuta.getText());
        this.ingresarRegistro(ruta);
        
        try{
    obj =new Exportar();
    obj.esportarexcel(tabla);
}catch(IOException ex){
    
}
    }//GEN-LAST:event_jButton2ActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Visual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Visual().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btneliminar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnlimpiar;
    private javax.swing.JButton btnmodificar;
    private javax.swing.JTextField fecha;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JPanel panel;
    public static javax.swing.JTable tabla;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtRuta;
    // End of variables declaration//GEN-END:variables

  
}
