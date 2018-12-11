/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tec1.controlador;
import java.io.IOException;
import java.io.OutputStream;
import javax.comm.*;
public class SerialTx {
    private CommPortIdentifier idPuerto;
    private SerialPort puertoSerie;
    private OutputStream flujoSalida;
    /* metodo para envio de texto por puerto serie.*/
    public void txMensaje(String puerto, String texto)
            throws NoSuchPortException, PortInUseException, IOException,
            UnsupportedCommOperationException {
        idPuerto = CommPortIdentifier.getPortIdentifier(puerto);
        System.out.println("Puerto identifcado:" + puerto);
        if (idPuerto.isCurrentlyOwned()) {
            System.out.println("Puerto en uso...");
        } else {
            puertoSerie = (SerialPort) idPuerto.open("TxSerial", 1000);
            System.out.println("Puerto abierto");
            flujoSalida = puertoSerie.getOutputStream();
            System.out.println("Flujo de salida creado");
            puertoSerie.setSerialPortParams(9600, SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            System.out.println("Puerto configurado");
            flujoSalida.write(texto.getBytes());
            System.out.println("Mensaje enviado");
            flujoSalida.close();
            puertoSerie.close();
            System.out.println("Puerto liberado");
        }
    }
}
