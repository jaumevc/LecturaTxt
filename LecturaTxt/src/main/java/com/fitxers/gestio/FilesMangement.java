package com.fitxers.gestio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FilesMangement {
	
	private static final String RUTH_FILE = "C:\\Users\\jvalls\\Desktop\\Tasques_Ramon\\29_50110_sergi\\FILES\\MDIAE2024.txt";
	private static final String RUTH_RESULTFILE = "C:\\Users\\jvalls\\Desktop\\Tasques_Ramon\\29_50110_sergi\\FILES\\RESULTFILE\\resultat.txt";
	
	public void getFileFromFolder(){
//		Map<String, Integer> map = new HashMap();
		//per obtenir el mapa ordenat per les claus de major a menor 
		Map<String, Integer> map = new TreeMap<>();
		List<String> linies = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(RUTH_FILE))) {
            String linia;
            while ((linia = br.readLine()) != null) {
                // Comprova si la línia comença amb '2'
                if (linia.startsWith("2")) {
                	// Comprova si la línia té almenys 154 caràcters
                    if (linia.length() >= 154) {
                        // Extreu els caràcters de les posicions 150 a 153
                        String epigrafs = linia.substring(149, 153);
                        
                        // Actualitza el comptador al map
                        map.put(epigrafs, map.getOrDefault(epigrafs, 0) + 1);
                    }
                    // Processa la línia que comença amb '2' aquí
                    linies.add(linia);
                    System.out.println(linia);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		// Imprimeix per consola el contingut del map
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println("Epígraf: " + entry.getKey() + ", Comptador: " + entry.getValue());
        }
        
        // Escriu el contingut del map al fitxer de resultat
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTH_RESULTFILE))) {
        	for (String linia: linies) {
        		bw.write(linia);
        		bw.newLine();
        	}
        	bw.newLine();
        	bw.newLine();
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                bw.write("Epígraf: " + entry.getKey() + ", Comptador: " + entry.getValue());
                bw.newLine();  // Afegeix un salt de línia
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Fitxer de resultat generat a " + RUTH_RESULTFILE);
	}
	
	
}
