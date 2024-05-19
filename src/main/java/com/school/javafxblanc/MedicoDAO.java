package com.school.javafxblanc;

import javafx.collections.ObservableList;

import java.sql.*;

public class MedicoDAO {
    public static int addMedico(Medico m) {
        Connection conn = ConnectionDB.openDB();
        PreparedStatement stmt = null;
        int id = 0;

        String sql = "INSERT INTO medico (nome, idade, idEspecialidade) VALUES (?, ?, ?);";

        try {
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, m.getNome());
            stmt.setInt(2, m.getIdade());
            stmt.setInt(3, m.getEspecialidade().getID());
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            System.out.println("Médico adicionado com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao adicionar novo médico: " + ex);

        } finally {
            ConnectionDB.closeDB(stmt);
        }
        return id;
    }


    public static ObservableList<Medico> listMedicos() {
        Connection conn = ConnectionDB.openDB();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ObservableList<Medico> medico = Settings.getMedico();
        medico.clear();
        String sql = "SELECT m.id, m.nome, m.idade, e.nome AS especialidade_nome FROM medico m, especialidade e WHERE m.idEspecialidade = e.id;";

        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                int idade = rs.getInt("idade");
                String especialidadeNome = rs.getString("especialidade_nome");
                Especialidade especialidade = new Especialidade(especialidadeNome);
                Medico m = new Medico(id, nome, idade, especialidade);
                medico.add(m);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao listar os medicos: " + ex);
        } finally {
            ConnectionDB.closeDB(stmt, rs);
        }
        return medico;
    }

    public static Medico pesquisaID(int num) {
        Connection conn = ConnectionDB.openDB();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Medico m = null;

        String sql = "SELECT m.id, m.nome, m.idade, m.idEspecialidade, e.nome AS especialidadeNome " +
                "FROM medico m " +
                "JOIN especialidade e ON m.idEspecialidade = e.id " +
                "WHERE m.id = ?;";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, num);
            rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                int idade = rs.getInt("idade");
                int idEspecialidade = rs.getInt("idEspecialidade");
                String especialidadeNome = rs.getString("especialidadeNome");
                Especialidade especialidade = new Especialidade(especialidadeNome, idEspecialidade);
                m = new Medico(id, nome, idade, especialidade);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao pesquisar o médico: " + ex);
        } finally {
            ConnectionDB.closeDB(stmt, rs);
        }
        return m;
    }


    public static void removeMedico(int id){
        Connection conn = ConnectionDB.openDB();
        PreparedStatement stmt = null;

        String sql = "DELETE FROM medico WHERE id = ?;";

        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            System.out.println("Medico eliminado com sucesso");
        }
        catch (SQLException ex) {
            System.out.println("Erro ao eliminar medico: "+ex);
        }
        finally {
            ConnectionDB.closeDB(stmt);
        }
    }

    public static void updateMedico(Medico m) {
        Connection conn = ConnectionDB.openDB();
        PreparedStatement stmt = null;

        String sql = "UPDATE medico SET nome = ?, idade = ?, idEspecialidade = ? WHERE id = ?;";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, m.getNome());
            stmt.setInt(2, m.getIdade());
            stmt.setInt(3, m.getEspecialidade().getID());
            stmt.setInt(4, m.getID());
            stmt.execute();
            System.out.println("Médico atualizado com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar médico: " + ex);

        } finally {
            ConnectionDB.closeDB(stmt);
        }
    }


    public static int getEspecialidadeIdByName(String nome) {
        Connection conn = ConnectionDB.openDB();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int id = -1;

        String sql = "SELECT id FROM especialidade WHERE nome = ?;";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao obter ID da especialidade: " + ex);
        } finally {
            ConnectionDB.closeDB(stmt, rs);
        }
        return id;
    }
}
