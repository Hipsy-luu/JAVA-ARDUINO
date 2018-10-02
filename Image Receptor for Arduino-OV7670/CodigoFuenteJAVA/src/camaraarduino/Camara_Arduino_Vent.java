package camaraarduino;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.comm.CommPortIdentifier;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 *
 * @author Luismiguel Ortiz Alv
 */
public class Camara_Arduino_Vent extends javax.swing.JFrame {

    PanamaHitek_Arduino ino = new PanamaHitek_Arduino();//Se crea una instancia
    SimpleRead arduinoOV7670;
    private static CommPortIdentifier portId;
    private String imagenRutaCompleta;
    private int[] resu;

    //Eventos que nos dicen el estado del programa y la conexion
    void clickTomarFotos() {
        //tiene que recibirse *RDY* por el serial para guardar la foto la foto
        while (arduinoOV7670.crearFotoSerial()) {
            //con esta funcion actualizamos lo que recibimos 
            //System.out.println( ino.printMessage() );
            //Creamos una conexion con la camara y tomamos la foto
            logEventos.setText(
                    logEventos.getText() + arduinoOV7670.logEventos + "\n"
            );
            //Actualizando la vista de la interfaz
            LnFotoAct.setText(" " + arduinoOV7670.numImagenesTomadas);
            imagenRutaCompleta = ruta.getText() + "\\" + nArchivo.getText();
            //fografia.setIcon(new javax.swing.ImageIcon(imagenRutaCompleta+arduinoOV7670.numImagenesTomadas+".bmp"));
            
            logEventos.setCaretPosition(logEventos.getText().length());
            System.out.println(imagenRutaCompleta + arduinoOV7670.numImagenesTomadas + ".bmp");
            try {
                BufferedImage img;
                File imagenSeleccionada= new File (imagenRutaCompleta + (arduinoOV7670.numImagenesTomadas-1) + ".bmp");
                //Asignamos a la variable bmp la imagen leida
                img = ImageIO.read(imagenSeleccionada);
                fografia.setIcon(new ImageIcon(img) );
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    ;
        
        SerialPortEventListener clickConectar = new SerialPortEventListener() {
        @Override
        public void serialEvent(SerialPortEvent spe) {

            try {

                if (ino.isMessageAvailable()) { //Checamos si recibimos algo por el puerto Serial
                    //System.out.println( ino.printMessage() );
                    if (!btnTomarFotos.isEnabled()) {
                        resu = resolucionSeleccionada();
                        arduinoOV7670 = new SimpleRead(
                                ino.serialPort,
                                ruta.getText(),
                                nArchivo.getText(),
                                Integer.parseInt(baudios.getText()),
                                resu[0],
                                resu[1]
                        );
                        clickTomarFotos();
                    }
                }
            } catch (ArduinoException ex) {
                java.util.logging.Logger.getLogger(Camara_Arduino_Vent.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SerialPortException ex) {
                java.util.logging.Logger.getLogger(Camara_Arduino_Vent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    /////////////////////FUNCIONES DE LA INTERFAZ/////////////////////////////////////
    //Devolvemos en string el COM seleccionado
    String comSeleccionado() {
        switch (sCOM.getSelectedIndex()) {
            case 0:
                return "COM1";
            case 1:
                return "COM2";
            case 2:
                return "COM3";
            case 3:
                return "COM4";
            case 4:
                return "COM5";
            case 5:
                return "COM6";
            case 6:
                return "COM7";
            case 7:
                return "COM8";
            case 8:
                return "COM9";
            case 9:
                return "COM10";
        }
        return "COM1";
    }
    //Devolvemos en un arreglo la resolucion seleccionada

    int[] resolucionSeleccionada() {
        int[] redu = {320, 240};
        if (resolucion1.getSelectedObjects().length == 1) {
            return redu;
        } else {
            redu[0] = 640;//ancho de la foto
            redu[1] = 480;//altura de la foto
            return redu;
        }
    }

    /////////////////////FUNCIONES DE LA INTERFAZ/////////////////////////////////////
    public Camara_Arduino_Vent() {
        initComponents();
        conectar.setBackground(Color.LIGHT_GRAY);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel2 = new javax.swing.JLabel();
        LnFotoAct = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        nArchivo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        sCOM = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        btnTomarFotos = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        baudios = new javax.swing.JTextField();
        ruta = new javax.swing.JTextField();
        parar = new javax.swing.JButton();
        resolucion1 = new javax.swing.JCheckBox();
        resolucion2 = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        conectar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        logEventos = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        fografia = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText("Fotografia:");

        LnFotoAct.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        LnFotoAct.setText("0");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Escriba la ruta de la carpeta donde guardar las imagenes");

        nArchivo.setText("foto");
        nArchivo.setToolTipText("Nombre del archivo");
        nArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nArchivoActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("\\");

            jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
            jLabel4.setText("Nombre del archivo:");

            jLabel5.setText("+#imagen.bmp");

            sCOM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "COM10" }));
            sCOM.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    sCOMActionPerformed(evt);
                }
            });

            jLabel6.setText("Seleccionar Puerto Serial:");

            btnTomarFotos.setText("Tomar Fotos");
            btnTomarFotos.setEnabled(false);
            btnTomarFotos.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnTomarFotosActionPerformed(evt);
                }
            });

