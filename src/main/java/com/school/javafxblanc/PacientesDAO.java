package com.school.javafxblanc;

import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class PacientesDAO {
    public static int addPaciente(Paciente p) {
        Connection conn = ConnectionDB.openDB();
        PreparedStatement stmt = null;
        int id = 0;

        String sql = "INSERT INTO paciente (nome, idade, morada, telefone, email, numUtente) VALUES (?, ?, ?, ?, ?, ?);";

        try {
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, p.getNome());
            stmt.setInt(2, p.getIdade());
            stmt.setString(3, p.getMorada());
            stmt.setString(4, p.getTelefone());
            stmt.setString(5, p.getEmail());
            stmt.setInt(6, p.getNumUtente());
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            System.out.println("Paciente adicionado com sucesso");
        } catch (
                SQLException ex) {
            System.out.println("Erro ao adicionar novo Paciente: " + ex);

        } finally {
            ConnectionDB.closeDB(stmt);
        }
        return id;
    }

    public static ObservableList<Paciente> listPacientes() {
        Connection conn = ConnectionDB.openDB();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ObservableList<Paciente> paciente = Settings.getPaciente();
        paciente.clear();
        String sql = "SELECT * FROM paciente;";

        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                int idade = rs.getInt("idade");
                String morada = rs.getString("morada");
                String telefone = rs.getString("telefone");
                String email = rs.getString("email");
                int numUtente = rs.getInt("numUtente");
                Paciente p = new Paciente(id, nome, idade, morada, telefone, email, numUtente);
                paciente.add(p);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao listar os pacientes: " + ex);
        } finally {
            ConnectionDB.closeDB(stmt, rs);
        }
        return paciente;
    }

    public static Paciente pesquisaID(int num) {
        Connection conn = ConnectionDB.openDB();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Paciente p = null;

        String sql = "SELECT * FROM paciente WHERE id = ?;";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, num);  // Definindo o parâmetro ID
            rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                int idade = rs.getInt("idade");
                String morada = rs.getString("morada");
                String telefone = rs.getString("telefone");
                String email = rs.getString("email");
                int numUtente = rs.getInt("numUtente");
                p = new Paciente(id, nome, idade, morada, telefone, email, numUtente);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao pesquisar o paciente: " + ex);
        } finally {
            ConnectionDB.closeDB(stmt, rs);
        }
        return p;
    }

    public static void removePaciente(int id){
        Connection conn = ConnectionDB.openDB();
        PreparedStatement stmt = null;

        String sql = "DELETE FROM paciente WHERE id = ?;";

        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            System.out.println("Paciente eliminado com sucesso");
        }
        catch (SQLException ex) {
            System.out.println("Erro ao eliminar paciente: "+ex);
        }
        finally {
            ConnectionDB.closeDB(stmt);
        }
    }

    public static void updatePaciente(Paciente p){
        Connection conn = ConnectionDB.openDB();
        PreparedStatement stmt = null;

        String sql = "UPDATE paciente SET nome = ?, idade = ?, morada = ?, telefone = ?, email = ?, numUtente = ? WHERE id = ?;";

        try{
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, p.getNome());
            stmt.setInt(2, p.getIdade());
            stmt.setString(3, p.getMorada());
            stmt.setString(4, p.getTelefone());
            stmt.setString(5, p.getEmail());
            stmt.setInt(6, p.getNumUtente());
            stmt.execute();
            System.out.println("Paciente atualizado com sucesso");
        }
        catch(SQLException ex){
            System.out.println("Erro ao atualizar paciente: "+ex);
        }
        finally {
            ConnectionDB.closeDB(stmt);
        }
    }
}
