package sn.brasilburger.Repository.Impl;

import sn.brasilburger.Entity.MenuComplement;
import sn.brasilburger.Repository.MenuComplementRepository;
import sn.brasilburger.config.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MenuComplementRepositoryImpl implements MenuComplementRepository {
    private Database database;

    public MenuComplementRepositoryImpl(Database database) {
        this.database = database;
    }

    @Override
    public int numberOfRows() {
        int count = 0;
        try {
            if (!database.isConnected()) {
                throw new SQLException("Erreur de connexion à la BD");
            }
            Connection conn = database.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM menu_complement");
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }


    @Override
    public int insert(MenuComplement mc) {
        try {
            if (!database.isConnected()) {
                throw new SQLException("Erreur de connexion à la BD");
            }
            Connection conn = database.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO menu_complement (id, menu_id, complement_id, quantite) VALUES (?, ?, ?, ?)"
            );

            ps.setInt(1, mc.getId());
            ps.setInt(2, mc.getMenuId());
            ps.setInt(3, mc.getComplementId());
            ps.setInt(4, mc.getQuantite());

            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


    @Override
    public Optional<MenuComplement> selectById(int id) {
        Connection conn = database.getConnection();
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement("select * from menu_complement where id = ?");
            ps.setInt(1, id);
            return database.<MenuComplement>fetch(ps, this::toEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<MenuComplement> selectAll() {
        try {
            Connection conn = database.getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from menu_complement");
            return database.<MenuComplement>fetchAll(ps, this::toEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private MenuComplement toEntity(ResultSet rs) throws SQLException {
        MenuComplement mc = new MenuComplement();
        mc.setId(rs.getInt("id"));
        mc.setMenuId(rs.getInt("menu_id"));
        mc.setComplementId(rs.getInt("complement_id"));
        mc.setQuantite(rs.getInt("quantite"));
        return mc;
    }

}
