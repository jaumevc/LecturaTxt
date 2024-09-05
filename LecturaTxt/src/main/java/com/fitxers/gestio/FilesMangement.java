package com.fitxers.gestio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FilesMangement {

	private static final String RUTH_FILE = "C:\\Users\\jvalls\\Desktop\\Tasques_Ramon\\29_50110_sergi\\FILES\\MDIAE2024.txt";
	private static final String RUTH_EPIGRAFS_NAME = "C:\\Users\\jvalls\\Desktop\\Tasques_Ramon\\29_50110_sergi\\FILES\\epigrafs.txt";
//	private static final String RUTH_RESULTFILE = "C:\\Users\\jvalls\\Desktop\\Tasques_Ramon\\29_50110_sergi\\FILES\\RESULTFILE\\resultat.txt";
	private static final String RUTH_RESULTFILE = "C:\\Users\\jvalls\\Desktop\\Tasques_Ramon\\29_50110_sergi\\FILES\\RESULTFILE\\resultat.xlsx";

	public void getFileFromFolder() throws FileNotFoundException, IOException {
//		Map<String, Integer> map = new HashMap();
		// per obtenir el mapa ordenat per les claus de major a menor
		Map<String, Integer> totalMap = new TreeMap<>();
		Map<String, Integer> superficieMap = new TreeMap<>();
		Map<String, Integer> noSuperficieMap = new TreeMap<>();
		Map<String, String> epigrafNameMap = new TreeMap<>();

//		List<String> linies = new ArrayList<>();

		createMapsFromRuthFile(totalMap, superficieMap, noSuperficieMap);
		
		createEpigrafNamesMapFromFile(epigrafNameMap);
		
		displayDataByConsole(epigrafNameMap, totalMap, superficieMap, noSuperficieMap);

		/* TXT:*/
		// Escriu el contingut del map al fitxer de resultat
//        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTH_RESULTFILE))) {
//        	for (String linia: linies) {
//        		bw.write(linia);
//        		bw.newLine();
//        	}
//        	bw.newLine();
//        	bw.newLine();
//            for (Map.Entry<String, Integer> entry : totalMap.entrySet()) {
//                bw.write("Epígraf: " + entry.getKey() + ", Comptador: " + entry.getValue());
//                bw.newLine();  // Afegeix un salt de línia
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

		/* EXCEL:*/
		// Escriu el contingut del map al fitxer de resultat Excel
		
		createExcelWithEpigrafs(totalMap,superficieMap, noSuperficieMap,epigrafNameMap);
		
	}


	private void createExcelWithEpigrafs(Map<String, Integer> totalMap, Map<String, Integer> superficieMap,
			Map<String, Integer> noSuperficieMap ,Map<String, String> epigrafNameMap) {
		Workbook workbook = new XSSFWorkbook();
		
		String sheetName = "Resultats Totals";
		
		String headerNameKey = "Epigrafs";
		String headerNameValue = "Comptador";
		String headerEpigrafName = "Nom Epigraf";
		
		createSheet(workbook, totalMap, epigrafNameMap,sheetName, headerNameKey, headerNameValue, headerEpigrafName);
		
		sheetName = "Resultats Amb Superfícies";
		headerNameValue = "Amb Superfícies";
		
		createSheet(workbook, superficieMap, epigrafNameMap,sheetName, headerNameKey, headerNameValue, headerEpigrafName);

		sheetName = "Resultats Sense Superfícies";
		headerNameValue = "Sense Superfícies";
		
		createSheet(workbook, noSuperficieMap, epigrafNameMap,sheetName, headerNameKey, headerNameValue, headerEpigrafName);

		// Escriu el workbook a un fitxer
		try (FileOutputStream fileOut = new FileOutputStream(RUTH_RESULTFILE)) {
			workbook.write(fileOut);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Fitxer de resultat generat a " + RUTH_RESULTFILE);
	}


	private void displayDataByConsole(Map<String, String> epigrafNameMap, Map<String, Integer> totalMap,
			Map<String, Integer> superficieMap, Map<String, Integer> noSuperficieMap) {
		System.out.println("\nEpigrafs i NOMS : \n");
		for (Map.Entry<String, String> entry : epigrafNameMap.entrySet()) {
			System.out.println("Epígraf: " + entry.getKey() + ", Nom: " + entry.getValue());
		}
		
		System.out.println("\nEpigrafs Totals: \n");
		// Imprimeix per consola el contingut del map
		for (Map.Entry<String, Integer> entry : totalMap.entrySet()) {
			System.out.println("Epígraf: " + entry.getKey() + ", Comptador: " + entry.getValue());
		}

		System.out.println("\nEpigrafs AMB Superficie : \n");
		// Imprimeix per consola el contingut del map
		for (Map.Entry<String, Integer> entry : superficieMap.entrySet()) {
			System.out.println("Epígraf: " + entry.getKey() + ", Amb Superf.: " + entry.getValue());
		}

		System.out.println("\nEpigrafs SENSE Superficie : \n");
		// Imprimeix per consola el contingut del map
		for (Map.Entry<String, Integer> entry : noSuperficieMap.entrySet()) {
			System.out.println("Epígraf: " + entry.getKey() + ", Sense Superf.: " + entry.getValue());
		}
		
	}

	private void createEpigrafNamesMapFromFile(Map<String, String> epigrafNameMap) {
		try {
			BufferedReader brEpi = new BufferedReader(new FileReader(RUTH_EPIGRAFS_NAME));
			String linia;
			while ((linia = brEpi.readLine()) != null) {
				String epigrafs = linia.substring(0, 4);
				String epigrafName = linia.substring(5, 45);
				epigrafNameMap.put(epigrafs, epigrafName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void createMapsFromRuthFile(Map<String, Integer> totalMap, Map<String, Integer> superficieMap,
			Map<String, Integer> noSuperficieMap) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(RUTH_FILE));
			String linia;
			while ((linia = br.readLine()) != null) {
				// Comprova si la línia comença amb '2'
				if (linia.startsWith("2")) {
					// Comprova si la línia té almenys 154 caràcters
					if (linia.length() >= 384) {
						// Extreu els caràcters de les posicions 151 a 154
						String epigrafs = linia.substring(150, 154);
						// Actualitza el comptador al map de totals
						totalMap.put(epigrafs, totalMap.getOrDefault(epigrafs, 0) + 1);

						String superficie = linia.substring(366, 383);
						Long superfValue = Long.parseLong(superficie);
						if (superfValue > 0) {
							superficieMap.put(epigrafs, superficieMap.getOrDefault(epigrafs, 0) + 1);
						} else {
							noSuperficieMap.put(epigrafs, noSuperficieMap.getOrDefault(epigrafs, 0) + 1);
						}
					}
					// Processa la línia que comença amb '2' aquí
//					linies.add(linia);
//                    System.out.println(linia);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void createSheet(Workbook workbook, Map<String, Integer> map,  Map<String, String> namesMap,String sheetName, String headerNameKey,
			String headerNameValue, String headerEpigrafName) {
		
		Sheet sheet = workbook.createSheet(sheetName);

		// Crear la fila de capçalera
		Row headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue(headerNameKey);
		headerRow.createCell(1).setCellValue(headerNameValue);
		headerRow.createCell(2).setCellValue(headerEpigrafName);

		int rowNum = 1;
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(entry.getKey());
			row.createCell(1).setCellValue(entry.getValue());
			row.createCell(2).setCellValue(namesMap.get(entry.getKey()));
		}

	}

}
