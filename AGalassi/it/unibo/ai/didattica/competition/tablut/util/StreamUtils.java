package it.unibo.ai.didattica.competition.tablut.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Questa classe offre dei metodi per inviare e ricevere stringhe attraverso delle socket
 * utilizzando l'encoding UTF-8.
 * Questi metodi mirano a sostituire writeUTF/readUTF della classe DataOutputStream/DataInputStream
 * che utilizzano un encoding UTF-8 modificato, proprio di java, che rende molto difficile 
 * l'interfacciamento con altri linguaggi di programmazione.
 * Fonte: https://docs.oracle.com/javase/7/docs/api/java/io/DataOutputStream.html#writeUTF(java.lang.String)
 *  
 * Per questo motivo, i metodi writeString/readString realizzano un approccio analogo a quello di
 * writeUTF/readUTF ma utilizzando un encoding UTF-8 standard.
 * 
 * In particolare, inviano inizialmente sulla socket un intero ( 4 byte ) che rappresenta
 * la lunghezza dei byte UTF-8 corrispondenti alla stringa, e poi successivamente invia i bytes.
 * 
 * In ricezione, si legge la lunghezza dell'array di byte, si legge l'array di bytes e si 
 * converte il risultato in stringa.
 */
public class StreamUtils {
	public static void writeString(DataOutputStream out, String s) throws IOException {
		// Converti la stringa in un array di byte codificati con UTF-8
		byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
		
		// Invio la lunghezza dell'array di byte come intero
		out.writeInt(bytes.length);
		
		// Invio l'array di byte
		out.write(bytes, 0, bytes.length);
	}
	
	public static String readString(DataInputStream in) throws IOException {
		// Leggo la lunghezza dei byte in ingresso
		int len = in.readInt();
		
		// Creo un array di bytes che conterra' i dati in ingresso
		byte[] bytes = new byte[len];
		
		// Leggo TUTTI i bytes
		in.readFully(bytes, 0, len);
		
		// Converto i bytes in stringa
		return new String(bytes, StandardCharsets.UTF_8);
	}
}
