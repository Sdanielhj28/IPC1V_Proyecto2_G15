package vista;

import modelo.Investigador;
import modelo.SistemaDatos;
import javax.swing.table.DefaultTableModel;
import modelo.Muestra;
import modelo.Patron;


/**
 *
 * @author danie
 */
public class AdminFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AdminFrame.class.getName());

    public AdminFrame() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaInvestigadores = new javax.swing.JTable();
        btnNuevo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaMuestras = new javax.swing.JTable();
        btnCrearMuestra = new javax.swing.JButton();
        btnCargarMuestra = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnCrearPatron = new javax.swing.JButton();
        btnCargarPatron = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaPatrones = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tablaInvestigadores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Género", "Experimentos"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaInvestigadores);

        btnNuevo.setText("Nuevo");
        btnNuevo.setName(""); // NOI18N
        btnNuevo.addActionListener(this::btnNuevoActionPerformed);

        btnEditar.setText("Editar");
        btnEditar.addActionListener(this::btnEditarActionPerformed);

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 938, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnNuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnEditar)
                    .addComponent(btnEliminar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnNuevo.getAccessibleContext().setAccessibleName("");

        jTabbedPane1.addTab("Investigadores", jPanel1);

        tablaMuestras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Descripción", "Estado", "Acciones"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablaMuestras);

        btnCrearMuestra.setText("Crear");
        btnCrearMuestra.addActionListener(this::btnCrearMuestraActionPerformed);

        btnCargarMuestra.setText("Cargar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCrearMuestra)
                .addGap(18, 18, 18)
                .addComponent(btnCargarMuestra)
                .addContainerGap(774, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 950, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrearMuestra)
                    .addComponent(btnCargarMuestra))
                .addContainerGap(548, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addGap(0, 125, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jTabbedPane1.addTab("Muestras", jPanel2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 950, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 615, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Asignación de experimentos", jPanel3);

        btnCrearPatron.setText("Crear");
        btnCrearPatron.addActionListener(this::btnCrearPatronActionPerformed);

        btnCargarPatron.setText("Cargar");

        tablaPatrones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Tipo", "Acciones"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tablaPatrones);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCrearPatron)
                .addGap(18, 18, 18)
                .addComponent(btnCargarPatron)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 950, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrearPatron)
                    .addComponent(btnCargarPatron))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Patrones", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 950, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        CrearInvestigadorFrame ventana = new CrearInvestigadorFrame(this);
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        int fila = tablaInvestigadores.getSelectedRow();

if (fila == -1) {
    javax.swing.JOptionPane.showMessageDialog(this, "Seleccione un investigador para editar");
    return;
}

CrearInvestigadorFrame ventana = new CrearInvestigadorFrame(this, fila);
ventana.setVisible(true);
ventana.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int fila = tablaInvestigadores.getSelectedRow();
        
        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccione un investigador para eliminar");
            return;
        }
        
        int confirmacion = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "¿Está segura de eliminar este investigador?",
                "Confirmar eliminación",
                javax.swing.JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {
            SistemaDatos.investigadores.remove(fila);
            actualizarTablaInvestigadores();
            javax.swing.JOptionPane.showMessageDialog(this, "Investigador eliminado correctamente");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCrearMuestraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearMuestraActionPerformed
        CrearMuestraFrame ventana = new CrearMuestraFrame(this);
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnCrearMuestraActionPerformed

    private void btnCrearPatronActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearPatronActionPerformed
        CrearPatronFrame ventana = new CrearPatronFrame(this);
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnCrearPatronActionPerformed
    
    public void actualizarTablaInvestigadores() {
    DefaultTableModel modelo = (DefaultTableModel) tablaInvestigadores.getModel();
    modelo.setRowCount(0);

    for (Investigador inv : SistemaDatos.investigadores) {
        modelo.addRow(new Object[]{
            inv.getCodigo(),
            inv.getNombre(),
            inv.getGenero(),
            inv.getExperimentos()
        });
    }
}
    
    public void actualizarTablaMuestras() {
    DefaultTableModel modelo = (DefaultTableModel) tablaMuestras.getModel();
    modelo.setRowCount(0);

    for (Muestra m : SistemaDatos.muestras) {
        modelo.addRow(new Object[]{
            m.getCodigo(),
            m.getDescripcion(),
            m.getEstado(),
            "Ver"
        });
    }
}
    
    public void actualizarTablaPatrones() {
    DefaultTableModel modelo = (DefaultTableModel) tablaPatrones.getModel();
    modelo.setRowCount(0);

    for (Patron p : SistemaDatos.patrones) {
        modelo.addRow(new Object[]{
            p.getCodigo(),
            p.getNombre(),
            p.getTipo(),
            "Ver"
        });
    }
}
    
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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new AdminFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCargarMuestra;
    private javax.swing.JButton btnCargarPatron;
    private javax.swing.JButton btnCrearMuestra;
    private javax.swing.JButton btnCrearPatron;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tablaInvestigadores;
    private javax.swing.JTable tablaMuestras;
    private javax.swing.JTable tablaPatrones;
    // End of variables declaration//GEN-END:variables
}
