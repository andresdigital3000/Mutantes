package dao;

import dto.ResponseInsertDTO;
import dto.ResponseStatsDTO;
import util.Configuration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MutanteDAO {

    public ResponseInsertDTO insertStatsHumans(){
        ResponseInsertDTO response = new ResponseInsertDTO();
        Configuration configuration = new Configuration();

        String query = "UPDATE phDB.STATS SET HUMANS = HUMANS + 1 WHERE id = ?";
        try (Connection conn = configuration.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1,1);//1 specifies the first parameter in the query
            int responseCode = stmt.executeUpdate();
            if(responseCode == 1){
                response.setCode(String.valueOf(responseCode));
                response.setMessage("El Humano ha sido agregado a la estadistica");
            }
            //close the connection
        } catch (Exception sqlException) {
            response.setCode("500");
            response.setMessage("Error registrando el humano " + sqlException);
        }
        return response;
    }

    public ResponseInsertDTO insertStatsMutant(){
        ResponseInsertDTO response = new ResponseInsertDTO();
        Configuration configuration = new Configuration();
        String query = "UPDATE phDB.STATS SET MUTANTS = MUTANTS + 1 WHERE id = ?";
        try (Connection conn = configuration.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)){
            //verify the connection is successful
            stmt.setInt(1,1);//1 specifies the first parameter in the query
            int responseCode = stmt.executeUpdate();
            if(responseCode == 1){
                response.setCode(String.valueOf(responseCode));
                response.setMessage("El Mutante ha sido agregado a la estadistica");
            }
            //close the connection
        } catch (Exception sqlException) {
            response.setCode("500");
            response.setMessage("Error registrando el mutante " + sqlException);
        }
        return response;
    }

    public ResponseStatsDTO getStats(){
        ResponseStatsDTO response = new ResponseStatsDTO();
        Configuration configuration = new Configuration();
        String query = "SELECT * FROM phDB.STATS WHERE id = ?";
        try (Connection conn = configuration.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)){
            //verify the connection is successful
            stmt.setInt(1,1);//1 specifies the first parameter in the query
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                response.setHumans(rs.getInt("humans"));
                response.setMutants(rs.getInt("mutants"));
            }
            //close the connection
        } catch (Exception sqlException) {
            response.setMensaje("Error obteniendo stats " + sqlException);
        }
        return response;
    }


}
