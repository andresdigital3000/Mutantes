package com.aws.ejemplo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import dao.MutanteDAO;
import dto.*;

import java.util.Optional;

/**
 * @author Andres Bedoya
 */
public class StatsLambda implements RequestHandler<RequestStatsDTO, ResponseStatsDTO> {
    public ResponseStatsDTO handleRequest(RequestStatsDTO requestDTO, Context context) {
        ResponseInsertDTO responseInsertDTO = new ResponseInsertDTO();
        MutanteDAO mutanteDAO = new MutanteDAO();
        ResponseStatsDTO responseDTO = mutanteDAO.getStats();
        return responseDTO;

    }
    /**
     * Recibe una matriz y retorna true si la cadena corresponde a un mutante
     *
     * @param dna matriz del individuo
     * @return si el individuo es mutante o no
     */
    boolean isMutant(String[] dna) {
        String[] columnas = cols(dna);
        String[] diagonal = diagonal(dna);
        int numCadenasADNByRow = numLinesADNByType(dna);
        int numCadenasADNByCol = numLinesADNByType(columnas);
        int numCadenasADNByDiagonal = numLinesADNByType(diagonal);

        int totalCadenasADN = numCadenasADNByRow + numCadenasADNByCol + numCadenasADNByDiagonal;
        return totalCadenasADN > 2;
    }

    /**
     * Retorna el numero de cadenas que coinciden con la genetica de un mutante por fila, columna o diagonal
     *
     * @param dnaType si es x fila, columna o diagonal
     * @return numero de cadenas
     */
    private int numLinesADNByType(String[] dnaTypeAux) {
        int secuencia = 0;
        Optional <String[]> dnaType = Optional.ofNullable(dnaTypeAux);
        if(dnaType.isPresent()) {
            for (String item : dnaType.get()) {
                if (isLineMutant(item)) {
                    secuencia++;
                    break;
                }
            }
        }

        return secuencia;
    }

    /**
     * Indica si una linea de ADN es mutante o no
     * @param lineADNToEvaluate
     * @return
     */
    public boolean isLineMutant(String lineADNToEvaluate) {
        boolean isMutant = false;
        int longestCount = longestCount(lineADNToEvaluate);
        if (longestCount >= 4) {
            isMutant = true;
        }
        return isMutant;
    }

    /**
     * Obtiene las columnas de la matriz
     *
     * @param dna
     * @return
     */
    protected String[] cols(String[] dna) {
        String[] cols = null;
        if (dna != null) {
            cols = new String[dna.length];
            String auxCol = "";
            if (dna.length == 6) {
                for (int col = 0; col < dna.length; col++) {
                    for (int i = 0; i < dna.length; i++) {
                        if (dna[i].length() == 6) {
                            auxCol += dna[i].charAt(col);
                        }
                    }
                    cols[col] = auxCol;
                    auxCol = "";
                }
            }
        }
        return cols;
    }

    /**
     * Obtiene las diagonales de la matriz
     *
     * @param dna
     * @return
     */
    protected String[] diagonal(String[] dna) {
        String[] diagonal = new String[5];
        if (dna != null) {
            String auxCol;
            int iAux = 2;
            int jAux = 0;
            if (dna.length == 6) {
                StringBuilder auxColBuilder1 = new StringBuilder();
                for (int z = 0; z < 3; z++) {
                    for (int i = iAux, j = jAux; i < 6; i++, j++) {
                        auxColBuilder1.append(dna[i].charAt(j));
                    }
                    diagonal[z] = auxColBuilder1.toString();
                    iAux = iAux > 0 ? iAux - 1 : iAux;
                    auxColBuilder1 = new StringBuilder();
                }
                auxCol = auxColBuilder1.toString();
                iAux = 0;
                jAux = 1;
                StringBuilder auxColBuilder = new StringBuilder(auxCol);
                for (int z = 3; z < 5; z++) {
                    for (int i = iAux, j = jAux; j < 6; i++, j++) {
                        auxColBuilder.append(dna[i].charAt(j));
                    }
                    diagonal[z] = auxColBuilder.toString();
                    jAux = jAux > 0 ? jAux + 1 : jAux;
                    auxColBuilder = new StringBuilder();
                }
                auxCol = auxColBuilder.toString();
            }
        }
        return diagonal;
    }

    /**
     * retorna el mayor numero de coincidencias de cualquier caracter en una linea de ADN
     * @param lineDNA
     * @return
     */
    public int longestCount(String lineDNA) {
        Optional<String> dna = Optional.ofNullable(lineDNA);
        char[] array = dna.isPresent() ? lineDNA.toCharArray() : new char[0];
        int count = 1;
        int max = 0;
        char maxChar = 0;
        if (array != null && array.length == 6) {
            for (int i = 1; i < array.length; i++) { // Start from 1 since we want to compare it with the char in index 0
                if (array[i] == array[i - 1]) {
                    count++;
                } else {
                    if (count > max) {  // Record current run length, is it the maximum?
                        max = count;
                        maxChar = array[i - 1];
                    }
                    count = 1; // Reset the count
                }
            }
            if (count > max) {
                max = count; // This is to account for the last run
                maxChar = array[array.length - 1];
            }
        }
        return max;
    }
}
