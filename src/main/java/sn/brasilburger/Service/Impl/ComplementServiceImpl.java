package sn.brasilburger.Service.Impl;

import sn.brasilburger.Entity.Complement;
import sn.brasilburger.Repository.ComplementRepository;
import sn.brasilburger.Service.ComplementService;

import java.util.List;
import java.util.Optional;

public class ComplementServiceImpl implements ComplementService {

    private ComplementRepository complementRepository;

    public ComplementServiceImpl(ComplementRepository complementRepository) {
        this.complementRepository = complementRepository;
    }

    @Override
    public void createComplement(Complement complement) {
        complementRepository.insert(complement);
    }

    @Override
    public Optional<Complement> selectById(int id) {
        return complementRepository.selectById(id);
    }

    @Override
    public List<Complement> selectAll() {
        return complementRepository.selectAll();
    }

    @Override
    public int numberOfRows()
    {
        return complementRepository.numberOfRows();
    }

}
