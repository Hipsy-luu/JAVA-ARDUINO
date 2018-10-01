package camaraarduino;

import java.io.IOException;
import jssc.SerialPort;
import jssc.SerialPortException;

public class SimpleRead {

    private static final char[] COMMAND = {'*', 'R', 'D', 'Y', '*'};
    private int WIDTH=320;// 320; //640;
    private int HEIGHT=240;// 240; //480;
    int[][] rgb = new int[HEIGHT][WIDTH];
    int[][] imagen = new int[WIDTH][HEIGHT];
    private String ruta;
    private String nombreArchivo;
    private int baudios;
    SerialPort serialPort;//este valor lo recibimos de la libreria PanamaHitek_arduino
    public int numImagenesTomadas = 0;
    public String logEventos="CONEXION CREADA\n";

    //Constructor principal
    public SimpleRead(
            //Atributos para iniciar la instancia
            SerialPort serialPort,
            String ruta,
            String nombreArchivo,
            int baudios,
            int WIDTH,
            int HEIGHT
    ) {//Llenado de los atributos globales de la clase
        this.serialPort = serialPort;
        this.baudios=baudios;
        this.ruta=ruta;
        this.nombreArchivo=nombreArchivo;
        this.WIDTH=WIDTH;
        this.HEIGHT=HEIGHT;
    }

    //Esta Funcion regresa el byte leido en int
    private int leerSerialInt() throws IOException, SerialPortException {
        byte[] caracter = serialPort.readBytes(1);
        int temp = (char) caracter[0];
        if (temp == -1) {
            throw new IllegalStateException("Exit");
        }
        return temp;
    }

    //Busca la cadena *RDY* y devuelve un true cundo la encuentra
    //Se supone que despues de encontrarla lo que entre es la imagen
    private boolean buscarInicioImagen() throws IOException, SerialPortException {
        int index = 0;
        int contador = 0;
        char caracter = 'v';//caracter diferente a COMMAND
        logEventos = logEventos + "Buscando comando *RDY* por el puerto serial\n";
        while (index < COMMAND.length && contador < 100000) {
            caracter = (char) leerSerialInt();
            contador++;
            if (caracter == COMMAND[index]) {
                logEventos = logEventos + " " + caracter ;
                index++;
            }
        }
        //Si se encuentra la imagen retornamos true
        if (index < COMMAND.length) {
            logEventos = logEventos + "No se encuentra comando\n";
            
            return false;
        }
        logEventos = logEventos + " Comando encontrado...\n\tVerificacion de inicio de imagen correcto\n";
        return true;
    }

    public boolean crearFotoSerial() {
        try {
            serialPort.setParams(baudios,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            
            char caracter = 'v';
            logEventos=logEventos+"Recibiendo datos...\n";

            if ( buscarInicioImagen() ) {
                logEventos = logEventos + "Recibiendo Imagen\n";
                for (int y = 0; y < HEIGHT; y++) {
                    for (int x = 0; x < WIDTH; x++) {
                        int temp = leerSerialInt();
                        rgb[y][x] = ((temp & 0xFF) << 16) | ((temp & 0xFF) << 8) | (temp & 0xFF);
                    }
                }

                for (int y = 0; y < HEIGHT; y++) {
                    for (int x = 0; x < WIDTH; x++) {
                        imagen[x][y] = rgb[y][x];
                    }
                }

                BMP bmp = new BMP();
                bmp.saveBMP(ruta +"\\"+ nombreArchivo + (numImagenesTomadas++) + ".bmp", imagen);

                logEventos = logEventos + "Imagen " + (numImagenesTomadas) + " guardada\n";
            }else{
                logEventos = logEventos + "NO SE ENCONTRO IMAGEN\n";
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
