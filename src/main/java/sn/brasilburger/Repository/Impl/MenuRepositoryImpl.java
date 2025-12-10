package sn.brasilburger.Repository.Impl;

import sn.brasilburger.Entity.Menu;
import sn.brasilburger.Repository.MenuRepository;
import sn.brasilburger.config.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuRepositoryImpl implements MenuRepository {
    private Database database;

    public MenuRepositoryImpl(Database database) {
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
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM menus");
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
    public int insert(Menu menu) {
        try {
            if (!database.isConnected()) {
                throw new SQLException("Erreur de connexion à la BD");
            }
            Connection conn = database.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO menus (id, libelle, image_url, is_archived, prix) VALUES (?, ?, ?, ?, ?)"
            );

            ps.setInt(1, menu.getId());
            ps.setString(2, menu.getLibelle());
            ps.setString(3, menu.getImageUrl());
            ps.setBoolean(4, menu.getArchived());
            ps.setDouble(5, menu.getPrix());

            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(Menu menu) {
        try {
            if (!database.isConnected()) {
                throw new SQLException("Erreur de connexion à la BD");
            }

            Connection conn = database.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE menus SET libelle = ?, image_url = ?, is_archived = ?, prix = ? WHERE id = ?"
            );

            ps.setString(1, menu.getLibelle());
            ps.setString(2, menu.getImageUrl());
            ps.setBoolean(3, menu.getArchived());
            ps.setDouble(4, menu.getPrix());
            ps.setInt(5, menu.getId());

            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


}