            jLabel7.setText("Seleccionar baudios: ");

            baudios.setText("460800");
            baudios.setToolTipText("Escrube el numero de blaudios:");

            ruta.setText("C:\\Users\\Luismiguel Ortiz Alv\\Desktop\\CANSAT NACIONAL\\Arduino DUE(camara ov7670)  e interfas en java pc bmp\\Proyecto tomar fotografias\\Programa JAVA\\fotos");
            ruta.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    rutaActionPerformed(evt);
                }
            });

            parar.setText("Parar");
            parar.setEnabled(false);
            parar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    pararActionPerformed(evt);
                }
            });

            resolucion1.setSelected(true);
            resolucion1.setText("320 x 240");
            resolucion1.setToolTipText("");
            resolucion1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    resolucion1ActionPerformed(evt);
                }
            });

            resolucion2.setText("640 x 480");
            resolucion2.setEnabled(false);
            resolucion2.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    resolucion2ActionPerformed(evt);
                }
            });

            jLabel8.setText("Resolucion de salida:");

            conectar.setText("Conectar");
            conectar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    conectarActionPerformed(evt);
                }
            });

            logEventos.setColumns(20);
            logEventos.setRows(5);
            jScrollPane1.setViewportView(logEventos);

            jLabel9.setText("Registro de eventos:");

            fografia.setIcon(new javax.swing.ImageIcon("C:\\Users\\Luismiguel Ortiz Alv\\Desktop\\CANSAT NACIONAL\\Arduino DUE(camara ov7670)  e interfas en java pc bmp\\Proyecto tomar fotografias\\Programa JAVA\\fotos\\foto1.bmp")); // NOI18N

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(19, 19, 19)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel7))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(sCOM, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(baudios, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(resolucion2)
                                        .addComponent(jLabel8)
                                        .addComponent(resolucion1))
                                    .addGap(44, 44, 44))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(conectar, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnTomarFotos, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(parar, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(28, 28, 28)))
                            .addGap(86, 86, 86)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(LnFotoAct)
                                    .addGap(27, 27, 27))))
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(ruta, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(301, 301, 301))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel3)
                                            .addGap(18, 18, 18))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(jLabel1)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(nArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel5))
                                        .addComponent(fografia, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9))
                    .addContainerGap())
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(21, 21, 21)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel4))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ruta, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addComponent(nArchivo))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(18, 18, Short.MAX_VALUE)
                            .addComponent(jLabel8)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(sCOM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6)
                                .addComponent(resolucion1))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel7)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(baudios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(resolucion2)))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnTomarFotos, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(parar, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(conectar, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(212, 212, 212))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addComponent(fografia, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(LnFotoAct)
                            .addContainerGap())))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void nArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nArchivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nArchivoActionPerformed

    private void sCOMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sCOMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sCOMActionPerformed

    private void btnTomarFotosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTomarFotosActionPerformed
        btnTomarFotos.setEnabled(false);
    }//GEN-LAST:event_btnTomarFotosActionPerformed

    private void pararActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pararActionPerformed
        try {
            ino.killArduinoConnection();
            parar.setEnabled(false);
            conectar.setEnabled(true);
            btnTomarFotos.setEnabled(false);
            conectar.setBackground(Color.LIGHT_GRAY);

            //habilitando interfaz 
            baudios.setEnabled(true);
            sCOM.setEnabled(true);
            resolucion1.setEnabled(true);
            resolucion1.setSelected(true);
            resolucion2.setEnabled(false);
            resolucion2.setSelected(false);
            ruta.setEnabled(true);
            nArchivo.setEnabled(true);
        } catch (ArduinoException ex) {
            Logger.getLogger(Camara_Arduino_Vent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_pararActionPerformed

    private void conectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conectarActionPerformed
        try {
            resu = resolucionSeleccionada();
            logEventos.setText(
                    comSeleccionado() + " Seleccionado\n"
                    + "Baudios: " + baudios.getText() + "\n"
                    + "Resolucion: " + resu[0] + "x" + resu[1] + "\n"
                    + "Ruta de guardado: " + ruta.getText() + "\\\nNombre del archivo: " + nArchivo.getText() + ".bmp\n"
                    + "Iniciando conexion..."
            );
            //Inicia la conexion con el Serial Seleccionado con la Clase de PanamaHitec
            ino.arduinoRX(comSeleccionado(), Integer.parseInt(baudios.getText()), clickConectar);
            logEventos.setText(
                    logEventos.getText() + "CONEXION ESTABLECIDA\n"
            );
            conectar.setBackground(Color.green);
            btnTomarFotos.setEnabled(true);
            parar.setEnabled(true);
            conectar.setEnabled(false);
            //desabilitando interfaz 
            baudios.setEnabled(false);
            sCOM.setEnabled(false);
            resolucion1.setEnabled(false);
            resolucion2.setEnabled(false);
            ruta.setEnabled(false);
            nArchivo.setEnabled(false);

        } catch (ArduinoException ex) {
            conectar.setBackground(Color.red);
            btnTomarFotos.setEnabled(false);
            parar.setEnabled(false);
            conectar.setEnabled(true);
            logEventos.setText(
                    logEventos.getText() + "Error ArduinoException\n"
            );
            java.util.logging.Logger.getLogger(Camara_Arduino_Vent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SerialPortException ex) {
            conectar.setBackground(Color.red);
            btnTomarFotos.setEnabled(false);
            parar.setEnabled(false);
            conectar.setEnabled(true);
            logEventos.setText(
                    logEventos.getText() + "SerialPortException\n"
            );
            java.util.logging.Logger.getLogger(Camara_Arduino_Vent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_conectarActionPerformed

    private void resolucion2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resolucion2ActionPerformed
        if (resolucion1.getSelectedObjects().length == 1) {
            resolucion1.setSelected(false);
        }
    }//GEN-LAST:event_resolucion2ActionPerformed

    private void resolucion1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resolucion1ActionPerformed
        if (resolucion2.getSelectedObjects().length == 1) {
            resolucion2.setSelected(false);
        }
    }//GEN-LAST:event_resolucion1ActionPerformed

    private void rutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rutaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rutaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Camara_Arduino_Vent().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LnFotoAct;
    private javax.swing.JTextField baudios;
    private javax.swing.JButton btnTomarFotos;
    private javax.swing.JButton conectar;
    private javax.swing.JLabel fografia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTextArea logEventos;
    private javax.swing.JTextField nArchivo;
    private javax.swing.JButton parar;
    private javax.swing.JCheckBox resolucion1;
    private javax.swing.JCheckBox resolucion2;
    private javax.swing.JTextField ruta;
    private javax.swing.JComboBox<String> sCOM;
    // End of variables declaration//GEN-END:variables
}
