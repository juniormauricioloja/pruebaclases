/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tec1.controlador;

import com.tec1.vista.RxSerial;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;
public class SerialRx extends Thread {
    private CommPortIdentifier idPuerto;
    private SerialPort puertoSerie;
    private InputStream flujoEntrada;
    private byte[] buffer = new byte[1024];
    private int len;
    public void rxMensaje(String puerto)
            throws NoSuchPortException, PortInUseException, IOException,
            UnsupportedCommOperationException {
        idPuerto = CommPortIdentifier.getPortIdentifier(puerto);
        System.out.println("Puerto identifcado:" + puerto);
        if (idPuerto.isCurrentlyOwned()) {
            System.out.println("Puerto en uso...");
        } else {
            puertoSerie = (SerialPort) idPuerto.open("RxSerial", 1000);
            System.out.println("Puerto abierto");
            flujoEntrada = puertoSerie.getInputStream();
            System.out.println("Flujo de entyrada creado");
            puertoSerie.setSerialPortParams(9600, SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            System.out.println("Puerto configurado");
            this.start();
        }
    }
    @Override
    public void run() {
        while (true) {
            try {
                if ((len = flujoEntrada.read(buffer)) > -1) {
                    RxSerial.txtmensaje.setText(new String(buffer, 0, len));
                }
            } catch (IOException ex) {
                Logger.getLogger(SerialRx.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void cerrar() throws IOException {
        flujoEntrada.close();
        puertoSerie.close();
        System.out.println("Puerto liberado");
    }
}
