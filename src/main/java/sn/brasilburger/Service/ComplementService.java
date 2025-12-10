package sn.brasilburger.Service;

import sn.brasilburger.Entity.Complement;

import java.util.List;
import java.util.Optional;

public interface ComplementService {
    void createComplement(Complement complement);


    int numberOfRows();
}


