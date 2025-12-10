package sn.brasilburger.Repository.Impl;

import sn.brasilburger.Entity.Complement;
import sn.brasilburger.Entity.Enum.TypeComplement;
import sn.brasilburger.Repository.ComplementRepository;
import sn.brasilburger.config.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ComplementRepositoryImpl implements ComplementRepository {
    private Database database;

    public ComplementRepositoryImpl(Database database) {
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
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM complements");
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
    public int insert(Complement c) {
        try {
            if (!database.isConnected()) {
                throw new SQLException("Erreur de connexion à la BD");
            }
            Connection conn = database.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO complements (id, libelle, prix, image_url, is_archived, type_complement) " +
                            "VALUES (?, ?, ?, ?, ?, ?::type_complement)"
            );

            ps.setInt(1, c.getId());
            ps.setString(2, c.getLibelle());
            ps.setDouble(3, c.getPrix());
            ps.setString(4, c.getImageUrl());
            ps.setBoolean(5, c.getArchived());
            ps.setString(6, c.getTypeComplement().name());

            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }



}
