package sn.brasilburger.Repository;


import sn.brasilburger.Entity.Complement;
import sn.brasilburger.Entity.Enum.TypeComplement;

import java.util.List;
import java.util.Optional;

public interface ComplementRepository {
    int numberOfRows();
    int insert(Complement complement);
    Optional<Complement> selectById(int id);

    List<Complement> selectAll();
    int update(Complement complement);

    List<Complement> selectByType(TypeComplement typeComplement);


}
