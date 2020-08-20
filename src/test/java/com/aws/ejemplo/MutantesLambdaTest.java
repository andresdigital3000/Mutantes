package com.aws.ejemplo;

import dto.RequestDTO;
import dto.ResponseDTO;
import org.junit.jupiter.api.Test;
import util.ConstantsMutants;

import static org.junit.jupiter.api.Assertions.*;

class MutantesLambdaTest {
    MutantesLambda mutantes;
    String[] dnaHumano;
    String[] dnaMutant;

    RequestDTO requestDTOHumano;
    RequestDTO requestDTOMutant;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        mutantes = new MutantesLambda();
        dnaHumano = new String[]{"ATGCGA","CAGTGC","TTATTT","AGACGG","GCGTCA","TCACTG"};
        dnaMutant = new String[]{"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
    }

    @Test
    void handleRequestIsMutant() {
        //Given
        requestDTOMutant = new RequestDTO();
        requestDTOMutant.setDna(dnaMutant);

        //When
        ResponseDTO responseDTO = mutantes.handleRequest(requestDTOMutant, null);

        //Then
        ResponseDTO responseExpectedDTO = new ResponseDTO();
        responseExpectedDTO.setCode(ConstantsMutants.CODE_OK);
        responseExpectedDTO.setMessage(ConstantsMutants.MESSAGE_SUCCESS);
        assertEquals(responseExpectedDTO, responseDTO);
    }

    @Test
    void handleRequesWithEmptyMatriz() {
        //Given
        RequestDTO requestDTO;
        requestDTO = new RequestDTO();
        String[] dnaMutant = new String[]{};

        //When
        ResponseDTO responseDTO = mutantes.handleRequest(requestDTO, null);

        //Then
        ResponseDTO responseExpectedDTO = new ResponseDTO();
        responseExpectedDTO.setCode(ConstantsMutants.CODE_ERROR);
        responseExpectedDTO.setMessage(ConstantsMutants.MESSAGE_ERROR);
        assertEquals(responseExpectedDTO, responseDTO);
    }

    @Test
    void handleRequestInvalidADNNumberLines() {
        //Given
        requestDTOHumano = new RequestDTO();
        String[] dnaMutant = new String[]{"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA"};
        requestDTOHumano.setDna(dnaMutant);


        //When
        ResponseDTO responseDTO = mutantes.handleRequest(requestDTOHumano, null);

        //Then
        ResponseDTO responseExpectedDTO = new ResponseDTO();
        responseExpectedDTO.setCode(ConstantsMutants.CODE_ERROR);
        responseExpectedDTO.setMessage(ConstantsMutants.MESSAGE_ERROR);

        ResponseDTO responsedDTO = mutantes.handleRequest(requestDTOHumano, null);
        assertEquals(responseExpectedDTO, responsedDTO);
    }

    @Test
    void handleRequestInvalidADNNumberInline() {
        //Given
        requestDTOHumano = new RequestDTO();
        String[] dnaMutant = new String[]{"ATGCA","CAGTGC","TTATGT","AGAAGG","CCCCTA", "TCACTG"};
        requestDTOHumano.setDna(dnaMutant);


        //When
        ResponseDTO responseDTO = mutantes.handleRequest(requestDTOHumano, null);

        //Then
        ResponseDTO responseExpectedDTO = new ResponseDTO();
        responseExpectedDTO.setCode(ConstantsMutants.CODE_ERROR);
        responseExpectedDTO.setMessage(ConstantsMutants.MESSAGE_ERROR);

        ResponseDTO responsedDTO = mutantes.handleRequest(requestDTOHumano, null);
        assertEquals(responseExpectedDTO, responsedDTO);
    }

    @Test
    void handleRequestIsNotMutant() {
        //Given
        requestDTOHumano = new RequestDTO();
        requestDTOHumano.setDna(dnaHumano);


        //When
        ResponseDTO responseDTO = mutantes.handleRequest(requestDTOHumano, null);

        //Then
        ResponseDTO responseExpectedDTO = new ResponseDTO();
        responseExpectedDTO.setCode(ConstantsMutants.CODE_ERROR);
        responseExpectedDTO.setMessage(ConstantsMutants.MESSAGE_ERROR);

        ResponseDTO responsedDTO = mutantes.handleRequest(requestDTOHumano, null);
        assertEquals(responseExpectedDTO, responsedDTO);
    }

    @Test
    void isMutant() {
        boolean isMutant = mutantes.isMutant(dnaMutant);
        assertTrue(isMutant);
    }

    @Test
    void isNotMutant() {
        boolean isMutant = mutantes.isMutant(dnaHumano);
        assertFalse(isMutant);
    }

    @Test
    void cols() {
        String[] cols = mutantes.cols(dnaMutant);
        String [] expected = new String[]{"ACTACT","TATGCC","GGAACA","CTTACC","GGGGTT","ACTGAG"};
        assertArrayEquals(expected, cols);
    }

    @Test
    void diagonal() {
        String[] cols = mutantes.diagonal(dnaMutant);
        String [] expected = new String[]{"TGCC","CTACT","AAAATG","TGTGA","GTGG"};
        assertArrayEquals(expected, cols);
    }

    @Test
    void testIsLineNotMutant() {
        String auxMutant = "ACTACT";
        boolean isMutant = mutantes.isLineMutant(auxMutant);
        assertFalse(isMutant);
    }

    @Test
    void testIsLineMutant() {
        String auxMutant4 = "ACTTTT";
        boolean isMutant = mutantes.isLineMutant(auxMutant4);
        assertTrue(isMutant);
    }
}