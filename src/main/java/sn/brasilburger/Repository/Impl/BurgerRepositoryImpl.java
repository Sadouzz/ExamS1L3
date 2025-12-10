package sn.brasilburger.Repository.Impl;

import sn.brasilburger.Entity.Burger;
import sn.brasilburger.Repository.BurgerRepository;
import sn.brasilburger.config.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BurgerRepositoryImpl implements BurgerRepository {
    private Database database;

    public BurgerRepositoryImpl(Database database) {
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
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM quartiers");
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
    public int insert(Burger burger) {
        try {
            if (!database.isConnected()) {
                throw new SQLException("Erreur de connexion à la BD");
            }
            Connection conn = database.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO burgers (id, libelle, description, prix, image_url, is_archived, burger_categorie_id) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)"
            );

            ps.setInt(1, burger.getId());
            ps.setString(2, burger.getLibelle());
            ps.setString(3, burger.getDesc());
            ps.setDouble(4, burger.getPrix());
            ps.setString(5, burger.getImageUrl());
            ps.setBoolean(6, burger.getArchived());
            ps.setInt(7, burger.getBurgerCategorieId());

            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private Burger toEntity(ResultSet rs) throws SQLException {
        Burger burger = new Burger();
        burger.setId(rs.getInt("id"));
        burger.setLibelle(rs.getString("libelle"));
        burger.setDesc(rs.getString("description"));
        burger.setPrix(rs.getDouble("prix"));
        burger.setImageUrl(rs.getString("image_url"));
        burger.setArchived(rs.getBoolean("is_archived"));
        burger.setBurgerCategorieId(rs.getInt("burger_categorie_id"));
        return burger;
    }

}
