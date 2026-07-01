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
        
        actualizarTablaInvestigadores();
        actualizarTablaMuestras();
        actualizarTablaPatrones();
        cargarCombosAsignacion();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                util.PersistenciaUtil.guardarDatos();
                System.exit(0);
            }
        });
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
        btnCargarInvestigador = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaMuestras = new javax.swing.JTable();
        btnCrearMuestra = new javax.swing.JButton();
        btnCargarMuestra = new javax.swing.JButton();
        btnEditarMuestra = new javax.swing.JButton();
        btnEliminarMuestra = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        txtInvestigador = new javax.swing.JLabel();
        comboInvestigador = new javax.swing.JComboBox<>();
        txtMuestra = new javax.swing.JLabel();
        comboMuestra = new javax.swing.JComboBox<>();
        btnAsignar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btnCrearPatron = new javax.swing.JButton();
        btnCargarPatron = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaPatrones = new javax.swing.JTable();
        btnEditarPatron = new javax.swing.JButton();
        btnEliminarPatron = new javax.swing.JButton();

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

        btnCargarInvestigador.setText("Cargar");
        btnCargarInvestigador.addActionListener(this::btnCargarInvestigadorActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevo)
                .addGap(18, 18, 18)
                .addComponent(btnCargarInvestigador)
                .addGap(18, 18, 18)
                .addComponent(btnEditar)
                .addGap(18, 18, 18)
                .addComponent(btnEliminar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 950, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnEditar)
                    .addComponent(btnEliminar)
                    .addComponent(btnCargarInvestigador))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        tablaMuestras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMuestrasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaMuestras);

        btnCrearMuestra.setText("Crear");
        btnCrearMuestra.addActionListener(this::btnCrearMuestraActionPerformed);

        btnCargarMuestra.setText("Cargar");
        btnCargarMuestra.addActionListener(this::btnCargarMuestraActionPerformed);

        btnEditarMuestra.setText("Editar");
        btnEditarMuestra.addActionListener(this::btnEditarMuestraActionPerformed);

        btnEliminarMuestra.setText("Eliminar");
        btnEliminarMuestra.addActionListener(this::btnEliminarMuestraActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCrearMuestra)
                .addGap(18, 18, 18)
                .addComponent(btnCargarMuestra)
                .addGap(18, 18, 18)
                .addComponent(btnEditarMuestra)
                .addGap(18, 18, 18)
                .addComponent(btnEliminarMuestra)
                .addContainerGap(585, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 950, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrearMuestra)
                    .addComponent(btnCargarMuestra)
                    .addComponent(btnEditarMuestra)
                    .addComponent(btnEliminarMuestra))
                .addContainerGap(548, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addGap(0, 115, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jTabbedPane1.addTab("Muestras", jPanel2);

        txtInvestigador.setText("Investigador");

        comboInvestigador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtMuestra.setText("Muestra");

        comboMuestra.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnAsignar.setText("Asignar");
        btnAsignar.addActionListener(this::btnAsignarActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(267, 267, 267)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtInvestigador)
                            .addComponent(txtMuestra))
                        .addGap(231, 231, 231)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboInvestigador, 0, 115, Short.MAX_VALUE)
                            .addComponent(comboMuestra, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(403, 403, 403)
                        .addComponent(btnAsignar)))
                .addContainerGap(271, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(comboInvestigador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtInvestigador))
                        .addGap(74, 74, 74)
                        .addComponent(txtMuestra))
                    .addComponent(comboMuestra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addComponent(btnAsignar)
                .addContainerGap(369, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Asignación de experimentos", jPanel3);

        jPanel4.setPreferredSize(new java.awt.Dimension(750, 615));

        btnCrearPatron.setText("Crear");
        btnCrearPatron.addActionListener(this::btnCrearPatronActionPerformed);

        btnCargarPatron.setText("Cargar");
        btnCargarPatron.addActionListener(this::btnCargarPatronActionPerformed);

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
        tablaPatrones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaPatronesMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablaPatrones);

        btnEditarPatron.setText("Editar");
        btnEditarPatron.addActionListener(this::btnEditarPatronActionPerformed);

        btnEliminarPatron.setText("Eliminar");
        btnEliminarPatron.addActionListener(this::btnEliminarPatronActionPerformed);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCrearPatron)
                .addGap(18, 18, 18)
                .addComponent(btnCargarPatron)
                .addGap(18, 18, 18)
                .addComponent(btnEditarPatron)
                .addGap(18, 18, 18)
                .addComponent(btnEliminarPatron)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 950, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrearPatron)
                    .addComponent(btnCargarPatron)
                    .addComponent(btnEditarPatron)
                    .addComponent(btnEliminarPatron))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void btnCargarPatronActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarPatronActionPerformed
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        int resultado = fileChooser.showOpenDialog(this);

        if (resultado == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File archivo = fileChooser.getSelectedFile();

            try {
                java.util.Scanner scanner = new java.util.Scanner(archivo);

                while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();

                String[] partes = linea.split(",", 3);

                if (partes.length < 3) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Formato CSV incorrecto");
                    scanner.close();
                    return;
                }

                String codigo = partes[0].trim();
                String nombre = partes[1].trim();
                String textoMatriz = partes[2].trim();

                int[][] matriz = util.CSVUtil.convertirTextoAMatriz(textoMatriz);

                Patron patron = new Patron(codigo, nombre, matriz);
                SistemaDatos.patrones.add(patron);
            }
                actualizarTablaPatrones();

                javax.swing.JOptionPane.showMessageDialog(this, "Patrón cargado correctamente");
            

            scanner.close();

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
      }
    }//GEN-LAST:event_btnCargarPatronActionPerformed

    private void btnCargarMuestraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarMuestraActionPerformed
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        int resultado = fileChooser.showOpenDialog(this);

        if (resultado == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File archivo = fileChooser.getSelectedFile();

            try {
                java.util.Scanner scanner = new java.util.Scanner(archivo);

                while (scanner.hasNextLine()) {
                    String linea = scanner.nextLine();

                    String[] partes = linea.split(",", 4);

                    if (partes.length < 4) {
                        javax.swing.JOptionPane.showMessageDialog(this, "Formato CSV incorrecto");
                        scanner.close();
                        return;
                    }

                    String codigo = partes[0].trim();
                    String descripcion = partes[1].trim();
                    String estado = partes[2].trim();
                    String textoMatriz = partes[3].trim();

                    int[][] matriz = util.CSVUtil.convertirTextoAMatriz(textoMatriz);

                    Muestra muestra = new Muestra(codigo, descripcion, estado, matriz);
                    SistemaDatos.muestras.add(muestra);

                    actualizarTablaMuestras();
                    cargarCombosAsignacion();
                }
                
                javax.swing.JOptionPane.showMessageDialog(this, "Muestra cargada correctamente");

                scanner.close();

            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
        
    }//GEN-LAST:event_btnCargarMuestraActionPerformed

    private void btnCargarInvestigadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarInvestigadorActionPerformed
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        int seleccion = fileChooser.showOpenDialog(this);
        
        if (seleccion == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File archivo = fileChooser.getSelectedFile();
            
            try {
                java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(archivo));
                String linea;
                
                boolean primeraLinea = true;

                while ((linea = br.readLine()) != null) {

                    if (primeraLinea) {
                        primeraLinea = false;
                        continue;
                    }

                    String[] datos = linea.split(",");

                    String codigo = datos[0].trim();
                    String nombre = datos[1].trim();
                    String genero = datos[2].trim();
                    int experimentos = Integer.parseInt(datos[3].trim());
                    String password = datos[4].trim();
                    
                    modelo.Investigador investigador = new modelo.Investigador(codigo, nombre, genero, password);
                    investigador.setExperimentos(experimentos);
                    
                    modelo.SistemaDatos.investigadores.add(investigador);
                }
                
                br.close();
                actualizarTablaInvestigadores();
                cargarCombosAsignacion();
                
                javax.swing.JOptionPane.showMessageDialog(this, "Investigadores cargados correctamente");
                
            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Error al cargar archivo CSV");
            }
        }
    }//GEN-LAST:event_btnCargarInvestigadorActionPerformed

    private void btnAsignarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsignarActionPerformed
        if (comboInvestigador.getSelectedItem() == null || comboMuestra.getSelectedItem() == null) {
        javax.swing.JOptionPane.showMessageDialog(this, "Debe seleccionar investigador y muestra");
        return;
        }

        String codigoInvestigador = comboInvestigador.getSelectedItem().toString();
        String codigoMuestra = comboMuestra.getSelectedItem().toString();

        for (Muestra m : SistemaDatos.muestras) {
            if (m.getCodigo().equals(codigoMuestra)) {

                if (!m.getCodigoInvestigadorAsignado().isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, "Esta muestra ya fue asignada");
                return;
            }

            m.setCodigoInvestigadorAsignado(codigoInvestigador);
            m.setEstado("En proceso");
            
            for (Investigador inv : SistemaDatos.investigadores) {
            if (inv.getCodigo().equals(codigoInvestigador)) {
            inv.setExperimentos(inv.getExperimentos() + 1);
            break;
        }
    }

            actualizarTablaMuestras();
            actualizarTablaInvestigadores();
            cargarCombosAsignacion();

            javax.swing.JOptionPane.showMessageDialog(this, "Muestra asignada correctamente");
            return;
            }
        }
    }//GEN-LAST:event_btnAsignarActionPerformed

    private void tablaMuestrasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMuestrasMouseClicked
        int fila = tablaMuestras.getSelectedRow();
        int columna = tablaMuestras.getSelectedColumn();

        if (fila == -1) {
            return;
        }

        if (columna == 3) {
            Muestra muestra = SistemaDatos.muestras.get(fila);
            generarHTMLMuestra(muestra);
        }
    }//GEN-LAST:event_tablaMuestrasMouseClicked

    private void tablaPatronesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaPatronesMouseClicked
        int fila = tablaPatrones.getSelectedRow();
        int columna = tablaPatrones.getSelectedColumn();

        if (fila == -1) {
            return;
        }

        if (columna == 3) {
            Patron patron = SistemaDatos.patrones.get(fila);
            generarHTMLPatron(patron);
        }
    }//GEN-LAST:event_tablaPatronesMouseClicked

    private void btnEditarMuestraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarMuestraActionPerformed
        int fila = tablaMuestras.getSelectedRow();

        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccione una muestra para editar");
            return;
        }

        CrearMuestraFrame ventana = new CrearMuestraFrame(this, fila);
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnEditarMuestraActionPerformed

    private void btnEliminarMuestraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarMuestraActionPerformed
        int fila = tablaMuestras.getSelectedRow();

        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccione una muestra para eliminar");
            return;
        }

        int confirmacion = javax.swing.JOptionPane.showConfirmDialog(
            this,
            "¿Está segura de eliminar esta muestra?",
            "Confirmar eliminación",
            javax.swing.JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {
            SistemaDatos.muestras.remove(fila);
            actualizarTablaMuestras();
            cargarCombosAsignacion();
            javax.swing.JOptionPane.showMessageDialog(this, "Muestra eliminada correctamente");
        }
    }//GEN-LAST:event_btnEliminarMuestraActionPerformed

    private void btnEliminarPatronActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarPatronActionPerformed
        int fila = tablaPatrones.getSelectedRow();

        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccione un patrón para eliminar");
            return;
        }

        int confirmacion = javax.swing.JOptionPane.showConfirmDialog(
            this,
            "¿Está segura de eliminar este patrón?",
            "Confirmar eliminación",
            javax.swing.JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {
            SistemaDatos.patrones.remove(fila);
            actualizarTablaPatrones();
            javax.swing.JOptionPane.showMessageDialog(this, "Patrón eliminado correctamente");
        }
    }//GEN-LAST:event_btnEliminarPatronActionPerformed

    private void btnEditarPatronActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarPatronActionPerformed
        int fila = tablaPatrones.getSelectedRow();

        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccione un patrón para editar");
            return;
        }

        CrearPatronFrame ventana = new CrearPatronFrame(this, fila);
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnEditarPatronActionPerformed
    
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
   
    public void cargarCombosAsignacion() {
    comboInvestigador.removeAllItems();
    comboMuestra.removeAllItems();

    for (Investigador inv : SistemaDatos.investigadores) {
        comboInvestigador.addItem(inv.getCodigo());
    }

    for (Muestra m : SistemaDatos.muestras) {
            comboMuestra.addItem(m.getCodigo());
    }
}
    
    public void actualizarTablaPatrones() {
    DefaultTableModel modelo = (DefaultTableModel) tablaPatrones.getModel();
    modelo.setRowCount(0);

    for (Patron p : SistemaDatos.patrones) {
        modelo.addRow(new Object[]{
            p.getCodigo(),
            p.getNombre(),
            "",
            "Ver"
        });
    }
}
    
    public void generarHTMLMuestra(Muestra muestra) {
    try {
        String nombreArchivo = "Muestra_" + muestra.getCodigo() + ".html";
        java.io.PrintWriter writer = new java.io.PrintWriter(nombreArchivo);

        writer.println("<html><body>");
        writer.println("<h1>Muestra " + muestra.getCodigo() + "</h1>");
        writer.println("<table border='1'>");

        int[][] matriz = muestra.getMatriz();

        for (int i = 0; i < matriz.length; i++) {
            writer.println("<tr>");
            for (int j = 0; j < matriz[i].length; j++) {
                writer.println("<td>" + matriz[i][j] + "</td>");
            }
            writer.println("</tr>");
        }

        writer.println("</table>");
        writer.println("</body></html>");
        writer.close();

        javax.swing.JOptionPane.showMessageDialog(this, "HTML generado: " + nombreArchivo);

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Error al generar HTML");
    }
}
    
    public void generarHTMLPatron(Patron patron) {
    try {
        String nombreArchivo = "Patron_" + patron.getCodigo() + ".html";
        java.io.PrintWriter writer = new java.io.PrintWriter(nombreArchivo);

        writer.println("<html><body>");
        writer.println("<h1>Patrón " + patron.getCodigo() + "</h1>");
        writer.println("<h2>" + patron.getNombre() + "</h2>");
        writer.println("<table border='1'>");

        int[][] matriz = patron.getMatriz();

        for (int i = 0; i < matriz.length; i++) {
            writer.println("<tr>");
            for (int j = 0; j < matriz[i].length; j++) {
                writer.println("<td>" + matriz[i][j] + "</td>");
            }
            writer.println("</tr>");
        }

        writer.println("</table>");
        writer.println("</body></html>");
        writer.close();

        javax.swing.JOptionPane.showMessageDialog(this, "HTML generado: " + nombreArchivo);

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Error al generar HTML");
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
    private javax.swing.JButton btnAsignar;
    private javax.swing.JButton btnCargarInvestigador;
    private javax.swing.JButton btnCargarMuestra;
    private javax.swing.JButton btnCargarPatron;
    private javax.swing.JButton btnCrearMuestra;
    private javax.swing.JButton btnCrearPatron;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEditarMuestra;
    private javax.swing.JButton btnEditarPatron;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminarMuestra;
    private javax.swing.JButton btnEliminarPatron;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox<String> comboInvestigador;
    private javax.swing.JComboBox<String> comboMuestra;
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
    private javax.swing.JLabel txtInvestigador;
    private javax.swing.JLabel txtMuestra;
    // End of variables declaration//GEN-END:variables
}
