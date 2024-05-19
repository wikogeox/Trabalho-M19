package com.school.javafxblanc;

import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class ConsultaDAO {
    public static int addConsulta(Consulta c) {
        Connection conn = ConnectionDB.openDB();
        PreparedStatement stmt = null;
        int id = 0;

        // Obtém os IDs do médico e do paciente pelos seus nomes
        int idMedico = getIdMedicoByNome(c.getMedico().getNome());
        int idPaciente = getIdPacienteByNome(c.getPaciente().getNome());

        // Verifica se os IDs foram obtidos com sucesso
        if (idMedico == -1 || idPaciente == -1) {
            System.out.println("Erro: Não foi possível obter os IDs do médico e do paciente.");
            return 0;
        }

        String sql = "INSERT INTO consulta (idMedico, idPaciente, dataConsulta, horaConsulta) VALUES (?, ?, ?, ?);";

        try {
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, idMedico);
            stmt.setInt(2, idPaciente);
            stmt.setDate(3, Date.valueOf((LocalDate) c.getDataConsulta()));
            stmt.setTime(4, c.getHoraConsulta());
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            System.out.println("Consulta adicionada com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao adicionar nova consulta: " + ex);

        } finally {
            ConnectionDB.closeDB(stmt);
        }
        return id;
    }


    public static ObservableList<Consulta> lisConsulta() {
        Connection conn = ConnectionDB.openDB();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ObservableList<Consulta> consulta = Settings.getConsulta();
        consulta.clear();

        String sql = "SELECT c.id, c.idMedico, c.idPaciente, c.dataConsulta, c.horaConsulta, m.nome AS nomeMedico, p.nome AS nomePaciente " +
                "FROM consulta c " +
                "JOIN medico m ON c.idMedico = m.id " +
                "JOIN paciente p ON c.idPaciente = p.id;";
        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int IDM = rs.getInt("ID");
                int idMedico = rs.getInt("idMedico");
                int idPaciente = rs.getInt("idPaciente");
                LocalDate dataConsulta = rs.getDate("dataConsulta").toLocalDate();
                Time horaConsulta = rs.getTime("horaConsulta");
                String nomeMedico = rs.getString("nomeMedico");
                String nomePaciente = rs.getString("nomePaciente");

                Especialidade especialidade = new Especialidade("");
                Medico medico = new Medico(idMedico, nomeMedico, 0, especialidade);
                Paciente paciente = new Paciente(idPaciente, nomePaciente, 0, "", "", "", 000000000);
                Consulta c = new Consulta(IDM, medico, paciente, dataConsulta, horaConsulta);
                consulta.add(c);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao listar as consultas: " + ex);
        } finally {
            ConnectionDB.closeDB(stmt, rs);
        }
        return consulta;
    }

    public static Consulta pesquisaID(int idConsulta) {
        Connection conn = ConnectionDB.openDB();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Consulta consulta = null;

        String sql = "SELECT c.id, c.idMedico, c.idPaciente, c.dataConsulta, c.horaConsulta, m.nome AS nomeMedico, p.nome AS nomePaciente " +
                "FROM consulta c " +
                "JOIN medico m ON c.idMedico = m.id " +
                "JOIN paciente p ON c.idPaciente = p.id " +
                "WHERE c.id = ?;";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idConsulta);
            rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                int idMedico = rs.getInt("idMedico");
                int idPaciente = rs.getInt("idPaciente");
                LocalDate dataConsulta = rs.getDate("dataConsulta").toLocalDate();
                Time horaConsulta = rs.getTime("horaConsulta");
                String nomeMedico = rs.getString("nomeMedico");
                String nomePaciente = rs.getString("nomePaciente");

                Especialidade especialidade = new Especialidade("");
                Medico medico = new Medico(idMedico, nomeMedico, 0, especialidade);
                Paciente paciente = new Paciente(idPaciente, nomePaciente, 0, "", "", "", 000000000);
                consulta = new Consulta(id, medico, paciente, dataConsulta, horaConsulta);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao pesquisar consulta por ID: " + ex);
        } finally {
            ConnectionDB.closeDB(stmt, rs);
        }
        return consulta;
    }


    public static void updateConsulta(Consulta c) {
        Connection conn = ConnectionDB.openDB();
        PreparedStatement stmt = null;

        // Obtém os IDs do médico e do paciente pelos seus nomes
        int idMedico = getIdMedicoByNome(c.getMedico().getNome());
        int idPaciente = getIdPacienteByNome(c.getPaciente().getNome());

        // Verifica se os IDs foram obtidos com sucesso
        if (idMedico == -1 || idPaciente == -1) {
            System.out.println("Erro: Não foi possível obter os IDs do médico e do paciente.");
            return;
        }

        String sql = "UPDATE consulta SET idMedico = ?, idPaciente = ?, dataConsulta = ?, horaConsulta = ? WHERE id = ?;";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idMedico);
            stmt.setInt(2, idPaciente);
            stmt.setDate(3, Date.valueOf((LocalDate) c.getDataConsulta()));
            stmt.setTime(4, c.getHoraConsulta());
            stmt.setInt(5, c.getIDM());
            stmt.executeUpdate();
            System.out.println("Consulta atualizada com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar consulta: " + ex);
        } finally {
            ConnectionDB.closeDB(stmt);
        }
    }

    public static void removeConsulta(int id){
        Connection conn = ConnectionDB.openDB();
        PreparedStatement stmt = null;

        String sql = "DELETE FROM consulta WHERE id = ?;";

        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            System.out.println("Consulta eliminada com sucesso");
        }
        catch (SQLException ex) {
            System.out.println("Erro ao eliminar consulta: "+ex);
        }
        finally {
            ConnectionDB.closeDB(stmt);
        }
    }

    // Método para obter o ID do médico pelo nome
    public static int getIdMedicoByNome(String nomeMedico) {
        Connection conn = ConnectionDB.openDB();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int id = -1;

        String sql = "SELECT id FROM medico WHERE nome = ?;";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nomeMedico);
            rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao obter ID do médico: " + ex);
        } finally {
            ConnectionDB.closeDB(stmt, rs);
        }
        return id;
    }

    // Método para obter o ID do paciente pelo nome
    public static int getIdPacienteByNome(String nomePaciente) {
        Connection conn = ConnectionDB.openDB();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int id = -1;

        String sql = "SELECT id FROM paciente WHERE nome = ?;";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nomePaciente);
            rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao obter ID do paciente: " + ex);
        } finally {
            ConnectionDB.closeDB(stmt, rs);
        }
        return id;
    }
}

